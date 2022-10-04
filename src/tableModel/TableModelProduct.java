package tableModel;

import java.sql.Date;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import controller.Product;

public class TableModelProduct extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String columns[] = { "Cod", "Nome", "Cod. Barra", "Preço", "Estoque", "Data Fabricação", "Data de validade" };
	private ArrayList<Product> products;
	private final int COLUMN_COD = 0;
	private final int COLUMN_NAME = 1;
	private final int COLUMN_BAR_CODE = 2;
	private final int COLUMN_PRICE = 3;
	private final int COLUMN_INVENTORY = 4;
	private final int COLUMN_MANUFACTURER_DATE = 5;
	private final int COLUMN_VALIDATION_DATE = 6;
	private Integer store;
	private Integer inventory;

	public TableModelProduct(ArrayList<Product> products, Integer store) {
		this.products = products;
		this.store = store;
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
		if (products != null)
			return products.size();
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

			case COLUMN_COD:
				return Integer.class;
			case COLUMN_NAME:
				return String.class;
			case COLUMN_BAR_CODE:
				return String.class;
			case COLUMN_PRICE:
				return Double.class;
			case COLUMN_INVENTORY:
				return Integer.class;
			case COLUMN_MANUFACTURER_DATE:
				return Date.class;
			case COLUMN_VALIDATION_DATE:
				return Date.class;
			default:
				return String.class;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columIndex) {
		Product product = this.products.get(rowIndex);
		switch (columIndex) {
			case COLUMN_COD:
				return product.getId();
			case COLUMN_NAME:
				return product.getName();
			case COLUMN_BAR_CODE:
				return product.getBarCode();
			case COLUMN_PRICE:
				return product.getPrice();
			case COLUMN_INVENTORY:
				return product.getInventory();
			case COLUMN_MANUFACTURER_DATE:
				return product.getManufacturerDate();
			case COLUMN_VALIDATION_DATE:
				return product.getValidationDate();
		}

		return null;
	}

	public void addProduct(Product newProduct, JTable tableProducts) {
		this.products.add(newProduct);
		reloadTable(tableProducts, products);
	}

	public void removeProduct(Product removedProduct, JTable tableProducts) {
		this.products.remove(removedProduct);
		reloadTable(tableProducts, products);
	}

	public void reloadTable(JTable table, ArrayList<Product> products) {
		TableModelProduct table_model = new TableModelProduct(products, store);
		table.setModel(table_model);
		this.fireTableDataChanged();
	}

}
