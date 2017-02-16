// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class ReportServlet extends HttpServlet {  // JDK 1.6 and above only
 
   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
 
      Connection conn = null;
      Statement stmt = null;
      try {
         // Step 1: Allocate a database Connection object
         conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/ebookshop?useSSL=false", "myuser", "xxxx"); // <== Check!
            // database-URL(hostname, port, default database), username, password
 
         // Step 2: Allocate a Statement object within the Connection
         stmt = conn.createStatement();
 
         // Step 3: Execute a SQL SELECT query
         String sqlStr = "select * from ebookshop";
 
         ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server
 
         // Step 4: Process the query result set
         int count = 0;
         /* setup html head page */
         out.println("<html>"
                  +"<head>"
                    +"<title> Report about time using JRD machine  </title>"
                    +"<style>" 
                      +"table, th, td {border: 1px solid black}"
                      +"th, td {width: 200px}"
                      +"tr {height: 50px}"
                      +"th {color: green}"
                    +"</style>"
                  +"</head>"
                  +"<body>"
                  +"<table>"
                    +"<h1> Report about time using JRD machine </h1>"
                    +"<form method=\"get\" action=\"http://localhost:9999/hello/Jrd_report\">"
                      +"<input type=\"submit\" value=\"Update\">"
                    +"</form>"
                    +"<hr>"
                    +"<thead>"
                      +"<tr> <th>Name</th> <th>Injury</th> <th>Date</th> <th>Time</th> <th>Duration</th> </tr>"
                    +"</thead>"
                    +"<tbody>");

         /* insert all row in body */
         while(rset.next()) {
            // Print a paragraph <p>...</p> for each record
            out.println("<tr><td>" + rset.getString("name") + "</td>"
                 + "<td>" + rset.getString("injury") + "</td>"
                 + "<td>" + "</td>"
                 + "<td>" + "</td>"                 
                 + "<td>" + rset.getInt("duration") + "</td></tr>");
            count++;
         }
         out.println("</tbody></table>");
         /* summary record and end body */
         out.println("<p>==== " + count + " records found =====</p>");
         out.println("</body></html>");
     } catch (SQLException ex) {
        ex.printStackTrace();
     } finally {
        out.close();  // Close the output writer
        try {
           // Step 5: Close the resources
           if (stmt != null) stmt.close();
           if (conn != null) conn.close();
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
     }
   }
}