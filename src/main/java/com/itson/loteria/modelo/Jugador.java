package com.itson.loteria.modelo;

import java.util.ArrayList;
import java.util.List;

public class Jugador {

  private String nombre;
  private int puntaje;
  private String imagen;
  private Tablero tablero;
  private List<Patron> patrones = new ArrayList<>();

  public Jugador(String nombre, int puntaje, String imagen, Tablero tablero) {
    this.nombre = nombre;
    this.puntaje = puntaje;
    this.imagen = imagen;
    this.tablero = tablero;
  }

  public void restarPuntos(int puntos) {
    this.puntaje -= puntos;
  }

  public void sumarPuntos(int puntos) {
    this.puntaje += puntos;
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

  public List<Patron> obtenerPatrones() {
    return this.patrones;
  }

  public void agregarPatron(Patron patron) {
    this.patrones.add(patron);
  }
}
