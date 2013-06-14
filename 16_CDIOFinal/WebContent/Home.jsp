<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="TabStyle.css">
		<title>Home Page</title>
	</head>
	<body>
		<%
			if (!s.loggedIn()) {
				response.sendRedirect("Login.jsp");
			}
		%>
		<h2>Welcome! You're logged in as: <%= s.getName() %></h2>
		<div id='tabs'>
			<ul>
			<%
				switch(s.getRights()) {
					case 1:
			%>
				<li><a href='Home.jsp' class='active'>Home</a>
				<li><a href='UserAdministration.jsp'>User Administration</a>
				<li><a href='CommodityAdministration.jsp'>Commodity Administration</a>
				<li><a href='PrescriptionAdministration.jsp'>Prescription Administration</a>
				<li><a href='ComBatchAdministration.jsp'>Commodity-batch Administration</a>
				<li><a href='ProductBatchAdministration.jsp'>Product-batch Administration</a>
			<%
						break;
					case 2:
			%>
				<li><a href='Home.jsp' class='active'>Home</a>
				<li><a href='CommodityAdministration.jsp'>Commodity Administration</a>
				<li><a href='PrescriptionAdministration.jsp'>Prescription Administration</a>
				<li><a href='ComBatchAdministration.jsp'>Commodity-batch Administration</a>
				<li><a href='ProductBatchAdministration.jsp'>Product-batch Administration</a>
			<%
						break;
					case 3:
			%>
				<li><a href='Home.jsp' class='active'>Home</a>
				<li><a href='ComBatchAdministration.jsp'>Commodity-batch Administration</a>
				<li><a href='ProductBatchAdministration.jsp'>Product-batch Administration</a>
			<%
						break;
					case 4:
			%>
				<li><a href='Home.jsp' class='active'>Home</a>
			<%
				}
			%>
			</ul>
		</div>
		<div id='content'>
			<h2>Printing some status messages</h2>
			My main content goes here!
		</div>
		<br>
		<form action="Login.jsp" method="post">
			<input type="hidden" name="logout" value="true">
			<input type="submit" value="Logout">
		</form>
	</body>
</html>