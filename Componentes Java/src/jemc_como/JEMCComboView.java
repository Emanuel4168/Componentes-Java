package jemc_como;

import javax.swing.*;

public class JEMCComboView extends JPanel{	

	private JComboBox cmbEstados,cmbMunicipios,cmbCiudades;
	
	public JEMCComboView() {
		cmbEstados = new JComboBox();
		cmbMunicipios = new JComboBox();
		cmbCiudades = new JComboBox();
		
		add(cmbEstados);
		add(cmbMunicipios);
		add(cmbCiudades);
		setVisible(true);
	}
}
