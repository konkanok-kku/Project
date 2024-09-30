class Handler:
    def __init__(self):
        self.next_handler = None

    def set_next(self, handler):
        self.next_handler = handler
        return handler

    def handle(self, request):
        if self.next_handler:
            return self.next_handler.handle(request)
        return None

class LocationHandler(Handler):
    def handle(self, request):
        if request.get('location') == 'valid_location':
            return "Location verified"
        return super().handle(request)

class PaymentHandler(Handler):
    def handle(self, request):
        if request.get('payment') == 'paid':
            return "Payment processed"
        return super().handle(request)

class DeliveryHandler(Handler):
    def handle(self, request):
        if request.get('delivery_ready') == True:
            return "Package delivered"
        return super().handle(request)

# Example usage
if __name__ == "__main__":
    location_handler = LocationHandler()
    payment_handler = PaymentHandler()
    delivery_handler = DeliveryHandler()

    location_handler.set_next(payment_handler).set_next(delivery_handler)

    request = {'location': 'valid_location', 'payment': 'paid', 'delivery_ready': True}
    result = location_handler.handle(request)
    print(result)  # Output should be "Location verified"
