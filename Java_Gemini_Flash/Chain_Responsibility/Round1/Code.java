package Chain_of_responsibility.round1;

import java.util.Optional;

interface PackageHandler {
    Optional<String> handle(PackageRequest request);

    PackageHandler nextHandler();

    void setNextHandler(PackageHandler handler);
}

class PackageRequest {
    private String location;
    private double amount;

    public PackageRequest(String location, double amount) {
        this.location = location;
        this.amount = amount;
    }

    public String getLocation() {
        return location;
    }

    public double getAmount() {
        return amount;
    }
}

class LocationHandler implements PackageHandler {
    private PackageHandler nextHandler;

    @Override
    public Optional<String> handle(PackageRequest request) {
        if (request.getLocation().equals("Invalid Location")) {
            return Optional.empty();
        } else {
            return Optional.of("Location Verified");
        }
    }

    @Override
    public PackageHandler nextHandler() {
        return nextHandler;
    }

    @Override
    public void setNextHandler(PackageHandler handler) {
        this.nextHandler = handler;
    }
}

class PaymentHandler implements PackageHandler {
    private PackageHandler nextHandler;

    @Override
    public Optional<String> handle(PackageRequest request) {
        if (request.getAmount() <= 0) {
            return Optional.empty();
        } else {
            return Optional.of("Payment Processed");
        }
    }

    @Override
    public PackageHandler nextHandler() {
        return nextHandler;
    }

    @Override
    public void setNextHandler(PackageHandler handler) {
        this.nextHandler = handler;
    }
}

class DeliveryHandler implements PackageHandler {
    private PackageHandler nextHandler;

    @Override
    public Optional<String> handle(PackageRequest request) {
        return Optional.of("Package Delivered");
    }

    @Override
    public PackageHandler nextHandler() {
        return nextHandler;
    }

    @Override
    public void setNextHandler(PackageHandler handler) {
        this.nextHandler = handler;
    }
}

class PackageDeliveryChain {
    private PackageHandler firstHandler;

    public PackageDeliveryChain(PackageHandler firstHandler) {
        this.firstHandler = firstHandler;
    }

    public String processRequest(PackageRequest request) {
        PackageHandler currentHandler = firstHandler;
        Optional<String> result = Optional.empty();

        while (result.isEmpty() && currentHandler != null) {
            result = currentHandler.handle(request);
            currentHandler = currentHandler.nextHandler();
        }

        if (result.isPresent()) {
            return result.get();
        } else {
            return "Request failed at all handlers.";
        }
    }

    public void setNextHandler(PackageHandler handler) {
        PackageHandler currentHandler = firstHandler;
        while (currentHandler.nextHandler() != null) {
            currentHandler = currentHandler.nextHandler();
        }
        currentHandler.setNextHandler(handler);
    }
}

// Removed AbstractPackageHandler class as it is no longer needed
