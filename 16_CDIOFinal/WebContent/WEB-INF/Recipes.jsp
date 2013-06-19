<%@ page import="database_objects.Recipe" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>

<%
	String message1 = null;
	String message2 = null;
	if (request.getParameter("filterRecipeId") != null) {
		message1 = "Filtered by recipe id: " + request.getParameter("filterRecipeId");
	}
	if (request.getParameter("add") != null) {
		message2 = s.addRecipe(request.getParameter("id"), request.getParameter("name"));		
	}
%>

<% if (message1 != null) { %>
	<span style="color: blue"><%= message1 %></span>
	<br>
	<br>
<% } %>
<% if (message2 != null) { %>
	<span style="color: red"><%= message2 %></span>
	<br>
	<br>
<% } %>
<table>
	<tr>
		<th>Id</th>
		<th>Name</th>
	</tr>
	<% for (Recipe recipe : s.getRecipes(request.getParameter("filterRecipeId"))) {	%>
		<tr>
			<td><center><%= recipe.getRecipeId() %></center></td>
			<td><center><%= recipe.getRecipeName() %></center></td>
			<td><center><a href="?page=RecipeComponents&filterRecipeId=<%= recipe.getRecipeId() %>">Recipe Components</a></center></td>
			<td><center><a href="?page=ProductBatches&filterRecipeId=<%= recipe.getRecipeId() %>">Product Batches</a></center></td>
		</tr>
	<% } %>
	<form method="post">
		<input type="hidden" name="add" value="true">
		<tr>
			<td><br><input type="text" name="id" value="<%= request.getParameter("add") != null && request.getParameter("id") != null ? request.getParameter("id") : "" %>"></td>
			<td><br><input type="text" name="name" value="<%= request.getParameter("add") != null && request.getParameter("name") != null ? request.getParameter("name") : "" %>"></td>
			<td><br><input type="submit" value="Add"></td>
		</tr>
	</form>
</table>