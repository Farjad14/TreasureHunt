package org.csc301;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

public class Game {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TreasureHunt game = new TreasureHunt();
		loadGui(game);
	}
	
	private static JPanel newGrid(TreasureHunt game){
		JPanel panel = new JPanel(new GridLayout(game.islands.height, game.islands.width));
		GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(5,5,5,5);
		JLabel l;
		
		for (int i = 0; i < game.islands.height; i++) {
			for (int j = 0; j < game.islands.width; j++) {
				if (i == game.islands.boat.gridY && j == game.islands.boat.gridX){
					l = new JLabel(new ImageIcon(Game.class.getResource("img/LargeShip.png")), JLabel.CENTER);
					//l = new JLabel("B", JLabel.CENTER);
				}
				else if (i == game.islands.treasure.gridY && j == game.islands.treasure.gridX){
					l = new JLabel(new ImageIcon(Game.class.getResource("img/TreasureChest.png")), JLabel.CENTER);
					//l = new JLabel("T", JLabel.CENTER);
				}
				else if (game.islands.map[i][j].inPath){
					l = new JLabel("*", JLabel.CENTER);
				}
				else if (game.islands.map[i][j].walkable){
					l = new JLabel(new ImageIcon(Game.class.getResource("img/tex_Water.jpg")), JLabel.CENTER);
					//l = new JLabel(".", JLabel.CENTER);
				}
				else{
					l = new JLabel(new ImageIcon(Game.class.getResource("img/grass03.png")), JLabel.CENTER);
					//l = new JLabel("+", JLabel.CENTER);
				}
	            panel.add(l);
				
			}
			
		}
        return panel;
    }
	

	private static void loadGui(final TreasureHunt game) {
		
		final JFrame frame=new JFrame("Grid");
	    JPanel panel=(JPanel)frame.getContentPane();
	    panel.setLayout(new GridLayout());
	    
	    frame.add(newGrid(game));
		
	    JPanel nav= new JPanel(new GridLayout(3,1,1,1));
	    JButton nw = new JButton("NW");
	    nav.add(nw);
	    nw.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				game.islands.move("NW");
				frame.setVisible(false);
				loadGui(game);
			}
	    });
	    JButton n = new JButton("N");
	    nav.add(n);
	    n.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				game.islands.move("N");
				frame.setVisible(false);
				loadGui(game);
			}
	    });
	    JButton ne = new JButton("NE");
	    nav.add(ne);
	    ne.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				game.islands.move("NE");
				frame.setVisible(false);
				loadGui(game);
			}
	    });
	    JButton w = new JButton("W");
	    nav.add(w);
	    w.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				game.islands.move("W");
				frame.setVisible(false);
				loadGui(game);
			}
	    });
		nav.add(new JLabel(new ImageIcon(Game.class.getResource("img/windrose.png")), JLabel.CENTER));
		JButton e = new JButton("E");
	    nav.add(e);
	    e.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				game.islands.move("E");
				frame.setVisible(false);
				loadGui(game);
			}
	    });
	    JButton sw = new JButton("SW");
	    nav.add(sw);
	    sw.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				game.islands.move("SW");
				frame.setVisible(false);
				loadGui(game);
			}
	    });
		JButton s = new JButton("S");
	    nav.add(s);
	    s.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				game.islands.move("S");
				frame.setVisible(false);
				loadGui(game);
			}
	    });
	    JButton se = new JButton("SE");
	    nav.add(se);
	    se.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				game.islands.move("SE");
				frame.setVisible(false);
				loadGui(game);
			}
	    });
		
		frame.add(nav);
		frame.setSize(1920, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}

}
