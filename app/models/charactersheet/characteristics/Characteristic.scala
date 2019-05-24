package models.charactersheet.characteristics

import play.api.libs.json.Json

sealed abstract class Characteristic(
  initial: Int,
  advances: Int = 0,
  otherBonuses: Int = 0
) {
  def current: Int = initial + advances + otherBonuses
}

case class WeaponSkill(init: Int) extends Characteristic(init)
case class BallisticSkill(init: Int) extends Characteristic(init)
case class Strength(init: Int) extends Characteristic(init)
case class Toughness(init: Int) extends Characteristic(init)
case class Initiative(init: Int) extends Characteristic(init)
case class Agility(init: Int) extends Characteristic(init)
case class Dexterity(init: Int) extends Characteristic(init)
case class Intelligence(init: Int) extends Characteristic(init)
case class Willpower(init: Int) extends Characteristic(init)
case class Fellowship(init: Int) extends Characteristic(init)

object WeaponSkill {
  implicit val format = Json.format[WeaponSkill]
}
object BallisticSkill {
  implicit val format = Json.format[BallisticSkill]
}
object Strength {
  implicit val format = Json.format[Strength]
}
object Toughness {
  implicit val format = Json.format[Toughness]
}
object Initiative {
  implicit val format = Json.format[Initiative]
}
object Agility {
  implicit val format = Json.format[Agility]
}
object Dexterity {
  implicit val format = Json.format[Dexterity]
}
object Intelligence {
  implicit val format = Json.format[Intelligence]
}
object Willpower {
  implicit val format = Json.format[Willpower]
}
object Fellowship {
  implicit val format = Json.format[Fellowship]
}
