package model;

public class ResponseMessageAndType {
	private ResponseType responseType = null;
	private String responseMessage = null;
	
	public ResponseMessageAndType() {
		// TODO Auto-generated constructor stub
	}
	
	public ResponseMessageAndType(ResponseType responseType, String responseMessage) { 
		this.responseMessage = responseMessage;
		this.responseType = responseType;
	}
	
	public ResponseType getResponseType() {
		return responseType;
	}
	public void setResponseType(ResponseType responseType) {
		this.responseType = responseType;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
}
