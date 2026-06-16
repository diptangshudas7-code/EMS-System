<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<style>
    body { font-family: Arial, sans-serif; margin: 40px; }
    h2 { margin-bottom: 20px; }
    table { border-spacing: 0 12px; }
    td { padding: 4px 10px; }
    input { padding: 6px; width: 250px; }
    button { padding: 6px 16px; margin-right: 10px; }
    a { padding: 6px 16px; }
</style>
</head>
<body>
<h2>Edit Employee</h2>
<% if(request.getAttribute("error") != null) { %>
  <p style="color:red"><%= request.getAttribute("error") %></p>
<% } %>
<form method="post" action="updateEmployee">
  <input type="hidden" name="id" value="<%= request.getParameter("id") %>"/>
  <table>
    <tr><td>Name:</td>       <td><input name="name"       value="<%= request.getParameter("name") %>"/></td></tr>
    <tr><td>Department:</td> <td><input name="department" value="<%= request.getParameter("dept") %>"/></td></tr>
    <tr><td>Salary:</td>     <td><input name="salary"     value="<%= request.getParameter("salary") %>"/></td></tr>
    <tr><td>Email:</td>      <td><input name="email"      value="<%= request.getParameter("email") %>" type="email"/></td></tr>
  </table>
  <button type="submit">Update</button>
  <a href="employees?page=1&sortBy=name">Cancel</a>
</form>
</body>
</html>