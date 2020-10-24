package br.com.campanha.domain.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
/**
 * classe para mapeamento do banco
 * @author rodrigo
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "campanha")
public class CampanhaEntity {
	
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_campanha",unique = true)
    private Long idCampanha;
    
    @NonNull
    @Column(name = "nome_campanha", length = 150)
    private String nomeCampanha;
    
    @NonNull
    @Column(name = "id_time")
    private Long idTime;

    @NonNull
    @Column(name = "dt_vigencia_inicial")
    private LocalDate dtVigenciaInicial;

    @NonNull
    @Column(name = "dt_vigencia_final")
    private LocalDate dtVigenciaFinal;
    
    @Column(name = "dt_alteracao")
    private LocalDate dtAlteracao;

}
