package models.charactersheet.skills

import play.api.libs.json.Json

case class SkillValue(
  characteristic: Int,
  advances: Int = 0,
  otherBonuses: Int = 0
) {
  def skillLevel: Int = characteristic + advances + otherBonuses
}

object SkillValue {
  implicit val format = Json.format[SkillValue]
}