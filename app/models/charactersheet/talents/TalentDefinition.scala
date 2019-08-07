package models.charactersheet.talents

import models.charactersheet.skills.SkillDefinition
import play.api.libs.json.Json

case class TalentDefinition(
  name: String,
  description: String,
  relatedSkills: Seq[SkillDefinition],
  maxTimesToTake: Int
)

object TalentDefinition {
  implicit val format = Json.format[TalentDefinition]
}
