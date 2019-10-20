package models.charactersheet

import models.charactersheet.characteristics.CharacteristicIdentifier._
import models.charactersheet.characteristics._
import models.charactersheet.skills.Skill
import models.charactersheet.skills.Skill.SkillsDefinitions

import scala.collection.mutable.{ArrayBuffer, Seq => MSeq}

class CharacterStatsFactory {

  def buildEmptyCharacterStats(): CharacterStats = {
    val weaponSkill = Characteristic(identifier = WeaponSkill)
    val ballisticSkill = Characteristic(identifier = BallisticSkill)
    val strength = Characteristic(identifier = Strength)
    val toughness = Characteristic(identifier = Toughness)
    val initiative = Characteristic(identifier = Initiative)
    val agility = Characteristic(identifier = Agility)
    val dexterity = Characteristic(identifier = Dexterity)
    val intelligence = Characteristic(identifier = Intelligence)
    val willPower = Characteristic(identifier = Willpower)
    val fellowship = Characteristic(identifier = Fellowship)

    val characteristics = MSeq(
      weaponSkill,
      ballisticSkill,
      strength,
      toughness,
      initiative,
      agility,
      dexterity,
      intelligence,
      willPower,
      fellowship
    )

    val skillsBuilder = new ArrayBuffer[Skill]
    SkillsDefinitions.basicSkillsDefinitions.foreach { skillDefinition =>
      skillsBuilder += Skill(definition = skillDefinition, allCharacteristics = characteristics)
    }

    CharacterStats(
      characteristics = characteristics,
      skills = skillsBuilder,
      talents = MSeq.empty
    )
  }

}
