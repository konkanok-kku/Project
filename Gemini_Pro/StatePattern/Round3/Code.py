class State:
    """Interface for light states."""

    def turn_on(self, light):
        raise NotImplementedError

    def turn_off(self, light):
        raise NotImplementedError

    def increase_brightness(self, light):
        raise NotImplementedError

    def decrease_brightness(self, light):
        raise NotImplementedError


class OnState(State):
    """Represents the 'On' state of the light."""

    def turn_on(self, light):
        print("Light is already on.")

    def turn_off(self, light):
        print("Turning light off.")
        light.set_state(OffState())

    def increase_brightness(self, light):
        light._brightness = min(light._brightness + 10, 100)
        print(f"Brightness increased to {light._brightness}%")

    def decrease_brightness(self, light):
        light._brightness = max(light._brightness - 10, 0)
        print(f"Brightness decreased to {light._brightness}%")


class OffState(State):
    """Represents the 'Off' state of the light."""

    def turn_on(self, light):
        print("Turning light on.")
        light.set_state(OnState())

    def turn_off(self, light):
        print("Light is already off.")

    def increase_brightness(self, light):
        print("Light is off. Cannot increase brightness.")

    def decrease_brightness(self, light):
        print("Light is off. Cannot decrease brightness.")


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
        self._state.increase_brightness(self)

    def decrease_brightness(self):
        self._state.decrease_brightness(self)

    def get_brightness(self):
        return self._brightness