package models.charactersheet.talents

import models.charactersheet.skills.Skill

case class Talent(
  name: String,
  description: String,
  relatedSkill: Skill,
  timesTaken: Int,
  maxTimesToTake: Int
)
