package gui;
import javax.swing.JFrame;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.border.Border;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.CaretListener;

import ships.Ship;
import main.GameEnvironment;

import javax.swing.event.CaretEvent;
import javax.swing.JSlider;
import java.awt.BorderLayout;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


public class GameSetup extends JFrame{

	private JPanel namePanel;
	private JLabel nameLbl;
	private JTextField nameTxt;
	private JPanel durationPanel;
	private JLabel durationLbl;
	private JPanel shipPanel;
	private ButtonGroup shipButtons;
	private JRadioButton bargeBtn;
	private JRadioButton clipperBtn;
	private JRadioButton warShipBtn;
	private JRadioButton tallShipBtn;
	private JLabel shipLbl;
	private JButton goBtn;
	private JTextPane shipDisplay;
	
	private Ship selectedShip;
	
	
	private GameEnvironment environment;
	private JPanel errorPanel;
	private JLabel errorLbl;
	private JPanel sliderPanel;
	private JSlider durationSlider;
	

	/**
	 * Create the application.
	 */
	public GameSetup(GameEnvironment environment) {
		this.environment = environment;
		initialize();
	}
	
	/**
	 * Updates the text box describing the player's chosen ship
	 */
	private void updateShipDisplay() {
		Ship[] possibleShips = GameEnvironment.getPossibleShips();
		selectedShip = possibleShips[0];
		
		JRadioButton[] buttonList = {tallShipBtn, warShipBtn, clipperBtn, bargeBtn};
		
		for (int i = 0; i < buttonList.length; i++) {
			if (buttonList[i].isSelected()) {
				selectedShip = possibleShips[i];
				break;
			}
		}
		
		shipDisplay.setText("Name: "+ selectedShip.getShipType() + "\n" +
							selectedShip.getDescription());
	}
	
	
	/**
	 * Updates the error label, which tells the user why they can't start yet,<br>
	 * as well as greying out the Go! button
	 */
	private void updateGoButtonAndErrorLabel() {
		String name = nameTxt.getText();
		boolean nameValid = environment.isValidName(name);
		String message = "";
		if (nameValid) {
			goBtn.setEnabled(true);
		} else {
			goBtn.setEnabled(false);
			if (!nameValid)
				if (name.length() > 0 && !name.matches("[a-zA-Z ]"))
					message += "Name must contain letters and spaces only<br>";
				else
					message += "Name must be between 3 and 15 chars.<br>";
		}
		
		message = "<html>" + message + "</html>";
		errorLbl.setText(message);
	}
	
	/**
	 * Updates the game duration label with the slider's value
	 */
	private void updateDuration() {
		int duration = durationSlider.getValue();
		durationLbl.setText("Game Duration: "+duration);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Game Setup");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		namePanel = new JPanel();
		GridBagConstraints gbc_namePanel = new GridBagConstraints();
		gbc_namePanel.anchor = GridBagConstraints.WEST;
		gbc_namePanel.insets = new Insets(10, 10, 5, 10);
		gbc_namePanel.fill = GridBagConstraints.BOTH;
		gbc_namePanel.gridx = 0;
		gbc_namePanel.gridy = 0;
		getContentPane().add(namePanel, gbc_namePanel);
		GridBagLayout gbl_namePanel = new GridBagLayout();
		gbl_namePanel.columnWidths = new int[]{31, 86, 0};
		gbl_namePanel.rowHeights = new int[]{20, 0};
		gbl_namePanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_namePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		namePanel.setLayout(gbl_namePanel);
		
		nameLbl = new JLabel("Name:");
		nameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_nameLbl = new GridBagConstraints();
		gbc_nameLbl.anchor = GridBagConstraints.WEST;
		gbc_nameLbl.insets = new Insets(0, 0, 0, 5);
		gbc_nameLbl.gridx = 0;
		gbc_nameLbl.gridy = 0;
		namePanel.add(nameLbl, gbc_nameLbl);
		
		nameTxt = new JTextField();
		nameTxt.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				updateGoButtonAndErrorLabel();
			}
		});
		
		GridBagConstraints gbc_nameTxt = new GridBagConstraints();
		gbc_nameTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTxt.anchor = GridBagConstraints.NORTH;
		gbc_nameTxt.gridx = 1;
		gbc_nameTxt.gridy = 0;
		namePanel.add(nameTxt, gbc_nameTxt);
		nameTxt.setColumns(10);
		
		
		
		durationPanel = new JPanel();
		GridBagConstraints gbc_durationPanel = new GridBagConstraints();
		gbc_durationPanel.anchor = GridBagConstraints.WEST;
		gbc_durationPanel.insets = new Insets(10, 10, 5, 10);
		gbc_durationPanel.fill = GridBagConstraints.BOTH;
		gbc_durationPanel.gridx = 0;
		gbc_durationPanel.gridy = 1;
		getContentPane().add(durationPanel, gbc_durationPanel);
		GridBagLayout gbl_durationPanel = new GridBagLayout();
		gbl_durationPanel.columnWidths = new int[]{103, 100, 0};
		gbl_durationPanel.rowHeights = new int[]{20, 0};
		gbl_durationPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_durationPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		durationPanel.setLayout(gbl_durationPanel);
		
		durationLbl = new JLabel("Game Duration:");
		GridBagConstraints gbc_durationLbl = new GridBagConstraints();
		gbc_durationLbl.anchor = GridBagConstraints.WEST;
		gbc_durationLbl.insets = new Insets(0, 0, 0, 5);
		gbc_durationLbl.gridx = 0;
		gbc_durationLbl.gridy = 0;
		durationPanel.add(durationLbl, gbc_durationLbl);
		
		sliderPanel = new JPanel();
		GridBagConstraints gbc_sliderPanel = new GridBagConstraints();
		gbc_sliderPanel.fill = GridBagConstraints.BOTH;
		gbc_sliderPanel.gridx = 1;
		gbc_sliderPanel.gridy = 0;
		durationPanel.add(sliderPanel, gbc_sliderPanel);
		sliderPanel.setLayout(new BorderLayout(0, 0));
		
		durationSlider = new JSlider();
		durationSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateDuration();
			}
		});
		durationSlider.setPaintTicks(true);
		durationSlider.setPaintLabels(true);
		durationSlider.setMinorTickSpacing(5);
		durationSlider.setMinimum(20);
		durationSlider.setMaximum(50);
		durationSlider.setMajorTickSpacing(15);
		sliderPanel.add(durationSlider);
		
		shipPanel = new JPanel();
		GridBagConstraints gbc_shipPanel = new GridBagConstraints();
		gbc_shipPanel.anchor = GridBagConstraints.WEST;
		gbc_shipPanel.insets = new Insets(10, 10, 10, 10);
		gbc_shipPanel.fill = GridBagConstraints.VERTICAL;
		gbc_shipPanel.gridx = 0;
		gbc_shipPanel.gridy = 2;
		getContentPane().add(shipPanel, gbc_shipPanel);
		shipPanel.setLayout(new GridLayout(5, 1, 0, 0));
		
		shipLbl = new JLabel("Ship:");
		shipPanel.add(shipLbl);
		
		shipButtons = new ButtonGroup();
		
		tallShipBtn = new JRadioButton("Tall Ship");
		
		tallShipBtn.setSelected(true);
		shipButtons.add(tallShipBtn);
		shipPanel.add(tallShipBtn);
		
		warShipBtn = new JRadioButton("War Ship");
		shipButtons.add(warShipBtn);
		shipPanel.add(warShipBtn);
		
		clipperBtn = new JRadioButton("Clipper");
		shipButtons.add(clipperBtn);
		shipPanel.add(clipperBtn);
		
		bargeBtn = new JRadioButton("Barge");
		shipButtons.add(bargeBtn);
		shipPanel.add(bargeBtn);
		
		JRadioButton[] buttonList = {tallShipBtn, warShipBtn, clipperBtn, bargeBtn};
		
		for (JRadioButton button: buttonList) {
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateShipDisplay();
				}
			});
		}
		
		
		shipDisplay = new JTextPane();
		Border border = BorderFactory.createLineBorder(Color.BLACK);
	    shipDisplay.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		shipDisplay.setEditable(false);
		updateShipDisplay();
		GridBagConstraints gbc_shipDisplay = new GridBagConstraints();
		gbc_shipDisplay.gridheight = 3;
		gbc_shipDisplay.insets = new Insets(10, 10, 10, 10);
		gbc_shipDisplay.fill = GridBagConstraints.BOTH;
		gbc_shipDisplay.gridx = 1;
		gbc_shipDisplay.gridy = 0;
		getContentPane().add(shipDisplay, gbc_shipDisplay);
		
		
		GridBagConstraints gbc_goBtn = new GridBagConstraints();
		gbc_goBtn.insets = new Insets(10, 10, 10, 10);
		gbc_goBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_goBtn.gridx = 1;
		gbc_goBtn.gridy = 3;
		
		goBtn = new JButton("Go!");
		goBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				environment.setName(nameTxt.getText());
				environment.setGameLength(durationSlider.getValue());
				environment.setShip(selectedShip);
				
				environment.startGame();
				setVisible(false);
				
				GUI.openNextFrame();
				
				dispose();
			}
		});
		
		errorPanel = new JPanel();
		GridBagConstraints gbc_errorPanel = new GridBagConstraints();
		gbc_errorPanel.insets = new Insets(0, 0, 5, 5);
		gbc_errorPanel.fill = GridBagConstraints.BOTH;
		gbc_errorPanel.gridx = 0;
		gbc_errorPanel.gridy = 3;
		getContentPane().add(errorPanel, gbc_errorPanel);
		errorPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		errorLbl = new JLabel("Message<br>Message");
		errorLbl.setHorizontalAlignment(SwingConstants.CENTER);
		errorLbl.setForeground(Color.RED);
		errorPanel.add(errorLbl);
		getContentPane().add(goBtn, gbc_goBtn);
		goBtn.setEnabled(false);
		updateGoButtonAndErrorLabel();
		
		ToolTipManager.sharedInstance().setInitialDelay(0);
		
	}
}
