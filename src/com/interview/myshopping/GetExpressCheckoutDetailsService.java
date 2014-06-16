package com.interview.myshopping;


import java.util.Map;
import java.util.logging.Logger;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsReq;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsRequestType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import com.paypal.sdk.exceptions.*;

public class GetExpressCheckoutDetailsService {
	
	public GetExpressCheckoutDetailsResponseType getExpressCheckoutDetails(String token, Map<String, String> customConfigurationMap) throws PayPalException{
		Logger logger = Logger.getLogger(this.getClass().toString());
		GetExpressCheckoutDetailsReq getExpressCheckoutDetailsReq = new GetExpressCheckoutDetailsReq();
		GetExpressCheckoutDetailsRequestType getExpressCheckoutDetailsRequest = new GetExpressCheckoutDetailsRequestType(token);
		getExpressCheckoutDetailsReq
		.setGetExpressCheckoutDetailsRequest(getExpressCheckoutDetailsRequest);
		
		PayPalAPIInterfaceServiceService service = null;
		service = new PayPalAPIInterfaceServiceService(customConfigurationMap);
		
		GetExpressCheckoutDetailsResponseType getExpressCheckoutDetailsResponse = null;
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			 getExpressCheckoutDetailsResponse = service
					.getExpressCheckoutDetails(getExpressCheckoutDetailsReq);
			 System.out.println("successful invocation of GetExpressCheckout \n");
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}
		
		return getExpressCheckoutDetailsResponse;

	}
		
	}


