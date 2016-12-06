package com.h3diagnostics.mainapp.controller;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GlobalHandler {
	

	private BorderPane rootLayout;
	
	private Stage primaryStage;
	
	private GlobalHandler(){
		//not init
	}
	private static class SingletonHolder {
        private static final GlobalHandler INSTANCE = new GlobalHandler();
    }

    public static GlobalHandler getInstance() {
        return GlobalHandler.SingletonHolder.INSTANCE;
    }

	public BorderPane getRootLayout() {
		return rootLayout;
	}

	public void setRootLayout(BorderPane rootLayout) {
		this.rootLayout = rootLayout;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

    
}
