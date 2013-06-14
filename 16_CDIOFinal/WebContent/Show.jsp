<jsp:useBean id="s" class="web.Session" scope="session"/>
<jsp:setProperty name="s" property="*"/>
<b>Test: </b><i><%= s.loggedIn() %></i>