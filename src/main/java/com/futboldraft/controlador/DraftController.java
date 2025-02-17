package com.futboldraft.controlador;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.chainsaw.Main;

import com.futboldraft.modelo.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class DraftController {
	private BaseDatos bbdd = BaseDatos.getInstance();
	
	@FXML
	private ImageView del1, del2, cen1, cen2, cen3, cen4, def1, def2, def3, def4, por1;
	
	@FXML
	private ImageView btnEmpezar, btnSalir, btnReroll, btnClasificacion;
	
	@FXML
	private BorderPane seleccionJugador, pClasificacion;

	@FXML
	private Text tNombreS1, tMediaS1, tAtaqS1, tDefS1, tTecS1, tPorS1, tPosS1;
	@FXML
	private Text tNombreS2, tMediaS2, tAtaqS2, tDefS2, tTecS2, tPorS2, tPosS2;
	@FXML
	private Text tNombreS3, tMediaS3, tAtaqS3, tDefS3, tTecS3, tPorS3, tPosS3;
	@FXML
	private Text tNombreS4, tMediaS4, tAtaqS4, tDefS4, tTecS4, tPorS4, tPosS4;
	@FXML
	private Text tNombreS5, tMediaS5, tAtaqS5, tDefS5, tTecS5, tPorS5, tPosS5;
	
	@FXML
	private Text tNombre1, tMedia1, tAtaq1, tDef1, tTec1, tPor1, tPos1;
	@FXML
	private Text tNombre2, tMedia2, tAtaq2, tDef2, tTec2, tPor2, tPos2;
	@FXML
	private Text tNombre3, tMedia3, tAtaq3, tDef3, tTec3, tPor3, tPos3;
	@FXML
	private Text tNombre4, tMedia4, tAtaq4, tDef4, tTec4, tPor4, tPos4;
	@FXML
	private Text tNombre5, tMedia5, tAtaq5, tDef5, tTec5, tPor5, tPos5;
	@FXML
	private Text tNombre6, tMedia6, tAtaq6, tDef6, tTec6, tPor6, tPos6;
	@FXML
	private Text tNombre7, tMedia7, tAtaq7, tDef7, tTec7, tPor7, tPos7;
	@FXML
	private Text tNombre8, tMedia8, tAtaq8, tDef8, tTec8, tPor8, tPos8;
	@FXML
	private Text tNombre9, tMedia9, tAtaq9, tDef9, tTec9, tPor9, tPos9;
	@FXML
	private Text tNombre10, tMedia10, tAtaq10, tDef10, tTec10, tPor10, tPos10;
	@FXML
	private Text tNombre11, tMedia11, tAtaq11, tDef11, tTec11, tPor11, tPos11;

	@FXML
	private Pane stdel1, stdel2, stcen1, stcen2, stcen3, stcen4, stdef1, stdef2, stdef3, stdef4, stpor1;
	
	@FXML
	private TextField nombreFilter;
	
	@FXML
	private ImageView sel1, sel2, sel3, sel4, sel5;
	
	@FXML
	private TableView<ClasificacionTabla> tableView;
	
	@FXML
	private TableColumn<ClasificacionTabla, Integer> colId, colAtaque, colTecnica, colDefensa, colPortero;
	@FXML
	private TableColumn<ClasificacionTabla, String> colEquipo, colNombre, colPosicion;
	
	private ObservableList<ClasificacionTabla> listaOriginal;
	
	@FXML
	private Button test;

	private Image btnEmpezar_Estado1 = new Image(getClass().getResource("/imagenes/Botones/start.png").toExternalForm());
	private Image btnEmpezar_Estado2 = new Image(getClass().getResource("/imagenes/Botones/start_action.png").toExternalForm());
	private Image btnEmpezar_dis = new Image(getClass().getResource("/imagenes/Botones/start_dis.png").toExternalForm());
	private Image btnSalir_Estado1 = new Image(getClass().getResource("/imagenes/Botones/btnSalir.png").toExternalForm());
	private Image btnSalir_Estado2 = new Image(getClass().getResource("/imagenes/Botones/btnSalir_action.png").toExternalForm());
	
	private Image pasarEncimaImg_Estado1 = new Image(getClass().getResource("/imagenes/rotating_card_loop.gif").toExternalForm());
	private Image pasarEncimaImg_Estado2 = new Image(getClass().getResource("/imagenes/default_selected.gif").toExternalForm());
	
	private Image btnReroll_Estado1 = new Image(getClass().getResource("/imagenes/Botones/btnReroll.png").toExternalForm());
	private Image btnReroll_Estado2 = new Image(getClass().getResource("/imagenes/Botones/btnReroll_action.png").toExternalForm());
	
	private Image btnClasificacion_Estado1 = new Image(getClass().getResource("/imagenes/Botones/leaderboard.png").toExternalForm());
	private Image btnClasificacion_Estado2 = new Image(getClass().getResource("/imagenes/Botones/leaderboard_action.png").toExternalForm());
	
	private Image bronze_Estado1 = new Image(getClass().getResource("/imagenes/cards/cardBronzeP.png").toExternalForm());
	private Image plata_Estado1 = new Image(getClass().getResource("/imagenes/cards/cardSilverP.png").toExternalForm());
	private Image oro_Estado1 = new Image(getClass().getResource("/imagenes/cards/cardGoldP.png").toExternalForm());
	private Image plus_Estado1 = new Image(getClass().getResource("/imagenes/cards/card+85P.png").toExternalForm());
	private Image mega_Estado1 = new Image(getClass().getResource("/imagenes/cards/card+90P.png").toExternalForm());
	private Image bronze_Estado2 = new Image(getClass().getResource("/imagenes/cards/cardBronzeP.gif").toExternalForm());
	private Image plata_Estado2 = new Image(getClass().getResource("/imagenes/cards/cardSilverP.gif").toExternalForm());
	private Image oro_Estado2 = new Image(getClass().getResource("/imagenes/cards/cardGoldP.gif").toExternalForm());
	private Image plus_Estado2 = new Image(getClass().getResource("/imagenes/cards/card+85P.gif").toExternalForm());
	private Image mega_Estado2 = new Image(getClass().getResource("/imagenes/cards/card+90P.gif").toExternalForm());
	
	private List<List<Text>> listaSEstadisticas;
	private List<List<Text>> listaEstadisticas;
	private List<ImageView> listaImgSeleccion;
	private List<Pane> listaPaneEstadisticas;
	
	private List<Text> estadisticaSeleccionada;
	private Pane panelEstadisticaSeleccionado;
	
	private Equipo equipoJugador;
	private ImageView jugadorSeleccionado;
	
	private MainController mc;


	@FXML
	public void initialize() {
		mc = MainController.getInstance();
		bbdd = BaseDatos.getInstance();
		

		bbdd.borrarEventosPartido();
		bbdd.borrarPartido();
		bbdd.borrarJornada();
		bbdd.borrarClasificacion();

		colId.setCellValueFactory(new PropertyValueFactory<>("idJugador"));
		colEquipo.setCellValueFactory(new PropertyValueFactory<>("equipo"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colPosicion.setCellValueFactory(new PropertyValueFactory<>("posicion"));
		colAtaque.setCellValueFactory(new PropertyValueFactory<>("fAtaque"));
		colTecnica.setCellValueFactory(new PropertyValueFactory<>("fTecnica"));
		colDefensa.setCellValueFactory(new PropertyValueFactory<>("fDefensa"));
		colPortero.setCellValueFactory(new PropertyValueFactory<>("fPortero"));

		
		btnEmpezar.setImage(btnEmpezar_dis);
		btnSalir.setImage(btnSalir_Estado1);
		btnReroll.setImage(btnReroll_Estado1);
		del1.setImage(pasarEncimaImg_Estado1);
		del2.setImage(pasarEncimaImg_Estado1);
		cen1.setImage(pasarEncimaImg_Estado1);
		cen2.setImage(pasarEncimaImg_Estado1);
		cen3.setImage(pasarEncimaImg_Estado1);
		cen4.setImage(pasarEncimaImg_Estado1);
		def1.setImage(pasarEncimaImg_Estado1);
		def2.setImage(pasarEncimaImg_Estado1);
		def3.setImage(pasarEncimaImg_Estado1);
		def4.setImage(pasarEncimaImg_Estado1);
		por1.setImage(pasarEncimaImg_Estado1);
		
		if(!mc.isAdmin()) btnReroll.setVisible(false);
		
		stdel1.setVisible(false);
		stdel2.setVisible(false);
		stcen1.setVisible(false);
		stcen2.setVisible(false);
		stcen3.setVisible(false);
		stcen4.setVisible(false);
		stdef1.setVisible(false);
		stdef2.setVisible(false);
		stdef3.setVisible(false);
		stdef4.setVisible(false);
		stpor1.setVisible(false);
		
		List<Equipo> listaEquipos = bbdd.selectEquiposByNombre("%");
		
		// Obtener Equipo del Jugador
		
		int random = (int) (Math.random() * listaEquipos.size());
		equipoJugador = listaEquipos.get(random);
		mc.setEquipoJug(equipoJugador);
		
		listaSEstadisticas = new ArrayList<>();
		listaSEstadisticas.add(List.of(tNombreS1, tMediaS1, tAtaqS1, tDefS1, tTecS1, tPorS1, tPosS1));
		listaSEstadisticas.add(List.of(tNombreS2, tMediaS2, tAtaqS2, tDefS2, tTecS2, tPorS2, tPosS2));
		listaSEstadisticas.add(List.of(tNombreS3, tMediaS3, tAtaqS3, tDefS3, tTecS3, tPorS3, tPosS3));
		listaSEstadisticas.add(List.of(tNombreS4, tMediaS4, tAtaqS4, tDefS4, tTecS4, tPorS4, tPosS4));
		listaSEstadisticas.add(List.of(tNombreS5, tMediaS5, tAtaqS5, tDefS5, tTecS5, tPorS5, tPosS5));
		
		listaEstadisticas = new ArrayList<>();
		listaEstadisticas.add(List.of(tNombre1, tMedia1, tAtaq1, tDef1, tTec1, tPor1, tPos1));
		listaEstadisticas.add(List.of(tNombre2, tMedia2, tAtaq2, tDef2, tTec2, tPor2, tPos2));
		listaEstadisticas.add(List.of(tNombre3, tMedia3, tAtaq3, tDef3, tTec3, tPor3, tPos3));
		listaEstadisticas.add(List.of(tNombre4, tMedia4, tAtaq4, tDef4, tTec4, tPor4, tPos4));
		listaEstadisticas.add(List.of(tNombre5, tMedia5, tAtaq5, tDef5, tTec5, tPor5, tPos5));
		listaEstadisticas.add(List.of(tNombre6, tMedia6, tAtaq6, tDef6, tTec6, tPor6, tPos6));
		listaEstadisticas.add(List.of(tNombre7, tMedia7, tAtaq7, tDef7, tTec7, tPor7, tPos7));
		listaEstadisticas.add(List.of(tNombre8, tMedia8, tAtaq8, tDef8, tTec8, tPor8, tPos8));
		listaEstadisticas.add(List.of(tNombre9, tMedia9, tAtaq9, tDef9, tTec9, tPor9, tPos9));
		listaEstadisticas.add(List.of(tNombre10, tMedia10, tAtaq10, tDef10, tTec10, tPor10, tPos10));
		listaEstadisticas.add(List.of(tNombre11, tMedia11, tAtaq11, tDef11, tTec11, tPor11, tPos11));
		
		listaImgSeleccion = List.of(sel1, sel2, sel3, sel4, sel5);
		listaPaneEstadisticas = List.of(stdel1, stdel2, stcen1, stcen2, stcen3, stcen4, stdef1, stdef2, stdef3, stdef4, stpor1);
		
		obtenerClasificacion();
		
	}
	
	public void obtenerClasificacion() {
	    Task<ObservableList<ClasificacionTabla>> task = new Task<>() {
	        @Override
	        protected ObservableList<ClasificacionTabla> call() {
	            listaOriginal = bbdd.obtenerClasificaciones();
	            System.out.println("Datos obtenidos: " + listaOriginal);  // Verifica que la lista no esté vacía
	            return listaOriginal;
	        }
	    };

	    task.setOnSucceeded(event -> {
	        ObservableList<ClasificacionTabla> data = task.getValue();
	        if (data != null && !data.isEmpty()) {
	            // Refrescar la tabla con los nuevos datos
	            tableView.setItems(data);
	            System.out.println("Tabla actualizada con los datos.");
	        } else {
	            System.out.println("No se han obtenido datos válidos.");
	        }
	    });

	    task.setOnFailed(event -> {
	        System.out.println("Error en la tarea de obtener clasificación.");
	        Throwable exception = task.getException();
	        exception.printStackTrace();
	    });

	    // Ejecutar la tarea en un hilo separado
	    new Thread(task).start();
	}
	
	private void actualizarClasificacion(int idJugador, String equipo) {
		for (ClasificacionTabla item : listaOriginal) {
		    if (item.getIdJugador() == idJugador) {
		    	System.out.println(idJugador + " " + equipo + " dbhrgrfajbhjiijsfdbhjoij");
		    	item.setEquipo(equipo);
		        break;
		    }
		}
	}

	
	@FXML
	private void aplicarFiltro() {
		String nombreBusqueda = nombreFilter.getText().toLowerCase();

		ObservableList<ClasificacionTabla> listaFiltrada = FXCollections.observableArrayList();
		for (ClasificacionTabla item : listaOriginal) {
			if (item.getNombre().toLowerCase().contains(nombreBusqueda)) {
				listaFiltrada.add(item);
			}
		}

		tableView.setItems(listaFiltrada);
	}	
	
	public List<Jugador> sacarDraft(String posicion) {
		List<Jugador> jugadoresD = new ArrayList<Jugador>();

		int cont = 0, random;

		List<Jugador> jugadoresP = bbdd.jugadorPosicion(posicion);
		while(cont <5) {
			random = (int)(Math.random()*jugadoresP.size());
			if(jugadoresP.get(random).getEquipo() == null) {
				jugadoresD.add(jugadoresP.remove(random));
				cont++;
			}
		}
		return jugadoresD;
	}
	
	
	public void anadirJugadorEquipo(String nEquipo) {
		String nJugador= "", posicion="";
		
		Jugador jugador = bbdd.selectJugador(nJugador, posicion);
		jugador.setEquipo(new Equipo(nEquipo));
		
		bbdd.insertarJugador(null);
	}
	
	@FXML
	public void clickBoton(MouseEvent event) {
		if(event.getSource() == btnEmpezar) {
			if(!btnEmpezar.getImage().equals(btnEmpezar_dis)) {
				if (btnEmpezar.getImage().equals(btnEmpezar_Estado1)) {
					btnEmpezar.setImage(btnEmpezar_Estado2);
				} else {
					btnEmpezar.setImage(btnEmpezar_Estado1);
				}
			}
		} else if(event.getSource() == btnSalir) {
			if (btnSalir.getImage().equals(btnSalir_Estado1)) {
				btnSalir.setImage(btnSalir_Estado2);
			} else {
				btnSalir.setImage(btnSalir_Estado1);
			}
		} else if(event.getSource() == btnReroll) {
			if (btnReroll.getImage().equals(btnReroll_Estado1)) {
				btnReroll.setImage(btnReroll_Estado2);
			} else {
				btnReroll.setImage(btnReroll_Estado1);
			}						
		} else if(event.getSource() == btnClasificacion) {
			if (btnClasificacion.getImage().equals(btnClasificacion_Estado1)) {
				btnClasificacion.setImage(btnClasificacion_Estado2);
			} else {
				btnClasificacion.setImage(btnClasificacion_Estado1);
			}
		}
	}
	
	@FXML
	public void soltarBoton(MouseEvent event) {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
			if (event.getSource() == btnEmpezar) {
				if(!btnEmpezar.getImage().equals(btnEmpezar_dis)) {
					btnEmpezar.setImage(btnEmpezar_Estado1);
					mc.setSiguienteVista(MainController.SIMULACION);
					try {
						mc.cargarVista(MainController.LOADING);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			} else if (event.getSource() == btnSalir) {
				btnSalir.setImage(btnSalir_Estado1);
				try {
					mc.setSiguienteVista(MainController.LOGIN);
					mc.cargarVista(MainController.LOADING);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else if(event.getSource() == btnReroll) {
				btnReroll.setImage(btnReroll_Estado1);
				List<Jugador> listaJugadores;
				listaJugadores = bbdd.jugadorPosicionRand("DEF");
				rellenarJugadoresSeleccion(listaJugadores);
			} else if(event.getSource() == btnClasificacion) {
				btnClasificacion.setImage(btnClasificacion_Estado1);
				if(pClasificacion.isVisible()) {
					pClasificacion.setVisible(false);
				} else {
					pClasificacion.setVisible(true);
				}
			}
		}));
		timeline.setCycleCount(1);
		timeline.play();
	}
	
	@FXML
	public void pasarEncima(MouseEvent event) {
		ImageView imageView = (ImageView) event.getSource();
		
		if(imageView.getImage() == pasarEncimaImg_Estado1) {
			imageView.setImage(pasarEncimaImg_Estado2);
		} else if(imageView.getImage() == pasarEncimaImg_Estado2) {
			imageView.setImage(pasarEncimaImg_Estado1);
		}
		
		if(imageView.getImage() == bronze_Estado1) {
			imageView.setImage(bronze_Estado2);
		} else if(imageView.getImage() == bronze_Estado2) {
			imageView.setImage(bronze_Estado1);
		} else if(imageView.getImage() == plata_Estado1) {
			imageView.setImage(plata_Estado2);
		} else if(imageView.getImage() == plata_Estado2) {
			imageView.setImage(plata_Estado1);
		} else if(imageView.getImage() == oro_Estado1) {
			imageView.setImage(oro_Estado2);
		} else if(imageView.getImage() == oro_Estado2) {
			imageView.setImage(oro_Estado1);
		} else if(imageView.getImage() == plus_Estado1) {
			imageView.setImage(plus_Estado2);
		} else if(imageView.getImage() == plus_Estado2) {
			imageView.setImage(plus_Estado1);
		} else if(imageView.getImage() == mega_Estado1) {
			imageView.setImage(mega_Estado2);
		} else if(imageView.getImage() == mega_Estado2) {
			imageView.setImage(mega_Estado1);
		}
	}
	
	public void clickSelec(MouseEvent event) {
		jugadorSeleccionado = (ImageView) event.getSource();
		List<Jugador> listaJugadores;
		
		if(jugadorSeleccionado.getImage() == pasarEncimaImg_Estado1 || jugadorSeleccionado.getImage() == pasarEncimaImg_Estado2) {
			if(event.getSource() == del1) {
			    listaJugadores = bbdd.jugadorPosicionRand("DEL");
			    rellenarJugadoresSeleccion(listaJugadores);
			    panelEstadisticaSeleccionado = listaPaneEstadisticas.get(0);
			    estadisticaSeleccionada = listaEstadisticas.get(0);
			} else if(event.getSource() == del2) {
			    listaJugadores = bbdd.jugadorPosicionRand("DEL");
			    rellenarJugadoresSeleccion(listaJugadores);
			    panelEstadisticaSeleccionado = listaPaneEstadisticas.get(1);
			    estadisticaSeleccionada = listaEstadisticas.get(1);
			} else if(event.getSource() == cen1) {
			    listaJugadores = bbdd.jugadorPosicionRand("MED");
			    rellenarJugadoresSeleccion(listaJugadores);
			    panelEstadisticaSeleccionado = listaPaneEstadisticas.get(2);
			    estadisticaSeleccionada = listaEstadisticas.get(2);
			} else if(event.getSource() == cen2) {
			    listaJugadores = bbdd.jugadorPosicionRand("MED");
			    rellenarJugadoresSeleccion(listaJugadores);
			    panelEstadisticaSeleccionado = listaPaneEstadisticas.get(3);
			    estadisticaSeleccionada = listaEstadisticas.get(3);
			} else if(event.getSource() == cen3) {
			    listaJugadores = bbdd.jugadorPosicionRand("MED");
			    rellenarJugadoresSeleccion(listaJugadores);
			    panelEstadisticaSeleccionado = listaPaneEstadisticas.get(4);
			    estadisticaSeleccionada = listaEstadisticas.get(4);
			} else if(event.getSource() == cen4) {
			    listaJugadores = bbdd.jugadorPosicionRand("MED");
			    rellenarJugadoresSeleccion(listaJugadores);
			    panelEstadisticaSeleccionado = listaPaneEstadisticas.get(5);
			    estadisticaSeleccionada = listaEstadisticas.get(5);
			} else if(event.getSource() == def1) {
			    listaJugadores = bbdd.jugadorPosicionRand("DEF");
			    rellenarJugadoresSeleccion(listaJugadores);
			    panelEstadisticaSeleccionado = listaPaneEstadisticas.get(6);
			    estadisticaSeleccionada = listaEstadisticas.get(6);
			} else if(event.getSource() == def2) {
			    listaJugadores = bbdd.jugadorPosicionRand("DEF");
			    rellenarJugadoresSeleccion(listaJugadores);
			    panelEstadisticaSeleccionado = listaPaneEstadisticas.get(7);
			    estadisticaSeleccionada = listaEstadisticas.get(7);
			} else if(event.getSource() == def3) {
			    listaJugadores = bbdd.jugadorPosicionRand("DEF");
			    rellenarJugadoresSeleccion(listaJugadores);
			    panelEstadisticaSeleccionado = listaPaneEstadisticas.get(8);
			    estadisticaSeleccionada = listaEstadisticas.get(8);
			} else if(event.getSource() == def4) {
			    listaJugadores = bbdd.jugadorPosicionRand("DEF");
			    rellenarJugadoresSeleccion(listaJugadores);
			    panelEstadisticaSeleccionado = listaPaneEstadisticas.get(9);
			    estadisticaSeleccionada = listaEstadisticas.get(9);
			} else if(event.getSource() == por1) {
			    listaJugadores = bbdd.jugadorPosicionRand("POR");
			    rellenarJugadoresSeleccion(listaJugadores);
			    panelEstadisticaSeleccionado = listaPaneEstadisticas.get(10);
			    estadisticaSeleccionada = listaEstadisticas.get(10);
			}
		}
	}
	
	@FXML
	private void selJugadorClick(MouseEvent event) {
		ImageView jugadorsel5 = (ImageView) event.getSource();
		jugadorSeleccionado.setImage(jugadorsel5.getImage());
		Jugador jugador = null;
		
		if(event.getSource() == sel1) {
			List<Text> estadistica = listaSEstadisticas.get(0);
			jugador = bbdd.selectJugador(estadistica.get(0).getText(), estadistica.get(6).getText());
		} else if(event.getSource() == sel2) {
			List<Text> estadistica = listaSEstadisticas.get(1);
			jugador = bbdd.selectJugador(estadistica.get(0).getText(), estadistica.get(6).getText());
		} else if(event.getSource() == sel3) {
			List<Text> estadistica = listaSEstadisticas.get(2);
			jugador = bbdd.selectJugador(estadistica.get(0).getText(), estadistica.get(6).getText());
		} else if(event.getSource() == sel4) {
			List<Text> estadistica = listaSEstadisticas.get(3);
			jugador = bbdd.selectJugador(estadistica.get(0).getText(), estadistica.get(6).getText());
		} else if(event.getSource() == sel5) {
			List<Text> estadistica = listaSEstadisticas.get(4);
			jugador = bbdd.selectJugador(estadistica.get(0).getText(), estadistica.get(6).getText());
		}
		
		jugador.setEquipo(equipoJugador);
		bbdd.insertarJugador(jugador);
		actualizarClasificacion(jugador.getIdJugador(), equipoJugador.getNombre());
		
		int media = jugador.calcularMedia();
		
		if(media >= 52) {
			jugadorSeleccionado.setImage(mega_Estado1);
		} else if(media >= 44) {
			jugadorSeleccionado.setImage(plus_Estado1);
		} else if(media >= 36) {
			jugadorSeleccionado.setImage(oro_Estado1);
		} else if(media >= 28) {
			jugadorSeleccionado.setImage(plata_Estado1);
		} else {
			jugadorSeleccionado.setImage(bronze_Estado1);
		}
		
		estadisticaSeleccionada.get(0).setText(jugador.getNombre()); // Nombre
		estadisticaSeleccionada.get(1).setText(media + ""); // Media (convertido a String)
		estadisticaSeleccionada.get(2).setText(jugador.getFuerzaAtaque() + ""); // Fuerza Ataque
		estadisticaSeleccionada.get(3).setText(jugador.getFuerzaDefensa() + ""); // Fuerza Defensa
		estadisticaSeleccionada.get(4).setText(jugador.getFuerzaTecnica() + ""); // Fuerza Técnica
		estadisticaSeleccionada.get(5).setText(jugador.getFuerzaPortero() + ""); // Fuerza Portero
		estadisticaSeleccionada.get(6).setText(jugador.getPosicion()); // Posición
		
		seleccionJugador.setVisible(false);
		panelEstadisticaSeleccionado.setVisible(true);
		
		boolean todosVisibles = true;
		
		for(Pane estadistica: listaPaneEstadisticas) {
			if(!estadistica.isVisible()) {
				todosVisibles = false;
			}
		}
		
		if(todosVisibles) {
			btnEmpezar.setImage(btnEmpezar_Estado1);
		}		
	}

	private void rellenarJugadoresSeleccion(List<Jugador> listaJugadores) {
		for(int i = 0; i < listaJugadores.size(); i++) {
			List<Text> estadistica = listaSEstadisticas.get(i);
			Jugador jugador = listaJugadores.get(i);
			int media = jugador.calcularMedia();
			
			estadistica.get(0).setText(jugador.getNombre());
			estadistica.get(1).setText(String.valueOf(media));
			estadistica.get(2).setText(String.valueOf(jugador.getFuerzaAtaque()));
			estadistica.get(3).setText(String.valueOf(jugador.getFuerzaDefensa()));
			estadistica.get(4).setText(String.valueOf(jugador.getFuerzaTecnica()));
			estadistica.get(5).setText(String.valueOf(jugador.getFuerzaPortero()));
			estadistica.get(6).setText(jugador.getPosicion());
			
			ImageView imagen = listaImgSeleccion.get(i);
			if(media >= 52) {
				imagen.setImage(mega_Estado1);
			} else if(media >= 44) {
				imagen.setImage(plus_Estado1);
			} else if(media >= 36) {
				imagen.setImage(oro_Estado1);
			} else if(media >= 28) {
				imagen.setImage(plata_Estado1);
			} else {
				imagen.setImage(bronze_Estado1);
			}
		}
		
		seleccionJugador.setVisible(true);
		
	}

	public Equipo getEquipoJugador() {
		return equipoJugador;
	}

	public void setEquipoJugador(Equipo equipoJugador) {
		this.equipoJugador = equipoJugador;
	}
	
}//fin class