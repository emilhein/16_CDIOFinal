<%@ page import="database_objects.Commodity" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>

<%
	String message = null;
	if (request.getParameter("add") != null) {
		message = s.addCommodity(request.getParameter("id"), request.getParameter("name"), request.getParameter("supplier"));	
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
		<th>Supplier</th>	
	</tr>
	<% for (Commodity commodity : s.getCommodities()) {	%>
		<tr>
			<td><center><%= commodity.getCommodityId() %></center></td>
			<td><center><%= commodity.getCommodityName() %></center></td>
			<td><center><%= commodity.getSupplier() %></center></td>
		</tr>
	<% } %>
	<form method="post">
		<input type="hidden" name="add" value="true">
		<tr>
			<td><input type="text" name="id"></td>
			<td><input type="text" name="name"></td>
			<td><input type="text" name="supplier"></td>
			<td><input type="submit" value="Add"></td>
		</tr>
	</form>
</table>