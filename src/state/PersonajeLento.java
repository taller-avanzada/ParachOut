package state;


public class PersonajeLento extends EstadoPersonaje {

	private int duracion = 10; // TODO implementar algun dia -- Y ese día llegó... 27/10
	
	public PersonajeLento()
	{
		this.velocidadY = 5;
	}
	
	@Override
	public EstadoPersonaje hit(int vidas) {
		
		if (vidas < 1) {
			return new PersonajeMuerto();
		}
		
		return new PersonajeLento();
		
	}
}
