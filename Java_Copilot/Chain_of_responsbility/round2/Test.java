package Chain_of_responsibility.round2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import static org.junit.jupiter.api.Assertions.*;

class DeliveryChainTest {

    private DeliveryChain deliveryChain;

    @BeforeEach
    void setUp() {
        deliveryChain = new DeliveryChain();
    }

    @Test
    void testValidDelivery() {
        PackageRequest request = new PackageRequest("Valid Location");
        boolean result = deliveryChain.processDeliveryRequest(request);
        assertTrue(result);
        assertTrue(request.isPaymentProcessed());
        assertTrue(request.canDeliver());
    }

    @Test
    void testInvalidLocation() {
        PackageRequest request = new PackageRequest("invalid");
        boolean result = deliveryChain.processDeliveryRequest(request);
        assertFalse(result);
        assertFalse(request.isPaymentProcessed());
        assertFalse(request.canDeliver());
    }

    @RepeatedTest(5)
    void testPaymentProcessing() {
        PackageRequest request = new PackageRequest("Valid Location");
        boolean result = deliveryChain.processDeliveryRequest(request);

        if (result) {
            assertTrue(request.isPaymentProcessed());
            assertTrue(request.canDeliver());
        } else {
            assertTrue(request.canDeliver());
            assertFalse(request.isPaymentProcessed());
        }
    }

    @Test
    void testEmptyLocation() {
        PackageRequest request = new PackageRequest("");
        boolean result = deliveryChain.processDeliveryRequest(request);
        assertFalse(result);
        assertFalse(request.isPaymentProcessed());
        assertFalse(request.canDeliver());
    }

    // This test ensures that the chain stops if a handler cannot process the request
    @Test
    void testChainStopsOnFailure() {
        // Create a custom chain where payment always fails
        LocationHandler locationHandler = new LocationHandler();
        PaymentHandler paymentHandler = new PaymentHandler() {
            protected boolean processPayment() {
                return false; // Always fail payment
            }
        };
        FinalDeliveryHandler finalDeliveryHandler = new FinalDeliveryHandler();

        locationHandler.setNextHandler(paymentHandler);
        paymentHandler.setNextHandler(finalDeliveryHandler);

        PackageRequest request = new PackageRequest("Valid Location");
        boolean result = locationHandler.handle(request);

        assertFalse(result);
        assertTrue(request.canDeliver()); // Location was valid
        assertFalse(request.isPaymentProcessed()); // Payment failed
    }
}
