package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import field.Field;
import field.Player;
import field.building.*;
import field.resource.*;
import field.unit.*;
import main.GameBoard;
import main.Main;
import resources.ImageLoader;

@SuppressWarnings("serial")
public class GraphicsPanel extends JPanel {
	
	private GameBoard gameBoard;
	
	private Field selected;
	
	public GraphicsPanel() {
		super();
		gameBoard = Main.getGameBoard();
		gameBoard.initializePlayground();
		addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                	try {
                		selected = gameBoard.getPlayground()[e.getY() / 40][e.getX() / 40];
                	} catch (Exception ex) {}
                	if (selected != null) {
                		Main.getGameToolbar().displaySelectedField(selected);
                	}
                	
                }

                @Override public void mouseReleased(MouseEvent e) {}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setBackground(Color.decode("#40E66F"));
		g2d.clearRect(0, 0, 1600, 1000);
		g2d.setColor(Color.black);
		for (int i = 0; i < 40; i++) {
			for (int j = 0; j < 25; j++) {
				g2d.drawImage(ImageLoader.load("fields/blackBorder.png"), i * 40, j * 40, this);
				Field f = gameBoard.getPlayground()[j][i];
				if (f instanceof Woods) g2d.drawImage(ImageLoader.load("forest.png"), i * 40 + 2, j * 40 + 2, this);
				if (f instanceof Mine) g2d.drawImage(ImageLoader.load("gold.png"), i * 40 + 2, j * 40 + 2, this);
				if (f instanceof Builder) g2d.drawImage(ImageLoader.load("Builder.png"), i * 40 + 6, j * 40 + 6, this);
				if (f instanceof Farmer) g2d.drawImage(ImageLoader.load("Farmer.png"), i * 40 + 6, j * 40 + 6, this);
				if (f instanceof Miner) g2d.drawImage(ImageLoader.load("Miner.png"), i * 40 + 6, j * 40 + 6, this);
				if (f instanceof Warrior) g2d.drawImage(ImageLoader.load("swordsman.png"), i * 40 + 6, j * 40 + 6, this);
				if (f instanceof Lumberjack) g2d.drawImage(ImageLoader.load("lumberjack.png"), i * 40 + 6, j * 40 + 6, this);
				if (f instanceof Farm) g2d.drawImage(ImageLoader.load("farm.png"), i * 40 + 3, j * 40 + 3, this);
				if (f instanceof Barrack) g2d.drawImage(ImageLoader.load("fort.png"), i * 40 + 3, j * 40 + 3, this);
				if (f instanceof MainBuilding) {
					g2d.drawImage(ImageLoader.load("castle.png"), i * 40 + 3, j * 40 + 4, this);
					if (((MainBuilding) f).getPlayer() == Player.First) g2d.setColor(Color.RED);
					else g2d.setColor(Color.BLUE);
					g2d.drawRect(i * 40, j * 40, 39, 39);
					g2d.drawRect(i * 40 + 1, j * 40 + 1, 37, 37);
				}
			}
		}
		g2d.dispose();
	}
	
	public void refresh() {
		setVisible(false);
		setVisible(true);
	}

}
