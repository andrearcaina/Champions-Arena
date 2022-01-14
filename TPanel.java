import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class TPanel extends JPanel implements ActionListener{
	///properties
	Timer timer = new Timer(1000/60, this);
	int intX = 0;
	int intY = 0;
	int intSizeX = 40;
	int intSizeY = 40;
	ArrayList<GameModel.Projectile1> projectiles = new ArrayList<GameModel.Projectile1>();

	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == timer){
			this.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		g.setColor(new Color(144, 238, 144)); //light green
		g.fillRect(0, 0, 1280, 720);
		g.setColor(Color.GRAY); //Character 
		g.fillRect(intX, intY, intSizeX, intSizeY); 
		for(int intCount = projectiles.size() -1; intCount > 0; intCount--){
			g.fillRect(projectiles.get(intCount).intX, projectiles.get(intCount).intY, projectiles.get(intCount).intSize, projectiles.get(intCount).intSize); 			
		}
	}
	
	public TPanel(){		
		super();
		this.setLayout(null);
		timer.start();
		
		//buffered images
	}
		
}
