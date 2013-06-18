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

<%=s.getdate()%>
<br>
Produkt Batch nr. <%=s.getProductBatches("Citron", "Salt")%><br/>
Recept nr. <%=s.getRecipes("saltvand")%>





<br>

<body>
	<%
		for (Recipe recipe : s.getRecipes(request.getParameter("filterCommodityId"))) {
	%>

	<br>
	Recept id: <%=recipe.getRecipeId()%><br>
	Recept name:  <%=recipe.getRecipeName()%><br>

	<%
		}
	%>
	
	<br>
	Sum Tara: <%= s.toString() %><br>
	Sum Netto: <%= s.toString() %><br>
	
	<br>
	
	Produktion Startet: <%= s.getdate() %><br>
	Produktion Slut:	<%= s.getdate() %><br>
	
</body>
</html>