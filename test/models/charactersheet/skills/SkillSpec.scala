package models.charactersheet.skills

import models.charactersheet.characteristics.{BallisticSkill, Fellowship, WeaponSkill}
import models.charactersheet.communication.internal.messages.{CharacteristicUpdateMessageContent, Message, TalentUpdateMessageContent}
import org.mockito.Mockito
import org.mockito.Mockito.verifyZeroInteractions
import org.scalatest.{MustMatchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar

class SkillSpec extends WordSpec with MustMatchers with MockitoSugar {

  private trait Test {
    val testSkillDefinition = SkillDefinition(
      name = "Test Skill",
      description = "Only for test purposes",
      category = Set(Basic),
      relatedCharacteristic = WeaponSkill
    )
    val testSkillDefinition_2 = SkillDefinition(
      name = "Test Skill 2",
      description = "Only for test purposes",
      category = Set(Advanced),
      relatedCharacteristic = Fellowship
    )
    val skillValueSpy = Mockito.spy(SkillValue(characteristic = 31, advances = 7, otherBonuses = 0))
    val skill = Skill(
      definition = testSkillDefinition,
      specialisation = None,
      value = skillValueSpy
    )
  }

  "Skill on onMessageReceived" when {

    "provided with message in incorrect format" should {
      "leave SkillValue unchanged" in new Test {

        skill.onMessageReceived(Message("Non-related message"))

        verifyZeroInteractions(skillValueSpy)
      }
    }

    "provided with CharacteristicUpdate message in correct format but related to different Characteristic" should {
      "leave SkillValue unchanged" in new Test {

        skill.onMessageReceived(
          Message(CharacteristicUpdateMessageContent(characteristicIdentifier = BallisticSkill, newValue = 13))
        )

        verifyZeroInteractions(skillValueSpy)
      }
    }

    "provided with CharacteristicUpdate message in correct format and related to the same Characteristic" should {
      "change SkillValue characteristic field to new value" in new Test {

        val newCharacteristicValue = 32
        skill.onMessageReceived(Message(CharacteristicUpdateMessageContent(
          characteristicIdentifier = WeaponSkill,
          newValue = newCharacteristicValue
        )))

        skill.value.characteristic must equal(newCharacteristicValue)
      }
    }

    "provided with TalentUpdate message in correct format but Talent is not related to any skill" should {
      "leave SkillValue unchanged" in new Test {

        skill.onMessageReceived(
          Message(TalentUpdateMessageContent(relatedSkills = Seq.empty, newTalentLevel = 1))
        )

        verifyZeroInteractions(skillValueSpy)
      }
    }


    "provided with TalentUpdate message in correct format but Talent is not related" should {
      "leave SkillValue unchanged" in new Test {

        skill.onMessageReceived(
          Message(TalentUpdateMessageContent(relatedSkills = Seq(testSkillDefinition_2), newTalentLevel = 2))
        )

        verifyZeroInteractions(skillValueSpy)
      }
    }

    "provided with TalentUpdate message in correct format and Talent is related" should {
      "change SkillValue otherBonuses field to new value x10" in new Test {

        val newTalentLevel = 3
        val newOtherBonusesValue = 10 * newTalentLevel
        skill.onMessageReceived(Message(
          TalentUpdateMessageContent(
            relatedSkills = Seq(testSkillDefinition_2, testSkillDefinition),
            newTalentLevel = newTalentLevel)
        ))

        skill.value.otherBonuses must equal(newOtherBonusesValue)
      }
    }

  }

}
