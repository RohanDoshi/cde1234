package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.InvokeWebServiceUsingHttpClient;
import model.ResponseMessage;
import model.ResponseType;

/**
 * Servlet implementation class RegisterUserUsingWebServiceServlet
 */
public class RegisterUserUsingWebServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String FIRST_NAME = "fName";
    private static final String LAST_NAME = "lName";
    private static final String EMAIL_ADDRESS = "email";
    private static final String PASSWORD = "passwd";
    private static final String CONFIRM_PASSWORD = "confirmPasswd";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterUserUsingWebServiceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String firstName = request.getParameter(FIRST_NAME);
		String lastName = request.getParameter(LAST_NAME);
		String email = request.getParameter(EMAIL_ADDRESS);
		byte passwdBytes[] = request.getParameter(PASSWORD) != null ? request.getParameter(PASSWORD).getBytes() : null;
		byte confirmPasswrdBytes[] = request.getParameter(CONFIRM_PASSWORD) != null ? request.getParameter(CONFIRM_PASSWORD).getBytes() : null;
		
		InvokeWebServiceUsingHttpClient webServiceUsingHttpClient = new InvokeWebServiceUsingHttpClient(firstName, lastName, email, passwdBytes, confirmPasswrdBytes);
		ResponseMessage responseMessage = webServiceUsingHttpClient.invokeUserRegistration();
		System.out.println(responseMessage.getResponseMessageAndType().getResponseMessage());
		System.out.println(responseMessage.getResponseMessageAndType().getResponseType());
		if(responseMessage != null && responseMessage.getResponseMessageAndType() != null) {
			request.setAttribute("messType", responseMessage.getResponseMessageAndType().getResponseType().getMessageTypeValue());
			request.setAttribute("messValue", responseMessage.getResponseMessageAndType().getResponseMessage());
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
		
	}

}
