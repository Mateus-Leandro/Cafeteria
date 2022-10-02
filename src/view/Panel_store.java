package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
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
import javax.swing.text.MaskFormatter;

import controller.Store;
import icons.Icons;
import model.StoreDB;
import tableModel.TableModelStore;
import tools.TextFieldFormat;

public class Panel_store extends JPanel {
	private JSeparator separatorTitle;
	private JLabel lblTitle;
	private JLabel lblId;
	private JTextField txtNumberStore;
	private JLabel lblCnpj;
	private JFormattedTextField fTxtCnpj;
	private JLabel lblNameStore;
	private JLabel lblStreet;
	private JTextField txtNameStore;
	private JTextField txtStreet;
	private JLabel lblStreetNumber;
	private JTextField txtStreetNumber;
	private JLabel lblDistrict;
	private JTextField txtDistrict;
	private JLabel lblCity;
	private JTextField txtCity;
	private JLabel lblAddress;
	private JSeparator separatorAddress;
	private JLabel lblRegisteredStores;
	private JSeparator separatorRegisteredStores1;
	private JSeparator separatorRegisteredStores2;
	private JLabel lblInfoBasic;
	private JSeparator separatorAddress1;
	private JScrollPane scrollPaneStores;
	private Icons icones = new Icons();
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnSave;
	private JButton btnCancel;
	private JTable tableStores;
	private ArrayList<Store> stores = new ArrayList<Store>();
	private TableModelStore tableModel;
	private StoreDB storeDB = new StoreDB();
	private MaskFormatter maskCnpj = null;
	private Store selectedStore = new Store();
	private Store storeMounted = new Store();

	public Panel_store() {
		setLayout(null);

		separatorTitle = new JSeparator();
		separatorTitle.setBounds(10, 49, 857, 3);
		add(separatorTitle);

		lblTitle = new JLabel("Cadastro de Loja");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTitle.setBounds(315, 11, 244, 29);
		add(lblTitle);

		lblId = new JLabel("Nº Loja:");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblId.setBounds(10, 145, 59, 17);
		add(lblId);

		txtNumberStore = new JTextField();
		txtNumberStore.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNumberStore.setToolTipText("Gerado automaticamente");
		txtNumberStore.setEditable(false);
		txtNumberStore.setBounds(71, 145, 59, 20);
		add(txtNumberStore);
		txtNumberStore.setColumns(10);

		lblCnpj = new JLabel("CNPJ:");
		lblCnpj.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCnpj.setBounds(676, 189, 47, 17);
		add(lblCnpj);

		try {
			maskCnpj = new MaskFormatter("##.###.###/####-##");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fTxtCnpj = new JFormattedTextField(maskCnpj);
		fTxtCnpj.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent clickCnpj) {
				fTxtCnpj.setCaretPosition(0);
			}
		});
		fTxtCnpj.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCnpj.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtCnpj.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtCnpj.setEditable(false);
		fTxtCnpj.setBounds(724, 187, 143, 20);
		add(fTxtCnpj);

		lblNameStore = new JLabel("Razão Social:");
		lblNameStore.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNameStore.setBounds(10, 187, 95, 17);
		add(lblNameStore);

		lblStreet = new JLabel("Rua:");
		lblStreet.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStreet.setBounds(10, 262, 37, 17);
		add(lblStreet);

		txtNameStore = new JTextField();
		txtNameStore.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNameStore.setDocument(new TextFieldFormat(55, "text"));
		txtNameStore.setEditable(false);
		txtNameStore.setColumns(10);
		txtNameStore.setBounds(102, 187, 399, 20);
		add(txtNameStore);

		txtStreet = new JTextField();
		txtStreet.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtStreet.setEditable(false);
		txtStreet.setColumns(10);
		txtStreet.setBounds(46, 262, 353, 20);
		add(txtStreet);

		lblStreetNumber = new JLabel("Nº:");
		lblStreetNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStreetNumber.setBounds(472, 262, 29, 17);
		add(lblStreetNumber);

		txtStreetNumber = new JTextField();
		txtStreetNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtStreetNumber.setEditable(false);
		txtStreetNumber.setColumns(10);
		txtStreetNumber.setBounds(500, 262, 59, 20);
		add(txtStreetNumber);

		lblDistrict = new JLabel("Bairro:");
		lblDistrict.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDistrict.setBounds(10, 301, 52, 17);
		add(lblDistrict);

		txtDistrict = new JTextField();
		txtDistrict.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDistrict.setEditable(false);
		txtDistrict.setColumns(10);
		txtDistrict.setBounds(61, 301, 338, 20);
		add(txtDistrict);

		lblCity = new JLabel("Cidade:");
		lblCity.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCity.setBounds(580, 262, 52, 17);
		add(lblCity);

		txtCity = new JTextField();
		txtCity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtCity.setEditable(false);
		txtCity.setColumns(10);
		txtCity.setBounds(635, 262, 232, 20);
		add(txtCity);

		lblAddress = new JLabel("Endereço");
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAddress.setBounds(10, 228, 99, 23);
		add(lblAddress);

		separatorAddress = new JSeparator();
		separatorAddress.setBounds(109, 242, 758, 3);
		add(separatorAddress);

		lblRegisteredStores = new JLabel("Lojas Cadastradas");
		lblRegisteredStores.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRegisteredStores.setBounds(354, 370, 187, 23);
		add(lblRegisteredStores);

		separatorRegisteredStores1 = new JSeparator();
		separatorRegisteredStores1.setBounds(9, 383, 340, 3);
		add(separatorRegisteredStores1);

		separatorRegisteredStores2 = new JSeparator();
		separatorRegisteredStores2.setBounds(540, 383, 327, 3);
		add(separatorRegisteredStores2);

		lblInfoBasic = new JLabel("Informações Básicas");
		lblInfoBasic.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblInfoBasic.setBounds(10, 111, 216, 23);
		add(lblInfoBasic);

		separatorAddress1 = new JSeparator();
		separatorAddress1.setBounds(222, 125, 645, 3);
		add(separatorAddress1);

		stores = searchStores();
		tableModel = new TableModelStore(stores);
		tableStores = new JTable(tableModel);
		tableStores.setBounds(10, 404, 722, 125);
		formatTable(tableStores);

		tableStores.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent selecaoLinhaTabela) {
				ListSelectionModel lsm = (ListSelectionModel) selecaoLinhaTabela.getSource();
				if (!lsm.isSelectionEmpty() && btnNew.isEnabled()) {
					btnEdit.setEnabled(true);
					btnDelete.setEnabled(true);

					int selectedRow = tableStores.getSelectedRow();
					int selectedStoreNumber = (int) tableStores.getValueAt(selectedRow, 0);
					selectStoreTable(selectedStoreNumber);
				} else {
					btnEdit.setEnabled(false);
					btnDelete.setEnabled(false);
					tableStores.clearSelection();
				}
			}
		});

		scrollPaneStores = new JScrollPane(tableStores);
		scrollPaneStores.setBounds(10, 404, 857, 125);
		add(scrollPaneStores);

		btnNew = new JButton("Novo");
		btnNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNewStore) {
				if (btnNew.isEnabled()) {
					newStore();
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
			public void mousePressed(MouseEvent clickEditStore) {
				if (btnEdit.isEnabled()) {
					editStore();
				}
			}
		});
		btnEdit.setEnabled(false);
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEdit.setBounds(10, 342, 106, 29);
		btnEdit.setIcon(icones.getIcon_edit());
		add(btnEdit);

		btnDelete = new JButton("Excluir");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickDeleteStore) {
				if (btnDelete.isEnabled()) {
					int opcao = JOptionPane.showConfirmDialog(lblStreetNumber,
							"Deseja excluir a loja abaixo?\n" + "Código: " + selectedStore.getId() + "\nRazão Social: "
									+ selectedStore.getName(),
							"Exclusão de Lojas", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);
					if (opcao == JOptionPane.YES_OPTION) {
						if (deleteStore(selectedStore.getId())) {
							tableModel.removeStore(selectedStore, tableStores);
							formatTable(tableStores);
						}
					}
				}
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(126, 342, 106, 29);
		btnDelete.setIcon(icones.getIcon_delete());
		add(btnDelete);

		btnSave = new JButton("Salvar");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickConfirmStore) {
				if (validateFields()) {
					if (confirmStore()) {
						JOptionPane.showMessageDialog(lblStreetNumber,
								"Loja" + " " + storeMounted.getId() + " " + "salva corretamente.", "Cadastro de lojas",
								JOptionPane.WARNING_MESSAGE);
						cancelStore();
					}
				} else {
					JOptionPane.showMessageDialog(lblStreetNumber, "Verificar campos obrigatórios!",
							"Campos obrigatórios não preenchidos.", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSave.setVisible(false);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSave.setBounds(761, 346, 106, 29);
		btnSave.setIcon(icones.getIcon_ok());
		add(btnSave);

		btnCancel = new JButton("Cancelar");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancel) {
				cancelStore();
			}
		});
		btnCancel.setVisible(false);
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancel.setBounds(646, 346, 106, 29);
		btnCancel.setIcon(icones.getIcon_cancel());
		add(btnCancel);
	}

	public void editStore() {
		btnEdit.setEnabled(false);
		btnDelete.setEnabled(false);
		enableFields();
		txtNameStore.requestFocus();
	}

	public void newStore() {
		tableStores.clearSelection();
		enableFields();
		cleanFields();
		txtNameStore.requestFocus();
	}

	public void cancelStore() {
		disableFields();
		cleanFields();
	}

	public void enableFields() {
		txtNameStore.setEditable(true);
		fTxtCnpj.setEditable(true);
		txtStreet.setEditable(true);
		txtStreetNumber.setEditable(true);
		txtCity.setEditable(true);
		txtDistrict.setEditable(true);

		btnNew.setEnabled(false);
		btnSave.setVisible(true);
		btnCancel.setVisible(true);
	}

	public void disableFields() {
		txtNameStore.setEditable(false);
		fTxtCnpj.setEditable(false);
		txtStreet.setEditable(false);
		txtStreetNumber.setEditable(false);
		txtCity.setEditable(false);
		txtDistrict.setEditable(false);

		btnNew.setEnabled(true);
		btnSave.setVisible(false);
		btnCancel.setVisible(false);
	}

	public void cleanFields() {
		txtNumberStore.setText(null);
		txtNameStore.setText(null);
		fTxtCnpj.setText(null);
		txtStreet.setText(null);
		txtStreetNumber.setText(null);
		txtCity.setText(null);
		txtDistrict.setText(null);

		tableStores.clearSelection();
		txtNameStore.setBorder(new LineBorder(Color.lightGray));
		fTxtCnpj.setBorder(new LineBorder(Color.lightGray));
	}

	public ArrayList<Store> searchStores() {
		stores = storeDB.searchStores(stores);
		return stores;
	}

	public void formatTable(JTable table) {
		tableStores.setAutoResizeMode(tableStores.AUTO_RESIZE_OFF);
		tableStores.getTableHeader().setReorderingAllowed(false);
		tableStores.getTableHeader().setResizingAllowed(false);

		table.getColumnModel().getColumn(0).setPreferredWidth(70); // Number Store
		table.getColumnModel().getColumn(1).setPreferredWidth(200); // Name
		table.getColumnModel().getColumn(2).setPreferredWidth(150); // Cnpj
		table.getColumnModel().getColumn(3).setPreferredWidth(200); // Street
		table.getColumnModel().getColumn(4).setPreferredWidth(70); // Street Number
		table.getColumnModel().getColumn(5).setPreferredWidth(150); // City
		table.getColumnModel().getColumn(6).setPreferredWidth(150); // Creation Date
		table.getColumnModel().getColumn(7).setPreferredWidth(100); // Creation Date

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
		table.setRowSorter(sorter);
	}

	public void selectStoreTable(int id) {
		selectedStore = null;
		for (Store storeFound : stores) {
			if (storeFound.getId() == id) {
				selectedStore = storeFound;
				break;
			}
		}

		if (selectedStore != null) {
			txtNumberStore.setText(Integer.toString(selectedStore.getId()));
			txtNameStore.setText(selectedStore.getName());
			fTxtCnpj.setText(selectedStore.getCnpj());
			txtStreet.setText(selectedStore.getStreet());
			txtStreetNumber.setText(selectedStore.getStreetNumber());
			txtCity.setText(selectedStore.getCity());
			txtDistrict.setText(selectedStore.getDistrict());
		}
	}

	public boolean confirmStore() {
		boolean saved = false;
		String operation;

		if (txtNumberStore.getText().isBlank()) {
			operation = "storeInclusion";
			storeMounted = storeDB.includeStore(storeMount());
			saved = storeMounted != null;
		} else {
			operation = "storeUpdate";
			saved = storeDB.updateStore(storeMount());
		}

		if (saved) {
			updateListStores(storeMounted, operation);
		}
		return saved;
	}

	public boolean validateFields() {
		boolean valid = true;

		if (txtNameStore.getText().trim().isBlank()) {
			valid = false;
			txtNameStore.setBorder(new LineBorder(Color.red));
		} else {
			txtNameStore.setBorder(new LineBorder(Color.lightGray));
		}

		if (fTxtCnpj.getText().equals("  .   .   /    -  ")) {
			valid = false;
			fTxtCnpj.setBorder(new LineBorder(Color.red));
		} else {
			fTxtCnpj.setBorder(new LineBorder(Color.lightGray));
		}

		return valid;
	}

	public boolean deleteStore(int storeNumberDeleted) {
		return storeDB.deleteStore(storeNumberDeleted);
	}

	public Store storeMount() {
		if (!txtNumberStore.getText().isBlank()) {
			storeMounted.setId(Integer.parseInt(txtNumberStore.getText()));
		}
		storeMounted.setName(txtNameStore.getText().trim());
		storeMounted.setCnpj(fTxtCnpj.getText().trim());
		storeMounted.setStreet(txtStreet.getText().trim());
		storeMounted.setStreetNumber(txtStreetNumber.getText().trim());
		storeMounted.setCity(txtCity.getText());
		storeMounted.setDistrict(txtDistrict.getText());
		return storeMounted;
	}

	public void updateListStores(Store store, String operation) {
		switch (operation) {
			case "storeUpdate":
				for (Store s : stores) {
					if (s.equals(store)) { // Método equal utilizando número da loja para comparação.
						stores.set(stores.indexOf(s), store);
						break;
					}
				}
				break;
			case "storeInclusion":
				tableModel.addStore(store, tableStores);
				break;
		}
		formatTable(tableStores);
	}
}
