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
@Table(name = "TB_CONTROLE_IMPR_OS", schema="USRDBVAH")
public class ControleImpressaoOS implements Serializable {
  @Id
  @Column(name = "CD_OS")
  private Long id;

  @Column(name = "DT_IMPRESSAO")
  private Date dataImpressao;

  @Column(name = "DT_ENVIO_EMAIL")
  private Date dataEmail;

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

  public Date getDataEmail() {
    return dataEmail;
  }

  public void setDataEmail(Date dataEmail) {
    this.dataEmail = dataEmail;
  }
}
