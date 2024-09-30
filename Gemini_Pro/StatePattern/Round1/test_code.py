import pytest
from Code import Light, OnState, OffState

class TestLight:

    def test_light_starts_off(self):
        light = Light()
        assert isinstance(light._state, OffState)

    def test_turn_on_from_off(self):
        light = Light()
        light.turn_on()
        assert isinstance(light._state, OnState)

    def test_turn_on_from_on(self):
        light = Light()
        light.turn_on()
        light.turn_on()
        assert isinstance(light._state, OnState)

    def test_turn_off_from_on(self):
        light = Light()
        light.turn_on()
        light.turn_off()
        assert isinstance(light._state, OffState)

    def test_turn_off_from_off(self):
        light = Light()
        light.turn_off()
        assert isinstance(light._state, OffState)