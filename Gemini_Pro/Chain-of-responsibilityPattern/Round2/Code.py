from abc import ABC, abstractmethod
from typing import Dict

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

    def handle(self, request: Dict) -> bool:
        """
        Handles the request.
        """
        if self.can_handle(request):
            self.process(request)
            return True
        elif self._next_handler:
            return self._next_handler.handle(request)
        else:
            return False

    @abstractmethod
    def can_handle(self, request: Dict) -> bool:
        """
        Checks if the handler can process the request.
        """
        pass

    @abstractmethod
    def process(self, request: Dict):
        """
        Processes the request.
        """
        pass

class LocationHandler(Handler):
    """
    Checks if the package can be delivered to the specified location.
    """
    def can_handle(self, request: Dict) -> bool:
        return request['location'] in ['Valid Location 1', 'Valid Location 2']

    def process(self, request: Dict):
        print(f"LocationHandler: Package can be delivered to {request['location']}.")

class PaymentHandler(Handler):
    """
    Handles payment processing for the delivery.
    """
    def can_handle(self, request: Dict) -> bool:
        return request['payment_method'] in ['CreditCard', 'PayPal']

    def process(self, request: Dict):
        print("PaymentHandler: Payment processed successfully.")

class DeliveryHandler(Handler):
    """
    Manages the actual delivery of the package.
    """
    def can_handle(self, request: Dict) -> bool:
        return True

    def process(self, request: Dict):
        print("DeliveryHandler: Package delivered successfully.")

class InvalidLocationHandler(Handler):
    """
    Handles requests with invalid locations.
    """
    def can_handle(self, request: Dict) -> bool:
        return request['location'] not in ['Valid Location 1', 'Valid Location 2']

    def process(self, request: Dict):
        print(f"InvalidLocationHandler: Unable to deliver to {request['location']}.")

class InvalidPaymentHandler(Handler):
    """
    Handles requests with invalid payment methods.
    """
    def can_handle(self, request: Dict) -> bool:
        return request['payment_method'] not in ['CreditCard', 'PayPal']

    def process(self, request: Dict):
        print(f"InvalidPaymentHandler: Invalid payment method {request['payment_method']}.")


def main():
    # Create the chain of handlers.
    location_handler = LocationHandler()
    payment_handler = PaymentHandler()
    delivery_handler = DeliveryHandler()
    invalid_location_handler = InvalidLocationHandler()
    invalid_payment_handler = InvalidPaymentHandler()

    # Link the handlers together.
    location_handler.set_next(payment_handler)
    payment_handler.set_next(delivery_handler)
    delivery_handler.set_next(invalid_location_handler)
    invalid_location_handler.set_next(invalid_payment_handler)

    # Create requests.
    valid_request = {
        'location': 'Valid Location 1',
        'payment_method': 'CreditCard',
    }
    invalid_location_request = {
        'location': 'Invalid Location',
        'payment_method': 'CreditCard',
    }
    invalid_payment_request = {
        'location': 'Valid Location 1',
        'payment_method': 'Cash',
    }

    # Process requests.
    location_handler.handle(valid_request)
    print("-"*20)
    location_handler.handle(invalid_location_request)
    print("-"*20)
    location_handler.handle(invalid_payment_request)

if __name__ == '__main__':
    main()