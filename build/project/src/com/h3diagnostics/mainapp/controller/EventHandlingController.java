package com.h3diagnostics.mainapp.controller;

import com.h3diagnostics.mainapp.BloodPressure;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * View-Controller for the person table.
 * 
 * @author Paras Dhanta
 */
public class EventHandlingController {
	private BloodPressure bloodPressure ;

	
	@FXML
	private Button bpButton;
	
	@FXML
	private Button bgButton;
	
	@FXML
	private Button bTempButton;
	
	@FXML
	private Button pOxyButton;
	
	@FXML
	private Button ecgButton;
	
	@FXML
	private Button connectButton;
	
	@FXML
	private Button syncButton;
	
	@FXML
	private Button helpButton;
	
	@FXML
	private Button exitButton;

	@FXML Button hameoButton;
	

	
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		
		bloodPressure =new BloodPressure();
		// Handle Bp Button event.
		bpButton.setOnAction((event) -> {
			System.out.println("Bp button clicked");
			Stage stage = (Stage) bpButton.getScene().getWindow();
			try {
				bloodPressure.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		// Handle Bg Button event.
		bgButton.setOnAction((event) -> {
			System.out.println("Bg button clicked");
					
		});
		
		// Handle temp Button event.
		bTempButton.setOnAction((event) -> {
			System.out.println("temp button clicked");
							
		});
				
		// Handle pulse oxy Button event.
		pOxyButton.setOnAction((event) -> {
			System.out.println("pulse oxy button clicked");
							
		});
				
		// Handle haemoglobin Button event.
		hameoButton.setOnAction((event) -> {
			System.out.println("haemoglobin button clicked");
							
		});
				
		// Handle ecg Button event.
		ecgButton.setOnAction((event) -> {
		    System.out.println("ecg button clicked");
							
		});
				
		// Handle connect Button event.
		connectButton.setOnAction((event) -> {
			System.out.println("connect button clicked");
			
									
		});
		
		// Handle sync Button event.
		syncButton.setOnAction((event) -> {
			System.out.println("connect button clicked");
									
		});
		// Handle help Button event.
		helpButton.setOnAction((event) -> {
			System.out.println("connect button clicked");
									
		});
		// Handle exit Button event.
		exitButton.setOnAction((event) -> {
			System.out.println("connect button clicked");
									
		});
		
	
		
	}

}
