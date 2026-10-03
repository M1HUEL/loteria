package com.itson.loteria.controlador;

import com.itson.loteria.modelo.Jugador;
import com.itson.loteria.modelo.ModeloJuego;
import com.itson.loteria.modelo.Patron;

public class ControladorJuego {

  private final ModeloJuego modelo;

  public ControladorJuego(ModeloJuego modelo) {
    this.modelo = modelo;
  }

  public void seleccionarCasilla(Jugador jugador, int fila, int columna) {
    modelo.seleccionarCasilla(jugador, fila, columna);
  }

  public void seleccionarPatron(Jugador jugador, Patron patron) {
    modelo.seleccionarPatron(jugador, patron);
  }

  public void seleccionarVictoria(Jugador jugador) {
    modelo.seleccionarVictoria(jugador);
  }
}
