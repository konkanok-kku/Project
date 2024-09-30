package Chain_of_responsibility.round3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChainOfResponsibilityTest {

    private DeliveryChain deliveryChain;

    @BeforeEach
    void setUp() {
        deliveryChain = new DeliveryChain();
    }

    @Test
    void testValidLocationAndSuccessfulPayment() {
        PackageRequest request = new PackageRequest("Valid Location");
        assertTrue(deliveryChain.processDeliveryRequest(request));
        assertTrue(request.canDeliver());
        assertTrue(request.isPaymentProcessed());
    }

    @Test
    void testInvalidLocation() {
        PackageRequest request = new PackageRequest("invalid");
        assertFalse(deliveryChain.processDeliveryRequest(request));
        assertFalse(request.canDeliver());
        assertFalse(request.isPaymentProcessed());
    }

    @Test
    void testEmptyLocation() {
        PackageRequest request = new PackageRequest("");
        assertFalse(deliveryChain.processDeliveryRequest(request));
        assertFalse(request.canDeliver());
        assertFalse(request.isPaymentProcessed());
    }

    @Test
    void testValidLocationButPaymentFailure() {
        PackageRequest request = new PackageRequest("Valid Location");

        // Use a custom DeliveryChain with a mocked PaymentHandler
        DeliveryChain mockChain = new DeliveryChain() {
            @Override
            public boolean processDeliveryRequest(PackageRequest request) {
                LocationHandler locationHandler = new LocationHandler();
                PaymentHandler paymentHandler = new PaymentHandler() {

                    protected boolean processPayment() {
                        return false; // Always fail payment
                    }
                };
                FinalDeliveryHandler finalHandler = new FinalDeliveryHandler();

                locationHandler.setNextHandler(paymentHandler);
                paymentHandler.setNextHandler(finalHandler);

                return locationHandler.handle(request);
            }
        };

        assertFalse(mockChain.processDeliveryRequest(request));
        assertTrue(request.canDeliver());
        assertFalse(request.isPaymentProcessed());
    }

    @Test
    void testLocationHandlerInIsolation() {
        LocationHandler locationHandler = new LocationHandler();
        PackageRequest validRequest = new PackageRequest("Valid Location");
        PackageRequest invalidRequest = new PackageRequest("invalid");

        assertTrue(locationHandler.handle(validRequest));
        assertTrue(validRequest.canDeliver());

        assertFalse(locationHandler.handle(invalidRequest));
        assertFalse(invalidRequest.canDeliver());
    }

    @Test
    void testPaymentHandlerInIsolation() {
        PaymentHandler paymentHandler = new PaymentHandler();
        PackageRequest request = new PackageRequest("Any Location");

        // Run multiple times to cover both success and failure scenarios
        boolean sawSuccess = false;
        boolean sawFailure = false;
        for (int i = 0; i < 100 && (!sawSuccess || !sawFailure); i++) {
            boolean result = paymentHandler.handle(request);
            if (result) {
                sawSuccess = true;
                assertTrue(request.isPaymentProcessed());
            } else {
                sawFailure = true;
                assertFalse(request.isPaymentProcessed());
            }
            // Reset for next iteration
            request.setPaymentProcessed(false);
        }
        assertTrue(sawSuccess && sawFailure, "Both success and failure scenarios should be observed");
    }

    @Test
    void testFinalDeliveryHandlerInIsolation() {
        FinalDeliveryHandler finalHandler = new FinalDeliveryHandler();

        PackageRequest successRequest = new PackageRequest("Valid Location");
        successRequest.setCanDeliver(true);
        successRequest.setPaymentProcessed(true);
        assertTrue(finalHandler.handle(successRequest));

        PackageRequest failRequest1 = new PackageRequest("Valid Location");
        failRequest1.setCanDeliver(false);
        failRequest1.setPaymentProcessed(true);
        assertFalse(finalHandler.handle(failRequest1));

        PackageRequest failRequest2 = new PackageRequest("Valid Location");
        failRequest2.setCanDeliver(true);
        failRequest2.setPaymentProcessed(false);
        assertFalse(finalHandler.handle(failRequest2));
    }
}
