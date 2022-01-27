/// TPanel - Tutorial Menu Panel
/// By: Andre Arcaina, Nicholas Hioe, Sean Kwee
/// ICS 4U1
/// Version 1.0
/// 2021-01-27

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
	
	// JComponents
	JButton Return = new JButton("Main Menu");
	JButton changeChamp = new JButton("Change Champion");
	JLabel promptUser = new JLabel("Press W, A, S, D to move.");
	
	// Character
	double dblX = 0;
	double dblY = 0;
	int intSizeX = 20;
	int intSizeY = 20;
	int intSkillTime = 0;
	int intLives = 3;
	int intCharType = 3; //tutorial character is defaulted to: MAGNUS
	int intHP = 0;
	
	// Dummy 
	int intDummyHP = 0;
	double intDummyX;
	double intDummyY;
	
	String[][] mapData = new String[484][5];
	
	// BufferedImages
	BufferedImage champion1;
	BufferedImage champion2;
	BufferedImage champion3;
	BufferedImage champion4;
	
	BufferedImage s;
	BufferedImage gr;
	BufferedImage w;
	BufferedImage bu;
	BufferedImage br;
	BufferedImage p;
	BufferedImage t;
	BufferedImage v;
	BufferedImage l;
	BufferedImage r;
	BufferedImage gp;
	BufferedImage bo;
	BufferedImage to;
	
	BufferedImage flamel;
	BufferedImage bishop;
	BufferedImage magnus;
	BufferedImage shadow;
	BufferedImage dummy;
	
	BufferedImage lives1;
	BufferedImage controls;
	
	Font customFont1;
	
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
				break;
			}
			if(mapData[intCount][2].equals("water")){	
				g.drawImage(w, Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), this);
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
				//terrain (look at loadMap method)
			}else if(mapData[intCount][2].equals("volcano")){
				g.drawImage(v, Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), null);
			}else if(mapData[intCount][2].equals("volcanot")){
				//terrain
			}else if(mapData[intCount][2].equals("lava")){
				g.drawImage(l, Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), this);
			}else if(mapData[intCount][2].equals("bones")){
				g.drawImage(bo, Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), this);
			}else if(mapData[intCount][2].equals("rock")){
				g.drawImage(r, Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), this);
			}else if(mapData[intCount][2].equals("grasspath")){
				g.drawImage(gp, Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), this);
			}else if(mapData[intCount][2].equals("torch")){
				g.drawImage(to, Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), this);
			}
		}
		
		g.drawImage(dummy, (int)intDummyX, (int)intDummyY, null);
		g.setColor(Color.RED);
		// Draw projectiles
		for(int intCount = projectiles.size() -1; intCount >= 0; intCount--){
			g.fillRect((int)projectiles.get(intCount).dblX, (int)projectiles.get(intCount).dblY, projectiles.get(intCount).intSize, projectiles.get(intCount).intSize); 			
		}
		g.setColor(Color.WHITE);
		g.fillRect(660, 0, 620, 720);
		g.fillRect(0, 660, 660, 60);
		
		g.setColor(Color.BLACK);
		g.fillRect(789, 9, 202, 32);
		g.setColor(Color.RED);
		g.setFont(customFont1);
		g.drawString("HP: "+intHP, 670, 35);
		g.fillRect(790, 10, intHP*2, 30);
		
		g.setColor(Color.BLACK);
		g.fillRect(789, 144, 202, 32);
		g.setColor(new Color(75, 0, 130));
		g.setFont(customFont1);
		g.drawString("DUMMY: "+intDummyHP, 670, 160);
		g.fillRect(790, 145, intDummyHP*2, 30);
		
		g.setColor(Color.BLACK);
		g.fillRect(789, 49, 202, 32);
		g.setColor(new Color(139, 0, 0));
		g.drawString("Skill: "+intSkillTime, 670, 65);
		g.fillRect(790, 50, intSkillTime*2, 30);
		
		g.setColor(new Color(255, 204, 203));
		g.drawString("Lives: "+intLives, 670, 105); 
		
		// Draw number of hearts correspodning to number of lives
		if(intLives == 3){
			g.drawImage(lives1, 780, 95, null);
			g.drawImage(lives1, 800, 95, null);
			g.drawImage(lives1, 820, 95, null);	
		}else if(intLives == 2){
			g.drawImage(lives1, 780, 95, null);
			g.drawImage(lives1, 800, 95, null);
		}else if(intLives == 1){	
			g.drawImage(lives1, 780, 95, null);
		}
		
		// Draw charcters corresponding to their charType (changes if they press button)
		if(intCharType == 1){
			g.drawImage(flamel, (int)dblX, (int)dblY, null);
		}else if(intCharType == 2){
			g.drawImage(bishop, (int)dblX, (int)dblY, null);
		}else if(intCharType == 3){
			g.drawImage(magnus, (int)dblX, (int)dblY, null);
		}else if(intCharType == 4){
			g.drawImage(shadow, (int)dblX, (int)dblY, null);
		}
		
		
		g.setColor(Color.BLACK);
		g.drawRect(670, 210, 580, 60);
		
		g.drawImage(controls, 670, 280, null);
	}
	
	//constructor
	public TPanel(){		
		super();
		this.setLayout(null);
		timer.start();
		
		//loading images of map 1 
		try{
			s = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("statue.png"));
			gr = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("grass.png"));
			w = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("water.PNG"));
			bu = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("building.png"));
			br = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("bridge.PNG"));
			p = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("path.png"));
			t = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("tree.png"));
			l = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("lava.png"));
			bo = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("bones.png"));
			v = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("volcano.png"));
			r = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("rock.png"));
			gp = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("grasspath.png"));
			to = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("torch.png"));
			
			flamel = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("ingame_flamel.png"));
			bishop = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("ingame_bishop.png"));
			magnus = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("ingame_magnus.png"));
			shadow = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("ingame_shadow.png"));
			dummy = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("dummy.png"));
			
			lives1 = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("lives1.png"));
			controls = ImageIO.read(TPanel.class.getClassLoader().getResourceAsStream("controls.png"));
			
			//accesses ttf file, creates it into a ttf font with java swing, and derives the font size using float
			customFont1 = Font.createFont(Font.TRUETYPE_FONT, TPanel.class.getClassLoader().getResourceAsStream("font1.ttf")).deriveFont(18f);
			Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, TPanel.class.getClassLoader().getResourceAsStream("font2.ttf")).deriveFont(22f);
			Font customFont3 = Font.createFont(Font.TRUETYPE_FONT, TPanel.class.getClassLoader().getResourceAsStream("font2.ttf")).deriveFont(50f);
			
			
			Return.setFont(customFont2);
			changeChamp.setFont(customFont2);
			promptUser.setFont(customFont3);
			
		}catch(FileNotFoundException e){
			System.out.println(e.toString());
		}catch(FontFormatException e){
			System.out.println(e.toString());
		}catch(IOException e){
			System.out.println(e.toString());
		}
		
		changeChamp.setBounds(1000, 10, 250, 70);
		Return.setBounds(1000, 105, 250, 70);
		promptUser.setBounds(720, 217, 580, 50);
		
		promptUser.setForeground(Color.BLACK);
		changeChamp.setBackground(Color.GRAY);
		Return.setBackground(Color.GRAY);
		changeChamp.setForeground(Color.WHITE);
		Return.setForeground(Color.WHITE);
		
		changeChamp.setHorizontalAlignment(SwingConstants.CENTER);
		Return.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.add(promptUser);
		this.add(Return);
		this.add(changeChamp);
	}
		
}
