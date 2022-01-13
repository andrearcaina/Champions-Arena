import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class GPanel extends JPanel implements ActionListener{
	///properties
	Timer timer = new Timer(1000/60, this);
	
	GameModel.Character1 c1 = new GameModel().new Character1(); //accessing the Character object in the GameModel class
	
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == timer){
			this.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		g.setColor(new Color(144, 238, 144)); //light green
		g.fillRect(0, 0, 1280, 720);
		
		g.setColor(Color.GRAY); //Character 
		g.fillRect(c1.intX, c1.intY, c1.intSizeX, c1.intSizeY);		
	}
	
	public GPanel(){		
		super();
		this.setLayout(null);
		
		timer.start();
		
		//buffered images
	}
		
}
