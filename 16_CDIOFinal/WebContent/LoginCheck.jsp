<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>LoginCheck</title>
	</head>
	<body>
		<% 
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		if (username.equals("Mathias") && password.equals("123")){
			session.setAttribute("username",username);
			session.setAttribute("type", "admin");
			response.sendRedirect("Home.jsp");
		}
		else if (username.equals("Khaan") && password.equals("123")){
			session.setAttribute("username",username);
			session.setAttribute("type", "pharmaceut");
			response.sendRedirect("Home.jsp");
		}
		else
			response.sendRedirect("Error.jsp");
		%>	
	</body>
</html>