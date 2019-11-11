package models.charactersheet.skills

import play.api.libs.json._

sealed trait SkillCategory

object SkillCategory {
  implicit val skillCategoryFormat: Format[SkillCategory] = new Format[SkillCategory] {
    override def writes(skillCategory: SkillCategory): JsValue = JsString(skillCategory.toString)

    override def reads(json: JsValue): JsResult[SkillCategory] = json match {
      case JsString("Basic")    => JsSuccess(Basic)
      case JsString("Advanced") => JsSuccess(Advanced)
      case JsString("Grouped")  => JsSuccess(Grouped)
    }
  }
}

case object Basic extends SkillCategory {
  override def toString: String = this.productPrefix
}

case object Advanced extends SkillCategory {
  override def toString: String = this.productPrefix
}

case object Grouped extends SkillCategory {
  override def toString: String = this.productPrefix
}
