package models.charactersheet.communication.internal

import models.charactersheet.communication.internal.messages.Message

import scala.collection.mutable

class BasicMessageBroker extends MessageBroker {

  val topicsSubscribers: mutable.Map[String, Set[Subscriber]] = mutable.HashMap.empty

  //noinspection ScalaUnusedExpression
  override def subscribe(subscriber: Subscriber, topic: String): Unit =
    topicsSubscribers.get(topic) match {
      case Some(currentSubscribers) => topicsSubscribers += (topic -> (currentSubscribers + subscriber))
      case None                     => topicsSubscribers += (topic -> Set(subscriber))
    }

  override def unsubscribe(subscriber: Subscriber, topic: String): Unit =
    topicsSubscribers.get(topic).map { currentSubscribers =>
      if ((currentSubscribers - subscriber).isEmpty)
        topicsSubscribers -= topic
      else
        topicsSubscribers += (topic -> (currentSubscribers - subscriber))
    }

  override def publish(message: Message[_], topic: String): Unit =
    topicsSubscribers.get(topic).foreach { allSubscribers =>
      allSubscribers.foreach { subscriber => subscriber.onMessageReceived(message)
      }
    }

  def getAllTopics(): Seq[String] = topicsSubscribers.keys.toSeq

  def getAllSubscribers(topic: String): Seq[Subscriber] =
    topicsSubscribers.get(topic).fold[Seq[Subscriber]](Seq.empty)(_.toSeq)

}
