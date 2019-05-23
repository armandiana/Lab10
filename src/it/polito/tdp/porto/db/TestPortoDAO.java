package it.polito.tdp.porto.db;

import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Paper;

public class TestPortoDAO {
	
	public static void main(String args[]) {
		PortoDAO pd = new PortoDAO();
		Map<Integer, Author>mapA= new HashMap<Integer, Author>();
		Map<Integer, Paper>mapP= new HashMap<Integer, Paper>();

		
		System.out.println(pd.getAutore(85,mapA));
		System.out.println(pd.getArticolo(2293546,mapP));
		System.out.println(pd.getArticolo(1941144,mapP));
		
		System.out.println("\n***************** Lista di TUTTI gli autori *****************\n");
		System.out.println(pd.getAutori(mapA));
		
		Author a=new Author(719,"Milanese", "Mario");
		Author aa=new Author(2185,"Taragona", "Michele");

		System.out.println("\n***************** Lista di TUTTI i coautori di: "+a.toString()+" *****************\n");
		System.out.println(pd.getCoautori(a, mapA));

		//System.out.println(pd.archi(a, aa));

 
		



	}

}
