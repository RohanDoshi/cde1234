package model;

public enum ResponseType { 
	NOTICE_MSG_TYPE(ResponseMessageTypeFactory.NOTICE_MSG),
	ERROR_MSG_TYPE(ResponseMessageTypeFactory.ERROR_MSG),
	SUCCESS_MSG_TYPE(ResponseMessageTypeFactory.SUCCESS_MSG),
	MISCELLANEOUS_MSG_TYPE(ResponseMessageTypeFactory.MISCELLANEOUS_MSG);
	
	private String messageTypeValue = null;
	private ResponseType(String messageTypeValue) {
		this.messageTypeValue = messageTypeValue;
	}
	
	public String getMessageTypeValue() { 
		return this.messageTypeValue;
	}
}