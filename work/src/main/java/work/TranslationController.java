package work;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import work.dto.Key;

@WebServlet(urlPatterns={ "/keys/*/translations/*" })
public class TranslationController extends HttpServlet {

	Gson _gson = new Gson();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			String pathInfo = request.getPathInfo();
			
			String[] splits = pathInfo.split("/");
			
			System.out.println(splits[0]);
			System.out.println(splits[1]);
			System.out.println(splits[2]);
			System.out.println(splits[3]);


			StringBuilder buffer = new StringBuilder();
		    BufferedReader reader = request.getReader();
		    String line;
		    while ((line = reader.readLine()) != null) {
		        buffer.append(line);
		    
		    
		    String payload = buffer.toString();
		    
//		    Key key = _gson.fromJson(payload, Key.class);
//		    dao.addkey(key);
//		    key = dao.getOneKey(key.getName());

		    
		    Map<String, Object> resultMap = new HashMap<String, Object>();
		    
		    resultMap.put("key", key);
		    sendAsJson(response, resultMap);
		}

		
	}
	
	
	private void sendAsJson(
			HttpServletResponse response, 
			Object obj) throws IOException {
			
			response.setContentType("application/json");
			
			String res = _gson.toJson(obj);
			     
			PrintWriter out = response.getWriter();
			  
			out.print(res);
			out.flush();
		}

}
