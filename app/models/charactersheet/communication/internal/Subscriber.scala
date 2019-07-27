package models.charactersheet.communication.internal

import models.charactersheet.communication.internal.messages.Message

trait Subscriber {
  def onMessageReceived(message: Message[_]): Unit
}
