<jsp:useBean id="s" class="web.Session" scope="session" />
<jsp:setProperty name="s" property="*" />
<%@page import="database_objects.CommodityBatch"%>
<%@page import="database_objects.Commodity"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Commodity Batch Administration</title>
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
						<li><a href='ComBatchAdministration.jsp' class='active'>Commodity-batch Administration</a>
						<li><a href='ProductBatchAdministration.jsp'>Product-batch Administration</a>
					<% break;
					case 2: %>
						<li><a href='Home.jsp'>Home</a>
						<li><a href='CommodityAdministration.jsp'>Commodity Administration</a>
						<li><a href='PrescriptionAdministration.jsp'>Prescription Administration</a>
						<li><a href='ComBatchAdministration.jsp' class='active'>Commodity-batch Administration</a>
						<li><a href='ProductBatchAdministration.jsp'>Product-batch Administration</a>
						<% break;
					case 3: %>
						<li><a href='Home.jsp'>Home</a>
						<li><a href='ComBatchAdministration.jsp' class='active'>Commodity-batch Administration</a>
						<li><a href='ProductBatchAdministration.jsp'>Product-batch Administration</a>
						<% break;
					case 4: response.sendRedirect("Home.jsp"); break;
					default: response.sendRedirect("Error.jsp"); break;
				}
				%>
			</ul>
		</div>
		
		<div id='content'>
			The content for commodity batch administration goes here
	<table>
				<tr>
					<th>CommodityBatch ID</th>
					<th>CommodityBatch ID</th>
					<th>Meangde</th>	
				</tr>
				<%
					for(CommodityBatch commodityBatch : s.getCommodityBatchList()) {
				%>
				<form action="ChangeComBatch.jsp" method="post" style="display:inline">
					<input type="hidden" value="<%= commodityBatch.getCbId()  %>" name="commodityBatchId">
					<td><input type="hidden" value="<%= commodityBatch.getCommodityId() %>" name="CommodityId"></td>
					<tr>
						<td><%= commodityBatch.getCbId() %> </td>
						<td><%= commodityBatch.getCommodityId() %> </td>
						<td><input type="text" value="<%= commodityBatch.getMaengde() %>" name="Maengde"></td>
						<td><input type="submit" value="Update" name="button"></td>
					</tr>
				</form>
				<% 
					}
				%>
				<form action="ChangeComBatch.jsp" method="post" style="display:inline">
					<input type="hidden" value="0" name="CommodityBatch ID">
					<tr>
						<td><input type="text" name="CommodityBatch ID"></td>
						<td><input type="text" name="Commodity ID"></td>
						<td><input type="text" name="Meagde"></td>
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