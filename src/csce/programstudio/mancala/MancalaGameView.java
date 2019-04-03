package csce.programstudio.mancala;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class MancalaGameView extends JPanel{

	private int houses;
	private JPanel player1HousePanel;
	private JPanel player2HousePanel;
	private JPanel player1StorePanel;
	private JPanel player2StorePanel;
	private JPanel player1LabelPanel;
	private JPanel player2LabelPanel;
	private JLabel backgroundImage;
	private ArrayList<JPanel> player1SubPanels;
	private ArrayList<JPanel> player2SubPanels;
	private ArrayList<JButton> player1SubPanelButtons;
	private ArrayList<JButton> player2SubPanelButtons;
	private JButton player1Store;
	private JButton player2Store;
	private JLabel player1StoreLabel;
	private JLabel player2StoreLabel;
	private JLabel errorMessage;
	private JButton pieRule;
	private ArrayList<JLabel> player1HouseLabels;
	private ArrayList<JLabel> player2HouseLabels;
	private Timer timer;
	private JLabel timerLabel;
	private JButton returnToMenu;
	private JLabel currentPlayerLabel;
	
	public MancalaGameView(int numPlayerHouses) {
		
		setLayout(null);
		setHouses(numPlayerHouses);
		
		player1SubPanels = new ArrayList<JPanel>();
		player2SubPanels = new ArrayList<JPanel>();
		player1HouseLabels = new ArrayList<JLabel>();
		player2HouseLabels = new ArrayList<JLabel>();
		player1SubPanelButtons = new ArrayList<JButton>();
		player2SubPanelButtons = new ArrayList<JButton>();
		
		player1HousePanel = new JPanel();
		player1HousePanel.setBounds(150, 350, 700, 100);
		player1HousePanel.setLayout(new GridLayout(1, 0, 0, 0));
		player1HousePanel.setOpaque(false);
		
		player2HousePanel = new JPanel();
		player2HousePanel.setBounds(150, 125, 700, 100);
		player2HousePanel.setLayout(new GridLayout(1, 0, 0, 0));
		player2HousePanel.setOpaque(false);
		
		player1StorePanel = new JPanel();
		player1StorePanel.setBounds(850, 120, 125, 325);
		player1StorePanel.setLayout(null);
		player1StorePanel.setOpaque(false);
		
		player2StorePanel = new JPanel();
		player2StorePanel.setBounds(25, 120, 125, 325);
		player2StorePanel.setLayout(null);
		player2StorePanel.setOpaque(false);
		
		player1LabelPanel = new JPanel();
		player1LabelPanel.setBounds(150, 285, 700, 100);
		player1LabelPanel.setLayout(new GridLayout(1, numPlayerHouses, 0, 0));
		player1LabelPanel.setOpaque(false);
		
		player2LabelPanel = new JPanel();
		player2LabelPanel.setBounds(150, 190, 700, 100);
		player2LabelPanel.setLayout(new GridLayout(1, numPlayerHouses, 0, 0));
		player2LabelPanel.setOpaque(false);
		
		// add buttons to appropriate panels
		int totalComponents = 2*houses+2;
		for(int i = 0; i < totalComponents; ++i) {
			JPanel panel = new JPanel();
			panel.setLayout(null);
			panel.setOpaque(false);
			
			JButton button = new JButton();

			button.setOpaque(false);
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
			
			JLabel label = new JLabel();
			// stores have different initialization
			if (i == totalComponents/2-1 || i == totalComponents-1) {
				button.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/testStore.png")));
				button.setBounds((player1StorePanel.getWidth()/2)-(button.getIcon().getIconWidth()/2), 0, button.getIcon().getIconWidth(), button.getIcon().getIconHeight());
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setSize(50,50);
				label.setFont(new Font("American Typewriter", Font.BOLD, 18));
			} else {
				button.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/emptyHouse.png")));
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setFont(new Font("American Typewriter", Font.BOLD, 18));
			}
			// add button to player 1 house panel
			if(i < houses) {
			    button.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/emptyHouse.png")));
				int pixPerHouse = player1HousePanel.getWidth()/numPlayerHouses;
				button.setBounds((pixPerHouse/2)-(button.getIcon().getIconWidth()/2), 0, button.getIcon().getIconWidth(), button.getIcon().getIconHeight());
				panel.add(button);
				player1SubPanelButtons.add(button);
				player1HousePanel.add(panel);
				player1SubPanels.add(panel);
				player1LabelPanel.add(label);
				player1HouseLabels.add(label);
			// add button to player 1 store panel
			} else if(i < houses+1) {
				label.setBounds((button.getWidth()/2)-8, button.getHeight()-50, 25, 25);
				player1StorePanel.add(label);
				player1StorePanel.add(button);
				player1StoreLabel = label;
				player1Store = button;
			// add button to player 2 house panel
			} else if(i < totalComponents-1) {
				button.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/emptyHouse.png")));
				int pixPerHouse = player1HousePanel.getWidth()/numPlayerHouses;
				button.setBounds((pixPerHouse/2)-(button.getIcon().getIconWidth()/2), 0, button.getIcon().getIconWidth(), button.getIcon().getIconHeight());
				panel.add(button);
				player2SubPanelButtons.add(button);
				player2HousePanel.add(panel);
				player2SubPanels.add(panel);
				player2LabelPanel.add(label);
				player2HouseLabels.add(label);
			// add button to player 2 store panel
			} else {
				label.setBounds((button.getWidth()/2)-8, 25, 25, 25);
				player2StorePanel.add(label);
				player2StorePanel.add(button);
				player2StoreLabel = label;
				player2Store = button;
			}
		}
		
		errorMessage = new JLabel("");
		errorMessage.setBounds(325, 275, 350, 20);
		errorMessage.setForeground(Color.RED);
		errorMessage.setFont(new Font("American Typewriter", Font.ITALIC, 15));
		errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
		
		pieRule = new JButton("Click for Pie Rule");
		pieRule.setBounds(175, 275, 140, 30);
		pieRule.setVisible(false);
		pieRule.setOpaque(false);
		pieRule.setContentAreaFilled(false);
		
		timer = new Timer(10, null);
		
		timerLabel = new JLabel("");
		timerLabel.setBounds(325, 75, 350, 30);
		timerLabel.setFont(new Font("American Typewriter", Font.ITALIC, 20));
		timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		returnToMenu = new JButton("Menu");
		returnToMenu.setBounds(740, 465, 80, 20);
		returnToMenu.setVisible(true);
		returnToMenu.setOpaque(false);
		returnToMenu.setContentAreaFilled(false);
		returnToMenu.setBorderPainted(false);
		
		currentPlayerLabel = new JLabel("");
		currentPlayerLabel.setBounds(400, 460, 200, 20);
		currentPlayerLabel.setForeground(Color.BLACK);
		currentPlayerLabel.setFont(new Font("American Typewriter", Font.BOLD, 17));
		currentPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		backgroundImage = new JLabel("");
		backgroundImage.setBounds(0, 0, 1000, 600);
		backgroundImage.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/testBG4.png")));
	
		add(player1HousePanel);
		add(player2HousePanel);
		add(player1StorePanel);
		add(player2StorePanel);
		add(player1LabelPanel);
		add(player2LabelPanel);
		add(errorMessage);
		add(pieRule);
		add(timerLabel);
		add(returnToMenu);
		add(currentPlayerLabel);
		add(backgroundImage);
		
		
	}
	
	public void addMancalaHouseListeners(ActionListener actionListener) {
		for(int i = 0; i < player1SubPanelButtons.size(); ++i) {
			player1SubPanelButtons.get(i).addActionListener(actionListener);
			player2SubPanelButtons.get(i).addActionListener(actionListener);
		}
	}
	
	public void addPieRuleListener(ActionListener actionListener) {
		pieRule.addActionListener(actionListener);
	}
	
	public void addTimerListener(ActionListener actionListener) {
		timer.addActionListener(actionListener);
	}

	public int getHouses() {
		return houses;
	}

	public void setHouses(int houses) {
		this.houses = houses;
	}

	public JLabel getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(JLabel backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public JPanel getPlayer1HousePanel() {
		return player1HousePanel;
	}

	public void setPlayer1HousePanel(JPanel player1HousePanel) {
		this.player1HousePanel = player1HousePanel;
	}

	public JPanel getPlayer2HousePanel() {
		return player2HousePanel;
	}

	public void setPlayer2HousePanel(JPanel player2HousePanel) {
		this.player2HousePanel = player2HousePanel;
	}

	public JPanel getPlayer1StorePanel() {
		return player1StorePanel;
	}

	public void setPlayer1StorePanel(JPanel player1StorePanel) {
		this.player1StorePanel = player1StorePanel;
	}

	public JPanel getPlayer2StorePanel() {
		return player2StorePanel;
	}

	public void setPlayer2StorePanel(JPanel player2StorePanel) {
		this.player2StorePanel = player2StorePanel;
	}

	public JButton getPlayer1Store() {
		return player1Store;
	}

	public void setPlayer1Store(JButton player1Store) {
		this.player1Store = player1Store;
	}

	public JButton getPlayer2Store() {
		return player2Store;
	}

	public void setPlayer2Store(JButton player2Store) {
		this.player2Store = player2Store;
	}

	public JLabel getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage.setText(errorMessage);
	}

	public JButton getPieRule() {
		return pieRule;
	}

	public void setPieRule(JButton pieRule) {
		this.pieRule = pieRule;
	}

	public ArrayList<JPanel> getPlayer1SubPanels() {
		return player1SubPanels;
	}

	public void setPlayer1SubPanels(ArrayList<JPanel> player1SubPanels) {
		this.player1SubPanels = player1SubPanels;
	}

	public ArrayList<JPanel> getPlayer2SubPanels() {
		return player2SubPanels;
	}

	public void setPlayer2SubPanels(ArrayList<JPanel> player2SubPanels) {
		this.player2SubPanels = player2SubPanels;
	}

	public ArrayList<JButton> getPlayer1SubPanelButtons() {
		return player1SubPanelButtons;
	}

	public void setPlayer1SubPanelButtons(ArrayList<JButton> player1SubPanelButtons) {
		this.player1SubPanelButtons = player1SubPanelButtons;
	}

	public ArrayList<JButton> getPlayer2SubPanelButtons() {
		return player2SubPanelButtons;
	}

	public void setPlayer2SubPanelButtons(ArrayList<JButton> player2SubPanelButtons) {
		this.player2SubPanelButtons = player2SubPanelButtons;
	}

	public JPanel getPlayer1LabelPanel() {
		return player1LabelPanel;
	}

	public void setPlayer1LabelPanel(JPanel player1LabelPanel) {
		this.player1LabelPanel = player1LabelPanel;
	}

	public JPanel getPlayer2LabelPanel() {
		return player2LabelPanel;
	}

	public void setPlayer2LabelPanel(JPanel player2LabelPanel) {
		this.player2LabelPanel = player2LabelPanel;
	}

	public ArrayList<JLabel> getPlayer1HouseLabels() {
		return player1HouseLabels;
	}

	public void setPlayer1HouseLabels(ArrayList<JLabel> player1HouseLabels) {
		this.player1HouseLabels = player1HouseLabels;
	}

	public ArrayList<JLabel> getPlayer2HouseLabels() {
		return player2HouseLabels;
	}

	public void setPlayer2HouseLabels(ArrayList<JLabel> player2HouseLabels) {
		this.player2HouseLabels = player2HouseLabels;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public JLabel getTimerLabel() {
		return timerLabel;
	}

	public void setTimerLabel(JLabel timerLabel) {
		this.timerLabel = timerLabel;
	}
	
	public JButton getReturnToMenu() {
		return returnToMenu;
	}
	
	public JLabel getPlayer1StoreLabel() {
		return player1StoreLabel;
	}

	public void setPlayer1StoreLabel(JLabel player1StoreLabel) {
		this.player1StoreLabel = player1StoreLabel;
	}

	public JLabel getPlayer2StoreLabel() {
		return player2StoreLabel;
	}

	public void setPlayer2StoreLabel(JLabel player2StoreLabel) {
		this.player2StoreLabel = player2StoreLabel;
	}

	public void setReturnToMenu(JButton returnToMenu) {
		this.returnToMenu = returnToMenu;
	}
	
	public JLabel getCurrentPlayerLabel() {
		return currentPlayerLabel;
	}
	
	public void setCurrentPlayerLabel(String currentPlayerLabel) {
		this.currentPlayerLabel.setText(currentPlayerLabel);
	}


	public void addReturnToMenuListener(ActionListener actionListener) {
		returnToMenu.addActionListener(actionListener);
	}

	
}
