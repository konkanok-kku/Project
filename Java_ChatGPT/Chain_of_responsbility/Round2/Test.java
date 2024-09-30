package Round2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestCode {
    private Handler locationHandler;
    private Handler paymentHandler;
    private Handler deliveryHandler;
    private DeliveryRequest request;

    @BeforeEach
    public void setUp() {
        locationHandler = new LocationHandler();
        paymentHandler = new PaymentHandler();
        deliveryHandler = new DeliveryHandler();

        // Set up the chain
        locationHandler.setNextHandler(paymentHandler);
        paymentHandler.setNextHandler(deliveryHandler);
    }

    @Test
    public void testValidLocation() {
        request = new DeliveryRequest("ValidLocation");
        locationHandler.handleRequest(request);
        assertTrue(request.isPaymentProcessed());
        assertTrue(request.isDelivered());
    }

    @Test
    public void testInvalidLocation() {
        request = new DeliveryRequest("InvalidLocation");
        locationHandler.handleRequest(request);
        assertFalse(request.isPaymentProcessed());
        assertFalse(request.isDelivered());
    }

    @Test
    public void testPaymentAlreadyProcessed() {
        request = new DeliveryRequest("ValidLocation");
        request.setPaymentProcessed(true);
        locationHandler.handleRequest(request);
        assertTrue(request.isDelivered());
    }

    @Test
    public void testNoPaymentProcessed() {
        request = new DeliveryRequest("ValidLocation");
        paymentHandler.handleRequest(request);
        assertTrue(request.isPaymentProcessed());
        assertTrue(request.isDelivered());
    }
}

