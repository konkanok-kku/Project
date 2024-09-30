package State.Round2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LightTest {

    private Light light;

    @BeforeEach
    void setUp() {
        light = new Light();  // Light is initially off
    }

    @Test
    void testInitialOffState() {
        assertTrue(light.getState() instanceof OffState);
    }

    @Test
    void testTurnOnFromOffState() {
        light.turnOn();  // Turn on the light
        assertTrue(light.getState() instanceof OnState);
    }

    @Test
    void testTurnOffFromOffState() {
        light.turnOff();  // Light is already off
        assertTrue(light.getState() instanceof OffState);
    }

    @Test
    void testTurnOffFromOnState() {
        light.turnOn();  // Turn on the light
        light.turnOff();  // Turn it off
        assertTrue(light.getState() instanceof OffState);
    }

    @Test
    void testTurnOnFromOnState() {
        light.turnOn();  // Turn on the light
        light.turnOn();  // Try to turn on again
        assertTrue(light.getState() instanceof OnState);
    }
}

