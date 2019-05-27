package work.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.sym.Name;

import work.dto.Key;

public class KeyDao extends DAOBase{
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public List<Key> getAllKeys() {
		System.out.println("----- getAllKeys() ------");
		ArrayList<Key> list = new ArrayList<Key>();
		
		try {
			conn = getConnection();
			stmt = conn.createStatement(); 
			String query = "";
			query = "select * from zoyikey";
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				System.out.println("데이터 ㄱ");
				Key keys = new Key();
				keys.setId(rs.getInt(1));
				keys.setName(rs.getString(2));
				System.out.println(rs.toString());
				list.add(keys);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeDBResources(rs, stmt, pstmt, conn);
		}
	
		return list;
	}
	
	public int addkey(Key key) {
		try {
			System.out.println("add 할 key :" + key.getName());
			conn = getConnection();
			pstmt = conn.prepareStatement("insert into zoyikey(name) values(?)");
			pstmt.setString(1, key.getName());
			 pstmt.executeUpdate();
			 System.out.println("Aa'");
			System.out.println("?? : " + pstmt.getGeneratedKeys());
			System.out.println("bbbb"+pstmt.getGeneratedKeys().getInt(1));
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeDBResources(rs, stmt, pstmt, conn);
		}
		return 0;
		
	}
	
	public int updateKey(String target , String rename) {
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
	
	public Key getOneKey(String name) {
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select * from zoyikey where name=?");
			pstmt.setString(1, name);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				
				Key keys = new Key();
				keys.setId(rs.getInt(1));
				keys.setName(rs.getString(2));
				System.out.println(rs.toString());
				return keys;
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeDBResources(rs, stmt, pstmt, conn);
		}
		return null;
	}
		
}


	

