package com.itson.loteria.modelo;

public interface Fachada {

  boolean validarCasilla(Jugador jugador, int fila, int columna);

  boolean validarPatron(Patron patron);

  boolean validarVictoria();
}
