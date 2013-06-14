<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login</title>
	</head>
	<body>
	<%
		String message = null;
	
		if (request.getParameter("logout") != null) {
			
			// Log out
			
			s.logout();
			message = "You are now logged out";
			
		} else if (s.loggedIn()) {
			
			// Already logged in
			
			response.sendRedirect("Home.jsp");
			
		} else if (request.getMethod().equalsIgnoreCase("POST")) {
			
			// Log in
			
			if (s.login(request.getParameter("id"), request.getParameter("password"))){		
				response.sendRedirect("Home.jsp");
			} else {
				s.logout();
				message = "User id or password is wrong.";
			}
			
		}
	%>
		<center>
			<h2>Login</h2>
			<form method="post">
				<table>
					<tr>
						<td>User id:</td>
						<td><input type="text" name="id"></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><input type="password" name="password"></td>
					</tr>
				</table>
				<br>
				<input type="submit" value="Login">
			</form>
			<%
				if (message != null) {
			%>
			<br>
			<span style="color: red"><%= message %></span>
			<%
				}
			%>
		</center>
	</body>
</html>