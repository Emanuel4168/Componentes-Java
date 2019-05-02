package components.jemc_combo;

import java.awt.event.ItemEvent;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.*;

public class JEMCCombo extends JPanel implements ItemListener{	

	private final static int ESTADO_LENGTH = 56;
	private final static int MUNICIPIO_LENGTH = 60;
	private final static int CIUDAD_LENGTH = 21;
	private final static String ESTADOS_DEFAULT = "Seleccione un Estado";
	private final static String MUNICIPIOS_DEFAULT = "Seleccione un Municipio";
	private final static String CIUDADES_DEFAULT = "Seleccione una Ciudad";
	public final static int ORIENTACION_HORIZONTAL = 1;
	public final static int ORIENTACION_VERTICAL = 2;
	
	private JComboBox cmbEstados,cmbMunicipios,cmbCiudades;
	private RandomAccessFile estados,municipios,ciudades;
	
	public JEMCCombo(int orientacion) {
		this(orientacion, "");
	}
	
	public JEMCCombo(int orientacion, String estado) {
		this(orientacion, estado,"");
	}
	
	public JEMCCombo(int orientacion, String estado, String municipio) {
		cmbEstados = new JComboBox();
		cmbEstados.setPreferredSize(new Dimension(170,30));
		cmbMunicipios = new JComboBox();
		cmbMunicipios.setPreferredSize(new Dimension(170,30));
		cmbCiudades = new JComboBox();
		cmbCiudades.setPreferredSize(new Dimension(170,30));
		
		try {
			estados = new RandomAccessFile( "estados.dat","rw");
			municipios = new RandomAccessFile( "municipios.dat","rw");
			ciudades = new RandomAccessFile( "ciudades.dat","rw");
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		if(orientacion==ORIENTACION_VERTICAL)
			this.setLayout(new GridLayout(3,1,5,5));
		
		createViews();
		addListeners();
		
		if(estado.equals("")) {
			loadStates();
			return;
		}
		
		cmbEstados.setSelectedItem(estado);
		restartCombo(cmbEstados);
		cmbEstados.addItem(estado);
		
		if(municipio.equals(""))
			return;
		cmbMunicipios.setSelectedItem(municipio);
		restartCombo(cmbMunicipios);
		cmbMunicipios.addItem(municipio);
	}
	
	private void createViews() {
		JPanel panelEstados = new JPanel();
		JPanel panelMunicipios = new JPanel();
		JPanel panelCiudades = new JPanel();
		panelEstados.add(new JLabel("      Estados:"));
		panelEstados.add(cmbEstados);
		panelMunicipios.add(new JLabel(" Municipios:"));
		panelMunicipios.add(cmbMunicipios);
		panelCiudades.add(new JLabel("    Ciudades:"));
		panelCiudades.add(cmbCiudades);
		
		add(panelEstados);
		add(panelMunicipios);
		add(panelCiudades);
	}
	
	private void loadStates() {
		try {
			cmbEstados.addItem(ESTADOS_DEFAULT);
			cmbEstados.setSelectedItem(ESTADOS_DEFAULT);
			int totalEstados = (int) (estados.length()/ESTADO_LENGTH); 
			for(int i = 0; i < totalEstados; i++) {
				estados.seek((i*ESTADO_LENGTH)+4);
				cmbEstados.addItem(estados.readUTF().trim());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void loadMunicipios(String idEstado) throws IOException {
		try{
			restartCombo(cmbMunicipios);
			int municipiosSize = (int) (municipios.length()/MUNICIPIO_LENGTH);
			String aux;
			boolean cutSearch = false;
			
			cmbMunicipios.addItem(MUNICIPIOS_DEFAULT);
			for(int i = 0; i < municipiosSize; i++) {
//				System.out.println(i+" | "+idEstado+" | "+municipiosSize);
				municipios.seek(i * MUNICIPIO_LENGTH);
				aux = municipios.readUTF();
				if(!aux.equals(idEstado)) {
					if(cutSearch)
						return;
					continue;
				}
				cutSearch = true;
				municipios.readUTF();
				cmbMunicipios.addItem(municipios.readUTF().trim());
			}
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void loadCities(String idEstado, String idMunicipio) {
		restartCombo(cmbCiudades);
		try{
			int ciudadesSize = (int) (ciudades.length()/CIUDAD_LENGTH);
			String auxEstado, auxMunicipio;
			boolean cutSearch = false;
			
			cmbCiudades.addItem(CIUDADES_DEFAULT);
			for(int i = 0; i < ciudadesSize; i++) {
				ciudades.seek(i *CIUDAD_LENGTH);
				auxEstado = ciudades.readUTF();
				auxMunicipio = ciudades.readUTF();
				if(!auxEstado.equals(idEstado) || !auxMunicipio.equals(idMunicipio) ) {
					if(cutSearch)
						return;
					continue;
				}
//				System.out.println(i+" | "+idEstado+" | "+idMunicipio);
				cutSearch = true;
				ciudades.readUTF();
				cmbCiudades.addItem(ciudades.readUTF().trim());
			}
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}	
	}
	
	private int search(RandomAccessFile file, String querry,int size) {
		try{
			int aux = (file == estados)? 4:(file == municipios)? 8:12;
			int fileSize = (int) (file.length()/size);
			for(int i = 0; i < fileSize; i++) {
				file.seek((i*size)+aux);
				if(file.readUTF().trim().equals(querry))
					return i+1;
			}
			return -1;
		}catch(Exception e) {return -1;}
	}
	
	private void addListeners() {
		cmbEstados.addItemListener(this);
		cmbMunicipios.addItemListener(this);
		cmbCiudades.addItemListener(this);
	}

	@Override
	public void itemStateChanged(ItemEvent evt) {
		if(evt.getStateChange() != ItemEvent.SELECTED)
			return;
		String id,selectedItem = (String) evt.getItem();
//		System.out.println(selectedItem);
		if(evt.getSource() == cmbEstados) {
			if(selectedItem == ESTADOS_DEFAULT) {
				restartCombo(cmbMunicipios);
				restartCombo(cmbCiudades);
				return;
			}
			int pos = this.search(estados, selectedItem, ESTADO_LENGTH);
			try{
				estados.seek((pos -1) * ESTADO_LENGTH);
				id = estados.readUTF();
				loadMunicipios(id);
			}catch(Exception e) {
				e.printStackTrace();
				return;
			}
		}
		if(evt.getSource() == cmbMunicipios) {
			if(selectedItem == MUNICIPIOS_DEFAULT) {
				restartCombo(cmbCiudades);
				return;
			}
			int posEst = this.search(estados, (String) cmbEstados.getSelectedItem(), ESTADO_LENGTH);
			int pos = this.search(municipios,selectedItem,MUNICIPIO_LENGTH);
			try{
				estados.seek((posEst -1) * ESTADO_LENGTH);
				String idEst = estados.readUTF();
				municipios.seek((pos -1) * MUNICIPIO_LENGTH);
				municipios.readUTF();
				id = municipios.readUTF();
				loadCities(idEst,id);
			}catch(Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}
	
	private void restartCombo(JComboBox cmb) {
		cmb.setModel(new DefaultComboBoxModel<String>(new String[0]));
	}
	
	public String getState() {
		return (String) cmbEstados.getSelectedItem();
	}
	
	public String getMunicipio() {
		return (String) cmbMunicipios.getSelectedItem();
	}
	
	public String getCity() {
		return(String) cmbCiudades.getSelectedItem();
	}
	
	public int getStateIndex() {
		return cmbEstados.getSelectedIndex();
	}
	
	public int getMunicipioIndex() {
		return cmbMunicipios.getSelectedIndex();
	}
	
	public int getCityIndex() {
		return cmbCiudades.getSelectedIndex();
	}
}
