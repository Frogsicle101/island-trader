import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JProgressBar;

public class Sailing extends JFrame {

	private JPanel contentPane;
	private JLabel dayLbl;
	private JProgressBar progressBar;
	private Timer timer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameEnvironment env = new GameEnvironment();
					env.resetGame();
					env.setGameLength(20);
					env.setName("test");
					env.setShip(GameEnvironment.getPossibleShips()[0]);
					env.startGame();
					env.setSail(env.getAllRoutes()[0], 0);
					Sailing frame = new Sailing(env);
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
	public Sailing(GameEnvironment environment) {
		Route route = environment.getRoute();
		int distance = route.getDistance();
		float shipSpeed = environment.getShip().getSpeed();
		int days = (int) (distance / shipSpeed);
		int startTime = environment.getGameTime();
		
				
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		dayLbl = new JLabel("Day: " + environment.getGameTime());
		GridBagConstraints gbc_dayLbl = new GridBagConstraints();
		gbc_dayLbl.anchor = GridBagConstraints.WEST;
		gbc_dayLbl.insets = new Insets(0, 0, 5, 0);
		gbc_dayLbl.gridx = 0;
		gbc_dayLbl.gridy = 0;
		contentPane.add(dayLbl, gbc_dayLbl);
		
		JLabel destinationLbl = new JLabel("Sailing to: " + environment.getDestination().getName());
		GridBagConstraints gbc_destinationLbl = new GridBagConstraints();
		gbc_destinationLbl.anchor = GridBagConstraints.WEST;
		gbc_destinationLbl.insets = new Insets(0, 0, 5, 0);
		gbc_destinationLbl.gridx = 0;
		gbc_destinationLbl.gridy = 1;
		contentPane.add(destinationLbl, gbc_destinationLbl);
		
				
		progressBar = new JProgressBar();
		progressBar.setMaximum(days);
		progressBar.setStringPainted(true);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.anchor = GridBagConstraints.SOUTH;
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 3;
		contentPane.add(progressBar, gbc_progressBar);
		
		ActionListener timerListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (environment.getState() == GameState.ON_ISLAND) {
					timer.stop();
					GUI.openNextFrame();
					dispose();
				}
				
				environment.passDay();
				progressBar.setValue(environment.getGameTime() - startTime);
				dayLbl.setText("Day: " + environment.getGameTime());
				
				
				
			}
		};
		
		
		timer = new Timer(1000, timerListener);
		timer.setInitialDelay(2000);
		timer.start();
		
		
	}

}
