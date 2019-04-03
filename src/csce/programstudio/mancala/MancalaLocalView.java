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

public class MancalaLocalView extends JPanel{

	private JButton pVpButton;
	private JButton pVaButton;
	private JButton aVaButton;
	private JButton returnToMenuButton;
	private JLabel mancalaTitle;
	private JSlider numHousesInput;
	private JSlider numSeedsInput;
	private JTextField timerInput;
	private JLabel numHousesInputLabel;
	private JLabel numSeedsInputLabel;
	private JLabel numHousesLabel;
	private JLabel numSeedsLabel;
	private JLabel timerInputLabel;
	private JRadioButton randomSeeds;
	private JLabel randomSeedsLabel;
	private JLabel titleImage;
	private JLabel errorMessage;
	private JLabel backgroundImage;
	
	public MancalaLocalView() {
		
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
		
		pVaButton = new JButton("Player vs AI");
		pVaButton.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/tile1.png")));
		pVaButton.setFont(new Font("American Typewriter", Font.BOLD, 12));
		pVaButton.setForeground(Color.WHITE);
		pVaButton.setVerticalTextPosition(SwingConstants.CENTER);
		pVaButton.setHorizontalTextPosition(SwingConstants.CENTER);
		pVaButton.setBounds(233, 219, 133, 68);
		
		pVpButton = new JButton("Player vs Player");
		pVpButton.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/tile1.png")));
		pVpButton.setFont(new Font("American Typewriter", Font.BOLD, 12));
		pVpButton.setForeground(Color.WHITE);
		pVpButton.setVerticalTextPosition(SwingConstants.CENTER);
		pVpButton.setHorizontalTextPosition(SwingConstants.CENTER);
		pVpButton.setBounds(423, 219, 133, 68);
		
		aVaButton = new JButton("AI vs AI");
		aVaButton.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/tile1.png")));
		aVaButton.setFont(new Font("American Typewriter", Font.BOLD, 12));
		aVaButton.setForeground(Color.WHITE);
		aVaButton.setVerticalTextPosition(SwingConstants.CENTER);
		aVaButton.setHorizontalTextPosition(SwingConstants.CENTER);
		aVaButton.setBounds(613, 219, 133, 68);
		
		returnToMenuButton = new JButton("MENU");
		returnToMenuButton.setFont(new Font("American Typewriter", Font.BOLD, 16));
		returnToMenuButton.setForeground(Color.WHITE);
		returnToMenuButton.setVerticalTextPosition(SwingConstants.CENTER);
		returnToMenuButton.setHorizontalTextPosition(SwingConstants.CENTER);
		returnToMenuButton.setBounds(78, 78, 100, 50);
		returnToMenuButton.setOpaque(false);
		returnToMenuButton.setContentAreaFilled(false);
		returnToMenuButton.setBorderPainted(false);
		
		randomSeeds = new JRadioButton();
		randomSeeds.setBounds(482, 399, 25, 25);
		randomSeeds.setBorderPainted(false);
		randomSeeds.setOpaque(false);
		
		randomSeedsLabel = new JLabel("Random Seed");
		randomSeedsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		randomSeedsLabel.setBounds(169, 285, 250, 250);
		randomSeedsLabel.setFont(new Font("American Typewriter", Font.BOLD, 13));
		
		timerInput = new JTextField(); 
		timerInput.setHorizontalAlignment(SwingConstants.LEFT);
		timerInput.setBounds(378, 437, 103, 26);
		timerInput.setText("0");
		
		timerInputLabel = new JLabel("Time Limit (ms)");
		timerInputLabel.setBounds(257, 442, 125, 16);
		timerInputLabel.setFont(new Font("American Typewriter", Font.BOLD, 12));
		
		errorMessage = new JLabel("");
		errorMessage.setBounds(280, 437, 375, 105);
		errorMessage.setForeground(Color.WHITE);
		errorMessage.setFont(new Font("American Typewriter", Font.BOLD, 16));
		errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
		
		backgroundImage = new JLabel("");
		backgroundImage.setBounds(0, 0, 1000, 600);
		backgroundImage.setIcon(new ImageIcon(MancalaView.class.getResource("/csce/programstudio/mancala/resources/MancalaMenuOfficial.png")));
		
		add(pVpButton);
		add(pVaButton);
		add(aVaButton);
		add(returnToMenuButton);
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
		add(errorMessage);
		add(backgroundImage);
		
	}
	
	public void addMancalaLocalListeners(ActionListener actionListener) {
		pVpButton.addActionListener(actionListener);
		pVaButton.addActionListener(actionListener);
		aVaButton.addActionListener(actionListener);
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

	public JButton getpVpButton() {
		return pVpButton;
	}

	public void setpVpButton(JButton pVpButton) {
		this.pVpButton = pVpButton;
	}

	public JButton getpVaButton() {
		return pVaButton;
	}

	public void setpVaButton(JButton pVaButton) {
		this.pVaButton = pVaButton;
	}

	public JButton getaVaButton() {
		return aVaButton;
	}

	public void setaVaButton(JButton aVaButton) {
		this.aVaButton = aVaButton;
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
