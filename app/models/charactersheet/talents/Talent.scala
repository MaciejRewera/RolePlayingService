package models.charactersheet.talents

import models.charactersheet.skills.SkillDefinition
import play.api.libs.json.Json

// TODO: Encapsulate into TalentDefinition
case class Talent(
  name: String,
  description: String,
  relatedSkill: SkillDefinition,
  maxTimesToTake: Int,
  timesTaken: Int
)

object Talent {
  implicit val format = Json.format[Talent]
}