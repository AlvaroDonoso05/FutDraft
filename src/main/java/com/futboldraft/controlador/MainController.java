package com.futboldraft.controlador;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController {

	public static final String LOGIN = "/vistas/MainMenu.fxml";
	public static final String DRAFT = "/vistas/DraftUI.fxml";
	public static final String LOADING = "/vistas/Loading.fxml";
	public static final String SIMULACION = "/vistas/Simulacion.fxml";

	private static MainController instance;
	private Stage stage;
	private boolean first;

	private boolean fullscreen = false;
	private int width = 1200;
	private int height = 900;
	
	private String siguienteVista;
	private boolean isAdmin;

	public static MainController firstInstance(Stage stage) {
		if(instance == null) {
			instance = new MainController(stage, true);
		}

		return instance;
	}

	public static MainController getInstance() {
		if (instance == null) {
			throw new IllegalStateException("MainController no ha sido inicializado. Llama a firstInstance(Stage stage) primero.");
		}
		return instance;
	}

	private MainController(Stage stage, Boolean first) {
		this.first = first;
		this.stage = stage;
	}

	public void cargarVista(String vista) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));
		Parent root = loader.load();

		Scene scene = new Scene(root);
		stage.setTitle("FutDraft");
		stage.setMinWidth(width);
		stage.setMinHeight(height);
		stage.setScene(scene);
		stage.setOnCloseRequest(event -> {
			//guardar datos...
		});
		stage.setFullScreen(fullscreen);

		if(first) {
			stage.show();
			first = false;
		}

		fadeInTransition(root);

	}

	private void fadeInTransition(Parent root) {
		FadeTransition fadeIn = new FadeTransition(Duration.millis(1500), root);
		fadeIn.setFromValue(0.0);
		fadeIn.setToValue(1.0);
		fadeIn.play();
	}


	public Stage getStage() {
		return this.stage;
	}

	public void setFullScreen(boolean fullScreen) {
		this.fullscreen = fullScreen;
	}

	public void setwidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getSiguienteVista() {
		return siguienteVista;
	}

	public void setSiguienteVista(String siguienteVista) {
		this.siguienteVista = siguienteVista;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
