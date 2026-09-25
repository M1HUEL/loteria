package com.itson.loteria.modelo;

public class Tablero {

  private final Carta[][] cartas;
  private final boolean[][] marcadas;

  public Tablero(Carta[][] cartas) {
    this.cartas = cartas;
    this.marcadas = new boolean[cartas.length][cartas[0].length];
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
}
