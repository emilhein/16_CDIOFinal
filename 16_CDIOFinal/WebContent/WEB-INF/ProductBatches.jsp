<%@ page import="database_objects.ProductBatch" %>
<%@ page import="database_objects.Recipe" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>

<%
	String message1 = null;
	String message2 = null;
	if (request.getParameter("filterProductBatchId") != null) {
		message1 = "Filtered by product batch id: " + request.getParameter("filterProductBatchId");
	} else if (request.getParameter("filterRecipeId") != null) {
		message1 = "Filtered by recipe id: " + request.getParameter("filterRecipeId");
	}
	if (request.getParameter("add") != null) {
		message2 = s.addProductBatch(request.getParameter("id"), request.getParameter("recipeId"));		
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
		<th>Recipe Id</th>
		<th>Begin</th>
		<th>End</th>
		<th>Status</th>
	</tr>
	<% for (ProductBatch productBatch : s.getProductBatches(request.getParameter("filterProductBatchId"), request.getParameter("filterRecipeId"))) { %>
		<tr>
			<td><center><%= productBatch.getPbId() %></center></td>
			<td><center><a href="?page=Recipes&filterRecipeId=<%= productBatch.getReceptId() %>"><%= s.getRecipes("" + productBatch.getReceptId()).get(0).getRecipeName() %> (<%= productBatch.getReceptId() %>)</a></center></td>
			<td><center><%= productBatch.getStartTime() %></center></td>
			<td><center><%= productBatch.getEndTime() == null ? "-" : productBatch.getEndTime() %></center></td>
			<td><center><%= productBatch.getStatus() %></center></td>
			<td><center><a href="?page=ProductBatchComponents&filterProductBatchId=<%= productBatch.getPbId() %>">Product Batch Components</a></center></td>
			<td><center><a href="?page=Print&filterProductBatchId=<%= productBatch.getPbId() %>">Print</a></center></td>
		</tr>
	<% } %>
	<form method="post">
		<input type="hidden" name="add" value="true">
		<tr>
			<td><br><input type="text" name="id" value="<%= request.getParameter("add") != null && request.getParameter("id") != null ? request.getParameter("id") : "" %>"></td>
			<td>
				<br>
				<select name="recipeId">
  				<% for (Recipe recipe : s.getRecipes(null)) { %>
  					<option value="<%= recipe.getRecipeId() %>"<%= request.getParameter("add") != null && request.getParameter("recipeId").equals("" + recipe.getRecipeId()) ? " selected=\"selected\"" : "" %>><%= recipe.getRecipeName() %> (<%= recipe.getRecipeId() %>)</option>
  				<% } %>
				</select>
			</td>
			<td><br></td>
			<td><br></td>
			<td><br></td>
			<td><br><input type="submit" value="Add"></td>
		</tr>
	</form>
</table>