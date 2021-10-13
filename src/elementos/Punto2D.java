package elementos;

import java.util.Objects;

public class Punto2D
{
	private double x;
	private	double y;
	
	public Punto2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void desplazarX(double dx) {
		this.x += dx;
	}
	
	public void desplazarY(double dy) {
		this.y += dy;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Punto2D other = (Punto2D) obj;
		return Double.doubleToLongBits(x) == Double.doubleToLongBits(other.x)
				&& Double.doubleToLongBits(y) == Double.doubleToLongBits(other.y);
	}
	
	
}
