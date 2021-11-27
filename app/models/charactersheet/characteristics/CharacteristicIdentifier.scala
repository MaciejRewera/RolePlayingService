package models.charactersheet.characteristics

import play.api.libs.json._

sealed class CharacteristicIdentifier(val fullName: String, val shortName: String)

object CharacteristicIdentifier {

  // TODO: Could store shortName only as a String
  implicit val format = new Format[CharacteristicIdentifier] {
    override def writes(o: CharacteristicIdentifier): JsValue =
      Json.obj("fullName" -> o.fullName, "shortName" -> o.shortName)

    override def reads(json: JsValue): JsResult[CharacteristicIdentifier] =
      for {
        fullName <- (json \ "fullName").validate[String]
        shortName <- (json \ "shortName").validate[String]
      } yield
        (fullName, shortName) match {
          case ("Weapon Skill", "WS")    => WeaponSkill
          case ("Ballistic Skill", "BS") => BallisticSkill
          case ("Strength", "S")         => Strength
          case ("Toughness", "T")        => Toughness
          case ("Initiative", "I")       => Initiative
          case ("Agility", "Ag")         => Agility
          case ("Dexterity", "Dex")      => Dexterity
          case ("Intelligence", "Int")   => Intelligence
          case ("Willpower", "WP")       => Willpower
          case ("Fellowship", "Fel")     => Fellowship
          case _                         => new CharacteristicIdentifier("Unknown", "Unknown")
        }
  }

  case object WeaponSkill extends CharacteristicIdentifier("Weapon Skill", "WS")
  case object BallisticSkill extends CharacteristicIdentifier("Ballistic Skill", "BS")
  case object Strength extends CharacteristicIdentifier("Strength", "S")
  case object Toughness extends CharacteristicIdentifier("Toughness", "T")
  case object Initiative extends CharacteristicIdentifier("Initiative", "I")
  case object Agility extends CharacteristicIdentifier("Agility", "Ag")
  case object Dexterity extends CharacteristicIdentifier("Dexterity", "Dex")
  case object Intelligence extends CharacteristicIdentifier("Intelligence", "Int")
  case object Willpower extends CharacteristicIdentifier("Willpower", "WP")
  case object Fellowship extends CharacteristicIdentifier("Fellowship", "Fel")
}
