package com.interview.myshopping;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import com.paypal.sdk.exceptions.PayPalException;
import urn.ebay.apis.eBLBaseComponents.ErrorType;
import urn.ebay.apis.eBLBaseComponents.PaymentInfoType;

public class PaypalAPICalls {
	
	protected Map<String, String> customConfigurationMap = new HashMap<String, String>();
	protected String returnURL = "http://1-dot-shobana-kandaswamy.appspot.com/confirmpurchase.jsf";
	protected String cancelURL = "http://1-dot-shobana-kandaswamy.appspot.com/cancelpurchase.jsf";
	
	//protected String returnURL = "http://localhost:8888/confirmpurchase.jsf";
	//protected String cancelURL = "http://localhost:8888/cancelpurchase.jsf";
	private Logger logger = null;
	
	
	public PaypalAPICalls() {
		logger = Logger.getLogger(this.getClass().toString());
		setCustomConfigurationMap();
	}

	public void setCustomConfigurationMap() {		
		//Merchant Details
		customConfigurationMap.put("mode", "sandbox");
		customConfigurationMap.put("acct1.UserName", "shobana.kandaswamy-facilitator_api1.gmail.com"); 
		customConfigurationMap.put("acct1.Password", "1402821586");
		customConfigurationMap.put("acct1.Signature", "AFcWxV21C7fd0v3bYYYRCpSSRl31AmI61i7VL1ZsYpi4gmHGEYFimYh-");
		customConfigurationMap.put("http.GoogleAppEngine", "true");
	}
	
	public void makePaypalAPICalls() {
		
		Logger logger = Logger.getLogger(this.getClass().toString());
		setCustomConfigurationMap();
		SetExpressCheckoutService setExpressCheckoutService = new SetExpressCheckoutService();
		GetExpressCheckoutDetailsService  getExpressCheckoutDetailsService = new GetExpressCheckoutDetailsService();
		SetExpressCheckoutResponseType setExpressCheckoutResponse = null;
		//GetExpressCheckoutDetailsResponseType getExpressCheckoutDetailsResponse = null;
		
		try {
			
			
		// ## Make API call to invoke setExpressCheckout
		setExpressCheckoutResponse = 
					setExpressCheckoutService.setExpressCheckout(returnURL,cancelURL);
		if (setExpressCheckoutResponse.getAck().getValue()
					.equalsIgnoreCase("success"))
		{
			
					getExpressCheckoutDetailsService.getExpressCheckoutDetails(setExpressCheckoutResponse.getToken(), customConfigurationMap);
		}
		else {
			List<ErrorType> errorList = setExpressCheckoutResponse.getErrors();
			logger.severe("API Error Message : "
					+ errorList.get(0).getLongMessage());
		}
		
		} catch(PayPalException e) { logger.severe("Error Message : " + e.getMessage());;}
	}
	
	public String setExpressCheckout() {
		
		SetExpressCheckoutService setExpressCheckoutService = new SetExpressCheckoutService();		
		SetExpressCheckoutResponseType setExpressCheckoutResponse = null;
		String tokenvalue = null;
		try {
			
			
			// ## Make API call to invoke setExpressCheckout
			setExpressCheckoutResponse = 
						setExpressCheckoutService.setExpressCheckout(returnURL,cancelURL);
			if (setExpressCheckoutResponse.getAck().getValue()
						.equalsIgnoreCase("success"))
			{
				System.out.println("the tokenvalue in PaypalAPICalls is :" + setExpressCheckoutResponse.getToken());
				tokenvalue =  setExpressCheckoutResponse.getToken();
			}
			else {
				List<ErrorType> errorList = setExpressCheckoutResponse.getErrors();
				logger.severe("API Error Message : "
						+ errorList.get(0).getLongMessage());
			}
			
			} catch(PayPalException e) { logger.severe("Error Message : " + e.getMessage());;}
			return tokenvalue;
	}
	
	public String getExpressCheckoutDetails(String token){
		GetExpressCheckoutDetailsService  getExpressCheckoutDetailsService = new GetExpressCheckoutDetailsService();
		GetExpressCheckoutDetailsResponseType getExpressCheckoutDetailsResponse = null;
		String payerId = null;
		try {
			
			// ## Make API call to invoke setExpressCheckout			
			getExpressCheckoutDetailsResponse = getExpressCheckoutDetailsService.getExpressCheckoutDetails(token, customConfigurationMap);	
			
			// ### Success values
			if (getExpressCheckoutDetailsResponse.getAck().getValue()
					.equalsIgnoreCase("success")) {

				// Unique PayPal Customer Account identification number. This
				// value will be null unless you authorize the payment by
				// redirecting to PayPal after `SetExpressCheckout` call.
				logger.info("the PayerID in PaypalAPICalls is : "
						+ getExpressCheckoutDetailsResponse
								.getGetExpressCheckoutDetailsResponseDetails()
								.getPayerInfo().getPayerID());
				payerId = getExpressCheckoutDetailsResponse.getGetExpressCheckoutDetailsResponseDetails().getPayerInfo().getPayerID();
			}
			// ### Error Values			
			else {
				List<ErrorType> errorList = getExpressCheckoutDetailsResponse
						.getErrors();
				logger.severe("API Error Message : "
						+ errorList.get(0).getLongMessage());
			}
		}
		catch(PayPalException e){logger.severe("Error Message : " + e.getMessage());;}
		return payerId;
	}
	
	public String doExpressCheckout(String token, String payerid){ 
		DoExpressCheckoutService doExpressCheckoutService = new DoExpressCheckoutService();	
		DoExpressCheckoutPaymentResponseType DoExpressCheckoutResponse = null; 
		String transactionId = null;
		
		try {
			
			// ## Make API call to invoke setExpressCheckout			
			DoExpressCheckoutResponse = doExpressCheckoutService.doExpressCheckout(token, payerid, customConfigurationMap);	
			
			// ### Success values
			if (DoExpressCheckoutResponse.getAck().getValue()
					.equalsIgnoreCase("success")) {

				// Transaction identification number of the transaction that was
				// created.
				// This field is only returned after a successful transaction
				// for DoExpressCheckout has occurred.
				if (DoExpressCheckoutResponse.getDoExpressCheckoutPaymentResponseDetails()
															.getPaymentInfo() != null) {
					Iterator<PaymentInfoType> paymentInfoIterator = DoExpressCheckoutResponse.getDoExpressCheckoutPaymentResponseDetails()
							.getPaymentInfo().iterator();
					while (paymentInfoIterator.hasNext()) {
						PaymentInfoType paymentInfo = paymentInfoIterator.next();
						logger.info("Transaction ID : "	+ paymentInfo.getTransactionID());
						transactionId = paymentInfo.getTransactionID();
					}
				}
				
			}
			// ### Error Values
			// Access error values from error list using getter methods
			else {
				List<ErrorType> errorList = DoExpressCheckoutResponse.getErrors();
				logger.severe("API Error Message : "
						+ errorList.get(0).getLongMessage());
			}
		}catch(PayPalException e){logger.severe("Error Message : " + e.getMessage());;}
		return transactionId;
	
}
}
