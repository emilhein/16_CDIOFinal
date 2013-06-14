<%@ page import="database_objects.CommodityBatch" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>

<%
	String message = null;
	if (request.getParameter("update") != null) {
		message = s.updateCommodityBatch(request.getParameter("id"), request.getParameter("quantity"));
	} else if (request.getParameter("add") != null) {
		message = s.addCommodityBatch(request.getParameter("id"), request.getParameter("commodityId"), request.getParameter("quantity"));		
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
		<th>Commodity Id</th>
		<th>Mængde</th>
		<th></th>
	</tr>
	<% for (CommodityBatch commodityBatch : s.getCommodityBatches()) {	%>
		<form method="post">
			<input type="hidden" name="update" value="true">
			<input type="hidden" name="id" value="<%= commodityBatch.getCbId() %>">
			<tr>
				<td><center><%= commodityBatch.getCbId() %></center></td>
				<td><center><%= commodityBatch.getCommodityId() %></center></td>
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