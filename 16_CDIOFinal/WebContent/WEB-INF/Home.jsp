<%@ page import="database_objects.CommodityBatch" %>
<%@ page import="special_objects.Commodity_Sum" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>

<h2>Messages just for you!</h2>
<% if (s.getLowCommodityBatches(6) != null) { %>
	<h3>Low quantity commodities</h3>
	<table>
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Quantity</th>
		</tr>
		<% for(Commodity_Sum commoditySum : s.getLowCommodityBatches(6)) { %>
			<tr>
				<td><center><%= commoditySum.getCommodityId() %></center></td>
				<td><center><%= commoditySum.getCommodityName() %></center></td>
				<td><center><%= commoditySum.getSum() %></center></td>
			</tr>
		<% } %>
	</table>
<% } %>