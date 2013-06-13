<jsp:useBean id="s" class="web.Session" scope="session" />
<jsp:setProperty name="s" property="*" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Operatøradministration</title>
	</head>
	<body>
	<%
		java.util.ArrayList<String> errors = new java.util.ArrayList<String>();
		Integer index = null;
		Integer identification = null;
		try {
			index = Integer.parseInt(request.getParameter("Index"));
		} catch (Exception e) {
			errors.add("Indeks er ikke et tal.");
		}
		try {
			identification = Integer.parseInt(request.getParameter("Identification"));
			if (identification < 1) {
				errors.add("Identifikation er mindre end 1.");
			}
			if (identification > 99999999) {
				errors.add("Identifikation er større end 99999999.");
			}
		} catch (Exception e) {
			errors.add("Identifikation er ikke et tal.");
		}
		String name = request.getParameter("Name");
		String initials = request.getParameter("Initials");
		String cpr = request.getParameter("CPR");
		String password = request.getParameter("Password");
		if (!name.matches("^.{2,20}$")) {
			errors.add("Navn er ugyldig.");
		}
		if (!initials.matches("^.{2,3}$")) {
			errors.add("Initialer er ugyldig.");
		}
		if (!cpr.matches("^[0-9]{10}$")) {
			errors.add("CPR er ugyldig.");
		}
		if (!password.matches("^.{7,8}$")) {
			errors.add("Adgangskode er ugyldig.");
		}
		if (errors.size() == 0) {
			if (index >= 0) {
				if (request.getParameter("button").equals("Fjern")) {
					s.removeOperator(index);
				} else {
					s.updateOperator(index, identification, name, initials, cpr, password);
				}
			} else {
				s.addOperator(identification, name, initials, cpr, password);
			}
			response.sendRedirect("OperatorAdministration.jsp");
		}
		for (String line : errors) {
	%>
		<p><%= line %></p>
	<%
		}
	%>
		<p><a href="javascript:history.go(-1)">Tilbage</a></p>
	</body>
</html>