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

<% String pbId = "1";
ProductBatch pb = s.getProductBatches(pbId, null).get(0);

%>


<body>
<div style="border:1px dashed black; width: 600px;">
<div style="border-bottom: 1px black solid; width: 80%; margin-left: auto; margin-right: auto;">
	Udskrevet: <%= s.getDate() %><br>
	Produkt Batch nr. <%= pb.getPbId()%><br>
	Recept nr. <%= pb.getReceptId() %>
</div>
	<%
	double tara =0.0;
	double netto = 0.0;
	
	%>



<br>


	<%
		for (FullBatchList fullbatchlist : s.getFullBatchListMade(1)) {
	%>

	<br>
	<div style="border-bottom: 1px black solid; width: 80%;">
	Commodity Id: <%=fullbatchlist.getCommodityId()%><br>
	Commodity Name:  <%=fullbatchlist.getCommodityName()%><br>
	</div>
	
	
	<auto>NomNetto: <%= fullbatchlist.getNomNetto() %></auto><br>
	<auto>Tolerance: <%= fullbatchlist.getTolerance()%></auto><br>
	<auto>Tara: <%= fullbatchlist.getTara()%></auto><br>
	<auto>Netto: <%= fullbatchlist.getNetto()%></auto><br>
	<auto>Opr: <%= fullbatchlist.getOprId()%></auto><br>
	<auto>Terminal : <%= fullbatchlist.getTerminal()%></auto><br>
	<% tara = tara + fullbatchlist.getTara();  %>
	<% netto = netto + fullbatchlist.getNetto();  %>
		
	<%
		}
	%>
	
	<br>
	<div style="border-bottom: 1px black solid;  margin-left: auto; margin-rigth: auto;">
	Sum Tara:  <%= tara%><br>
	Sum Netto:  <%= netto %><br>
	
	
	<br>
	Produktion Status:  <%= pb.getStatus() == 0?"ikke påbegyndt": pb.getStatus() == 1?"under produktion":"Afsluttet"%><br>
	Produktion Startet: <%= pb.getStartTime() %> <br>
	Produktion Slut:	<%= pb.getEndTime() == null?" - ": pb.getEndTime() %> <br>
	</div>
	</div>
</body>
</html>
