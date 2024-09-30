from abc import ABC, abstractmethod

class Handler(ABC):
    """
    Base class for handlers in the chain of responsibility.
    """
    _next_handler = None

    def set_next(self, handler):
        """
        Sets the next handler in the chain.
        """
        self._next_handler = handler

    def handle(self, request):
        """
        Handles the request.
        """
        if self.can_handle(request):
            self.process(request)
        elif self._next_handler:
            self._next_handler.handle(request)

    @abstractmethod
    def can_handle(self, request):
        """
        Checks if the handler can process the request.
        """
        pass

    @abstractmethod
    def process(self, request):
        """
        Processes the request.
        """
        pass

class LocationHandler(Handler):
    """
    Checks if the package can be delivered to the specified location.
    """
    def can_handle(self, request):
        return request['location'] == 'Valid Location'

    def process(self, request):
        print(f"LocationHandler: Package can be delivered to {request['location']}.")

class PaymentHandler(Handler):
    """
    Handles payment processing for the delivery.
    """
    def can_handle(self, request):
        return request['payment_method'] == 'Valid Payment'

    def process(self, request):
        print("PaymentHandler: Payment processed successfully.")

class DeliveryHandler(Handler):
    """
    Manages the actual delivery of the package.
    """
    def can_handle(self, request):
        return True

    def process(self, request):
        print("DeliveryHandler: Package delivered successfully.")

def main():
    # Create the chain of handlers.
    location_handler = LocationHandler()
    payment_handler = PaymentHandler()
    delivery_handler = DeliveryHandler()

    # Link the handlers together.
    location_handler.set_next(payment_handler)
    payment_handler.set_next(delivery_handler)

    # Create a request.
    request = {
        'location': 'Valid Location',
        'payment_method': 'Valid Payment',
    }

    # Process the request.
    location_handler.handle(request)

if __name__ == '__main__':
    main()