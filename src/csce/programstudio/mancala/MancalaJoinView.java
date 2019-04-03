package csce.programstudio.mancala;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MancalaJoinView extends JPanel{
	
	private JButton playAsPlayerButton;
	private JButton playAsAIButton;
	private JButton returnToMenuButton;
	private JLabel backgroundImage;
	private JTextField ipAddressInput;
	private JLabel ipAddressInputLabel;
	private JTextField portNumberInput;
	private JLabel portNumberInputLabel;
	private JLabel errorMessage;
	
	public MancalaJoinView() {
		
		setLayout(null);
		
		playAsPlayerButton = new JButton("Play as Player");
		playAsPlayerButton.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/tile1.png")));
		playAsPlayerButton.setFont(new Font("American Typewriter", Font.BOLD, 14));
		playAsPlayerButton.setForeground(Color.WHITE);
		playAsPlayerButton.setVerticalTextPosition(SwingConstants.CENTER);
		playAsPlayerButton.setHorizontalTextPosition(SwingConstants.CENTER);
		playAsPlayerButton.setBounds(525, 219, 133, 68);
		
		playAsAIButton = new JButton("Play as AI");
		playAsAIButton.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/tile1.png")));
		playAsAIButton.setFont(new Font("American Typewriter", Font.BOLD, 14));
		playAsAIButton.setForeground(Color.WHITE);
		playAsAIButton.setVerticalTextPosition(SwingConstants.CENTER);
		playAsAIButton.setHorizontalTextPosition(SwingConstants.CENTER);
		playAsAIButton.setBounds(325, 219, 133, 68);
		
		returnToMenuButton = new JButton("MENU");
		returnToMenuButton.setFont(new Font("American Typewriter", Font.BOLD, 16));
		returnToMenuButton.setForeground(Color.WHITE);
		returnToMenuButton.setVerticalTextPosition(SwingConstants.CENTER);
		returnToMenuButton.setHorizontalTextPosition(SwingConstants.CENTER);
		returnToMenuButton.setBounds(78, 78, 100, 50);
		returnToMenuButton.setOpaque(false);
		returnToMenuButton.setContentAreaFilled(false);
		returnToMenuButton.setBorderPainted(false);
		
		backgroundImage = new JLabel("");
		backgroundImage.setBounds(0, 0, 1000, 600);
		backgroundImage.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/MancalaMenuOfficial.png")));
		
		ipAddressInput = new JTextField(); 
		ipAddressInput.setHorizontalAlignment(SwingConstants.LEFT);
		ipAddressInput.setBounds(444, 321, 103, 26);
		ipAddressInput.setText("127.0.0.1");
		
		ipAddressInputLabel = new JLabel("IP Address");
		ipAddressInputLabel.setBounds(342, 327, 125, 16);
		ipAddressInputLabel.setFont(new Font("American Typewriter", Font.BOLD, 13));
		
		portNumberInput = new JTextField(); 
		portNumberInput.setHorizontalAlignment(SwingConstants.LEFT);
		portNumberInput.setBounds(444, 397, 103, 26);
		portNumberInput.setText("9090");
		
		portNumberInputLabel = new JLabel("Port Number");
		portNumberInputLabel.setBounds(337, 404, 125, 16);
		portNumberInputLabel.setFont(new Font("American Typewriter", Font.BOLD, 13));
		
		errorMessage = new JLabel("");
		errorMessage.setBounds(300, 437, 400, 105);
		errorMessage.setForeground(Color.WHITE);
		errorMessage.setFont(new Font("American Typewriter", Font.BOLD, 16));
		errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
		
		add(playAsPlayerButton);
		add(playAsAIButton);
		add(returnToMenuButton);
		add(ipAddressInput);
		add(ipAddressInputLabel);
		add(portNumberInput);
		add(portNumberInputLabel);
		add(errorMessage);
		add(backgroundImage);
	}
	
	public void addMancalaJoinListeners(ActionListener actionListener) {
		playAsPlayerButton.addActionListener(actionListener);
		playAsAIButton.addActionListener(actionListener);
	}
	
	public void addReturnToMenuListeners(ActionListener actionListener) {
		returnToMenuButton.addActionListener(actionListener);
	}
	
	public JLabel getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackground(JLabel backgroundImage) {
		this.backgroundImage = backgroundImage;
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
	
	public JTextField getipAddressInput() {
		return ipAddressInput;
	}

	public void setipAddressInput(JTextField ipAddressInput) {
		this.ipAddressInput = ipAddressInput;
	}

	public JLabel getipAddressInputLabel() {
		return ipAddressInputLabel;
	}

	public void setipAddressInputLabel(JLabel ipAddressInputLabel) {
		this.ipAddressInputLabel = ipAddressInputLabel;
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
	
	public JLabel getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(JLabel errorMessage) {
		this.errorMessage = errorMessage;
	}

	public JButton getReturnToMenuButton() {
		return returnToMenuButton;
	}

	public void setReturnToMenuButton(JButton returnToMenuButton) {
		this.returnToMenuButton = returnToMenuButton;
	}
}

/*
 * 
 * package csce.programstudio.mancala;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MancalaHomeView extends JPanel{

	private JButton playAsPlayerButton;
	private JButton onlineButton;
	private JLabel backgroundImage;
	
	public MancalaHomeView() {
		
		setLayout(null);
		
		localButton = new JButton("Local");
		localButton.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/tile1.png")));
		localButton.setFont(new Font("American Typewriter", Font.BOLD, 20));
		localButton.setForeground(Color.WHITE);
		localButton.setVerticalTextPosition(SwingConstants.CENTER);
		localButton.setHorizontalTextPosition(SwingConstants.CENTER);
		localButton.setBounds(525, 219, 133, 68);
		
		onlineButton = new JButton("Online");
		onlineButton.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/tile1.png")));
		onlineButton.setFont(new Font("American Typewriter", Font.BOLD, 20));
		onlineButton.setForeground(Color.WHITE);
		onlineButton.setVerticalTextPosition(SwingConstants.CENTER);
		onlineButton.setHorizontalTextPosition(SwingConstants.CENTER);
		onlineButton.setBounds(325, 219, 133, 68);
		
		backgroundImage = new JLabel("");
		backgroundImage.setBounds(0, 0, 1000, 600);
		backgroundImage.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/MancalaMenuOfficial.png")));
		
		add(localButton);
		add(onlineButton);
		add(backgroundImage);
		
	}
	
	public void addMancalaHomeListeners(ActionListener actionListener) {
		localButton.addActionListener(actionListener);
		onlineButton.addActionListener(actionListener);
	}
	
	public JLabel getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackground(JLabel backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public JButton getLocalButton() {
		return localButton;
	}

	public void setLocalButton(JButton localButton) {
		this.localButton = localButton;
	}

	public JButton getOnlineButton() {
		return onlineButton;
	}

	public void setOnlineButton(JButton onlineButton) {
		this.onlineButton = onlineButton;
	}
	
}

 * */
