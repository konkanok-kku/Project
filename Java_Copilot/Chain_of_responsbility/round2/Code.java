package Chain_of_responsibility.round2;

class PackageRequest {
    private String location;
    private boolean paymentProcessed;
    private boolean canDeliver;

    public PackageRequest(String location) {
        this.location = location;
        this.paymentProcessed = false;
        this.canDeliver = false;
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

    public boolean canDeliver() {
        return canDeliver;
    }

    public void setCanDeliver(boolean canDeliver) {
        this.canDeliver = canDeliver;
    }
}

// DeliveryHandler.java
abstract class DeliveryHandler {
    protected DeliveryHandler nextHandler;

    public void setNextHandler(DeliveryHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract boolean handle(PackageRequest request);
}

// LocationHandler.java
class LocationHandler extends DeliveryHandler {
    @Override
    public boolean handle(PackageRequest request) {
        if (isValidLocation(request.getLocation())) {
            request.setCanDeliver(true);
            System.out.println("Location is valid.");
            return nextHandler != null ? nextHandler.handle(request) : true;
        } else {
            System.out.println("Invalid location. Cannot deliver.");
            return false;
        }
    }

    private boolean isValidLocation(String location) {
        // In a real scenario, this would check against a database of valid locations
        return !location.isEmpty() && !location.equalsIgnoreCase("invalid");
    }
}

// PaymentHandler.java
class PaymentHandler extends DeliveryHandler {
    @Override
    public boolean handle(PackageRequest request) {
        if (processPayment()) {
            request.setPaymentProcessed(true);
            System.out.println("Payment processed successfully.");
            return nextHandler != null ? nextHandler.handle(request) : true;
        } else {
            System.out.println("Payment processing failed.");
            return false;
        }
    }

    private boolean processPayment() {
        // In a real scenario, this would interact with a payment gateway
        return Math.random() > 0.2; // 80% success rate for demonstration
    }
}

// DeliveryHandler.java
class FinalDeliveryHandler extends DeliveryHandler {
    @Override
    public boolean handle(PackageRequest request) {
        if (request.canDeliver() && request.isPaymentProcessed()) {
            System.out.println("Package delivered successfully.");
            return true;
        } else {
            System.out.println("Cannot complete delivery due to previous steps.");
            return false;
        }
    }
}

// DeliveryChain.java
class DeliveryChain {
    private DeliveryHandler chain;

    public DeliveryChain() {
        buildChain();
    }

    private void buildChain() {
        chain = new LocationHandler();
        DeliveryHandler paymentHandler = new PaymentHandler();
        DeliveryHandler deliveryHandler = new FinalDeliveryHandler();

        chain.setNextHandler(paymentHandler);
        paymentHandler.setNextHandler(deliveryHandler);
    }

    public boolean processDeliveryRequest(PackageRequest request) {
        return chain.handle(request);
    }
}
