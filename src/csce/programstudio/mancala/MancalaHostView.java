package csce.programstudio.mancala;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class MancalaHostView extends JPanel{

	private JButton playAsAIButton;
	private JButton playAsPlayerButton;
	private JButton waitForClientButton;
	private JLabel mancalaTitle;
	private JSlider numHousesInput;
	private JSlider numSeedsInput;
	private JLabel numHousesInputLabel;
	private JLabel numSeedsInputLabel;
	private JLabel numHousesLabel;
	private JLabel numSeedsLabel;
	private JLabel timerInputLabel;
	private JTextField timerInput;
	private JLabel portNumberInputLabel;
	private JTextField portNumberInput;
	private JRadioButton randomSeeds;
	private JLabel randomSeedsLabel;
	private JLabel titleImage;
	private JButton returnToMenuButton;
	private JLabel errorMessage;
	private JLabel backgroundImage;
	
	public MancalaHostView() {
		
		setLayout(null);
		
		numSeedsInput = new JSlider();
		numSeedsInput.setBounds(445, 317, 103, 37);
		numSeedsInput.setOpaque(false);
		numSeedsInput.setMinimum(1);
		numSeedsInput.setMaximum(10);
		numSeedsInput.setValue(4);
		numSeedsInput.addChangeListener(new ChangeListener() {
	        @Override
	        public void stateChanged(ChangeEvent ce) {
	        		numSeedsInputLabel.setText(Integer.toString(((JSlider) ce.getSource()).getValue()));
	        }
	    });
		
		numSeedsInputLabel = new JLabel(Integer.toString(numSeedsInput.getValue()));
		numSeedsInputLabel.setBounds(610, 317, 125, 37);
		numSeedsInputLabel.setFont(new Font("American Typewriter", Font.BOLD, 15));
		
		numHousesInput = new JSlider();
		numHousesInput.setBounds(380, 355, 103, 37);
		numHousesInput.setOpaque(false);
		numHousesInput.setMinimum(4);
		numHousesInput.setMaximum(9);
		numHousesInput.setValue(6);
		numHousesInput.addChangeListener(new ChangeListener() {
	        @Override
	        public void stateChanged(ChangeEvent ce) {
	        		numHousesInputLabel.setText(Integer.toString(((JSlider) ce.getSource()).getValue()));
	        }
	    });
		
		numHousesInputLabel = new JLabel(Integer.toString(numHousesInput.getValue()));
		numHousesInputLabel.setBounds(550, 355, 125, 37);
		numHousesInputLabel.setFont(new Font("American Typewriter", Font.BOLD, 13));
		
		numSeedsLabel = new JLabel("Seeds/House");
		numSeedsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		numSeedsLabel.setBounds(295, 325, 120, 16);
		numSeedsLabel.setFont(new Font("American Typewriter", Font.BOLD, 13));
		
		numHousesLabel = new JLabel("Houses/Player");
		numHousesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		numHousesLabel.setBounds(237, 364, 120, 16);
		numHousesLabel.setFont(new Font("American Typewriter", Font.BOLD, 13));
		
		playAsAIButton = new JButton("Play as AI");
		playAsAIButton.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/tile1.png")));
		playAsAIButton.setFont(new Font("American Typewriter", Font.BOLD, 12));
		playAsAIButton.setForeground(Color.WHITE);
		playAsAIButton.setVerticalTextPosition(SwingConstants.CENTER);
		playAsAIButton.setHorizontalTextPosition(SwingConstants.CENTER);
		playAsAIButton.setBounds(233, 219, 133, 68);
		
		playAsPlayerButton = new JButton("Play as Player");
		playAsPlayerButton.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/tile1.png")));
		playAsPlayerButton.setFont(new Font("American Typewriter", Font.BOLD, 12));
		playAsPlayerButton.setForeground(Color.WHITE);
		playAsPlayerButton.setVerticalTextPosition(SwingConstants.CENTER);
		playAsPlayerButton.setHorizontalTextPosition(SwingConstants.CENTER);
		playAsPlayerButton.setBounds(423, 219, 133, 68);
		
		waitForClientButton = new JButton("Wait for Client");
		waitForClientButton.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/tile1.png")));
		waitForClientButton.setFont(new Font("American Typewriter", Font.BOLD, 12));
		waitForClientButton.setForeground(Color.WHITE);
		waitForClientButton.setVerticalTextPosition(SwingConstants.CENTER);
		waitForClientButton.setHorizontalTextPosition(SwingConstants.CENTER);
		waitForClientButton.setBounds(613, 219, 133, 68);
		
		randomSeeds = new JRadioButton();
		randomSeeds.setBounds(482, 399, 25, 25);
		randomSeeds.setBorderPainted(false);
		randomSeeds.setOpaque(false);
		
		randomSeedsLabel = new JLabel("Random Seed");
		randomSeedsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		randomSeedsLabel.setBounds(169, 285, 250, 250);
		randomSeedsLabel.setFont(new Font("American Typewriter", Font.BOLD, 13));
		
		timerInputLabel = new JLabel("Time Limit (ms)");
		timerInputLabel.setBounds(257, 442, 125, 16);
		timerInputLabel.setFont(new Font("American Typewriter", Font.BOLD, 12));
		
		timerInput = new JTextField(); 
		timerInput.setHorizontalAlignment(SwingConstants.LEFT);
		timerInput.setBounds(378, 437, 103, 26);
		timerInput.setText("0");
	
		portNumberInputLabel = new JLabel("Port Number");
		portNumberInputLabel.setBounds(327, 480, 125, 16);
		portNumberInputLabel.setFont(new Font("American Typewriter", Font.BOLD, 13));
		
		portNumberInput = new JTextField(); 
		portNumberInput.setHorizontalAlignment(SwingConstants.LEFT);
		portNumberInput.setBounds(440, 475, 103, 26);
		portNumberInput.setText("9090");
		
		returnToMenuButton = new JButton("MENU");
		returnToMenuButton.setFont(new Font("American Typewriter", Font.BOLD, 16));
		returnToMenuButton.setForeground(Color.WHITE);
		returnToMenuButton.setVerticalTextPosition(SwingConstants.CENTER);
		returnToMenuButton.setHorizontalTextPosition(SwingConstants.CENTER);
		returnToMenuButton.setBounds(78, 78, 100, 50);
		returnToMenuButton.setOpaque(false);
		returnToMenuButton.setContentAreaFilled(false);
		returnToMenuButton.setBorderPainted(false);
		
		errorMessage = new JLabel("");
		errorMessage.setBounds(475, 398, 400, 105);
		errorMessage.setForeground(Color.WHITE);
		errorMessage.setFont(new Font("American Typewriter", Font.BOLD, 15));
		errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
		
		backgroundImage = new JLabel("");
		backgroundImage.setBounds(0, 0, 1000, 600);
		backgroundImage.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/MancalaMenuOfficial.png")));
		
		add(playAsPlayerButton);
		add(playAsAIButton);
		add(waitForClientButton);
		add(numHousesInput);
		add(numHousesLabel);
		add(numHousesInputLabel);
		add(numSeedsInput);
		add(numSeedsLabel);
		add(numSeedsInputLabel);
		add(randomSeeds);
		add(randomSeedsLabel);
		add(timerInput);
		add(timerInputLabel);
		add(portNumberInputLabel);
		add(portNumberInput);
		add(returnToMenuButton);
		add(errorMessage);
		add(backgroundImage);
		
	}
	
	public void addMancalaHostListeners(ActionListener actionListener) {
		playAsPlayerButton.addActionListener(actionListener);
		playAsAIButton.addActionListener(actionListener);
		waitForClientButton.addActionListener(actionListener);
		returnToMenuButton.addActionListener(actionListener);
	}
	
	public void addReturnToMenuListeners(ActionListener actionListener) {
		returnToMenuButton.addActionListener(actionListener);
	}
	
	public JLabel getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(JLabel errorMessage) {
		this.errorMessage = errorMessage;
	}

	public JTextField getTimerInput() {
		return timerInput;
	}

	public void setTimerInput(JTextField timerInput) {
		this.timerInput = timerInput;
	}

	public JLabel getTimerInputLabel() {
		return timerInputLabel;
	}

	public void setTimerInputLabel(JLabel timerInputLabel) {
		this.timerInputLabel = timerInputLabel;
	}
	
	public JTextField getPortNumberInput() {
		return portNumberInput;
	}

	public void setPortNumberInput(JTextField portNumberInput) {
		this.portNumberInput = portNumberInput;
	}

	public JLabel getPortNumberInputLabel() {
		return portNumberInputLabel;
	}

	public void setPortNumberInputLabel(JLabel portNumberInputLabel) {
		this.portNumberInputLabel = portNumberInputLabel;
	}

	public JButton getPlayAsPlayerButton() {
		return playAsPlayerButton;
	}

	public void setPlayAsPlayerButton(JButton playAsPlayerButton) {
		this.playAsPlayerButton = playAsPlayerButton;
	}

	public JButton getPlayAsAIButton() {
		return playAsAIButton;
	}

	public void setPlayAsAIButton(JButton playAsAIButton) {
		this.playAsAIButton = playAsAIButton;
	}

	public JButton getWaitForClientButton() {
		return waitForClientButton;
	}

	public void setWaitForClientButton(JButton waitForClientButton) {
		this.waitForClientButton = waitForClientButton;
	}

	public JLabel getMancalaTitle() {
		return mancalaTitle;
	}

	public void setMancalaTitle(JLabel mancalaTitle) {
		this.mancalaTitle = mancalaTitle;
	}

	public JSlider getNumHousesInput() {
		return numHousesInput;
	}

	public void setNumHousesInput(JSlider numHousesInput) {
		this.numHousesInput = numHousesInput;
	}

	public JSlider getNumSeedsInput() {
		return numSeedsInput;
	}

	public void setNumSeedsInput(JSlider numSeedsInput) {
		this.numSeedsInput = numSeedsInput;
	}

	public JLabel getNumHousesLabel() {
		return numHousesLabel;
	}

	public void setNumHousesLabel(JLabel numHousesLabel) {
		this.numHousesLabel = numHousesLabel;
	}

	public JLabel getNumSeedsLabel() {
		return numSeedsLabel;
	}

	public void setNumSeedsLabel(JLabel numSeedsLabel) {
		this.numSeedsLabel = numSeedsLabel;
	}

	public JRadioButton getRandomSeeds() {
		return randomSeeds;
	}
	
	public JButton getReturnToMenuButton() {
		return returnToMenuButton;
	}
	
	public void setReturnToMenuButton(JButton returnToMenuButton) {
		this.returnToMenuButton = returnToMenuButton;
	}

	public JLabel getTitleImage() {
		return titleImage;
	}

	public void setTitleImage(JLabel titleImage) {
		this.titleImage = titleImage;
	}

	public JLabel getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackground(JLabel backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public void setRandomSeeds(JRadioButton randomSeeds) {
		this.randomSeeds = randomSeeds;
	}
}