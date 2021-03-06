package it.polito.tdp.porto;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {

	private Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

    
    @FXML
    void handleCoautori(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Author autore=this.boxPrimo.getValue();
    	
    	if(autore==null) {
    		
    		this.txtResult.appendText("Selezionare un autore! \n");
    	}
    	
    	try {
    		this.model.creaGrafo();
    		List<Author>coautori=this.model.getCoautori(autore);
 
    		if(coautori!=null) {
    			this.txtResult.appendText("COAUTORI: \n");

    			for(Author coautore: coautori) {
    			this.txtResult.appendText(coautore.toString()+" \n");
    			}
    		
    		}else {
    			this.txtResult.appendText("Non ci sono coautori per l'autore selezionato. \n");
    		}
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		this.txtResult.appendText("ERRORE! \n");
    		throw new RuntimeException("ERRORE nel metodo handleCoautori. \n");
    	}

    	if(autore!=null) {
        	this.boxSecondo.getItems().addAll(this.model.trovaNonCoautori(autore));
    	}else {
    		this.txtResult.appendText("Selezionare un autore! \n");
    	}
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	this.txtResult.clear();
    	
    	try {
 
    		Author a=this.boxPrimo.getValue();
    		Author nonA=this.boxSecondo.getValue();
    		
    		if(a==null||nonA==null) {
        		this.txtResult.appendText("ERRORE: selezionare due autori! \n");
    		}
    		
    		List<Paper>pubblicazioni=this.model.trovaArticoliComiuni(a, nonA);
    		
    		if(pubblicazioni==null) {
    			this.txtResult.appendText("Non esistono pubblicazioni per gli autori selezionati. \n");
    			return;
    		}
    		
    		this.txtResult.appendText("Articoli che collegano "+a.toString()+" a "+nonA.toString()+": \n");
    		
    		for(Paper p: pubblicazioni) {
    			this.txtResult.appendText(p.toString()+" \n");
    		}
    	
    	}catch(Exception e) {
    		e.printStackTrace();
    		this.txtResult.appendText("ERRORE! \n");
    		throw new RuntimeException("ERRORE nel metodo handleSequenza. \n");
    	}
    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }

	public void setModel(Model model) {
		this.model=model;
		this.boxPrimo.getItems().addAll(this.model.getAutori());
	}

}
