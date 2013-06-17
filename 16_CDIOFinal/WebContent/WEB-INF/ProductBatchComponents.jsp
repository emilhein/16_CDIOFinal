<%@ page import="database_objects.ProductBatchComp" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>

<%
	String message1 = null;
	if (request.getParameter("filterProductBatchId") != null) {
		message1 = "Filtered by product batch id: " + request.getParameter("filterProductBatchId");
	} else if (request.getParameter("filterCommodityBatchId") != null) {
		message1 = "Filtered by commodity batch id: " + request.getParameter("filterCommodityBatchId");
	} else if (request.getParameter("filterOperatorId") != null) {
		message1 = "Filtered by operator id: " + request.getParameter("filterOperatorId");
	}
%>

<% if (message1 != null) { %>
	<span style="color: blue"><%= message1 %></span>
	<br>
	<br>
<% } %>
<table>
	<tr>
		<th>Product Batch Id</th>
		<th>Commodity Batch Id</th>
		<th>Operator Id</th>
		<th>Netto</th>
		<th>Tara</th>
	</tr>
	<% for (ProductBatchComp productBatchComp : s.getProductBatchComponents(request.getParameter("filterProductBatchId"), request.getParameter("filterCommodityBatchId"), request.getParameter("filterOperatorId"))) { %>
		<tr>
			<td><center><a href="?page=ProductBatches&filterProductBatchId=<%= productBatchComp.getPbId() %>"><%= productBatchComp.getPbId() %></a></center></td>
			<td><center><a href="?page=CommodityBatches&filterCommodityId=<%= productBatchComp.getCbId() %>"><%= productBatchComp.getCbId() %></a></center></td>
			<td><center><a href="?page=Operators&filterOperatorId=<%= productBatchComp.getOprId() %>"><%= productBatchComp.getOprId() %></a></center></td>
			<td><center><%= productBatchComp.getNetto() %></center></td>
			<td><center><%= productBatchComp.getTara() %></center></td>
		</tr>
	<% } %>
</table>