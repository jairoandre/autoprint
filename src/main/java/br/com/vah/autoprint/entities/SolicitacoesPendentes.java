package br.com.vah.autoprint.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Jairoportela on 20/09/2016.
 */
@Entity
@Table(name = "VW_SOLICITACOES_PENDENTES", schema = "USRDBVAH")
public class SolicitacoesPendentes implements Serializable {

  @Id
  @Column(name = "CD_OS")
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
