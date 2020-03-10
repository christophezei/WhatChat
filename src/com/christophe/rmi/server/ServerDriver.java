package com.christophe.rmi.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.JFrame;

public class ServerDriver {

	public static void main(String[] args) throws RemoteException, MalformedURLException {
		Naming.rebind("RMIChatServer",new Server());
		System.out.println("Server Ready!");
	}

}
