<%@ page import="database_objects.ProductBatch" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>

<%
	String message = null;
	if (request.getParameter("add") != null) {
		message = s.addProductBatch(request.getParameter("id"), request.getParameter("receptId"), request.getParameter("timestamp"));		
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
		<th>Recept Id</th>
		<th>Timestamp</th>
		<th>Status</th>
	</tr>
	<% for (ProductBatch productBatch : s.getProductBatches()) { %>
		<tr>
			<td><center><%= productBatch.getPbId() %></center></td>
			<td><center><%= productBatch.getReceptId() %></center></td>
			<td><center><%= productBatch.getTimeStamp() %></center></td>
			<td><center><%= productBatch.getStatus() %></center></td>
		</tr>
	<% } %>
	<form method="post">
		<input type="hidden" name="add" value="true">
		<tr>
			<td><input type="text" name="id"></td>
			<td><input type="text" name="receptId"></td>
			<td><input type="text" name="timestamp"></td>
			<td><center>0</center></td>
			<td><input type="submit" value="Add"></td>
		</tr>
	</form>
</table>