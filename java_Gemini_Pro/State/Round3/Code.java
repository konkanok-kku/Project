package State.Round3;

public class Code {
    public static void main(String[] args) {
        Light light = new Light();
        System.out.println("Initial state: " + light.getState());

        light.turnOn();
        System.out.println("State after turning on: " + light.getState());

        light.turnOn();
        System.out.println("State after trying to turn on again: " + light.getState());

        light.turnOff();
        System.out.println("State after turning off: " + light.getState());

        light.turnOff();
        System.out.println("State after trying to turn off again: " + light.getState());
    }
}

class Light {
    private State state;

    public Light() {
        this.state = new OffState();
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

    public String getState() {
        return state.toString();
    }
}

interface State {
    void turnOn(Light light);
    void turnOff(Light light);
}

class OnState implements State {
    @Override
    public void turnOn(Light light) {
        System.out.println("Light is already on.");
    }

    @Override
    public void turnOff(Light light) {
        System.out.println("Turning light off.");
        light.setState(new OffState());
    }

    @Override
    public String toString() {
        return "On";
    }
}

class OffState implements State {
    @Override
    public void turnOn(Light light) {
        System.out.println("Turning light on.");
        light.setState(new OnState());
    }

    @Override
    public void turnOff(Light light) {
        System.out.println("Light is already off.");
    }

    @Override
    public String toString() {
        return "Off";
    }
}
