<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.ems.model.*, java.util.*" %>
<%
  if(!"admin".equals(session.getAttribute("role"))) { response.sendRedirect("login.jsp"); return; }
  List<Employee> employees = (List<Employee>) request.getAttribute("employees");
  int currentPage  = (int) request.getAttribute("currentPage");
  int totalPages   = (int) request.getAttribute("totalPages");
  String sortBy    = (String) request.getAttribute("sortBy");
%>
<html>
<head>
<style>
    body { font-family: Arial, sans-serif; margin: 40px; }
    h2 { margin-bottom: 20px; }
    a { margin-right: 10px; }
    table { border-collapse: collapse; width: 80%; margin-top: 20px; }
    th, td { border: 1px solid #ccc; padding: 10px 16px; text-align: left; }
    th { background-color: #f2f2f2; }
    tr:nth-child(even) { background-color: #fafafa; }
    .pagination { margin-top: 16px; }
    .pagination a { padding: 6px 12px; border: 1px solid #ccc; 
                    text-decoration: none; margin-right: 4px; }
    .actions a { margin-right: 8px; }
</style>
</head>
<body>
<h2>Admin Dashboard</h2>
<a href="addEmployee.jsp">Add Employee</a> | <a href="logout">Logout</a>

<table>
  <tr>
    <th><a href="employees?page=1&sortBy=name">Name</a></th>
    <th><a href="employees?page=1&sortBy=department">Department</a></th>
    <th><a href="employees?page=1&sortBy=salary">Salary</a></th>
    <th>Email</th>
    <th>Actions</th>
  </tr>
  <% for(Employee e : employees) { %>
  <tr>
    <td><%= e.getName() %></td>
    <td><%= e.getDepartment() %></td>
    <td><%= e.getSalary() %></td>
    <td><%= e.getEmail() %></td>
    <td class="actions">
      <a href="editEmployee.jsp?id=<%= e.getId() %>&name=<%= e.getName() %>&dept=<%= e.getDepartment() %>&salary=<%= e.getSalary() %>&email=<%= e.getEmail() %>">Edit</a>
      <a href="deleteEmployee?id=<%= e.getId() %>" onclick="return confirm('Delete this employee?')">Delete</a>
    </td>
  </tr>
  <% } %>
</table>

<div class="pagination">
  <% for(int i=1; i<=totalPages; i++) { %>
    <a href="employees?page=<%= i %>&sortBy=<%= sortBy %>"><%= i %></a>
  <% } %>
</div>
</body>
</html>