package view;

import javax.swing.DefaultListSelectionModel;

class DisabledItemSelectionModel extends DefaultListSelectionModel {

	@Override
	public void setSelectionInterval(int index0, int index1) {
		super.setSelectionInterval(-1, -1);
	}
}