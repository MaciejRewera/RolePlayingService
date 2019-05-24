package models.charactersheet.characteristics

import forms.InitialAttributes
import play.api.libs.json.Json

case class Characteristics(
  weaponSkill: WeaponSkill,
  ballisticSkill: BallisticSkill,
  strength: Strength,
  toughness: Toughness,
  initiative: Initiative,
  agility: Agility,
  dexterity: Dexterity,
  intelligence: Intelligence,
  willpower: Willpower,
  fellowship: Fellowship
)

object Characteristics {
  implicit val format = Json.format[Characteristics]

  def apply(initialAttributes: InitialAttributes): Characteristics = Characteristics(
    weaponSkill = WeaponSkill(initialAttributes.weaponSkill),
    ballisticSkill = BallisticSkill(initialAttributes.ballisticSkill),
    strength = Strength(initialAttributes.strength),
    toughness = Toughness(initialAttributes.toughness),
    initiative = Initiative(initialAttributes.initiative),
    agility = Agility(initialAttributes.agility),
    dexterity = Dexterity(initialAttributes.dexterity),
    intelligence = Intelligence(initialAttributes.intelligence),
    willpower = Willpower(initialAttributes.willpower),
    fellowship = Fellowship(initialAttributes.fellowship)
  )
}
