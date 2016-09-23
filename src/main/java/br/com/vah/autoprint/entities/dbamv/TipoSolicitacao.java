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
@Table(name = "TIPO_OS", schema = "DBAMV")
public class TipoSolicitacao implements Serializable {

  @Id
  @Column(name = "CD_TIPO_OS")
  private Long id;

  @Column(name = "DS_TIPO_OS")
  private String nome;

  @Column(name = "TP_PRELIMINAR")
  private String preliminar;

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

  public String getPreliminar() {
    return preliminar;
  }

  public void setPreliminar(String preliminar) {
    this.preliminar = preliminar;
  }
}
