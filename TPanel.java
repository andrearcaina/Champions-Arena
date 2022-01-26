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
	JButton Return = new JButton("Return to Help");
	JButton changeChamp = new JButton("Change Champion");
	JLabel promptUser = new JLabel("Press W, A, S, D to move.");

	BufferedImage champion1;
	BufferedImage champion2;
	BufferedImage champion3;
	BufferedImage champion4;
	
	//character
	int intX = 0;
	int intY = 0;
	int intSizeX = 40;
	int intSizeY = 40;
	int intSkillTime = 0;
	int intLives = 3;
	int intCharType = 3; //tutorial character is defaulted to: MAGNUS
	int intHP = 0;
	
	//dummy 
	int intDummyHP = 0;
	
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
	
	BufferedImage lives1;
	
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
		
		g.setColor(Color.RED);
		for(int intCount = projectiles.size() -1; intCount >= 0; intCount--){
			g.fillRect(projectiles.get(intCount).intX, projectiles.get(intCount).intY, projectiles.get(intCount).intSize, projectiles.get(intCount).intSize); 			
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
		
		if(intCharType == 1){
			g.drawImage(flamel, intX, intY, null);
		}else if(intCharType == 2){
			g.drawImage(bishop, intX, intY, null);
		}else if(intCharType == 3){
			g.drawImage(magnus, intX, intY, null);
		}else if(intCharType == 4){
			g.drawImage(shadow, intX, intY, null);
		}
		
		g.setColor(Color.BLACK);
		g.drawRect(670, 210, 580, 60);
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
			l = ImageIO.read(new File("lava.png"));
			bo = ImageIO.read(new File("bones.png"));
			v = ImageIO.read(new File("volcano.png"));
			r = ImageIO.read(new File("rock.png"));
			gp = ImageIO.read(new File("grasspath.png"));
			to = ImageIO.read(new File("torch.png"));
			
			flamel = ImageIO.read(new File("ingame_flamel.png"));
			bishop = ImageIO.read(new File("ingame_bishop.png"));
			magnus = ImageIO.read(new File("ingame_magnus.png"));
			shadow = ImageIO.read(new File("ingame_shadow.png"));
			
			lives1 = ImageIO.read(new File("lives1.png"));
			
			
			//accesses ttf file, creates it into a ttf font with java swing, and derives the font size using float
			customFont1 = Font.createFont(Font.TRUETYPE_FONT, new File("font1.ttf")).deriveFont(18f);
			Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("font2.TTF")).deriveFont(22f);
			Font customFont3 = Font.createFont(Font.TRUETYPE_FONT, new File("font2.TTF")).deriveFont(50f);
			
			
			Return.setFont(customFont2);
			changeChamp.setFont(customFont2);
			
			promptUser.setFont(customFont3);
			
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(FontFormatException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
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
