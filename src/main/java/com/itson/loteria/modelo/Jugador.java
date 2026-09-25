package com.itson.loteria.modelo;

public class Jugador {

  private String nombre;
  private int puntaje;
  private String imagen;
  private Tablero tablero;

  public Jugador(String nombre, int puntaje, String imagen, Tablero tablero) {
    this.nombre = nombre;
    this.puntaje = puntaje;
    this.imagen = imagen;
    this.tablero = tablero;
  }

  public String obtenerNombre() {
    return this.nombre;
  }

  public int obtenerPuntaje() {
    return this.puntaje;
  }

  public String obtenerImagen() {
    return this.imagen;
  }

  public Tablero obtenerTablero() {
    return this.tablero;
  }
}
