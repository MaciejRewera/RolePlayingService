package models.charactersheet.skills

import models.charactersheet.characteristics.{BallisticSkill, WeaponSkill}
import models.charactersheet.communication.internal.messages.{CharacteristicUpdateMessageContent, Message}
import org.mockito.Mockito
import org.mockito.Mockito.verifyZeroInteractions
import org.scalatest.{MustMatchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar

class SkillSpec extends WordSpec with MustMatchers with MockitoSugar {

  private trait Test {
    val skillValueSpy = Mockito.spy(SkillValue(characteristic = 31, advances = 7, otherBonuses = 3))
    val skill = Skill(
      definition = SkillDefinition(
        name = "TestSkill",
        description = "Only for test purposes",
        category = Set(Basic),
        relatedCharacteristic = WeaponSkill
      ),
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

    "provided with message in correct format but related to different Characteristic" should {
      "leave SkillValue unchanged" in new Test {

        skill.onMessageReceived(
          Message(CharacteristicUpdateMessageContent(characteristicIdentifier = BallisticSkill, newValue = 13))
        )

        verifyZeroInteractions(skillValueSpy)
      }
    }

    "provided with message in correct format and related to the same Characteristic" should {
      "change SkillValue characteristic field to new value" in new Test {

        val newCharacteristicValue = 32
        skill.onMessageReceived(Message(CharacteristicUpdateMessageContent(
          characteristicIdentifier = WeaponSkill,
          newValue = newCharacteristicValue
        )))

        skill.value.characteristic must equal(newCharacteristicValue)
      }
    }

  }

}
