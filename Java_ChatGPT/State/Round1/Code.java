package State.Round1;

// State interface defines the methods for actions.
interface State {
    void turnOn(Light light);
    void turnOff(Light light);
}

// Concrete State: OnState
class OnState implements State {
    @Override
    public void turnOn(Light light) {
        System.out.println("The light is already ON.");
    }

    @Override
    public void turnOff(Light light) {
        System.out.println("Turning the light OFF.");
        light.setState(new OffState());
    }
}

// Concrete State: OffState
class OffState implements State {
    @Override
    public void turnOn(Light light) {
        System.out.println("Turning the light ON.");
        light.setState(new OnState());
    }

    @Override
    public void turnOff(Light light) {
        System.out.println("The light is already OFF.");
    }
}

// Light class that manages state transitions
class Light {
    private State state;

    public Light() {
        this.state = new OffState();  // Default state is OFF.
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

