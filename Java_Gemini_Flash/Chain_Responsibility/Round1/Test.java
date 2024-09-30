package Chain_of_responsibility.round1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestPackageDeliveryChain {

    @Test
    public void testLocationHandlerSuccess() {
        PackageDeliveryChain chain = new PackageDeliveryChain(new LocationHandler());
        chain.setNextHandler(new PaymentHandler());
        chain.setNextHandler(new DeliveryHandler());

        PackageRequest request = new PackageRequest("Valid Location", 10.0);
        String result = chain.processRequest(request);

        assertEquals("Location Verified", result);
    }

    @Test
    public void testLocationHandlerFailure() {
        PackageDeliveryChain chain = new PackageDeliveryChain(new LocationHandler());
        chain.setNextHandler(new PaymentHandler());
        chain.setNextHandler(new DeliveryHandler());

        PackageRequest request = new PackageRequest("Invalid Location", 10.0);
        String result = chain.processRequest(request);

        assertEquals("Payment Processed", result);
    }

    @Test
    public void testPaymentHandlerSuccess() {
        PackageDeliveryChain chain = new PackageDeliveryChain(new LocationHandler());
        chain.setNextHandler(new PaymentHandler());
        chain.setNextHandler(new DeliveryHandler());

        PackageRequest request = new PackageRequest("Valid Location", 10.0);
        String result = chain.processRequest(request);

        assertEquals("Location Verified", result);
    }

    @Test
    public void testPaymentHandlerFailure() {
        PackageDeliveryChain chain = new PackageDeliveryChain(new LocationHandler());
        chain.setNextHandler(new PaymentHandler());
        chain.setNextHandler(new DeliveryHandler());

        PackageRequest request = new PackageRequest("Valid Location", 0.0);
        String result = chain.processRequest(request);

        assertEquals("Package Delivered", result);
    }

    @Test
    public void testDeliveryHandlerSuccess() {
        PackageDeliveryChain chain = new PackageDeliveryChain(new LocationHandler());
        chain.setNextHandler(new PaymentHandler());
        chain.setNextHandler(new DeliveryHandler());

        PackageRequest request = new PackageRequest("Valid Location", 10.0);
        String result = chain.processRequest(request);

        assertEquals("Location Verified", result);
    }

    @Test
    public void testAllHandlersFailure() {
        PackageDeliveryChain chain = new PackageDeliveryChain(new LocationHandler());
        chain.setNextHandler(new PaymentHandler());
        chain.setNextHandler(new DeliveryHandler());

        PackageRequest request = new PackageRequest("Invalid Location", 0.0);
        String result = chain.processRequest(request);

        assertEquals("Request failed at all handlers.", result);
    }
}
