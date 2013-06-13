<jsp:useBean id="s" class="web.Session" scope="session" />
<jsp:setProperty name="s" property="*" />
<%@page import="database_objects.Operator"%>
<%@page import="database.DatabaseAccess"%>
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
		
		
					
		if (s.login(request.getParameter("oprId"),request.getParameter("password"))){		
			if (s.getOperator().getRights() >= 0 && s.getOperator().getRights() <= 4) {
				response.sendRedirect("Home.jsp");
			} else {
				response.sendRedirect("Error.jsp");
			}
		}
		else{ 
			System.out.println("Wrong password");
			response.sendRedirect("Error.jsp"); // Wrong password
		}
		
		%>	
	</body>
</html>