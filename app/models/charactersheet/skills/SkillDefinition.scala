package models.charactersheet.skills

import models.charactersheet.characteristics.CharacteristicIdentifier
import play.api.libs.json._

case class SkillDefinition(
  name: String,
  description: String = "",
  category: Set[SkillCategory],
  possibleSpecialisations: Set[String] = Set.empty,
  relatedCharacteristic: CharacteristicIdentifier
)

object SkillDefinition {

  implicit private val skillCategorySetWrites: Writes[Set[SkillCategory]] =
    Writes.set(SkillCategory.skillCategoryFormat)

  implicit val format = new Format[SkillDefinition] {
    override def writes(o: SkillDefinition): JsValue = Json.obj(
      "name" -> o.name,
      "description" -> o.description,
      "category" -> Json.toJsFieldJsValueWrapper(o.category),
      "possibleSpecialisations" -> Json.toJsFieldJsValueWrapper(o.possibleSpecialisations),
      "relatedCharacteristic" -> o.relatedCharacteristic
    )

    override def reads(json: JsValue): JsResult[SkillDefinition] = for {
      name <- (json \ "name").validate[String]
      description <- (json \ "description").validate[String]
      category <- (json \ "category").validate[Set[SkillCategory]]
      possibleSpecialisations <- (json \ "possibleSpecialisations").validate[Set[String]]
      relatedCharacteristic <- (json \ "relatedCharacteristic").validate[CharacteristicIdentifier]
    } yield SkillDefinition(name, description, category, possibleSpecialisations, relatedCharacteristic)
  }
}