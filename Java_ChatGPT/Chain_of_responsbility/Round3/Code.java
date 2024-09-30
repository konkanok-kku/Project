package Round3;

// DeliveryRequest class that holds details about the package
class DeliveryRequest {
    private String location;
    private boolean paymentProcessed;
    private boolean isDelivered;

    public DeliveryRequest(String location) {
        this.location = location;
        this.paymentProcessed = false;
        this.isDelivered = false;
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
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }
}

// Abstract Handler in the chain
abstract class Handler {
    protected Handler nextHandler;

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handleRequest(DeliveryRequest request);
}

// LocationHandler verifies if the package can be delivered to the specified location
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
            System.out.println("Invalid location. Stopping the chain.");
        }
    }
}

// PaymentHandler handles payment processing
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
            System.out.println("Payment already processed. Stopping the chain.");
        }
    }
}

// DeliveryHandler manages the actual delivery of the package
class DeliveryHandler extends Handler {
    @Override
    public void handleRequest(DeliveryRequest request) {
        if (request.isPaymentProcessed()) {
            System.out.println("Delivering package...");
            request.setDelivered(true);
        } else {
            System.out.println("Cannot deliver package. Payment not processed.");
        }
    }
}

// Chain of Responsibility setup
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
        DeliveryRequest request = new DeliveryRequest("ValidLocation");
        locationHandler.handleRequest(request);

        // Create an invalid delivery request
        DeliveryRequest invalidRequest = new DeliveryRequest("InvalidLocation");
        locationHandler.handleRequest(invalidRequest);
    }
}

