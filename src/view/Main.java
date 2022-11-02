package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import controller.Store;
import model.StoreDB;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private Panel_store panel_store;
	private Panel_product panel_product;
	private Panel_request panel_request;
	private JLabel lblRobertosCafeteria;
	private StoreDB storeDB = new StoreDB();
	private ArrayList<Store> stores;
	private JLabel lblLoja;
	private JComboBox<Store> cbxSelectedStore;
	private Store selectedStore;

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
		setBounds(100, 100, 923, 673);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 13));
		tabbedPane.setBounds(10, 63, 886, 557);

		stores = storeDB.searchStores(stores);
		cbxSelectedStore = new JComboBox<Store>();
		cbxSelectedStore.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxSelectedStore.setBounds(712, 56, 184, 22);
		for (Store s : stores) {
			cbxSelectedStore.addItem(s);
		}
		contentPane.add(cbxSelectedStore);

		lblLoja = new JLabel("Loja:");
		lblLoja.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLoja.setBounds(670, 61, 41, 17);
		contentPane.add(lblLoja);
		panel_store = new Panel_store(stores, cbxSelectedStore);
		panel_product = new Panel_product((Store) cbxSelectedStore.getSelectedItem());
		panel_request = new Panel_request((Store) cbxSelectedStore.getSelectedItem());
		tabbedPane.addTab("Lojas", panel_store);
		tabbedPane.addTab("Produtos", panel_product);
		tabbedPane.addTab("Pedidos", panel_request);
		contentPane.add(tabbedPane);

		setLocationRelativeTo(null);

		lblRobertosCafeteria = new JLabel("Roberto's Cafeteria");
		lblRobertosCafeteria.setFont(new Font("Segoe Print", Font.PLAIN, 26));
		lblRobertosCafeteria.setBounds(331, 16, 257, 33);
		contentPane.add(lblRobertosCafeteria);
		cbxSelectedStore.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent changeStore) {
				if (changeStore.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
					selectedStore = (Store) cbxSelectedStore.getSelectedItem();
					panel_product.changeStore(selectedStore);
				}
			}
		});
	}

	public Store getSelectedStore() {
		return (Store) cbxSelectedStore.getSelectedItem();
	}
}
