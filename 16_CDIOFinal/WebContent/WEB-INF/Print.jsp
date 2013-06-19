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
	
	<%
		List<FullBatchList> fullBatchListMade = s.getFullBatchListMade(productBatch.getPbId());
		List<FullBatchList> fullBatchListNotMade = s.getFullBatchListNotMade(productBatch.getPbId());
	%>
	<div style="border: 1px dashed black; width: 600px; padding: 10px">
		<div style="border-bottom: 1px black solid; width: 80%; margin-left: auto; margin-right: auto">
			Udskrevet: <%= s.getDate() %><br>
			Produkt Batch nr. <%= productBatch.getPbId()%><br>
			Recept nr. <%= productBatch.getReceptId() %>
		</div>
		<% if (fullBatchListMade.size() > 0) { %>
			<br>
			<center>Produceret</center>
			<% for (FullBatchList fullBatchList : fullBatchListMade) { %>
				<%
					tara += fullBatchList.getTara();
					netto += fullBatchList.getNetto();
				%>
				<br>
				<div style="border-bottom: 1px black solid">
					Commodity Id: <%= fullBatchList.getCommodityId() %><br>
					Commodity Name: <%= fullBatchList.getCommodityName() %><br>
				</div>
				<table id="print">
					<tr>
						<th>NomNetto</th>
						<th>Tolerance</th>
						<th>Tara</th>
						<th>Netto</th>
						<th>Batch</th>
						<th>Opr</th>
						<th>Terminal</th>
					</tr>
					<tr>
						<td><%= s.decimalFormat(fullBatchList.getNomNetto()) %></td>
						<td><%= s.decimalFormat(fullBatchList.getTolerance()) %></td>
						<td><%= s.decimalFormat(fullBatchList.getTara()) %></td>
						<td><%= s.decimalFormat(fullBatchList.getNetto()) %></td>
						<td><%= s.decimalFormat(fullBatchList.getCbId()) %></td>
						<td><%= fullBatchList.getOprId() %></td>
						<td><%= fullBatchList.getTerminal() %></td>
						
					
				</table>
			<% } %>
		<% } %>
		<% if (fullBatchListNotMade.size() > 0) { %>
			<br>
			<center>Mangler</center>
			<% for (FullBatchList fullBatchList : fullBatchListNotMade) { %>
				<br>
				<div style="border-bottom: 1px black solid">
					Commodity Id: <%= fullBatchList.getCommodityId() %><br>
					Commodity Name: <%= fullBatchList.getCommodityName() %><br>
				</div>
				<table id="print">
					<tr>
						<th>NomNetto</th>
						<th>Tolerance</th>
						<th>Tara</th>
						<th>Netto</th>
						<th>Batch</th>
						<th>Opr</th>
						<th>Terminal</th>
					</tr>
					<tr>
						<td><%= s.decimalFormat(fullBatchList.getNomNetto()) %></td>
						<td><%= s.decimalFormat(fullBatchList.getTolerance()) %></td>
				</table>
			<% } %>
		<% } %>
		<br>
		<center>Opsummering</center>
		<br>
		<div style="border-bottom: 1px black solid; margin-left: auto; margin-rigth: auto">
			Sum Tara: <%= s.decimalFormat(tara) %><br>
			Sum Netto: <%= s.decimalFormat(netto) %><br>
			<br>
			Produktion Status: <%= productBatch.getStatus() == 0 ? "Ikke påbegyndt" : productBatch.getStatus() == 1 ? "Under produktion" : "Afsluttet" %><br>
			Produktion Startet: <%= productBatch.getStartTime() %><br>
			Produktion Slut: <%= productBatch.getEndTime() == null ? "-" : productBatch.getEndTime() %><br>
		</div>
	</div>
	
<% } else { %>
	
	Product batch not found.
		
<% } %>