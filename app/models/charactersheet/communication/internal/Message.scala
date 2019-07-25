package models.charactersheet.communication.internal

trait Message[T] {
  def content: T
}
