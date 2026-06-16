<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.ems.dao.EmployeeDAO, com.ems.model.*" %>
<%
  User user = (User) session.getAttribute("user");
  if(user == null) { response.sendRedirect("login.jsp"); return; }
  Employee emp = new EmployeeDAO().getByUsername(user.getUsername());
%>
<html>
<head>
<style>
    body { font-family: Arial, sans-serif; margin: 40px; }
    h2 { margin-bottom: 20px; }
    table { border-collapse: collapse; width: 50%; margin-top: 20px; }
    th, td { border: 1px solid #ccc; padding: 10px 16px; text-align: left; }
    th { background-color: #f2f2f2; width: 150px; }
    a { display: inline-block; margin-top: 20px; padding: 6px 16px;
        background: #d9534f; color: white; text-decoration: none; border-radius: 4px; }
</style>
</head>
<body>
<h2>Welcome, <%= emp.getName() %></h2>

<table>
  <tr><th>Department</th><td><%= emp.getDepartment() %></td></tr>
  <tr><th>Salary</th>    <td><%= emp.getSalary() %></td></tr>
  <tr><th>Email</th>     <td><%= emp.getEmail() %></td></tr>
  <tr><th>Username</th>  <td><%= emp.getUsername() %></td></tr>
</table>

<a href="logout">Logout</a>
</body>
</html>