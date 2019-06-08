package models.charactersheet.talents

import models.charactersheet.skills.SkillDefinition

case class Talent(
  name: String,
  description: String,
  relatedSkill: SkillDefinition,
  timesTaken: Int,
  maxTimesToTake: Int
)
