package State.round2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LightTest {

    @Test
    void testTurnOnFromOff() {
        Light light = new Light();
        assertTrue(light.getState() instanceof OffState); // Check if the state is off
        light.turnOn();
        assertTrue(light.getState() instanceof OnState); // Check if the state is on
    }

    @Test
    void testTurnOnFromOn() {
        Light light = new Light();
        light.turnOn();
        light.turnOn();
        assertTrue(light.getState() instanceof OnState);
    }

    @Test
    void testTurnOffFromOn() {
        Light light = new Light();
        light.turnOn();
        light.turnOff();
        assertTrue(light.getState() instanceof OffState);
    }

    @Test
    void testTurnOffFromOff() {
        Light light = new Light();
        light.turnOff();
        assertTrue(light.getState() instanceof OffState);
    }
}
