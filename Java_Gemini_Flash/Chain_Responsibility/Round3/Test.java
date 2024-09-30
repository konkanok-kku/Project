package Chain_of_responsibility.round3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestPackageDelivery {

    @Test
    void testValidDelivery() {
        PackageRequest request = new PackageRequest("New York", 10.0, "John Doe");
        ChainLocationHandler locationHandler = new ChainLocationHandler("New York");
        ChainPaymentHandler paymentHandler = new ChainPaymentHandler();
        ChainDeliveryHandler deliveryHandler = new ChainDeliveryHandler();

        locationHandler.setNext(paymentHandler);
        paymentHandler.setNext(deliveryHandler);

        PackageDeliveryChain chain = new PackageDeliveryChain(locationHandler);

        chain.processRequest(request);

        // Verify the handlers were called in the correct order
        assertTrue(locationHandler.canHandle(request));
        assertTrue(paymentHandler.canHandle(request));
        assertTrue(deliveryHandler.canHandle(request));
    }

    @Test
    void testInvalidLocation() {
        PackageRequest request = new PackageRequest("London", 10.0, "Jane Doe");
        ChainLocationHandler locationHandler = new ChainLocationHandler("New York");
        ChainPaymentHandler paymentHandler = new ChainPaymentHandler();
        ChainDeliveryHandler deliveryHandler = new ChainDeliveryHandler();

        locationHandler.setNext(paymentHandler);
        paymentHandler.setNext(deliveryHandler);

        PackageDeliveryChain chain = new PackageDeliveryChain(locationHandler);

        chain.processRequest(request);

        // Verify the handlers were called in the correct order
        assertFalse(locationHandler.canHandle(request));
        assertTrue(paymentHandler.canHandle(request));
        assertTrue(deliveryHandler.canHandle(request));
    }

    @Test
    void testNoPayment() {
        PackageRequest request = new PackageRequest("New York", 0.0, "Mike Doe");
        ChainLocationHandler locationHandler = new ChainLocationHandler("New York");
        ChainPaymentHandler paymentHandler = new ChainPaymentHandler();
        ChainDeliveryHandler deliveryHandler = new ChainDeliveryHandler();

        locationHandler.setNext(paymentHandler);
        paymentHandler.setNext(deliveryHandler);

        PackageDeliveryChain chain = new PackageDeliveryChain(locationHandler);

        chain.processRequest(request);

        // Verify the handlers were called in the correct order
        assertTrue(locationHandler.canHandle(request));
        assertFalse(paymentHandler.canHandle(request));
        assertTrue(deliveryHandler.canHandle(request));
    }
}
