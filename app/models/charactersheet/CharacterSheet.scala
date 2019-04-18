package models.charactersheet

import models.charactersheet.characteristics.Characteristics
import models.charactersheet.skills.{BasicSkill, GroupedSkill}
import models.charactersheet.talents.Talent

case class CharacterSheet(
  characteristics: Characteristics,
  basicSkills: Seq[BasicSkill],
  groupedSkills: Seq[GroupedSkill],
  talents: Seq[Talent]
)
