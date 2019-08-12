package models.charactersheet.communication.internal

import models.charactersheet.communication.internal.messages.Message
import org.mockito.ArgumentMatchers.{eq => meq}
import org.mockito.Mockito.{verify, verifyZeroInteractions}
import org.scalatest.{MustMatchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar

class BasicMessageBrokerSpec extends WordSpec with MustMatchers with MockitoSugar {

  private trait Test {
    val subscriberMock_1 = mock[Subscriber]
    val subscriberMock_2 = mock[Subscriber]
    val messageBroker = new BasicMessageBroker
  }

  "BasicMessageBroker on subscribe" when {

    "subscribing to non-existing topic" should {
      "create new record with given subscriber" in new Test {

        val newTopic = "NewTopic"
        messageBroker.subscribe(subscriberMock_1, newTopic)

        val allTopics = messageBroker.getAllTopics()
        allTopics.length must equal(1)
        allTopics must contain(newTopic)

        val allNewTopicSubscribers = messageBroker.getAllSubscribers(newTopic)
        allNewTopicSubscribers.length must equal(1)
        allNewTopicSubscribers must contain(subscriberMock_1)
      }
    }

    "subscribing to existing topic" should {
      "add given subscriber to the subscription list for this topic" in new Test {

        val topic = "Topic"
        messageBroker.subscribe(subscriberMock_1, topic)
        messageBroker.subscribe(subscriberMock_2, topic)

        val allTopicSubscribers = messageBroker.getAllSubscribers(topic)
        allTopicSubscribers.length must equal(2)
        allTopicSubscribers must contain(subscriberMock_1)
        allTopicSubscribers must contain(subscriberMock_2)
      }
    }

    "subscribing the same subscriber to the same topic twice" should {
      "result in having the subscriber being stored only once" in new Test {

        val topic = "Topic"
        messageBroker.subscribe(subscriberMock_1, topic)
        messageBroker.subscribe(subscriberMock_1, topic)

        val allTopicSubscribers = messageBroker.getAllSubscribers(topic)
        allTopicSubscribers.length must equal(1)
        allTopicSubscribers must contain(subscriberMock_1)
      }
    }

    "subscribing the same subscriber to 2 different topics" should {
      "result in having the subscriber present on both topics subscription lists" in new Test {

        val topic_1 = "TopicOne"
        val topic_2 = "TopicTwo"
        messageBroker.subscribe(subscriberMock_1, topic_1)
        messageBroker.subscribe(subscriberMock_1, topic_2)

        val allTopicOneSubscribers = messageBroker.getAllSubscribers(topic_1)
        allTopicOneSubscribers.length must equal(1)
        allTopicOneSubscribers must contain(subscriberMock_1)

        val allTopicTwoSubscribers = messageBroker.getAllSubscribers(topic_2)
        allTopicTwoSubscribers.length must equal(1)
        allTopicTwoSubscribers must contain(subscriberMock_1)
      }
    }
  }

  "BasicMessageBroker on unsubscribe" should {

    "result in no changes" when {

      "provided with non-existing topic" in new Test {

        messageBroker.unsubscribe(subscriberMock_1, "Non-existingTopic")

        messageBroker.getAllTopics() must be(empty)
      }

      "provided with non-existing topic, but Subscriber is present on a different topic's list" in new Test {

        val topic = "Topic"
        messageBroker.subscribe(subscriberMock_1, topic)

        messageBroker.unsubscribe(subscriberMock_1, "Non-existingTopic")

        val allTopicSubscribers = messageBroker.getAllSubscribers(topic)
        allTopicSubscribers.length must equal(1)
        allTopicSubscribers must contain(subscriberMock_1)
      }

      "provided with existing topic, but Subscriber is not present on the list" in new Test {

        val topic = "Topic"
        messageBroker.subscribe(subscriberMock_1, topic)

        messageBroker.unsubscribe(subscriberMock_2, topic)

        val allTopicSubscribers = messageBroker.getAllSubscribers(topic)
        allTopicSubscribers.length must equal(1)
        allTopicSubscribers must contain(subscriberMock_1)
      }

      "provided with existing topic, but Subscriber is present on a different topic's list" in new Test {

        val topic_1 = "TopicOne"
        val topic_2 = "TopicTwo"
        messageBroker.subscribe(subscriberMock_1, topic_1)
        messageBroker.subscribe(subscriberMock_2, topic_2)

        messageBroker.unsubscribe(subscriberMock_1, topic_2)

        val allTopicOneSubscribers = messageBroker.getAllSubscribers(topic_1)
        allTopicOneSubscribers.length must equal(1)
        allTopicOneSubscribers must contain(subscriberMock_1)

        val allTopicTwoSubscribers = messageBroker.getAllSubscribers(topic_2)
        allTopicTwoSubscribers.length must equal(1)
        allTopicTwoSubscribers must contain(subscriberMock_2)
      }

    }

    "remove the Subscriber from provided topic's list only" when {

      "provided with existing topic and Subscriber is present on the list" in new Test {

        val topic = "Topic"
        messageBroker.subscribe(subscriberMock_1, topic)
        messageBroker.subscribe(subscriberMock_2, topic)

        messageBroker.unsubscribe(subscriberMock_1, topic)

        val allTopicSubscribers = messageBroker.getAllSubscribers(topic)
        allTopicSubscribers.length must equal(1)
        allTopicSubscribers must contain(subscriberMock_2)
      }

      "provided with existing topic, but Subscriber is present also on a different topic's list" in new Test {

        val topic_1 = "TopicOne"
        val topic_2 = "TopicTwo"
        messageBroker.subscribe(subscriberMock_1, topic_1)
        messageBroker.subscribe(subscriberMock_1, topic_2)
        messageBroker.subscribe(subscriberMock_2, topic_1)

        messageBroker.unsubscribe(subscriberMock_1, topic_1)

        val allTopicOneSubscribers = messageBroker.getAllSubscribers(topic_1)
        allTopicOneSubscribers.length must equal(1)
        allTopicOneSubscribers must contain(subscriberMock_2)

        val allTopicTwoSubscribers = messageBroker.getAllSubscribers(topic_2)
        allTopicTwoSubscribers.length must equal(1)
        allTopicTwoSubscribers must contain(subscriberMock_1)
      }
    }

    "remove the topic if the Subscriber was the only subscriber to the topic" in new Test {

      val topic = "Topic"
      messageBroker.subscribe(subscriberMock_1, topic)

      messageBroker.unsubscribe(subscriberMock_1, topic)

      messageBroker.getAllTopics() must be(empty)
    }
  }

  "BasicMessageBroker on publish" when {

    "publishing to non-existing topic" should {
      "not call any Subscriber" in new Test {

        val topic = "Topic"
        messageBroker.subscribe(subscriberMock_1, topic)
        messageBroker.subscribe(subscriberMock_2, topic)

        messageBroker.publish(Message("msg"), "Non-existingTopic")

        verifyZeroInteractions(subscriberMock_1)
        verifyZeroInteractions(subscriberMock_2)
      }
    }

    "publishing to existing topic" should {

      "call every Subscriber to this topic once" in new Test {

        val topic = "Topic"
        messageBroker.subscribe(subscriberMock_1, topic)
        messageBroker.subscribe(subscriberMock_2, topic)

        val message = Message("msg")
        messageBroker.publish(message, topic)

        verify(subscriberMock_1).onMessageReceived(meq(message))
        verify(subscriberMock_2).onMessageReceived(meq(message))
      }

      "not call Subscribers to other topics" in new Test {

        val topic_1 = "TopicOne"
        val topic_2 = "TopicTwo"
        messageBroker.subscribe(subscriberMock_1, topic_1)
        messageBroker.subscribe(subscriberMock_2, topic_2)

        val message = Message("msg")
        messageBroker.publish(message, topic_1)

        verify(subscriberMock_1).onMessageReceived(meq(message))
        verifyZeroInteractions(subscriberMock_2)
      }

      "call the Subscriber even if subscribed to multiple topics" in new Test {

        val topic_1 = "TopicOne"
        val topic_2 = "TopicTwo"
        messageBroker.subscribe(subscriberMock_1, topic_1)
        messageBroker.subscribe(subscriberMock_1, topic_2)

        val message = Message("msg")
        messageBroker.publish(message, topic_1)

        verify(subscriberMock_1).onMessageReceived(meq(message))
      }
    }
  }

}
