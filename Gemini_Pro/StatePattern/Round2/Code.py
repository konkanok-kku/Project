class State:
    """Interface for light states."""

    def turn_on(self, light):
        raise NotImplementedError

    def turn_off(self, light):
        raise NotImplementedError


class OnState(State):
    """Represents the 'On' state of the light."""

    def turn_on(self, light):
        print("Light is already on.")

    def turn_off(self, light):
        print("Turning light off.")
        light.set_state(OffState())


class OffState(State):
    """Represents the 'Off' state of the light."""

    def turn_on(self, light):
        print("Turning light on.")
        light.set_state(OnState())

    def turn_off(self, light):
        print("Light is already off.")


class Light:
    """Represents a light with state management."""

    def __init__(self):
        self._state = OffState()
        self._brightness = 0

    def set_state(self, state):
        self._state = state

    def turn_on(self):
        self._state.turn_on(self)

    def turn_off(self):
        self._state.turn_off(self)

    def increase_brightness(self):
        if self._state.__class__ == OnState:
            self._brightness = min(self._brightness + 10, 100)
            print(f"Brightness increased to {self._brightness}%")
        else:
            print("Light is off. Cannot increase brightness.")

    def decrease_brightness(self):
        if self._state.__class__ == OnState:
            self._brightness = max(self._brightness - 10, 0)
            print(f"Brightness decreased to {self._brightness}%")
        else:
            print("Light is off. Cannot decrease brightness.")

    def get_brightness(self):
        return self._brightness