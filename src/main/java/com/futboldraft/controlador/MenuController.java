package com.futboldraft.controlador;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map.Entry;

public class MenuController {	
	@FXML
	private TextField tMail;
	
	@FXML
	private PasswordField tPassword;
	
	@FXML
	private CheckBox cTerms;
	
	@FXML
	private ImageView btnEmpezar, btnSalir;
	
	@FXML
	private Label tInfo;
	
	private Image btnEmpezar_Estado1 = new Image(getClass().getResource("/imagenes/Botones/start.png").toExternalForm());
	private Image btnEmpezar_Estado2 = new Image(getClass().getResource("/imagenes/Botones/start_action.png").toExternalForm());
	private Image btnSalir_Estado1 = new Image(getClass().getResource("/imagenes/Botones/btnSalir.png").toExternalForm());
	private Image btnSalir_Estado2 = new Image(getClass().getResource("/imagenes/Botones/btnSalir_action.png").toExternalForm());
	
	private MainController mc;
	private LoadingController lc;
	
	private HashMap<String, String> creeds = new HashMap<String, String>() {{
		put("usuario1", "contraseña123");
		put("usuario2", "clave456");
        put("admin", "admin");
	}};

	@FXML
	public void initialize() {
		mc = MainController.getInstance();
		
		btnEmpezar.setImage(btnEmpezar_Estado1);
		btnSalir.setImage(btnSalir_Estado1);
	}
	
	@FXML
	public void clickBoton(MouseEvent event) {
		if(event.getSource() == btnEmpezar) {
				if (btnEmpezar.getImage().equals(btnEmpezar_Estado1)) {
					btnEmpezar.setImage(btnEmpezar_Estado2);
				} else {
					btnEmpezar.setImage(btnEmpezar_Estado1);
				}
		} else if(event.getSource() == btnSalir) {
			if (btnSalir.getImage().equals(btnSalir_Estado1)) {
				btnSalir.setImage(btnSalir_Estado2);
			} else {
				btnSalir.setImage(btnSalir_Estado1);
			}
		}
	}
	
	@FXML
	public void soltarBoton(MouseEvent event) {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
			if (event.getSource() == btnEmpezar) {
					btnEmpezar.setImage(btnEmpezar_Estado1);
					
					if(cTerms.isSelected()) {
						if(checkCreeds()) {
							tInfo.setText("¿Has olvidado la contraseña o necesitas crear una nueva?");
							tInfo.setTextFill(Color.BLUE);
							try {
								mc.setSiguienteVista(MainController.DRAFT);
								mc.cargarVista(MainController.LOADING);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						} else {
							tMail.setText("");
							tMail.setPromptText("Escribe tu tlf. o correo electrónico");
							tPassword.setText("");
							tPassword.setPromptText("Escriba su contraseña");
							tInfo.setText("Usuario o Contraseña incorrectos, Has olvidado la Contraseña?");
							tInfo.setTextFill(Color.RED);
						}
					} else {
						tInfo.setText("Acepta los Terminos y Condiciones de uso");
						tInfo.setTextFill(Color.RED);
					}
			} else if (event.getSource() == btnSalir) {
				btnSalir.setImage(btnSalir_Estado1);
				Platform.exit();
			}
		}));
		timeline.setCycleCount(1);
		timeline.play();
	}


	private boolean checkCreeds() {
		
		for(Entry<String, String> entry : creeds.entrySet()) {
			if(entry.getKey().equals(tMail.getText()) && entry.getValue().equals(tPassword.getText())) {
				return true;
			}
		}
		
		return false;
	}


	
}
