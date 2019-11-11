package models.charactersheet.characteristics

import play.api.libs.json.Json

case class Characteristic(
  identifier: CharacteristicIdentifier,
  var initial: Int = 0,
  var advances: Int = 0,
  var otherBonuses: Int = 0
) {
  def current: Int = initial + advances + otherBonuses
}

object Characteristic {
  implicit val format = Json.format[Characteristic]
}
