package models.charactersheet

import java.util.UUID

import models.charactersheet.characteristics._
import models.charactersheet.skills._
import models.charactersheet.talents.Talent
import play.api.libs.json.{JsValue, Json}

import scala.collection.mutable.{Seq => MSeq}

case class CharacterStats(
  id: String = UUID.randomUUID().toString,
  characteristics: MSeq[Characteristic],
  skills: MSeq[Skill],
  talents: MSeq[Talent]
)

object CharacterStats {
  implicit val format = Json.format[CharacterStats]

  def factory: CharacterStatsFactory = new CharacterStatsFactory()

  // TODO: When building this object from DB, the 'wiring' is not set

  def apply(json: JsValue): CharacterStats = ???


}