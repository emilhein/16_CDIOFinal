<%@page import="database_objects.CommodityBatch"%>
<%@page import="database_objects.Commodity"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Commodity Batch Administration</title>
		
		<style type="text/css">
			#tabs{
				padding:0; 
			    margin:0; 
			    font-family:Arial, Helvetica, sans-serif; 
			    font-size:12px; 
			    color:#FFF; 
			    font-weight:bold;
			}
			#tabs ul{
			list-style:none;
			margin:0;
			padding:0;
			}
			#tabs ul li{ 
			    display:inline; 
			    margin:0; 
			    text-transform:capitalize; 
			} 
			#tabs ul li a{ 
			    padding:5px 16px; 
			    color:#FFF; 
			    background:#E7A272; 
			    float:left; 
			    text-decoration:none; 
			    border:1px solid #D17B40; 
			    border-left:0; 
			    margin:0; 
			    text-transform:capitalize; 
			} 
			#tabs ul li a:hover{ 
			    background:#EAEAEA; 
			    color:#7F9298; 
			    text-decoration:none; 
			    border-bottom:1px solid #EAEAEA; 
			} 
			#tabs ul li a.active{ 
			    background:#EAEAEA; 
			    color:#7F9298; 
			    border-bottom:1px solid #EAEAEA; 
			} 
			#content{    
			    background:#EAEAEA; 
			    clear:both; 
			    font-size:11px; 
			    color:#000; 
			    padding:10px; 
			    font-family:Arial, Helvetica, sans-serif; 
			}
		</style>
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
		<th class="nr">m�ngde</th>
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