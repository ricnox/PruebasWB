package spring.batch;

import java.util.ArrayList;
import java.util.Date;

/*
 * Clase usuario
 * Contiene los campos que necesitaremos tanto para la ejecución del batch
 * como para la aplicación de las reglas
 */
public class Usuario {
	
	private int id;
	
	private String nombre;
	
	private String apellidos;
	
	private int edad;
	
	private Double precio;
	
	private Date fecha;
	
	private ArrayList<String> oferta;
	
	
	// Getters y Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public ArrayList<String> getOferta() {
		return oferta;
	}

	public void setOferta(ArrayList<String> oferta) {
		this.oferta = oferta;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", precio=" + precio + ", fecha=" + fecha + "]";
	}
	
	

}
