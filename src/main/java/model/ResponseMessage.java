package model;

import org.apache.http.StatusLine;


public class ResponseMessage {
	
	private StatusLine statusLine = null;
	private ResponseMessageAndType responseMessageAndType = null;

	public ResponseMessage() {
		// TODO Auto-generated constructor stub
	}
	
	public ResponseMessage(StatusLine statusLine, ResponseMessageAndType responseMessageAndType) { 
		this.statusLine = statusLine;
		this.responseMessageAndType = responseMessageAndType;
	}
	
	public StatusLine getStatusLine() {
		return statusLine;
	}
	public void setStatusLine(StatusLine statusLine) {
		this.statusLine = statusLine;
	}
	public ResponseMessageAndType getResponseMessageAndType() {
		return responseMessageAndType;
	}
	public void setResponseMessageAndType(
			ResponseMessageAndType responseMessageAndType) {
		this.responseMessageAndType = responseMessageAndType;
	}
	
}



class ResponseMessageTypeFactory {
	

	public static final String NOTICE_MSG = "notice-msg";
	public static final String ERROR_MSG = "error-msg";
	public static final String SUCCESS_MSG = "success-msg";
	public static final String MISCELLANEOUS_MSG = "miscellaneous-msg";
	
	private ResponseMessageTypeFactory() {
	}
	
	public static ResponseType getResponseTypeInstance(String responseMessage) {
		if(responseMessage == null)
			return ResponseType.MISCELLANEOUS_MSG_TYPE;
		
		else if(responseMessage.equals(NOTICE_MSG))
			return ResponseType.NOTICE_MSG_TYPE;
		
		else if(responseMessage.equals(SUCCESS_MSG))
			return ResponseType.SUCCESS_MSG_TYPE;
		
		else if(responseMessage.equals(ERROR_MSG))
			return ResponseType.ERROR_MSG_TYPE;
		
		else
			return ResponseType.MISCELLANEOUS_MSG_TYPE;
	}
}
