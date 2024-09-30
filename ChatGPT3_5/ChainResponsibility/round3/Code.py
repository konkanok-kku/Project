class Handler:
    """Base Handler class that defines the interface for handling requests."""
    def __init__(self):
        self.next_handler = None

    def set_next(self, handler):
        """Sets the next handler in the chain."""
        self.next_handler = handler
        return handler

    def handle(self, request):
        """Tries to handle the request. If unable, passes to the next handler."""
        if self.next_handler:
            return self.next_handler.handle(request)
        return "Request could not be processed by any handler."


class LocationHandler(Handler):
    """Handles location validation for package delivery."""
    def handle(self, request):
        location = request.get('location')
        if location == 'valid_location':
            print("LocationHandler: Package can be delivered to this location.")
            return "Location verified"
        elif location is None:
            print("LocationHandler: No location provided.")
            return "Location missing"
        print("LocationHandler: Passing to the next handler...")
        return super().handle(request)


class PaymentHandler(Handler):
    """Handles payment processing for the package."""
    def handle(self, request):
        payment_status = request.get('payment')
        if payment_status == 'paid':
            print("PaymentHandler: Payment has been processed successfully.")
            return "Payment processed"
        elif payment_status is None:
            print("PaymentHandler: No payment information provided.")
            return "Payment missing"
        print("PaymentHandler: Passing to the next handler...")
        return super().handle(request)


class DeliveryHandler(Handler):
    """Handles the actual package delivery."""
    def handle(self, request):
        delivery_ready = request.get('delivery_ready')
        if delivery_ready:
            print("DeliveryHandler: Package is being delivered.")
            return "Package delivered"
        elif delivery_ready is None:
            print("DeliveryHandler: Delivery status unknown.")
            return "Delivery status missing"
        print("DeliveryHandler: Passing to the next handler...")
        return super().handle(request)


# Example usage
if __name__ == "__main__":
    # Chain setup
    location_handler = LocationHandler()
    payment_handler = PaymentHandler()
    delivery_handler = DeliveryHandler()

    location_handler.set_next(payment_handler).set_next(delivery_handler)

    # Test cases
    request_valid = {'location': 'valid_location', 'payment': 'paid', 'delivery_ready': True}
    result = location_handler.handle(request_valid)
    print(f"Result: {result}")  # Output: "Location verified"

    request_invalid = {'location': 'invalid_location', 'payment': 'unpaid', 'delivery_ready': False}
    result = location_handler.handle(request_invalid)
    print(f"Result: {result}")  # Output: "Request could not be processed by any handler."
