package com.h3diagnostics.mainapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BloodPressure {

    // doesn't need to be called "start" any more...    
    public void start(Stage window) throws Exception {
        Parent root = FXMLLoader.load(BloodPressure.class.getResource("view/Test.fxml"));
        Scene scene =  new Scene(root, 700 ,500);
        window.setScene(scene);
        window.show();
       
    }
    
}   