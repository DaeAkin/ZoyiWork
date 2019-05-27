package work.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import work.dto.Key;
import work.dto.Translation;

public class TranslateDao extends DAOBase {

	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	//키의 모든 번역 확인하기 
	public List<Translation> getAllTranslation() {
		System.out.println("--- translate() ----");
		ArrayList<Translation> list = new ArrayList<Translation>();
		
		try {
			conn = getConnection();
			stmt = conn.createStatement(); 
			String query = "";
			query = "select * from zoyitranslation";
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				
				Translation tl = new Translation();
				tl.setId(rs.getInt(1));
				tl.setKeyId(rs.getInt(2));
				tl.setLocale(rs.getString(3));
				tl.setValue(rs.getString(4));
				list.add(tl);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeDBResources(rs, stmt, pstmt, conn);
		}
		
		return list;
		
	}
	
	//키의 특정 번역 확인하기
	public List<Translation> getAllTranslatioWithOneKey(int keyId) {
		System.out.println("--- translate() ----");
		ArrayList<Translation> list = new ArrayList<Translation>();
		
		try {
			conn = getConnection();
			stmt = conn.createStatement(); 
			String query = "";
			query = "select * from zoyitranslation where keyId ="+keyId;
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				
				Translation tl = new Translation();
				tl.setId(rs.getInt(1));
				tl.setKeyId(rs.getInt(2));
				tl.setLocale(rs.getString(3));
				tl.setValue(rs.getString(4));
				list.add(tl);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeDBResources(rs, stmt, pstmt, conn);
		}
		
		return list;
		
	}
	
	// 번역 추가하기 
	public int addTranslation(Translation tl) {
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("insert into zoyitranslation(keyId,locale,value) values(?,?,?)");
			pstmt.setInt(1, tl.getKeyId());
			pstmt.setString(2, tl.getLocale());
			pstmt.setString(3, tl.getValue());
			pstmt.executeUpdate();
		
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeDBResources(rs, stmt, pstmt, conn);
		}
		return 0;
		
	}
	
	//키의 특정 번역 수정하기
	public void updateTranslation(String willChangedValue,int keyId) {
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("update zoyikey set name=? where name=?");
			pstmt.setString(1, rename);
	    	pstmt.setString(2, target);
	    	return 	pstmt.executeUpdate();
	    	 
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				 closeDBResources(rs, stmt, pstmt, conn);
			}
			return 0;
	}
	
}
