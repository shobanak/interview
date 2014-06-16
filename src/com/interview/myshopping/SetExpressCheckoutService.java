package com.interview.myshopping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutReq;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutRequestType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.AddressType;
import urn.ebay.apis.eBLBaseComponents.CountryCodeType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.SellerDetailsType;
import urn.ebay.apis.eBLBaseComponents.SetExpressCheckoutRequestDetailsType;
import com.paypal.sdk.exceptions.*;

public class SetExpressCheckoutService {
	
	public SetExpressCheckoutResponseType setExpressCheckout( String returnURL, String cancelURL) throws PayPalException{
		
		Logger logger = Logger.getLogger(this.getClass().toString());
		SetExpressCheckoutRequestDetailsType setExpressCheckoutRequestDetails = new SetExpressCheckoutRequestDetailsType();
		setExpressCheckoutRequestDetails.setReturnURL(returnURL);
		setExpressCheckoutRequestDetails.setCancelURL(cancelURL);
		
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
		AddressType shipToAddress = new AddressType();
		shipToAddress.setStreet1("1 Palm Road");
		shipToAddress.setCityName("Singapore");
		shipToAddress.setStateOrProvince("SG");
		shipToAddress.setCountry(CountryCodeType.SG);
		shipToAddress.setPostalCode("456448");		
		paymentDetails1.setShipToAddress(shipToAddress);
		paymentDetailsList.add(paymentDetails1);
		setExpressCheckoutRequestDetails.setPaymentDetails(paymentDetailsList);
		
		SetExpressCheckoutReq setExpressCheckoutReq = new SetExpressCheckoutReq();
		SetExpressCheckoutRequestType setExpressCheckoutRequest = new SetExpressCheckoutRequestType(
				setExpressCheckoutRequestDetails);
		setExpressCheckoutReq
		.setSetExpressCheckoutRequest(setExpressCheckoutRequest);
				
		// ## Creating service wrapper object
				// Creating service wrapper object to make API call and loading
				// configuration file for your credentials and endpoint
		
			Map<String, String> customConfigurationMap = new HashMap<String, String>();
			customConfigurationMap.put("mode", "sandbox");
			customConfigurationMap.put("acct1.UserName", "shobana.kandaswamy-facilitator_api1.gmail.com"); 
			customConfigurationMap.put("acct1.Password", "1402821586");
			customConfigurationMap.put("acct1.Signature", "AFcWxV21C7fd0v3bYYYRCpSSRl31AmI61i7VL1ZsYpi4gmHGEYFimYh-");
			customConfigurationMap.put("http.GoogleAppEngine", "true");
			
			PayPalAPIInterfaceServiceService service = null;	

			service = new PayPalAPIInterfaceServiceService(customConfigurationMap);
				
				SetExpressCheckoutResponseType setExpressCheckoutResponse = null;
				try {
					// ## Making API call
					// Invoke the appropriate method corresponding to API in service
					// wrapper object
					setExpressCheckoutResponse = service
							.setExpressCheckout(setExpressCheckoutReq);
					System.out.println("successful invocation of SetExpressCheckout \n");
				} catch (Exception e) {
					logger.severe("Error Message : " + e.getMessage());
				}
				return setExpressCheckoutResponse;
		
		
	}

}
