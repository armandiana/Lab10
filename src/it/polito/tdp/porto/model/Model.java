package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {

	private List<Author> autori;
	//private List<Author>coautori;
	private Map<Integer, Author> autoriIdMap;
	
	private Graph<Author, DefaultEdge>grafo;
	
	
	public Model() {
		this.autoriIdMap=new HashMap<Integer, Author>();
	}
	
	public List<Author> getAutori() {
		if(this.autori==null) {
			
			PortoDAO dao= new PortoDAO();
			
		    this.autori=dao.getAutori(autoriIdMap);
		    
		    if(this.autori==null) {
		    	throw new RuntimeException("\n Errore di connessione col database. \n");
		    }
		}

		return this.autori;
	}
	
	public void creaGrafo() {
		PortoDAO dao= new PortoDAO();
		
		//creo l'oggetto grafo
		this.grafo= new SimpleGraph<Author, DefaultEdge>(DefaultEdge.class);
		
		//creo i vertici e li aggiungo.
	    //dao= new PortoDAO();
		//this.autori=dao.getAutori(autoriIdMap);//ho la lista dei vertici
		
		Graphs.addAllVertices(this.grafo, getAutori()); //ricordiamo di cercare di delegare il più possibile ai metodi.
		
		//aggiugiamo gli archi
		for(Author autore: grafo.vertexSet()) {
			
		   List<Author>coautori=dao.getCoautori(autore, autoriIdMap);
		   
			for(Author coautore:coautori) {
				if(this.grafo.containsVertex(coautore) && this.grafo.containsVertex(autore)) {
					this.grafo.addEdge(autore, coautore);
				}
			}
		}
	}


	public Map<Integer, Author> getAutoriIdMap() {
		return autoriIdMap;
	}

	public Graph<Author, DefaultEdge> getGrafo() {
		return grafo;
	}

	/*public List<Author> getCoautori(Author a){
		if(a!=null) {
			this.coautori=dao.getCoautori(a, autoriIdMap);
		}
		return coautori;
	}*/
	
	public List<Author> getCoautori(Author a){
		if(this.grafo==null) {
			creaGrafo();
		}
		
		List<Author>coautori=Graphs.neighborListOf(this.grafo, a);
		Collections.sort(coautori);
		return coautori;
	}
	
	public List<Author> trovaNonCoautori(Author a){
		//i non coautori sono tutti quelli non presenti nella lilsta autori e l'autore stesso.
		List<Author>c=getCoautori(a);
		List<Author>nonCoautori=new ArrayList<Author>();
	
		for(Author aa: this.grafo.vertexSet()) {
			if(!c.contains(aa) && !aa.equals(a))
				nonCoautori.add(aa);
		}
	
		return nonCoautori;
	}
	
	public List<Paper> trovaArticoliComiuni(Author a1, Author a2){
		
		PortoDAO dao= new PortoDAO();

		List<Paper>result= new ArrayList<Paper>();
	
		DijkstraShortestPath<Author, DefaultEdge>dijkstra= new DijkstraShortestPath<Author, DefaultEdge>(grafo);
		GraphPath<Author, DefaultEdge>dijkstraPath=dijkstra.getPath(a1, a2);
		
		
		/*if(dijkstraPath.getVertexList().contains(a1) && dijkstraPath.getVertexList().contains(a2)) {
			List<Paper>pubblicazioni=dao.getPubblicazioni(a1, a2);
			return pubblicazioni;
		}else {   
			System.out.println("Non esiste la sequenza per gli autori selezionati");
			return null;
		}*/
		if(dijkstraPath==null) {
			return null;
		}
		
		List<DefaultEdge>archi=dijkstraPath.getEdgeList();
		//ora abbiamo le coppie di autori con un articolo in comune:
		//dobbiamo trovarci l'articolo
		for(DefaultEdge e: archi) {
			
			Author s=grafo.getEdgeSource(e);
			Author t=grafo.getEdgeTarget(e);
			
			Paper p=dao.getPubblicazioneComune(s, t);
			if(p==null) {
				throw new NullPointerException("Pubblicazione non trovata.\n");
			}
			result.add(p);
		}
		return result;
	}
	
	
}
