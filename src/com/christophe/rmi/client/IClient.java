package com.christophe.rmi.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote{
	void retrieveMessage(String message,Boolean isPrivate)  throws RemoteException;
}
