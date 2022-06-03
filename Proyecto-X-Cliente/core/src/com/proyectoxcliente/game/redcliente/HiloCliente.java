package com.proyectoxcliente.game.redcliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.proyectoxcliente.game.pantallas.Mapa;
import com.proyectoxcliente.game.utiles.Global;

public class HiloCliente extends Thread {

	private DatagramSocket conexion;
	private InetAddress ipServer;
	private boolean fin = false;
	private Mapa app;
	private JFrame frame;

	private int puerto = 4000;

	public HiloCliente(Mapa app) {
		this.app = app;
		frame = new JFrame();
		try {
			ipServer = InetAddress.getByName(JOptionPane.showInputDialog(frame, "Ingrese la IP"));
			conexion = new DatagramSocket();
			System.out.println(conexion.getInetAddress());
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		enviarMensaje("Conexion");
	}

	public void enviarMensaje(String mensaje) {
		byte[] data = mensaje.getBytes();
		DatagramPacket dp = new DatagramPacket(data, data.length, ipServer, puerto);
		try {
			conexion.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		do {
			byte[] data = new byte[1024];
			DatagramPacket dp = new DatagramPacket(data, data.length);
			try {
				conexion.receive(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}
			procesarMensaje(dp);
		} while (!fin);
	}

	private void procesarMensaje(final DatagramPacket dp) {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {

				String mensaje = (new String(dp.getData()).trim());
				String[] mensajeParametrizado = mensaje.split("\\*");
				if (mensajeParametrizado.length < 2) {
					if (mensaje.equals("Empieza")) {
						Global.empieza = true;
					}
				} else {
					if (mensajeParametrizado[0].equals("OK")) {
						ipServer = dp.getAddress();
						app.setNumeroJugador(Integer.parseInt(mensajeParametrizado[1]));
					} else if (mensajeParametrizado[0].equals("Actualizar")) {
						if (mensajeParametrizado[1].equals("P1")) {
							try {
								float posX = Float.parseFloat(mensajeParametrizado[2]);
								float posY = Float.parseFloat(mensajeParametrizado[3]);
								app.getPlayer()[0].setPosition(new Vector3(posX, posY, 0));
							} catch (Exception e) {
							}

						} else if (mensajeParametrizado[1].equals("P2")) {
							try {
								float posX = Float.parseFloat(mensajeParametrizado[2]);
								float posY = Float.parseFloat(mensajeParametrizado[3]);
								app.getPlayer()[1].setPosition(new Vector3(posX, posY, 0));
							} catch (Exception e) {
							}
						}
					} else if (mensajeParametrizado[0].equals("PosicionTopTube")) {
						float posY = Float.parseFloat(mensajeParametrizado[2]);
						float posX = Float.parseFloat(mensajeParametrizado[1]);
						app.agregarTubo(posX, posY);
					} else if (mensajeParametrizado[0].equals("Reposicionar")) {

						float posX = Float.parseFloat(mensajeParametrizado[2]);
						float posY = Float.parseFloat(mensajeParametrizado[3]);
						int tubo = Integer.parseInt(mensajeParametrizado[4]);
						app.reposicionarTubo(posX, posY, tubo);
					} else if (mensajeParametrizado[0].equals("TerminaJuego")) {
						boolean gano = Boolean.parseBoolean(mensajeParametrizado[1]);
						app.setGano(gano);
						Global.termina = true;

					}
				}
			}
		});
	}
}
