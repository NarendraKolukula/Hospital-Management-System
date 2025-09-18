package com.hospitalmanagementsystem;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		
		if(email == null || pwd == null || email.trim().isEmpty() || pwd.trim().isEmpty()) {
			showError(response, out, "Email and password are required");
			return;
		}
		
		Connection c = GetConnection.getConnection();
		if(c == null) {
			showError(response, out, "Database connection failed");
			return;
		}
		
		String checkUserSql = "select * from assistant where email = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = c.prepareStatement(checkUserSql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			
			if(rs.next() && rs.getString("password").equals(pwd)) {
				HttpSession session = request.getSession();
				session.setAttribute("name", rs.getString("name"));
				session.setAttribute("aid", rs.getInt("aid"));
				response.sendRedirect("welcome.html");
			} else {
				showError(response, out, "Invalid email or password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			showError(response, out, "Database error: " + e.getMessage());
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void showError(HttpServletResponse response, PrintWriter out, String message) throws IOException {
		response.setContentType("text/html");
		out.println("<br><br><br><h1 align=center><font color=\"red\">" + message + "<br>REDIRECTING YOU TO LOGIN PAGE</font></h1>");
		out.println("<script type=\"text/javascript\">");
		out.println("setTimeout(function(){ window.location.href = 'login.html'; }, 3000);");
		out.println("</script>");
	}

}
