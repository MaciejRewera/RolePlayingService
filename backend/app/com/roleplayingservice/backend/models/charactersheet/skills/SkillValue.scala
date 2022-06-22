package com.roleplayingservice.backend.models.charactersheet.skills

import com.roleplayingservice.backend.models.charactersheet.characteristics.Characteristic
import play.api.libs.json.Json

case class SkillValue(
  private val relatedCharacteristic: Characteristic,
  var advances: Int = 0,
  var otherBonuses: Int = 0
) {
  def characteristic: Int = relatedCharacteristic.current
  def skillLevel: Int = characteristic + advances + otherBonuses
}

object SkillValue {
  implicit val format = Json.format[SkillValue]
}
