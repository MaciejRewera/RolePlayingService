package models.charactersheet.talents

import play.api.libs.json.Json

case class Talent(definition: TalentDefinition, var timesTaken: Int)

object Talent {
  implicit val format = Json.format[Talent]
}
