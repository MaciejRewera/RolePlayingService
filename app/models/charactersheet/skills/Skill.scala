package models.charactersheet.skills

import models.charactersheet.characteristics.Characteristic

abstract class Skill(
  name: String,
  relatedCharacteristic: Characteristic,
  advances: Int = 0,
  otherBonuses: Int = 0
) {
  def skillValue: Int = relatedCharacteristic.current + advances + otherBonuses
}
