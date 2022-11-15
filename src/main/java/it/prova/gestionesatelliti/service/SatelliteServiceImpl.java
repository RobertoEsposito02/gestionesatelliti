package it.prova.gestionesatelliti.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;
import it.prova.gestionesatelliti.repository.SatelliteRepository;

@Service
public class SatelliteServiceImpl implements SatelliteService{

	@Autowired
	private SatelliteRepository satelliteRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Satellite> listAll() {
		return (List<Satellite>) satelliteRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Satellite caricaSingoloElemento(Long id) {
		return satelliteRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void aggiorna(Satellite satelliteInstance) {
		satelliteRepository.save(satelliteInstance);
	}

	@Override
	@Transactional
	public void inserisciNuovo(Satellite satelliteInstance) {
		satelliteRepository.save(satelliteInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long id) {
		satelliteRepository.deleteById(id);
	}

	@Override
	public List<Satellite> findByExample(Satellite satellite) {
		Specification<Satellite> specificationCriteria = (root, query, cb) -> {
		List<Predicate> predicates = new ArrayList<Predicate>();		

		if(!satellite.getCodice().isEmpty())
			predicates.add(cb.like(cb.upper(root.get("codice")),"%" + satellite.getCodice().toUpperCase() + "%"));
		if(!satellite.getDenominazione().isEmpty())
			predicates.add(cb.like(cb.upper(root.get("denominazione")),"%" + satellite.getDenominazione() + "%"));
		if(satellite.getDataLancio() != null)
			predicates.add(cb.greaterThanOrEqualTo(root.get("dataLancio"), satellite.getDataLancio()));
		if(satellite.getDataRientro() != null)
			predicates.add(cb.greaterThanOrEqualTo(root.get("dataRientro"), satellite.getDataRientro()));
		if(satellite.getStato() != null)
			predicates.add(cb.equal(root.get("stato"), satellite.getStato()));
		
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		return satelliteRepository.findAll(specificationCriteria);
	}

	@Override
	public List<Satellite> cercaQuelliDisattivatiAncoraNonRientrati(StatoSatellite stato, Date dataRieentro) {
		return satelliteRepository.findByStatoAndDataRientro(stato, dataRieentro);
	}

	@Override
	public List<Satellite> cercaQuelliDaDueAnniInOrbitaNonDisattivati() {
		return satelliteRepository.findByYearDataRientroMinusCurdateGreaterThan();
	}

}
