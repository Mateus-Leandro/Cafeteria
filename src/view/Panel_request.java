package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import controller.Request;
import controller.Store;
import model.RequestDB;
import tableModel.TableModelRequest;

public class Panel_request extends JPanel {
	private JLabel lblPedidos;
	private JSeparator separatorTitle;
	private JLabel lblTypeSearch;
	private JComboBox comboBox;
	private JTextField textField;
	private JTable tableRequest;
	private JScrollPane scrollPaneRequest;
	private JLabel lblRequests;
	private JSeparator SeparatorRequests;
	private JScrollPane scrollPaneProducts;
	private JLabel lblProducts;
	private JSeparator SeparatorProducts;
	private JLabel lblSelectedStore;
	private JComboBox<Store> cbxSelectedStore;
	private ArrayList<Request> requests = new ArrayList<Request>();
	private TableModelRequest tableModel;
	private RequestDB request_db = new RequestDB();

	/**
	 * Create the panel.
	 */
	public Panel_request() {
		setLayout(null);

		lblPedidos = new JLabel("Pedidos de Venda");
		lblPedidos.setHorizontalAlignment(SwingConstants.CENTER);
		lblPedidos.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblPedidos.setBounds(315, 11, 244, 29);
		add(lblPedidos);

		separatorTitle = new JSeparator();
		separatorTitle.setBounds(10, 49, 857, 3);
		add(separatorTitle);

		lblTypeSearch = new JLabel("Buscar Por:");
		lblTypeSearch.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTypeSearch.setBounds(10, 79, 86, 17);
		add(lblTypeSearch);

		comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "N. Pedido", "Cliente", "Data" }));
		comboBox.setBounds(94, 72, 105, 23);
		add(comboBox);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(204, 75, 325, 20);
		add(textField);

		scrollPaneRequest = new JScrollPane();
		scrollPaneRequest.setBounds(10, 137, 857, 155);
		add(scrollPaneRequest);

		search_requests();
		tableModel = new TableModelRequest(requests);
		tableRequest = new JTable(tableModel);
		formatTable(tableRequest);
		scrollPaneRequest.setViewportView(tableRequest);

		lblRequests = new JLabel("Pedidos");
		lblRequests.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRequests.setBounds(10, 103, 86, 23);
		add(lblRequests);

		SeparatorRequests = new JSeparator();
		SeparatorRequests.setBounds(94, 117, 773, 3);
		add(SeparatorRequests);

		scrollPaneProducts = new JScrollPane();
		scrollPaneProducts.setBounds(10, 352, 857, 155);
		add(scrollPaneProducts);

		lblProducts = new JLabel("Produtos do Pedido");
		lblProducts.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblProducts.setBounds(10, 318, 201, 23);
		add(lblProducts);

		SeparatorProducts = new JSeparator();
		SeparatorProducts.setBounds(214, 332, 653, 9);
		add(SeparatorProducts);

		lblSelectedStore = new JLabel("Loja Selecionada:");
		lblSelectedStore.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSelectedStore.setBounds(561, 79, 120, 17);
		add(lblSelectedStore);

		cbxSelectedStore = new JComboBox<Store>();
		cbxSelectedStore.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxSelectedStore.setBounds(686, 72, 181, 22);
		add(cbxSelectedStore);

	}

	public void search_requests() {
		this.requests = request_db.searchRequest(requests);
	}

	public void formatTable(JTable table) {
		tableRequest.setAutoResizeMode(tableRequest.AUTO_RESIZE_OFF);
		tableRequest.getTableHeader().setReorderingAllowed(false);
		tableRequest.getTableHeader().setResizingAllowed(false);

		table.getColumnModel().getColumn(0).setPreferredWidth(70); // Request id
		table.getColumnModel().getColumn(1).setPreferredWidth(230); // Client Name
		table.getColumnModel().getColumn(2).setPreferredWidth(149); // Request Amount
		table.getColumnModel().getColumn(3).setPreferredWidth(200); // Payment Type
		table.getColumnModel().getColumn(4).setPreferredWidth(75); // Request Finished
		table.getColumnModel().getColumn(5).setPreferredWidth(130); // Manufacturer Date

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
		table.setRowSorter(sorter);
	}
}
