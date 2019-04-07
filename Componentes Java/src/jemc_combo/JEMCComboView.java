package jemc_combo;

import java.awt.event.ItemEvent;
import java.awt.event.*;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.*;

public class JEMCComboView extends JPanel implements ItemListener{	

	private final static int ESTADO_LENGTH = 56;
	private final static int MUNICIPIO_LENGTH = 60;
	private final static int CIUDAD_LENGTH = 21;
	private final static String ESTADOS_DEFAULT = "Seleccione un Estado";
	private final static String MUNICIPIOS_DEFAULT = "Seleccione un Municipio";
	private final static String CIUDADES_DEFAULT = "Seleccione una Ciudad";
	
//	private final static int ESTADO_INDEX_LENGTH = 60;
//	private final static int MUNICIPIO_INDEX_LENGTH = 60;
//	private final static int CIUDAD_INDEX_LENGTH = 17;
	
	private JComboBox cmbEstados,cmbMunicipios,cmbCiudades;
	private RandomAccessFile estados,municipios,ciudades;
	
	public JEMCComboView() {
		cmbEstados = new JComboBox();
		cmbMunicipios = new JComboBox();
		cmbCiudades = new JComboBox();
		
		try {
			estados = new RandomAccessFile( "estados.dat","rw");
			municipios = new RandomAccessFile( "municipios.dat","rw");
			ciudades = new RandomAccessFile( "ciudades.dat","rw");
		}catch(Exception e) {e.printStackTrace();}
		
		add(new JLabel("Estados:"));
		add(cmbEstados);
		add(new JLabel("Municipios:"));
		add(cmbMunicipios);
		add(new JLabel("Ciudades:"));
		add(cmbCiudades);
		
		addListeners();
		loadStates();
	}
	
	private void loadStates() {
		try {
			//cmbEstados.addItem("Seleccionar Estado");
			cmbEstados.addItem(ESTADOS_DEFAULT);
			cmbEstados.setSelectedItem(ESTADOS_DEFAULT);
			int totalEstados = (int) (estados.length()/ESTADO_LENGTH); 
			for(int i = 0; i < totalEstados; i++) {
				estados.seek((i*ESTADO_LENGTH)+4);
				cmbEstados.addItem(estados.readUTF().trim());
			}
			
		}catch(Exception e) {}
	}
	
	private int busquedaBinaria(RandomAccessFile indices, String querry,int size) {
		try {
			String current;
			int largo = (int) (indices.length()/ size),inferior =1, mitad, superior = largo;
			while(inferior <= superior) {
				mitad = (inferior + superior)/2;
				System.out.println(largo+" "+inferior+" "+superior+" "+mitad);
				indices.seek((mitad - 1)*size);
				current = indices.readUTF();
				if(querry.compareTo(current) == 0)
					return mitad;
				if(current.compareTo(querry) > 0)
					superior = mitad - 1;
				else
					inferior = mitad + 1;
			}
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
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
		}catch(Exception E) {E.printStackTrace();}
	}
	
	private void loadCities(String idMunicipio) {
		try{
			int ciudadesSize = (int) (ciudades.length()/CIUDAD_LENGTH);
			String aux;
			boolean cutSearch = false;
			
			cmbCiudades.addItem(CIUDADES_DEFAULT);
			for(int i = 0; i < ciudadesSize; i++) {
				ciudades.seek(i *CIUDAD_LENGTH);
				ciudades.readUTF();
				aux = ciudades.readUTF();
				if(!aux.equals(idMunicipio)) {
					if(cutSearch)
						return;
					continue;
				}
				System.out.println(i+" | "+idMunicipio);
				cutSearch = true;
				ciudades.readUTF();
				cmbCiudades.addItem(ciudades.readUTF().trim());
			}
		}catch(Exception E) {E.printStackTrace();}		
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
			}catch(Exception e) {e.printStackTrace();}
		}
		if(evt.getSource() == cmbMunicipios) {
			if(selectedItem == MUNICIPIOS_DEFAULT) {
				restartCombo(cmbCiudades);
				return;
			}
			int pos = this.search(municipios,selectedItem,MUNICIPIO_LENGTH);
			try{
				municipios.seek((pos -1) * MUNICIPIO_LENGTH);
				municipios.readUTF();
				id = municipios.readUTF();
				loadCities(id);
			}catch(Exception e) {e.printStackTrace();}
		}
	}
	
	private void restartCombo(JComboBox cmb) {
		cmb.setModel(new DefaultComboBoxModel<String>(new String[0]));
//		String item = (cmb == cmbMunicipios)? MUNICIPIOS_DEFAULT:CIUDADES_DEFAULT;
//		cmb.addItem(item);
	}
	
}
