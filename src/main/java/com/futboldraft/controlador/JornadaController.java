package com.futboldraft.controlador;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.futboldraft.modelo.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class JornadaController {

	
	private BaseDatos bbdd = BaseDatos.getInstance();
	private List<Equipo> equipos, equipos2;
	private Equipo equipoJug;
	private int contJorn, jorn;
	private Equipo [][] eqEnfr;
	private List<Clasificacion> clasificaciones;
	private DraftController draftC;
	private boolean cambiarJug;
	private List<PartidoThread> partidos;
	
	@FXML
	private BorderPane pJugadores, pClasificacion;
	
	@FXML
	private TextField nombreFilter, nombreFilterEquipo;
	
	@FXML
	private TableView<JugadorTabla> tableView;
	
	@FXML
	private TableColumn<JugadorTabla, Integer> colId, colAtaque, colTecnica, colDefensa, colPortero;
	@FXML
	private TableColumn<JugadorTabla, String> colEquipo, colNombre, colPosicion;
	
	private ObservableList<JugadorTabla> listaOriginal;
	
	@FXML
	private TableView<ClasificacionTabla> tableViewEquipo;
	
	@FXML
	private TableColumn<ClasificacionTabla, Integer> colIdEquipo, colPuntos, colFavor, colContra, colPartidos;
	@FXML
	private TableColumn<ClasificacionTabla, String> colEquipo1;
	
	private ObservableList<ClasificacionTabla> listaOriginalEquipo;
	
	@FXML
    private ChoiceBox<String> choiceBoxJornadas;
    @FXML
    private ListView<String> listViewPartidos;
    @FXML
    private ListView<String> listViewEventos;
    
    @FXML
    private Text txtTiempo, txtContador;
	
	@FXML
	private ImageView btnSiguienteJornada, btnSalir, btnJugadores, btnClasificacion;
	
	private Image btnSJornada_Estado1 = new Image(getClass().getResource("/imagenes/Botones/btnSJornada.png").toExternalForm());
	private Image btnSJornada_Estado2 = new Image(getClass().getResource("/imagenes/Botones/btnSJornada_action.png").toExternalForm());
	private Image btnSJornada_dis = new Image(getClass().getResource("/imagenes/Botones/btnSJornada_dis.png").toExternalForm());
	private Image btnSalir_Estado1 = new Image(getClass().getResource("/imagenes/Botones/btnSalir.png").toExternalForm());
	private Image btnSalir_Estado2 = new Image(getClass().getResource("/imagenes/Botones/btnSalir_action.png").toExternalForm());
	
	private MainController mc;
	
	private final Map<Integer, List<PartidoThread>> jornadas = new HashMap<>();
    private final Map<String, List<String>> eventosPartidos = new HashMap<>();
    
    private Image btnJugadores_Estado1 = new Image(getClass().getResource("/imagenes/Botones/btnJugadores.png").toExternalForm());
	private Image btnJugadores_Estado2 = new Image(getClass().getResource("/imagenes/Botones/btnJugadores_action.png").toExternalForm());
	private Image btnClasificacion_Estado1 = new Image(getClass().getResource("/imagenes/Botones/leaderboard.png").toExternalForm());
	private Image btnClasificacion_Estado2 = new Image(getClass().getResource("/imagenes/Botones/leaderboard_action.png").toExternalForm());
	
	@FXML
	private void initialize() {
		mc = MainController.getInstance();
		contJorn = 0;
		jorn = 1;
		eqEnfr = new Equipo[190][2];
		equipos = bbdd.selectEquiposByNombre("%");
		equipoJug = mc.getEquipoJug();
		List<Equipo> equiposSinJug = bbdd.selectEquiposSinEqJug(equipoJug.getIdEquipo());
		
		Collections.shuffle(equipos);
		Equipo eqEl = null;
		for(Equipo equipo2 : equipos) {
			if(equipo2.getIdEquipo() == equipoJug.getIdEquipo()) {
				eqEl = equipo2;
			}
		}
		equipos.remove(eqEl);
		
		colId.setCellValueFactory(new PropertyValueFactory<>("idJugador"));
		colEquipo.setCellValueFactory(new PropertyValueFactory<>("equipo"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colPosicion.setCellValueFactory(new PropertyValueFactory<>("posicion"));
		colAtaque.setCellValueFactory(new PropertyValueFactory<>("fAtaque"));
		colTecnica.setCellValueFactory(new PropertyValueFactory<>("fTecnica"));
		colDefensa.setCellValueFactory(new PropertyValueFactory<>("fDefensa"));
		colPortero.setCellValueFactory(new PropertyValueFactory<>("fPortero"));
		
		colIdEquipo.setCellValueFactory(new PropertyValueFactory<>("idEquipo"));
	    colEquipo1.setCellValueFactory(new PropertyValueFactory<>("equipo"));
	    colPuntos.setCellValueFactory(new PropertyValueFactory<>("puntos"));
	    colFavor.setCellValueFactory(new PropertyValueFactory<>("golesFavor"));
	    colContra.setCellValueFactory(new PropertyValueFactory<>("golesContra"));
	    colPartidos.setCellValueFactory(new PropertyValueFactory<>("partidosJugados"));
		
		
		
		Equipo eqTemp = equipos.set(0, equipoJug);
		equipos.add(eqTemp);
		cambiarJug = false;
		
		bbdd.borrarClasificacion();
		for(int i = 0; i<equipos.size(); i++) {
			Clasificacion clas = new Clasificacion(equipos.get(i));
			clas.setIdEquipo(equipos.get(i).getIdEquipo());
			clas.setGolesContra(0);
			clas.setGolesFavor(0);
			clas.setPartidosJugados(0);
			clas.setPuntos(0);
			bbdd.insertarClasificacion(clas);
		}
		
		listViewPartidos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> mostrarEventos(newVal));
		
		choiceBoxJornadas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
	        if (newVal != null) {
	            int jornadaSeleccionada = Integer.parseInt(newVal.replace("Jornada ", ""));
	            mostrarJornada(jornadaSeleccionada);
	            
	        }
	    });
		
		obtenerJugadores();
		obtenerClasificacion();
		
	}
	
	@FXML
	private void aplicarFiltro() {
		String nombreBusqueda = nombreFilter.getText().toLowerCase();

		ObservableList<JugadorTabla> listaFiltrada = FXCollections.observableArrayList();
		for (JugadorTabla item : listaOriginal) {
			if (item.getNombre().toLowerCase().contains(nombreBusqueda)) {
				listaFiltrada.add(item);
			}
		}

		tableView.setItems(listaFiltrada);
	}
	
	@FXML
	private void aplicarFiltroEquipo() {
		String nombreBusqueda = nombreFilterEquipo.getText().toLowerCase();

		ObservableList<ClasificacionTabla> listaFiltrada = FXCollections.observableArrayList();
		for (ClasificacionTabla item : listaOriginalEquipo) {
			if (item.getEquipo().toLowerCase().contains(nombreBusqueda)) {
				listaFiltrada.add(item);
			}
		}

		tableViewEquipo.setItems(listaFiltrada);
	}	
	
	public void obtenerJugadores() {
	    Task<ObservableList<JugadorTabla>> task = new Task<>() {
	        @Override
	        protected ObservableList<JugadorTabla> call() {
	            listaOriginal = bbdd.obtenerJugadoresCla();
	            System.out.println("Datos obtenidos: " + listaOriginal);  // Verifica que la lista no esté vacía
	            return listaOriginal;
	        }
	    };

	    task.setOnSucceeded(event -> {
	        ObservableList<JugadorTabla> data = task.getValue();
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
	
	public void obtenerClasificacion() {
	    Task<ObservableList<ClasificacionTabla>> task = new Task<>() {
	        @Override
	        protected ObservableList<ClasificacionTabla> call() {
	        	listaOriginalEquipo = bbdd.obtenerClasificaciones();
	            System.out.println("Datos obtenidos: " + listaOriginalEquipo);  // Verifica que la lista no esté vacía
	            return listaOriginalEquipo;
	        }
	    };

	    task.setOnSucceeded(event -> {
	        ObservableList<ClasificacionTabla> data = task.getValue();
	        if (data != null && !data.isEmpty()) {
	            // Refrescar la tabla con los nuevos datos
	            tableViewEquipo.setItems(data);
	            System.out.println("Tabla Equipo Clasificacion actualizada con los datos.");
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
	
	private void mostrarJornada(int jornada) {
	    if (jornadas.containsKey(jornada)) {
	        List<PartidoThread> partidos = jornadas.get(jornada);
	        List<String> nombresPartidos = new ArrayList<>();

	        for (PartidoThread partido : partidos) {
	            nombresPartidos.add(partido.getNombrePartido());
	        }
	        listViewPartidos.getItems().setAll(nombresPartidos);
	        listViewEventos.getItems().clear();
	        
	    }
	}
	
	//botonSiguiente Jornada, inicializar jornada a 0 a 1, decidir despues
	@FXML
	private void enfrentamientosJornada() {
		obtenerJugadores();
		cambiarJug = false;
		if(jorn < 20) {
			choiceBoxJornadas.getItems().add("Jornada " + jorn);
	        choiceBoxJornadas.setValue("Jornada " + jorn);
	        
	        partidos = new ArrayList<>();
	        List<String> nombresPartidos = new ArrayList<>();
	        for (int i = 0; i < 19; i++) {
	        	//local
				if(i%2==0) {
					for (int  j = 0; j < equipos.size()/2; j++) {
		                eqEnfr[(i*equipos.size()/2)+j][0] = equipos.get(j);
		                eqEnfr[(i*equipos.size()/2)+j][1] = equipos.get(equipos.size() - 1 - j);
		                                     
		            }
					//visitante
				}else {
					for (int  j = 0; j < equipos.size()/2; j++) {
		            	eqEnfr[(i*equipos.size()/2)+j][1] = equipos.get(j);
		            	eqEnfr[(i*equipos.size()/2)+j][0] = equipos.get(equipos.size() - 1 - j);
		                      		                                  
		            }
					
				}
	            
	            // Rotar los equipos para la próxima jornada
	            Collections.rotate(equipos.subList(1, equipos.size()), 1);            
	        }
			
			for (int i = 0; i <equipos.size()/2; i++) {
				System.out.println(eqEnfr[i][0].getNombre() + " " + eqEnfr[i][1].getNombre());
	            String partidoNombre = eqEnfr[i+(10*contJorn)][0].getNombre() + " vs " + eqEnfr[i+(10*contJorn)][1].getNombre();
	            nombresPartidos.add(partidoNombre);
	            if(i == 0) {
	            	PartidoThread partido = new PartidoThread(partidoNombre, true, eqEnfr[i+(10*contJorn)][0], eqEnfr[i+(10*contJorn)][1], jorn, eventosPartidos, listViewPartidos, listViewEventos, txtContador);
		            partidos.add(partido);
		            eventosPartidos.put(partidoNombre, new ArrayList<>());
	            }else {
	            	PartidoThread partido = new PartidoThread(partidoNombre, false, eqEnfr[i+(10*contJorn)][0], eqEnfr[i+(10*contJorn)][1], jorn, eventosPartidos, listViewPartidos, listViewEventos, txtContador);
		            partidos.add(partido);
		            eventosPartidos.put(partidoNombre, new ArrayList<>());
	            }
	        }
			
			jornadas.put(jorn, partidos);
			listViewPartidos.getItems().setAll(nombresPartidos);
			
			bbdd.insertarJornada(new Jornada(jorn));
			
			for (PartidoThread partido : partidos) {
	            partido.start();
	        }
			
			new Thread(() -> {
	            for (PartidoThread partido : partidos) {
	                try {
	                    partido.join();
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	        }).start();
			
			contJorn++;
			jorn++;

		}	
		if(jorn%5 == 0) {
			clasificaciones = bbdd.selectClasificacionOrdenada("DESC");
			for(int i = 0; i < 3; i ++) {
				if(clasificaciones.get(i).getEquipo().equals(equipoJug)) {
					cambiarJug = true;
				}
			}
		}
		
		if(jorn >= 20) {
			btnSiguienteJornada.setImage(btnSJornada_dis);
		}
		
		obtenerClasificacion();
	}
	
	private void mostrarEventos(String partido) {
        if (partido != null) {
        	partidos.get(0).setImprimir(false);
        	listViewEventos.getItems().clear();
            listViewEventos.getItems().setAll(eventosPartidos.get(partido));
            int golesLoc = partidos.get(choiceBoxJornadas.getSelectionModel().getSelectedIndex()).getGolesLoc();
            int golesVis = partidos.get(choiceBoxJornadas.getSelectionModel().getSelectedIndex()).getGolesVis();
            System.out.println("Goles local: "+golesLoc);
            System.out.println("Goles vis: "+golesVis);
            txtContador.setText(golesLoc + " - "+ golesVis);
        }
    }
	
	public void hacerFichajes() {
		Jugador jPor, jDef, jMed, jDel, jPor1, jDef1, jMed1, jDel1;
		List<Jugador> lJugadores = bbdd.selectJugadoresEquipo(equipoJug.getIdEquipo());//Jugadores del usuario
		
		List<Jugador> lJugadoresP = bbdd.jugadorPosicionLibre("POR");
		List<Jugador> lJugadoresD = bbdd.jugadorPosicionLibre("DEF");
		List<Jugador> lJugadoresM = bbdd.jugadorPosicionLibre("MED");
		List<Jugador> lJugadoresDel = bbdd.jugadorPosicionLibre("DEL");
		
		//Obtener Jugadores Libres random
		jPor1 = lJugadoresP.get((int)(Math.random()*lJugadoresP.size()));
		jDef1 = lJugadoresD.get((int)(Math.random()*lJugadoresD.size()));
		jMed1 = lJugadoresM.get((int)(Math.random()*lJugadoresM.size()));
		jDel1 = lJugadoresDel.get((int)(Math.random()*lJugadoresDel.size()));
		
		//Jugador aleatorio de cada posicion del jugador
		do {
			jPor = lJugadores.get((int)(Math.random()*lJugadores.size()));
		}while(!jPor.getPosicion().equalsIgnoreCase("POR"));
		do {
			jDef = lJugadores.get((int)(Math.random()*lJugadores.size()));
		}while(!jDef.getPosicion().equalsIgnoreCase("DEF"));
		do {
			jMed = lJugadores.get((int)(Math.random()*lJugadores.size()));
		}while(!jMed.getPosicion().equalsIgnoreCase("MED"));
		do {
			jDel = lJugadores.get((int)(Math.random()*lJugadores.size()));
		}while(!jDel.getPosicion().equalsIgnoreCase("DEL"));	
		
		jPor.setEquipo(null);
		jDef.setEquipo(null);
		jMed.setEquipo(null);
		jDel.setEquipo(null);
		
		jPor1.setEquipo(equipoJug);
		jDef1.setEquipo(equipoJug);
		jMed1.setEquipo(equipoJug);
		jDel1.setEquipo(equipoJug);
		
		bbdd.insertarJugador(jPor);
		bbdd.insertarJugador(jDef);
		bbdd.insertarJugador(jMed);
		bbdd.insertarJugador(jDel);
		
		bbdd.insertarJugador(jPor1);
		bbdd.insertarJugador(jDef1);
		bbdd.insertarJugador(jMed1);
		bbdd.insertarJugador(jDel1);
	}
	
	
	public void cambiarJugador(String nombre, String posicion, String nombre1, String posicion1) {
		Jugador jug1 = bbdd.selectJugador(nombre, posicion);
		Jugador jug2 = bbdd.selectJugador(nombre1, posicion1);
		jug1.setEquipo(new Equipo(null));
		jug2.setEquipo(equipoJug);
		bbdd.insertarJugador(jug1);
		bbdd.insertarJugador(jug2);		
	}
	
	public void mostrarClasificacion(){
		List<Clasificacion> clasificacion = bbdd.selectClasificacionOrdenada("DESC");
	
	}
	

	@FXML
	public void clickBoton(MouseEvent event) {
		if(event.getSource() == btnSiguienteJornada) {
			if(!btnSiguienteJornada.getImage().equals(btnSJornada_dis)) {
				if (btnSiguienteJornada.getImage().equals(btnSJornada_Estado1)) {
					btnSiguienteJornada.setImage(btnSJornada_Estado2);
				} else {
					btnSiguienteJornada.setImage(btnSJornada_Estado1);
				}
			}
		} else if(event.getSource() == btnSalir) {
			if (btnSalir.getImage().equals(btnSalir_Estado1)) {
				btnSalir.setImage(btnSalir_Estado2);
			} else {
				btnSalir.setImage(btnSalir_Estado1);
			}
		} else if(event.getSource() == btnJugadores) {
			if (btnJugadores.getImage().equals(btnJugadores_Estado1)) {
				btnJugadores.setImage(btnJugadores_Estado2);
			} else {
				btnJugadores.setImage(btnJugadores_Estado1);
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
			if (event.getSource() == btnSiguienteJornada) {
				btnSiguienteJornada.setImage(btnSJornada_Estado1);
				enfrentamientosJornada();
			} else if (event.getSource() == btnSalir) {
				btnSalir.setImage(btnSalir_Estado1);
				try {
					mc.setSiguienteVista(MainController.LOGIN);
					mc.cargarVista(MainController.LOADING);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else if(event.getSource() == btnJugadores) {
				btnJugadores.setImage(btnJugadores_Estado1);
				if(pJugadores.isVisible()) {
					pJugadores.setVisible(false);
				} else {
					pJugadores.setVisible(true);
				}
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
}
