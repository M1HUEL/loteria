package com.itson.loteria.modelo;

import java.util.ArrayList;
import java.util.List;

public class Jugador {

  private String nombre;
  private int puntaje;
  private String imagen;
  private Carta[][] cartas;
  private boolean[][] marcadas;
  private List<Patron> patrones = new ArrayList<>();

  public Jugador(String nombre, int puntaje, String imagen, Carta[][] cartas) {
    this.nombre = nombre;
    this.puntaje = puntaje;
    this.imagen = imagen;
    this.cartas = cartas;
    this.marcadas = new boolean[cartas.length][cartas[0].length];
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

  public Carta obtenerCarta(int fila, int columna) {
    return cartas[fila][columna];
  }

  public boolean obtenerMarcada(int fila, int columna) {
    return marcadas[fila][columna];
  }

  public void seleccionarCasilla(int fila, int columna) {
    marcadas[fila][columna] = !marcadas[fila][columna];
  }

  public Carta[][] obtenerCartas() {
    return cartas;
  }

  public boolean[][] obtenerMarcadas() {
    return marcadas;
  }

  public List<Patron> obtenerPatrones() {
    return this.patrones;
  }

  public void agregarPatron(Patron patron) {
    this.patrones.add(patron);
  }
}
