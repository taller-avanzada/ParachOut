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
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Juego extends Application
{
	private GraphicsContext graficos;
	private Pane pane;
	private Scene escena;
	private Canvas lienzo;
	private Translate translate;
	private double posX = 0;
	private Rectangle rect;
	public HashMap<String,Image> imagenes;
	private Personaje player;
	private ArrayList<Obstaculo> obstaculos;
	@Override
	public void start(Stage ventana) throws Exception
	{
		inicializarComponentes();
		paint();
		gestionEventos();
		ventana.setScene(escena);
		ventana.setTitle("Actually this is a game");
		ventana.show();
		cicloDeJuego();
	}
	
	private void inicializarComponentes()
	{
		setearGraficos();
		cargarImagenes();
		instanciarJugadores();
		instanciarObstaculos();
		Label label = new Label("Vidas");
		label.setTranslateX(1000); //SETEARLE ESTILO y a�adir vidas
		//Si hubo hit, restar
		pane.getChildren().add(label);
	}
	
	private void instanciarObstaculos()
	{
		obstaculos = new ArrayList<Obstaculo>();
		Obstaculo obs1 = new Obstaculo(new Punto2D(0,500), 100, 100);
		obs1.setGraphics();
		obstaculos.add(obs1);
		pane.getChildren().add(obs1.getRectangle());
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
		pane.getChildren().add(lienzo);
		graficos = lienzo.getGraphicsContext2D();
		translate = new Translate();
	}

	public void cargarImagenes()
	{
		imagenes.put("background",new Image("graphics\\fondo.png",1280,720,false,false));
		imagenes.put("player",new Image("graphics\\player.png",1280,720,false,false));
	}
	
	
	public void paint()
	{
		graficos.drawImage(imagenes.get("background"),0,0);
		//Mostrar vidas
	}
	
	private void checkEstados()
	{
		//Por cada obstaculo, si lo puse sacar vida
		for(Obstaculo obs : obstaculos)
		{
			if (player.checkColision(obs)) //Y adem�s pasaron 10 (duracion) segundos de inmunidad
			{
				player.hit();
				System.out.print("HIT!");
			}
		}
	}
	
	public void cicloDeJuego()
	{
		AnimationTimer animationTimer = new AnimationTimer() {
			long tiempoInicial = System.nanoTime();
			@Override
			//Este m�todo se ejecuta 60 veces por segundo
			public void handle(long tiempoActual) //Recibe como parametro lo que dura el frame
			{
				double t = (tiempoActual - tiempoInicial) / 1000000000.0;
				//System.out.println(t + " segundos");
				subirObstaculos(2.5); //Patente pendiente
				checkEstados();
				paint();
			}
		};
		animationTimer.start(); //Empieza el ciclo de juego
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
		launch(args);
	}
	
	
	
}
