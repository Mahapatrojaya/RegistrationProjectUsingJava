package com.registration;


	import java.io.IOException;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;

	import javax.servlet.RequestDispatcher;
	import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import javax.servlet.http.HttpSession;
	@WebServlet("/Login")
	public class Login extends HttpServlet {
		private static final long serialVersionUID=1L;
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			String uemail=req.getParameter("username");
			String upwd=req.getParameter("password");
			RequestDispatcher dispatcher=null;
			HttpSession session=req.getSession();
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube?useSSL=false","root","root");
				PreparedStatement pst=con.prepareStatement("select * from users where uemail = ? and upwd = ?");
				pst.setString(1,  uemail);
				pst.setString(2, upwd);
				
				ResultSet rs=pst.executeQuery();
				if(rs.next()) {
					session.setAttribute("name", rs.getString("uname"));
					dispatcher=req.getRequestDispatcher("index.jsp");	
				}else {
					req.setAttribute("status", "failed");
					dispatcher=req.getRequestDispatcher("login.jsp");
				}
				dispatcher.forward(req, resp);
			}catch(Exception e) {
				e.printStackTrace();
				
			}
		}

	}

