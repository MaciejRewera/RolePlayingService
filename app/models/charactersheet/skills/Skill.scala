package models.charactersheet.skills

import play.api.libs.json.Json

case class Skill(
  definition: SkillDefinition,
  specialisation: Option[String],
  value: SkillValue
)

object Skill {
  implicit val format = Json.format[Skill]
}
