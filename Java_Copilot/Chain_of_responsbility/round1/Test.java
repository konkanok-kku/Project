package Chain_of_responsibility.round1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @Test
    void testPaymentFailure() {
        boolean paymentFailureOccurred = false;
        for (int i = 0; i < 100 && !paymentFailureOccurred; i++) {
            PackageRequest request = new PackageRequest("Valid Location");
            boolean result = deliveryChain.processDeliveryRequest(request);
            if (!result && request.canDeliver() && !request.isPaymentProcessed()) {
                paymentFailureOccurred = true;
            }
        }
        assertTrue(paymentFailureOccurred, "Payment failure should occur at least once in 100 attempts");
    }

    @Test
    void testDeliveryFailureDueToIncompleteSteps() {
        PackageRequest request = new PackageRequest("Valid Location");
        request.setCanDeliver(true);
        request.setPaymentProcessed(false);
        boolean result = deliveryChain.processDeliveryRequest(request);
        assertTrue(result);
    }
}