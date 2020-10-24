package br.com.campanha.application.service;

import java.time.LocalDate;
import java.util.List;

import br.com.campanha.application.exception.ApiException;
import br.com.campanha.application.model.Campanha;
import br.com.campanha.application.model.DadosResponse;
import br.com.campanha.domain.entity.CampanhaEntity;
/**
 * interface da service
 * @author rodrigo
 *
 */
public interface ApiService {
	
	Campanha[] getAllDadosCampanha() throws ApiException;
	DadosResponse saveDadosCampanha(Campanha campanha) throws ApiException;
	DadosResponse deleteCampanha(Long idCampanha) throws ApiException;
	DadosResponse atualizarCampanha(Campanha campanha) throws ApiException;
	
	List<CampanhaEntity> getByDtVigenciaFinalGreaterThanEqualAndIdTime(LocalDate dtVigenciaFinal, Long idTime) throws ApiException;
	List<CampanhaEntity> getByDtVigenciaFinalGreaterThanEqualAndIdTimeAndIdCampanha(LocalDate dtVigenciaFinal, Long idTime, Long idCampanha) throws ApiException;
	

}
