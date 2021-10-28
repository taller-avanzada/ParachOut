package elementos;

import java.util.Objects;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import state.EstadoPersonaje;
import state.PersonajeNormal;

public class Personaje {
	
	private Punto2D posicion;
	private double puntaje;
	private EstadoPersonaje estado; 
	private int vidas;
	private double velocidadX;
	private double ancho;
	private double alto;
	private Rectangle rectangulo;
	final double ALTO = 100;
	final double ANCHO = 100;
	public Personaje()
	{
		this.posicion = new Punto2D(0,0);
		this.setPuntaje(0);
		this.estado = new PersonajeNormal();
		this.velocidadX = 5;
		this.vidas = 3;
		this.ancho = 10;
		this.alto = 10;
		this.rectangulo = new Rectangle(ANCHO,ALTO);
	}
	
	public Personaje(double x, double y)
	{
		this.posicion = new Punto2D(x,y);
		this.setPuntaje(0);
		this.estado = new PersonajeNormal();
		this.rectangulo = new Rectangle(x,y,ANCHO,ALTO);
        this.vidas = 3;
        this.velocidadX = 5;
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
		this.vidas--;
		estado = estado.hit(this.vidas);
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
	/*
	public void moverIzquierda() {
		this.posicion.desplazarX(-velocidadX);
	}*/
	
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
}


