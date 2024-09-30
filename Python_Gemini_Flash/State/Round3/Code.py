from abc import ABC, abstractmethod

class LightState(ABC):
  """Abstract base class representing the light state"""

  @abstractmethod
  def turn_on(self, light: "Light") -> "LightState":
    pass

  @abstractmethod
  def turn_off(self, light: "Light") -> "LightState":
    pass

class OnState(LightState):
  """Concrete state representing the light being on"""

  def turn_on(self, light):
    print("Light is already on.")
    return self

  def turn_off(self, light):
    print("Turning light off.")
    return OffState()

class OffState(LightState):
  """Concrete state representing the light being off"""

  def turn_on(self, light):
    print("Turning light on.")
    return OnState()

  def turn_off(self, light):
    print("Light is already off.")
    return self

class Light:
  """Light class that manages its state"""

  def __init__(self):
    self.state = OffState()  # Initial state is Off

  def turn_on(self):
    """Delegates turn on action to the current state"""
    self.state = self.state.turn_on(self)

  def turn_off(self):
    """Delegates turn off action to the current state"""
    self.state = self.state.turn_off(self)

# Example Usage
light = Light()

light.turn_on()  # Output: Turning light on.
light.turn_on()  # Output: Light is already on.
light.turn_off()  # Output: Turning light off.
light.turn_off()  # Output: Light is already off.
