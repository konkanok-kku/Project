package State.round1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LightTest {

    @Test
    public void testInitialState() {
        Light light = new Light();
        assertTrue(light.getState() instanceof OffState);
    }

    @Test
    public void testTurnOnFromOff() {
        Light light = new Light();
        light.turnOn();
        assertTrue(light.getState() instanceof OnState);
    }

    @Test
    public void testTurnOffFromOn() {
        Light light = new Light();
        light.turnOn();
        light.turnOff();
        assertTrue(light.getState() instanceof OffState);
    }

    @Test
    public void testTurnOnFromOn() {
        Light light = new Light();
        light.turnOn();
        light.turnOn();
        assertTrue(light.getState() instanceof OnState);
    }

    @Test
    public void testTurnOffFromOff() {
        Light light = new Light();
        light.turnOff();
        assertTrue(light.getState() instanceof OffState);
    }
}
