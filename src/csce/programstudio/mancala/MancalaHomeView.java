package csce.programstudio.mancala;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MancalaHomeView extends JPanel{

	private JButton localButton;
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
