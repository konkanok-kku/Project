package Chain_of_responsbility.Round1;

public class Code {

    public interface PackageHandler {
        void handleRequest(Package pkg);

        void setNextHandler(PackageHandler nextHandler);
    }

    public static class Package {
        public String location;
        public boolean paymentComplete;
        public boolean delivered;

        public Package(String location, boolean paymentComplete) {
            this.location = location;
            this.paymentComplete = paymentComplete;
            this.delivered = false;
        }
    }

    public static class LocationHandler implements PackageHandler {
        private PackageHandler nextHandler;
        private final String[] supportedLocations = {"City A", "City B", "City C"};

        @Override
        public void handleRequest(Package pkg) {
            if (isValidLocation(pkg.location)) {
                System.out.println("LocationHandler: Location validated.");
                if (nextHandler != null) {
                    nextHandler.handleRequest(pkg);
                } else {
                    System.out.println("LocationHandler: No further processing needed.");
                }
            } else {
                System.out.println("LocationHandler: Invalid location. Cannot process package.");
            }
        }

        @Override
        public void setNextHandler(PackageHandler nextHandler) {
            this.nextHandler = nextHandler;
        }

        private boolean isValidLocation(String location) {
            for (String supportedLocation : supportedLocations) {
                if (supportedLocation.equals(location)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class PaymentHandler implements PackageHandler {
        private PackageHandler nextHandler;

        @Override
        public void handleRequest(Package pkg) {
            if (pkg.paymentComplete) {
                System.out.println("PaymentHandler: Payment already complete.");
                if (nextHandler != null) {
                    nextHandler.handleRequest(pkg);
                } else {
                    System.out.println("PaymentHandler: No further processing needed.");
                }
            } else {
                System.out.println("PaymentHandler: Payment incomplete. Cannot process package.");
            }
        }

        @Override
        public void setNextHandler(PackageHandler nextHandler) {
            this.nextHandler = nextHandler;
        }
    }

    public static class DeliveryHandler implements PackageHandler {

        @Override
        public void handleRequest(Package pkg) {
            System.out.println("DeliveryHandler: Delivering the package.");
            pkg.delivered = true;
        }

        @Override
        public void setNextHandler(PackageHandler nextHandler) {
            // Not applicable for the last handler in the chain
        }
    }

    public static void main(String[] args) {
        LocationHandler locationHandler = new LocationHandler();
        PaymentHandler paymentHandler = new PaymentHandler();
        DeliveryHandler deliveryHandler = new DeliveryHandler();

        locationHandler.setNextHandler(paymentHandler);
        paymentHandler.setNextHandler(deliveryHandler);

        Package pkg1 = new Package("City A", true);
        locationHandler.handleRequest(pkg1);

        System.out.println("-------");

        Package pkg2 = new Package("City D", true);
        locationHandler.handleRequest(pkg2);

        System.out.println("-------");

        Package pkg3 = new Package("City B", false);
        locationHandler.handleRequest(pkg3);
    }
}
