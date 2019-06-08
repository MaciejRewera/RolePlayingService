package models.charactersheet

import models.charactersheet.characteristics.Characteristic
import models.charactersheet.skills.Skill
import models.charactersheet.talents.Talent

case class CharacterSheet(
  characteristics: Seq[Characteristic],
  skills: Seq[Skill],
  talents: Seq[Talent]
)
