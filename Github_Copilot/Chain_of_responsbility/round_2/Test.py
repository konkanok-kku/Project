import pytest
from io import StringIO
import sys

from Code import StockHandler, PaymentHandler, ShippingHandler

def capture_output(func, *args, **kwargs):
    old_stdout = sys.stdout
    sys.stdout = StringIO()
    try:
        func(*args, **kwargs)
        return sys.stdout.getvalue()
    finally:
        sys.stdout = old_stdout

def test_stock_handler():
    handler = StockHandler()
    request = {'in_stock': True}
    output = capture_output(handler.handle, request)
    assert "StockHandler: Product is in stock." in output

    request = {'in_stock': False}
    output = capture_output(handler.handle, request)
    assert "StockHandler: Product is not in stock. Stopping the chain." in output


def test_payment_handler():
    handler = PaymentHandler()
    request = {'payment_successful': True}
    output = capture_output(handler.handle, request)
    assert "PaymentHandler: Payment processed successfully." in output

    request = {'payment_successful': False}
    output = capture_output(handler.handle, request)
    assert "PaymentHandler: Payment failed. Stopping the chain." in output


def test_shipping_handler():
    handler = ShippingHandler()
    request = {'shipping_address': '123 Main St'}
    output = capture_output(handler.handle, request)
    assert "ShippingHandler: Shipping the product." in output

    request = {'shipping_address': None}
    output = capture_output(handler.handle, request)
    assert "ShippingHandler: No shipping address provided. Stopping the chain." in output


def test_chain_of_responsibility():
    shipping_handler = ShippingHandler()
    payment_handler = PaymentHandler(successor=shipping_handler)
    stock_handler = StockHandler(successor=payment_handler)

    request = {
        'in_stock': True,
        'payment_successful': True,
        'shipping_address': '123 Main St'
    }
    output = capture_output(stock_handler.handle, request)
    assert "StockHandler: Product is in stock." in output
    assert "PaymentHandler: Payment processed successfully." in output
    assert "ShippingHandler: Shipping the product." in output

    request = {
        'in_stock': True,
        'payment_successful': False,
        'shipping_address': '123 Main St'
    }
    output = capture_output(stock_handler.handle, request)
    assert "StockHandler: Product is in stock." in output
    assert "PaymentHandler: Payment failed. Stopping the chain." in output

    request = {
        'in_stock': False,
        'payment_successful': True,
        'shipping_address': '123 Main St'
    }
    output = capture_output(stock_handler.handle, request)
    assert "StockHandler: Product is not in stock. Stopping the chain." in output
