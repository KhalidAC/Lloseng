// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient loginid)
  {
    System.out.println("Message received from: "  + msg + " from " + loginid);
    this.sendToAllClients(msg);
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }

  public void handleMessageFromServer(Object message) 
  {
    // sendToAllClients(msg);
    try
    {
      if (message.equals("#quit")){ // will quit the server if called
        System.out.println("You are now closing the server...");
        System.exit(1);
      }
      else if (message.equals("#stop")){ // closes the connection to the server client remains active
        stopListening();
      }
      else if (message.equals("#close")){ // closes the connection to all clients and stops listening.
        close();
      }
  
      else if (message.equals("#setport")){ //calls setPort ONLY if logged out
        if (isListening()){
          System.out.println("Server is already connected...");
        }else{
          // setPort(port);//need to include a port number but cant figure it out... should come from the console.
          
        }
        
      }
      else if (message.equals("#start")){ // will allow client to log in if not connected to server
        if (isListening()){
          System.out.println("Server is already connected...");
        }else{
          run();
        }
      }
      else if (message.equals("#getport")){ // displays current port
        System.out.println(getPort());
      }
      else{
      sendToAllClients(message);}
    }
    catch(IOException e)
    {
      String msg = "Command not understood";
      sendToAllClients(msg);
      System.exit(1);
    }
  }
  // public static void handleMessageFromServer2(Object msg) 
  // {
  //   handleMessageFromServer(msg);
  // }
//E5c
  public void clientConnected(ConnectionToClient client){
    System.out.println("New client has connected!"); // prints message when a client connects
  }

  public void clientDisconnected(ConnectionToClient client){
    System.out.println("A client has disconnected...");
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  // public void handleMessageFromClientUI(String message)
  // { 
  //   try
  //   {
  //     if (message.equals("#quit")){ // will quit the server if called
  //       System.out.println("You are now leaving the server...");
  //       System.exit(1);
  //     }
  //     else if (message.equals("#stop")){ // closes the connection to the server client remains active
  //       stopListening();
  //     }
  //     else if (message.equals("#close")){ // closes the connection to all clients and stops listening.
  //       close();
  //     }
  
  //     else if (message.equals("#setport")){ //calls setPort ONLY if logged out
  //       if (isListening()){
  //         System.out.println("Server is already connected...");
  //       }else{
  //         // setPort(port);//need to include a port number but cant figure it out... should come from the console.
          
  //       }
        
  //     }
  //     else if (message.equals("#start")){ // will allow client to log in if not connected to server
  //       if (isListening()){
  //         System.out.println("Server is already connected...");
  //       }else{
  //         run();
  //       }
  //     }
  //     else if (message.equals("#getport")){ // displays current port
  //       System.out.println(getPort());
  //     }
  //     else{
  //     handleMessageFromServer(message);}
  //   }
  //   catch(IOException e)
  //   {
  //     String msg = "Command not understood";
  //     sendToAllClients(msg);
  //     System.exit(1);
  //   }
  }
// }
//End of EchoServer class
