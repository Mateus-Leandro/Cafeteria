package tableModel;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import controller.Product_request;

public class TableModelProductRequest extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String columns[] = { "código", "Nome", "Barras", "Quantidade", "Pr.Unit.", "Pr.Total" };
	private ArrayList<Product_request> productsRequest;
	private final int COLUMN_ID_PRODUCT = 0;
	private final int COLUMN_PRODUCT_NAME = 1;
	private final int COLUMN_BAR_CODE = 2;
	private final int COLUMN_THE_AMOUNT = 3;
	private final int COLUMN_UNIT_PRICE = 4;
	private final int COLUMN_TOTAL_PRICE = 5;

	public TableModelProductRequest(ArrayList<Product_request> productsRequest) {
		this.productsRequest = productsRequest;
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
		return productsRequest.size();
	}

	public String getColumnName(int index) {
		return columns[index];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case COLUMN_ID_PRODUCT:
				return Integer.class;
			case COLUMN_PRODUCT_NAME:
				return String.class;
			case COLUMN_BAR_CODE:
				return String.class;
			case COLUMN_THE_AMOUNT:
				return Double.class;
			case COLUMN_UNIT_PRICE:
				return Double.class;
			case COLUMN_TOTAL_PRICE:
				return Double.class;
			default:
				return String.class;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columIndex) {
		Product_request product = this.productsRequest.get(rowIndex);
		switch (columIndex) {
			case COLUMN_ID_PRODUCT:
				return product.getId();
			case COLUMN_PRODUCT_NAME:
				return product.getName();
			case COLUMN_BAR_CODE:
				return product.getBarCode();
			case COLUMN_THE_AMOUNT:
				return product.getTheAmount();
			case COLUMN_UNIT_PRICE:
				return product.getUnitPrice();
			case COLUMN_TOTAL_PRICE:
				return product.getTotalPrice();
		}
		return null;
	}

	public void reloadTable(JTable table, ArrayList<Product_request> products) {
		this.productsRequest = products;
		TableModelProductRequest table_model = new TableModelProductRequest(products);
		table.setModel(table_model);
		this.fireTableDataChanged();
	}

}
