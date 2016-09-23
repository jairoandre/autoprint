package br.com.vah.autoprint.dtos;

import br.com.vah.autoprint.entities.dbamv.Solicitacao;

import java.text.SimpleDateFormat;

/**
 * Created by Jairoportela on 21/09/2016.
 */
public class SolicitacaoDTO {

  private String cdOs = "-";
  private String tipoOs = "-";
  private String solicitante = "-";
  private String setor = "-";
  private String bem = "-";
  private String codigoBem = "-";
  private String servico = "-";
  private String motivoServico = "-";
  private String oficina = "-";
  private String observacao = "-";
  private String especialidade = "-";
  private String dataOs = "-";
  private String localidade = "-";
  private String preventiva = "-";

  public SolicitacaoDTO(Solicitacao solicitacao) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    cdOs = solicitacao.getId().toString();
    solicitante = solicitacao.getSolicitante();
    servico = solicitacao.getDescricao();
    if (solicitacao.getObservacao() != null) {
      observacao = solicitacao.getObservacao();
    }
    dataOs = sdf.format(solicitacao.getDataPedido());
    tipoOs = solicitacao.getTipo().getNome();
    setor = solicitacao.getSetor().getNome();
    if (solicitacao.getBens() != null) {
      bem = solicitacao.getBens().getNome();
      codigoBem = solicitacao.getBens().getId().toString();
    }
    if (solicitacao.getMotivo() != null) {
      motivoServico = solicitacao.getMotivo().getNome();
    }
    if (solicitacao.getOficina() != null) {
      oficina = solicitacao.getOficina().getNome();
    }
    if (solicitacao.getManutencao() != null) {
      especialidade = solicitacao.getManutencao().getNome();
    }
    if (solicitacao.getLocalidade() != null) {
      localidade = solicitacao.getLocalidade().getNome();
    }
    if (solicitacao.getTipo() != null) {
      switch (solicitacao.getTipo().getPreliminar()) {
        case "P":
          preventiva = "Preventiva";
          break;
        case "C":
          preventiva = "Corretiva";
          break;
        case "R":
          preventiva = "Rotina";
          break;
        case "J":
          preventiva = "Projetos";
          break;
        case "I":
          preventiva = "Instalação";
          break;
        default:
          preventiva = "N/A";
          break;
      }
    }

  }

  public String getTipoOs() {
    return tipoOs;
  }

  public String getSolicitante() {
    return solicitante;
  }

  public String getSetor() {
    return setor;
  }

  public String getBem() {
    return bem;
  }

  public String getCodigoBem() {
    return codigoBem;
  }

  public String getServico() {
    return servico;
  }

  public String getMotivoServico() {
    return motivoServico;
  }

  public String getOficina() {
    return oficina;
  }

  public String getObservacao() {
    return observacao;
  }

  public String getEspecialidade() {
    return especialidade;
  }

  public String getDataOs() {
    return dataOs;
  }

  public String getLocalidade() {
    return localidade;
  }

  public String getCdOs() {
    return cdOs;
  }

  public String getPreventiva() {
    return preventiva;
  }
}
