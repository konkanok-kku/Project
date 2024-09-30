package State.Round3;

// State interface
interface State {
    void turnOn(Light light);
    void turnOff(Light light);
}

// Concrete state for "On"
class OnState implements State {
    @Override
    public void turnOn(Light light) {
        System.out.println("The light is already on.");
    }

    @Override
    public void turnOff(Light light) {
        System.out.println("Turning off the light.");
        light.setState(new OffState());
    }
}

// Concrete state for "Off"
class OffState implements State {
    @Override
    public void turnOn(Light light) {
        System.out.println("Turning on the light.");
        light.setState(new OnState());
    }

    @Override
    public void turnOff(Light light) {
        System.out.println("The light is already off.");
    }
}

// Context class (Light)
class Light {
    private State state;

    public Light() {
        this.state = new OffState(); // Default state is off
    }

    public void setState(State state) {
        this.state = state;
    }

    public void turnOn() {
        state.turnOn(this);
    }

    public void turnOff() {
        state.turnOff(this);
    }

    public State getState() {
        return state;
    }
}
