<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register at openissues.com</title>
<script type="text/javascript">
function validateInput() {
	if(document.getElementById("fNameId").value == "" || document.getElementById("lNameId").value == "" ||
			document.getElementById("emailId").value == "" || document.getElementById("passwdId").value == "" ||
			document.getElementById("confirmPasswdId").value == "") {
		alert("Please enter all values !");
		return false;
	}
	return true;
}
</script>
</head>
<body>
<% if(request.getAttribute("messType") == null) { %>
<h3 align="center"> Welcome to OpenIssues Registration Form</h3>
<form action="registerUserOnOpenIssues" method="post" onsubmit="return validateInput()">
<table border="0" align="center">
	<tr><td><b>First Name:</b></td><td><input type="text" name="fName" id="fNameId" value=""></td></tr>
	<tr><td><b>Last Name:</b></td><td><input type="text" name="lName" id="lNameId" value=""></td></tr>
	<tr><td><b>Email Address:</b></td><td><input type="text" name="email" id="emailId" value=""></td></tr>
	<tr><td><b>Password:</b></td><td><input type="password" name="passwd" id="passwdId" value=""></td></tr>
	<tr><td><b>Confirm Password:</b></td><td><input type="password" name="confirmPasswd" id="confirmPasswdId" value=""></td></tr>
	<tr> <td align="center"> <input align="right" type="submit" value="Submit" onclick="validateInput()"> </td></tr>
</table>
</form>
<% } else {
	if(request.getAttribute("messType").toString().equals("success-msg")) { %> 
		<h3 align="center"> <% out.println(request.getAttribute("messValue")); %> </h3>
<% } else { %>
	
	<h3 align="center"> Welcome to OpenIssues Registration Form</h3>
	<h6 align="center"> <font color="Red"><% out.println(request.getAttribute("messValue")); %></font></h6>
	<form action="registerUserOnOpenIssues" method="post" onsubmit="return validateInput()">
	<table border="0" align="center">
		<tr><td><b>First Name:</b></td><td><input type="text" name="fName" id="fNameId"></td></tr>
		<tr><td><b>Last Name:</b></td><td><input type="text" name="lName" id="lNameId"></td></tr>
		<tr><td><b>Email Address:</b></td><td><input type="text" name="email" id="emailId"></td></tr>
		<tr><td><b>Password:</b></td><td><input type="password" name="passwd" id="passwdId"></td></tr>
		<tr><td><b>Confirm Password:</b></td><td><input type="password" name="confirmPasswd" id="confirmPasswdId"></td></tr>
		<tr><td align="center"> <input align="right" type="submit" value="Submit"> </td></tr>
	</table>
	</form>
<% } %>
<% } %>
</body>
</html>