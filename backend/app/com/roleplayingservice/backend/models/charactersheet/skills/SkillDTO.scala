package com.roleplayingservice.backend.models.charactersheet.skills

import play.api.libs.json.Json

case class SkillDTO(
  definition: SkillDefinition,
  specialisation: Option[String],
  advances: Int,
  otherBonuses: Int
)

object SkillDTO {
  implicit val format = Json.format[SkillDTO]
}
