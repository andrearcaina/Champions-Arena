import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class HPanel extends JPanel implements ActionListener{
	///properties
	Timer timer = new Timer(1000/60, this);
	
	JButton Return = new JButton("Main Menu");
	JButton Tutorial = new JButton("Tutorial");
	JLabel readInstruction = new JLabel("I have read the instructions");
	JCheckBox boxInstruction = new JCheckBox();
	
	BufferedImage HelpTitle;
	BufferedImage Logo;
	BufferedImage Instructions;
	
	///methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == timer){
			this.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1280, 720);
		g.drawImage(HelpTitle, 20, -40, null);
		// g.drawImage(Instructions, #, #, null );
		//g.drawImage(Logo, (some number), (some number), null);
	}
	///constructor
	public HPanel(){		
		super();
		this.setLayout(null);
		
		Return.setBounds(1000, 10, 150, 80);
		Tutorial.setBounds(800, 10, 150, 80);
		//readInstruction.setBounds();
		//boxInstruction.setBounds();
		
		Return.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(this.getClass().getResource("handCursor.png")).getImage(), new Point(0,0),"hand cursor"));
		Tutorial.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(this.getClass().getResource("handCursor.png")).getImage(), new Point(0,0),"hand cursor"));
		
		this.add(Return);
		this.add(Tutorial);
		this.add(readInstruction);
		this.add(boxInstruction);
		
		timer.start();
		
		//buffered images
		try{
			HelpTitle = ImageIO.read(HPanel.class.getClassLoader().getResourceAsStream("HelpTitle.png"));
			//Instructions = ImageIO.read(HPanel.class.getClassLoader().getResourceAsStream("Instructions.png"));
		}catch(IOException e){
			System.out.println(e.toString());
		}
	}
}
