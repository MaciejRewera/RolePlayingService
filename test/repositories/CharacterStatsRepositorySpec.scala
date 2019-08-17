package repositories

import models.charactersheet.CharacterStats
import models.charactersheet.characteristics.Dexterity
import models.charactersheet.skills.{Basic, Grouped, SkillDefinition}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{BeforeAndAfterEach, MustMatchers, WordSpec}
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global

class CharacterStatsRepositorySpec extends WordSpec with MustMatchers with ScalaFutures with BeforeAndAfterEach {

  implicit private val patience = PatienceConfig(
    timeout = scaled(Span(10, Seconds)), interval = scaled(Span(150, Millis))
  )

  private val repository = new CharacterStatsRepository()

  override def beforeEach(): Unit = {
    super.beforeEach()
    repository.removeAll().futureValue
  }

  override def afterEach(): Unit = {
    super.beforeEach()
    repository.removeAll().futureValue
  }


  "CharacterStatsRepository" should {

    "insert CharacterStats to the DB" in {

      val statsToInsert = CharacterStats()
      repository.create(CharacterStats()).futureValue

      val artSkillDefinition = SkillDefinition(name = "Art", category = Set(Basic, Grouped), relatedCharacteristicIdentifier = Dexterity)
      repository.find(
        "skills[0].definition" -> Json.toJson(
          statsToInsert.skills.find(_.definition == artSkillDefinition)
        )
      )
    }

    "find all" in {

      val statsToInsert = CharacterStats()
      repository.create(statsToInsert).futureValue
      repository.create(statsToInsert).futureValue
      repository.create(statsToInsert).futureValue

      val result: Seq[CharacterStats] = repository.findAll().futureValue

      result.length must equal(3)
      result.foreach(elem => elem must equal(statsToInsert))
    }

    "remove all" in {

      repository.create(CharacterStats()).futureValue
      repository.create(CharacterStats()).futureValue
      repository.create(CharacterStats()).futureValue

      repository.removeAll().futureValue

      val result: Seq[CharacterStats] = repository.findAll().futureValue
      result must be(empty)
    }
  }


}
