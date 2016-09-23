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
@Table(name = "LOCALIDADE", schema = "DBAMV")
public class Localidade implements Serializable {

  @Id
  @Column(name = "CD_LOCALIDADE")
  private Long id;

  @Column(name = "DS_LOCALIDADE")
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
