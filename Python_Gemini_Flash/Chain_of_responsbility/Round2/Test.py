import pytest
from Code import LocationHandler, PaymentHandler, DeliveryHandler, deliver_package

def test_location_handler_valid():
  request = {"location": "Customer Address"}
  assert deliver_package(request)

def test_location_handler_invalid():
  request = {"location": "Restricted Area"}
  assert not deliver_package(request)

def test_payment_handler_not_processed():
  request = {"location": "Customer Address", "payment_processed": False}
  assert deliver_package(request)

def test_payment_handler_processed():
  request = {"location": "Customer Address", "payment_processed": True}
  assert deliver_package(request)

def test_delivery_handler_all_processed():
  request = {"location": "Customer Address", "payment_processed": True}
  assert deliver_package(request)

def test_delivery_handler_not_processed():
  request = {"location": "Customer Address", "payment_processed": False}
  assert not deliver_package(request)

def test_chain_traversal():
  request = {"location": "Customer Address"}
  first_handler = LocationHandler(
      PaymentHandler(DeliveryHandler())
  )
  assert not first_handler.handle(request)  # LocationHandler updates request and passes to PaymentHandler
  assert PaymentHandler(DeliveryHandler()).handle(request)  # PaymentHandler passes to DeliveryHandler

# Test updating request object
def test_location_handler_updates_request():
  request = {"location": "Customer Address"}
  deliver_package(request)
  assert request.get("location_verified") == True  # Verify location_verified is added
