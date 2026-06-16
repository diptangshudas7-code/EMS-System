<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<style>
    body { font-family: Arial, sans-serif; margin: 40px; }
    h2 { margin-bottom: 20px; }
    table { border-spacing: 0 12px; }
    td { padding: 4px 10px; }
    input { padding: 6px; width: 250px; }
    button, a { margin-top: 10px; padding: 6px 16px; }
</style>
</head>
<body>
<h2>Add Employee</h2>
<% if(request.getAttribute("error") != null) { %>
  <p style="color:red"><%= request.getAttribute("error") %></p>
<% } %>
<form method="post" action="addEmployee">
  <table>
    <tr><td>Name:</td>      <td><input name="name"/></td></tr>
    <tr><td>Department:</td><td><input name="department"/></td></tr>
    <tr><td>Salary:</td>    <td><input name="salary"/></td></tr>
    <tr><td>Email:</td>     <td><input name="email" type="email"/></td></tr>
    <tr><td>Username:</td>  <td><input name="username"/></td></tr>
  </table>
  <button type="submit">Add</button>
  <a href="employees?page=1&sortBy=name">Cancel</a>
</form>
</body>
</html>