package Chain_of_responsibility.round3;

interface PackageHandler {
    boolean canHandle(PackageRequest request);
    void handle(PackageRequest request);
}

class PackageRequest {
    private String location;
    private double paymentAmount;
    private String recipientName;

    public PackageRequest(String location, double paymentAmount, String recipientName) {
        this.location = location;
        this.paymentAmount = paymentAmount;
        this.recipientName = recipientName;
    }

    public String getLocation() {
        return location;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public String getRecipientName() {
        return recipientName;
    }
}

class LocationHandler implements PackageHandler {
    private final String validLocation;

    public LocationHandler(String validLocation) {
        this.validLocation = validLocation;
    }

    @Override
    public boolean canHandle(PackageRequest request) {
        return request.getLocation().equals(validLocation);
    }

    @Override
    public void handle(PackageRequest request) {
        System.out.println("Location validated: " + request.getLocation());
    }
}

class PaymentHandler implements PackageHandler {
    @Override
    public boolean canHandle(PackageRequest request) {
        return request.getPaymentAmount() > 0;
    }

    @Override
    public void handle(PackageRequest request) {
        System.out.println("Payment processed: " + request.getPaymentAmount());
    }
}

class DeliveryHandler implements PackageHandler {
    @Override
    public boolean canHandle(PackageRequest request) {
        return true; // Always handles the delivery
    }

    @Override
    public void handle(PackageRequest request) {
        System.out.println("Package delivered to " + request.getRecipientName() + " at " + request.getLocation());
    }
}

class PackageDeliveryChain {
    private PackageHandler handler;

    public PackageDeliveryChain(PackageHandler firstHandler) {
        this.handler = firstHandler;
    }

    public void processRequest(PackageRequest request) {
        if (handler.canHandle(request)) {
            handler.handle(request);
        } else {
            // Pass to the next handler in the chain if available
            if (handler instanceof ChainHandler) {
                ((ChainHandler) handler).setNextHandler(request);
            }
        }
    }
}

interface ChainHandler extends PackageHandler {
    void setNextHandler(PackageRequest request);
}

abstract class AbstractChainHandler implements ChainHandler {
    private PackageHandler nextHandler;

    @Override
    public void setNextHandler(PackageRequest request) {
        if (nextHandler != null) {
            nextHandler.handle(request);
        }
    }

    public void setNext(PackageHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}

class ChainLocationHandler extends AbstractChainHandler {

    private String validLocation;

    public ChainLocationHandler(String validLocation) {
        this.validLocation = validLocation;
    }

    @Override
    public boolean canHandle(PackageRequest request) {
        return request.getLocation().equals(validLocation);
    }

    @Override
    public void handle(PackageRequest request) {
        System.out.println("Location validated: " + request.getLocation());
    }
}

class ChainPaymentHandler extends AbstractChainHandler {
    @Override
    public boolean canHandle(PackageRequest request) {
        return request.getPaymentAmount() > 0;
    }

    @Override
    public void handle(PackageRequest request) {
        System.out.println("Payment processed: " + request.getPaymentAmount());
    }
}

class ChainDeliveryHandler extends AbstractChainHandler {
    @Override
    public boolean canHandle(PackageRequest request) {
        return true; // Always handles the delivery
    }

    @Override
    public void handle(PackageRequest request) {
        System.out.println("Package delivered to " + request.getRecipientName() + " at " + request.getLocation());
    }
}
