import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class GPanel extends JPanel implements ActionListener{
	///properties
	Timer timer = new Timer(1000/60, this);
	JButton Return = new JButton("Main Menu");
	
	BufferedImage champion1;
	BufferedImage champion2;
	BufferedImage champion3;
	BufferedImage champion4;
	
	int intX = 0;
	int intY = 0;
	int intSizeX = 40;
	int intSizeY = 40;
	int intSkillTime = 0;

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
	BufferedImage lives2;
	BufferedImage lives3;
	BufferedImage lives4;
		
	ArrayList<GameModel.Projectile1> projectiles = new ArrayList<GameModel.Projectile1>();
	ArrayList<GameModel.Terrain1> map = new ArrayList<GameModel.Terrain1>();
	ArrayList<GameModel.Character1> characters = new ArrayList<GameModel.Character1>();

	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == timer){
			this.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
	
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
			}else if(mapData[intCount][2].equals("volcanox")){
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
			if(projectiles.get(intCount).intID == 5){
				g.setColor(Color.RED);
			}else if(projectiles.get(intCount).intID == 6){
				g.setColor(Color.BLUE);
			}else if(projectiles.get(intCount).intID == 7){
				g.setColor(Color.GREEN);
			}else if(projectiles.get(intCount).intID == 8){
				g.setColor(Color.YELLOW);
			}else if(projectiles.get(intCount).intID == 9){
				g.setColor(Color.PINK);
			}
			g.fillRect(projectiles.get(intCount).intX, projectiles.get(intCount).intY, projectiles.get(intCount).intSize, projectiles.get(intCount).intSize); 			
		}
		
		for(int intCount = characters.size() -1; intCount >= 0; intCount--){
			if(characters.get(intCount).intID == 5){
				g.setColor(Color.RED);
			}else if(characters.get(intCount).intID == 6){
				g.setColor(Color.BLUE);
			}else if(characters.get(intCount).intID == 7){
				g.setColor(Color.GREEN);
			}else if(characters.get(intCount).intID == 8){
				g.setColor(Color.YELLOW);
			}else if(characters.get(intCount).intID == 9){
				g.setColor(Color.PINK);
			}
			g.fillRect(characters.get(intCount).intX, characters.get(intCount).intY+15, characters.get(intCount).intSizeX, characters.get(intCount).intSizeY-15); 			
			if(characters.get(intCount).intCharType == 1){
				g.drawImage(flamel, characters.get(intCount).intX, characters.get(intCount).intY, null);
			}else if(characters.get(intCount).intCharType == 2){
				g.drawImage(bishop, characters.get(intCount).intX, characters.get(intCount).intY, null);
			}else if(characters.get(intCount).intCharType == 3){
				g.drawImage(magnus, characters.get(intCount).intX, characters.get(intCount).intY, null);
			}else if(characters.get(intCount).intCharType == 4){
				g.drawImage(shadow, characters.get(intCount).intX, characters.get(intCount).intY, null);
			}
			 
		}
				
		g.setColor(Color.GRAY);
		g.fillRect(660, 0, 620, 720);
		g.fillRect(0, 660, 660, 60);
		
		for(int intCount = characters.size() -1; intCount >= 0; intCount--){
			if(characters.get(intCount).intID == 5){
				
				/*
				g.setFont(new Font("OCR A Extended", Font.BOLD, 18));
				g.setColor(Color.BLACK);
				g.drawString(strUsername, 770, 5);
				
				user1.setBounds(770, 5, 220, 30);
				user2.setBounds(770, 195, 220, 30);
				user3.setBounds(770, 390, 220, 30);
				user4.setBounds(770, 580, 220, 30);
				*/
				
				g.setColor(Color.BLACK);
				g.fillRect(779, 49, 202, 32);
				g.setColor(Color.RED);
				g.setFont(new Font("OCR A Extended", Font.BOLD, 18));
				g.drawString("HP: "+characters.get(intCount).intHP, 680, 75);
				g.fillRect(780, 50, characters.get(intCount).intHP*2, 30);
				
				g.setColor(Color.BLACK);
				g.fillRect(779, 89, 202, 32);
				g.setColor(new Color(139, 0, 0));
				g.drawString("Skill: "+characters.get(intCount).intSkillTime, 680, 105);
				g.fillRect(780, 90, characters.get(intCount).intSkillTime*2, 30);
				
				g.setColor(new Color(255, 204, 203));
				g.drawString("Lives: "+characters.get(intCount).intLives, 680, 145); 
				
				if(characters.get(intCount).intLives == 3){
					g.drawImage(lives1, 780, 130, null);
					g.drawImage(lives1, 800, 130, null);
					g.drawImage(lives1, 820, 130, null);	
				}else if(characters.get(intCount).intLives == 2){
					g.drawImage(lives1, 780, 130, null);
					g.drawImage(lives1, 800, 130, null);
				}else if(characters.get(intCount).intLives == 1){	
					g.drawImage(lives1, 780, 130, null);
				}
				
			}else if(characters.get(intCount).intID == 6){
				g.setColor(Color.BLACK);
				g.fillRect(779, 229, 202, 32);
				g.setColor(Color.BLUE);
				g.setFont(new Font("OCR A Extended", Font.BOLD, 18));
				g.drawString("HP: "+characters.get(intCount).intHP, 680, 245);
				g.fillRect(780, 230, characters.get(intCount).intHP*2, 30);
				
				g.setColor(Color.BLACK);
				g.fillRect(779, 269, 202, 32);		
				g.setColor(new Color(0, 0, 139));
				g.drawString("Skill: "+characters.get(intCount).intSkillTime, 680, 285);
				g.fillRect(780, 270, characters.get(intCount).intSkillTime*2, 30);

				g.setColor(new Color(173, 216, 230));
				g.drawString("Lives: "+characters.get(intCount).intLives, 680, 325);
				
				if(characters.get(intCount).intLives == 3){
					g.drawImage(lives2, 780, 310, null);
					g.drawImage(lives2, 800, 310, null);
					g.drawImage(lives2, 820, 310, null);	
				}else if(characters.get(intCount).intLives == 2){
					g.drawImage(lives2, 780, 310, null);
					g.drawImage(lives2, 800, 310, null);
				}else if(characters.get(intCount).intLives == 1){	
					g.drawImage(lives2, 780, 310, null);
				}			
				
			}else if(characters.get(intCount).intID == 7){
				g.setColor(Color.BLACK);
				g.fillRect(779, 399, 202, 32);
				g.setColor(Color.GREEN);
				g.setFont(new Font("OCR A Extended", Font.BOLD, 18));
				g.drawString("HP: "+characters.get(intCount).intHP, 680, 415);
				g.fillRect(780, 400, characters.get(intCount).intHP*2, 30);
				
				g.setColor(Color.BLACK);
				g.fillRect(779, 439, 202, 32);	
				g.setColor(new Color(0, 139, 0));
				g.drawString("Skill: "+characters.get(intCount).intSkillTime, 680, 455);
				g.fillRect(780, 440, characters.get(intCount).intSkillTime*2, 30);

				g.setColor(new Color(144, 238, 144));
				g.drawString("Lives: "+characters.get(intCount).intLives, 680, 495);
				
				if(characters.get(intCount).intLives == 3){
					g.drawImage(lives3, 780, 480, null);
					g.drawImage(lives3, 800, 480, null);
					g.drawImage(lives3, 820, 480, null);	
				}else if(characters.get(intCount).intLives == 2){
					g.drawImage(lives3, 780, 480, null);
					g.drawImage(lives3, 800, 480, null);
				}else if(characters.get(intCount).intLives == 1){	
					g.drawImage(lives3, 780, 480, null);
				}	
										
			}else if(characters.get(intCount).intID == 8){
				g.setColor(Color.BLACK);
				g.fillRect(779, 569, 202, 32);
				g.setColor(Color.YELLOW);
				g.setFont(new Font("OCR A Extended", Font.BOLD, 18));
				g.drawString("HP: "+characters.get(intCount).intHP, 680, 585);
				g.fillRect(780, 570, characters.get(intCount).intHP*2, 30);
				
				g.setColor(Color.BLACK);
				g.fillRect(779, 609, 202, 32);
				g.setColor(new Color(204, 204, 0));
				g.drawString("Skill: "+characters.get(intCount).intSkillTime, 680, 625);
				g.fillRect(780, 610, characters.get(intCount).intSkillTime*2, 30);

				g.setColor(new Color(255, 255, 102));
				g.drawString("Lives: "+characters.get(intCount).intLives, 680, 665);

				if(characters.get(intCount).intLives == 3){
					g.drawImage(lives4, 780, 650, null);
					g.drawImage(lives4, 800, 650, null);
					g.drawImage(lives4, 820, 650, null);	
				}else if(characters.get(intCount).intLives == 2){
					g.drawImage(lives4, 780, 650, null);
					g.drawImage(lives4, 800, 650, null);
				}else if(characters.get(intCount).intLives == 1){	
					g.drawImage(lives4, 780, 650, null);
				}	
			
			/* testing:
			}else if(characters.get(intCount).intID == 9){
				g.setColor(Color.BLACK);
				g.fillRect(779, 539, 202, 32);
				g.setColor(Color.PINK);
				g.setFont(new Font("OCR A Extended", Font.BOLD, 20));
				g.drawString("HP: "+characters.get(intCount).intHP, 680, 555);
				g.fillRect(780, 540, characters.get(intCount).intHP*2, 30);
			*/
			}	 
		}
	}
	
	//constructor
	public GPanel(){		
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
			lives2 = ImageIO.read(new File("lives2.png"));
			lives3 = ImageIO.read(new File("lives3.png"));
			lives4 = ImageIO.read(new File("lives4.png"));
			
			//accesses ttf file, creates it into a ttf font with java swing, and derives the font size using float
			Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, new File("font1.ttf")).deriveFont(30f);
			Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("font2.ttf")).deriveFont(40f);
			Font customFont3 = Font.createFont(Font.TRUETYPE_FONT, new File("font3.TTF")).deriveFont(55f);
			Font customFont4 = Font.createFont(Font.TRUETYPE_FONT, new File("font4.TTF")).deriveFont(60f);
		
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(FontFormatException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}
	}
		
}
		
