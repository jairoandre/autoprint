package br.com.vah.autoprint.entities.dbamv;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Jairoportela on 21/09/2016.
 */
@Entity
@Table(name = "TB_SETOR", schema = "DBAMV")
public class Setor implements Serializable {

  @Id
  @Column(name = "CD_SETOR")
  private Long id;

  @Column(name = "NM_SETOR")
  private String nome;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }
}