package br.com.vah.autoprint.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jairoportela on 20/09/2016.
 */
@Entity
@Table(name = "TB_CONTROLE_IMPR_OS_PREV", schema="USRDBVAH")
public class ControleImpressaoPrevisaoOS implements Serializable {

  @Id
  @Column(name = "CD_OS")
  private Long id;

  @Column(name = "DT_IMPRESSAO")
  private Date dataImpressao;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getDataImpressao() {
    return dataImpressao;
  }

  public void setDataImpressao(Date dataImpressao) {
    this.dataImpressao = dataImpressao;
  }
}