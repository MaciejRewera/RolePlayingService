package models.charactersheet.communication.internal

trait MessageBroker {
  def subscribe(subscriber: Subscriber, topic: String): Unit
  def unsubscribe(subscriber: Subscriber, topic: String): Unit
  def publish(message: Message[_], topic: String): Unit
}
