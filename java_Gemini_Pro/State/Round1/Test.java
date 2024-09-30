package State.Round1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestLight {

    @Test
    void testInitialOffState() {
        Light light = new Light();
        assertEquals("Off", light.getState());
    }

    @Test
    void testTurnOn() {
        Light light = new Light();
        light.turnOn();
        assertEquals("On", light.getState());
    }

    @Test
    void testTurnOff() {
        Light light = new Light();
        light.turnOn();
        light.turnOff();
        assertEquals("Off", light.getState());
    }

    @Test
    void testAlreadyOn() {
        Light light = new Light();
        light.turnOn();
        light.turnOn();
        assertEquals("On", light.getState());
    }

    @Test
    void testAlreadyOff() {
        Light light = new Light();
        light.turnOff();
        assertEquals("Off", light.getState());
    }
}
