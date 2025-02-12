package com.futboldraft.vista;

import com.futboldraft.controlador.MainController;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainUI extends Application {
	private MainController mc;
	@Override
	public void start(Stage primaryStage) throws Exception {

		if(mc == null) {
			mc = MainController.firstInstance(primaryStage);
		} else {
			mc = MainController.getInstance();
		}

		mc.cargarVista(MainController.LOGIN);
	}

	public static void main(String[] args) {
		launch(args);
	}
}