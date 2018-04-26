import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ClientGUI extends JFrame{
	String fromServer;
    String fromUser;
    String serverResponse;
	private final JButton[] buttons;
	private String inputString;
	private String serverMessage;
	private PrintWriter out;
	private BufferedReader in;
	
	// main panel
	private final JPanel mainPanel;
	
	// button panel
	private final JPanel buttonPanel;
	private final JButton stopButton;
	private final JButton whoButton;
	private final JButton goAwayButton;
	private JButton responseButton;

	private final JButton yesButton;
	private final JButton noButton;
	
	
	// instructions panel
	private final JPanel instructionsPanel;
	private JLabel errorsLabel;
	private JLabel serverMessageLabel;
	private JLabel instructionsLabel;
	
	/**
	 * Socket is passed to the gui from the client so it can be closed with the click of a button
	 * @param socket
	 */
	
	public ClientGUI(Socket socket){
		super("Client");
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {}
		inputString = "";
		serverMessage = "";
		
		
		/**
		 *  initialize the main panel
		 */
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 1));
		
		/**
		 *  initialize the instructions panel
		 */
		
		instructionsPanel = new JPanel();
		instructionsPanel.setLayout(new GridLayout(3, 1));
		instructionsLabel = new JLabel("", SwingConstants.CENTER);
		instructionsPanel.add(instructionsLabel);
		errorsLabel = new JLabel("", SwingConstants.CENTER);
		instructionsPanel.add(errorsLabel);
		serverMessageLabel = new JLabel("No server message yet", SwingConstants.CENTER);
		instructionsPanel.add(serverMessageLabel);
		mainPanel.add(instructionsPanel);
		

		
		/**
		 *  initialize the button panel
		 */
		
		buttons = new JButton[6];
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		
		stopButton = new JButton("Stop");
		stopButton.setVisible(true);
		buttons[0] = stopButton;
		stopButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				
				/**
				 * simultaneously close the ClientGui and Socket
				 */
				
				try {
					out.close();
					in.close();
					socket.close();
					dispose();
				} catch (Exception e) {
					errorsLabel.setText(e.toString());
				}
		    }
		});
		
		whoButton = new JButton("Who's There?");
		whoButton.setVisible(true);
		buttons[1] = whoButton;
		whoButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				
				/**
				 * send "who's there" to  server
				 */
				
				inputString = "Who's There?";
				System.out.println(inputString);
				out.println(inputString);
				whoButton.setVisible(false);
				responseButton.setVisible(true);


								
		    }
		});
		
		responseButton = new JButton(serverMessage + " who?");
		responseButton.setVisible(false);
		buttons[2] = responseButton;
		responseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				
				/**
				 * send joke rebuttal to server
				**/

				inputString = fromServer + " who?";
				System.out.println(inputString);
				out.println(inputString);
				whoButton.setVisible(true);
				yesButton.setVisible(true);
				noButton.setVisible(true);
				responseButton.setVisible(false);
				whoButton.setVisible(false);
				goAwayButton.setVisible(false);
		    }
		});			
		
		goAwayButton = new JButton("Go Away!");
		goAwayButton.setVisible(true);
		buttons[3] = goAwayButton;
		goAwayButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				
				/**
				 * send "go away" to server
				**/ 
				
				inputString = "Go Away!";
				System.out.println(inputString);
				out.println(inputString);
		    }
		});	
		

		
		yesButton = new JButton("Yes");
		yesButton.setVisible(false);
		buttons[4] = yesButton;
		yesButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				
				/**
				 * send "Y" to server
				**/ 
				
				inputString = "y";
				System.out.println(inputString);
				out.println(inputString);
				whoButton.setVisible(true);
				goAwayButton.setVisible(true);
				yesButton.setVisible(false);
				noButton.setVisible(false);
				
		    }
		});	
		
		noButton = new JButton("No");
		noButton.setVisible(false);
		buttons[5] = noButton;
		noButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				
				/**
				 * send "N" to server
				**/ 
				
				inputString = "n";
				System.out.println(inputString);
				out.println(inputString);
		    }
		});			
		
		for (int count = 0; count < buttons.length; count++){
			buttonPanel.add(buttons[count]);
		}		
		add(buttonPanel, BorderLayout.SOUTH);
		
		add(mainPanel, BorderLayout.CENTER);
		try {
			this.start();
		} catch (Exception e){}
		
	}		
	
	public void start() throws Exception{
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(500, 400);
		this.setVisible(true);
	
			while ((fromServer = in.readLine()) != null) {
			    setServerMessageLabel("Server: " + fromServer);
			    serverResponse = fromServer;
			    if (fromServer.equals("Bye."))
			        break;
			}
	}


	public void setInstructionLabel(String str){
		this.instructionsLabel.setText(str);
	}
	
	public void setErrorsLabel(String str){
		this.errorsLabel.setText(str);
	}
	
	public void setServerMessageLabel(String str){
		this.serverMessageLabel.setText(str);
	}
	
}
