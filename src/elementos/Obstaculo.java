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
}
