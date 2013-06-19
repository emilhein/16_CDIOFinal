<%@ page import="database_objects.RecipeComp" %>
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
			<td><center><a href="?page=Recipes&filterRecipeId=<%= recipeComp.getRecipeId() %>"><%= recipeComp.getRecipeId() %></a></center></td>
			<td><center><a href="?page=Commodities&filterCommodityId=<%= recipeComp.getCommodityId() %>"><%= recipeComp.getCommodityId() %></a></center></td>
			<td><center><%= s.decimalFormat(recipeComp.getNomNetto()) %></center></td>
			<td><center><%= s.decimalFormat(recipeComp.getTolerance()) %></center></td>
		</tr>
	<% } %>
	<form method="post">
		<input type="hidden" name="add" value="true">
		<tr>
			<td><br><input type="text" name="recipeId" value="<%= request.getParameter("add") != null && request.getParameter("recipeId") != null ? request.getParameter("recipeId") : "" %>"></td>
			<td><br><input type="text" name="commodityId" value="<%= request.getParameter("add") != null && request.getParameter("commodityId") != null ? request.getParameter("commodityId") : "" %>"></td>
			<td><br><input type="text" name="quantity" value="<%= request.getParameter("add") != null && request.getParameter("quantity") != null ? request.getParameter("quantity") : "" %>"></td>
			<td><br><input type="text" name="tolerance" value="<%= request.getParameter("add") != null && request.getParameter("tolerance") != null ? request.getParameter("tolerance") : "" %>"></td>
			<td><br><input type="submit" value="Add"></td>
		</tr>
	</form>
</table>