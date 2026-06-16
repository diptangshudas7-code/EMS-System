package com.ems.servlet;

import com.ems.dao.EmployeeDAO;
import com.ems.model.Employee;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/updateEmployee")
public class UpdateEmployeeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String dept = req.getParameter("department");
            double salary = Double.parseDouble(req.getParameter("salary"));
            String email = req.getParameter("email");
            String username = req.getParameter("username");

            Employee emp = new Employee(id, name, dept, salary, email, username);
            EmployeeDAO dao = new EmployeeDAO();
            if (dao.update(emp)) {
                res.sendRedirect("employees?page=1&sortBy=name");
            } else {
                req.setAttribute("error", "Failed to update employee.");
                req.getRequestDispatcher("editEmployee.jsp").forward(req, res);
            }
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid input format.");
            req.getRequestDispatcher("editEmployee.jsp").forward(req, res);
        }
    }
}