<jsp:useBean id="s" class="web.Session" scope="session" />
<jsp:setProperty name="s" property="*" />
<%@page import="database_objects.Recipe"%>
<%@page import="database_objects.RecipeComp"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Prescription Administration</title>
		<link rel="stylesheet" type="text/css" href="TabStyle.css">
	</head>
	<body>
		<%
			if (!s.loggedIn()) {
				response.sendRedirect("Login.jsp");
			} 
			if (s.getRights() > 3) {
				response.sendRedirect("Home.jsp");
			}
			
			String message = null;
			
			if (request.getMethod().equalsIgnoreCase("post")) {
				if (request.getParameter("add") != null) {

					// Add operator
					
					message = s.addRecipe(request.getParameter("recipeId"), request.getParameter("recipeName"));
					
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
						<li><a href='CommodityAdministration.jsp'>Commodity Administration</a>
						<li><a href='PrescriptionAdministration.jsp' class='active'>Prescription Administration</a>
						<li><a href='ComBatchAdministration.jsp'>Commodity-batch Administration</a>
						<li><a href='ProductBatchAdministration.jsp'>Product-batch Administration</a>
					<% break;
					case 2: %>
						<li><a href='Home.jsp'>Home</a>
						<li><a href='CommodityAdministration.jsp'>Commodity Administration</a>
						<li><a href='PrescriptionAdministration.jsp' class='active'>Prescription Administration</a>
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
			The content for prescription administration goes here.	
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
					<th>Recipe ID</th>
					<th>Recipe name</th>
					
				</tr>
				<%
					for(Recipe recipe : s.getRecipeList()) {
				%>
				<form  method="post" style="display:inline">
					<input type="hidden" value="<%= recipe.getRecipeId()  %>" name="recipeId">
					<td><input type="hidden" value="<%= recipe.getRecipeName() %>" name="recipeName"></td>
					<tr>
						<td><%= recipe.getRecipeId() %> </td>
						<td><%= recipe.getRecipeName() %> </td>
						
					</tr>
				</form>
				<% 
					}
				%>
				<form method="post" style="display:inline">
					<input type="hidden" value="0" name="recipeId">
					<input type="hidden" value="true" name="add">
					<tr>
						<td><input type="text" name="recipeId"></td>
						<td><input type="text" name="recipeName"></td>
						
					<td><input type="submit" value="Add" name="button"></td>
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