class Handler:
    def __init__(self, successor=None):
        self._successor = successor

    def handle(self, request):
        raise NotImplementedError("Must provide implementation in subclass")


class StockHandler(Handler):
    def handle(self, request):
        if request.get('in_stock', False):
            print("StockHandler: Product is in stock.")
            if self._successor:
                self._successor.handle(request)
        else:
            print("StockHandler: Product is not in stock. Stopping the chain.")


class PaymentHandler(Handler):
    def handle(self, request):
        if request.get('payment_successful', False):
            print("PaymentHandler: Payment processed successfully.")
            if self._successor:
                self._successor.handle(request)
        else:
            print("PaymentHandler: Payment failed. Stopping the chain.")


class ShippingHandler(Handler):
    def handle(self, request):
        if request.get('shipping_address', None):
            print("ShippingHandler: Shipping the product.")
        else:
            print("ShippingHandler: No shipping address provided. Stopping the chain.")
