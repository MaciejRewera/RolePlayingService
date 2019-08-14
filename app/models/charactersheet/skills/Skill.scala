package models.charactersheet.skills

import models.charactersheet.characteristics.Characteristic
import play.api.libs.json.Json

case class Skill(
  definition: SkillDefinition,
  specialisation: Option[String],
  private val relatedCharacteristic: Characteristic
) {
  var advances: Int = 0
  var otherBonuses: Int = 0

  def characteristic: Int = relatedCharacteristic.current

  def skillLevel: Int = characteristic + advances + otherBonuses
}

object Skill {
  implicit val format = Json.format[Skill]

  def apply(
    definition: SkillDefinition,
    specialisation: Option[String] = None,
    allCharacteristics: Seq[Characteristic]
  ): Skill = {
    val relatedCharacteristic = allCharacteristics.find(_.identifier == definition.relatedCharacteristicIdentifier)
    new Skill(definition, specialisation, relatedCharacteristic.get)
  }

}
