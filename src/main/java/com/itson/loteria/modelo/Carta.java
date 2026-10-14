package com.itson.loteria.modelo;

public class Carta {

  private final String nombre;
  private final String imagen;

  public Carta(String nombre, String imagen) {
    this.nombre = nombre;
    this.imagen = imagen;
  }

  public String obtenerNombre() {
    return this.nombre;
  }

  public String obtenerImagen() {
    return this.imagen;
  }

  public boolean esMisma(Carta otra) {
    return otra != null && this.nombre.equals(otra.obtenerNombre());
  }
}
