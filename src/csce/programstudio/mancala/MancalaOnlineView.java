package csce.programstudio.mancala;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MancalaOnlineView extends JPanel{
	private JButton hostButton;
	private JButton joinButton;
	private JButton returnToMenuButton;
	private JLabel backgroundImage;
	
	public MancalaOnlineView() {
		setLayout(null);
		
		hostButton = new JButton("Host Game");
		hostButton.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/tile1.png")));
		hostButton.setFont(new Font("American Typewriter", Font.BOLD, 18));
		hostButton.setForeground(Color.WHITE);
		hostButton.setVerticalTextPosition(SwingConstants.CENTER);
		hostButton.setHorizontalTextPosition(SwingConstants.CENTER);
		hostButton.setBounds(325, 219, 133, 68);
		
		joinButton = new JButton("Join");
		joinButton.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/tile1.png")));
		joinButton.setFont(new Font("American Typewriter", Font.BOLD, 18));
		joinButton.setForeground(Color.WHITE);
		joinButton.setVerticalTextPosition(SwingConstants.CENTER);
		joinButton.setHorizontalTextPosition(SwingConstants.CENTER);
		joinButton.setBounds(525, 219, 133, 68);
		
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
		
		add(hostButton);
		add(joinButton);
		add(returnToMenuButton);
		add(backgroundImage);
	}
	
	public void addMancalaOnlineListeners(ActionListener actionListener) {
		hostButton.addActionListener(actionListener);
		joinButton.addActionListener(actionListener);
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

	public JButton getHostButton() {
		return hostButton;
	}

	public void setHostButton(JButton hostButton) {
		this.hostButton = hostButton;
	}

	public JButton getJoinButton() {
		return joinButton;
	}

	public void setJoinButton(JButton joinButton) {
		this.joinButton = joinButton;
	}
}
