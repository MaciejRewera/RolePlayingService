package models.charactersheet

import models.charactersheet.characteristics._
import models.charactersheet.skills._
import models.charactersheet.talents.Talent
import play.api.libs.json._

import java.util.UUID
import scala.collection.mutable.{Seq => MSeq}

case class CharacterStats(
  id: String = UUID.randomUUID().toString,
  characteristics: Seq[Characteristic] = Seq.empty,
  skills: MSeq[Skill] = MSeq.empty,
  talents: MSeq[Talent] = MSeq.empty
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
        skills <- (json \ "skills").validate[MSeq[SkillDTO]]
        talents <- (json \ "talents").validate[MSeq[Talent]]
      } yield
        CharacterStats(
          id = id,
          characteristics = characteristics,
          skills = buildSkills(skills, characteristics),
          talents = talents
        )

    private def buildSkills(skills: MSeq[SkillDTO], characteristics: MSeq[Characteristic]): MSeq[Skill] =
      skills.map { skill =>
        Skill(
          definition = skill.definition,
          specialisation = skill.specialisation,
          advances = skill.advances,
          otherBonuses = skill.otherBonuses,
          allCharacteristics = characteristics
        )
      }
  }

}
