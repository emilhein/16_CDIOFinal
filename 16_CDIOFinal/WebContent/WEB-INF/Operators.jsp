<%@ page import="database_objects.Operator" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>

<%
	String message1 = null;
	String message2 = null;
	if (request.getParameter("filterOperatorId") != null) {
		message1 = "Filtered by operator id: " + request.getParameter("filterOperatorId");
	}
	if (request.getParameter("update") != null) {
		message2 = s.updateOperator(request.getParameter("id"), request.getParameter("name"), request.getParameter("initials"), request.getParameter("password"), request.getParameter("rights"));
	} else if (request.getParameter("add") != null) {
		message2 = s.addOperator(request.getParameter("id"), request.getParameter("name"), request.getParameter("initials"), request.getParameter("cpr"), request.getParameter("password"), request.getParameter("rights"));		
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
		<th>Initials</th>
		<th>CPR</th>
		<th>Password</th>
		<th>Rights</th>
	</tr>
	<% for (Operator operator : s.getOperators(request.getParameter("filterOperatorId"))) {	%>
		<form method="post">
			<input type="hidden" name="update" value="true">
			<input type="hidden" name="id" value="<%= operator.getOprId() %>">
			<tr>
				<td><center><%= operator.getOprId() %></center></td>
				<td><input type="text" name="name" value="<%= operator.getOprName() %>"></td>
				<td><input type="text" name="initials" value="<%= operator.getIni() %>"></td>
				<td><center><%= operator.getCpr() %></center></td>
				<td><input type="text" name="password" value="<%= operator.getPassword() %>"></td>
				<td>
					<select name="rights">
  					<% for (int i = 1; i <= 5; i++) { %>
  						<option value="<%= i %>"<%= operator.getRights() == i ? " selected=\"selected\"" : "" %>><%= i %></option>
  					<% } %>
					</select>
				</td>
				<td><input type="submit" value="Update"></td>
				<td><center><a href="?page=ProductBatchComponents&filterOperatorId=<%= operator.getOprId() %>">Product Batch Components</a></center></td>
			</tr>
		</form>
	<% } %>
	<form method="post">
		<input type="hidden" name="add" value="true">
		<tr>
			<td><br><input type="text" name="id" value="<%= request.getParameter("add") != null && message2 != null && request.getParameter("id") != null ? request.getParameter("id") : "" %>"></td>
			<td><br><input type="text" name="name" value="<%= request.getParameter("add") != null && message2 != null && request.getParameter("name") != null ? request.getParameter("name") : "" %>"></td>
			<td><br><input type="text" name="initials" value="<%= request.getParameter("add") != null && message2 != null && request.getParameter("initials") != null ? request.getParameter("initials") : "" %>"></td>
			<td><br><input type="text" name="cpr" value="<%= request.getParameter("add") != null && message2 != null && request.getParameter("cpr") != null ? request.getParameter("cpr") : "" %>"></td>
			<td><br><input type="text" name="password" value="<%= request.getParameter("add") != null && message2 != null && request.getParameter("password") != null ? request.getParameter("password") : "" %>"></td>
			<td>
				<br>
				<select name="rights">
  				<% for (int i = 1; i <= 5; i++) { %>
  					<option value="<%= i %>"<%= request.getParameter("add") != null && message2 != null && request.getParameter("rights").equals("" + i) ? " selected=\"selected\"" : "" %>><%= i %></option>
  				<% } %>
				</select>
			</td>
			<td><br><input type="submit" value="Add"></td>
		</tr>
	</form>
</table>