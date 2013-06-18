<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="web.Page"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="database_objects.Recipe"%>
<jsp:useBean id="s" class="web.Session" scope="session" />
<jsp:setProperty name="s" property="*" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Produktion</title>
</head>



<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Produktion</title>

<div style="border-bottom: 1px black solid; width: 50%; margin-left: 25%;">
Udskrevet: <%= s.getDate() %>
<br>
Produkt Batch nr. <%= s.getProductBatches("Citron", "Salt") %><br/>
Recept nr. <%= s.getRecipes("saltvand") %>
</div>




<br>

<body>
	<%
		for (Recipe recipe : s.getRecipes(request.getParameter("filterCommodityId"))) {
	%>

	<br>
	<div style="border-bottom: 1px black solid; width: 50%; margin-left: 25%;">
	Recept id: <%=recipe.getRecipeId()%><br>
	Recept name:  <%=recipe.getRecipeName()%><br>
	</div>
	<%
		}
	%>
	
	<br>
	<div style="border-bottom: 1px black solid; width: 50%; margin-left: 25%;">
	Sum Tara:  test<% %><br>
	Sum Netto:  test1<% %><br>
	
	
	<br>
	Produktion Status: test<br>
	Produktion Startet: test1 <%  %><br>
	Produktion Slut:	test2 <% %><br>
	</div>
</body>
</html>