<%@ page import="database_objects.RecipeComp" %>
<%@ page import="database_objects.Recipe" %>
<%@ page import="database_objects.Commodity" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>

<%
	String message1 = null;
	String message2 = null;
	if (request.getParameter("filterRecipeId") != null) {
		message1 = "Filtered by recipe id: " + request.getParameter("filterRecipeId");
	} else if (request.getParameter("filterCommodityId") != null) {
		message1 = "Filtered by commodity id: " + request.getParameter("filterCommodityId");
	}
	if (request.getParameter("add") != null) {
		message2 = s.addRecipeComponent(request.getParameter("recipeId"), request.getParameter("commodityId"), request.getParameter("quantity"), request.getParameter("tolerance"));		
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
		<th>Recipe Id</th>
		<th>Commodity Id</th>
		<th>Quantity</th>
		<th>Tolerance</th>
	</tr>
	<% for (RecipeComp recipeComp : s.getRecipeComponents(request.getParameter("filterRecipeId"), request.getParameter("filterCommodityId"))) {	%>
		<tr>
			<td><center><a href="?page=Recipes&filterRecipeId=<%= recipeComp.getRecipeId() %>"><%= s.getRecipes("" + recipeComp.getRecipeId()).get(0).getRecipeName() %> (<%= recipeComp.getRecipeId() %>)</a></center></td>
			<td><center><a href="?page=Commodities&filterCommodityId=<%= recipeComp.getCommodityId() %>"><%= s.getCommodities("" + recipeComp.getCommodityId()).get(0).getCommodityName() %> (<%= recipeComp.getCommodityId() %>)</a></center></td>
			<td><center><%= s.decimalFormat(recipeComp.getNomNetto()) %></center></td>
			<td><center><%= s.decimalFormat(recipeComp.getTolerance()) %></center></td>
		</tr>
	<% } %>
	<form method="post">
		<input type="hidden" name="add" value="true">
		<tr>
			<td>
				<br>
				<select name="recipeId">
  				<% for (Recipe recipe : s.getRecipes(null)) { %>
  					<option value="<%= recipe.getRecipeId() %>"<%= request.getParameter("add") != null && message2 != null && request.getParameter("recipeId").equals("" + recipe.getRecipeId()) ? " selected=\"selected\"" : "" %>><%= recipe.getRecipeName() %> (<%= recipe.getRecipeId() %>)</option>
  				<% } %>
				</select>
			</td>
			<td>
				<br>
				<select name="commodityId">
  				<% for (Commodity commodity : s.getCommodities(null)) { %>
  					<option value="<%= commodity.getCommodityId() %>"<%= request.getParameter("add") != null && message2 != null && request.getParameter("commodityId").equals("" + commodity.getCommodityId()) ? " selected=\"selected\"" : "" %>><%= commodity.getCommodityName() %> (<%= commodity.getCommodityId() %>)</option>
  				<% } %>
				</select>
			</td>
			<td><br><input type="text" name="quantity" value="<%= request.getParameter("add") != null && message2 != null && request.getParameter("quantity") != null ? request.getParameter("quantity") : "" %>"></td>
			<td><br><input type="text" name="tolerance" value="<%= request.getParameter("add") != null && message2 != null && request.getParameter("tolerance") != null ? request.getParameter("tolerance") : "" %>"></td>
			<td><br><input type="submit" value="Add"></td>
		</tr>
	</form>
</table>