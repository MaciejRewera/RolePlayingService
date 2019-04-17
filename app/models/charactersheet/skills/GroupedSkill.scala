package models.charactersheet.skills

import models.charactersheet.characteristics.Attribute

case class GroupedSkill(name: String, relatedAttribute: Attribute)(group: String) extends Skill(name, relatedAttribute)
