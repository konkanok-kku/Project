from abc import ABC, abstractmethod

class State(ABC):
  """Abstract base class representing the light state"""

  @abstractmethod
  def turn_on(self):
    pass

  @abstractmethod
  def turn_off(self):
    pass


class OnState(State):
  """Concrete state representing the light being on"""

  def turn_on(self):
    print("Light is already on.")

  def turn_off(self):
    print("Turning light off.")
    return OffState()


class OffState(State):
  """Concrete state representing the light being off"""

  def turn_on(self):
    print("Turning light on.")
    return OnState()

  def turn_off(self):
    print("Light is already off.")


class Light:
  """Light class that manages its state"""

  def __init__(self):
    self.state = OffState()  # Initial state is Off

  def turn_on(self):
    """Delegates turn on action to the current state"""
    self.state = self.state.turn_on()

  def turn_off(self):
    """Delegates turn off action to the current state"""
    self.state = self.state.turn_off()


# Example Usage
light = Light()

light.turn_on()  # Output: Turning light on.
light.turn_on()  # Output: Light is already on.
light.turn_off()  # Output: Turning light off.
light.turn_off()  # Output: Light is already off.
