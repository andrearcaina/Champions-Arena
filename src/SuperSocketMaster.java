import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.AWTEventMulticaster;
import javax.swing.Timer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.NetworkInterface;
import java.net.Inet4Address;
import java.net.SocketException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.Enumeration;

/**
 * <h1>SuperSocketMaster</h1>
 * This class gives Java students the ability to quickly open a Java network socket<p>
 * They can then send outgoing text over the socket<p>
 * They can recieve incoming text over the socket<p>
 * Incoming text triggers an ActionEvent<p>
 * This class is meant to be used in Java Swing/AWT programs
 * @author  Alfred Cadawas
 * @version 2.0
 * @since   2016-04-21 
 */
public class SuperSocketMaster{
  // Properties
  private int intPort = 1337;
  private String strServerIP = null;
  private String strIncomingText = null;
  private SocketConnection soccon = null;
  transient ActionListener actionListener = null;
  // Methods
  /**
   * Sends text over the open socket<p>
   * If the socket is not open, the text goes nowhere<p>
   * You can use this to check to see of the socket is still open
   * 
   * @param strText Text you want to send over the network socket
   * @return Returns true if data was successfully sent over the network
   */
  public boolean sendText(String strText){
    if(soccon != null){
      return soccon.sendText(strText);
    }
    return false;
  }
  /**
   * Reads the text that was recieved from the open socket<p>
   * Should only be called after and ActionEvent is triggered
   * 
   * @return Returns recieved text from the socket or an empty string
   */
  public String readText(){
    if(soccon != null){
      return strIncomingText; 
    }else{
      return "";
    }
  }
  /**
   * Disconnects all open sockets<p>
   * Servers will disconnect all clients before closing the server socket<p>
   * Clients will disconnect their socket connection to the server
   */
  public void disconnect(){
    if(soccon != null){
      soccon.closeConnection();
      soccon = null;
    }
  }
  /**
   * Gets the IP Address of the computer<p>
   * Should grab the Ethernet IP first if both Wifi and Ethernet are connected
   * 
   * @return Returns computer's IP address
   */
  public String getMyAddress(){
    try {
      Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
      while (networkInterfaces.hasMoreElements()) {
        NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
        Enumeration<InetAddress> niAddresses = networkInterface.getInetAddresses();
        while(niAddresses.hasMoreElements()) {
          InetAddress inetAddress = (InetAddress) niAddresses.nextElement();
          if (!inetAddress.isLinkLocalAddress() && !inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
            return inetAddress.getHostAddress();
          }
        }
      }
    } catch (SocketException e) {
      
    }
    return "127.0.0.1";
  }
  /**
   * Gets the Hostname of the computer
   * 
   * @return Returns computer's Hostname
   */
  public String getMyHostname(){
    try{
      return InetAddress.getLocalHost().getHostName();
    }catch(UnknownHostException e){
      return "localhost";
    }
  }
  /**
   * Opens a socket connection<p>
   * Server - Opens a server socket and waits for clients to connect<p>
   * Client - Opens a socket and connects to the server<p>
   * Once connected, both start a thread to listen for incoming clients or incoming text
   * 
   * @return Returns true if socket connection was successfull
   */
  public boolean connect(){
    // First check to see if you can make a socket connection
    soccon = new SocketConnection(strServerIP, intPort, this);
    if(soccon.openConnection()){
      return true;
    }else{
      soccon = null;
      return false;
    }
  }
  private synchronized void addActionListener(ActionListener listener) {
    actionListener = AWTEventMulticaster.add(actionListener, listener);
  }
  private synchronized void removeActionListener(ActionListener listener) {
    actionListener = AWTEventMulticaster.remove(actionListener, listener);
  }
  private void postActionEvent() {
    // when event occurs which causes "action" semantic
    ActionListener listener = actionListener;
    if (listener != null) {
      listener.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"Network Message"));
    }
  }
  // Constructor
  /**
   * Server Mode SuperSocketMaster Constructor<p>
   * 
   * @param intPort TCP Port you want to use for your connection
   * @param listener Swing/AWT program's ActionListener.  Usually "this"
   */
  public SuperSocketMaster(int intPort, ActionListener listener){
    this.addActionListener(listener);
    this.intPort = intPort;
  }
  /**
   * Client Mode SuperSocketMaster Constructor<p>
   * 
   * @param strServerIP Hostname or IP address of the server you want to connect to
   * @param intPort TCP Port you want to use for your connection
   * @param listener Swing/AWT program's ActionListener.  Usually "this"
   */
  public SuperSocketMaster(String strServerIP, int intPort, ActionListener listener){
    this.addActionListener(listener);
    this.intPort = intPort;
    this.strServerIP = strServerIP;
  }
  
  /********************************************************************
    * SocketConnection Class
    * Creates a socket connection in either server mode or client
    * Server opens a server socket and listens for incomming connections
    * If a connection is made, make an appropriate client object
    * and starts listening for data
    * Client opens a socket and starts listening for data
    * *****************************************************************/
  
  private class SocketConnection implements Runnable, ActionListener{
    SuperSocketMaster parentssm = null;
    int intPort = 1337;
    String strServerIP = null;
    String strIncomingText = "";
    ServerSocket serverSocketObject = null;
    Socket socketObject = null;
    PrintWriter outBuffer = null;
    BufferedReader inBuffer = null;
    String strMyIP;
    String strMyHostname;
    Vector<ClientConnection> clientconnections = new Vector<ClientConnection>();
    boolean blnListenForClients = true;
    
    Timer theTimer;
    public void actionPerformed(ActionEvent evt){
      if(evt.getSource() == theTimer){
        //System.out.println("Heartbeat");
        this.sendText("Heartbeat");
      }
    }
    
    public boolean sendText(String strText) {
      if (strServerIP == null || strServerIP.equals("")) {
        // Server mode sending text needs to send to all clients
        // It therefore goes through the vector
        // and uses each object's sendText method.
        ////System.out.println("Sending message to all "+portconnections.size()+" clients: "+strText);
        for (int intCounter = 0; intCounter < clientconnections.size(); intCounter++) {
          clientconnections.get(intCounter).sendText(strText);
        }
        // restarting Heartbeat after last message that was sent.
        // No need to send heartbeat if there are lots of network messages being sent
        theTimer.restart();
        return true;
      } else {
        // Client mode is much easier.
        ////System.out.println("Sending message: "+strText);
        // First check if connecion is down
        if(socketObject != null){
          if(outBuffer.checkError()){
            closeConnection();
            return false;
          }else{
            outBuffer.println(strText); 
            // restarting Heartbeat after last message that was sent.
            // No need to send heartbeat if there are lots of network messages being sent
            theTimer.restart();
            return true;
          }
          
        }
        return false;
      }
    } 
    // This might be called buy two areas simultaneously!
    // Might be called by the disconnecting while loop in the run method
    // Might be called by the disconnect method.
    public void removeClient(ClientConnection clientConnection){
      if(clientConnection.socketObject != null){
        System.out.println("Trying to close server connection to client");
        try{
          // Since two methods might be running this code simultaneously
          // Some of the objects might be null
          // So catch the null pointer exception
          // the first method that accesses this should close everything correctly
          try{
            clientConnection.socketObject.shutdownInput();
            clientConnection.socketObject.shutdownOutput();
            clientConnection.socketObject.close();
            clientConnection.outBuffer.close();
            clientConnection.inBuffer.close();
            clientConnection.socketObject = null;
            clientConnection.inBuffer = null;
            clientConnection.outBuffer = null;
            clientConnection.strIncomingText = null;
            System.out.println("Done closing server connection to client");
            clientconnections.remove(clientConnection);
            clientConnection = null;
            System.out.println("Server removed a client connection.  Current Size: "+clientconnections.size());
          }catch(NullPointerException e){
          }
        }catch(IOException e){ 
        }
      }
    }
    public void run(){
      if(strServerIP == null || strServerIP.equals("")){
        // Server
        // while loop to listen for incoming clients
        // When a client connects, create a socket object
        // Add that socket object to a vector
        // Spawn a thread with that socket object
        // One thread for each client
        while (blnListenForClients) {
          try {
            socketObject = serverSocketObject.accept();
            ClientConnection singleconnection = new ClientConnection(this.parentssm, this.socketObject, this);
            clientconnections.addElement(singleconnection);
            Thread t1 = new Thread(singleconnection);
            t1.start();
            System.out.println("Server accepted a client connection:  Current Size: "+clientconnections.size());
          } catch (IOException e) {
            blnListenForClients = false;
          }
        }   
      }else{
        // Client 
        // Already connected to a server and have a socket object
        // while loop to listen for incoming data  
        while (strIncomingText != null) {
          try {
            strIncomingText = inBuffer.readLine();
            if(strIncomingText != null && !strIncomingText.equals("Heartbeat")){
              this.parentssm.strIncomingText = strIncomingText;
              this.parentssm.postActionEvent();
            }
          } catch (IOException e) {
          }        
        }
        System.out.println("reading while loop done");
        // CADAWAS
        closeConnection();
      }
    }
    public void closeConnection(){
      // If server, kill all client sockets then close the serversocket
      if(strServerIP == null || strServerIP.equals("")){
        blnListenForClients = false;
        while(clientconnections.size() > 0){
          removeClient(clientconnections.get(0));
          //System.out.println("Trying to remove all clients");
        }        
        try{
          serverSocketObject.close();
        }catch(IOException e){
        }
        serverSocketObject = null;
        clientconnections = null;
        theTimer.stop();
      }else{
        // If client, just kill the socket
        // This might be called buy two areas simultaneously!
        // Might be called by the disconnecting while loop in the run method
        // Might be called by the disconnect method.
        if(socketObject != null){
          System.out.println("Trying to close the client conneccion");
          try{
            // Since two methods might be running this code simultaneously
            // Some of the objects might be null
            // So catch the null pointer exception
            // the first method that accesses this should close everything correctly
            try{
              socketObject.shutdownInput();
              socketObject.shutdownOutput();
              socketObject.close();
              outBuffer.close();
              inBuffer.close();
              socketObject = null;
              inBuffer = null;
              outBuffer = null;
              strIncomingText = null;
              System.out.println("Done closing client connection");
            }catch(NullPointerException e){
            }
          }catch(IOException e){ 
          }
        }
        theTimer.stop();
      }
    }
    public boolean openConnection(){
      if(strServerIP == null || strServerIP.equals("")){
        // Server style connection.
        // Open Port
        // Create a serversocket object
        try {
          serverSocketObject = new ServerSocket(intPort); 
        } catch (IOException e) {
          return false;
        }
        Thread t1 = new Thread(this);
        t1.start();
        System.out.println("Server port opened.  Listening to incoming client connections");
        // Heartbeat start
        theTimer.start();
        return true;
      }else{
        // Client style connection.
        // Open port
        // Create a socket object        
        try {
          socketObject = new Socket(strServerIP, intPort);
          outBuffer = new PrintWriter(socketObject.getOutputStream(), true);
          inBuffer = new BufferedReader(new InputStreamReader(socketObject.getInputStream()));          
        } catch (IOException e) {
          return false;
        }
        Thread t1 = new Thread(this);
        t1.start();
        System.out.println("Client connected to server.  Listening for incoming data");
        // Heartbeat start
        theTimer.start();
        return true;
      }
    }
    public SocketConnection(String strServerIP, int intPort, SuperSocketMaster parentssm){
      this.strServerIP = strServerIP;
      this.intPort = intPort;
      this.parentssm = parentssm;
      // Heartbeat to verify if socket is connected
      // Shouldn't be real time heartbeat check/keepalive
      // Therefore will beat every 10 seconds
      // Anyone wanting to real time disconnect should use the disconnect method
      theTimer = new Timer(10000, this);
      
    }
  }
  private class ClientConnection implements Runnable{
    SuperSocketMaster parentssm = null;
    SocketConnection socketConnection = null;
    String strIncomingText = "";
    Socket socketObject = null;
    PrintWriter outBuffer = null;
    BufferedReader inBuffer = null; 
    public void run(){
      try {
        inBuffer = new BufferedReader(new InputStreamReader(socketObject.getInputStream()));
        outBuffer = new PrintWriter(socketObject.getOutputStream(), true);
      } catch (IOException e) {
      }
      while (strIncomingText != null) {
        try {
          strIncomingText = inBuffer.readLine();
          if(strIncomingText != null && !strIncomingText.equals("Heartbeat")){
            // Send to all other clients except for this recieving one
            for (int intCounter = 0; intCounter < socketConnection.clientconnections.size(); intCounter++) {
              if(socketConnection.clientconnections.get(intCounter) != this){
                socketConnection.clientconnections.get(intCounter).sendText(strIncomingText);
              }
            }
            // Then set text
            this.parentssm.strIncomingText = strIncomingText;
            this.parentssm.postActionEvent();
          }
        } catch (IOException e) {
        }         
      }
      System.out.println("reading while loop done");
      socketConnection.removeClient(this);
    }
    public boolean sendText(String strText) {
      if(outBuffer.checkError()){    
        socketConnection.removeClient(this);  
        return false;
      }else{
        outBuffer.println(strText);
        return true;
      }
    }
    public ClientConnection(SuperSocketMaster parentssm, Socket socketObject, SocketConnection socketConnection){
      this.socketConnection = socketConnection;
      this.socketObject = socketObject;
      this.parentssm = parentssm;  
    } 
  }
}