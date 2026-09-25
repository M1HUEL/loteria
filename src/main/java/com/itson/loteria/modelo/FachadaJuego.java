package com.itson.loteria.modelo;

public class FachadaJuego implements Fachada {

  @Override
  public boolean validarCasilla(Jugador jugador, int fila, int columna) {
    return false;
  }

  @Override
  public boolean validarPatron(Patron patron) {
    return true;
  }

  @Override
  public boolean validarVictoria() {
    return true;
  }
}
