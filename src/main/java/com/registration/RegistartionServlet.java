package com.registration;


	import java.io.IOException;
	import java.io.PrintWriter;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.SQLException;

	import javax.servlet.Registration;
	import javax.servlet.RequestDispatcher;
	import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;


	@WebServlet("/register")
	public class RegistartionServlet extends HttpServlet {
		private static final long serialVersionUID=1L;
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String uname =req.getParameter("name");
			String uemail =req.getParameter("email");
			String upwd =req.getParameter("pass");
			String umobile =req.getParameter("contact");
			
			RequestDispatcher dispatcher=null;
			Connection con=null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube?useSSL=false","root","root");
				PreparedStatement pst=con.prepareStatement("insert into users(uname,upwd,uemail,umobile) values(?,?,?,?) ");
				pst.setString(1, uname);
				pst.setString(2, upwd);
				pst.setString(3, uemail);
				pst.setString(4, umobile);
				
				int rowCount=pst.executeUpdate();
				dispatcher=req.getRequestDispatcher("registration.jsp");
				if(rowCount >0) {
					req.setAttribute("status", "success");
				}else {
					req.setAttribute("status", "failed");
				}
				dispatcher.forward(req, resp);
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}

	}



