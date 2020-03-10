package com.christophe.rmi.client;

import java.awt.Color;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.christophe.rmi.server.IServer;

public class Client extends UnicastRemoteObject implements IClient, Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServer server;
	private String clientName = null;
	private Boolean isInPrivateChatMode=false;
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_RESET = "\u001B[0m";
	private static JTextField textfieldInputMessage;
	
	protected Client(String clientName, IServer server) throws RemoteException {
		this.clientName = clientName;
		this.server = server;
		server.registerClient(clientName,this);
	}

	@Override
	public void retrieveMessage(String message,Boolean isPrivate) throws RemoteException {
		if(isPrivate) {
			System.out.println(ANSI_BLUE + message + ANSI_RESET);
		}else {
			System.out.println(message);
			
		}
	
	}

	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		String message;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		String dir = System.getProperty("user.home");
		String fullPath = dir + "/WhatChatHistory/History.txt";
		System.out.println("To see your history enter the following command '/h'");
		//this.initMainChatRoomWindow();
		while(true) {
			message = scanner.nextLine();
			this.checkHistory(message, fullPath);
			try {
				if(message.charAt(0) != '/' && message.charAt(0) !='@') {
					server.writeToFile(dtf.format(now).toString() + " " + clientName + ":" + message);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				if(message.equals("@")){
				    this.sendPrivateMessage(scanner, dtf, now);
				}else {
				  if(message.charAt(0) != '/' && message.charAt(0) !='@') {
					  server.broadcastMessage(dtf.format(now).toString() + " " + clientName + ":" + message);
				  }
				
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
	}

	private void sendPrivateMessage(Scanner scanner, DateTimeFormatter dtf, LocalDateTime now) throws RemoteException {
		String message;
		System.out.println("Welcome to private chat section,enter the name of the user you want to chat with");
		message = scanner.nextLine();
		String receiver = message;
		this.setIsInPrivateChatMode(true);
		System.out.println("Private chat started with " + receiver);
		while(this.getIsInPrivateChatMode() == true) {
			message = scanner.nextLine();
			String messageSent = message;
			if(message.equals("/exit")) {
				System.out.println("Exiting private chatting...");
				this.setIsInPrivateChatMode(false);
			}else {
				if(server.isUserExist(receiver)) {
					server.privateMessage(receiver,dtf.format(now).toString() + " " + clientName + ":" + messageSent);
					server.writeToPrivFile(clientName,dtf.format(now).toString() + " " + clientName + ":" + messageSent);
				}else {
					System.out.println("User does not exist!,Exiting private chatting...");
					this.setIsInPrivateChatMode(false);
				}
			}
		}
	}

	private void checkHistory(String message, String fullPath) {
		String history = null;
		try {
			history = server.retreiveHistory(fullPath);
			if(history!=null && !history.isEmpty()) {
			 if(message.equals("/h")) {
				 System.out.println(history);
			 }
			}
			else{
				 if(message.equals("/h")){
					System.out.println("There is no previous messages");
				}
			 }
		} catch (RemoteException e2) {
			e2.printStackTrace();
		}
	}

	public Boolean getIsInPrivateChatMode() {
		return isInPrivateChatMode;
	}

	public void setIsInPrivateChatMode(Boolean isInPrivateChatMode) {
		this.isInPrivateChatMode = isInPrivateChatMode;
	}
	
/*	private  void initMainChatRoomWindow() {
		JFrame f = new JFrame("Welcome to the chat room");
		f.setSize(800, 800);
	    f.setLocation(300,200);
	    f.setResizable(false);
	    textfieldInputMessage = new JTextField("Type your message",10);
	    textfieldInputMessage.setBorder(new LineBorder(Color.BLACK, 10));
	    f.getContentPane().add(textfieldInputMessage);
	    f.pack();
	    f.setVisible(true);
	}*/

}
