import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class GameController implements ActionListener, KeyListener, MouseListener{
	///properties
	Timer thetimer = new Timer(1000/60, this);
	
	GameModel.Character1 c1 = new GameModel().new Character1(); //accessing the Character object in the GameModel class
	
	
	///methods
	//methods for KeyListener
	
	public void actionPerformed(ActionEvent evt){
		
	}
	
	public void keyReleased(KeyEvent evt){
		c1.moveX(0); //after releasing A, D, character stops moving
		c1.moveY(0); //after releasing W, S, character stops moving
	} 
	public void keyPressed(KeyEvent evt){
		if(evt.getKeyChar() == 'w'){
			c1.moveY(-5);
			System.out.println("W");
		}else if(evt.getKeyChar() == 'a'){
			c1.moveX(-5);
			System.out.println("A");
		}else if(evt.getKeyChar() == 's'){
			c1.moveY(5);
			System.out.println("S");
		}else if(evt.getKeyChar() == 'd'){
			c1.moveX(5);
			System.out.println("D");
		}
	}
	public void keyTyped(KeyEvent evt){
		
	}
	//methods for MouseListener
	public void mouseExited(MouseEvent evt){
	
	}
	public void mouseClicked(MouseEvent evt){
	
	}
	public void mousePressed(MouseEvent evt){
		
	}
	public void mouseEntered(MouseEvent evt){
		
	}
	public void mouseReleased(MouseEvent evt){
	
	}
	
	///constructor ?
}
