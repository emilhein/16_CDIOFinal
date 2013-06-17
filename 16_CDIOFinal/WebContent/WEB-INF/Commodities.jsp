<%@ page import="database_objects.Commodity" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>

<%
	String message1 = null;
	String message2 = null;
	if (request.getParameter("filter") != null) {
		message1 = "Filtered by commodity id: " + request.getParameter("filter");
	}
	if (request.getParameter("add") != null) {
		message2 = s.addCommodity(request.getParameter("id"), request.getParameter("name"), request.getParameter("supplier"));	
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
		<th>Supplier</th>	
	</tr>
	<% for (Commodity commodity : s.getCommodities(request.getParameter("filter"))) {	%>
		<tr>
			<td><center><%= commodity.getCommodityId() %></center></td>
			<td><center><%= commodity.getCommodityName() %></center></td>
			<td><center><%= commodity.getSupplier() %></center></td>
			<td><center><a href="?page=CommodityBatches&filter=<%= commodity.getCommodityId() %>">Commodity Batches</a></center></td>
		</tr>
	<% } %>
	<form method="post">
		<input type="hidden" name="add" value="true">
		<tr>
			<td><br><input type="text" name="id"></td>
			<td><br><input type="text" name="name"></td>
			<td><br><input type="text" name="supplier"></td>
			<td><br><input type="submit" value="Add"></td>
		</tr>
	</form>
</table>