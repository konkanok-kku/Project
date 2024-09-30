package State.Round1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LightTest {

    private Light light;

    @BeforeEach
    void setUp() {
        light = new Light();
    }

    @Test
    void testInitialLightStateIsOff() {
        assertTrue(light.getState() instanceof OffState);
    }

    @Test
    void testTurnOnFromOffState() {
        light.turnOn();
        assertTrue(light.getState() instanceof OnState);
    }

    @Test
    void testTurnOffFromOffState() {
        light.turnOff();  // Already off, should remain off
        assertTrue(light.getState() instanceof OffState);
    }

    @Test
    void testTurnOnFromOnState() {
        light.turnOn();  // Turn it on first
        light.turnOn();  // Try turning it on again, should stay on
        assertTrue(light.getState() instanceof OnState);
    }

    @Test
    void testTurnOffFromOnState() {
        light.turnOn();  // Turn it on first
        light.turnOff();  // Now turn it off
        assertTrue(light.getState() instanceof OffState);
    }
}

