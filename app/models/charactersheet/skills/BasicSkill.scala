package models.charactersheet.skills

import models.charactersheet.characteristics.Characteristic

case class BasicSkill(name: String, relatedCharacteristic: Characteristic) extends Skill(name, relatedCharacteristic)
