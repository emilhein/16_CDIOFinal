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
		DatabaseAccess db = new DatabaseAccess();
		
		String oprId=request.getParameter("oprId");
		String password=request.getParameter("password");
		
		int id = Integer.getInteger(oprId);		
		database_objects.Operator operator = db.getOperator(id);
		
		
		
		if (oprId.equals("Mathias") && password.equals("123")){
			session.setAttribute("oprId",oprId);
			session.setAttribute("user", 1);
			response.sendRedirect("Home.jsp");
		}
		else if (oprId.equals("Khaan") && password.equals("123")){
			session.setAttribute("oprId",oprId);
			session.setAttribute("user", 2);
			response.sendRedirect("Home.jsp");
		}
		else if (oprId.equals("Jens") && password.equals("123")){
			session.setAttribute("oprId",oprId);
			session.setAttribute("user", 3);
			response.sendRedirect("Home.jsp");
		}
		else if (oprId.equals("Emil") && password.equals("123")){
			session.setAttribute("oprId",oprId);
			session.setAttribute("user", 4);
			response.sendRedirect("Home.jsp");
		}
		else
			response.sendRedirect("Error.jsp");
		%>	
	</body>
</html>