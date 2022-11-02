package view;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import controller.Product_request;
import controller.Request;
import controller.Store;
import model.RequestDB;
import tableModel.TableModelProductRequest;
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
	private JLabel lblProducts;
	private JSeparator SeparatorProducts;
	private JLabel lblSelectedStore;
	private ArrayList<Request> requests = new ArrayList<Request>();
	private TableModelRequest tableModelRequest;
	private RequestDB request_db = new RequestDB();
	private JTable tableProductsRequest;
	private JScrollPane scrollPaneProducstRequest;
	private ArrayList<Product_request> products = new ArrayList<Product_request>();
	private TableModelProductRequest tableModelProductRequest;
	private Store selectedStore;

	/**
	 * Create the panel.
	 */
	public Panel_request(Store store) {
		this.selectedStore = store;
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

		search_requests();
		tableModelRequest = new TableModelRequest(requests);
		tableRequest = new JTable(tableModelRequest);
		formatTableRequest(tableRequest);

		tableRequest.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent selecaoLinhaTabela) {
				ListSelectionModel lsm = (ListSelectionModel) selecaoLinhaTabela.getSource();
				if (!lsm.isSelectionEmpty()) {
					int selectedRow = tableRequest.getSelectedRow();
					int selectedRequestId = (int) tableRequest.getValueAt(selectedRow, 0);
					selectRequest(selectedRequestId);
					formatTableProductsRequest(tableProductsRequest);
				} else {
					tableRequest.clearSelection();
				}
			}
		});

		scrollPaneRequest = new JScrollPane(tableRequest);
		scrollPaneRequest.setBounds(10, 137, 857, 155);
		add(scrollPaneRequest);

		lblRequests = new JLabel("Pedidos");
		lblRequests.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRequests.setBounds(10, 103, 86, 23);
		add(lblRequests);

		SeparatorRequests = new JSeparator();
		SeparatorRequests.setBounds(94, 117, 773, 3);
		add(SeparatorRequests);

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

		tableModelProductRequest = new TableModelProductRequest(products);
		tableProductsRequest = new JTable(tableModelProductRequest);
		tableProductsRequest.setRowSelectionAllowed(false);
		scrollPaneProducstRequest = new JScrollPane(tableProductsRequest);
		scrollPaneProducstRequest.setBounds(10, 352, 857, 163);
		formatTableProductsRequest(tableProductsRequest);
		add(scrollPaneProducstRequest);

	}

	public void search_requests() {
		this.requests = request_db.searchRequest(requests);
	}

	public void selectRequest(int idRequest) {
		for (Request r : requests) {
			if (r.getId() == idRequest) {
				products = r.getProducts();
				tableModelProductRequest.reloadTable(tableProductsRequest, products);
				break;
			}
		}
	}

	public void formatTableRequest(JTable table) {
		tableRequest.setAutoResizeMode(tableRequest.AUTO_RESIZE_OFF);
		tableRequest.getTableHeader().setReorderingAllowed(false);
		tableRequest.getTableHeader().setResizingAllowed(false);

		tableRequest.getColumnModel().getColumn(0).setPreferredWidth(70); // Request id
		tableRequest.getColumnModel().getColumn(1).setPreferredWidth(230); // Client Name
		tableRequest.getColumnModel().getColumn(2).setPreferredWidth(149); // Request Amount
		tableRequest.getColumnModel().getColumn(3).setPreferredWidth(200); // Payment Type
		tableRequest.getColumnModel().getColumn(4).setPreferredWidth(75); // Request Finished
		tableRequest.getColumnModel().getColumn(5).setPreferredWidth(130); // Manufacturer Date

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModelRequest);
		tableRequest.setRowSorter(sorter);
	}

	public void formatTableProductsRequest(JTable table) {
		tableProductsRequest.setAutoResizeMode(tableRequest.AUTO_RESIZE_OFF);
		tableProductsRequest.getTableHeader().setReorderingAllowed(false);
		tableProductsRequest.getTableHeader().setResizingAllowed(false);

		tableProductsRequest.getColumnModel().getColumn(0).setPreferredWidth(70); // Product id
		tableProductsRequest.getColumnModel().getColumn(1).setPreferredWidth(230); // Product Name
		tableProductsRequest.getColumnModel().getColumn(2).setPreferredWidth(149); // BarCode
		tableProductsRequest.getColumnModel().getColumn(3).setPreferredWidth(149); // the Amount
		tableProductsRequest.getColumnModel().getColumn(4).setPreferredWidth(149); // Unit Price
		tableProductsRequest.getColumnModel().getColumn(5).setPreferredWidth(149); // Total Price

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModelProductRequest);
		tableProductsRequest.setRowSorter(sorter);
	}
}
