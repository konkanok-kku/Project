import pytest
from Code import LocationHandler, PaymentHandler, DeliveryHandler

@pytest.fixture
def setup_chain():
    location_handler = LocationHandler()
    payment_handler = PaymentHandler()
    delivery_handler = DeliveryHandler()

    location_handler.set_next(payment_handler).set_next(delivery_handler)
    return location_handler

def test_location_handler_valid(setup_chain):
    request = {'location': 'valid_location', 'payment': 'unpaid', 'delivery_ready': False}
    result = setup_chain.handle(request)
    assert result == "Location verified"

def test_payment_handler_valid(setup_chain):
    request = {'location': 'invalid_location', 'payment': 'paid', 'delivery_ready': False}
    result = setup_chain.handle(request)
    assert result == "Payment processed"

def test_delivery_handler_valid(setup_chain):
    request = {'location': 'invalid_location', 'payment': 'unpaid', 'delivery_ready': True}
    result = setup_chain.handle(request)
    assert result == "Package delivered"

def test_no_handler_processes_request(setup_chain):
    request = {'location': 'invalid_location', 'payment': 'unpaid', 'delivery_ready': False}
    result = setup_chain.handle(request)
    assert result is None
