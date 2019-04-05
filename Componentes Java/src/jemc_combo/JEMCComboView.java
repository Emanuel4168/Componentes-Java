package jemc_combo;

import javax.swing.*;

public class JEMCComboView extends JPanel{	

	private JComboBox cmbEstados,cmbMunicipios,cmbCiudades;
	
	public JEMCComboView() {
		cmbEstados = new JComboBox();
		cmbMunicipios = new JComboBox();
		cmbCiudades = new JComboBox();
		
		add(new JLabel("Estados:"));
		add(cmbEstados);
		add(new JLabel("Municipios:"));
		add(cmbMunicipios);
		add(new JLabel("Ciudades:"));
		add(cmbCiudades);
		setVisible(true);
	}
}
