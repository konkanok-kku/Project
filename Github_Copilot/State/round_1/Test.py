import pytest
from Code import Light, OnState, OffState

def test_initial_state():
    light = Light()
    assert isinstance(light.get_state(), OffState)

def test_turn_on_from_off_state():
    light = Light()
    light.turn_on()
    assert isinstance(light.get_state(), OnState)

def test_turn_off_from_on_state():
    light = Light()
    light.turn_on()
    light.turn_off()
    assert isinstance(light.get_state(), OffState)

def test_turn_on_from_on_state():
    light = Light()
    light.turn_on()
    light.turn_on()
    assert isinstance(light.get_state(), OnState)

def test_turn_off_from_off_state():
    light = Light()
    light.turn_off()
    assert isinstance(light.get_state(), OffState)

if __name__ == "__main__":
    pytest.main()
