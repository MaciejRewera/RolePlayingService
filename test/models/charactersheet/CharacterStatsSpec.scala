package models.charactersheet

import models.charactersheet.CharacterStats.SkillsDefinitions
import models.charactersheet.characteristics._
import org.scalatest.{MustMatchers, WordSpec}

class CharacterStatsSpec extends WordSpec with MustMatchers {

  "CharacterStats ono apply()" should {

    "return CharacterStats containing all Characteristics" in {

      val characteristics = CharacterStats().characteristics

      val CharacteristicsAmount = 10
      characteristics.size must equal(CharacteristicsAmount)

      characteristics must contain(Characteristic(identifier = WeaponSkill))
      characteristics must contain(Characteristic(identifier = BallisticSkill))
      characteristics must contain(Characteristic(identifier = Strength))
      characteristics must contain(Characteristic(identifier = Toughness))
      characteristics must contain(Characteristic(identifier = Initiative))
      characteristics must contain(Characteristic(identifier = Agility))
      characteristics must contain(Characteristic(identifier = Dexterity))
      characteristics must contain(Characteristic(identifier = Intelligence))
      characteristics must contain(Characteristic(identifier = Willpower))
      characteristics must contain(Characteristic(identifier = Fellowship))
    }

    "return CharacterStats containing all basic Skills" in {

      val skills = CharacterStats().skills

      val BasicSkillsAmount = 25
      skills.size must equal(BasicSkillsAmount)
      SkillsDefinitions.basicSkillsDefinitions.foreach { basicSkillDefinition =>
        skills.map(_.definition) must contain(basicSkillDefinition)
      }
    }
  }

}
