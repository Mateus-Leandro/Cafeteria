package tableModel;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import controller.Request;
import controller.Store;

public class TableModelRequest extends AbstractTableModel {

	/**
	 * 
	 */
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static final long serialVersionUID = 1L;
	private String columns[] = { "Nº", "Cliente", "Valor Total", "Pagamento", "Finalizado", "Data Criação" };
	private ArrayList<Request> requests;
	private final int COLUMN_NUMBER_REQUEST = 0;
	private final int COLUMN_CLIENTE_NAME = 1;
	private final int COLUMN_PAYMENT_AMOUNT = 2;
	private final int COLUMN_PAYMENT_TYPE = 3;
	private final int COLUMN_FINISHED_REQUEST = 4;
	private final int COLUMN_CREATION_DATE = 5;

	public TableModelRequest(ArrayList<Request> requests) {
		this.requests = requests;
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
		if (requests != null)
			return requests.size();
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

			case COLUMN_NUMBER_REQUEST:
				return Integer.class;
			case COLUMN_CLIENTE_NAME:
				return String.class;
			case COLUMN_PAYMENT_AMOUNT:
				return Double.class;
			case COLUMN_PAYMENT_TYPE:
				return String.class;
			case COLUMN_FINISHED_REQUEST:
				return Boolean.class;
			case COLUMN_CREATION_DATE:
				return Date.class;
			default:
				return String.class;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columIndex) {
		Request request = this.requests.get(rowIndex);
		switch (columIndex) {
			case COLUMN_NUMBER_REQUEST:
				return request.getId();
			case COLUMN_CLIENTE_NAME:
				return request.getUser().getName();
			case COLUMN_PAYMENT_AMOUNT:
				return request.getAmount();
			case COLUMN_PAYMENT_TYPE:
				return request.getPayment().getDescription();
			case COLUMN_FINISHED_REQUEST:
				return request.getFinished();
			case COLUMN_CREATION_DATE:
				return request.getCreationDate();
		}

		return null;
	}

	public void reloadTable(JTable table, ArrayList<Store> stores) {
		TableModelRequest table_model = new TableModelRequest(requests);
		table.setModel(table_model);
		this.fireTableDataChanged();
	}

}
