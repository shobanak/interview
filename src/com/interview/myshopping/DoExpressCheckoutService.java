package com.interview.myshopping;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentReq;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentRequestType;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.DoExpressCheckoutPaymentRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.SellerDetailsType;
import com.paypal.sdk.exceptions.*;


public class DoExpressCheckoutService {

	public DoExpressCheckoutPaymentResponseType doExpressCheckout(String token, String payerId,  Map<String, String> customConfigurationMap)throws PayPalException { 
		
		Logger logger = Logger.getLogger(this.getClass().toString());
		// ## DoExpressCheckoutPaymentReq
		DoExpressCheckoutPaymentReq doExpressCheckoutPaymentReq = new DoExpressCheckoutPaymentReq();

		DoExpressCheckoutPaymentRequestDetailsType doExpressCheckoutPaymentRequestDetails = new DoExpressCheckoutPaymentRequestDetailsType();

		// The timestamped token value that was returned in the
		// `SetExpressCheckout` response and passed in the
		// `GetExpressCheckoutDetails` request.
		doExpressCheckoutPaymentRequestDetails.setToken(token);

		// Unique paypal buyer account identification number as returned in
		// `GetExpressCheckoutDetails` Response
		doExpressCheckoutPaymentRequestDetails.setPayerID(payerId);
		// ### Payment Information
		// list of information about the payment
		List<PaymentDetailsType> paymentDetailsList = new ArrayList<PaymentDetailsType>();
		PaymentDetailsType paymentDetails1 = new PaymentDetailsType();
		BasicAmountType orderTotal1 = new BasicAmountType(CurrencyCodeType.SGD,
				"28.00");
		paymentDetails1.setOrderTotal(orderTotal1);
		paymentDetails1.setPaymentAction(PaymentActionCodeType.SALE);
		SellerDetailsType sellerDetails = new SellerDetailsType();
		sellerDetails.setPayPalAccountID("shobana.kandaswamy@gmail.com");
		paymentDetails1.setSellerDetails(sellerDetails);
		paymentDetails1.setPaymentRequestID("PaymentRequest1");
		paymentDetailsList.add(paymentDetails1);
		
		doExpressCheckoutPaymentRequestDetails.setPaymentDetails(paymentDetailsList);
		DoExpressCheckoutPaymentRequestType doExpressCheckoutPaymentRequest = new DoExpressCheckoutPaymentRequestType(
																				doExpressCheckoutPaymentRequestDetails);
		doExpressCheckoutPaymentReq.setDoExpressCheckoutPaymentRequest(doExpressCheckoutPaymentRequest);
		
		// ## Creating service wrapper object
		// Creating service wrapper object to make API call and loading
		// configuration file for your credentials and endpoint
		PayPalAPIInterfaceServiceService service = null;
		service = new PayPalAPIInterfaceServiceService(customConfigurationMap);
		DoExpressCheckoutPaymentResponseType doExpressCheckoutPaymentResponse = null;
		
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			doExpressCheckoutPaymentResponse = service
					.doExpressCheckoutPayment(doExpressCheckoutPaymentReq);
			
			System.out.println("successful invocation of DoExpressCheckout \n");
		}catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}
		return doExpressCheckoutPaymentResponse;
	}
}
