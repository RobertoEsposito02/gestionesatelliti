package it.prova.gestionesatelliti.service;

import java.util.Date;
import java.util.List;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;

public interface SatelliteService {
	public List<Satellite> listAll();
	
	public Satellite caricaSingoloElemento(Long id);
	
	public void aggiorna(Satellite satelliteInstance);

	public void inserisciNuovo(Satellite satelliteInstance);

	public void rimuovi(Long id);
	
	public List<Satellite> findByExample(Satellite satellite);
	
	public List<Satellite> cercaQuelliDaDueAnniInOrbitaNonDisattivati();	
	
	public List<Satellite> cercaQuelliDisattivatiAncoraNonRientrati(StatoSatellite stato,Date dataRieentro);
	
	public List<Satellite> cercaQuelliDaAlmenoDieciAnniInOrbitaCheOraSonoFissi();
	
	public List<Satellite> cercaQuelliNonDisabilitatiAncoraNonRientrati();
}
