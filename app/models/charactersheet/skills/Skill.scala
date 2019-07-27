package models.charactersheet.skills

import models.charactersheet.communication.internal.Subscriber
import models.charactersheet.communication.internal.messages.{CharacteristicUpdateMessageContent, Message}
import play.api.libs.json.Json

case class Skill(
  definition: SkillDefinition,
  specialisation: Option[String],
  value: SkillValue
) extends Subscriber {

  override def onMessageReceived(message: Message[_]): Unit = message.content match {
    case msgContent: CharacteristicUpdateMessageContent if isCharacteristicRelated(msgContent) =>
      this.value.characteristic = msgContent.newValue
    case _ => ()
  }

  private def isCharacteristicRelated(content: CharacteristicUpdateMessageContent): Boolean =
    content.characteristicIdentifier == this.definition.relatedCharacteristic
}

object Skill {
  implicit val format = Json.format[Skill]
}
