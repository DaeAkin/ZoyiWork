package work;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import work.dto.Key;

@WebServlet({"/keys/*/translate/*" })
public class TranslateController extends HttpServlet{

	
	Gson _gson = new Gson();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String pathInfo = request.getPathInfo();

		if(pathInfo == null || pathInfo.equals("/")){

			StringBuilder buffer = new StringBuilder();
		    BufferedReader reader = request.getReader();
		    String line;
		    while ((line = reader.readLine()) != null) {
		        buffer.append(line);
		    }
		    
		    String payload = buffer.toString();
		    
		    Key key = _gson.fromJson(payload, Key.class);
		    dao.addkey(key);
		    key = dao.getOneKey(key.getName());

		    
		    Map<String, Object> resultMap = new HashMap<String, Object>();
		    
		    resultMap.put("key", key);
		    sendAsJson(response, resultMap);
		}
		else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
	}
	
}
