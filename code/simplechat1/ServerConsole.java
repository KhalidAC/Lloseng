import java.io.*;
import client.*;
import common.*;
import ocsf.server.AbstractServer;

public class ServerConsole implements ChatIF{

    //Class variables *************************************************
  
    ChatIF clientUI;

    ChatIF loginid;


    /*
    Variable to store login id

    */
    private String setInfo;
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  EchoServer server;
  
  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ServerConsole(int port) 
  {
    try 
    {
      EchoServer server = new EchoServer(port);
      server.listen();
      accept();  

    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.exit(1);
    }
  }
   //Instance methods ************************************************
  
/*
Method to return the loginid stored on the server

*/

final public String getInfo() {
    return setInfo;
}

  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the server's message handler.
   */
  public void accept() 
  { 
    try
    {
      BufferedReader fromConsole = 
        new BufferedReader(new InputStreamReader(System.in));
      String message;

      while (true) 
      {
        message = fromConsole.readLine();
       server.handleMessageFromServer(message);
        display(message);
        
      }
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
   
    }
    
  }


/**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
    server.sendToAllClients("SERVER MSG> " + message);
  }

  public static void main(String[] args) 
  {
    String host = "";
    int port = 0;  //The port number

    try
    {
      host = args[0];
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      host = "localhost";
    }
    //E5b
    try
    {
      port = Integer.parseInt(args[1]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }

    ServerConsole chat= new ServerConsole(port);

    chat.accept();  //Wait for console data
  }




}