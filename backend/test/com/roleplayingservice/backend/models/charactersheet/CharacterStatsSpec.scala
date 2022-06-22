package com.roleplayingservice.backend.models.charactersheet

import com.roleplayingservice.backend.models.charactersheet.characteristics.Characteristic
import com.roleplayingservice.backend.models.charactersheet.characteristics.CharacteristicIdentifier.Fellowship
import com.roleplayingservice.backend.models.charactersheet.skills.Skill
import com.roleplayingservice.backend.models.charactersheet.skills.SkillDefinition.Gossip
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

      Json.fromJson[CharacterStats](exampleCharacterStatsJSON) mustBe JsSuccess(exampleCharacterStats)
    }
  }

}

object CharacterStatsSpec {

  val exampleCharacterStats = {
    val fellowship = Characteristic(identifier = Fellowship, initial = 31, advances = 7)
    CharacterStats(
      id = "RANDOM-ID-1-23",
      characteristics = MSeq(fellowship),
      skills = MSeq(
        Skill(
          definition = Gossip,
          specialisation = Some("TEST"),
          advances = 7,
          otherBonuses = 10,
          allCharacteristics = Seq(fellowship)
        )
      ),
      talents = MSeq.empty
    )
  }

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
