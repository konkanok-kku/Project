import pytest
from Code import LocationHandler, PaymentHandler, DeliveryHandler

@pytest.fixture
def setup_chain():
    """Sets up the handler chain for testing."""
    location_handler = LocationHandler()
    payment_handler = PaymentHandler()
    delivery_handler = DeliveryHandler()

    location_handler.set_next(payment_handler).set_next(delivery_handler)
    return location_handler


def test_location_handler_valid(setup_chain):
    """Test if LocationHandler processes valid location."""
    request = {'location': 'valid_location', 'payment': 'unpaid', 'delivery_ready': False}
    result = setup_chain.handle(request)
    assert result == "Location verified"


def test_location_handler_missing(setup_chain):
    """Test if LocationHandler returns error when location is missing."""
    request = {'payment': 'unpaid', 'delivery_ready': False}
    result = setup_chain.handle(request)
    assert result == "Location missing"


def test_payment_handler_valid(setup_chain):
    """Test if PaymentHandler processes valid payment."""
    request = {'location': 'invalid_location', 'payment': 'paid', 'delivery_ready': False}
    result = setup_chain.handle(request)
    assert result == "Payment processed"


def test_payment_handler_missing(setup_chain):
    """Test if PaymentHandler returns error when payment info is missing."""
    request = {'location': 'invalid_location', 'delivery_ready': False}
    result = setup_chain.handle(request)
    assert result == "Payment missing"


def test_delivery_handler_valid(setup_chain):
    """Test if DeliveryHandler processes valid delivery."""
    request = {'location': 'invalid_location', 'payment': 'unpaid', 'delivery_ready': True}
    result = setup_chain.handle(request)
    assert result == "Package delivered"


def test_delivery_handler_missing(setup_chain):
    """Test if DeliveryHandler returns error when delivery status is missing."""
    request = {'location': 'invalid_location', 'payment': 'unpaid'}
    result = setup_chain.handle(request)
    assert result == "Delivery status missing"


def test_no_handler_processes_request(setup_chain):
    """Test if no handler can process the invalid request."""
    request = {'location': 'invalid_location', 'payment': 'unpaid', 'delivery_ready': False}
    result = setup_chain.handle(request)
    assert result == "Request could not be processed by any handler."


def test_full_chain_processes_valid_request(setup_chain):
    """Test if the entire chain handles a fully valid request."""
    request = {'location': 'valid_location', 'payment': 'paid', 'delivery_ready': True}
    result = setup_chain.handle(request)
    assert result == "Location verified"


def test_partial_chain_request(setup_chain):
    """Test if one handler in the middle of the chain processes the request."""
    request = {'location': 'invalid_location', 'payment': 'paid', 'delivery_ready': False}
    result = setup_chain.handle(request)
    assert result == "Payment processed"
