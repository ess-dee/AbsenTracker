//How to add checkbox in Jtable populated using rs2xml
//https://stackoverflow.com/questions/32445418/how-to-add-checkbox-in-jtable-populated-using-rs2xml/32446003

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class CheckBoxWrapperTableModel extends AbstractTableModel {
	private ArrayList<Boolean> checkBoxes = new ArrayList<>();

	private DefaultTableModel model;
	private String columnName;

	public CheckBoxWrapperTableModel(DefaultTableModel model, String columnName) {
		this.model = model;
		this.columnName = columnName;

		for (int i = 0; i < model.getRowCount(); i++)
			checkBoxes.add(Boolean.FALSE);
	}

	@Override
	public String getColumnName(int column) {
		return (column > 0) ? model.getColumnName(column - 1) : columnName;
	}

	@Override
	public int getRowCount() {
		return model.getRowCount();
	}

	@Override
	public int getColumnCount() {
		return model.getColumnCount() + 1;
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (column > 0)
			return model.getValueAt(row, column - 1);
		else {
			Object value = checkBoxes.get(row);
			return (value == null) ? Boolean.FALSE : value;
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if (column > 0)
			return false;
		else
			return true;
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		if (column > 0)
			model.setValueAt(value, row, column - 1);
		else {
			checkBoxes.set(row, (Boolean) value);
			// ((Boolean) value).booleanValue();

		}

		fireTableCellUpdated(row, column);
	}

	@Override
	public Class getColumnClass(int column) {
		return (column > 0) ? model.getColumnClass(column - 1) : Boolean.class;
	}

	public void removeRow(int row) {
		checkBoxes.remove(row);
		fireTableRowsDeleted(row, row);
		model.removeRow(row);
	}
}
