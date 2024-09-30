import pytest
from Code import Light, OnState, OffState

def test_light_initial_state():
  light = Light()
  assert isinstance(light.state, OffState)  # Verify initial state is Off

def test_turn_on_from_off():
  light = Light()
  light.turn_on()
  assert isinstance(light.state, OnState)  # Verify state changes to On

def test_turn_on_from_on():
  light = Light()
  light.turn_on()  # Turn on initially
  light.turn_on()
  assert isinstance(light.state, OnState)  # Verify On state remains

def test_turn_off_from_off():
  light = Light()
  light.turn_off()
  assert isinstance(light.state, OffState)  # Verify Off state remains

def test_turn_off_from_on():
  light = Light()
  light.turn_on()  # Turn on initially
  light.turn_off()
  assert isinstance(light.state, OffState)  # Verify state changes to Off
