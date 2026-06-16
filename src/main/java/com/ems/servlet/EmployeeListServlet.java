package com.ems.servlet;

import com.ems.dao.EmployeeDAO;
import com.ems.model.Employee;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/employees")
public class EmployeeListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            res.sendRedirect("login.jsp"); return;
        }

        int page = 1, pageSize = 5;
        String sortBy = req.getParameter("sortBy") != null ? req.getParameter("sortBy") : "name";
        try { page = Integer.parseInt(req.getParameter("page")); } catch (Exception ignored) {}

        EmployeeDAO dao = new EmployeeDAO();
        List<Employee> list = dao.getAll(page, pageSize, sortBy);
        int total = dao.getTotalCount();
        int totalPages = (int) Math.ceil((double) total / pageSize);

        req.setAttribute("employees", list);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("sortBy", sortBy);
        req.getRequestDispatcher("adminDashboard.jsp").forward(req, res);
    }
}