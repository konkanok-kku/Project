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

# Concrete state: On
class OnState(State):
    def turn_on(self, light):
        print("The light is already on.")

    def turn_off(self, light):
        print("Turning the light off.")
        light.set_state(OffState())  # Transition to OffState

# Concrete state: Off
class OffState(State):
    def turn_on(self, light):
        print("Turning the light on.")
        light.set_state(OnState())  # Transition to OnState

    def turn_off(self, light):
        print("The light is already off.")

# Context class: Light
class Light:
    def __init__(self):
        # Initialize with the light turned off
        self.state = OffState()  # Default state

    def set_state(self, state):
        """Set a new state for the light."""
        self.state = state

    def turn_on(self):
        """Delegate to the state's turn_on method."""
        self.state.turn_on(self)

    def turn_off(self):
        """Delegate to the state's turn_off method."""
        self.state.turn_off(self)
