package it.polito.tdp.porto.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();

		Author a=new Author(719,"Milanese", "Mario");
		Author aa=new Author(2185,"Taragona", "Michele");

		model.creaGrafo();
		
		System.out.println("************************************\n");
		System.out.println("GRAFO CREATO CON "
		+model.getGrafo().vertexSet().size()+" VERTICI E "
				+model.getGrafo().edgeSet().size()+" ARCHI");
		
		System.out.println("\n************************************\n");
		//System.out.println("GRAFO: \n"+model.getGrafo());
		
		System.out.println("\n************************************\n");
		System.out.println("TROVA COAUTORI: \n"+model.getCoautori(a));
		
		System.out.println("\n************************************\n");
		System.out.println("TROVA I NON COAUTORI: \n"+model.trovaNonCoautori(a));
		
		System.out.println("\n************************************\n");
		System.out.println("SEQUENZA: \n"+model.trovaArticoliComiuni(a, aa));


		
	}

}
