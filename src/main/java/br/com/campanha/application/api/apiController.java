package br.com.campanha.application.api;


import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.campanha.application.exception.ApiException;
import br.com.campanha.application.model.Campanha;
import br.com.campanha.application.model.DadosResponse;
import br.com.campanha.application.model.ErrorResponse;
import br.com.campanha.application.service.ApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Api(value = "API Socio Torcedor")
@RestController
@RequiredArgsConstructor
@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Api executada com sucesso"),
		@ApiResponse(code = 202, message = "Registro processado com sucesso"),
		@ApiResponse(code = 401, message = "Você não está autorizado a visualizar o recurso"),
	    @ApiResponse(code = 403, message = "É proibido acessar o recurso que você estava tentando acessar"),
	    @ApiResponse(code = 404, message = "O recurso que você estava tentando acessar não foi encontrado"),
	    @ApiResponse(code = 500, message = "Erro interno")})
@RequestMapping("/campanha")
public class apiController {
	
	private final ApiService service;
	 
	@ApiOperation(value = "Metodo para buscar todas campanhas")
	@RequestMapping(value = "",
    produces = { "application/json" },
    method = RequestMethod.GET)
	  public ResponseEntity<?> getAllCampanhas() {
	    try {

	      return ResponseEntity.ok(service.getAllDadosCampanha());
	      
	    }catch (ApiException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(ErrorResponse
                            .builder()
                            .code(e.getCode())
                            .description(e.getReason())
                            .message(e.getMessage())
                            .build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse
                            .builder()
                            .code("Internal Error")
                            .description(e.getMessage())
                            .message(e.getMessage())
                            .build());
        } 
	  }
	
	@ApiOperation(value = "Metodo para salvar campanha")
	@RequestMapping(value = "",
		    produces = { "application/json" },
		    method = RequestMethod.POST)
			  public ResponseEntity<?> saveCampanhas(@RequestBody Campanha campanha) {
			    try {

			    	DadosResponse result = service.saveDadosCampanha(campanha);
			    	return ResponseEntity.status(result.getCode()).body(result);
			      
			    }catch (ApiException e) {
		            return ResponseEntity
		                    .status(e.getStatusCode())
		                    .body(ErrorResponse
		                            .builder()
		                            .code(e.getCode())
		                            .description(e.getReason())
		                            .message(e.getMessage())
		                            .build());
		        } catch (RuntimeException e) {
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                    .body(ErrorResponse
		                            .builder()
		                            .code("Internal Error")
		                            .description(e.getMessage())
		                            .message(e.getMessage())
		                            .build());
		        } 
	 }
	
	@ApiOperation(value = "Metodo para atualizar campanhas")
	@RequestMapping(value = "",
		    produces = { "application/json" },
		    method = RequestMethod.PATCH)
			  public ResponseEntity<?> atualizarCampanhas(@RequestBody Campanha campanha) {
			    try {

			    	DadosResponse result = service.atualizarCampanha(campanha);
			    	return ResponseEntity.status(result.getCode()).body(result);
			      
			    }catch (ApiException e) {
		            return ResponseEntity
		                    .status(e.getStatusCode())
		                    .body(ErrorResponse
		                            .builder()
		                            .code(e.getCode())
		                            .description(e.getReason())
		                            .message(e.getMessage())
		                            .build());
		        } catch (RuntimeException e) {
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                    .body(ErrorResponse
		                            .builder()
		                            .code("Internal Error")
		                            .description(e.getMessage())
		                            .message(e.getMessage())
		                            .build());
		        } 
	 }
	
	@ApiOperation(value = "Metodo para apagar campanha")
	@RequestMapping(value = "/{idCampanha}",
		    produces = { "application/json" },
		    method = RequestMethod.DELETE)
			  public ResponseEntity<?> deleteCampanhas(@PathVariable("idCampanha") @NotNull Long idCampanha) {
			    try {

			    	DadosResponse  result = service.deleteCampanha(idCampanha);
			    	return ResponseEntity.status(result.getCode()).body(result);
			      
			    }catch (ApiException e) {
		            return ResponseEntity
		                    .status(e.getStatusCode())
		                    .body(ErrorResponse
		                            .builder()
		                            .code(e.getCode())
		                            .description(e.getReason())
		                            .message(e.getMessage())
		                            .build());
		        } catch (RuntimeException e) {
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                    .body(ErrorResponse
		                            .builder()
		                            .code("Internal Error")
		                            .description(e.getMessage())
		                            .message(e.getMessage())
		                            .build());
		        } 
			  }
	
	@ApiOperation(value = "Metodo para listar campanha ativa por time")
	@RequestMapping(value = "/vigenciawithtime",
		    produces = { "application/json" },
		    method = RequestMethod.GET)
			  public ResponseEntity<?> getByDtVigenciaFinalGreaterThanEqualAndIdTime(
					  @RequestParam(name = "dtVigenciaFinal",required = true)  LocalDate dtVigenciaFinal,
					  @RequestParam(name = "idTime",required = true) Long idTime ) {
			    try {

			      return ResponseEntity.ok(service.getByDtVigenciaFinalGreaterThanEqualAndIdTime(dtVigenciaFinal, idTime) );
			      
			    }catch (ApiException e) {
		            return ResponseEntity
		                    .status(e.getStatusCode())
		                    .body(ErrorResponse
		                            .builder()
		                            .code(e.getCode())
		                            .description(e.getReason())
		                            .message(e.getMessage())
		                            .build());
		        } catch (RuntimeException e) {
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                    .body(ErrorResponse
		                            .builder()
		                            .code("Internal Error")
		                            .description(e.getMessage())
		                            .message(e.getMessage())
		                            .build());
		        } 
			  }	
	
	@ApiOperation(value = "Metodo para validar campanha ativa por time")
	@RequestMapping(value = "/vigenciawithtimecampanha",
		    produces = { "application/json" },
		    method = RequestMethod.GET)
			  public ResponseEntity<?> getByDtVigenciaFinalGreaterThanEqualAndIdTimeAndIdCampanha(
					  @RequestParam(name = "dtVigenciaFinal",required = true)  LocalDate dtVigenciaFinal,
					  @RequestParam(name = "idTime",required = true) Long idTime ,
					  @RequestParam(name = "idCampanha",required = true) Long idCampanha ) {
			    try {

			      return ResponseEntity.ok(service.getByDtVigenciaFinalGreaterThanEqualAndIdTimeAndIdCampanha(dtVigenciaFinal, idTime, idCampanha) );
			      
			    }catch (ApiException e) {
		            return ResponseEntity
		                    .status(e.getStatusCode())
		                    .body(ErrorResponse
		                            .builder()
		                            .code(e.getCode())
		                            .description(e.getReason())
		                            .message(e.getMessage())
		                            .build());
		        } catch (RuntimeException e) {
		            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                    .body(ErrorResponse
		                            .builder()
		                            .code("Internal Error")
		                            .description(e.getMessage())
		                            .message(e.getMessage())
		                            .build());
		        } 
			  }	
}
