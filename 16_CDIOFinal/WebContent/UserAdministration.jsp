<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="database_objects.Operator" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="TabStyle.css">
		<title>User Administration</title>
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
				if (request.getParameter("update") != null) {

					// Update operator
					
					message = s.updateOperator(request.getParameter("id"), request.getParameter("name"), request.getParameter("initials"), request.getParameter("password"), request.getParameter("rigths"));
					
				} else if (request.getParameter("add") != null) {

					// Add operator
					
					message = s.addOperator(request.getParameter("name"), request.getParameter("initials"), request.getParameter("cpr"), request.getParameter("password"), request.getParameter("rigths"));
					
				}
			}
		%>
		<h2>Welcome! You're logged in as: <%= s.getName() %></h2>
		<div id='tabs'>
			<ul>
				<li><a href='Home.jsp'>Home</a>
				<li><a href='UserAdministration.jsp' class='active'>User Administration</a>
				<li><a href='CommodityAdministration.jsp'>Commodity Administration</a>
				<li><a href='PrescriptionAdministration.jsp'>Prescription Administration</a>
				<li><a href='ComBatchAdministration.jsp'>Commodity-batch Administration</a>
				<li><a href='ProductBatchAdministration.jsp'>Product-batch Administration</a>
			</ul>
		</div>
		<div id='content'>
			<h2>Add or update operators</h2>
			<%
				if (message != null) {
			%>
			<br>
			<span style="color: red"><%= message %></span>
			<br>
			<%
				}
			%>
			<table>
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>Initials</th>
					<th>CPR</th>
					<th>Password</th>
					<th>Rights</th>
					<th></th>
				</tr>
				<%
					for (Operator operator : s.getOperators()) {
				%>
				<form method="post">
					<input type="hidden" name="update" value="true">
					<input type="hidden" name="id" value="<%= operator.getOprId() %>">
					<tr>
						<td><center><%= operator.getOprId() %></center></td>
						<td><input type="text" name="name" value="<%= operator.getOprName() %>"></td>
						<td><input type="text" name="initials" value="<%= operator.getIni() %>"></td>
						<td><center><%= operator.getCpr() %></center></td>
						<td><input type="text" name="password" value="<%= operator.getPassword() %>"></td>
						<td><input type="text" name="rigths" value="<%= operator.getRights() %>"></td>
						<td><input type="submit" value="Update"></td>
					</tr>
				</form>
				<%
					}
				%>
				<form method="post">
					<input type="hidden" name="add" value="true">
					<tr>
						<td><input type="text" name="id"></td>
						<td><input type="text" name="name"></td>
						<td><input type="text" name="initials"></td>
						<td><input type="text" name="cpr"></td>
						<td><input type="text" name="password"></td>
						<td><input type="text" name="rights"></td>
						<td><input type="submit" value="Add"></td>
					</tr>
				</form>
			</table>
		</div>
		<br>
		<form action="Login.jsp" method="post">
			<input type="hidden" name="logout" value="true">
			<input type="submit" value="Logout">
		</form>
	</body>
	</html>