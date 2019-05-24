package forms

import play.api.data.Forms._
import play.api.data.{Form, Forms}

case class InitialAttributes(
  weaponSkill: Int,
  ballisticSkill: Int,
  strength: Int,
  toughness: Int,
  initiative: Int,
  agility: Int,
  dexterity: Int,
  intelligence: Int,
  willpower: Int,
  fellowship: Int
)

object InitialAttributes {
  val weaponSkillKey = "weaponSkill"
  val ballisticSkillKey = "ballisticSkill"
  val strengthKey = "strength"
  val toughnessKey = "toughness"
  val initiativeKey = "initiative"
  val agilityKey = "agility"
  val dexterityKey = "dexterity"
  val intelligenceKey = "intelligence"
  val willpowerKey = "willpower"
  val fellowshipKey = "fellowship"

  val attributeValueOutOfRange = "Attribute must be positive"

  val mapping = Forms.mapping(
    weaponSkillKey -> number().verifying(attributeValueOutOfRange, _ >= 0),
    ballisticSkillKey -> number().verifying(attributeValueOutOfRange, _ >= 0),
    strengthKey -> number().verifying(attributeValueOutOfRange, _ >= 0),
    toughnessKey -> number().verifying(attributeValueOutOfRange, _ >= 0),
    initiativeKey -> number().verifying(attributeValueOutOfRange, _ >= 0),
    agilityKey -> number().verifying(attributeValueOutOfRange, _ >= 0),
    dexterityKey -> number().verifying(attributeValueOutOfRange, _ >= 0),
    intelligenceKey -> number().verifying(attributeValueOutOfRange, _ >= 0),
    willpowerKey -> number().verifying(attributeValueOutOfRange, _ >= 0),
    fellowshipKey -> number().verifying(attributeValueOutOfRange, _ >= 0)
  )(InitialAttributes.apply)(InitialAttributes.unapply)

  def form(): Form[InitialAttributes] = Form(mapping)
}