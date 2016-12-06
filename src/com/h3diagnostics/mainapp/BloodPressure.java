/*******************************************************************************
 * Copyright (c) 5/12/2016
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 *
 *
 * Contributors:
 *     paras dhanta - initial API and implementation
 *******************************************************************************/
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