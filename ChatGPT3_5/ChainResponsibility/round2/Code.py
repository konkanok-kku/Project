class Handler:
    def __init__(self):
        self.next_handler = None

    def set_next(self, handler):
        self.next_handler = handler
        return handler

    def handle(self, request):
        if self.next_handler:
            return self.next_handler.handle(request)
        return "Request could not be processed by any handler."

class LocationHandler(Handler):
    def handle(self, request):
        if request.get('location') == 'valid_location':
            print("LocationHandler: Package can be delivered to this location.")
            return "Location verified"
        print("LocationHandler: Passing to the next handler...")
        return super().handle(request)

class PaymentHandler(Handler):
    def handle(self, request):
        if request.get('payment') == 'paid':
            print("PaymentHandler: Payment has been processed successfully.")
            return "Payment processed"
        print("PaymentHandler: Passing to the next handler...")
        return super().handle(request)

class DeliveryHandler(Handler):
    def handle(self, request):
        if request.get('delivery_ready') == True:
            print("DeliveryHandler: Package is being delivered.")
            return "Package delivered"
        print("DeliveryHandler: Passing to the next handler...")
        return super().handle(request)

# Example usage
if __name__ == "__main__":
    location_handler = LocationHandler()
    payment_handler = PaymentHandler()
    delivery_handler = DeliveryHandler()

    location_handler.set_next(payment_handler).set_next(delivery_handler)

    # Valid request
    request_valid = {'location': 'valid_location', 'payment': 'paid', 'delivery_ready': True}
    result = location_handler.handle(request_valid)
    print(f"Result: {result}")  # Output: "Location verified"

    # Invalid location request
    request_invalid = {'location': 'invalid_location', 'payment': 'unpaid', 'delivery_ready': False}
    result = location_handler.handle(request_invalid)
    print(f"Result: {result}")  # Output: "Request could not be processed by any handler."
