package Chain_of_responsibility.round2;

interface Handler {
    void handleRequest(PackageRequest request);
}

class PackageRequest {
    private String location;
    private double paymentAmount;

    public PackageRequest(String location, double paymentAmount) {
        this.location = location;
        this.paymentAmount = paymentAmount;
    }

    public String getLocation() {
        return location;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }
}

class LocationHandler implements Handler {
    private Handler nextHandler;

    public LocationHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(PackageRequest request) {
        if (isValidLocation(request.getLocation())) {
            System.out.println("Location validated.");
        } else {
            System.out.println("Invalid location. Passing to next handler.");
            nextHandler.handleRequest(request);
        }
    }

    private boolean isValidLocation(String location) {
        // Check if the location is valid, for example:
        return !location.isEmpty();
    }
}

class PaymentHandler implements Handler {
    private Handler nextHandler;

    public PaymentHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(PackageRequest request) {
        if (isPaymentProcessed(request.getPaymentAmount())) {
            System.out.println("Payment processed.");
        } else {
            System.out.println("Payment not processed. Passing to next handler.");
            nextHandler.handleRequest(request);
        }
    }

    private boolean isPaymentProcessed(double paymentAmount) {
        // Check if the payment is processed, for example:
        return paymentAmount > 0;
    }
}

class DeliveryHandler implements Handler {

    @Override
    public void handleRequest(PackageRequest request) {
        System.out.println("Package delivered.");
    }
}
