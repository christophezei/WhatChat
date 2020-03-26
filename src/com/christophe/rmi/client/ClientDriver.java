package com.christophe.rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JFrame;

import com.christophe.rmi.server.IServer;

public class ClientDriver {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		String serverURL = "rmi://127.0.0.1:5000/RMIChatServer";
		IServer server = (IServer) Naming.lookup(serverURL);
		String userName = args[0];
		new Thread(new Client(userName, server)).start();
		server.broadcastMessage(userName + " Has joined the chat room");
	}
}
