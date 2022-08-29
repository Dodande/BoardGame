package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import field.Player;
import graphics.GameToolbar;
import graphics.GraphicsPanel;
import backend.Control;
import backend.Gamer;
import backend.Turn;

public class Main {
	
	private static JFrame frame;
	
	private static GameToolbar gameToolbar;
	
	private static GraphicsPanel gameMapPanel;
	private static Turn turn = new Turn();
	private static GameBoard gameBoard = new GameBoard();
	private static Gamer firstGamer = new Gamer();
	private static Gamer secondGamer = new Gamer();
	
	
	public static GameToolbar getGameToolbar() {
		return gameToolbar;
	}
	
	public static GraphicsPanel getGameMapPanel() {
		return gameMapPanel;
	}
	
	public static void exit() {
		frame.dispose();
	}
	
	public static GameBoard getGameBoard() {
		return gameBoard;
	}
	
	private static void createGUI() {
		frame = new JFrame();
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic('g');
        JMenuItem newGame = new JMenuItem("New...");
        newGame.addActionListener(e -> newGame());
        newGame.setMnemonic('n');
        gameMenu.add(newGame);
        JMenuItem exitMenu = new JMenuItem("Exit");
        exitMenu.setMnemonic('x');
        exitMenu.addActionListener(e -> exit());
        gameMenu.add(exitMenu);
        menuBar.add(gameMenu);
        frame.setJMenuBar(menuBar);
        frame.setPreferredSize(new Dimension(600, 600));
        frame.setTitle("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
	}
	
	private static void newGame() {
		displayNewGame();
    	init();
	}

	private static void init() {
		Control theController = Control.getControl(gameToolbar, turn, firstGamer, secondGamer);

	}
	
	private static void displayNewGame() {
		if (gameMapPanel != null) frame.remove(gameMapPanel);
		if (gameToolbar != null) frame.remove(gameToolbar);
		frame.setPreferredSize(new Dimension(1900, 1000));
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.LINE_AXIS));
		gameToolbar = new GameToolbar();
		frame.add(gameToolbar);
		gameMapPanel = new GraphicsPanel();
		gameMapPanel.setPreferredSize(new Dimension(1600, 1000));
		gameMapPanel.setMinimumSize(gameMapPanel.getPreferredSize());
		gameMapPanel.setBackground(Color.decode("#40E66F"));
		frame.add(gameMapPanel);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    	frame.pack();
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(Main::createGUI);
	}
	
	public static void displayWinner(Player player) {
		JDialog winDialog = new JDialog(frame, "", true);
		winDialog.getContentPane().setBackground(Color.black);
		JLabel text = new JLabel(" WINS!");
		switch (player) {
			case First:
				text.setText("RED" + text.getText());
				text.setForeground(Color.RED);
				break;
			case Second:
				text.setText("BLUE" + text.getText());
				text.setForeground(Color.BLUE);
				break;
			default:
				return;
		}
		text.setFont(new Font(text.getFont().getName(), Font.PLAIN, 100));
		text.setAlignmentX(Component.CENTER_ALIGNMENT);
		text.setAlignmentY(Component.CENTER_ALIGNMENT);
		winDialog.getContentPane().add(text);
		winDialog.setPreferredSize(new Dimension(760, 240));
		winDialog.pack();
		winDialog.setVisible(true);
	}

}
