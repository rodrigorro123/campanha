package br.com.campanha.application.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.apache.commons.beanutils.PropertyUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.campanha.application.exception.ApiException;
import br.com.campanha.application.model.Campanha;
import br.com.campanha.application.model.DadosResponse;
import br.com.campanha.application.service.ApiService;
import br.com.campanha.domain.entity.CampanhaEntity;
import br.com.campanha.domain.repository.CampanhaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
/**
 * classe principal para manipulaçao dos dados de campanha
 * @author rodrigo
 *
 */
@AllArgsConstructor
@Service
@Log4j2
public class ApiServiceImpl implements ApiService{

	private final CampanhaRepository repository;
	private final ModelMapper modelMapper;
	/**
	 * retorna todos os dados de camapnha ativa
	 */
	@Override
	public Campanha[] getAllDadosCampanha() throws ApiException {
		try {
		
			List<CampanhaEntity>  campanhas = repository.findByDtVigenciaFinalGreaterThanEqual(LocalDate.now()) ;

			if(campanhas.isEmpty()) {
	            throw ApiException
	            .builder()
	            .statusCode(HttpStatus.NOT_FOUND.value())
	            .code(ApiException.NOTFOUND_ERROR)
	            .message("Dado nao localizado na base")
	            .reason("Dado nao localizado")
	            .build();
			}
			return modelMapper.map(campanhas, Campanha[].class);
			
		}catch(ApiException ae) {
            throw ApiException
            .builder()
            .statusCode(ae.getStatusCode())
            .code(ae.getCode())
            .message(ae.getMessage())
            .reason(ae.getReason())
            .build();
		} catch (Exception e) {
			log.error(e.getMessage());
            throw ApiException
            .builder()
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .code(ApiException.GENERAL_ERROR)
            .message("Erro ao buscar dados")
            .reason("Erro ao buscar dados")
            .build();
		}
	}

	/**
	 * salva dados de campanha
	 */
	@Override
	public DadosResponse saveDadosCampanha(Campanha campanha) throws ApiException {
		try {
			DadosResponse response = new DadosResponse();
			
			CampanhaEntity entity = new CampanhaEntity();
			PropertyUtils.copyProperties(entity, campanha);
			
			List<CampanhaEntity> validaDados = repository
					.findByDtVigenciaFinalAndDtVigenciaInicialAndIdTime(
						campanha.getDtVigenciaFinal(),
						campanha.getDtVigenciaInicial(),
						campanha.getIdTime());
			
			if(!validaDados.isEmpty()) {
	            throw ApiException
	            .builder()
	            .statusCode(HttpStatus.CONFLICT.value())
	            .code(ApiException.VALIDATION_ERROR)
	            .message("Campanha cadastrada com mesmo periodo")
	            .reason("Campanha cadastrada com mesmo periodo")
	            .build();
			}
			
			validaPeriodoCampanha(entity);
			
			repository.save(entity);
			
			response.setCode(200);
			response.setMessage("Dados Salvo com Sucesso ");
			return response;
			
		}catch(ApiException ae) {
            throw ApiException
            .builder()
            .statusCode(ae.getStatusCode())
            .code(ae.getCode())
            .message(ae.getMessage())
            .reason(ae.getReason())
            .build();
		} catch (Exception e) {
			log.error(e.getMessage());
            throw ApiException
            .builder()
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .code(ApiException.GENERAL_ERROR)
            .message("Erro ao salvar dados campanha")
            .reason("Erro ao salvar dados")
            .build();
		}
	}
	
	/**
	 * valida campanha por periodo valido e time
	 * @param campanha
	 * @throws ApiException
	 */
	private void validaPeriodoCampanha(CampanhaEntity campanha) throws ApiException {
		try {

			List<CampanhaEntity> validaDados = repository
								.findByDtVigenciaFinalLessThanEqualAndDtVigenciaInicialGreaterThanEqualAndIdTime(
										campanha.getDtVigenciaFinal(),
										campanha.getDtVigenciaInicial(),
										campanha.getIdTime());

			if (!validaDados.isEmpty()) {
				for (CampanhaEntity campanhaEntity : validaDados) {
					addDayCampanha(campanhaEntity); //adiciona 1 dia a validade
				}
				validaPeriodoCampanha(campanha);//realiza a recursiva para eliminar as ocorrencias
			}
			 
			if(Objects.nonNull(campanha.getIdCampanha())) {
				List<CampanhaEntity>  dtFinal = repository
						.findByDtVigenciaFinalAndIdTimeAndIdCampanhaNot(
								campanha.getDtVigenciaFinal(),
								campanha.getIdTime(),
								campanha.getIdCampanha() );
				
				if(!dtFinal.isEmpty()) {
					for (CampanhaEntity campanhaDtFinal : dtFinal) {
						addDayCampanha(campanhaDtFinal);
					}
					validaPeriodoCampanha(campanha);
				}
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			throw ApiException.builder()
					.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.code(ApiException.GENERAL_ERROR)
					.message("Erro ao validar campanha")
					.reason("Erro ao validar campanha")
					.build();
		}
	}
	
	/**
	 * verifica o periodo da campanha no momento de atualização
	 * @param campanha
	 * @throws ApiException
	 */
	private void validaPeriodoCampanhaUpdt(CampanhaEntity campanha) throws ApiException {
		try {

			List<CampanhaEntity> validaDados = repository
								.findByDtVigenciaFinalLessThanEqualAndDtVigenciaInicialGreaterThanEqualAndIdTimeAndIdCampanhaNot(
										campanha.getDtVigenciaFinal(),
										campanha.getDtVigenciaInicial(),
										campanha.getIdTime(),
										campanha.getIdCampanha());

			if (!validaDados.isEmpty()) {
				for (CampanhaEntity campanhaEntity : validaDados) {
					addDayCampanha(campanhaEntity);
				}
				validaPeriodoCampanhaUpdt(campanha);
			}
			 
			if(Objects.nonNull(campanha.getIdCampanha())) {
				List<CampanhaEntity>  dtFinal = repository
						.findByDtVigenciaFinalAndIdTimeAndIdCampanhaNot(
								campanha.getDtVigenciaFinal(),
								campanha.getIdTime(),
								campanha.getIdCampanha() );
				
				if(!dtFinal.isEmpty()) {
					for (CampanhaEntity campanhaDtFinal : dtFinal) {
						addDayCampanha(campanhaDtFinal);
					}
					validaPeriodoCampanhaUpdt(campanha);
				}
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			throw ApiException.builder()
					.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.code(ApiException.GENERAL_ERROR)
					.message("Erro ao validar campanha")
					.reason("Erro ao validar campanha")
					.build();
		}
	}
	
	/**
	 * adiciona 1 dia a campanha
	 * @param campanha
	 * @throws ApiException
	 */
	private void addDayCampanha(CampanhaEntity campanha) throws ApiException  {
		
		try {
			campanha.setDtVigenciaFinal(campanha.getDtVigenciaFinal().plusDays(1));
			campanha.setDtAlteracao(LocalDate.now());
			repository.save(campanha);
			
			List<CampanhaEntity>  dtFinal = repository
					.findByDtVigenciaFinalAndIdTimeAndIdCampanhaNot(
							campanha.getDtVigenciaFinal(),
							campanha.getIdTime(),
							campanha.getIdCampanha() );
			
			if(!dtFinal.isEmpty()) {
				addDayCampanha(dtFinal.get(0));
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
            throw ApiException
            .builder()
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .code(ApiException.GENERAL_ERROR)
            .message("Erro ao adicionar data final campanha")
            .reason("Erro ao salvar dados")
            .build();
		}
	}
 
	/**
	 * apaga campanha por id
	 */
	public DadosResponse deleteCampanha(Long idCampanha) throws ApiException  {
		
		try {
			DadosResponse response = new DadosResponse();
			repository.deleteById(idCampanha);
			
			response.setCode(200);
			response.setMessage("Dado apagado com Sucesso ");
			return response;
			
		} catch (Exception e) {
			log.error(e.getMessage());
            throw ApiException
            .builder()
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .code(ApiException.GENERAL_ERROR)
            .message("Erro ao apagar campanha")
            .reason("Erro ao apagar dados")
            .build();
		}
	}
	
	/** 
	 * atualiza campanha
	 */
	public DadosResponse atualizarCampanha(Campanha campanha) throws ApiException{
		try {
			DadosResponse response = new DadosResponse();
			if(Objects.isNull(campanha.getIdCampanha())) {
				throw ApiException.builder()
				.statusCode(HttpStatus.PRECONDITION_REQUIRED.value())
				.code(ApiException.VALIDATION_ERROR)
				.message("Favor informar id da campanha")
				.reason("Campo obrigatorio não informado")
				.build();
			}
			
			if(!repository.existsById(campanha.getIdCampanha())) {
				throw ApiException.builder()
				.statusCode(HttpStatus.PRECONDITION_FAILED.value())
				.code(ApiException.VALIDATION_ERROR)
				.message("Verificar id da campanha nao localizado")
				.reason("Campo obrigatorio não informado corretamente")
				.build();
			}
		
			CampanhaEntity entity = new CampanhaEntity();
			PropertyUtils.copyProperties(entity, campanha);
			entity.setDtAlteracao(LocalDate.now());
			
			validaPeriodoCampanhaUpdt(entity);
			
			repository.save(entity);
			
			response.setCode(200);
			response.setMessage("Dados Atualizados com Sucesso ");
			return response;
			
		}catch(ApiException ae) {
            throw ApiException
            .builder()
            .statusCode(ae.getStatusCode())
            .code(ae.getCode())
            .message(ae.getMessage())
            .reason(ae.getReason())
            .build();
		} catch (Exception e) {
			log.error(e.getMessage());
            throw ApiException
            .builder()
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .code(ApiException.GENERAL_ERROR)
            .message("Erro ao apagar campanha")
            .reason("Erro ao apagar dados")
            .build();
		}
	}

	/**
	 * busca campanha com data de vigencia por time
	 */
	@Override
	public List<CampanhaEntity> getByDtVigenciaFinalGreaterThanEqualAndIdTime(LocalDate dtVigenciaFinal, Long idTime)
			throws ApiException {
		try {
			
			return repository.findByDtVigenciaFinalGreaterThanEqualAndIdTime( dtVigenciaFinal,  idTime);
		
		}  catch (Exception e) {
			log.error(e.getMessage());
            throw ApiException
            .builder()
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .code(ApiException.GENERAL_ERROR)
            .message("Erro buscar dados campanha por time e dt vigencia ativa")
            .reason("Erro ao buscar dados")
            .build();
		}
	}

	/**
	 * retorna campanha ativa por time e id
	 */
	@Override
	public List<CampanhaEntity> getByDtVigenciaFinalGreaterThanEqualAndIdTimeAndIdCampanha(LocalDate dtVigenciaFinal,
			Long idTime, Long idCampanha) throws ApiException {
		try {
			
			return  repository.findByDtVigenciaFinalGreaterThanEqualAndIdTimeAndIdCampanha( dtVigenciaFinal,  idTime,  idCampanha);
		}  catch (Exception e) {
			log.error(e.getMessage());
            throw ApiException
            .builder()
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .code(ApiException.GENERAL_ERROR)
            .message("Erro buscar dados campanha por time, id campanha e dt vigencia ativa")
            .reason("Erro ao buscar dados")
            .build();
		}
	}
 
}
