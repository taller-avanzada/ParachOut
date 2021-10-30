package graphics;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


import elementos.Obstaculo;
import elementos.Paracaidas;
import elementos.Personaje;
import elementos.Punto2D;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import state.PersonajeLento;

public class Juego extends Application
{
	private Stage referenceToPrimary;
	private GraphicsContext graficos;
	private GraphicsContext graficosMenu;
	private TextField userName;
	private Pane pane;
	private Scene escena;
	private Canvas lienzo;
	private Canvas menuCanvas;
	private Personaje player;
	public HashMap<String,Image> imagenes;
	public HashMap<String,String> sonidos;
	private ArrayList<Obstaculo> obstaculos;
	private ArrayList<Paracaidas> paracaidas;
	private ArrayList<Rectangle> nubes;
	private Rectangle vidas[];
	AudioClip mediaPlayer;
	Media media;
	
	//private ArrayList<Obstaculo> obstaculosAEliminar;
	@Override
	public void start(Stage ventana) throws Exception
	{
		referenceToPrimary = ventana;
		cargarImagenes();
		cargarSonidos();
		Pane menu = new Pane();
		Scene sceneMenu = new Scene(menu,1280,720);
		menuCanvas = new Canvas(1280,720);
		ventana.setScene(sceneMenu);
		menu.getChildren().add(menuCanvas);
		graficosMenu = menuCanvas.getGraphicsContext2D();
		graficosMenu.drawImage(imagenes.get("menu"),0,0);
		userName = new TextField("user");//nombre por default
        userName.setEditable(true);
        userName.setLayoutX(1280/2-270);
        userName.setLayoutY(720/2-10);
        userName.setPrefHeight(50);
        userName.setPrefWidth(500);
        userName.setFont(Font.font("verdana",FontWeight.BOLD,20));
		
        menu.getChildren().add(userName);
		
        
        media = new Media(new File(sonidos.get("intro")).toURI().toString());  
        
        mediaPlayer = new AudioClip(media.getSource());
        mediaPlayer.play();
        
        
		sceneMenu.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER) {
                	mediaPlayer.stop();
                	pane = new Pane();
                	escena = new Scene(pane,1280,720);
                	inicializarComponentes();
                	paintBackground();
                	gestionEventos();
                	ventana.setTitle("Parachout");
                	ventana.setScene(escena);
                	ventana.show();
                	media = new Media(new File(sonidos.get("game")).toURI().toString());  
                    mediaPlayer = new AudioClip(media.getSource());
                    mediaPlayer.play();
                	cicloDeJuego();
                }
            }
        });
		
		ventana.show();
	}
	
	private void cargarSonidos()
	{
		sonidos = new HashMap<String,String>();
		sonidos.put("intro","bin\\music\\intro.wav");
		sonidos.put("game","bin\\music\\game.mp3");
		sonidos.put("end","bin\\music\\end.mp3");
		sonidos.put("hit","bin\\music\\hit.mp3");
		sonidos.put("hp","bin\\music\\hp.mp3");
	}
	
	private void inicializarComponentes()
	{
		playMusic();
		setearGraficos();
		generarNubes();
		instanciarParacaidas();
		instanciarObstaculos();
		instanciarJugadores();
		iniciarVidas();
		pintarVidas();
	}

	private void playMusic()
	{
		
	}
	

	private void iniciarVidas()
	{
		vidas = new Rectangle[3];
		Rectangle vida1 = new Rectangle(1100,0,35,35);
		Rectangle vida2 = new Rectangle(1150,0,35,35);
		Rectangle vida3 = new Rectangle(1200,0,35,35);
		vida1.setFill(new ImagePattern(imagenes.get("vidas")));
		vida2.setFill(new ImagePattern(imagenes.get("vidas")));
		vida3.setFill(new ImagePattern(imagenes.get("vidas")));
		vidas[0] = vida1;
		vidas[1] = vida2;
		vidas[2] = vida3;
		pane.getChildren().add(vida1);
		pane.getChildren().add(vida2);
		pane.getChildren().add(vida3);
	}

	private void generarNubes()
	{
		
		nubes = new ArrayList<Rectangle>();
		int posY = 720;
		Rectangle c1,c2,c3;
		int r1,r2,r3;
		Random r = new Random();
		r1 = r.nextInt(1180);
		
		for (int i = 0; i < 250; i++)
		{
	
				c1 = new Rectangle(r1, posY+=200, 100, 100);
				c1.setFill(new ImagePattern(imagenes.get("nube")));
				nubes.add(c1);
				pane.getChildren().add(c1);
				do
				{
					r2 = r.nextInt(1180);
				}while(Math.abs(r2 - r1) < 500);
				
				c2 = new Rectangle(r1+250, posY, 100, 100);
				c2.setFill(new ImagePattern(imagenes.get("nube")));
				nubes.add(c2);
				pane.getChildren().add(c2);
				
				do
				{
					r3 = r.nextInt(1180);
				}while(Math.abs(r3 - r2) < 250 && Math.abs(r3 - r1) < 250);
				
				c3 = new Rectangle(r3, posY, 100, 100);
				c3.setFill(new ImagePattern(imagenes.get("nube")));
				nubes.add(c3);
				pane.getChildren().add(c3);
		}
	}

	private void instanciarParacaidas()
	{
		paracaidas = new ArrayList<Paracaidas>();
		int posY = 200;
		Random r = new Random();
		
		for (int i = 0; i < 10; i++)
		{
			Paracaidas par = new Paracaidas(new Punto2D(r.nextInt(1180),posY+=5000), 50, 100);
			par.setGraphics(new ImagePattern(imagenes.get("paracaidas")));
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
				Obstaculo obs1 = new Obstaculo(new Punto2D(0,posY+=500), primerRandom, segundoRandom);
				obs1.setGraphics(new ImagePattern(imagenes.get("obstaculo")));
				obstaculos.add(obs1);
				pane.getChildren().add(obs1.getRectangle());
				
				Obstaculo obs2 = new Obstaculo(new Punto2D(primerRandom + 250,posY), 1280 - primerRandom - 150, segundoRandom);
				obs2.setGraphics(new ImagePattern(imagenes.get("obstaculo")));
				obstaculos.add(obs2);
				pane.getChildren().add(obs2.getRectangle());
			}
			else
			{
				primerRandom = r.nextInt(1180);
				Obstaculo obs1 = new Obstaculo(new Punto2D(primerRandom,posY+=200), r.nextInt(100)+50, r.nextInt(100)+50);
				obs1.setGraphics(new ImagePattern(imagenes.get("obstaculo")));
				obstaculos.add(obs1);
				pane.getChildren().add(obs1.getRectangle());
				do
				{
					segundoRandom = r.nextInt(1180);
				}while(Math.abs(segundoRandom - primerRandom) < 500);
				
				Obstaculo obs2 = new Obstaculo(new Punto2D(segundoRandom,posY), r.nextInt(100)+50, r.nextInt(100)+50);
				obs2.setGraphics(new ImagePattern(imagenes.get("obstaculo")));
				obstaculos.add(obs2);
				pane.getChildren().add(obs2.getRectangle());
				
				do
				{
					tercerRandom = r.nextInt(1180);
				}while(Math.abs(tercerRandom - segundoRandom) < 250 || Math.abs(tercerRandom - primerRandom) < 250);
				
				Obstaculo obs3 = new Obstaculo(new Punto2D(tercerRandom,posY), r.nextInt(100)+50, r.nextInt(100)+50);
				obs3.setGraphics(new ImagePattern(imagenes.get("obstaculo")));
				obstaculos.add(obs3);
				pane.getChildren().add(obs3.getRectangle());
			}
		}
	}

	private void instanciarJugadores()
	{
		player = new Personaje(0,0);
		player.setGraphics(new ImagePattern(imagenes.get("player")));
		player.setName(userName.getText());
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
		imagenes.put("background",new Image("graphics\\fondo_celeste.png",1280,720,false,false));
		imagenes.put("player",new Image("graphics\\player.png",1280,720,false,false));
		imagenes.put("menu",new Image("graphics\\menu.png",1280,720,false,false));
		imagenes.put("nube",new Image("graphics\\nube.png",1280,720,false,false));
		imagenes.put("paracaidas",new Image("graphics\\parachute.png",1280,720,false,false));
		imagenes.put("obstaculo",new Image("graphics\\obstaculo.jpg",1280,720,false,false));
		imagenes.put("vidas",new Image("graphics\\hp.png",1280,720,false,false));
		imagenes.put("ending",new Image("graphics\\ending.png",1280,720,false,false));
	}
	
	public void paintBackground()
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
				pintarVidas();
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
			pintarVidas();
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
	
	

	private void pintarVidas()
	{
		
		for(int i=0;i<3;++i){
			if(i < player.getVidas())
			     vidas[i].setVisible(true);
			else
				vidas[i].setVisible(false);
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
				subirParacaidas(player.getVelocidadY());
				subirNubes(player.getVelocidadY());
				player.subirPuntaje(player.getVelocidadY());
				checkEstados();
				
					if (player.estaMuerto())
					{
						mediaPlayer.stop();
						media = new Media(new File(sonidos.get("end")).toURI().toString());  
	                    mediaPlayer = new AudioClip(media.getSource());
	                    mediaPlayer.play();
						stop();
						Canvas pantallaFinal = new Canvas(1280,720);
				        Label score = new Label("YOUR FINAL SCORE IS: "+(int)player.getPuntaje());
				        Label user = new Label(player.getName().toUpperCase());
				        score.setLayoutX(720/2 + 25);
				        score.setLayoutY(720*8/10);
				        score.setTextFill(Color.WHITE);
				        score.setFont(Font.font("Consolas", FontWeight.BOLD, 36));
				        user.setLayoutX(10);
				        user.setLayoutY(657);
				        user.setTextFill(Color.WHITE);
				        user.setFont(Font.font("Consolas", FontWeight.BOLD, 36));
				       

				        Pane finalPane = new Pane();
				        GraphicsContext ending = pantallaFinal.getGraphicsContext2D();
				        ending.drawImage(imagenes.get("ending"),0,0);
				        finalPane.getChildren().add(pantallaFinal);
				        finalPane.getChildren().add(score);
				        finalPane.getChildren().add(user);
				        
				        referenceToPrimary.setTitle("End of the game");
				        Scene finalScene = new Scene(finalPane,1280,720);
				        
				        finalScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
				            @Override
				            public void handle(KeyEvent event) {
				                if(event.getCode() == KeyCode.ESCAPE) {
				                	mediaPlayer.stop();
				                	referenceToPrimary.close();
				                }
				            }
				        });

				        referenceToPrimary.setScene(finalScene);
				        referenceToPrimary.show();
					}
				player.getEstado().acelerar();
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
	
	private void subirNubes(double velocidad)
	{
		for(Rectangle cloud : nubes)
		{
			cloud.setTranslateY(cloud.getTranslateY() - velocidad);
		}
	}
	
	public void gestionEventos()
	{
		escena.setOnKeyPressed(new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event)
			{
				if(event.getCode() == KeyCode.RIGHT){
					
						player.moverDerecha();
			    }
			    else if(event.getCode() == KeyCode.LEFT){
			    	
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
