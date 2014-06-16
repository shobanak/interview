package com.interview.myshopping;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@ManagedBean
@SessionScoped
public class ShoppingBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	 
	private String name;
	protected String tokenvalue;
	protected String payerid;
	protected String transactionid;
	protected Map<String, String> customConfigurationMap = new HashMap<String, String>();
	
	
		
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTokenvalue() {
		return tokenvalue;
	}
 
	public void setTokenvalue(String token) {
		this.tokenvalue = token;
	}
	
	public String getPayerid() {
		return payerid;
	}
 
	public void setPayerid(String id) {
		this.payerid = id;
	}
		
	public String getTransactionid() {
		return transactionid;
	}
 
	public void setTransactionid(String id) {
		this.transactionid = id;
	}
	
	
	public void makeFirstCall() {
		Logger logger = Logger.getLogger(this.getClass().toString());
		String tokenfromapicall;
		System.out.println("Successfully called the FirstCall()" );		
		PaypalAPICalls apiCall = new PaypalAPICalls();
		tokenfromapicall = apiCall.setExpressCheckout();
		System.out.println("the tokenvalue after making paypalAPI call is " + tokenfromapicall + "\n");
		String redirecturl = "https://www.sandbox.paypal.com/webscr?cmd=_express-checkout&useraction=commit&token="+tokenfromapicall;
		System.out.println("the redirect url is" + redirecturl + "\n");
		try{
		HttpServletResponse response = 
		          (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				response.sendRedirect(redirecturl);
		}catch(IOException e) {logger.severe("Error Message : " + e.getMessage());
		};
	} 
	
	public void makeSecondCall() {
		
		System.out.println("Successfully called the SecondCall(), the token value is :" + getTokenvalue() );	
		PaypalAPICalls apiCall = new PaypalAPICalls();
		String id = apiCall.getExpressCheckoutDetails(getTokenvalue());
		if(id != null) setPayerid(id);
			makeThirdCall();
	} 
	
	public void makeThirdCall() {
		System.out.println("Successfully called the SecondCall(), the token value is :" + getTokenvalue() );	
		PaypalAPICalls apiCall = new PaypalAPICalls();
		String txnid = apiCall.doExpressCheckout(getTokenvalue(), getPayerid());
		if(txnid != null)  setTransactionid(txnid);
	}
	
	public String makeSubsequentcalls() {
		System.out.println("Successfully called the SecondCall(), the token value is :" + getTokenvalue() );	
		PaypalAPICalls apiCall = new PaypalAPICalls();
		String id = apiCall.getExpressCheckoutDetails(getTokenvalue());
		if(id != null) setPayerid(id);
			
		
		System.out.println("Successfully called the ThirdCall(), the payerid is :" + getPayerid() );	
		String txnid = apiCall.doExpressCheckout(getTokenvalue(), getPayerid());
		if(txnid != null)  
			{	setTransactionid(txnid);
				return "txnsuccess" ;
			}
		else
			return "txnfailure" ;  
	}
}


