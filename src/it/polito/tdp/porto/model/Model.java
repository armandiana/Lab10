package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {

	private List<Author> autori;
	private List<Author>coautori;

	private Map<Integer, Author> autoriIdMap;
	//private Map<Integer, Paper> paperIdMap;
	private Graph<Author, DefaultEdge>grafo;
	
	private PortoDAO dao;

	
	public Model() {
		this.autoriIdMap=new HashMap<Integer, Author>();
		this.dao=new PortoDAO();
	}
	
	public void creaGrafo() {
		//creo l'oggetto grafo
		this.grafo= new SimpleGraph<Author, DefaultEdge>(DefaultEdge.class);
		
		//creo i vertici e li aggiungo.
		PortoDAO dao= new PortoDAO();
		this.autori=dao.getAutori(autoriIdMap);//ho la lista dei vertici
		
		Graphs.addAllVertices(this.grafo, this.autori);
		
		//aggiugiamo gli archi
		for(Author autore:autori) {
		   List<Author>coaut=new ArrayList<Author>(getCoautori(autore));
			for(Author coautore:coaut) {
				if(this.grafo.containsVertex(autore) && this.grafo.containsVertex(coautore))
					this.grafo.addEdge(autore, coautore);
			}
		}
	}

	public List<Author> getAutori() {
		this.autori=dao.getAutori(autoriIdMap);
		return autori;
	}

	public Map<Integer, Author> getAutoriIdMap() {
		return autoriIdMap;
	}

	public Graph<Author, DefaultEdge> getGrafo() {
		return grafo;
	}

	public List<Author> getCoautori(Author a){
		if(a!=null) {
			this.coautori=dao.getCoautori(a, autoriIdMap);
		}
		return coautori;
	}
	
	
}
