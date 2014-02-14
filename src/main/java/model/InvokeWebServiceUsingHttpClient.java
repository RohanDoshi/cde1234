package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class InvokeWebServiceUsingHttpClient {

	private String firstName = null;
	private String lastName = null;
	private String email = null;
	private byte[] passwd = null;
	private byte[] confirmPasswd = null;
		
	private static final String FIRST_NAME_ATTR = "firstname";
	private static final String LAST_NAME_ATTR = "lastname";
	private static final String EMAIL_ATTR = "email";
	private static final String PASSWORD_ATTR = "password";
	private static final String CONFIRM_PASSWORD_ATTR = "confirmation";
	
	private static final String DOMAIN = ".myopenissues.com";
	private static final String WEB_SRV_ENDPOINT = "http://myopenissues.com/magento/index.php/customer/account/createpost/";
	private static final ResponseMessageAndType EXCEPTION_MESSAGE_AND_TYPE = new ResponseMessageAndType(ResponseType.MISCELLANEOUS_MSG_TYPE, "INTERNAL SERVER ERROR");
	
	public InvokeWebServiceUsingHttpClient() {
		
	}
	
	public InvokeWebServiceUsingHttpClient(String firstName, String lastName, String email, byte[] password, byte[] confirmPassword) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.passwd = password;
		this.confirmPasswd = confirmPassword;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPasswd(byte[] passwd) {
		this.passwd = passwd;
	}

	public void setConfirmPasswd(byte[] confirmPasswd) {
		this.confirmPasswd = confirmPasswd;
	}
	
	public ResponseMessage invokeUserRegistration() throws ClientProtocolException, IOException { 
		
		if(this.firstName == null || this.lastName == null || this.email == null
				|| this.passwd == null || this.confirmPasswd == null) {
			return new ResponseMessage(null, new ResponseMessageAndType(ResponseType.ERROR_MSG_TYPE, "Null Input Provided. Please enter values for all"));
		}
		
		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie cookie = new BasicClientCookie("cookie1", "value1");
		cookie.setVersion(0);
		cookie.setDomain(DOMAIN);
		cookie.setPath("/");
		cookieStore.addCookie(cookie);
		
		CloseableHttpClient client = HttpClients.custom().setRedirectStrategy(new DefaultRedirectStrategy() {
			@Override
			public boolean isRedirected(HttpRequest request,
					HttpResponse response, HttpContext context)
					throws ProtocolException {
				if(response.getStatusLine().getStatusCode() == 302)
					return true;
				else
					return super.isRedirected(request, response, context);
			}
		}).setDefaultCookieStore(cookieStore).build();
		
		HttpPost post = new HttpPost(WEB_SRV_ENDPOINT);
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair(FIRST_NAME_ATTR,firstName));
		nameValuePairs.add(new BasicNameValuePair(LAST_NAME_ATTR,lastName));
		nameValuePairs.add(new BasicNameValuePair(EMAIL_ATTR,email));
		nameValuePairs.add(new BasicNameValuePair(PASSWORD_ATTR, new String(passwd)));
		nameValuePairs.add(new BasicNameValuePair(CONFIRM_PASSWORD_ATTR, new String(confirmPasswd)));
		
		try { 
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			CloseableHttpResponse closeableHttpResponse = client.execute(post);
			ResponseMessage sendToServlet = parseResponse(closeableHttpResponse);
			return sendToServlet;
		} 
		catch (Exception e) {
			return new ResponseMessage(null, EXCEPTION_MESSAGE_AND_TYPE);
		}
		finally {
			post.releaseConnection();
		}
	}
	
	
	private ResponseMessage parseResponse(CloseableHttpResponse closeableHttpResponse) throws IOException {
		String HTMLString = generateHTMLStringFromResponse(closeableHttpResponse);
		ResponseMessageAndType responseMessageAndType = parseHTMLString(HTMLString);
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setResponseMessageAndType(responseMessageAndType);
		responseMessage.setStatusLine(closeableHttpResponse.getStatusLine());
		return responseMessage;
	}
	
	private ResponseMessageAndType parseHTMLString(String html) {
		ResponseType responseMsgType = ResponseType.MISCELLANEOUS_MSG_TYPE;
		String responseMessage = "NO MESSAGE FROM SERVER";
		
		try {
			Document HTMLDoc = Jsoup.parse(html);
			System.out.println(html);
			Elements messageTypeElements = HTMLDoc.select("ul.messages");
			if(messageTypeElements.size() > 0) {
				Element messageElement = messageTypeElements.first();
				if(messageElement.children().size() > 0) {
					Element childElement = messageElement.child(0);
					if(childElement.hasAttr("class")) {
						String msgType = childElement.attr("class");
						responseMsgType = ResponseMessageTypeFactory.getResponseTypeInstance(msgType);
					}
					int count = 0;
					while(childElement.children().size() > 0 && count < 3) {
						childElement = childElement.child(0);
						count++;
					}
					responseMessage = childElement.text();
				}
			}
		} catch(Exception e) {
			ResponseMessageAndType responseMessageAndType = new ResponseMessageAndType();
			responseMessageAndType.setResponseMessage("HTML Parsing Failed");
			responseMessageAndType.setResponseType(ResponseType.ERROR_MSG_TYPE);
			return responseMessageAndType;
		}
		
		return new ResponseMessageAndType(responseMsgType, responseMessage);
	}
	
	private String generateHTMLStringFromResponse(CloseableHttpResponse closeableHttpResponse) throws IOException { 
		HttpEntity entity = closeableHttpResponse.getEntity();
		String line = null;
		StringBuffer HTMLString = new StringBuffer("");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
		while((line=bufferedReader.readLine()) != null) {
			HTMLString.append(line);
		}
		
		return HTMLString.toString();
	}
	
}
