package models.charactersheet.communication.internal.messages

import models.charactersheet.characteristics.CharacteristicIdentifier

case class CharacteristicUpdateMessageContent(
  characteristicIdentifier: CharacteristicIdentifier,
  newValue: Int
)
