package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

import com.toedter.calendar.JDateChooser;

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
	private JTextField txtIdRequest;
	private JTable tableRequest;
	private JScrollPane scrollPaneRequest;
	private JLabel lblRequests;
	private JSeparator SeparatorRequests;
	private JLabel lblProducts;
	private JSeparator SeparatorProducts;
	private ArrayList<Request> requests = new ArrayList<Request>();
	private TableModelRequest tableModelRequest;
	private RequestDB request_db = new RequestDB();
	private JTable tableProductsRequest;
	private JScrollPane scrollPaneProducstRequest;
	private ArrayList<Product_request> products = new ArrayList<Product_request>();
	private TableModelProductRequest tableModelProductRequest;
	private Store selectedStore;
	private JLabel lblQuantityRequests;
	private JLabel QuantityRequests = new JLabel();
	private JLabel lblStartingDate;
	private JLabel lblFinalDate;
	private JDateChooser jdcStartingDate;
	private JDateChooser jdcFinalDate;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

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

		jdcStartingDate = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		jdcStartingDate.setVisible(false);
		jdcStartingDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jdcStartingDate.setBounds(421, 76, 110, 20);
		add(jdcStartingDate);

		jdcFinalDate = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		jdcFinalDate.setVisible(false);
		jdcFinalDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jdcFinalDate.setBounds(595, 76, 110, 20);
		add(jdcFinalDate);

		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent changeTypeSearch) {
				if (changeTypeSearch.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
					if (comboBox.getSelectedItem().equals("Data")) {

						jdcStartingDate.setDate(new Date());
						jdcFinalDate.setDate(new Date());

						lblStartingDate.setVisible(true);
						jdcStartingDate.setVisible(true);
						lblFinalDate.setVisible(true);
						jdcFinalDate.setVisible(true);
						txtIdRequest.setVisible(false);
						searchForDate();
					} else {
						lblStartingDate.setVisible(false);
						jdcStartingDate.setVisible(false);
						lblFinalDate.setVisible(false);
						jdcFinalDate.setVisible(false);
						txtIdRequest.setVisible(true);
						search_requests("All", null, null, null);
					}
					formatTableRequest(tableProductsRequest);
				}
			}
		});
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "N. Pedido", "Data" }));
		comboBox.setBounds(94, 72, 105, 23);
		add(comboBox);

		txtIdRequest = new JTextField();
		txtIdRequest.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent searchRequestId) {
				if (txtIdRequest.getText().isBlank()) {
					search_requests("All", null, null, null);
				} else {
					search_requests("NumberRequest", null, null, Integer.parseInt(txtIdRequest.getText().trim()));
				}
				tableModelRequest.reloadTable(tableProductsRequest, requests);
				products.clear();
				tableModelProductRequest.reloadTable(tableProductsRequest, products);
				formatTableProductsRequest(tableProductsRequest);
			}
		});
		txtIdRequest.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtIdRequest.setColumns(10);
		txtIdRequest.setBounds(204, 75, 147, 20);
		add(txtIdRequest);

		search_requests("All", null, null, null);
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

		tableModelProductRequest = new TableModelProductRequest(products);
		tableProductsRequest = new JTable(tableModelProductRequest);
		tableProductsRequest.setRowSelectionAllowed(false);
		scrollPaneProducstRequest = new JScrollPane(tableProductsRequest);
		scrollPaneProducstRequest.setBounds(10, 352, 857, 163);
		formatTableProductsRequest(tableProductsRequest);
		add(scrollPaneProducstRequest);

		lblQuantityRequests = new JLabel("Qtd. Pedidos:");
		lblQuantityRequests.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblQuantityRequests.setBounds(730, 79, 100, 17);
		add(lblQuantityRequests);

		QuantityRequests.setForeground(Color.BLUE);
		QuantityRequests.setFont(new Font("Tahoma", Font.BOLD, 14));
		QuantityRequests.setBounds(840, 79, 27, 17);
		add(QuantityRequests);

		lblStartingDate = new JLabel("De:");
		lblStartingDate.setVisible(false);
		lblStartingDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStartingDate.setBounds(391, 79, 33, 17);
		add(lblStartingDate);

		lblFinalDate = new JLabel("Até");
		lblFinalDate.setVisible(false);
		lblFinalDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFinalDate.setBounds(548, 79, 33, 17);
		add(lblFinalDate);

		jdcStartingDate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent changedStartingDate) {
				String name = changedStartingDate.getPropertyName();
				jdcFinalDate.setMinSelectableDate(jdcStartingDate.getDate());
				if (name.equalsIgnoreCase("date")) {
					try {
						if (jdcFinalDate.getDate() != null && jdcStartingDate.getDate() != null) {
							if (sdf.parse(((JTextField) jdcFinalDate.getDateEditor()).getText())
									.before(sdf.parse(((JTextField) jdcStartingDate.getDateEditor()).getText()))) {
								jdcFinalDate.setDate(jdcStartingDate.getDate());
							}
							searchForDate();
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					formatTableRequest(tableProductsRequest);
				}
			}
		});
		jdcFinalDate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent changedFinalDate) {
				String name = changedFinalDate.getPropertyName();

				if (name.equalsIgnoreCase("date")) {
					searchForDate();
					formatTableRequest(tableProductsRequest);
				}
			}
		});

	}

	public void search_requests(String typeSearch, Date startingDate, Date finalDate, Integer requestSelected) {
		this.requests = request_db.searchRequest(typeSearch, startingDate, finalDate, requestSelected, requests,
				selectedStore.getId());
		QuantityRequests.setText(Integer.toString(requests.size()));
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

	public void changeStore(Store selectedStore) {
		this.selectedStore = selectedStore;
		search_requests("All", null, null, null);
		tableModelRequest.reloadTable(tableProductsRequest, requests);
		products.clear();
		tableModelProductRequest.reloadTable(tableProductsRequest, products);
		formatTableRequest(tableProductsRequest);
		formatTableProductsRequest(tableProductsRequest);
		
		comboBox.getModel().setSelectedItem("N. Pedido");
		txtIdRequest.setVisible(true);
		jdcStartingDate.setVisible(false);
		jdcFinalDate.setVisible(false);
	}

	public void formatTableRequest(JTable table) {
		tableRequest.setAutoResizeMode(tableRequest.AUTO_RESIZE_OFF);
		tableRequest.getTableHeader().setReorderingAllowed(false);
		tableRequest.getTableHeader().setResizingAllowed(false);

		tableRequest.getColumnModel().getColumn(0).setPreferredWidth(60); // Request id
		tableRequest.getColumnModel().getColumn(1).setPreferredWidth(60); // Store id
		tableRequest.getColumnModel().getColumn(2).setPreferredWidth(230); // Client Name
		tableRequest.getColumnModel().getColumn(3).setPreferredWidth(149); // Request Amount
		tableRequest.getColumnModel().getColumn(4).setPreferredWidth(150); // Payment Type
		tableRequest.getColumnModel().getColumn(5).setPreferredWidth(75); // Request Finished
		tableRequest.getColumnModel().getColumn(6).setPreferredWidth(130); // Manufacturer Date

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModelRequest);
		tableRequest.setRowSorter(sorter);
	}

	public void formatTableProductsRequest(JTable table) {
		tableProductsRequest.setAutoResizeMode(tableRequest.AUTO_RESIZE_OFF);
		tableProductsRequest.getTableHeader().setReorderingAllowed(false);
		tableProductsRequest.getTableHeader().setResizingAllowed(false);

		tableProductsRequest.getColumnModel().getColumn(0).setPreferredWidth(70); // Product id
		tableProductsRequest.getColumnModel().getColumn(1).setPreferredWidth(200); // Product Name
		tableProductsRequest.getColumnModel().getColumn(2).setPreferredWidth(137); // BarCode
		tableProductsRequest.getColumnModel().getColumn(3).setPreferredWidth(149); // the Amount
		tableProductsRequest.getColumnModel().getColumn(4).setPreferredWidth(149); // Unit Price
		tableProductsRequest.getColumnModel().getColumn(5).setPreferredWidth(149); // Total Price

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModelProductRequest);
		tableProductsRequest.setRowSorter(sorter);
	}

	public void searchForDate() {
		txtIdRequest.setText(null);
		search_requests("Date", jdcStartingDate.getDate(), jdcFinalDate.getDate(), null);
		products.clear();
		tableModelProductRequest.reloadTable(tableProductsRequest, products);
		tableModelRequest.reloadTable(tableProductsRequest, requests);
		formatTableProductsRequest(tableProductsRequest);
		formatTableRequest(tableProductsRequest);
	}
}
