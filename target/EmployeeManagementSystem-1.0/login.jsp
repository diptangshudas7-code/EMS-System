<%@ page contentType="text/html;charset=UTF-8" %>
<html><body>
<h2>EMS Login</h2>
<% if(request.getAttribute("error") != null) { %>
  <p style="color:red"><%= request.getAttribute("error") %></p>
<% } %>
<form action="login" method="POST">
    <label>Username:</label>
    <input type="text" name="username" required /> 
    <label>Password:</label>
    <input type="password" name="password" required />
    
    <button type="submit">Login</button>
</form>
</body></html>