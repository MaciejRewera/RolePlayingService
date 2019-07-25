package models.charactersheet.communication.internal

trait Subscriber {
  def onMessageReceived[T](message: Message[T]): Unit
}
