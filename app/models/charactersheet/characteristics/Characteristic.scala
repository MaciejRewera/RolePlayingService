package models.charactersheet.characteristics

import play.api.libs.json.Json

case class Characteristic(
  identifier: CharacteristicIdentifier,
  initial: Int,
  advances: Int = 0,
  otherBonuses: Int = 0
) {
  def current: Int = initial + advances + otherBonuses
}

object Characteristic {
  implicit val format = Json.format[Characteristic]
}