<%@ page import="database_objects.Recipe" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>

<%
	String message = null;
	if (request.getParameter("add") != null) {
		message = s.addRecipe(request.getParameter("id"), request.getParameter("name"));		
	}
%>

<% if (message != null) { %>
	<span style="color: red"><%= message %></span>
	<br>
	<br>
<% } %>
<table>
	<tr>
		<th>Id</th>
		<th>Name</th>
		<th></th>
	</tr>
	<% for (Recipe recipe : s.getRecipes()) {	%>
		<tr>
			<td><center><%= recipe.getRecipeId() %></center></td>
			<td><center><%= recipe.getRecipeName() %></center></td>
		</tr>
	<% } %>
	<form method="post">
		<input type="hidden" name="add" value="true">
		<tr>
			<td><input type="text" name="id"></td>
			<td><input type="text" name="name"></td>
			<td><input type="submit" value="Add"></td>
		</tr>
	</form>
</table>