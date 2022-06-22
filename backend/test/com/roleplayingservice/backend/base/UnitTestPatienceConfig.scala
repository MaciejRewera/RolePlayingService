package com.roleplayingservice.backend.base

import org.scalatest.concurrent.{AbstractPatienceConfiguration, PatienceConfiguration}
import org.scalatest.time.{Millis, Seconds, Span}

trait UnitTestPatienceConfig extends AbstractPatienceConfiguration { this: PatienceConfiguration =>
  private val defaultPatienceConfig: PatienceConfig =
    PatienceConfig(
      timeout = scaled(Span(5, Seconds)),
      interval = scaled(Span(50, Millis))
    )

  implicit override val patienceConfig: PatienceConfig = defaultPatienceConfig
}
