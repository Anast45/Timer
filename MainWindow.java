package MainWindow;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainWindow extends JFrame implements ActionListener{
		//JLabel userN = new JLabel("username :");
		JLabel timeL = new JLabel("Δώσε ώρα που θα ήθελες να απενεργοποιηθεί ο υπολογιστής");
		JLabel timeHL = new JLabel("Ώρα");
		JLabel timeML = new JLabel("Λεπτά");
		
		//TextField
		JTextField timerH = new JTextField(2);
		JTextField timerM = new JTextField(2);
		
		//Button = "Κουμπιά"
		JButton confirm = new JButton("Επιβεβαίωση");
		JButton cb = new JButton("Αναίρεση");
		JButton exit = new JButton("Exit");
		
		//Panel = "Ο Πίνακας : λευκός καμβας που μπαινουν τα αντικειμενα"
		JPanel obj =new JPanel();
				
		//Flow = "Ροή Κειμένου Δεξιά-Αριστερά"
		FlowLayout flow =new FlowLayout(FlowLayout.LEFT);
		
	public MainWindow(){
		
		cb.addActionListener(this);
		cb.setBackground(Color.ORANGE);
		confirm.addActionListener(this);
		confirm.setBackground(Color.ORANGE);
		exit.addActionListener(this);
		exit.setBackground(Color.ORANGE);
		
		obj.setBackground(Color.LIGHT_GRAY);
		//obj.setForeground(Color.LIGHT_GRAY);
		obj.add(timeL);
		obj.add(Box.createHorizontalStrut(1000000));
		obj.add(timeHL);
		obj.add(timerH);
		obj.add(timeML);
		obj.add(timerM);
		obj.add(confirm);
		obj.add(Box.createHorizontalStrut(1000000));
		obj.add(cb);
		obj.add(exit);
		
		//The area where components are inserted
		setContentPane(obj);
	
	}
	
	public void actionPerformed(ActionEvent evt) {
		Object source=evt.getSource();
		if(source == exit )
			exitForm();
		else if(source == confirm) {
				String answer = "Λεπτά για να κλείσει ο Η/Υ σας  "+timerH.getText()+" : "+timerM.getText();
				
				try {
					shutdown(timerH.getText(),timerM.getText());
				} 
				catch (RuntimeException | IOException e) {
					e.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(null,answer,"Application", JOptionPane.WARNING_MESSAGE);
			}
		else if(source == cb) {
			try {
				antishutdown();
			} catch (RuntimeException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null,"Your timer has been canceled","Application", JOptionPane.WARNING_MESSAGE);
			}
		}
	
	private void exitForm() {
		dispose();
	}
	
	public void shutdown(String H , String M) throws RuntimeException, IOException {
	    String shutdownCommand;
	    int K=Integer.parseInt(H);
	    int J=Integer.parseInt(M);
	    K=K*3600+J*60;
	    
	    shutdownCommand = "shutdown.exe -s -t "+K;
	    Runtime.getRuntime().exec(shutdownCommand);
	    //System.exit(0);
	}
	
	public static void antishutdown() throws RuntimeException, IOException {
	    String shutdownCommand;
	    shutdownCommand = "shutdown.exe -a";
	    
	    Runtime.getRuntime().exec(shutdownCommand);
	    //System.exit(0);
	}
	
	    public static void main(String[] args) {
		MainWindow form= new MainWindow();
		
		form.setSize(500,200);
		form.setTitle("Timer-Διακόπτης");
		form.setDefaultCloseOperation(EXIT_ON_CLOSE);
		form.setLocation(670, 500);
		
		form.show();
	}

}

