package models.charactersheet.skills

import models.charactersheet.communication.internal.Subscriber
import models.charactersheet.communication.internal.messages.{CharacteristicUpdateMessageContent, Message, TalentUpdateMessageContent}
import play.api.libs.json.Json

case class Skill(
  definition: SkillDefinition,
  specialisation: Option[String],
  value: SkillValue
) extends Subscriber {

  private val TalentLevelBonusMultiplier = 10

  override def onMessageReceived(message: Message[_]): Unit = message.content match {
    case msgContent: CharacteristicUpdateMessageContent if isCharacteristicRelated(msgContent) =>
      this.value.characteristic = msgContent.newValue
    case msgContent: TalentUpdateMessageContent if isTalentRelated(msgContent) =>
      this.value.otherBonuses = msgContent.newTalentLevel * TalentLevelBonusMultiplier
    case _ => ()
  }

  private def isCharacteristicRelated(content: CharacteristicUpdateMessageContent): Boolean =
    content.characteristicIdentifier == this.definition.relatedCharacteristic

  private def isTalentRelated(content: TalentUpdateMessageContent): Boolean =
    content.relatedSkills.contains(this.definition)

}

object Skill {
  implicit val format = Json.format[Skill]
}
