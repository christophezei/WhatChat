package com.christophe.rmi.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.christophe.rmi.client.IClient;

public class Server extends UnicastRemoteObject implements IServer {
	private static final long serialVersionUID = 1L;
	private HashMap<String, IClient> clients;

	protected Server() throws RemoteException {
		clients = new HashMap<String, IClient>();
	}

	@Override
	public synchronized void registerClient(String clientName, IClient client) throws RemoteException {
		this.clients.put(clientName, client);
	}

	@Override
	public synchronized void privateMessage(String clientName, String message) throws RemoteException {
		clients.get(clientName).retrieveMessage(message, true);
	}

	@Override
	public synchronized void broadcastMessage(String message) throws RemoteException {
		Iterator hmIterator = clients.entrySet().iterator();
		while (hmIterator.hasNext()) {
			Map.Entry mapElement = (Map.Entry) hmIterator.next();
			((IClient) mapElement.getValue()).retrieveMessage(message, false);
		}

	}

	@Override
	public synchronized void writeToFile(String message) {
		String userHome = createDirectory();
		File log = new File(userHome + "/History.txt");
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(log, true));
			writer.println(message);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void writeToPrivFile(String clientName, String message) {
		String userHome = createDirectory();
		File log = new File(userHome + "/History-" + clientName + ".txt");
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(log, true));
			writer.println(message);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String createDirectory() {
		boolean success = false;
		String dir = System.getProperty("user.home");
		String fullPath = dir + "/WhatChatHistory";
		File directory = new File(fullPath);
		if (directory.exists() && directory.isDirectory()) {
			System.out.println("Directory already exists ...");

		} else {
			System.out.println("Directory not exists, creating now");

			success = directory.mkdir();
			if (success) {
				System.out.printf("Successfully created new directory : %s%n", dir);
			} else {
				System.out.printf("Failed to create new directory: %s%n", dir);
			}
		}
		return fullPath;
	}

	@Override
	public String retreiveHistory(String absolutePath) throws RemoteException {

		String history = null;
		try {
			history = new String(Files.readAllBytes(Paths.get(absolutePath)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// can print any error
		}
		return history;
	}

	@Override
	public Boolean isUserExist(String clientName) {
		if (clients.containsKey(clientName)) {
			return true;
		} else {
			return false;
		}
	}
}