package Round2;

// DeliveryRequest class that holds details of the package delivery
class DeliveryRequest {
    private String location;
    private boolean paymentProcessed;
    private boolean delivered;

    public DeliveryRequest(String location) {
        this.location = location;
        this.paymentProcessed = false;
        this.delivered = false;
    }

    public String getLocation() {
        return location;
    }

    public boolean isPaymentProcessed() {
        return paymentProcessed;
    }

    public void setPaymentProcessed(boolean paymentProcessed) {
        this.paymentProcessed = paymentProcessed;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }
}

// Abstract handler in the chain
abstract class Handler {
    protected Handler nextHandler;

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handleRequest(DeliveryRequest request);
}

// LocationHandler verifies if the location is valid for delivery
class LocationHandler extends Handler {
    private static final String VALID_LOCATION = "ValidLocation";

    @Override
    public void handleRequest(DeliveryRequest request) {
        if (VALID_LOCATION.equals(request.getLocation())) {
            System.out.println("Location is valid.");
            if (nextHandler != null) {
                nextHandler.handleRequest(request);
            }
        } else {
            System.out.println("Invalid location. Cannot proceed.");
        }
    }
}

// PaymentHandler processes payment
class PaymentHandler extends Handler {
    @Override
    public void handleRequest(DeliveryRequest request) {
        if (!request.isPaymentProcessed()) {
            System.out.println("Processing payment...");
            request.setPaymentProcessed(true);
            if (nextHandler != null) {
                nextHandler.handleRequest(request);
            }
        } else {
            System.out.println("Payment already processed.");
        }
    }
}

// DeliveryHandler manages the actual delivery of the package
class DeliveryHandler extends Handler {
    @Override
    public void handleRequest(DeliveryRequest request) {
        if (request.isPaymentProcessed()) {
            System.out.println("Package delivered.");
            request.setDelivered(true);
        } else {
            System.out.println("Cannot deliver package. Payment not processed.");
        }
    }
}

// Main class to set up and test the Chain of Responsibility
public class Code {
    public static void main(String[] args) {
        // Create handlers
        Handler locationHandler = new LocationHandler();
        Handler paymentHandler = new PaymentHandler();
        Handler deliveryHandler = new DeliveryHandler();

        // Set up the chain
        locationHandler.setNextHandler(paymentHandler);
        paymentHandler.setNextHandler(deliveryHandler);

        // Create a valid delivery request
        DeliveryRequest validRequest = new DeliveryRequest("ValidLocation");
        locationHandler.handleRequest(validRequest);

        // Create an invalid delivery request
        DeliveryRequest invalidRequest = new DeliveryRequest("InvalidLocation");
        locationHandler.handleRequest(invalidRequest);
    }
}

