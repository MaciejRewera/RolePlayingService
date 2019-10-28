package models.charactersheet

import models.charactersheet.characteristics.Characteristic
import models.charactersheet.characteristics.CharacteristicIdentifier._
import models.charactersheet.skills.Skill
import models.charactersheet.skills.SkillDefinition.Gossip
import org.scalatest.{MustMatchers, WordSpec}
import play.api.libs.json.{JsSuccess, JsValue, Json}

import scala.collection.mutable.{Seq => MSeq}

class CharacterStatsSpec extends WordSpec with MustMatchers {

  import CharacterStatsSpec._

  "CharacterStats JSON writes" should {

    "convert CharacterStats into JSON" in {

      Json.toJson(exampleCharacterStats) mustBe exampleCharacterStatsJSON
    }
  }

  "CharacterStats JSON reads" should {

    "convert simple CharacterStats from JSON into case class" in {

      Json.fromJson(exampleCharacterStatsJSON) mustBe JsSuccess(exampleCharacterStats)
    }
  }

}

object CharacterStatsSpec {

  val exampleCharacterStats = CharacterStats(
    id = "RANDOM-ID-1-23",
    characteristics = MSeq(Characteristic(identifier = Fellowship, initial = 31, advances = 7)),
    skills = MSeq(
      Skill(
        definition = Gossip,
        specialisation = Some("TEST"),
        advances = 7,
        otherBonuses = 10,
        allCharacteristics = Seq(Characteristic(identifier = Fellowship))
      )
    ),
    talents = MSeq.empty
  )

  val exampleCharacterStatsJSON: JsValue = Json.obj(
    "id" -> "RANDOM-ID-1-23",
    "characteristics" -> Json.arr(
      Json.obj(
        "identifier" -> Json.obj("fullName" -> "Fellowship", "shortName" -> "Fel"),
        "initial" -> 31,
        "advances" -> 7,
        "otherBonuses" -> 0
      )
    ),
    "skills" -> Json.arr(
      Json.obj(
        "definition" -> Json.obj(
          "name" -> "Gossip",
          "description" -> "",
          "category" -> Json.arr("Basic"),
          "possibleSpecialisations" -> Json.arr(),
          "relatedCharacteristic" -> Json.obj("fullName" -> "Fellowship", "shortName" -> "Fel")
        ),
        "specialisation" -> "TEST",
        "advances" -> 7,
        "otherBonuses" -> 10
      )
    ),
    "talents" -> Json.arr()
  )

}
