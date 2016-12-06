package br.com.vah.autoprint.entities.dbamv;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jairoportela on 21/09/2016.
 */
@Entity
@Table(name = "TB_SOLICITACAO_OS", schema = "DBAMV")
public class Solicitacao implements Serializable {

  @Id
  @Column(name = "CD_OS")
  private Long id;

  @Column(name = "DS_SERVICO")
  private String descricao;

  @Column(name = "NM_SOLICITANTE")
  private String solicitante;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "DT_PEDIDO")
  private Date dataPedido;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "DT_EXECUCAO")
  private Date dataExecucao;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CD_MOT_SERV")
  private MotivoServico motivo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CD_SETOR")
  private Setor setor;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CD_LOCALIDADE")
  private Localidade localidade;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CD_BEM")
  private Bens bens;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CD_TIPO_OS")
  private TipoSolicitacao tipo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CD_ESPEC")
  private Manutencao manutencao;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CD_OFICINA")
  private Oficina oficina;

  @Transient
  private String observacao;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getSolicitante() {
    return solicitante;
  }

  public void setSolicitante(String solicitante) {
    this.solicitante = solicitante;
  }

  public Date getDataPedido() {
    return dataPedido;
  }

  public void setDataPedido(Date dataPedido) {
    this.dataPedido = dataPedido;
  }

  public Date getDataExecucao() {
    return dataExecucao;
  }

  public void setDataExecucao(Date dataExecucao) {
    this.dataExecucao = dataExecucao;
  }

  public String getObservacao() {
    return observacao;
  }

  public void setObservacao(String observacao) {
    this.observacao = observacao;
  }

  public MotivoServico getMotivo() {
    return motivo;
  }

  public void setMotivo(MotivoServico motivo) {
    this.motivo = motivo;
  }

  public Setor getSetor() {
    return setor;
  }

  public void setSetor(Setor setor) {
    this.setor = setor;
  }

  public Localidade getLocalidade() {
    return localidade;
  }

  public void setLocalidade(Localidade localidade) {
    this.localidade = localidade;
  }

  public Bens getBens() {
    return bens;
  }

  public void setBens(Bens bens) {
    this.bens = bens;
  }

  public TipoSolicitacao getTipo() {
    return tipo;
  }

  public void setTipo(TipoSolicitacao tipo) {
    this.tipo = tipo;
  }

  public Manutencao getManutencao() {
    return manutencao;
  }

  public void setManutencao(Manutencao manutencao) {
    this.manutencao = manutencao;
  }

  public Oficina getOficina() {
    return oficina;
  }

  public void setOficina(Oficina oficina) {
    this.oficina = oficina;
  }
}
