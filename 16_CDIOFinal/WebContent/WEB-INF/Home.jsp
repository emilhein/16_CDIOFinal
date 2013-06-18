<%@ page import="database_objects.CommodityBatch" %>
<%@ page import="special_objects.Commodity_Sum" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>

<h2>Messages for you!</h2>
<% if (s.getRights() <= 3 && s.getLowCommodityBatches(3) != null) { %>
	<h3>Low quantity commodities</h3>
	<table>
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Quantity</th>
		</tr>
		<% for(Commodity_Sum commoditySum : s.getLowCommodityBatches(3)) { %>
			<tr>
				<td><center><%= commoditySum.getCommodityId() %></center></td>
				<td><center><%= commoditySum.getCommodityName() %></center></td>
				<td><center><%= commoditySum.getSum() %></center></td>
			</tr>
		<% } %>
	</table>
<% } %>
<br/>
<br/>
<% if (s.getRights() <= 3 && s.getLowCommodityBatches(0.0001) != null) { %>
<h3>Empty Commodities</h3>
<table>
	<tr>
		<th>Id</th>
		<th>Name</th>
		<th>Quantity</th>
	</tr>
	<% for(Commodity_Sum commoditySum :s.getLowCommodityBatches(0.0001)) { %>
		<tr>
			<td><center><%= commoditySum.getCommodityId() %></center></td>
			<td><center><%= commoditySum.getCommodityName() %></center></td>
			<td><center><%= commoditySum.getSum() %></center></td>
		</tr>
	<% } %>
</table>
<% } %>