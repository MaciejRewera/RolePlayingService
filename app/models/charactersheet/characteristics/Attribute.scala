package models.charactersheet.characteristics

sealed abstract class Attribute(
  initial: Int,
  advances: Int = 0,
  otherBonuses: Int = 0
) {
  def current: Int = initial + advances + otherBonuses
}

case class WeaponSkill(init: Int) extends Attribute(init)
case class BallisticSkill(init: Int) extends Attribute(init)
case class Strength(init: Int) extends Attribute(init)
case class Toughness(init: Int) extends Attribute(init)
case class Initiative(init: Int) extends Attribute(init)
case class Agility(init: Int) extends Attribute(init)
case class Dexterity(init: Int) extends Attribute(init)
case class Intelligence(init: Int) extends Attribute(init)
case class Willpower(init: Int) extends Attribute(init)
case class Fellowship(init: Int) extends Attribute(init)