package com.itson.loteria.modelo;

public enum Patron {
  CHORRO("Chorro"),
  CRUZ("Cruz"),
  DIAGONAL("Diagonal"),
  ESQUINAS("Cuatro Esquinas"),
  CARTA_LLENA("Carta Llena");

  private final String nombre;

  Patron(String nombre) {
    this.nombre = nombre;
  }

  public String obtenerNombre() {
    return nombre;
  }

  @Override
  public String toString() {
    return nombre;
  }
}
