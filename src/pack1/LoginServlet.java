package pack1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends GenericServlet implements Servlet {
	private static final long serialVersionUID = 1L;
      Connection con = null; 
    /**
     * @see GenericServlet#GenericServlet()
     */
    public LoginServlet() {
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
		pstmt = con.prepareStatement("select username,password from register where username=? and password=?");
		pstmt.setString(1,username);
		pstmt.setString(2,password);
		ResultSet rs = pstmt.executeQuery();
		PrintWriter pw = response.getWriter();
		do
		{
			if(rs.next())
			{
			pw.println("<html><body><h2>");
			pw.println("Welcome"+""+rs.getString(1));
			pw.println("</h2></body></html>");	
			}
			else
			{
				 pw.println("<html><body><h2>Invalid username or password!</h2></body></html>");
				
				 RequestDispatcher rd = request.getRequestDispatcher("login.html");
				 
				 rd.include(request, response);
			}
		}while(rs.next());
		

		
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
