package it.prova.gestionesatelliti.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;

public interface SatelliteRepository extends CrudRepository<Satellite, Long>, JpaSpecificationExecutor<Satellite>{
	
	@Query("from Satellite s where not s.stato = 'DISABILITATO' and year(curdate())-year(s.dataLancio) > 2")
	public List<Satellite> findByYearDataRientroMinusCurdateGreaterThan();
	
	public List<Satellite> findByStatoAndDataRientro(StatoSatellite stato, Date dataRientro);
	
	@Query("from Satellite s where s.stato = 'FISSO' and year(curdate())-year(s.dataLancio) >= 10 ")
	public List<Satellite> findByDataLancioGretherThan10AndStatoFisso();
	
	@Query("from Satellite s where not s.stato = 'DISABILITATO' and (s.dataRientro = null or s.dataRientro > curdate()) ")
	public List<Satellite> findByDataRitornoNullAndStatoNotDisabilitato();
}
