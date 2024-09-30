from abc import ABC, abstractmethod


# State interface
class State(ABC):
    @abstractmethod
    def turn_on(self, light):
        pass

    @abstractmethod
    def turn_off(self, light):
        pass


# Concrete state: On
class OnState(State):
    def turn_on(self, light):
        print("The light is already on.")

    def turn_off(self, light):
        print("Turning the light off.")
        light.set_state(OffState())


# Concrete state: Off
class OffState(State):
    def turn_on(self, light):
        print("Turning the light on.")
        light.set_state(OnState())

    def turn_off(self, light):
        print("The light is already off.")


# Context class: Light
class Light:
    def __init__(self):
        self.state = OffState()  # Default state is Off

    def set_state(self, state):
        self.state = state

    def turn_on(self):
        self.state.turn_on(self)

    def turn_off(self):
        self.state.turn_off(self)
