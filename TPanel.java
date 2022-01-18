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

	int intHP = 0;
	
	String[][] mapData = new String[484][5];
	
	//buffered images
	BufferedImage s;
	BufferedImage gr;
	BufferedImage w;
	BufferedImage bu;
	BufferedImage br;
	BufferedImage p;
	BufferedImage d;
	BufferedImage t;
		
	ArrayList<GameModel.Projectile1> projectiles = new ArrayList<GameModel.Projectile1>();
	ArrayList<GameModel.Terrain1> map = new ArrayList<GameModel.Terrain1>();

	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == timer){
			this.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0,0,660,660);
		
		for(int intCount = 0; intCount < 484; intCount++){
			if(mapData[intCount][2] == (null)){
				break; // change to try catch later
			}
			if(mapData[intCount][2].equals("water")){	
				g.drawImage(w, Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), this);
			}else if(mapData[intCount][2].equals("dummy")){
				g.drawImage(d, Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), this);
			}else if(mapData[intCount][2].equals("tree")){
				g.drawImage(t, Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), this);
			}else if(mapData[intCount][2].equals("statue")){
				g.drawImage(s, Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), null);
			}else if(mapData[intCount][2].equals("building")){
				g.drawImage(bu, Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), this);
			}else if(mapData[intCount][2].equals("grass")){
				g.drawImage(gr, Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), this);
			}else if(mapData[intCount][2].equals("path")){
				g.drawImage(p, Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), this);
			}else if(mapData[intCount][2].equals("bridge")){
				g.drawImage(br, Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), this);
			}else if(mapData[intCount][2].equals("statuex")){
				//terrain
			}
		}
		
		g.setColor(Color.RED);
		for(int intCount = projectiles.size() -1; intCount >= 0; intCount--){
			g.fillRect(projectiles.get(intCount).intX, projectiles.get(intCount).intY, projectiles.get(intCount).intSize, projectiles.get(intCount).intSize); 			
		}
		for(int intCount = map.size() -1; intCount >= 0; intCount--){
			g.fillRect(map.get(intCount).intX, map.get(intCount).intY, map.get(intCount).intSizeX, map.get(intCount).intSizeY); 
		}
		
		g.setColor(Color.WHITE);
		g.fillRect(660, 0, 620, 720);
		g.fillRect(0, 660, 660, 60);
		g.setColor(Color.GRAY); //Character 
		g.fillRect(intX, intY, intSizeX, intSizeY);
		
		g.setColor(Color.RED);
		g.drawString("HP: "+intHP, 750, 50);
	}
	
	//constructor
	public TPanel(){		
		super();
		this.setLayout(null);
		timer.start();
		
		//loading images of map 1 
		try{
			s = ImageIO.read(new File("statue.png"));
			gr = ImageIO.read(new File("grass.png"));
			w = ImageIO.read(new File("water.png"));
			bu = ImageIO.read(new File("building.png"));
			br = ImageIO.read(new File("bridge.png"));
			p = ImageIO.read(new File("path.png"));
			d = ImageIO.read(new File("dummy.png"));
			t = ImageIO.read(new File("tree.png"));
			
		}catch(IOException e){
			System.out.println("Unable to load file.");
		}
	}
		
}
