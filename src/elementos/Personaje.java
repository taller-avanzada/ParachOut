package elementos;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import state.EstadoPersonaje;
import state.PersonajeLento;
import state.PersonajeMuerto;
import state.PersonajeNormal;

public class Personaje {
	
	private Punto2D posicion;
	private double puntaje;
	private EstadoPersonaje estado; 
	private int vidas;
	private double velocidadX;
	private Rectangle rectangulo;
	final double ALTO = 100;
	final double ANCHO = 100;
	private long lastHit;
	private Image[] imagenes_estados = {
			new Image("graphics\\player.png",1280,720,false,false),
			new Image("graphics\\playerhit.png",1280,720,false,false),
			new Image("graphics\\fondo.png",1280,720,false,false)
	};
			
	public Personaje()
	{
		this.posicion = new Punto2D(0,0);
		this.setPuntaje(0);
		this.estado = new PersonajeNormal();
		this.velocidadX = 5;
		this.vidas = 3;
		this.rectangulo = new Rectangle(ANCHO,ALTO);
		lastHit = 0;
	}
	
	public Personaje(double x, double y)
	{
		this.posicion = new Punto2D(x,y);
		this.setPuntaje(0);
		this.estado = new PersonajeNormal();
		this.rectangulo = new Rectangle(x,y,ANCHO,ALTO);
        this.vidas = 3;
        this.velocidadX = 5;
        lastHit = 0;
	}
	
	
	public Rectangle getRectangle()
	{
		return this.rectangulo;
	}
	public void recogerParacaidas() {
		if (0 < vidas && vidas < 3)
			vidas++;
	}
	
	public void hit() {
		if (puedeGolpearse())
		{
			vidas--;
			estado = estado.hit(this.vidas);
			CambiarImagenSegunEstado();
			lastHit = System.currentTimeMillis();
		}
			
	}
	
	private void CambiarImagenSegunEstado()
	{
		if (estado.getClass().equals(new PersonajeNormal().getClass()))
		{
			setGraphics(new ImagePattern(imagenes_estados[0]));
		}
		if (estado.getClass().equals(new PersonajeLento().getClass()))
		{
			setGraphics(new ImagePattern(imagenes_estados[1]));
		}
		if (estado.getClass().equals(new PersonajeMuerto().getClass()))
		{
			setGraphics(new ImagePattern(imagenes_estados[2]));
		}
	}

	public long getLastHit()
	{
		return lastHit;
	}
	
	public void reestablecerEstado()
	{
		if (estado.getClass().equals(new PersonajeLento().getClass()))
		{
			this.estado = new PersonajeNormal();
			lastHit = 0;
			System.out.print("Estado reestablecido");
			setGraphics(new ImagePattern(imagenes_estados[0]));
			
		}
	}
	
	private boolean puedeGolpearse()
	{
		return estado.getClass().equals(new PersonajeNormal().getClass());
	}

	/**
	 * @return the estado
	 */
	public EstadoPersonaje getEstado() {
		return estado;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(puntaje);
	}

	// Esto no se si deberia comparar solo por puntaje o por todo, cuidado
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personaje other = (Personaje) obj;
		return puntaje == other.puntaje;
	}

	public double compararPuntaje(Personaje otro) {
		// TODO Auto-generated method stub
		return puntaje - otro.puntaje;
	}

	/**
	 * @return the vidas
	 */
	public int getVidas() {
		return vidas;
	}

	/**
	 * @param puntaje the puntaje to set
	 */
	public void setPuntaje(double puntaje) {
		this.puntaje = puntaje;
	}
	/*
	public void moverDerecha() {
		this.posicion.desplazarX(velocidadX);
	}*/
	
	public double getPuntaje(){
        return Math.abs(this.posicion.getY());
    }
	
	public void moverDerecha() {
        rectangulo.setTranslateX(rectangulo.getTranslateX()+velocidadX);
    }

    public void moverIzquierda() {
        rectangulo.setTranslateX(rectangulo.getTranslateX()-velocidadX);
    }

	
	public void caer() {
		this.posicion.desplazarY(estado.getVelocidadY());
	}

	public Punto2D getPosicion() {
		return this.posicion;
	}

	public double getAncho() {
		return ANCHO;
	}

	public double getAlto() {
		return ALTO;
	}	
	
	public double getPosX() {
        return rectangulo.getTranslateX();
    }
	
	public double getPosY() {
        return rectangulo.getTranslateY();
    }
	
	public void setGraphics(ImagePattern image)
	{
		this.rectangulo.setFill(image);
	}
	
	public boolean checkColision(Obstaculo obj)
	{
		if ( getPosX() > obj.getPosX() + obj.getAncho() ) {
			return false;
			}
			if ( getPosX()+ANCHO < obj.getPosX() ) {
			return false;
			}
			if ( getPosY() > obj.getPosY()+obj.getLargo() ) {
			return false;
			}
			if ( getPosY()+ALTO < obj.getPosY() ) {
			return false;
			}
			return true;
	}

	public double getVelocidadY()
	{
		return estado.getVelocidadY();
	}
}


