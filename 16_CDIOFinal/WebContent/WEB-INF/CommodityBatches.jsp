<%@ page import="database_objects.CommodityBatch" %>
<%@ page import="database_objects.Commodity" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>

<%
	String message1 = null;
	String message2 = null;
	if (request.getParameter("filterCommodityId") != null) {
		message1 = "Filtered by commodity id: " + request.getParameter("filterCommodityId");
	}
	if (request.getParameter("update") != null) {
		message2 = s.updateCommodityBatch(request.getParameter("id"), request.getParameter("quantity"));
	} else if (request.getParameter("add") != null) {
		message2 = s.addCommodityBatch(request.getParameter("id"), request.getParameter("commodityId"), request.getParameter("quantity"));		
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
		<th>Commodity Id</th>
		<th>Quantity</th>
	</tr>
	<% for (CommodityBatch commodityBatch : s.getCommodityBatches(request.getParameter("filterCommodityId"))) {	%>
		<form method="post">
			<input type="hidden" name="update" value="true">
			<input type="hidden" name="id" value="<%= commodityBatch.getCbId() %>">
			<tr>
				<td><center><%= commodityBatch.getCbId() %></center></td>
				<td><center><a href="?page=Commodities&filterCommodityId=<%= commodityBatch.getCommodityId() %>"><%= commodityBatch.getCommodityId() %>. <%= s.getCommodities("" + commodityBatch.getCommodityId()).get(0).getCommodityName() %></a></center></td>
				<td><input type="text" name="quantity" value="<%= s.decimalFormat(commodityBatch.getMaengde())%>"></td>
				<td><input type="submit" value="Update"></td>
				<td><center><a href="?page=ProductBatchComponents&filterCommodityBatchId=<%= commodityBatch.getCbId() %>">Product Batch Components</a></center></td>
			</tr>
		</form>
	<% } %>
	<form method="post">
		<input type="hidden" name="add" value="true">
		<tr>
			<td><br><input type="text" name="id" value="<%= request.getParameter("add") != null && request.getParameter("id") != null ? request.getParameter("id") : "" %>"></td>
			<td>
				<br>
				<select name="commodityId">
  				<% for (Commodity commodity : s.getCommodities(null)) { %>
  					<option value="<%= commodity.getCommodityId() %>"<%= request.getParameter("add") != null && request.getParameter("commodityId").equals("" + commodity.getCommodityId()) ? " selected=\"selected\"" : "" %>><%= commodity.getCommodityId() %>. <%= commodity.getCommodityName() %></option>
  				<% } %>
				</select>
			</td>
			<td><br><input type="text" name="quantity" value="<%= request.getParameter("add") != null && request.getParameter("quantity") != null ? request.getParameter("quantity") : "" %>"></td>
			<td><br><input type="submit" value="Add"></td>
		</tr>
	</form>
</table>