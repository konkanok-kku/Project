class Handler:
  """Abstract base class for handlers in the chain"""
  def __init__(self, next_handler=None):
    self.next_handler = next_handler

  def handle(self, request):
    """Processes the request or passes it to the next handler"""
    processed = self._handle(request)
    if not processed and self.next_handler:
      processed = self.next_handler.handle(request)
    return processed

  def _handle(self, request):
    """Concrete implementation of request processing for each handler"""
    raise NotImplementedError("Subclass must implement _handle")

class LocationHandler(Handler):
  """Verifies if the delivery can be made to the specified location"""
  def _handle(self, request):
    if request.location == "Restricted Area":
      print(f"Delivery cannot be made to {request.location}")
      return False
    else:
      print(f"Delivery location {request.location} is valid.")
      return True

class PaymentHandler(Handler):
  """Handles payment processing for the delivery"""
  def _handle(self, request):
    if not request.payment_processed:
      print("Processing payment...")
      # Simulate payment processing
      request.payment_processed = True
      print("Payment successful.")
      return True
    else:
      print("Payment already processed.")
      return True

class DeliveryHandler(Handler):
  """Manages the actual delivery of the package"""
  def _handle(self, request):
    if request.location_verified and request.payment_processed:
      print(f"Delivering package to {request.location}")
      return True
    else:
      print("Delivery cannot proceed yet.")
      return False

# Example usage
def deliver_package(request):
  """Initializes the chain of responsibility and starts the delivery process"""
  first_handler = LocationHandler(
      PaymentHandler(DeliveryHandler())
  )
  return first_handler.handle(request)

# Sample request data
request = {
  "location": "Customer Address",
  "payment_processed": False
}

deliver_package(request)
