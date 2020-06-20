// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  ChatIF loginid;

  int chgport;

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String loginid,String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  { 
    try
    {
      if (message.equals("#quit")){ // will quit the server if called
        System.out.println("You are now leaving the server...");
        System.exit(1);
      }
      else if (message.equals("#logoff")){ // closes the connection to the server client remains active
        closeConnection();
      }
      else if (message.equals("#sethost" )){//calls setHost ONLY if logged out
        if (isConnected()){
          System.out.println("You are already connected...");
        }else{
          // setHost(); //need to include a host but cant figure it out... should come from input from the console.
          openConnection();

        }
      }
      else if (message.equals("#setport" + chgport)){ //calls setPort ONLY if logged out
        if (isConnected()){
          System.out.println("You are already connected...");
        }else{
          // setPort(port);//need to include a port number but cant figure it out... should come from the console.
          openConnection();
        }
        
      }
      else if (message.equals("#login" + loginid)){ // will allow client to log in if not connected to server
        if (isConnected()==true){
          System.out.println("You are already connected...");
        }else{
          openConnection();
        }
      }
      else if (message.equals("#gethost")){ // displays current host
        System.out.println(getHost());
        
      }
      else if (message.equals("#getport")){ // displays current port
        System.out.println(getPort());
      }
      else{
      sendToServer(message);}
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
/* Started to work on E5a through Teams Professor Lethrbridge mentioned it is not required to complete
*/
  // public void terminateClient(){
  //   if (isConnected() != true){
  //     System.out.println("The Server has closed terminating client...");
  //     quit();
  //   }
  // }
}
//End of ChatClient class
