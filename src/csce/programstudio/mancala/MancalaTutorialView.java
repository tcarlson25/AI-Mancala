package csce.programstudio.mancala;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MancalaTutorialView extends JPanel{

	private JButton continueButton;
	private JButton returnToMenuButton;
	private JLabel tutorialTextLabel;
	private JLabel tutorialText2Label;
	private JLabel backgroundImage;
	
	public MancalaTutorialView() {
		
		setLayout(null);
		
		continueButton = new JButton("Continue");
		continueButton.setBounds(442, 465, 120, 20);
		continueButton.setFont(new Font("American Typewriter", Font.BOLD, 16));
		continueButton.setVisible(true);
		continueButton.setOpaque(false);
		continueButton.setContentAreaFilled(false);
		continueButton.setBorderPainted(true);
		
		returnToMenuButton = new JButton("Menu");
		returnToMenuButton.setBounds(740, 465, 80, 20);
		returnToMenuButton.setFont(new Font("American Typewriter", Font.BOLD, 16));
		returnToMenuButton.setVisible(true);
		returnToMenuButton.setOpaque(false);
		returnToMenuButton.setContentAreaFilled(false);
		returnToMenuButton.setBorderPainted(false);
		
		tutorialTextLabel = new JLabel("Welcome to the Mancala Tutorial!");
		tutorialTextLabel.setBounds(400, 250, 300, 20);
		tutorialTextLabel.setForeground(Color.BLACK);
		tutorialTextLabel.setFont(new Font("American Typewriter", Font.BOLD, 17));
		tutorialTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		tutorialText2Label = new JLabel("Press \"Continue\" to go through the Tutorial!");
		tutorialText2Label.setBounds(400, 270, 300, 20);
		tutorialText2Label.setForeground(Color.BLACK);
		tutorialText2Label.setFont(new Font("American Typewriter", Font.BOLD, 17));
		tutorialText2Label.setHorizontalAlignment(SwingConstants.CENTER);
		
		backgroundImage = new JLabel("");
		backgroundImage.setBounds(0, 0, 1000, 600);
		backgroundImage.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/tutorial1.png")));
		
		add(continueButton);
		add(returnToMenuButton);
		add(tutorialTextLabel);
		add(tutorialText2Label);
		add(backgroundImage);
		
	}
	
	public void addMancalaTutorialListeners(ActionListener actionListener) {
		continueButton.addActionListener(actionListener);
		returnToMenuButton.addActionListener(actionListener);
	}
	
	public JLabel getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackground(JLabel backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public JButton getContinueButton() {
		return continueButton;
	}

	public void setContinueButton(JButton continueButton) {
		this.continueButton = continueButton;
	}

	public JButton getReturnToMenuButton() {
		return returnToMenuButton;
	}

	public void setReturnToMenuButton(JButton returnToMenuButton) {
		this.returnToMenuButton = returnToMenuButton;
	}
	
	public JLabel getTutorialTextLabel() {
		return tutorialTextLabel;
	}

	public void setTutorialTextLabel(JLabel tutorialTextLabel) {
		this.tutorialTextLabel = tutorialTextLabel;
	}
	
	public JLabel getTutorialText2Label() {
		return tutorialText2Label;
	}

	public void setTutorialText2Label(JLabel tutorialText2Label) {
		this.tutorialText2Label = tutorialText2Label;
	}
	
}
