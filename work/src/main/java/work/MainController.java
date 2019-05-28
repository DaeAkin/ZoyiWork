package work;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.kerberos.KerberosTicket;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import work.dao.KeyDao;
import work.dao.TranslationDao;
import work.dto.Key;
import work.dto.Translation;
@WebServlet({"/keys" , "/keys/*" })
public class MainController extends HttpServlet{
	
	KeyDao dao = new KeyDao();
	TranslationDao tDao = new TranslationDao(); 
	
	Gson _gson = new Gson();
	

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] splits = null;
		System.out.println("--- post() ---");
		
		String pathInfo = request.getPathInfo();
		
		if(pathInfo != null) {
		splits = pathInfo.split("/");
		} else {
			splits = new String[1];
		}
		
//		_gson.toJson(resultMap);
		if(splits.length == 1) {
		sendAsJson(response, getAllKey());
		// 검색
		}else if(splits.length == 2 ) {
			sendAsJson(response, searchWithName(splits[1]));
		}
		else if (splits.length == 3) {
		
			System.out.println("키로만 찾음");
			sendAsJson(response,getAllTranslationWithKey(splits));
		}else if(splits.length == 4) {
			System.out.println("키하고 locale로 찾음");
			sendAsJson(response, getAllTranslationsWithKeys(splits));
			
		}
	}
	private Map<String, Object> getAllKey() {
		List<Key> list = dao.getAllKeys();
//		for (Key key : list) {
//			System.out.println(key.getId());
//			System.out.println(key.getName());	
//		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("key", list);
		return resultMap;
	}
	
	private Map<String, Object> searchWithName(String searchName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("key", dao.getOneKey(searchName));
		return resultMap;
	}
	private Map<String, Object> getAllTranslationWithKey(String[] urls) {
		List<Translation> list = 
				tDao.getAllTranslationWithKey(Integer.parseInt(urls[1]));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("translations", list);
		return resultMap;
	}
	private Map<String, Object> getAllTranslationsWithKeys(String[] urls) {
		Translation tl =
				tDao.getTranslatioWithOneKeyAndWtihOneLocale(Integer.parseInt(urls[1]), urls[3]);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("translations", tl);
		return resultMap;
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
			String[] splits = null;
			System.out.println("--- post() ---");
			
			String pathInfo = request.getPathInfo();
			
			if(pathInfo != null) {
			splits = pathInfo.split("/");
			} else {
				splits = new String[1];
			}
			//바디 읽기
			StringBuilder buffer = new StringBuilder();
		    BufferedReader reader = request.getReader();
		    String line;
		    while ((line = reader.readLine()) != null) {
		        buffer.append(line);
		    }
		    
		    String payload = buffer.toString();
		    if(splits.length > 2) {
		    	sendAsJson(response, postTranslationVerb(payload, splits));
		    } else {
			sendAsJson(response, postKeyVerb(payload));
		    
		    }
		  
		  
	}
	
	private Map<String, Object> postKeyVerb(String payload) {
		  Key key = _gson.fromJson(payload, Key.class);
		    dao.addkey(key);
		    key = dao.getOneKey(key.getName());	    
		    Map<String, Object> resultMap = new HashMap<String, Object>();	 
		    resultMap.put("key", key);
		    return resultMap;
	}
	
	private Map<String, Object> postTranslationVerb(String payload,String[] urls) {
			Translation tl = _gson.fromJson(payload, Translation.class);
			tl.setKeyId(Integer.parseInt(urls[1]));
			tl.setLocale(urls[3]);
			
		    tDao.addTranslation(tl);
		    Map<String, Object> resultMap = new HashMap<String, Object>();	 
		    resultMap.put("translation", tDao.getTranslatioWithOneKeyAndWtihOneLocale(Integer.parseInt(urls[1]), urls[3]));
		    
		    return resultMap;
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
		
		

		StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	    }
	    
	    String payload = buffer.toString();
	   if(splits.length >2) {
		   sendAsJson(response, putTranslationVerb(splits, payload));
		   // 번역쪽 
	   } else {
		   // key쪽
		   System.out.println("키수정 ㄱ");
		  sendAsJson(response, putKeyVerb(splits, payload)); 
	   }
	   
//	    sendAsJson(response, resultMap);
	}

	private Map<String, Object> putKeyVerb(String[] urls, String payload) {
		
		Key key = _gson.fromJson(payload, Key.class);
		dao.updateKey(Integer.parseInt(urls[1]), key.getName());
		System.out.println("바뀔 name : " + key.getName());
		// 다시 그 key로 get 해서 넘겨줘야함
		key = dao.getOneKey(key.getName());
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("key", key);
		
		
		return resultMap;
	}
	
	private Map<String, Object> putTranslationVerb(String[] urls, String payload) {
		int keyId = Integer.parseInt(urls[1]);
		String locale = urls[3];
		Translation tl = _gson.fromJson(payload, Translation.class);
		tDao.updateTranslation(tl.getValue(), keyId, locale);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
	resultMap.put("translation", tDao.getTranslatioWithOneKeyAndWtihOneLocale(keyId, locale));
		
	return resultMap;
		
	}
	
	
	 
	

	

	
	
}
