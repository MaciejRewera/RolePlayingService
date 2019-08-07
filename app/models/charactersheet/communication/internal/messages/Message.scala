package models.charactersheet.communication.internal.messages

case class Message[T](content: T) {
  def toOption: Option[T] = Option(content)
}
