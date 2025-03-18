package com.futboldraft.vista;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.futboldraft.controlador.MainController;
import com.futboldraft.modelo.Equipo;
import com.futboldraft.modelo.Jugador;

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