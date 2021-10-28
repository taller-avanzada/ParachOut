package state;


public class PersonajeLento extends EstadoPersonaje {

	public final static int duracion = 5; // TODO implementar algun dia -- Y ese d�a lleg�... 27/10
	
	public PersonajeLento()
	{
		this.velocidadY = 1.25;
	}
	
	@Override
	public EstadoPersonaje hit(int vidas) {
		
		if (vidas < 1) {
			return new PersonajeMuerto();
		}
		
		return new PersonajeLento();
	}
}
