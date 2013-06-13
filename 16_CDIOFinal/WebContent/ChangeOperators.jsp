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

			try {
		int oprId = s.getOperator().getOprId();
		if (oprId < 1) {
			errors.add("oprId er mindre end 1.");
		}
		if (oprId > 99999999) {
			errors.add("oprId er større end 99999999.");
		}
			} catch (Exception e) {
		errors.add("Identifikation er ikke et tal.");
			}

			int oprId = s.getOperator().getOprId();
			String oprName = s.getOperator().getOprName();
			String ini = s.getOperator().getIni();
			String cpr = s.getOperator().getCpr();
			String password = s.getOperator().getPassword();
			int rights = s.getOperator().getRights();

			if (!oprName.matches("^.{2,20}$")) {
		errors.add("Navn er ugyldig.");
			}
			if (!ini.matches("^.{2,3}$")) {
		errors.add("Initialer er ugyldig.");
			}
			if (!cpr.matches("^[0-9]{10}$")) {
		errors.add("CPR er ugyldig.");
			}
			if (!password.matches("^.{7,8}$")) {
		errors.add("Adgangskode er ugyldig.");
		}
		if (errors.size() == 0) {
			session.addOperator(oprId, oprName, ini, cpr, password, rights);
			

		} else {
			session.updateOperator(oprId, oprName, ini, cpr, password,
					rights);
		}
		response.sendRedirect("UserAdministration.jsp");

		for (String line : errors) {
	%>
		<p><%= line %></p>
	<%
		}
	%>
		<p><a href="javascript:history.go(-1)">Tilbage</a></p>
	</body>
</html>