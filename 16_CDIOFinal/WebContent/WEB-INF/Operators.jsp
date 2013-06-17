<%@ page import="database_objects.Operator" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>

<%
	String message2 = null;
	if (request.getParameter("update") != null) {
		message2 = s.updateOperator(request.getParameter("id"), request.getParameter("name"), request.getParameter("initials"), request.getParameter("password"), request.getParameter("rights"));
	} else if (request.getParameter("add") != null) {
		message2 = s.addOperator(request.getParameter("id"), request.getParameter("name"), request.getParameter("initials"), request.getParameter("cpr"), request.getParameter("password"), request.getParameter("rights"));		
	}
%>

<% if (message2 != null) { %>
	<span style="color: red"><%= message2 %></span>
	<br>
	<br>
<% } %>
<table>
	<tr>
		<th>Id</th>
		<th>Name</th>
		<th>Initials</th>
		<th>CPR</th>
		<th>Password</th>
		<th>Rights</th>
	</tr>
	<% for (Operator operator : s.getOperators()) {	%>
		<form method="post">
			<input type="hidden" name="update" value="true">
			<input type="hidden" name="id" value="<%= operator.getOprId() %>">
			<tr>
				<td><center><%= operator.getOprId() %></center></td>
				<td><input type="text" name="name" value="<%= operator.getOprName() %>"></td>
				<td><input type="text" name="initials" value="<%= operator.getIni() %>"></td>
				<td><center><%= operator.getCpr() %></center></td>
				<td><input type="text" name="password" value="<%= operator.getPassword() %>"></td>
				<td><input type="text" name="rights" value="<%= operator.getRights() %>"></td>
				<td><input type="submit" value="Update"></td>
			</tr>
		</form>
	<% } %>
	<form method="post">
		<input type="hidden" name="add" value="true">
		<tr>
			<td><input type="text" name="id"></td>
			<td><input type="text" name="name"></td>
			<td><input type="text" name="initials"></td>
			<td><input type="text" name="cpr"></td>
			<td><input type="text" name="password"></td>
			<td><input type="text" name="rights"></td>
			<td><input type="submit" value="Add"></td>
		</tr>
	</form>
</table>