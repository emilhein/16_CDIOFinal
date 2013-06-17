<%@ page import="database_objects.CommodityBatch" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>

<%
	String message1 = null;
	String message2 = null;
	if (request.getParameter("filter") != null) {
		message1 = "Filtered by commodity id: " + request.getParameter("filter");
	}
	if (request.getParameter("update") != null) {
		message2 = s.updateCommodityBatch(request.getParameter("id"), request.getParameter("quantity"));
	} else if (request.getParameter("add") != null) {
		message2 = s.addCommodityBatch(request.getParameter("id"), request.getParameter("commodityId"), request.getParameter("quantity"));		
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
		<th>Commodity Id</th>
		<th>Quantity</th>
	</tr>
	<% for (CommodityBatch commodityBatch : s.getCommodityBatches(request.getParameter("filter"))) {	%>
		<form method="post">
			<input type="hidden" name="update" value="true">
			<input type="hidden" name="id" value="<%= commodityBatch.getCbId() %>">
			<tr>
				<td><center><%= commodityBatch.getCbId() %></center></td>
				<td><center><a href="?page=Commodities&filter=<%= commodityBatch.getCommodityId() %>"><%= commodityBatch.getCommodityId() %></a></center></td>
				<td><input type="text" name="quantity" value="<%= commodityBatch.getMaengde() %>"></td>
				<td><input type="submit" value="Update"></td>
			</tr>
		</form>
	<% } %>
	<form method="post">
		<input type="hidden" name="add" value="true">
		<tr>
			<td><input type="text" name="id"></td>
			<td><input type="text" name="commodityId"></td>
			<td><input type="text" name="quantity"></td>
			<td><input type="submit" value="Add"></td>
		</tr>
	</form>
</table>