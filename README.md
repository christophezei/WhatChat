# Christophe El Zeinaty 
# WhatChat
Welcome to Whatchat, this a java console chatting application based on rmi technology.
It contains the following features : 
1)Global chat room<br/>
2)Private messaging<br/>
3)History of the global chat room<br/>
4)History of the private chat rooms<br/>
5)Chat persistency<br/>

# How to run the server ?
Simple navigate to the jar directory and run the following command:<br/>
   <b>java -jar server.jar </b>
   
# How to run the client ?
Simple navigate to the jar directory and run the following command:<br/>
   <b>java -jar client.jar  username </b>
   
# Design Choices 
The project is spearted between 2 main packages client and server:<br/>
<b>Server functions signatures:</b><br/>
   1)void registerClient(String clientName, IClient client) throws RemoteException;<br/>
   2)void broadcastMessage(String message) throws RemoteException;<br/>
   3)void privateMessage(String clientName, String message) throws RemoteException;<br/>
   4)void writeToFile(String message) throws RemoteException;<br/>
   5)void writeToPrivFile(String clientName, String message) throws RemoteException;<br/>
   6)Boolean isUserExist(String clientName) throws RemoteException;<br/>
   7)String retreiveHistory(String absolutePath) throws RemoteException;<br/>
<b>Main design choices for the server:</b><br/>
    1)Storing clients in a HashMap where the keys are the 'clientName'<br/>
    2)Storing the history in a text file where a directory is created in your 'home' dir on your pc, in this directory we will store public history of the global chat room under the textfile 'history.txt' and private messages between users under the filename 'history-sender-receiver.txt' (sender/receiver are passed dynamically) <br/>
    3)Synchronization is applied to the following methods 'registerClient' , 'privateMessage' , 'broadcastMessage' , 'writeToFile' , 'writeToPrivFile' using the java synchronized keyword<br/>
<b>Client functions signatures:</b><br/>
   1)void retrieveMessage(String message, Boolean isPrivate) throws RemoteException;<br/>
   <b>Main design choices for the client:</b><br/>
   1)Clients are created in form of threads
   
   # Useful commands you can use (also you can see them if you run /list in the client command line)
   1)To see your history enter the following command '/history'<br/>
   2)To chat a specific user privately enter the following command '@'<br/>
   3)To exit private chat enter the following command'/exit'(once you are in private chat mode if you run it in the global chat room application will terminate)<br/>
   
