package controllers

import base.CustomPatienceConfig
import models.charactersheet.CharacterStats
import org.mockito.ArgumentMatchers.{any, eq => eqTo}
import org.mockito.Mockito
import org.mockito.Mockito.{verify, when}
import org.scalatest.MustMatchers.convertToAnyMustWrapper
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import repositories.CharacterStatsRepository

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class BackendControllerSpec
    extends WordSpec with MockitoSugar with ScalaFutures with CustomPatienceConfig with BeforeAndAfterEach {

  private val characterStatsRepository = mock[CharacterStatsRepository]
  private val backendController = new BackendController(stubControllerComponents(), characterStatsRepository)

  private val getRequest = FakeRequest("GET", "")

  override def beforeEach(): Unit = {
    super.beforeEach()
    Mockito.reset(characterStatsRepository)
  }

  "BackendController on getCharacterSheet" should {
    "call CharacterStatsRepository" in {
      when(characterStatsRepository.findById(any())(any())) thenReturn Future.successful(None)
      val sheetId = UUID.randomUUID()

      backendController.getCharacterSheet(sheetId.toString)(getRequest).futureValue

      verify(characterStatsRepository).findById(eqTo(sheetId))(any())
    }
  }

  "BackendController on getCharacterSheet" when {
    val sheetId = UUID.randomUUID().toString

    "CharacterStatsRepository returns failed Future" should {
      "throw an exception" in {
        when(characterStatsRepository.findById(any())(any())) thenReturn Future.failed(new Exception("Test Exception"))

        val exc = backendController.getCharacterSheet(sheetId)(getRequest).failed.futureValue

        exc.getMessage mustBe "Test Exception"
      }
    }

    "CharacterStatsRepository returns successful Future containing single element" should {
      val characterStats = CharacterStats()

      "return Ok status" in {
        when(characterStatsRepository.findById(any())(any())) thenReturn Future.successful(Some(characterStats))

        val result = backendController.getCharacterSheet(sheetId)(getRequest).futureValue

        result.header.status mustBe OK
      }

      "return this element in body" in {
        when(characterStatsRepository.findById(any())(any())) thenReturn Future.successful(Some(characterStats))

        val result = backendController.getCharacterSheet(sheetId)(getRequest)

        contentAsJson(result) mustBe Json.toJson(characterStats)
      }
    }

    "CharacterStatsRepository returns successful Future containing empty Option" should {

      "return Not Found status" in {
        when(characterStatsRepository.findById(any())(any())) thenReturn Future.successful(None)

        val result = backendController.getCharacterSheet(sheetId)(getRequest).futureValue

        result.header.status mustBe NOT_FOUND
      }

      "return empty body" in {
        when(characterStatsRepository.findById(any())(any())) thenReturn Future.successful(None)

        val result = backendController.getCharacterSheet(sheetId)(getRequest)

        contentAsString(result) mustBe Matchers.empty
      }
    }
  }
}
