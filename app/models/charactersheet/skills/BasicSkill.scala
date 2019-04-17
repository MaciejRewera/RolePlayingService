package models.charactersheet.skills

import models.charactersheet.characteristics.Attribute

case class BasicSkill(name: String, relatedAttribute: Attribute) extends Skill(name, relatedAttribute)
