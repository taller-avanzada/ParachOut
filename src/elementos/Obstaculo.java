package elementos;

public class Obstaculo
{
	private Punto2D posicion;
	private double ancho;
	private double largo;
	
	public Obstaculo(Punto2D pos, double ancho, double largo)
	{
		this.posicion = pos;
		this.ancho = ancho;
		this.largo = largo;
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
	
}
