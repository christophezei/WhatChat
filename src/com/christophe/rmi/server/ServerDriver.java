package com.christophe.rmi.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerDriver {

	public static void main(String[] args) throws RemoteException, MalformedURLException {
		Registry registry = LocateRegistry.createRegistry(5000);
		registry.rebind("RMIChatServer", new Server());
		System.out.println("Server Ready!");
	}

}
