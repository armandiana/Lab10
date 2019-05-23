package it.polito.tdp.porto.model;

public class Creator {
	//Lega ciascuna pubblicazione con i porpri autori
	private Author a;
	private Paper p;
	
	public Creator(Author a, Paper p) {
		super();
		this.a = a;
		this.p = p;
	}

	public Author getA() {
		return a;
	}

	public void setA(Author a) {
		this.a = a;
	}

	public Paper getP() {
		return p;
	}

	public void setP(Paper p) {
		this.p = p;
	}

	@Override
	public String toString() {
		return "Creator: "+a.toString()+" "+p.toString()+"\n";
	}

	
	
	
	

}
