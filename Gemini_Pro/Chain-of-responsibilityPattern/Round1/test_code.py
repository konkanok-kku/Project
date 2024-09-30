import pytest
from Code import LocationHandler, PaymentHandler, DeliveryHandler

def test_location_handler():
    location_handler = LocationHandler()
    request = {'location': 'Valid Location'}
    assert location_handler.can_handle(request) is True
    location_handler.process(request)

    request = {'location': 'Invalid Location'}
    assert location_handler.can_handle(request) is False

def test_payment_handler():
    payment_handler = PaymentHandler()
    request = {'payment_method': 'Valid Payment'}
    assert payment_handler.can_handle(request) is True
    payment_handler.process(request)

    request = {'payment_method': 'Invalid Payment'}
    assert payment_handler.can_handle(request) is False

def test_delivery_handler():
    delivery_handler = DeliveryHandler()
    request = {}
    assert delivery_handler.can_handle(request) is True
    delivery_handler.process(request)

def test_chain_of_responsibility():
    location_handler = LocationHandler()
    payment_handler = PaymentHandler()
    delivery_handler = DeliveryHandler()

    location_handler.set_next(payment_handler)
    payment_handler.set_next(delivery_handler)

    # Valid request
    request = {'location': 'Valid Location', 'payment_method': 'Valid Payment'}
    location_handler.handle(request)
    # Output: LocationHandler: Package can be delivered to Valid Location.
    #         PaymentHandler: Payment processed successfully.
    #         DeliveryHandler: Package delivered successfully.

    # Invalid location
    request = {'location': 'Invalid Location', 'payment_method': 'Valid Payment'}
    location_handler.handle(request)
    # Output: PaymentHandler: Payment processed successfully.
    #         DeliveryHandler: Package delivered successfully.

    # Invalid payment
    request = {'location': 'Valid Location', 'payment_method': 'Invalid Payment'}
    location_handler.handle(request)
    # Output: DeliveryHandler: Package delivered successfully.