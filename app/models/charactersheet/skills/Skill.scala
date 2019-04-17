package models.charactersheet.skills

import models.charactersheet.characteristics.Attribute

abstract class Skill(
  name: String,
  relatedAttribute: Attribute,
  advances: Int = 0,
  otherBonuses: Int = 0
) {
  def skillValue: Int = relatedAttribute.current + advances + otherBonuses
}
