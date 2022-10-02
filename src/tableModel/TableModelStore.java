package tableModel;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import controller.Store;

public class TableModelStore extends AbstractTableModel {

	/**
	 * 
	 */
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static final long serialVersionUID = 1L;
	private String columns[] = { "Nº Loja", "Razão", "CNPJ", "Rua", "Número", "Cidade", "Bairro", "Data Criação" };
	private ArrayList<Store> stores;
	private final int COLUMN_NUMBER_STORE = 0;
	private final int COLUMN_NAME = 1;
	private final int COLUMN_CNPJ = 2;
	private final int COLUMN_STREET = 3;
	private final int COLUMN_NUMBER_ADDRESS = 4;
	private final int COLUMN_CITY = 5;
	private final int COLUMN_DISTRICT = 6;
	private final int COLUMN_CREATION_DATE = 7;

	public TableModelStore(ArrayList<Store> stores) {
		this.stores = stores;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		if (stores != null)
			return stores.size();
		else {
			return 0;
		}
	}

	public String getColumnName(int index) {
		return columns[index];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {

			case COLUMN_NUMBER_STORE:
				return Integer.class;
			case COLUMN_NAME:
				return String.class;
			case COLUMN_CNPJ:
				return String.class;
			case COLUMN_STREET:
				return String.class;
			case COLUMN_NUMBER_ADDRESS:
				return String.class;
			case COLUMN_CITY:
				return String.class;
			case COLUMN_DISTRICT:
				return String.class;
			case COLUMN_CREATION_DATE:
				return Date.class;
			default:
				return String.class;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columIndex) {
		Store store = this.stores.get(rowIndex);
		switch (columIndex) {
			case COLUMN_NUMBER_STORE:
				return store.getId();
			case COLUMN_NAME:
				return store.getName();
			case COLUMN_CNPJ:
				return store.getCnpj();
			case COLUMN_STREET:
				return store.getStreet();
			case COLUMN_NUMBER_ADDRESS:
				return store.getStreetNumber();
			case COLUMN_CITY:
				return store.getCity();
			case COLUMN_DISTRICT:
				return store.getDistrict();
			case COLUMN_CREATION_DATE:
				return store.getCreationDate();
		}

		return null;
	}

	public void addStore(Store new_store, JTable tableStores) {
		this.stores.add(new_store);
		reloadTable(tableStores, stores);
	}

	public void removeStore(Store removed_store, JTable tableStores) {
		this.stores.remove(removed_store);
		reloadTable(tableStores, stores);
	}

	public void reloadTable(JTable table, ArrayList<Store> stores) {
		TableModelStore table_model = new TableModelStore(stores);
		table.setModel(table_model);
		this.fireTableDataChanged();
	}

}
