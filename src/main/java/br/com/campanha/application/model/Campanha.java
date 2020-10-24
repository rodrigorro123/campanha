package br.com.campanha.application.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Campanha {

	@NonNull
   	private Long idCampanha;
    
    @NonNull
    private String nomeCampanha;
    
    @NonNull
    private Long idTime;

    @NonNull
    private LocalDate dtVigenciaInicial;

    @NonNull
    private LocalDate dtVigenciaFinal;
    
    private LocalDate dtAlteracao;
}
