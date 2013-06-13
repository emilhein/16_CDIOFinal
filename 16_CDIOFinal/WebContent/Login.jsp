<jsp:useBean id="s" class="web.Session" scope="session" />
<jsp:setProperty name="s" property="*" />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login</title>
	</head>
	<body>
		<h1>Login page</h1>
		<center>
			<h2>Login Details</h2>
			<form action="LoginCheck.jsp" method="post">
				<br/>User id: <input type="text" name="oprId">
				<br/><br/>Password: <input type="password" name="password">
				<br/><br/><input type="submit" value="Login">
			</form>
		</center>
	</body>
</html>