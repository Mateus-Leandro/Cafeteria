package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import controller.Product_registration;
import controller.Store;
import icons.Icons;
import model.ProductDB;
import tableModel.TableModelProduct;
import tools.FormatNumber;
import tools.TextFieldFormat;

public class Panel_product extends JPanel {
	private JSeparator separatorTitle;
	private JLabel lblTitle;
	private JLabel lblId;
	private JTextField txtIdProduct;
	private JLabel lblNameProduct;
	private JLabel lblPrice;
	private JTextField txtNameProduct;
	private JTextField txtPrice;
	private JLabel lblRegisteredProducts;
	private JSeparator separatorRegisteredProducts1;
	private JSeparator separatorRegisteredProducts2;
	private JLabel lblInfoProduct;
	private JSeparator separatorInfoProduct;
	private JScrollPane scrollPaneProducts;
	private Icons icones = new Icons();
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnSave;
	private JButton btnCancel;
	private JTable tableProducts;
	private ArrayList<Product_registration> products = new ArrayList<Product_registration>();
	private TableModelProduct tableModel;
	private ProductDB productDB = new ProductDB();
	private Product_registration selectedProduct;
	private Product_registration productMounted = new Product_registration();
	private JLabel lblBarCode;
	private JTextField txtBarCode;
	private JLabel lblManufacturerDate;
	private JLabel lblValidationDate;
	private JLabel lblSearchType;
	private JComboBox<String> cbxSearchType;
	private JTextField txtSearch;
	private FormatNumber fn;
	private JDateChooser jdcManufacturerDate;
	private JDateChooser jdcValidationDate;
	private JLabel lblInventory;
	private JTextField txtInventory;
	private DecimalFormat df = new DecimalFormat(",##0.00");
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private TextFieldFormat tf;
	private Store selectedStore;

	public Panel_product(Store Store) {
		this.selectedStore = Store;
		setLayout(null);
		separatorTitle = new JSeparator();
		separatorTitle.setBounds(10, 49, 857, 3);
		add(separatorTitle);

		lblTitle = new JLabel("Cadastro de Produto");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTitle.setBounds(295, 11, 264, 29);
		add(lblTitle);

		lblId = new JLabel("C??digo:");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblId.setBounds(10, 156, 59, 17);
		add(lblId);

		txtIdProduct = new JTextField();
		txtIdProduct.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtIdProduct.setToolTipText("Gerado automaticamente");
		txtIdProduct.setEditable(false);
		txtIdProduct.setBounds(71, 156, 59, 20);
		add(txtIdProduct);
		txtIdProduct.setColumns(10);

		lblNameProduct = new JLabel("Nome:");
		lblNameProduct.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNameProduct.setBounds(154, 156, 47, 17);
		add(lblNameProduct);

		lblPrice = new JLabel("Pre??o de venda:");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPrice.setBounds(10, 196, 120, 17);
		add(lblPrice);

		tf = new TextFieldFormat(55, "text");
		txtNameProduct = new JTextField();
		txtNameProduct.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNameProduct.setDocument(tf);
		txtNameProduct.setEditable(false);
		txtNameProduct.setColumns(10);
		txtNameProduct.setBounds(206, 154, 353, 20);
		add(txtNameProduct);

		fn = new FormatNumber(9, 2);
		txtPrice = new JTextField();
		txtPrice.setDocument(fn);
		txtPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPrice.setEditable(false);
		txtPrice.setColumns(10);
		txtPrice.setBounds(131, 194, 101, 20);
		add(txtPrice);

		lblRegisteredProducts = new JLabel("Produtos Cadastrados");
		lblRegisteredProducts.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRegisteredProducts.setBounds(314, 370, 227, 23);
		add(lblRegisteredProducts);

		separatorRegisteredProducts1 = new JSeparator();
		separatorRegisteredProducts1.setBounds(9, 383, 299, 3);
		add(separatorRegisteredProducts1);

		separatorRegisteredProducts2 = new JSeparator();
		separatorRegisteredProducts2.setBounds(538, 383, 329, 3);
		add(separatorRegisteredProducts2);

		lblInfoProduct = new JLabel("Informa????es do Produto");
		lblInfoProduct.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblInfoProduct.setBounds(10, 122, 252, 23);
		add(lblInfoProduct);

		separatorInfoProduct = new JSeparator();
		separatorInfoProduct.setBounds(262, 136, 605, 3);
		add(separatorInfoProduct);

		products = searchproducts(selectedStore.getId());
		tableModel = new TableModelProduct(products, selectedStore.getId());
		tableProducts = new JTable(tableModel);
		tableProducts.setBounds(10, 404, 722, 125);
		formatTable(tableProducts);

		tableProducts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent selecaoLinhaTabela) {
				ListSelectionModel lsm = (ListSelectionModel) selecaoLinhaTabela.getSource();
				if (!lsm.isSelectionEmpty() && btnNew.isEnabled()) {
					btnEdit.setEnabled(true);
					btnDelete.setEnabled(true);

					int selectedRow = tableProducts.getSelectedRow();
					int selectedProductId = (int) tableProducts.getValueAt(selectedRow, 0);
					selectProductTable(selectedProductId);
				} else {
					btnEdit.setEnabled(false);
					btnDelete.setEnabled(false);
					tableProducts.clearSelection();
				}
			}
		});

		scrollPaneProducts = new JScrollPane(tableProducts);
		scrollPaneProducts.setBounds(10, 404, 857, 125);
		add(scrollPaneProducts);

		btnNew = new JButton("Novo");
		btnNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNewProduct) {
				if (btnNew.isEnabled()) {
					newProduct();
				}
			}
		});
		btnNew.setBounds(10, 70, 89, 29);
		btnNew.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNew.setIcon(icones.getIcon_plus());
		add(btnNew);

		btnEdit = new JButton("Editar");
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEditProduct) {
				if (btnEdit.isEnabled()) {
					editProduct();
				}
			}
		});
		btnEdit.setEnabled(false);
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEdit.setBounds(10, 295, 106, 29);
		btnEdit.setIcon(icones.getIcon_edit());
		add(btnEdit);

		btnDelete = new JButton("Excluir");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickDeleteProduct) {
				if (btnDelete.isEnabled()) {
					int opcao = JOptionPane.showConfirmDialog(lblRegisteredProducts,
							"Deseja excluir o produto abaixo?\n" + "C??digo: " + selectedProduct.getId() + "\nNome: "
									+ selectedProduct.getName(),
							"Exclus??o de Lojas", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);
					if (opcao == JOptionPane.YES_OPTION) {
						if (deleteProduct(selectedProduct.getId())) {
							tableModel.removeProduct(selectedProduct, tableProducts);
							tableModel.reloadTable(tableProducts, products);
							formatTable(tableProducts);
						}
					}
				}
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(126, 295, 106, 29);
		btnDelete.setIcon(icones.getIcon_delete());
		add(btnDelete);

		btnSave = new JButton("Salvar");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickConfirmProduct) {
				if (validateFields()) {
					if (confirmProduct()) {
						JOptionPane.showMessageDialog(lblRegisteredProducts,
								"Produto:" + " " + productMounted.getId() + " " + "salvo corretamente.",
								"Cadastro de produtos", JOptionPane.WARNING_MESSAGE);
						cancelProduct();
					}
				} else {
					JOptionPane.showMessageDialog(lblRegisteredProducts, "Verificar campos obrigat??rios!",
							"Campos obrigat??rios n??o preenchidos.", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSave.setVisible(false);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSave.setBounds(761, 295, 106, 29);
		btnSave.setIcon(icones.getIcon_ok());
		add(btnSave);

		btnCancel = new JButton("Cancelar");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancel) {
				cancelProduct();
			}
		});
		btnCancel.setVisible(false);
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancel.setBounds(646, 295, 106, 29);
		btnCancel.setIcon(icones.getIcon_cancel());
		add(btnCancel);

		lblBarCode = new JLabel("Cod. Barras:");
		lblBarCode.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBarCode.setBounds(625, 156, 89, 17);
		add(lblBarCode);

		txtBarCode = new JTextField();
		txtBarCode.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtBarCode.setEditable(false);
		txtBarCode.setColumns(10);
		txtBarCode.setBounds(717, 154, 150, 20);
		add(txtBarCode);

		lblManufacturerDate = new JLabel("Data de fabrica????o:");
		lblManufacturerDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblManufacturerDate.setBounds(616, 196, 136, 17);
		add(lblManufacturerDate);

		lblValidationDate = new JLabel("Data de Validade:");
		lblValidationDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblValidationDate.setBounds(626, 227, 126, 17);
		add(lblValidationDate);

		lblSearchType = new JLabel("Pesquisar por");
		lblSearchType.setToolTipText("");
		lblSearchType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSearchType.setBounds(10, 337, 89, 20);
		add(lblSearchType);

		cbxSearchType = new JComboBox<String>();
		cbxSearchType.setModel(new DefaultComboBoxModel(new String[] { "Cod", "Nome", "Cod. Barras" }));
		cbxSearchType.setSelectedIndex(0);
		cbxSearchType.setMaximumRowCount(3);
		cbxSearchType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxSearchType.setBounds(99, 335, 96, 26);
		add(cbxSearchType);

		txtSearch = new JTextField();
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent productSearch) {
				if (txtSearch.getText().isBlank()) {
					products = searchproducts(selectedStore.getId());
				} else {
					products = productDB.searchProduct(selectedStore, products, txtSearch.getText().trim() + "%",
							cbxSearchType.getSelectedItem().toString());
				}
				tableModel.reloadTable(tableProducts, products);
				formatTable(tableProducts);
			}
		});
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSearch.setColumns(10);
		txtSearch.setBounds(204, 338, 663, 20);
		add(txtSearch);

		jdcValidationDate = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		jdcValidationDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jdcValidationDate.setBounds(757, 224, 110, 20);
		jdcValidationDate.setEnabled(false);
		add(jdcValidationDate);

		jdcManufacturerDate = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		jdcManufacturerDate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent clickAlteraDataPagamento) {
//				jdcManufacturerDate.setDate(null);
				jdcValidationDate.setMinSelectableDate(jdcManufacturerDate.getDate());
				try {
					if (jdcValidationDate.getDate() != null && jdcManufacturerDate.getDate() != null) {
						if (sdf.parse(((JTextField) jdcValidationDate.getDateEditor()).getText())
								.before(sdf.parse(((JTextField) jdcManufacturerDate.getDateEditor()).getText()))) {
							jdcValidationDate.setDate(null);
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		jdcManufacturerDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jdcManufacturerDate.setBounds(757, 196, 110, 20);
		jdcManufacturerDate.setEnabled(false);
		add(jdcManufacturerDate);

		lblInventory = new JLabel("Estoque Atual:");
		lblInventory.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblInventory.setBounds(10, 230, 106, 17);
		add(lblInventory);

		tf = new TextFieldFormat(6, "integer");
		txtInventory = new JTextField();
		txtInventory.setDocument(tf);
		txtInventory.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtInventory.setEditable(false);
		txtInventory.setColumns(10);
		txtInventory.setBounds(118, 228, 101, 20);
		add(txtInventory);
	}

	public void editProduct() {
		btnEdit.setEnabled(false);
		btnDelete.setEnabled(false);
		enableFields();
		txtNameProduct.requestFocus();
	}

	public void newProduct() {
		tableProducts.clearSelection();
		enableFields();
		cleanFields();
		txtNameProduct.requestFocus();
	}

	public void cancelProduct() {
		disableFields();
		cleanFields();
	}

	public void enableFields() {
		txtNameProduct.setEditable(true);
		txtBarCode.setEditable(true);
		txtPrice.setEditable(true);
		txtInventory.setEditable(true);
		jdcManufacturerDate.setEnabled(true);
		jdcValidationDate.setEnabled(true);

		btnNew.setEnabled(false);
		btnSave.setVisible(true);
		btnCancel.setVisible(true);
	}

	public void disableFields() {
		txtNameProduct.setEditable(false);
		txtBarCode.setEditable(false);
		txtPrice.setEditable(false);
		txtInventory.setEditable(false);
		jdcManufacturerDate.setEnabled(false);
		jdcValidationDate.setEnabled(false);

		btnNew.setEnabled(true);
		btnSave.setVisible(false);
		btnCancel.setVisible(false);
	}

	public void cleanFields() {
		txtIdProduct.setText(null);
		txtNameProduct.setText(null);
		txtBarCode.setText(null);
		txtPrice.setText(null);
		txtInventory.setText(null);
		jdcManufacturerDate.setDate(null);
		jdcValidationDate.setDate(null);

		tableProducts.clearSelection();
		txtNameProduct.setBorder(new LineBorder(Color.lightGray));
		jdcValidationDate.setBorder(new LineBorder(Color.lightGray));
		jdcManufacturerDate.setBorder(new LineBorder(Color.lightGray));
		selectedProduct = null;
	}

	public ArrayList<Product_registration> searchproducts(int selectedStoreId) {
		products = productDB.searchAllProducts(products, selectedStoreId);
		return products;
	}

	public void formatTable(JTable table) {
		tableProducts.setAutoResizeMode(tableProducts.AUTO_RESIZE_OFF);
		tableProducts.getTableHeader().setReorderingAllowed(false);
		tableProducts.getTableHeader().setResizingAllowed(false);

		table.getColumnModel().getColumn(0).setPreferredWidth(70); // Product id
		table.getColumnModel().getColumn(1).setPreferredWidth(224); // Name Product
		table.getColumnModel().getColumn(2).setPreferredWidth(150); // Bar Code
		table.getColumnModel().getColumn(3).setPreferredWidth(75); // Price
		table.getColumnModel().getColumn(4).setPreferredWidth(75); // Inventory
		table.getColumnModel().getColumn(5).setPreferredWidth(130); // Manufacturer Date
		table.getColumnModel().getColumn(6).setPreferredWidth(130); // Validation Date

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
		table.setRowSorter(sorter);
	}

	public void selectProductTable(int id) {
		selectedProduct = null;
		for (Product_registration productFound : products) {
			if (productFound.getId() == id) {
				selectedProduct = productFound;
				break;
			}
		}

		if (selectedProduct != null) {
			txtIdProduct.setText(Integer.toString(selectedProduct.getId()));
			txtNameProduct.setText(selectedProduct.getName());
			txtBarCode.setText(selectedProduct.getBarCode());
			txtPrice.setText(df.format(selectedProduct.getPrice()));
			txtInventory.setText(Integer.toString(selectedProduct.getInventory()));
			jdcManufacturerDate.setDate(selectedProduct.getManufacturerDate());
			jdcValidationDate.setDate(selectedProduct.getValidationDate());

		}
	}

	public boolean confirmProduct() {
		boolean saved = false;
		String operation;

		if (txtIdProduct.getText().isBlank()) {
			operation = "productInclusion";
			productMounted = productDB.includeProduct(productMount(), selectedStore.getId());
			saved = productMounted != null;
		} else {
			operation = "productUpdate";
			saved = productDB.updateProduct(productMount(), selectedStore.getId());
		}

		if (saved) {
			updateListProducts(productMounted, operation);
		}
		return saved;
	}

	public boolean validateFields() {
		boolean valid = true;

		if (txtNameProduct.getText().isBlank()) {
			valid = false;
			txtNameProduct.setBorder(new LineBorder(Color.red));
		}

		if (jdcManufacturerDate.getDate() == null) {
			valid = false;
			jdcManufacturerDate.setBorder(new LineBorder(Color.RED));
		}

		if (jdcValidationDate.getDate() == null) {
			valid = false;
			jdcValidationDate.setBorder(new LineBorder(Color.RED));
		}

		return valid;
	}

	public boolean deleteProduct(int productIdDeleted) {
		return productDB.deleteProduct(productIdDeleted);
	}

	public Product_registration productMount() {
		productMounted = new Product_registration();
		Integer id = null;
		String name = null;
		String barCode = null;
		Double price = 0.00;
		Integer inventory = 0;
		Date manufacturerDate = null;
		Date validationDate = null;

		if (!txtIdProduct.getText().isBlank()) {
			id = Integer.parseInt(txtIdProduct.getText());
		}
		if (!txtNameProduct.getText().isBlank()) {
			name = txtNameProduct.getText().trim();
		}
		if (!txtBarCode.getText().isBlank()) {
			barCode = txtBarCode.getText().trim();
		}
		if (!txtPrice.getText().isBlank()) {
			price = Double.valueOf(txtPrice.getText().replaceAll("\\.", "").replace(",", "."));
		}
		if (!txtInventory.getText().isBlank()) {
			inventory = Integer.parseInt(txtInventory.getText().replaceAll("\\.", "").replace(",", "."));
		}

		if (jdcManufacturerDate.getDate() != null) {
			manufacturerDate = jdcManufacturerDate.getDate();
		}
		if (jdcValidationDate.getDate() != null) {
			validationDate = jdcValidationDate.getDate();
		}

		productMounted.setId(id);
		productMounted.setName(name);
		productMounted.setBarCode(barCode);
		productMounted.setPrice(price);
		productMounted.setInventory(inventory);
		productMounted.setManufacturerDate(manufacturerDate);
		productMounted.setValidationDate(validationDate);

		return productMounted;
	}

	public void updateListProducts(Product_registration product, String operation) {
		switch (operation) {
			case "productUpdate":
				for (Product_registration p : products) {
					if (p.equals(product)) { // M??todo equal utilizando c??digo do produto para compara????o.
						products.set(products.indexOf(p), product);
						break;
					}
				}
				break;
			case "productInclusion":
				tableModel.addProduct(product, tableProducts);
				break;
		}
		tableModel.reloadTable(tableProducts, products);
		tableModel.fireTableDataChanged();
		formatTable(tableProducts);
	}

	public void changeStore(Store selectedStore) {
		this.selectedStore = selectedStore;
		products = searchproducts(selectedStore.getId());
		tableModel.reloadTable(tableProducts, products);
		formatTable(tableProducts);
		cancelProduct();
		txtSearch.setText(null);
	}
}
