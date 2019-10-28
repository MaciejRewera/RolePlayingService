package models.charactersheet.skills

import models.charactersheet.characteristics.Characteristic
import models.charactersheet.characteristics.CharacteristicIdentifier._
import play.api.libs.json.Json

case class Skill(
  definition: SkillDefinition,
  specialisation: Option[String],
  private val relatedCharacteristic: Characteristic
) {
  var advances: Int = 0
  var otherBonuses: Int = 0

  def characteristic: Int = relatedCharacteristic.current

  def skillLevel: Int = characteristic + advances + otherBonuses
}

object Skill {
  implicit val format = Json.format[Skill]

  def apply(
    definition: SkillDefinition,
    specialisation: Option[String] = None,
    allCharacteristics: Seq[Characteristic]
  ): Skill = {
    val relatedCharacteristic = allCharacteristics.find(_.identifier == definition.relatedCharacteristicIdentifier)
    new Skill(definition, specialisation, relatedCharacteristic.get)
  }

  object SkillsDefinitions {

    val Art = SkillDefinition(name = "Art", category = Set(Basic, Grouped), relatedCharacteristicIdentifier = Dexterity)
    val Athletics =
      SkillDefinition(name = "Athletics", category = Set(Basic), relatedCharacteristicIdentifier = Agility)
    val Bribery = SkillDefinition(name = "Bribery", category = Set(Basic), relatedCharacteristicIdentifier = Fellowship)
    val Charm = SkillDefinition(name = "Charm", category = Set(Basic), relatedCharacteristicIdentifier = Fellowship)
    val CharmAnimal =
      SkillDefinition(name = "CharmAnimal", category = Set(Basic), relatedCharacteristicIdentifier = Willpower)
    val Climb = SkillDefinition(name = "Climb", category = Set(Basic), relatedCharacteristicIdentifier = Strength)
    val ConsumeAlcohol =
      SkillDefinition(name = "ConsumeAlcohol", category = Set(Basic), relatedCharacteristicIdentifier = Toughness)
    val Cool = SkillDefinition(name = "Cool", category = Set(Basic), relatedCharacteristicIdentifier = Willpower)
    val Dodge = SkillDefinition(name = "Dodge", category = Set(Basic), relatedCharacteristicIdentifier = Agility)
    val Drive = SkillDefinition(name = "Drive", category = Set(Basic), relatedCharacteristicIdentifier = Agility)
    val Endurance =
      SkillDefinition(name = "Endurance", category = Set(Basic), relatedCharacteristicIdentifier = Toughness)
    val Entertain =
      SkillDefinition(name = "Entertain", category = Set(Basic, Grouped), relatedCharacteristicIdentifier = Fellowship)
    val Gamble = SkillDefinition(name = "Gamble", category = Set(Basic), relatedCharacteristicIdentifier = Intelligence)
    val Gossip = SkillDefinition(name = "Gossip", category = Set(Basic), relatedCharacteristicIdentifier = Fellowship)
    val Haggle = SkillDefinition(name = "Haggle", category = Set(Basic), relatedCharacteristicIdentifier = Fellowship)
    val Intimidate =
      SkillDefinition(name = "Intimidate", category = Set(Basic), relatedCharacteristicIdentifier = Strength)
    val Intuition =
      SkillDefinition(name = "Intuition", category = Set(Basic), relatedCharacteristicIdentifier = Initiative)
    val Leadership =
      SkillDefinition(name = "Leadership", category = Set(Basic), relatedCharacteristicIdentifier = Fellowship)
    val Melee =
      SkillDefinition(name = "Melee", category = Set(Basic, Grouped), relatedCharacteristicIdentifier = WeaponSkill)
    val Navigation =
      SkillDefinition(name = "Navigation", category = Set(Basic), relatedCharacteristicIdentifier = Initiative)
    val OutdoorSurvival =
      SkillDefinition(name = "OutdoorSurvival", category = Set(Basic), relatedCharacteristicIdentifier = Intelligence)
    val Perception =
      SkillDefinition(name = "Perception", category = Set(Basic), relatedCharacteristicIdentifier = Initiative)
    val Ride = SkillDefinition(name = "Ride", category = Set(Basic, Grouped), relatedCharacteristicIdentifier = Agility)
    val Row = SkillDefinition(name = "Row", category = Set(Basic), relatedCharacteristicIdentifier = Strength)
    val Stealth = SkillDefinition(name = "Stealth", category = Set(Basic), relatedCharacteristicIdentifier = Agility)

    val basicSkillsDefinitions: Set[SkillDefinition] = Set(
      Art,
      Athletics,
      Bribery,
      Charm,
      CharmAnimal,
      Climb,
      ConsumeAlcohol,
      Cool,
      Dodge,
      Drive,
      Endurance,
      Entertain,
      Gamble,
      Gossip,
      Haggle,
      Intimidate,
      Intuition,
      Leadership,
      Melee,
      Navigation,
      OutdoorSurvival,
      Perception,
      Ride,
      Row,
      Stealth
    )
  }

}
