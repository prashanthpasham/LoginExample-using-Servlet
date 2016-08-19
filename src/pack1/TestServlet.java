package pack1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet implementation class TestServlet
 */
public class TestServlet extends GenericServlet implements Servlet {
	private static final long serialVersionUID = 1L;
      Connection con = null; 
    /**
     * @see GenericServlet#GenericServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
public void init()
{
	try
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
	con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
}
	/**
	 * @see Servlet#service(ServletRequest request, ServletResponse response)
	 */
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		
		PreparedStatement pstmt = null;
		try
		{
		String username = request.getParameter("uname");
		String password = request.getParameter("pwd");
		String email = request.getParameter("email");
		String ph = request.getParameter("phone");
		String phone = ph.trim();
		long mobile = Long.parseLong(phone);
		String address = request.getParameter("address");
		
		pstmt = con.prepareStatement("insert into register values(?,?,?,?,?)");
		pstmt.setString(2,username);
		pstmt.setString(3,password);
		pstmt.setString(1,email);
		pstmt.setLong(5,mobile);
		pstmt.setString(4,address);
		int i = pstmt.executeUpdate();
		
		PrintWriter pw = response.getWriter();
		if(i > 0)
		{
		pw.println("<html><body><h2>You are sucessfully registered!</h2></body></html>");
		 pw.print("you are successfully registered!");
            RequestDispatcher rd = request.getRequestDispatcher("/login.html");
           
            rd.forward(request, response);
	   }
		else
		{
			pw.println("<html><body><h2>Registration Failed!</h2></body></html>");
		}
		
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
			pstmt.close();
			//con.close();
			}catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}

}
