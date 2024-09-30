import pytest
from Code import Light, OnState, OffState


# Test 1: Verify the light starts in the OffState
def test_initial_state_is_off():
    light = Light()
    assert isinstance(light.state, OffState), "The light should start off."


# Test 2: Test turning the light on from the off state
def test_turn_on_from_off():
    light = Light()
    light.turn_on()
    assert isinstance(light.state, OnState), "The light should be on after turning it on."


# Test 3: Test turning the light off from the on state
def test_turn_off_from_on():
    light = Light()
    light.turn_on()  # Turn it on first
    light.turn_off()
    assert isinstance(light.state, OffState), "The light should be off after turning it off."


# Test 4: Test turning the light on when it is already on
def test_turn_on_when_already_on():
    light = Light()
    light.turn_on()  # Turn it on
    light.turn_on()  # Try turning it on again
    assert isinstance(light.state, OnState), "The light should remain on if it's already on."


# Test 5: Test turning the light off when it is already off
def test_turn_off_when_already_off():
    light = Light()
    light.turn_off()  # Try turning it off when it's already off
    assert isinstance(light.state, OffState), "The light should remain off if it's already off."


# Test 6: Test full state transitions
def test_full_state_transitions():
    light = Light()

    # Start in OffState
    assert isinstance(light.state, OffState), "Initial state should be OffState."

    # Turn on, should transition to OnState
    light.turn_on()
    assert isinstance(light.state, OnState), "After turning on, the light should be in OnState."

    # Turn off, should transition back to OffState
    light.turn_off()
    assert isinstance(light.state, OffState), "After turning off, the light should be in OffState."


# Test 7: Ensure state does not change unnecessarily
def test_unnecessary_state_changes():
    light = Light()

    # Try turning off when it's already off
    light.turn_off()
    assert isinstance(light.state, OffState), "The state should not change if already off."

    # Turn on, then try turning on again without state change
    light.turn_on()
    assert isinstance(light.state, OnState), "The light should be on."
    light.turn_on()  # No change should occur
    assert isinstance(light.state, OnState), "The state should not change if already on."
