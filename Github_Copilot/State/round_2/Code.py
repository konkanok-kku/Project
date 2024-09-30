from abc import ABC, abstractmethod

class State(ABC):
    @abstractmethod
    def turn_on(self, light):
        pass

    @abstractmethod
    def turn_off(self, light):
        pass

class OnState(State):
    def turn_on(self, light):
        print("The light is already on.")

    def turn_off(self, light):
        print("Turning off the light.")
        light.set_state(OffState())

class OffState(State):
    def turn_on(self, light):
        print("Turning on the light.")
        light.set_state(OnState())

    def turn_off(self, light):
        print("The light is already off.")

class Light:
    def __init__(self):
        self._state = OffState()  # Initial state

    def set_state(self, state):
        self._state = state

    def get_state(self):
        return self._state

    def turn_on(self):
        self._state.turn_on(self)

    def turn_off(self):
        self._state.turn_off(self)

if __name__ == "__main__":
    light = Light()

    light.turn_on()  # Turning on the light.
    light.turn_on()  # The light is already on.
    light.turn_off() # Turning off the light.
    light.turn_off() # The light is already off.
