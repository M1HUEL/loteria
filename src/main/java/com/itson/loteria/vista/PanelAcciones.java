package com.itson.loteria.vista;

import com.itson.loteria.controlador.ControladorJuego;
import com.itson.loteria.modelo.Jugador;
import com.itson.loteria.modelo.Modelo;
import com.itson.loteria.modelo.Patron;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class PanelAcciones extends JPanel {

  private final ControladorJuego controlador;
  private final Jugador jugador;
  private final JLabel lblCartaGritada;
  private final JLabel lblCartasRestantes;
  private final List<JLabel> lblPuntajes = new ArrayList<>();

  public PanelAcciones(ControladorJuego controlador, Jugador jugador, List<Jugador> jugadores) {
    this.controlador = controlador;
    this.jugador = jugador;

    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setPreferredSize(new Dimension(240, 0));

    Border lineaInferior = BorderFactory.createCompoundBorder(
      BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK),
      BorderFactory.createEmptyBorder(8, 6, 8, 6));

    lblCartaGritada = new JLabel("-", SwingConstants.CENTER);
    lblCartaGritada.setPreferredSize(new Dimension(10, 200));
    lblCartaGritada.setMinimumSize(new Dimension(10, 200));
    lblCartaGritada.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
    lblCartaGritada.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
    lblCartaGritada.setFont(lblCartaGritada.getFont().deriveFont(Font.BOLD, 18f));
    lblCartaGritada.setAlignmentX(Component.CENTER_ALIGNMENT);

    lblCartasRestantes = new JLabel("", SwingConstants.CENTER);
    lblCartasRestantes.setAlignmentX(Component.CENTER_ALIGNMENT);
    lblCartasRestantes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

    JLabel lblTituloCarta = new JLabel("Carta Gritada", SwingConstants.CENTER);
    lblTituloCarta.setFont(lblTituloCarta.getFont().deriveFont(Font.BOLD, 16f));
    lblTituloCarta.setAlignmentX(Component.CENTER_ALIGNMENT);
    lblTituloCarta.setMaximumSize(new Dimension(Integer.MAX_VALUE, lblTituloCarta.getPreferredSize().height));

    JPanel panelCarta = new JPanel();
    panelCarta.setLayout(new BoxLayout(panelCarta, BoxLayout.Y_AXIS));
    panelCarta.setBorder(lineaInferior);
    panelCarta.add(lblTituloCarta);
    panelCarta.add(Box.createVerticalStrut(4));
    panelCarta.add(lblCartasRestantes);
    panelCarta.add(Box.createVerticalStrut(8));
    panelCarta.add(lblCartaGritada);
    add(panelCarta);
    add(Box.createVerticalStrut(10));

    Dimension tamanoBoton = new Dimension(10, 36);

    JPanel panelPatrones = new JPanel();
    panelPatrones.setLayout(new BoxLayout(panelPatrones, BoxLayout.Y_AXIS));
    panelPatrones.setBorder(lineaInferior);
    for (Patron patron : Patron.values()) {
      JButton botonPatron = new JButton(patron.obtenerNombre());
      botonPatron.setPreferredSize(tamanoBoton);
      botonPatron.setMinimumSize(tamanoBoton);
      botonPatron.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
      botonPatron.setAlignmentX(Component.CENTER_ALIGNMENT);
      botonPatron.addActionListener(evento -> seleccionarPatron(patron));
      panelPatrones.add(botonPatron);
      panelPatrones.add(Box.createVerticalStrut(8));
    }
    JButton botonVictoria = new JButton("Cantar victoria");
    botonVictoria.setPreferredSize(tamanoBoton);
    botonVictoria.setMinimumSize(tamanoBoton);
    botonVictoria.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
    botonVictoria.setAlignmentX(Component.CENTER_ALIGNMENT);
    botonVictoria.addActionListener(evento -> seleccionarVictoria());
    panelPatrones.add(botonVictoria);
    add(panelPatrones);
    add(Box.createVerticalStrut(10));

    JPanel panelPuntajes = new JPanel(new GridLayout(0, 1, 0, 8));
    panelPuntajes.setBorder(lineaInferior);
    for (int i = 0; i < jugadores.size(); i++) {
      JLabel lblPuntaje = new JLabel();
      this.lblPuntajes.add(lblPuntaje);
      panelPuntajes.add(lblPuntaje);
    }
    add(panelPuntajes);
    add(Box.createVerticalStrut(10));

    JButton botonSalir = new JButton("Salir");
    botonSalir.setPreferredSize(tamanoBoton);
    botonSalir.setMinimumSize(tamanoBoton);
    botonSalir.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
    botonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
    botonSalir.addActionListener(evento -> System.exit(0));
    JPanel panelSalir = new JPanel();
    panelSalir.setLayout(new BoxLayout(panelSalir, BoxLayout.Y_AXIS));
    panelSalir.setBorder(lineaInferior);
    panelSalir.add(botonSalir);
    add(panelSalir);
  }

  private void seleccionarPatron(Patron patron) {
    this.controlador.seleccionarPatron(this.jugador, patron);
  }

  private void seleccionarVictoria() {
    this.controlador.seleccionarVictoria(this.jugador);
  }

  public void actualizar(Modelo modelo) {
    lblCartaGritada.setText(modelo.obtenerCartaActual() == null ? "-" : modelo.obtenerCartaActual().obtenerNombre());
    lblCartasRestantes.setText(modelo.obtenerCartasRestantes() + " cartas restantes");

    for (int i = 0; i < modelo.obtenerJugadores().size(); i++) {
      Jugador jugadorDelModelo = modelo.obtenerJugadores().get(i);
      this.lblPuntajes.get(i).setText(jugadorDelModelo.obtenerNombre() + " = " + jugadorDelModelo.obtenerPuntaje());
    }

    revalidate();
    repaint();
  }
}
