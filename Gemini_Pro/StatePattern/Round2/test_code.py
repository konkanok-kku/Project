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

    def test_increase_brightness_on(self):
        light = Light()
        light.turn_on()
        light.increase_brightness()
        assert light.get_brightness() == 10

    def test_increase_brightness_off(self):
        light = Light()
        light.increase_brightness()
        assert light.get_brightness() == 0

    def test_decrease_brightness_on(self):
        light = Light()
        light.turn_on()
        light.increase_brightness()
        light.increase_brightness()
        light.decrease_brightness()
        assert light.get_brightness() == 10

    def test_decrease_brightness_off(self):
        light = Light()
        light.decrease_brightness()
        assert light.get_brightness() == 0

    def test_increase_brightness_max(self):
        light = Light()
        light.turn_on()
        for _ in range(11):
            light.increase_brightness()
        assert light.get_brightness() == 100

    def test_decrease_brightness_min(self):
        light = Light()
        light.turn_on()
        light.increase_brightness()
        for _ in range(2):
            light.decrease_brightness()
        assert light.get_brightness() == 0