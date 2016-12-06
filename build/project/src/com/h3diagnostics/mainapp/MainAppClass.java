package com.h3diagnostics.mainapp;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.usb.UsbDevice;
import javax.usb.UsbException;
import javax.usb.UsbHub;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import com.h3diagnostics.serialcommunication.SerialConnectionMethods;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jssc.SerialPortException;

public class MainAppClass extends Application {

	private Stage primaryStage;
    private  BorderPane rootLayout;
   
    //same as onCreate method of the android 
    //a stage is the main container and scene is like an android activity, which 
    //contians all the views 
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("H3Diagnostics");
        
       /* Image applicationIcon = new Image(getClass().getResourceAsStream("H3Diagnostic.png"));
        primaryStage.getIcons().add(applicationIcon);*/
         

        initRootLayout();

        showHomeScreenOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppClass.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout,700 ,500);
            
            primaryStage.setScene(scene);
            
            primaryStage.setResizable(false);
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the home screen overview inside the root layout.
     */
    public  void showHomeScreenOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppClass.class.getResource("view/DiagnosticHomeScr.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
 
            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    
}
