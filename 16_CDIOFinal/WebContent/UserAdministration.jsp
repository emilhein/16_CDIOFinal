<jsp:useBean id="s" class="web.Session" scope="session" />
<jsp:setProperty name="s" property="*" />
<%@page import="database_objects.Operator"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>User Administration</title>
		<link rel="stylesheet" type="text/css" href="TabStyle.css">
	</head>
	<body>
		<h2>You're logged in as: Admin</h2>
		<div id='tabs'>
			<ul>
				<%
				switch(s.getOperator().getRights()) {
					case 1: %>
						<li><a href='Home.jsp'>Home</a>
						<li><a href='UserAdministration.jsp' class='active'>User Administration</a>
						<li><a href='CommodityAdministration.jsp'>Commodity Administration</a>
						<li><a href='PrescriptionAdministration.jsp'>Prescription Administration</a>
						<li><a href='ComBatchAdministration.jsp'>Commodity-batch Administration</a>
						<li><a href='ProductBatchAdministration.jsp'>Product-batch Administration</a>
					<% break;
					case 2: response.sendRedirect("Home.jsp"); break;
					case 3: response.sendRedirect("Home.jsp"); break;
					case 4: response.sendRedirect("Home.jsp"); break;
					default: response.sendRedirect("Error.jsp"); break;
				}
				%>
			</ul>
		</div>
		
		<div id='content'>
			The content for user administration goes here
			
			<table>
			<tr>
				<th>oprId</th>
				<th>oprName</th>
				<th>ini</th>
				<th>cpr</th>
				<th>password</th>
				<th>rights</th>
				<th></th>
			</tr>
			<%
				for (Operator operator : s.getOperators()) {
			%>
			<form action="ChangeOperators.jsp" method="post" style="display:inline">
				<input type="hidden" value="<%= operator.getOprId() %>" name="OprId">
				<tr>
					<td><%= operator.getOprId() %></td>
					<td><input type="text" value="<%= operator.getOprName() %>" name="Name"></td>
					<td><input type="text" value="<%= operator.getIni() %>" name="Initials"></td>
					<td><input type="text" value="<%= operator.getCpr() %>" name="CPR"></td>
					<td><input type="text" value="<%= operator.getPassword() %>" name="Password"></td>
					<td><input type="text" value="<%= operator.getRights() %>" name="Rigths"></td>
					<td><input type="submit" value="Opdater" name="button"></td>
					
				</tr>
			</form> 
			<%
				}
			%>
			<form action="ChangeOperators.jsp" method="post" style="display:inline">
				<input type="hidden" value="0" name="OprId">
				<tr>
					<td><input type="text" name="oprId"></td>
					<td><input type="text" name="Name"></td>
					<td><input type="text" name="Ini"></td>
					<td><input type="text" name="cpr"></td>
					<td><input type="text" name="Password"></td>
					<td><input type="text" name="Rights"></td>
					<td><input type="submit" value="Tilføj"></td>
					</tr>
			</form>
		</table>
			
			
			<h2>Printing some status messages for the user administration tab</h2>
			
		</div>
		<form action="Login.jsp" method="post">
			<br/> <input type="submit" value="Log out">
		</form>
	</body>
	</html>