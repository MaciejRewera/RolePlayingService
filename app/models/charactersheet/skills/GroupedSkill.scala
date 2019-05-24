package models.charactersheet.skills

import models.charactersheet.characteristics.Characteristic

case class GroupedSkill(name: String, relatedCharacteristic: Characteristic)(group: String) extends Skill(name, relatedCharacteristic)
