import java.net.*;

public class KnockKnockClient implements Runnable{
	/**
	 * sets up a socket and the GUI
	 */
    public void run(){
		Socket kkSocket = null;
		int port = 4444;
        ClientGUI gui = null;
        String laptopName = "127.0.0.1";

        try {
            kkSocket = new Socket(laptopName, port);
            gui = new ClientGUI(kkSocket);
            gui.setInstructionLabel("Client listening on port " + port);
        } catch (UnknownHostException e) {
        	System.out.println("Don't know about host: " + laptopName + ".");
            System.exit(1);
        } catch (Exception e) {
        	System.out.println("Couldn't get I/O for the connection to: " + laptopName + ".");
            System.exit(1);
        }
	}
}