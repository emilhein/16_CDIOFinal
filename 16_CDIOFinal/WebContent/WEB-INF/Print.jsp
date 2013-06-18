<%@ page import="special_objects.FullBatchList" %>
<%@ page import="database_objects.ProductBatch" %>
<%@ page import="java.util.List" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>

<% 
	List<ProductBatch> productBatches = s.getProductBatches(request.getParameter("filterProductBatchId") == null ? "" : request.getParameter("filterProductBatchId"), null);
	ProductBatch productBatch = productBatches == null ? null : productBatches.get(0);
	double tara = 0.0;
	double netto = 0.0;
%>

<% if (productBatch != null) { %>
	
	<div style="border: 1px dashed black; width: 600px; padding: 10px">
		<div style="border-bottom: 1px black solid; width: 80%; margin-left: auto; margin-right: auto">
			Udskrevet: <%= s.getDate() %><br>
			Produkt Batch nr. <%= productBatch.getPbId()%><br>
			Recept nr. <%= productBatch.getReceptId() %>
		</div>
		<br>
		<center>Produceret</center>
		<% for (FullBatchList fullbatchlist : s.getFullBatchListMade(productBatch.getPbId())) { %>
			<br>
			<div style="border-bottom: 1px black solid">
				Commodity Id: <%= fullbatchlist.getCommodityId() %><br>
				Commodity Name: <%= fullbatchlist.getCommodityName() %><br>
			</div>
			NomNetto: <%= fullbatchlist.getNomNetto() %><br>
			Tolerance: <%= fullbatchlist.getTolerance() %><br>
			Tara: <%= fullbatchlist.getTara() %><br>
			Netto: <%= fullbatchlist.getNetto() %><br>
			Opr: <%= fullbatchlist.getOprId() %><br>
			Terminal: <%= fullbatchlist.getTerminal() %><br>
			<%
				tara += fullbatchlist.getTara();
				netto += fullbatchlist.getNetto();
			%>
		<% } %>
		<br>
		<center>Mangler</center>
		<% for (FullBatchList fullbatchlist : s.getFullBatchListNotMade(productBatch.getPbId())) { %>
			<br>
			<div style="border-bottom: 1px black solid">
				Commodity Id: <%= fullbatchlist.getCommodityId() %><br>
				Commodity Name: <%= fullbatchlist.getCommodityName() %><br>
			</div>
			NomNetto: <%= fullbatchlist.getNomNetto() %><br>
			Tolerance: <%= fullbatchlist.getTolerance() %><br>
		<% } %>
		<br>
		<center>Opsummering</center>
		<br>
		<div style="border-bottom: 1px black solid; margin-left: auto; margin-rigth: auto">
			Sum Tara: <%= tara %><br>
			Sum Netto: <%= netto %><br>
			<br>
			Produktion Status: <%= productBatch.getStatus() == 0 ? "Ikke påbegyndt" : productBatch.getStatus() == 1 ? "Under produktion" : "Afsluttet" %><br>
			Produktion Startet: <%= productBatch.getStartTime() %><br>
			Produktion Slut: <%= productBatch.getEndTime() == null ? "-" : productBatch.getEndTime() %><br>
		</div>
	</div>
	
<% } else { %>
	
	Product batch not found.
		
<% } %>