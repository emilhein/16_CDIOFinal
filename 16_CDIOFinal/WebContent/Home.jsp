<jsp:useBean id="s" class="web.Session" scope="session" />
<jsp:setProperty name="s" property="*" />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Home Page</title>
		<link rel="stylesheet" type="text/css" href="TabStyle.css"> 
	</head>
	<body>
		<h2>Welcome! You're logged in as: <label> <%= s.getOperator().getOprName() %> </label> </h2>
		<div id='tabs'>
			<ul>
				<% 
				switch(s.getOperator().getRights()) {
					case 1: %>
						<li><a href='Home.jsp' class='active'>Home</a>
						<li><a href='UserAdministration.jsp'>User Administration</a>
						<li><a href='CommodityAdministration.jsp'>Commodity Administration</a>
						<li><a href='PrescriptionAdministration.jsp'>Prescription Administration</a>
						<li><a href='ComBatchAdministration.jsp'>Commodity-batch Administration</a>
						<li><a href='ProductBatchAdministration.jsp'>Product-batch Administration</a>
					<% break;
					case 2: %>
						<li><a href='Home.jsp' class='active'>Home</a>
						<li><a href='CommodityAdministration.jsp'>Commodity Administration</a>
						<li><a href='PrescriptionAdministration.jsp'>Prescription Administration</a>
						<li><a href='ComBatchAdministration.jsp'>Commodity-batch Administration</a>
						<li><a href='ProductBatchAdministration.jsp'>Product-batch Administration</a>
						<% break;
					case 3: %>
						<li><a href='Home.jsp' class='active'>Home</a>
						<li><a href='ComBatchAdministration.jsp'>Commodity-batch Administration</a>
						<li><a href='ProductBatchAdministration.jsp'>Product-batch Administration</a>
						<% break;
					case 4: %>
						<li><a href='Home.jsp' class='active'>Home</a>
						<% break;
					default: response.sendRedirect("Error.jsp"); break;
				}
				%>
			</ul>
		</div>
		
		<div id='content'>
			My main content goes here!
			bla bla bla
			
			<h2>Printing some status messages</h2>
			<% System.out.println(session.getAttribute("username")); %>
			
		</div>
		
		<form action="Login.jsp" method="post">
			<br/> <input type="submit" value="Log out">
		</form>
	</body>
</html>