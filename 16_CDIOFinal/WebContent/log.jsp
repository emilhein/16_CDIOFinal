<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="web.Page"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="special_objects.FullBatchList"%>
<%@ page import="database_objects.ProductBatch" %>
<jsp:useBean id="s" class="web.Session" scope="session" />
<jsp:setProperty name="s" property="*" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Produktion</title>
</head>

<body>
<div style="border:1px dashed black; width: 600px;">
<div style="border-bottom: 1px black solid; width: 80%; margin-left: auto; margin-right: auto;">
	Udskrevet: <%= s.getDate() %><br>
	Produkt Batch nr. <%= s.getProductBatches(null, null)%><br>
	Recept nr. <%= s.getDate() %>
</div>




<br>


	<%
		for (FullBatchList fullbatchlist : s.getFullBatchListMade(1)) {
	%>

	<br>
	<div style="border-bottom: 1px black solid; width: 80%; margin-left: auto; margin-rigth: auto;">
	Commodity Id: <%=fullbatchlist.getCommodityId()%><br>
	Commodity Name:  <%=fullbatchlist.getCommodityName()%><br>
	</div>
	
	
	<%= fullbatchlist.getNomNetto() %>
	<%= fullbatchlist.getTolerance()%>
	<%= fullbatchlist.getTara()%>
		<%= fullbatchlist.getNetto()%>
		<%= fullbatchlist.getCommodityId()%>
		<%= fullbatchlist.getOprId()%>
		<%= fullbatchlist.getTerminal()%>
		
	<%
		}
	%>
	
	<br>
	<div style="border-bottom: 1px black solid;  margin-left: auto; margin-rigth: auto;">
	Sum Tara:  test<br>
	Sum Netto:  test1<br>
	
	
	<br>
	Produktion Status: test<br>
	Produktion Startet: test1 <br>
	Produktion Slut:	test2 <br>
	</div>
	</div>
</body>
</html>
