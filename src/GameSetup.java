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
import javax.swing.event.CaretEvent;


public class GameSetup extends JFrame{

	private JPanel namePanel;
	private JLabel nameLbl;
	private JTextField nameTxt;
	private JPanel durationPanel;
	private JLabel durationLbl;
	private JTextField durationTxt;
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
	

	/**
	 * Create the application.
	 */
	public GameSetup(GameEnvironment environment) {
		this.environment = environment;
		initialize();
	}
	
	
	private void updateShipDisplay() {
		
		Ship[] possibleShips = environment.getPossibleShips();
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
	
	
	private void updateGoButton() {
		
		boolean nameValid = environment.isValidName(nameTxt.getText());
		boolean durationValid = environment.isValidDuration(durationTxt.getText());
		
		
		
		if (nameValid && durationValid) {
			goBtn.setEnabled(true);
			goBtn.setToolTipText(null);
		} else {
			goBtn.setEnabled(false);
			String message = "";
			if (!nameValid)
				message = message + "Name must be between 3 and 15 chars. ";
			if (!durationValid) {
				message = message + "Duration must be between 20 and 50.";
			}
			goBtn.setToolTipText(message);
			
		}
		
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
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		namePanel = new JPanel();
		GridBagConstraints gbc_namePanel = new GridBagConstraints();
		gbc_namePanel.anchor = GridBagConstraints.WEST;
		gbc_namePanel.insets = new Insets(10, 10, 0, 10);
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
				updateGoButton();
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
		gbc_durationPanel.insets = new Insets(10, 10, 0, 10);
		gbc_durationPanel.fill = GridBagConstraints.BOTH;
		gbc_durationPanel.gridx = 0;
		gbc_durationPanel.gridy = 1;
		getContentPane().add(durationPanel, gbc_durationPanel);
		GridBagLayout gbl_durationPanel = new GridBagLayout();
		gbl_durationPanel.columnWidths = new int[]{114, 86, 0};
		gbl_durationPanel.rowHeights = new int[]{20, 0};
		gbl_durationPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_durationPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		durationPanel.setLayout(gbl_durationPanel);
		
		durationLbl = new JLabel("Game Duration [20-50]:");
		GridBagConstraints gbc_durationLbl = new GridBagConstraints();
		gbc_durationLbl.anchor = GridBagConstraints.WEST;
		gbc_durationLbl.insets = new Insets(0, 0, 0, 5);
		gbc_durationLbl.gridx = 0;
		gbc_durationLbl.gridy = 0;
		durationPanel.add(durationLbl, gbc_durationLbl);
		
		durationTxt = new JTextField();
		durationTxt.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				updateGoButton();
			}
		});
		GridBagConstraints gbc_durationTxt = new GridBagConstraints();
		gbc_durationTxt.anchor = GridBagConstraints.NORTHWEST;
		gbc_durationTxt.gridx = 1;
		gbc_durationTxt.gridy = 0;
		durationPanel.add(durationTxt, gbc_durationTxt);
		durationTxt.setColumns(10);
		
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
		
		
		goBtn = new JButton("Go!");
		goBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				environment.setName(nameTxt.getText());
				environment.setGameLength(Integer.parseInt(durationTxt.getText()));
				environment.setShip(selectedShip);
				
				environment.startGame();
				setVisible(false);
				//TODO: Probably should open the next window
				dispose();
				
				
			}
		});
		goBtn.setEnabled(false);
		updateGoButton();
		GridBagConstraints gbc_goBtn = new GridBagConstraints();
		gbc_goBtn.insets = new Insets(10, 10, 10, 10);
		gbc_goBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_goBtn.gridx = 1;
		gbc_goBtn.gridy = 3;
		getContentPane().add(goBtn, gbc_goBtn);
		
		ToolTipManager.sharedInstance().setInitialDelay(0);
		
	}
}
