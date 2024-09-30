import pytest
from Code import LocationHandler, PaymentHandler, DeliveryHandler, InvalidLocationHandler, InvalidPaymentHandler, WeightHandler

def test_location_handler():
    location_handler = LocationHandler()
    request = {'location': 'Valid Location 1'}
    assert location_handler.can_handle(request) is True
    location_handler.process(request)

    request = {'location': 'Invalid Location'}
    assert location_handler.can_handle(request) is False

def test_payment_handler():
    payment_handler = PaymentHandler()
    request = {'payment_method': 'CreditCard'}
    assert payment_handler.can_handle(request) is True
    payment_handler.process(request)

    request = {'payment_method': 'Cash'}
    assert payment_handler.can_handle(request) is False

def test_delivery_handler():
    delivery_handler = DeliveryHandler()
    request = {}
    assert delivery_handler.can_handle(request) is True
    delivery_handler.process(request)

def test_invalid_location_handler():
    invalid_location_handler = InvalidLocationHandler()
    request = {'location': 'Invalid Location'}
    assert invalid_location_handler.can_handle(request) is True
    invalid_location_handler.process(request)

    request = {'location': 'Valid Location 1'}
    assert invalid_location_handler.can_handle(request) is False

def test_invalid_payment_handler():
    invalid_payment_handler = InvalidPaymentHandler()
    request = {'payment_method': 'Cash'}
    assert invalid_payment_handler.can_handle(request) is True
    invalid_payment_handler.process(request)

    request = {'payment_method': 'CreditCard'}
    assert invalid_payment_handler.can_handle(request) is False

def test_weight_handler():
    weight_handler = WeightHandler()
    request = {'weight': 15}
    assert weight_handler.can_handle(request) is True
    weight_handler.process(request)

    request = {'weight': 5}
    assert weight_handler.can_handle(request) is False

def test_chain_of_responsibility():
    location_handler = LocationHandler()
    payment_handler = PaymentHandler()
    delivery_handler = DeliveryHandler()
    invalid_location_handler = InvalidLocationHandler()
    invalid_payment_handler = InvalidPaymentHandler()
    weight_handler = WeightHandler()

    location_handler.set_next(payment_handler)
    payment_handler.set_next(delivery_handler)
    delivery_handler.set_next(weight_handler)
    weight_handler.set_next(invalid_location_handler)
    invalid_location_handler.set_next(invalid_payment_handler)

    # Valid request
    request = {'location': 'Valid Location 1', 'payment_method': 'CreditCard', 'weight': 5}
    assert location_handler.handle(request) is True
    # Output: LocationHandler: Package can be delivered to Valid Location 1.
    #         PaymentHandler: Payment processed successfully.
    #         DeliveryHandler: Package delivered successfully.

    # Invalid location
    request = {'location': 'Invalid Location', 'payment_method': 'CreditCard', 'weight': 5}
    assert location_handler.handle(request) is True
    # Output: InvalidLocationHandler: Unable to deliver to Invalid Location.

    # Invalid payment
    request = {'location': 'Valid Location 1', 'payment_method': 'Cash', 'weight': 5}
    assert location_handler.handle(request) is True
    # Output: InvalidPaymentHandler: Invalid payment method Cash.

    # Heavy package
    request = {'location': 'Valid Location 1', 'payment_method': 'CreditCard', 'weight': 15}
    assert location_handler.handle(request) is True
    # Output: WeightHandler: Package weight exceeds limit (15 kg).