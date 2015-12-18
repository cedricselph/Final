package ch.makery.address.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.text.DecimalFormat;
import java.util.UUID;

import org.apache.poi.ss.formula.functions.FinanceLib;

import base.RateDAL;
import ch.makery.address.MainApp;
import ch.makery.address.model.Rate;


public class MortgageController {

	
	@FXML
	private TextField txtIncome;
	
	@FXML
	private TextField txtExpenses;
	
	@FXML
	private TextField txtCreditScore;
	
	@FXML
	private TextField txtHouseCost;
	
	@FXML
	private Labeled output;
	
	@FXML
	private Labeled lblMortgagePayment;
	
	@FXML
	private ComboBox ComboTerm;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MortgageController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    	ComboTerm . setOnMousePressed ( new EventHandler < MouseEvent >(){

    		 @Override

    		 public void handle ( MouseEvent event ) {

    			 ComboTerm . requestFocus (); } });
    	
    	ComboTerm.getItems().addAll ("15","30");
    	lblMortgagePayment.setVisible(false);
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    // Calculates monthly mortgage payment
    public static double mortgage(double interestRate, double term, double costOfHouse) {
    	
    	//Hooray for lab 1 lol
    	double A = (FinanceLib.pmt((interestRate / 100) / 12, 
    			term * 12 , 
    			costOfHouse, 
    			0, false));
    	
    	
    	return (A);
    }
    
    // caclulates affordability
    @FXML
    private void calculate() {
    	
    	lblMortgagePayment.setVisible(false);
    	
    	Double income = Double.parseDouble(this.txtIncome.getText());
    	
    	Double monthlyExpense = Double.parseDouble(this.txtExpenses.getText());
    	
    	int creditScore = Integer.parseInt(this.txtCreditScore.getText());
    	
    	Double costOfHouse = Double.parseDouble(this.txtHouseCost.getText());
    	
    	Double term = Double.parseDouble(this.ComboTerm.getValue().toString());
    	
    	Double interestRate = RateDAL.getRate(creditScore);
    	
    	// morgage calculation
    double mortgage = mortgage(interestRate, term, costOfHouse);
    	
    	if(mortgage <= (income*0.36) && mortgage <=(income + (monthlyExpense*2))*0.28) {
      		
    		this.output.setText("You Can afford this house!");
    		
    		lblMortgagePayment.setVisible(true);
    	} else {
    		
    		this.output.setText("This House Cost too much.");
    		
    		System.out.println("Morgage=" + mortgage);
    		
    		System.out.println("36% of your income = " + income * 0.36);
    		
    		System.out.println("18% of oyur income and added expenses = " + (income+(monthlyExpense*2))*0.18);
    	}
 
    }
   
}