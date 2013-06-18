<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="web.Page" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="TabStyle.css">
		<title>16_CDIOFinal</title>
	</head>
	<body>
	<%
		// Logout
		if (request.getParameter("logout") != null) {
			s.logout();
		}

		// Login
		String message = null;
		if (request.getParameter("login") != null) {
			message = s.login(request.getParameter("id"), request.getParameter("password"));
		}

		if (s.isLoggedIn()) {
			Page p = s.getPage(request.getParameter("page"));
			String file = "WEB-INF/" + p.getName() + ".jsp";
	%>
			<h2>Welcome! You're logged in as: <%= s.getName() %></h2>
			<div id='tabs'>
				<ul>
				<%
					for (Page x : s.getPages()) {
						if (s.getRights() <= x.getRightsRequired()) {
				%>
							<li><a href="?page=<%= x.getName() %>"<%= x == p ? "class=\"active\"" : "" %>><%= x.getTitle() %></a>
				<%
						}
					}
				%>
				</ul>
			</div>
			<div id='content'>
				<jsp:include page="<%= file %>"/>
			</div>
			<br>
			<form action="Index.jsp" method="post">
				<input type="hidden" name="logout" value="true">
				<input type="submit" value="Sign out">
			</form>

	<% } else {	%>

		<h2>Login</h2>
		<form method="post">
			<input type="hidden" name="login" value="true">
			<table>
				<tr>
					<td>Id:</td>
					<td><input type="text" name="id" value="<%= request.getParameter("id") != null ? request.getParameter("id") : "" %>"></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="password" value="<%= request.getParameter("password") != null ? request.getParameter("password") : "" %>"></td>
				</tr>
				<tr>
					<td></td>
					<td><br><input type="submit" value="Sign in"></td>
				</tr>
			</table>
		</form>
		<% if (message != null) { %>
			<br>
			<span style="color: red"><%= message %></span>
		<% } %>

	<% } %>
	</body>
</html>