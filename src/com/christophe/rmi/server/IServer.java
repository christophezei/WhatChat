package com.christophe.rmi.server;


import java.rmi.Remote;
import java.rmi.RemoteException;

import com.christophe.rmi.client.IClient;

public interface IServer extends Remote {
	void registerClient(String clientName,IClient client) throws RemoteException;
	void broadcastMessage(String message) throws RemoteException;
	void privateMessage(String clientName,String message) throws RemoteException;
	void writeToFile(String message)throws RemoteException;
	void writeToPrivFile(String clientName,String message)throws RemoteException;
	Boolean isUserExist(String clientName) throws RemoteException;
	String retreiveHistory(String absolutePath) throws RemoteException;
}
