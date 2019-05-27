package work;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.maven.model.Model;

import com.google.gson.Gson;

import net.minidev.json.JSONUtil;
import work.dao.KeyDao;
import work.dto.Key;
@WebServlet({"/keys" , "/keys/*"})
public class MainController extends HttpServlet{
	
	KeyDao dao = new KeyDao();
	
	
	Gson _gson = new Gson();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("hello");
		ArrayList<Key> list = (ArrayList<Key>) getAllKeys();
		for (Key key : list) {
			System.out.println(key.getId());
			System.out.println(key.getName());
			
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("key", list);
		_gson.toJson(resultMap);
		
		sendAsJson(resp, resultMap);
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
	
	@Override
	protected void doPost(
			HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {

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
	
	
	@Override
	protected void doPut(
			HttpServletRequest request,
			HttpServletResponse response) 
					throws IOException, ServletException {
		
		System.out.println("--- dopost ---");
		String pathInfo = request.getPathInfo();
		System.out.println("pathInfo : " + pathInfo);
		String[] splits = pathInfo.split("/");
		
		String rename = splits[1];

		StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	    }
	    
	    String payload = buffer.toString();
	   
	    Key key = _gson.fromJson(payload, Key.class);
	    
	    dao.updateKey(key.getName(),rename);
	    
	    // 다시 그 key로 get 해서 넘겨줘야함
	   key = dao.getOneKey(rename);
	    
	    
	    
	    Map<String, Object> resultMap = new HashMap<String, Object>();
	    
	    resultMap.put("key", key);
	    sendAsJson(response, resultMap);
	}
	
	
	
	public List<Key> getAllKeys() {
		return dao.getAllKeys();
	}
	

	

	
	
}
