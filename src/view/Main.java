package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;


public class Main extends JFrame {

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private Panel_store panel_store = new Panel_store();
	private JLabel lblRobertosCafeteria;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/icons/cafe.png")));
		setTitle("Roberto's Cafeteria");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 923, 655);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 13));
		tabbedPane.setBounds(10, 38, 886, 567);
		tabbedPane.addTab("Lojas", panel_store);
		contentPane.add(tabbedPane);
		setLocationRelativeTo(null);
		
		lblRobertosCafeteria = new JLabel("Roberto's Cafeteria");
		lblRobertosCafeteria.setFont(new Font("Segoe Print", Font.PLAIN, 26));
		lblRobertosCafeteria.setBounds(331, 16, 257, 33);
		contentPane.add(lblRobertosCafeteria);
	}
}
