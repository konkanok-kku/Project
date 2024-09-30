package State.round2;

// State
interface State {
    void turnOn(Light light);
    void turnOff(Light light);
}

// OnState
class OnState implements State {
    @Override
    public void turnOn(Light light) {
        System.out.println("Light is already on");
    }

    @Override
    public void turnOff(Light light) {
        System.out.println("Turning light off");
        light.setState(new OffState());
    }
}

// OffState
class OffState implements State {
    @Override
    public void turnOn(Light light) {
        System.out.println("Turning light on");
        light.setState(new OnState());
    }

    @Override
    public void turnOff(Light light) {
        System.out.println("Light is already off");
    }
}

// Light
class Light {
    private State state;

    public Light() {
        state = new OffState();
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
