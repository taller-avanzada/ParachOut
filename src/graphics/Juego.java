package graphics;


import java.util.ArrayList;
import java.util.HashMap;

import elementos.Obstaculo;
import elementos.Personaje;
import elementos.Punto2D;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import state.PersonajeLento;

public class Juego extends Application
{
	private GraphicsContext graficos;
	private Pane pane;
	private Scene escena;
	private Label label;
	private Canvas lienzo;
	private Personaje player;
	public HashMap<String,Image> imagenes;
	private ArrayList<Obstaculo> obstaculos;
	
	@Override
	public void start(Stage ventana) throws Exception
	{
		inicializarComponentes();
		paint();
		gestionEventos();
		ventana.setTitle("Parachout");
		ventana.setScene(escena);
		ventana.show();
		cicloDeJuego();
	}
	
	private void inicializarComponentes()
	{
		setearGraficos();
		cargarImagenes();
		instanciarObstaculos();
		instanciarJugadores();
		label = new Label("Vidas: " + player.getVidas());
		label.setTranslateX(1000);
		pane.getChildren().add(label);
	}
	
	private void instanciarObstaculos()
	{
		obstaculos = new ArrayList<Obstaculo>();
		Obstaculo obs1 = new Obstaculo(new Punto2D(0,500), 100, 100);
		obs1.setGraphics();
		obstaculos.add(obs1);
		Obstaculo obs2 = new Obstaculo(new Punto2D(0,2000), 100, 100);
		obs2.setGraphics();
		obstaculos.add(obs2);
		Obstaculo obs3 = new Obstaculo(new Punto2D(0,3000), 100, 100);
		obs3.setGraphics();
		obstaculos.add(obs3);
		pane.getChildren().add(obs1.getRectangle());
		pane.getChildren().add(obs2.getRectangle());
		pane.getChildren().add(obs3.getRectangle());
	}

	private void instanciarJugadores()
	{
		player = new Personaje(0,0);
		player.setGraphics(new ImagePattern(imagenes.get("player")));
		pane.getChildren().add(player.getRectangle());
		
	}

	private void setearGraficos()
	{
		pane = new Pane();
		escena = new Scene(pane,1280,720);
		lienzo = new Canvas(1280,780);
		imagenes = new HashMap<String,Image>();
		graficos = lienzo.getGraphicsContext2D();
		pane.getChildren().add(lienzo);
	}

	public void cargarImagenes()
	{
		imagenes.put("background",new Image("graphics\\fondo.png",1280,720,false,false));
		imagenes.put("player",new Image("graphics\\player.png",1280,720,false,false));
	}
	
	public void paint()
	{
		graficos.drawImage(imagenes.get("background"),0,0);
	}
	
	private void checkEstados()
	{
		for(Obstaculo obs : obstaculos)
		{
			if (player.checkColision(obs)) 
			{
				player.hit();
				label.setText("Vidas: " + player.getVidas());
			}
		}
		if (System.currentTimeMillis() - player.getLastHit() > PersonajeLento.duracion * 1000 && player.getLastHit() != 0)
		{
			player.reestablecerEstado();
		}
	}
	
	public void cicloDeJuego()
	{
		AnimationTimer animationTimer = new AnimationTimer() {
			@Override
			//Este método se ejecuta 60 veces por segundo
			public void handle(long tiempoActual) //Recibe como parametro lo que dura el frame
			{
				subirObstaculos(player.getVelocidadY());
				//Cargar meta al mismo tiempo que los obstaculos
				checkEstados();
				paint();
			}
		};
		animationTimer.start();
	}
	
	private void subirObstaculos(double velocidad)
	{
		for(Obstaculo obs : obstaculos)
		{
			obs.subir(velocidad);
		}
	}
	
	public void gestionEventos()
	{
		escena.setOnKeyPressed(new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event)
			{
				if(event.getCode() == KeyCode.RIGHT){
					if (player.getPosX() + player.getAncho() < 1280)
						player.moverDerecha();
			    }
			    else if(event.getCode() == KeyCode.LEFT){
			    	if (player.getPosX() > 0)
			    		player.moverIzquierda();
			    }
			}
		});
	}

	
	public static void main(String[] args)
	{
		launch(args); //IMPLEMENTAR MENU
	}
	
}
