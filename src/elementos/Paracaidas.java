package elementos;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Paracaidas 
{
	private Punto2D posicion;
	private double ancho;
	private double largo;
	private Rectangle rectangulo;
	
	public Paracaidas(Punto2D pos, double ancho, double largo)
	{
		this.posicion = pos;
		this.ancho = ancho;
		this.largo = largo;
		this.rectangulo = new Rectangle(0,0,ancho,largo);
		rectangulo.setTranslateX(pos.getX());
		rectangulo.setTranslateY(pos.getY());
	}
	
	public void subir(double cant)
	{
		this.rectangulo.setTranslateY(rectangulo.getTranslateY() - cant);
	}

	public Punto2D getPosicion()
	{
		return posicion;
	}

	public double getAncho()
	{
		return ancho;
	}

	public double getLargo()
	{
		return largo;
	}
	
	public boolean colision( Personaje personaje ) {
		
		if( this.posicion.getX() > personaje.getPosicion().getX() + personaje.getAncho()) {
			return false;
		}
		if( this.posicion.getX() + this.ancho < personaje.getPosicion().getX()) {
			return false;
		}
		if(this.posicion.getY() > personaje.getPosicion().getY() + personaje.getAlto()) {
			return false;
		}
		if(this.posicion.getY() + this.largo < personaje.getPosicion().getY()) {
			return false;
		}
		return true;
		
	}

	public void setGraphics(ImagePattern image)
	{
		this.rectangulo.setFill(image);
	}

	public Rectangle getRectangle()
	{
		return this.rectangulo;
	}
	
	public double getPosX() {
        return rectangulo.getTranslateX();
    }
	
	public double getPosY() {
        return rectangulo.getTranslateY();
    }
}