package models.charactersheet.communication.internal

class StringMessage(value: String) extends Message[String] {
  override def content: String = value
}

object StringMessage {
  def apply(content: String): StringMessage = new StringMessage(content)
}
