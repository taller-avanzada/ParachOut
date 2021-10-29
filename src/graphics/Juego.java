package graphics;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import elementos.Obstaculo;
import elementos.Paracaidas;
import elementos.Partida;
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
	private GraphicsContext graficosMenu;
	private Pane pane;
	private Scene escena;
	private Label label;
	private Canvas lienzo;
	private Canvas menuCanvas;
	private Personaje player;
	public HashMap<String,Image> imagenes;
	private ArrayList<Obstaculo> obstaculos;
	private ArrayList<Paracaidas> paracaidas;
	//private ArrayList<Obstaculo> obstaculosAEliminar;
	@Override
	public void start(Stage ventana) throws Exception
	{
		cargarImagenes();
		Pane menu = new Pane();
		Scene sceneMenu = new Scene(menu,1280,720);
		menuCanvas = new Canvas(1280,720);
		ventana.setScene(sceneMenu);
		menu.getChildren().add(menuCanvas);
		graficosMenu = menuCanvas.getGraphicsContext2D();
		graficosMenu.drawImage(imagenes.get("menu"),0,0);
		sceneMenu.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER) {
                	pane = new Pane();
                	escena = new Scene(pane,1280,720);
                	inicializarComponentes();
                	paint();
                	gestionEventos();
                	ventana.setTitle("Parachout");
                	ventana.setScene(escena);
                	ventana.show();
                	cicloDeJuego();
                	ventana.show();
                }
            }
        });
		
		ventana.show();
	}
	
	private void inicializarComponentes()
	{
		setearGraficos();
		instanciarParacaidas();
		instanciarObstaculos();
		instanciarJugadores();
		label = new Label("Vidas: " + player.getVidas());
		label.setTranslateX(1000);
		pane.getChildren().add(label);
	}
	
	private void instanciarParacaidas()
	{
		paracaidas = new ArrayList<Paracaidas>();
		int posY = 200;
		Random r = new Random();
		
		for (int i = 0; i < 10; i++)
		{
			Paracaidas par = new Paracaidas(new Punto2D(r.nextInt(1180),posY+=5000), 50, 100);
			par.setGraphics();
			paracaidas.add(par);
			pane.getChildren().add(par.getRectangle());
		}
	}

	private void instanciarObstaculos()
	{
		obstaculos = new ArrayList<Obstaculo>();
		//obstaculosAEliminar = new ArrayList<Obstaculo>();
		int posY = 720;
		Random r = new Random();
		int primerRandom, segundoRandom, tercerRandom;
		/*
		 * la idea de guardar el primer random es hacer que siempre queden a al menos 500 de distancia
		 * entre ellos para evitar obstaculos muy cercanos
		 */
		
		for (int i = 0; i < 250; i++)
		{
			if(i%9 == 0)//cada 9 obstaculos genero este obstaculo particular
			{
				primerRandom = r.nextInt(1180);
				segundoRandom = r.nextInt(100) + 50;
				Obstaculo obs1 = new Obstaculo(new Punto2D(0,posY+=300), primerRandom, segundoRandom);
				obs1.setGraphics();
				obstaculos.add(obs1);
				pane.getChildren().add(obs1.getRectangle());
				
				Obstaculo obs2 = new Obstaculo(new Punto2D(primerRandom + 250,posY), 1280 - primerRandom - 150, segundoRandom);
				obs2.setGraphics();
				obstaculos.add(obs2);
				pane.getChildren().add(obs2.getRectangle());
			}
			else
			{
				primerRandom = r.nextInt(1180);
				Obstaculo obs1 = new Obstaculo(new Punto2D(primerRandom,posY+=200), r.nextInt(100)+50, r.nextInt(100)+50);
				obs1.setGraphics();
				obstaculos.add(obs1);
				pane.getChildren().add(obs1.getRectangle());
				do
				{
					segundoRandom = r.nextInt(1180);
				}while(Math.abs(segundoRandom - primerRandom) < 500);
				
				Obstaculo obs2 = new Obstaculo(new Punto2D(segundoRandom,posY), r.nextInt(100)+50, r.nextInt(100)+50);
				obs2.setGraphics();
				obstaculos.add(obs2);
				pane.getChildren().add(obs2.getRectangle());
				
				do
				{
					tercerRandom = r.nextInt(1180);
				}while(Math.abs(tercerRandom - segundoRandom) < 250 && Math.abs(tercerRandom - primerRandom) < 250);
				
				Obstaculo obs3 = new Obstaculo(new Punto2D(tercerRandom,posY), r.nextInt(100)+50, r.nextInt(100)+50);
				obs3.setGraphics();
				obstaculos.add(obs3);
				pane.getChildren().add(obs3.getRectangle());
			}
		}
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
		graficos = lienzo.getGraphicsContext2D();
		pane.getChildren().add(lienzo);
	}

	public void cargarImagenes()
	{
		imagenes = new HashMap<String,Image>();
		imagenes.put("background",new Image("graphics\\background.jpg",1280,720,false,false));
		imagenes.put("player",new Image("graphics\\player.png",1280,720,false,false));
		imagenes.put("menu",new Image("graphics\\menu.png",1280,720,false,false));
	}
	
	public void paint()
	{
		graficos.drawImage(imagenes.get("background"),0,0);
	}
	
	private void checkEstados()
	{
		for(int i = 0; i < 3; i++)
		{
			if (player.checkColisionObstaculo(obstaculos.get(i))) 
			{
				player.hit(player.getVelocidadY());
				label.setText("Vidas: " + player.getVidas());
			}
			if (obstaculos.get(i).getPosY() < -obstaculos.get(i).getLargo() )
			{
				if(obstaculos.size() > 3)
				{
					pane.getChildren().remove(obstaculos.get(i).getRectangle());
					obstaculos.remove(i);									
				}
			}
		}
		if (!paracaidas.isEmpty() && player.puedeRecoger() && player.checkColisionParacaidas(paracaidas.get(0)))
		{
			player.recogerParacaidas();
			label.setText("Vidas: " + player.getVidas());
			pane.getChildren().remove(paracaidas.get(0).getRectangle());
			paracaidas.remove(0); 
		}
		if (!paracaidas.isEmpty() && paracaidas.get(0).getPosY() < -paracaidas.get(0).getLargo() )
		{
			pane.getChildren().remove(paracaidas.get(0).getRectangle());
			paracaidas.remove(0); 
		}
		if (System.currentTimeMillis() - player.getLastHit() > PersonajeLento.duracion * 1000 && player.getLastHit() != 0)
		{
			player.reestablecerEstado(player.getVelocidadY());
		}
	}
	
	public void cicloDeJuego()
	{
		AnimationTimer animationTimer = new AnimationTimer() {
			@Override
			//Este método se ejecuta 60 veces por segundo
			public void handle(long tiempoActual) //Recibe como parametro lo que dura el frame
			{
				System.out.println(player.getVelocidadY());
				subirObstaculos(player.getVelocidadY());
				subirParacaidas(player.getVelocidadY());
				//Cargar meta al mismo tiempo que los obstaculos
				checkEstados();
				player.getEstado().acelerar();
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
	
	private void subirParacaidas(double velocidad)
	{
		for(Paracaidas par : paracaidas)
		{
			par.subir(velocidad);
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
