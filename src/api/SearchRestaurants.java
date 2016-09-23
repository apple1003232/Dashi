package api;

import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.MongoDBConnection;
import db.MySQLDBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SearchRestaurants
 */
@WebServlet("/restaurants")
public class SearchRestaurants extends HttpServlet {
    private static final long serialVersionUID = 1L;

     protected void doGet(HttpServletRequest request,
   		 HttpServletResponse response) throws ServletException, IOException {
    	// allow access only if session exists
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null
				|| !request.getParameter("user_id").equals((String) session.getAttribute("user"))) {
 			response.setStatus(403);
 			return;
 		}

   	              JSONArray array = new JSONArray();
		DBConnection connection = new MySQLDBConnection();
//   	           DBConnection connection = new MongoDBConnection();
		if (request.getParameterMap().containsKey("user_id")
				&& request.getParameterMap().containsKey("lat")
				&& request.getParameterMap().containsKey("lon")) {
			String userId = request.getParameter("user_id");
			double lat = Double.parseDouble(request.getParameter("lat"));
			double lon = Double.parseDouble(request.getParameter("lon"));
			array = connection.searchRestaurants(userId, lat, lon);
		}
		RpcParser.writeOutput(response, array);
    }
    
}
