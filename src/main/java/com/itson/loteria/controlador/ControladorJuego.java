package com.itson.loteria.controlador;

import com.itson.loteria.modelo.ModeloJuego;
import com.itson.loteria.modelo.Patron;

public class ControladorJuego {

  private final ModeloJuego modelo;

  public ControladorJuego(ModeloJuego modelo) {
    this.modelo = modelo;
  }

  public void seleccionarCasilla(int jugador, int fila, int columna) {
    modelo.seleccionarCasilla(jugador, fila, columna);
  }

  public void seleccionarPatron(Patron patron) {
    modelo.seleccionarPatron(patron);
  }

  public void seleccionarVictoria() {
    modelo.seleccionarVictoria();
  }
}
