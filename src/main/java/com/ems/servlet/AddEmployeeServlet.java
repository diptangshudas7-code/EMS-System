package com.ems.servlet;

import com.ems.dao.EmployeeDAO;
import com.ems.model.Employee;
import com.ems.util.EmailUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/addEmployee")
public class AddEmployeeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String dept = req.getParameter("department");
        double salary = Double.parseDouble(req.getParameter("salary"));
        String email = req.getParameter("email");
        String username = req.getParameter("username");

        Employee emp = new Employee(0, name, dept, salary, email, username);
        EmployeeDAO dao = new EmployeeDAO();
        if (dao.add(emp)) {
            EmailUtil.sendEmail(email, "Welcome to EMS",
                "Hi " + name + ", your employee profile has been created.");
            res.sendRedirect("employees?page=1&sortBy=name");
        } else {
            req.setAttribute("error", "Failed to add employee.");
            req.getRequestDispatcher("addEmployee.jsp").forward(req, res);
        }
    }
}