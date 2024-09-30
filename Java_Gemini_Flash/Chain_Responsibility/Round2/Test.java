package Chain_of_responsibility.round2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestChainOfResponsibility {

    @Test
    void testValidLocationAndPayment() {
        DeliveryHandler deliveryHandler = new DeliveryHandler();
        PaymentHandler paymentHandler = new PaymentHandler(deliveryHandler);
        LocationHandler locationHandler = new LocationHandler(paymentHandler);

        PackageRequest request = new PackageRequest("Valid Location", 100.0);

        locationHandler.handleRequest(request);

        // No need for Mockito verification here since we're not mocking
        // We can directly check the internal state of the handlers
        assertTrue(deliveryHandler.isRequestHandled());
    }

    @Test
    void testInvalidLocation() {
        DeliveryHandler deliveryHandler = new DeliveryHandler();
        PaymentHandler paymentHandler = new PaymentHandler(deliveryHandler);
        LocationHandler locationHandler = new LocationHandler(paymentHandler);

        PackageRequest request = new PackageRequest("", 0);

        locationHandler.handleRequest(request);

        assertFalse(deliveryHandler.isRequestHandled());
        assertTrue(paymentHandler.isRequestHandled());
    }

    @Test
    void testInvalidPayment() {
        DeliveryHandler deliveryHandler = new DeliveryHandler();
        PaymentHandler paymentHandler = new PaymentHandler(deliveryHandler);
        LocationHandler locationHandler = new LocationHandler(paymentHandler);

        PackageRequest request = new PackageRequest("Valid Location", 0);

        locationHandler.handleRequest(request);

        assertFalse(deliveryHandler.isRequestHandled());
        assertTrue(paymentHandler.isRequestHandled());
    }
}