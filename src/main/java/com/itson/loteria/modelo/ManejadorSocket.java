package com.itson.loteria.modelo;

import javax.swing.SwingUtilities;

public class ManejadorSocket implements Socket {

  private final ModeloJuego modelo;

  public ManejadorSocket(ModeloJuego modelo) {
    this.modelo = modelo;
  }

  @Override
  public void recibirMensaje() {
    System.out.println("Socket: peticion recibida, cantando carta...");
    SwingUtilities.invokeLater(this.modelo::gritarCarta);
  }
}
