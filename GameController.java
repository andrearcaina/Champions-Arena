import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class GameController implements ActionListener{
	///properties
	Timer theTimer = new Timer(1000/60, this);	

	///methods
	//methods for KeyListener
	
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theTimer){
			
		}
	}
	
	///constructor ?
	public GameController(){
		theTimer.start();
	}
}
