package com.hospitalmanagementsystem;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CreateMedicine")
public class CreateMedicine extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public CreateMedicine() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		PrintWriter out = response.getWriter();
		try {
			
			Connection c = GetConnection.getConnection();
			String  name = request.getParameter("name");
			String  price = request.getParameter("price");
			String  count = request.getParameter("count");
			
			String sql = "insert into medicine(name,price,count) values(?,?,?)";
			
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, price);
			ps.setString(3, count);
			ps.addBatch();
			
			int successCount = 0;
			successCount += ps.executeBatch()[0];
			ps.clearBatch();
			
			if(successCount == 1) {
				response.setContentType("text/html");  
				out.println("<br><br><br><h1 align=center><font color=\"green\">SUCCESSFUL<br></font></h1><script type=\"text/javascript\">");  
//				out.println("redirectURL = \"welcome.html\";setTimeout(\"location.href = redirectURL;\",\"5000\");");  
				out.println("</script>");
			}
			else {
				response.setContentType("text/html");  
				out.println("<br><br><br><h1 align=center><font color=\"red\">THERE IS PROBLEM<br></font></h1><script type=\"text/javascript\">");  
//				out.println("redirectURL = \"welcome.html\";setTimeout(\"location.href = redirectURL;\",\"5000\");");  
				out.println("</script>");
			}
		} catch (SQLException e) {
			response.setContentType("text/html");  
			out.println("<br><br><br><h1 align=center><font color=\"red\">THERE IS PROBLEM<br></font></h1><script type=\"text/javascript\">");  
//			out.println("redirectURL = \"welcome.html\";setTimeout(\"location.href = redirectURL;\",\"5000\");");  
			out.println("</script>");
			e.printStackTrace();
		}
	}

}
