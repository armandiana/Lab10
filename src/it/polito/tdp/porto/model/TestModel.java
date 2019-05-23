package it.polito.tdp.porto.model;

import java.util.HashMap;
import java.util.Map;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		//Map<Integer, Author>mapA= new HashMap<Integer, Author>();

		Author a=new Author(719,"Milanese", "Mario");
		Author aa=new Author(2185,"Taragona", "Michele");

		model.creaGrafo();
		
		System.out.println("************************************\n");
		System.out.println("GRAFO CREATO CON "
		+model.getGrafo().vertexSet().size()+" VERTICI E "
				+model.getGrafo().edgeSet().size()+" ARCHI");
		
		System.out.println("\n************************************\n");
		
	}

}
