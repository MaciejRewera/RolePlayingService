package com.roleplayingservice.backend.models.charactersheet.skills

import com.roleplayingservice.backend.models.charactersheet.characteristics.Characteristic
import play.api.libs.json._

case class Skill(
  definition: SkillDefinition,
  specialisation: Option[String],
  var advances: Int,
  var otherBonuses: Int,
  private val relatedCharacteristic: Characteristic
) {

  def characteristic: Int = relatedCharacteristic.current
  def skillLevel: Int = characteristic + advances + otherBonuses
}

object Skill {

  implicit val format = new OFormat[Skill] {

    override def writes(o: Skill): JsObject = Json.toJsObject(skill2SkillDAO(o))

    private def skill2SkillDAO(skill: Skill): SkillDTO = SkillDTO(
      definition = skill.definition,
      specialisation = skill.specialisation,
      advances = skill.advances,
      otherBonuses = skill.otherBonuses
    )

    override def reads(json: JsValue): JsResult[Skill] = ???
  }

  def apply(
    definition: SkillDefinition,
    specialisation: Option[String] = None,
    advances: Int = 0,
    otherBonuses: Int = 0,
    allCharacteristics: Seq[Characteristic]
  ): Skill = {
    val relatedCharacteristic = allCharacteristics.find(_.identifier == definition.relatedCharacteristicIdentifier)
    new Skill(definition, specialisation, advances, otherBonuses, relatedCharacteristic.get)
  }

}
