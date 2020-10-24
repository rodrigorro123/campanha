package br.com.campanha.domain.entity;

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
@Entity(name = "time")
public class TimeEntity {
	
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_time",unique = true)
    private Integer idTime;
    
    @NonNull
    @Column(name = "nome_time", length = 250)
    private String nomeTime;
    
}
