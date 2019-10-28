package models.charactersheet

import java.util.UUID

import models.charactersheet.characteristics._
import models.charactersheet.skills._
import models.charactersheet.talents.Talent
import play.api.libs.json._

import scala.collection.mutable.{Seq => MSeq}

case class CharacterStats(
  id: String = UUID.randomUUID().toString,
  characteristics: MSeq[Characteristic],
  skills: MSeq[Skill],
  talents: MSeq[Talent]
)

object CharacterStats {

  def factory: CharacterStatsFactory = new CharacterStatsFactory()

  implicit val format: OFormat[CharacterStats] = new OFormat[CharacterStats] {
    override def writes(o: CharacterStats): JsObject = Json.obj(
      "id" -> o.id,
      "characteristics" -> Json.toJsFieldJsValueWrapper(o.characteristics),
      "skills" -> Json.toJsFieldJsValueWrapper(o.skills),
      "talents" -> Json.toJsFieldJsValueWrapper(o.talents)
    )

    override def reads(json: JsValue): JsResult[CharacterStats] =
      for {
        id <- (json \ "id").validate[String]
        characteristics <- (json \ "characteristics").validate[MSeq[Characteristic]]
        skills <- (json \ "skills").validate[MSeq[Skill]]
        talents <- (json \ "talents").validate[MSeq[Talent]]
      } yield
        CharacterStats(
          id = id,
          characteristics = characteristics,
          skills = buildSkills(skills, characteristics),
          talents = talents
        )

    private def buildSkills(skills: MSeq[Skill], characteristics: MSeq[Characteristic]): MSeq[Skill] =
      skills.map { skill =>
        Skill(
          definition = skill.definition,
          specialisation = skill.specialisation,
          allCharacteristics = characteristics
        )
      }
  }

}
