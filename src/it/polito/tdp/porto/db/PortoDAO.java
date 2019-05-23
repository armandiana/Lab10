package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id, Map<Integer, Author>idMap) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				if(idMap.get(id)==null) {
				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				idMap.put(autore.getId(), autore);
				return autore;
				
				}else {
					return idMap.get(id);
				}
			}
			conn.close();
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid, Map<Integer, Paper>idMap) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				if(idMap.get(eprintid)==null) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				idMap.put(paper.getEprintid(), paper);
				return paper;
				}
				else {
					return idMap.get(eprintid);
				}
			}
			conn.close();
			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<Author> getAutori(Map<Integer, Author>idMap){
		String sql=" SELECT *\n" + 
				"FROM author\n" + 
				"WHERE id IN ( SELECT authorid\n" + 
				"FROM creator)";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs=st.executeQuery();
			
			List<Author> result = new ArrayList<Author>();
			
			while(rs.next()) {
				
				if(idMap.get(rs.getInt("id"))==null) {
					
					Author a= new Author(rs.getInt("id"), rs.getString("lastname"),rs.getString("firstname"));
					result.add(a);
					idMap.put(a.getId(), a);
					
				}else {
					
					result.add(idMap.get(rs.getInt("id")));
				}
			}
			conn.close();
			return result;
			
	} catch (SQLException e) {
		 e.printStackTrace();
		throw new RuntimeException("Errore Db");
	}
  }
	
	public List<Author> getCoautori(Author a, Map<Integer, Author>idMap){
		String sql="SELECT DISTINCT a.id, a.lastname, a.firstname\n" + 
				"FROM creator c1,creator c2, author a\n" + 
				"WHERE c1.eprintid=c2.eprintid\n" + 
				"AND c2.authorid=a.id\n" + 
				"AND c1.authorid=?\n" + 
				"AND a.id<>c1.authorid";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, a.getId());
			
			ResultSet rs=st.executeQuery();
			
			List<Author> result = new ArrayList<Author>();
			
			while(rs.next()) {
				if(idMap.get(rs.getInt("a.id"))==null) {
					
					Author coauthor= new Author(rs.getInt("a.id"), rs.getString("a.lastname"),rs.getString("a.firstname"));
					
					result.add(coauthor);
					idMap.put(coauthor.getId(), coauthor);
				
				}else {
					
					result.add(idMap.get(rs.getInt("a.id")));
				}
			}
			conn.close();
			return result;
				
	}catch (SQLException e) {
		 e.printStackTrace();
		throw new RuntimeException("Errore Db");
	}
 }
	
	/*public Integer archi(Author a1, Author a2) {
		String sql="SELECT COUNT(*)\n" + 
				"FROM creator c1,creator c2\n" + 
				"WHERE c1.eprintid=c2.eprintid\n" + 
				"AND c1.authorid=?\n" + 
				"AND c2.authorid=? ";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, a1.getId());
			st.setInt(2, a2.getId());
			
			 ResultSet rs=st.executeQuery();
			 
			 if(rs.next()) {
				 Integer num=rs.getInt("COUNT(*)");
				 return num;
			 }
			 
			 conn.close();
			 return null;
			 
		}catch (SQLException e) {
			 e.printStackTrace();
				throw new RuntimeException("Errore Db");
			}	 
	}*/
	
	
	
	

	
	
}