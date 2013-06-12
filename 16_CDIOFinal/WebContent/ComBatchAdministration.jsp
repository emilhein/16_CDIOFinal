<%@page import="database_objects.CommodityBatch"%>
<%@page import="database_objects.Commodity"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Commodity Batch Administration</title>
		<link rel="stylesheet" type="text/css" href="TabStyle.css">
	</head>
	<body>
		<h2>You're logged in as: Admin</h2>
		<div id='tabs'>
			<ul>
				<% int userType = Integer.parseInt(session.getAttribute("user").toString());
				switch(userType) {
					case 1: %>
						<li><a href='Home.jsp'>Home</a>
						<li><a href='UserAdministration.jsp'>User Administration</a>
						<li><a href='CommodityAdministration.jsp'>Commodity Administration</a>
						<li><a href='PrescriptionAdministration.jsp'>Prescription Administration</a>
						<li><a href='ComBatchAdministration.jsp' class='active'>Commodity-batch Administration</a>
						<li><a href='ProductBatchAdministration.jsp'>Product-batch Administration</a>
					<% break;
					case 2: %>
						<li><a href='Home.jsp'>Home</a>
						<li><a href='CommodityAdministration.jsp'>Commodity Administration</a>
						<li><a href='PrescriptionAdministration.jsp'>Prescription Administration</a>
						<li><a href='ComBatchAdministration.jsp' class='active'>Commodity-batch Administration</a>
						<li><a href='ProductBatchAdministration.jsp'>Product-batch Administration</a>
						<% break;
					case 3: %>
						<li><a href='Home.jsp'>Home</a>
						<li><a href='ComBatchAdministration.jsp' class='active'>Commodity-batch Administration</a>
						<li><a href='ProductBatchAdministration.jsp'>Product-batch Administration</a>
						<% break;
					case 4: response.sendRedirect("Home.jsp"); break;
					default: response.sendRedirect("Error.jsp"); break;
				}
				%>
			</ul>
		</div>
		
		<div id='content'>
			The content for commodity batch administration goes here
			<h2>Printing some status messages</h2>
			
	<table id="list">
	<tr class="info">
		<th class="nr">Cb id nummer</th>
		<th class="nr"> id vare nummer</th>
		<th class="nr">mængde</th>
	</tr>
	<%
		
		java.util.List<CommodityBatch> CommodityBatch; //commodityId.getCommodityBatch();
		
		for(CommodityBatch commoditybatch :CommodityBatch) {
			%>
	<tr class="item">
		<td class="nr"><%= commoditybatch.getCbId() %></td>
		<td class="nr"><%= commoditybatch.getCommodityId() %></td>
		<td class="nr"><%= commoditybatch.getMaengde() %></td>
		
		
	 </tr>
	
	<% 
	} %>
	</table>
			
		</div>
		<form action="Login.jsp" method="post">
			<br/> <input type="submit" value="Log out">
		</form>
	</body>
</html>