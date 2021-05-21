package gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import islands.Island;
import islands.Route;
import ships.Ship;
import main.GameEnvironment;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Embark extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameEnvironment env = new GameEnvironment();
					env.setGameLength(20);
					env.setName("test");
					env.setShip(GameEnvironment.getPossibleShips()[0]);
					env.startGame();
					Embark frame = new Embark(env);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Embark(GameEnvironment environment) {
		setTitle("Where would you like to go?");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				environment.disembark();
				GUI.openNextFrame();
				dispose();
			}
		});
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 6, 0, 0));
		
		
		//Headings
		JLabel destLbl = new JLabel("Destination:");
		destLbl.setFont(new Font("Tahoma", Font.ITALIC, 11));
		panel.add(destLbl);
		
		JLabel timeLbl = new JLabel("Time:");
		timeLbl.setFont(new Font("Tahoma", Font.ITALIC, 11));
		panel.add(timeLbl);
		
		JLabel pRiskLbl = new JLabel("Pirate Risk");
		pRiskLbl.setFont(new Font("Tahoma", Font.ITALIC, 11));
		panel.add(pRiskLbl);
		
		JLabel wRiskLbl = new JLabel("Weather Risk");
		wRiskLbl.setFont(new Font("Tahoma", Font.ITALIC, 11));
		panel.add(wRiskLbl);
		
		JLabel rRiskLbl = new JLabel("Rescue Risk");
		rRiskLbl.setFont(new Font("Tahoma", Font.ITALIC, 11));
		panel.add(rRiskLbl);
		
		
		//Fills in the extra item at the end of the row for grid layout
		JPanel voidPanel = new JPanel();
		panel.add(voidPanel);
		
				
		Island currentIsland = environment.getCurrentIsland();
		float shipSpeed = environment.getShip().getSpeed();
		ArrayList<Route> validRoutes = Route.availableRoutes(environment.getAllRoutes(), currentIsland);
		for (Route route : validRoutes) {
			
			String name = route.getOtherIsland(currentIsland).getName();
			JLabel loopDestLbl = new JLabel(name);
			panel.add(loopDestLbl);
			
			
			int days = (int) (route.getDistance() / shipSpeed);
			JLabel loopTimeLbl = new JLabel(String.valueOf(days));
			panel.add(loopTimeLbl);
			
			
			String[] risks = route.getRisk();
			for (int j = 0; j < risks.length; j++) {
				JLabel riskLbl = new JLabel(risks[j]);
				panel.add(riskLbl);
			}
			
			
			
			JButton loopGoBtn = new JButton("Go!");
			
			Ship ship = environment.getShip();
			int wages = (int) (Ship.DAILY_WAGE * ship.getCrew() * (route.getDistance() / shipSpeed));
			
			if (environment.getMoney() < wages) {
				loopGoBtn.setEnabled(false);
				loopGoBtn.setToolTipText("Unfortunately, you can't pay your crew enough to get you there.");
			}
			
			loopGoBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String message = "You pay your crew %d in wages and set off".formatted(wages);
					JOptionPane.showMessageDialog(contentPane, message, "", JOptionPane.INFORMATION_MESSAGE);
					environment.setSail(route, wages);
					
					GUI.openNextFrame();
					dispose();
					
				}
			});
			panel.add(loopGoBtn);
			
			
		}
		
		
		
	}

}
