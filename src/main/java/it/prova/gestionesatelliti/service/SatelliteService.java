package it.prova.gestionesatelliti.service;

import java.util.List;

import it.prova.gestionesatelliti.model.Satellite;

public interface SatelliteService {
	public List<Satellite> listAll();
	
	public Satellite findById(Long id);
	
	public void aggiorna(Satellite satelliteInstance);

	public void inserisciNuovo(Satellite satelliteInstance);

	public void rimuovi(Long id);
	
	public List<Satellite> findByExample(Satellite satellite);
}
