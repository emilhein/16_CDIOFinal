<%@ page import="database_objects.CommodityBatch" %>
<%@ page import="special_objects.Commodity_Sum" %>
<%@ page import="java.util.List" %>
<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>

<%
	List<Commodity_Sum> lowCommodityBatches = s.getLowCommodityBatches(3);
	List<Commodity_Sum> emptyCommodityBatches = s.getLowCommodityBatches(0.0001);
%>

<h2>Messages for you!</h2>
<% if (s.getRights() <= 3 && lowCommodityBatches != null) { %>
	<% if (s.getLowCommodityBatches(3) != null) { %>
		<h3>Low quantity commodities</h3>
		<table>
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Quantity</th>
			</tr>
			<% for(Commodity_Sum commoditySum : lowCommodityBatches) { %>
				<tr>
					<td><center><%= commoditySum.getCommodityId() %></center></td>
					<td><center><%= commoditySum.getCommodityName() %></center></td>
					<td><center><%= commoditySum.getSum() %></center></td>
				</tr>
			<% } %>
		</table>
	<% } %>
	<% if (lowCommodityBatches != null && emptyCommodityBatches != null) { %>
		<br>
	<% } %>
	<% if (emptyCommodityBatches != null) { %>
		<h3>Empty Commodities</h3>
		<table>
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Quantity</th>
			</tr>
			<% for(Commodity_Sum commoditySum : emptyCommodityBatches) { %>
				<tr>
					<td><center><%= commoditySum.getCommodityId() %></center></td>
					<td><center><%= commoditySum.getCommodityName() %></center></td>
					<td><center><%= commoditySum.getSum() %></center></td>
				</tr>
			<% } %>
		</table>
	<% } %>
<% } %>