package work;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;import javax.swing.text.html.HTML.Tag;

import com.detectlanguage.DetectLanguage;
import com.detectlanguage.errors.APIError;
import com.google.gson.Gson;

@WebServlet("/language_detect/*")
public class LanguageController extends HttpServlet{

	Gson _gson = new Gson();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DetectLanguage.apiKey = "61e15b054320d70a59f3597ab27a3541";
		
		String[] splits = null;
		
		
		String pathInfo = request.getPathInfo();
		splits = pathInfo.split("/");
		
		String target = splits[1];
		System.out.println("target : " + target);
		
		Map<String, Object> resultMap = new HashMap<>();
		String language = null;
		try {
			language = DetectLanguage.simpleDetect(target);
		} catch (APIError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultMap.put("locale", language);
		
		sendAsJson(response, resultMap);
		
		
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
