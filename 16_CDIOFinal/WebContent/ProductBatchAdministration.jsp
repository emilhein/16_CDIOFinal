<jsp:useBean id="s" class="web.Session" scope="session" />
<jsp:setProperty name="s" property="*" />
<%@page import="database_objects.ProductBatchComp"%>
<%@page import="database_objects.ProductBatch"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Product Batch Administration</title>
		<link rel="stylesheet" type="text/css" href="TabStyle.css">
	</head>
	<body>
		<h2>Welcome! You're logged in as: <label> <%= s.getOperator().getOprName() %> </label> </h2>
		<div id='tabs'>
			<ul>
				<%
				switch(s.getOperator().getRights()) {
					case 1: %>
						<li><a href='Home.jsp'>Home</a>
						<li><a href='UserAdministration.jsp'>User Administration</a>
						<li><a href='CommodityAdministration.jsp'>Commodity Administration</a>
						<li><a href='PrescriptionAdministration.jsp'>Prescription Administration</a>
						<li><a href='ComBatchAdministration.jsp'>Commodity-batch Administration</a>
						<li><a href='ProductBatchAdministration.jsp' class='active'>Product-batch Administration</a>
					<% break;
					case 2: %>
						<li><a href='Home.jsp'>Home</a>
						<li><a href='CommodityAdministration.jsp'>Commodity Administration</a>
						<li><a href='PrescriptionAdministration.jsp'>Prescription Administration</a>
						<li><a href='ComBatchAdministration.jsp'>Commodity-batch Administration</a>
						<li><a href='ProductBatchAdministration.jsp' class='active'>Product-batch Administration</a>
						<% break;
					case 3: %>
						<li><a href='Home.jsp'>Home</a>
						<li><a href='ComBatchAdministration.jsp'>Commodity-batch Administration</a>
						<li><a href='ProductBatchAdministration.jsp' class='active'>Product-batch Administration</a>
						<% break;
					case 4: response.sendRedirect("Home.jsp"); break;
					default: response.sendRedirect("Error.jsp"); break;
				}
				%>
			</ul>
		</div>
		
		<div id='content'>
			My main content goes here!
			
			<table>
				<tr>
					<th>ProductBatch ID</th>
					<th>Recept ID</th>
					<th>Timestamp</th>
					<th>Status</th>
				</tr>
				<%
					for(ProductBatch productBatch : s.getProductBatchList()) {
				%>
				<form  method="post" style="display:inline">
					<input type="hidden" value="<%= productBatch.getPbId()  %>" name="pbId">
					<td><input type="hidden" value="<%= productBatch.getReceptId() %>" name="ReceptId"></td>
					<td><input type="hidden" value="<%= productBatch.getTimeStamp() %>" name="Timestamp"></td>
					<td><input type="hidden" value="<%= productBatch.getStatus() %>" name="Status"></td>
					<tr>
						<td><%= productBatch.getPbId() %> </td>
						<td><%= productBatch.getReceptId() %> </td>
						<td><%= productBatch.getTimeStamp() %> </td>
						<td><%= productBatch.getStatus() %> </td>
						
												
					</tr>
				</form>
				<% 
					}
				%>
				<form  method="post" style="display:inline">
					<input type="hidden" value="0" name="ProductBatch ID">
					<tr>
						<td><input type="text" name="Recept ID"></td>
						<td><input type="text" name="Timestamp"></td>
						<td><input type="text" name="Status"></td>
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