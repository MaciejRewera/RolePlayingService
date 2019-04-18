package models.charactersheet.characteristics

import forms.Attributes

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

  def apply(initialAttributes: Attributes): Characteristics = Characteristics(
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
