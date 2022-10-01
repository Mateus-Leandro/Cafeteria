package view;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.Store;
import icons.Icons;
import tableModel.TableModelStore;

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
	private TableModelStore tableModel = new TableModelStore(stores);

	public Panel_store() {
		setLayout(null);		
		
		separatorTitle = new JSeparator();
		separatorTitle.setBounds(10, 50, 722, 2);
		add(separatorTitle);
		
		lblTitle = new JLabel("Cadastro de Loja");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTitle.setBounds(244, 11, 244, 29);
		add(lblTitle);
		
		lblId = new JLabel("Nº Loja:");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblId.setBounds(10, 145, 59, 17);
		add(lblId);
		
		txtNumberStore = new JTextField();
		txtNumberStore.setBounds(71, 145, 59, 20);
		add(txtNumberStore);
		txtNumberStore.setColumns(10);
		
		lblCnpj = new JLabel("CNPJ:");
		lblCnpj.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCnpj.setBounds(580, 187, 47, 17);
		add(lblCnpj);
		
		fTxtCnpj = new JFormattedTextField();
		fTxtCnpj.setBounds(626, 187, 106, 20);
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
		txtNameStore.setColumns(10);
		txtNameStore.setBounds(102, 187, 353, 20);
		add(txtNameStore);
		
		txtStreet = new JTextField();
		txtStreet.setColumns(10);
		txtStreet.setBounds(46, 262, 353, 20);
		add(txtStreet);
		
		lblStreetNumber = new JLabel("Nº:");
		lblStreetNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStreetNumber.setBounds(472, 262, 29, 17);
		add(lblStreetNumber);
		
		txtStreetNumber = new JTextField();
		txtStreetNumber.setColumns(10);
		txtStreetNumber.setBounds(500, 262, 59, 20);
		add(txtStreetNumber);
		
		lblDistrict = new JLabel("Bairro:");
		lblDistrict.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDistrict.setBounds(10, 301, 52, 17);
		add(lblDistrict);
		
		txtDistrict = new JTextField();
		txtDistrict.setColumns(10);
		txtDistrict.setBounds(61, 301, 338, 20);
		add(txtDistrict);
		
		lblCity = new JLabel("Cidade:");
		lblCity.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCity.setBounds(445, 301, 52, 17);
		add(lblCity);
		
		txtCity = new JTextField();
		txtCity.setColumns(10);
		txtCity.setBounds(500, 301, 232, 20);
		add(txtCity);
		
		lblAddress = new JLabel("Endereço");
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAddress.setBounds(10, 228, 99, 23);
		add(lblAddress);
		
		separatorAddress = new JSeparator();
		separatorAddress.setBounds(109, 242, 623, 3);
		add(separatorAddress);
		
		lblRegisteredStores = new JLabel("Lojas Cadastradas");
		lblRegisteredStores.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRegisteredStores.setBounds(255, 371, 187, 23);
		add(lblRegisteredStores);
		
		separatorRegisteredStores1 = new JSeparator();
		separatorRegisteredStores1.setBounds(9, 382, 237, 2);
		add(separatorRegisteredStores1);
		
		separatorRegisteredStores2 = new JSeparator();
		separatorRegisteredStores2.setBounds(443, 384, 289, 3);
		add(separatorRegisteredStores2);
		
		lblInfoBasic = new JLabel("Informações Básicas");
		lblInfoBasic.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblInfoBasic.setBounds(10, 111, 216, 23);
		add(lblInfoBasic);
		
		separatorAddress1 = new JSeparator();
		separatorAddress1.setBounds(222, 126, 510, 2);
		add(separatorAddress1);
		
		
		tableStores = new JTable(tableModel);
		tableStores.setBounds(10, 404, 722, 125);
		
		scrollPaneStores = new JScrollPane(tableStores);
		scrollPaneStores.setBounds(10, 404, 722, 125);
		add(scrollPaneStores);
		
		btnNew = new JButton("Novo");
		btnNew.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNew.setBounds(10, 63, 106, 29);
		btnNew.setIcon(icones.getIcone_mais());
		add(btnNew);
		
		btnEdit = new JButton("Editar");
		btnSave.setVisible(false);
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEdit.setBounds(10, 342, 106, 29);
		btnEdit.setIcon(icones.getIcone_editar());
		add(btnEdit);
		
		btnDelete = new JButton("Excluir");
		btnDelete.setVisible(false);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(126, 342, 106, 29);
		btnDelete.setIcon(icones.getIcone_excluir());
		add(btnDelete);
		
		btnSave = new JButton("Salvar");
		btnSave.setVisible(false);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSave.setBounds(626, 347, 106, 29);
		btnSave.setIcon(icones.getIcone_ok());
		add(btnSave);
		
		btnCancel = new JButton("Cancelar");
		btnCancel.setVisible(false);
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancel.setBounds(510, 347, 106, 29);
		btnCancel.setIcon(icones.getIcone_cancelar());
		add(btnCancel);
	}
}
