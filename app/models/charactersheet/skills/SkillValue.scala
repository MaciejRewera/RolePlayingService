package models.charactersheet.skills

import play.api.libs.json.Json

case class SkillValue(
  var characteristic: Int,
  var advances: Int = 0,
  var otherBonuses: Int = 0
) {
  def skillLevel: Int = characteristic + advances + otherBonuses
}

object SkillValue {
  implicit val format = Json.format[SkillValue]
}