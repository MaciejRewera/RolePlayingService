package models.charactersheet.communication.internal.messages

import models.charactersheet.skills.SkillDefinition

case class TalentUpdateMessageContent(relatedSkills: Seq[SkillDefinition], newTalentLevel: Int)
