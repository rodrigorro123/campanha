package br.com.campanha.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.campanha.domain.entity.CampanhaEntity;
/**
 * interface que realiza comunicaçao com a base de dados
 * @author rodrigo
 *
 */
public interface CampanhaRepository extends CrudRepository<CampanhaEntity, Long> {
	
	
	List<CampanhaEntity> findByDtVigenciaFinalGreaterThanEqual(LocalDate dtVigenciaFinal);
	
	List<CampanhaEntity> findByDtVigenciaFinalAndDtVigenciaInicialAndIdTime(LocalDate dtVigenciaFinal, LocalDate dtVigenciaInicial, Long idTime);
	
	List<CampanhaEntity> findByDtVigenciaFinalLessThanEqualAndDtVigenciaInicialGreaterThanEqualAndIdTime(LocalDate dtVigenciaFinal, LocalDate dtVigenciaInicial, Long idTime);
	List<CampanhaEntity> findByDtVigenciaFinalLessThanEqualAndDtVigenciaInicialGreaterThanEqualAndIdTimeAndIdCampanhaNot(LocalDate dtVigenciaFinal, LocalDate dtVigenciaInicial, Long idTime, Long idCampanha);

	List<CampanhaEntity> findByDtVigenciaFinalAndIdTimeAndIdCampanhaNot(LocalDate dtVigenciaFinal, Long idTime, Long idCampanha);
	
	//
	List<CampanhaEntity> findByDtVigenciaFinalGreaterThanEqualAndIdTimeAndIdCampanha(LocalDate dtVigenciaFinal, Long idTime, Long idCampanha);
	List<CampanhaEntity> findByDtVigenciaFinalGreaterThanEqualAndIdTime(LocalDate dtVigenciaFinal, Long idTime);
}
