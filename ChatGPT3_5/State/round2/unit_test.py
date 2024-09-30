import pytest
from Code import Light, OnState, OffState

# Test that the light starts off by default
def test_light_initial_state():
    light = Light()
    assert isinstance(light.state, OffState)  # The initial state is Off

# Test turning the light on from the off state
def test_turn_on_from_off():
    light = Light()
    light.turn_on()  # Turn the light on
    assert isinstance(light.state, OnState)  # It should now be in the On state

# Test turning the light off from the on state
def test_turn_off_from_on():
    light = Light()
    light.turn_on()  # Turn the light on first
    light.turn_off()  # Now, turn it off
    assert isinstance(light.state, OffState)  # It should now be in the Off state

# Test turning the light on when it's already on
def test_turn_on_when_already_on():
    light = Light()
    light.turn_on()  # Turn the light on
    light.turn_on()  # Try to turn it on again
    assert isinstance(light.state, OnState)  # It should remain in the On state

# Test turning the light off when it's already off
def test_turn_off_when_already_off():
    light = Light()
    light.turn_off()  # Turn it off, though it's already off
    assert isinstance(light.state, OffState)  # It should remain in the Off state

# Test state transitions directly (extra validation for transitions)
def test_state_transitions():
    light = Light()

    # Test from Off to On
    assert isinstance(light.state, OffState)
    light.turn_on()
    assert isinstance(light.state, OnState)

    # Test from On to Off
    light.turn_off()
    assert isinstance(light.state, OffState)

# Run the tests using Pytest for 100% branch coverage
