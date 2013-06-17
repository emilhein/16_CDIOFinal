<%@ page import="database_objects.RecipeComp" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>

<%
	String message = null;
	if (request.getParameter("filter") != null) {
		message = "Filtered by recipe id: " + request.getParameter("filter");
	}	
	if (request.getParameter("add") != null) {
		message = s.addRecipeComponent(request.getParameter("recipeId"), request.getParameter("commodityId"), request.getParameter("quantity"), request.getParameter("tolerance"));		
	}
%>

<% if (message != null) { %>
	<span style="color: red"><%= message %></span>
	<br>
	<br>
<% } %>
<table>
	<tr>
		<th>Recipe Id</th>
		<th>Commodity Id</th>
		<th>Quantity</th>
		<th>Tolerance</th>
	</tr>
	<% for (RecipeComp recipeComp : s.getRecipeComponents(request.getParameter("filter"))) {	%>
		<tr>
			<td><center><a href="?page=Recipes&filter=<%= recipeComp.getRecipeId() %>"><%= recipeComp.getRecipeId() %></a></center></td>
			<td><center><a href="?page=Commodities&filter=<%= recipeComp.getCommodityId() %>"><%= recipeComp.getCommodityId() %></a></center></td>
			<td><center><%= recipeComp.getNomNetto() %></center></td>
			<td><center><%= recipeComp.getTolerance() %></center></td>
		</tr>
	<% } %>
	<form method="post">
		<input type="hidden" name="add" value="true">
		<tr>
			<td><br><input type="text" name="recipeId"></td>
			<td><br><input type="text" name="commodityId"></td>
			<td><br><input type="text" name="quantity"></td>
			<td><br><input type="text" name="tolerance"></td>
			<td><br><input type="submit" value="Add"></td>
		</tr>
	</form>
</table>