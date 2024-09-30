package Chain_of_responsbility.Round1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestCode {

    @Test
    void testCompletePackageDelivery() {
        Code.LocationHandler locationHandler = new Code.LocationHandler();
        Code.PaymentHandler paymentHandler = new Code.PaymentHandler();
        Code.DeliveryHandler deliveryHandler = new Code.DeliveryHandler();

        locationHandler.setNextHandler(paymentHandler);
        paymentHandler.setNextHandler(deliveryHandler);

        Code.Package pkg = new Code.Package("City A", true);
        locationHandler.handleRequest(pkg);
        assertTrue(pkg.delivered);
    }

    @Test
    void testInvalidLocation() {
        Code.LocationHandler locationHandler = new Code.LocationHandler();
        Code.PaymentHandler paymentHandler = new Code.PaymentHandler();
        Code.DeliveryHandler deliveryHandler = new Code.DeliveryHandler();

        locationHandler.setNextHandler(paymentHandler);
        paymentHandler.setNextHandler(deliveryHandler);

        Code.Package pkg = new Code.Package("City X", true);
        locationHandler.handleRequest(pkg);
        assertFalse(pkg.delivered);
    }

    @Test
    void testIncompletePayment() {
        Code.LocationHandler locationHandler = new Code.LocationHandler();
        Code.PaymentHandler paymentHandler = new Code.PaymentHandler();
        Code.DeliveryHandler deliveryHandler = new Code.DeliveryHandler();

        locationHandler.setNextHandler(paymentHandler);
        paymentHandler.setNextHandler(deliveryHandler);

        Code.Package pkg = new Code.Package("City B", false);
        locationHandler.handleRequest(pkg);
        assertFalse(pkg.delivered);
    }

    // Test to ensure location handler works independently
    @Test
    void testLocationHandlerOnly() {
        Code.LocationHandler locationHandler = new Code.LocationHandler();
        Code.Package pkg = new Code.Package("City C", false);
        locationHandler.handleRequest(pkg);
        assertFalse(pkg.delivered);
    }

    // Test to ensure payment handler works independently
    @Test
    void testPaymentHandlerOnly() {
        Code.PaymentHandler paymentHandler = new Code.PaymentHandler();
        Code.Package pkg = new Code.Package("City A", true);
        paymentHandler.handleRequest(pkg);
        assertFalse(pkg.delivered);
    }
}
