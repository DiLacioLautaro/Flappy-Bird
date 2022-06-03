package com.proyectox.game.redservidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.badlogic.gdx.Gdx;
import com.proyectox.game.pantallas.Mapa;
import com.proyectox.game.utiles.Global;

public class HiloServidor extends Thread {

	private DatagramSocket conexion;
	private boolean fin = false;
	private DireccionRed[] clientes = new DireccionRed[2];
	private int cantClientes = 0;
	private Mapa app;

	public HiloServidor(Mapa app) {
		this.app = app;
		try {
			conexion = new DatagramSocket(5000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	} 


	public void enviarMensaje(String msg, InetAddress ip, int puerto) {
		byte[] data = msg.getBytes();
		DatagramPacket dp = new DatagramPacket(data, data.length, ip, puerto);
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
				String msg = (new String(dp.getData())).trim();
				int nroCliente = -1;

				if (cantClientes > 1) { // Detecto que cliente me esta mandando mensajes conociendo su ip y su puerto
					for (int i = 0; i < clientes.length; i++) {
						if (dp.getPort() == clientes[i].getPuerto() && dp.getAddress().equals(clientes[i].getIp())) {
							nroCliente = i;
						}
					}

				}
			
				if (cantClientes < 2) { // Si todavia no estan conectados ambos clientes
					if (msg.equals("Conexion")) {
						if (cantClientes < 2) {
							clientes[cantClientes] = new DireccionRed(dp.getAddress(), dp.getPort());
							enviarMensaje("OK*" + (cantClientes + 1), clientes[cantClientes].getIp(),
									clientes[cantClientes++].getPuerto()); 
							
							
							if (cantClientes == 2) {
								Global.empieza = true;
								for (int i = 0; i < clientes.length; i++) {
									enviarMensaje("Empieza", clientes[i].getIp(), clientes[i].getPuerto());
								}
							}
						}
					}
				} else { // Si estan conectados, comienzo con las verificaciones de jugabilidad
					if (nroCliente != -1) {
						if (msg.equals("ApreteSaltar")) {
							if(nroCliente == 0) {
								app.setMouseAbajo(true);
							} else {
								app.setMouseAbajo2(true) ;
							}
						} else if (msg.equals("DejeApretarSaltar")) {
							if(nroCliente == 0) {
								app.setMouseAbajo(false) ;
							} else {
								app.setMouseAbajo2(false) ;
							}
						}
					}
				}
			}
		});

	}

	public void enviarMensajeATodos(String msg) {
		for (int i = 0; i < clientes.length; i++) {
			enviarMensaje(msg, clientes[i].getIp(), clientes[i].getPuerto());
		}

	}
}
