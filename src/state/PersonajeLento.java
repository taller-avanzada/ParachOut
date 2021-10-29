package state;


public class PersonajeLento extends EstadoPersonaje {

	public final static int duracion = 5; // TODO implementar algun dia -- Y ese día llegó... 27/10
	
	public PersonajeLento(double velocidadY)
	{
		this.velocidadY = velocidadY/2;
	}
	
	@Override
	public EstadoPersonaje hit(int vidas, double velocidadY) {
		
		if (vidas < 1) {
			return new PersonajeMuerto();
		}
		
		return new PersonajeLento(velocidadY);
	}
}
