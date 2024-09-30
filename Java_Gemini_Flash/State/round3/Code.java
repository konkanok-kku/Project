package State.round3;

class Light {

    private State state;

    public Light() {
        state = new OffState();
    }

    public void turnOn() {
        state.turnOn(this);
    }

    public void turnOff() {
        state.turnOff(this);
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public static void main(String[] args) {
        Light light = new Light();
        light.turnOn();
        light.turnOff();
    }
}

interface State {
    void turnOn(Light light);
    void turnOff(Light light);
}

class OnState implements State {

    @Override
    public void turnOn(Light light) {
        System.out.println("Light is already on!");
    }

    @Override
    public void turnOff(Light light) {
        System.out.println("Turning off the light...");
        light.setState(new OffState());
    }
}

class OffState implements State {

    @Override
    public void turnOn(Light light) {
        System.out.println("Turning on the light...");
        light.setState(new OnState());
    }

    @Override
    public void turnOff(Light light) {
        System.out.println("Light is already off!");
    }
}
