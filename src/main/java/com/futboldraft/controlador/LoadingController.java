package com.futboldraft.controlador;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.util.Duration;

public class LoadingController {
	
	private MainController mc;
	
	@FXML
	public void initialize() {
		mc = MainController.getInstance();
		System.out.println(mc.getSiguienteVista());
		switch(mc.getSiguienteVista()) {
			case MainController.LOGIN:
				try {
					Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), e -> {
						try {
							mc.cargarVista(MainController.LOGIN);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}));
					timeline.setCycleCount(1);
					timeline.play();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case MainController.DRAFT:
				try {
					Timeline timeline = new Timeline(new KeyFrame(Duration.millis(4000), e -> {
						try {
							
							CargarBaseDatos load = new CargarBaseDatos();
							Thread dbThread = new Thread(load);
							dbThread.setDaemon(true);
							dbThread.start();
							
							load.setOnSucceeded(event -> {
							    try {
									mc.cargarVista(MainController.DRAFT);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							});
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}));
					timeline.setCycleCount(1);
					timeline.play();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				try {
					Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), e -> {
						try {
							mc.cargarVista(MainController.LOGIN);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}));
					timeline.setCycleCount(1);
					timeline.play();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
		}
	}
}
