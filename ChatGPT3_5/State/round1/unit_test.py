import pytest
from Code import Light, OnState, OffState

def test_light_initial_state():
    light = Light()
    assert isinstance(light.state, OffState)  # Initially off

def test_turn_on_from_off():
    light = Light()
    light.turn_on()
    assert isinstance(light.state, OnState)  # After turning on, it should be on

def test_turn_off_from_on():
    light = Light()
    light.turn_on()  # Turn on first
    light.turn_off()
    assert isinstance(light.state, OffState)  # After turning off, it should be off

def test_turn_on_when_already_on():
    light = Light()
    light.turn_on()  # Turn on first
    light.turn_on()  # Try turning on again (no state change)
    assert isinstance(light.state, OnState)  # Should still be on

def test_turn_off_when_already_off():
    light = Light()
    light.turn_off()  # Turn off when already off (no state change)
    assert isinstance(light.state, OffState)  # Should still be off

# Run the tests with pytest for 100% branch coverage
