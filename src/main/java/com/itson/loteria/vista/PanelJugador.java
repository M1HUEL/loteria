package com.itson.loteria.vista;

import com.itson.loteria.controlador.ControladorJuego;
import com.itson.loteria.modelo.Jugador;
import com.itson.loteria.modelo.Tablero;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelJugador extends JPanel {

  private static final int FILAS_TABLERO = 4;
  private static final int COLUMNAS_TABLERO = 4;

  private final Jugador jugador;
  private final ControladorJuego controlador;
  private final JButton[][] botones = new JButton[FILAS_TABLERO][COLUMNAS_TABLERO];

  public PanelJugador(Jugador jugador, ControladorJuego controlador) {
    this.jugador = jugador;
    this.controlador = controlador;

    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
    add(new JLabel(jugador.obtenerNombre()), BorderLayout.NORTH);

    JPanel tablero = new JPanel(new GridLayout(FILAS_TABLERO, COLUMNAS_TABLERO));
    for (int f = 0; f < FILAS_TABLERO; f++) {
      final int fila = f;
      for (int c = 0; c < COLUMNAS_TABLERO; c++) {
        final int columna = c;
        JButton casilla = new JButton(jugador.obtenerTablero().obtenerCarta(fila, columna).obtenerNombre());
        casilla.addActionListener(evento -> seleccionarCasilla(fila, columna));
        tablero.add(casilla);
        this.botones[fila][columna] = casilla;
      }
    }
    add(tablero, BorderLayout.CENTER);
  }

  public void seleccionarCasilla(int fila, int columna) {
    this.controlador.seleccionarCasilla(this.jugador, fila, columna);
  }

  public void actualizar() {
    Tablero tablero = this.jugador.obtenerTablero();
    for (int f = 0; f < FILAS_TABLERO; f++) {
      for (int c = 0; c < COLUMNAS_TABLERO; c++) {
        this.botones[f][c].setBackground(tablero.obtenerMarcada(f, c) ? Color.RED : Color.WHITE);
      }
    }
  }
}
