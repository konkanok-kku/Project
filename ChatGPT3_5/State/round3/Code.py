from abc import ABC, abstractmethod

# State interface (abstract class)
class State(ABC):
    @abstractmethod
    def turn_on(self, light):
        """Handle turning the light on."""
        pass

    @abstractmethod
    def turn_off(self, light):
        """Handle turning the light off."""
        pass

# Concrete state: Light is ON
class OnState(State):
    def turn_on(self, light):
        """Handle case when light is already on."""
        print("The light is already on.")

    def turn_off(self, light):
        """Turn off the light and transition to OffState."""
        print("Turning the light off.")
        light.set_state(OffState())

# Concrete state: Light is OFF
class OffState(State):
    def turn_on(self, light):
        """Turn on the light and transition to OnState."""
        print("Turning the light on.")
        light.set_state(OnState())

    def turn_off(self, light):
        """Handle case when light is already off."""
        print("The light is already off.")

# Context class: Light
class Light:
    def __init__(self):
        # Initialize the light in the OffState
        self.state = OffState()

    def set_state(self, state):
        """Set the current state of the light."""
        self.state = state

    def turn_on(self):
        """Delegate to the state's turn_on method."""
        print("Attempting to turn on the light...")
        self.state.turn_on(self)

    def turn_off(self):
        """Delegate to the state's turn_off method."""
        print("Attempting to turn off the light...")
        self.state.turn_off(self)
