<jsp:useBean id="s" class="web.Session" scope="session" />
<jsp:setProperty name="s" property="*" />
<%@page import="database_objects.Commodity" %>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Commodity Administration</title>
		<link rel="stylesheet" type="text/css" href="TabStyle.css">
		
	</head>
	<body>
	<%
			if (!s.loggedIn()) {
				response.sendRedirect("Login.jsp");
			} 
			if (s.getRights() != 1) {
				response.sendRedirect("Home.jsp");
			}
			
			String message = null;
			
			if (request.getMethod().equalsIgnoreCase("post")) {
				if (request.getParameter("add") != null) {

					// Add operator
					
					message = s.addCommodity(request.getParameter("commodityId"), request.getParameter("commodityName"), request.getParameter("supplier"));
					
				}
					
				}
			
		%>
		<h2>Welcome! You're logged in as: <label> <%= s.getOperator().getOprName() %> </label> </h2>
		<div id='tabs'>
			<ul>
				<% 
				switch(s.getOperator().getRights()) {
					case 1: %>
						<li><a href='Home.jsp'>Home</a>
						<li><a href='UserAdministration.jsp'>User Administration</a>
						<li><a href='CommodityAdministration.jsp' class='active'>Commodity Administration</a>
						<li><a href='PrescriptionAdministration.jsp'>Prescription Administration</a>
						<li><a href='ComBatchAdministration.jsp'>Commodity-batch Administration</a>
						<li><a href='ProductBatchAdministration.jsp'>Product-batch Administration</a>
					<% break;
					case 2: %>
						<li><a href='Home.jsp'>Home</a>
						<li><a href='CommodityAdministration.jsp' class='active'>Commodity Administration</a>
						<li><a href='PrescriptionAdministration.jsp'>Prescription Administration</a>
						<li><a href='ComBatchAdministration.jsp'>Commodity-batch Administration</a>
						<li><a href='ProductBatchAdministration.jsp'>Product-batch Administration</a>
						<% break;
					case 3: response.sendRedirect("Home.jsp"); break;
					case 4: response.sendRedirect("Home.jsp"); break;
					default: response.sendRedirect("Error.jsp"); break;
				}
				%>
			</ul>
		</div>
		
		<div id='content'>
			The content for Commodity Administration goes here
			<%
				if (message != null) {
			%>
			<span style="color: red"><%= message %></span>
			<br><br>
			<%
				}
			%>
			<table>
				<tr>
					<th>Commodity ID</th>
					<th>Commodity name</th>
					<th>Supplier</th>	
				</tr>
				
				<%
					for(Commodity commodity : s.getCommodityList()) {
				%>
				
				<form method="post" style="display:inline">
					<input type="hidden" value="<%= commodity.getCommodityId()  %>" name="commodityId">
					<td><input type="hidden" value="<%= commodity.getCommodityName() %>" name="Name"></td>
					<td><input type="hidden" value="<%= commodity.getSupplier() %>" name="Supplier"></td>
					<tr>
						<td><%= commodity.getCommodityId() %> </td>
						<td><%= commodity.getCommodityName() %> </td>
						<td><%= commodity.getSupplier() %> </td>
					</tr>
				</form>
				<% 
					}
				%>
				<form method="post" style="display:inline">
					<input type="hidden" value="0" name="Commodity ID">
					<input type="hidden" value="true" name="add">
					<tr>
						<td><input type="text" name="commodityId"></td>
						<td><input type="text" name="commodityName"></td>
						<td><input type="text" name="supplier"></td>
						<td><input type="submit" value="Add"></td>
					</tr>	
				</form>
			</table>
			
			
			<h2>Printing some status messages</h2>
			
		</div>
		<form action="Login.jsp" method="post">
			<br/> <input type="submit" value="Log out">
		</form>
	</body>
</html>