package br.com.vah.autoprint;

import br.com.vah.autoprint.dtos.SolicitacaoDTO;
import br.com.vah.autoprint.entities.ControleImpressaoOS;
import br.com.vah.autoprint.entities.ControleImpressaoPrevisaoOS;
import br.com.vah.autoprint.entities.dbamv.Solicitacao;
import br.com.vah.autoprint.report.ReportLoader;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Jairoportela on 20/09/2016.
 */
public class Main implements Runnable {

  private Properties props;

  private int count;

  Main(int count, Properties props) {
    this.props = props;
    this.count = count;
    System.out.println("Impressão OS - Ciclo " + count);
    Thread handler = new Thread(this, "autoprint-" + count);
    handler.start();
  }

  private Properties toDbProperties(Properties props) {
    Properties dbProps = new Properties();
    String url = props.getProperty("url");
    String user = props.getProperty("user");
    String password = props.getProperty("password");
    dbProps.put("javax.persistence.jdbc.url", url);
    dbProps.put("javax.persistence.jdbc.user", user);
    dbProps.put("javax.persistence.jdbc.password", password);
    return dbProps;
  }

  private PrintService loadPrintService(String print) {
    PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
    for (PrintService printService : printServices) {
      if (printService.getName().equals(print)) {
        return printService;
      }
    }
    for (PrintService printService : printServices) {
      if (printService.getName().contains(print)) {
        return printService;
      }
    }
    return null;
  }

  private String loadObservacao(Long cdOs, Session session) {
    String sql = "SELECT DS_OBSERVACAO FROM DBAMV.SOLICITACAO_OS WHERE CD_OS = " + cdOs;
    SQLQuery query = session.createSQLQuery(sql);
    query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
    Map<String, Object> map = (Map<String, Object>) query.uniqueResult();
    return (String) map.get("DS_OBSERVACAO");
  }

  private List<Solicitacao> getSolicitacoes(Session session) {
    Criteria crit = session.createCriteria(Solicitacao.class);

    DetachedCriteria dc = DetachedCriteria.forClass(ControleImpressaoOS.class);
    dc.setProjection(Property.forName("id"));
    crit.add(Subqueries.propertyNotIn("id", dc));

    //crit.add(Restrictions.between("dataPedido", yesterday(), today()));

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt_BR"));


    try {
      Date data = sdf.parse("01/05/2017");
      crit.add(Restrictions.eq("dataPedido", data));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    //crit.createAlias("tipo", "t").add(Restrictions.ne("t.preliminar", "P"));
    //crit.createAlias("oficina", "o").add(Restrictions.not(Restrictions.in("o.id", 26l, 4l)));
    //crit.createAlias("bens", "b", JoinType.LEFT_OUTER_JOIN);

    return (List<Solicitacao>) crit.list();
  }

  private Date yesterday() {
    Calendar cld = Calendar.getInstance();
    cld.setTime(new Date());
    cld.add(Calendar.DAY_OF_MONTH, -2);
    cld.set(Calendar.HOUR_OF_DAY, 23);
    cld.set(Calendar.MINUTE, 59);
    cld.set(Calendar.SECOND, 59);
    return cld.getTime();
  }

  private Date today() {
    Calendar cld = Calendar.getInstance();
    cld.setTime(new Date());
    cld.set(Calendar.HOUR_OF_DAY, 23);
    cld.set(Calendar.MINUTE, 59);
    cld.set(Calendar.SECOND, 59);
    return cld.getTime();
  }

  private List<Solicitacao> getSolicitacoesPreventiva(Session session) {
    Criteria crit = session.createCriteria(Solicitacao.class);

    DetachedCriteria dc = DetachedCriteria.forClass(ControleImpressaoPrevisaoOS.class);
    dc.setProjection(Property.forName("id"));
    crit.add(Subqueries.propertyNotIn("id", dc));

    crit.add(Restrictions.le("dataPedido", today()));
    crit.add(Restrictions.isNull("dataExecucao"));

    crit.createAlias("tipo", "t").add(Restrictions.eq("t.preliminar", "P"));
    Disjunction disj = Restrictions.disjunction();
    disj.add(Restrictions.not(Restrictions.in("o.id", 26l, 4l)));
    disj.add(Restrictions.isNull("oficina"));
    crit.createAlias("oficina", "o", JoinType.LEFT_OUTER_JOIN).add(disj);
    crit.createAlias("bens", "b", JoinType.LEFT_OUTER_JOIN);

    return (List<Solicitacao>) crit.list();
  }

  private ControleImpressaoOS toControleImpressao(Solicitacao solicitacao) {
    ControleImpressaoOS controle = new ControleImpressaoOS();
    controle.setId(solicitacao.getId());
    controle.setDataImpressao(new Date());
    return controle;
  }

  private ControleImpressaoPrevisaoOS toControleImpressaoPrev(Solicitacao solicitacao) {
    ControleImpressaoPrevisaoOS controle = new ControleImpressaoPrevisaoOS();
    controle.setId(solicitacao.getId());
    controle.setDataImpressao(new Date());
    return controle;
  }


  public void run() {

    Properties dbProps = toDbProperties(props);

    String termoImpressora = props.getProperty("print");

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    /*
    PrintService printer = loadPrintService(props.getProperty("print"));

    if (printer == null) {
      System.out.println(sdf.format(new Date()) + " -> Impressora não encontrada: " + termoImpressora);
      return;
    }*/

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence", dbProps);
    EntityManager em = emf.createEntityManager();
    EntityTransaction et = em.getTransaction();

    try {
      et.begin();
      Session session = (Session) em.getDelegate();

      List<Solicitacao> solicitacoes = getSolicitacoes(session);
      List<Solicitacao> solicitacoesPrev = new ArrayList<>();// getSolicitacoesPreventiva(session);

      List<SolicitacaoDTO> solicitacoesToPrint = new ArrayList<>();
      List<ControleImpressaoOS> controleToRegister = new ArrayList<>();
      List<ControleImpressaoPrevisaoOS> controlePrevToRegister = new ArrayList<>();

      int documentos = 1;

      // Solicitações
      for (Solicitacao solicitacao : solicitacoes) {
//        if (documentos++ > 5) {
//          break;
//        }
        solicitacao.setObservacao(loadObservacao(solicitacao.getId(), session));
        solicitacoesToPrint.add(new SolicitacaoDTO(solicitacao));
        // controleToRegister.add(toControleImpressao(solicitacao));
      }

      documentos = 1;

      // Solicitações Previstas
      for (Solicitacao solicitacao : solicitacoesPrev) {
        if (documentos++ > 5) {
          break;
        }
        solicitacao.setObservacao(loadObservacao(solicitacao.getId(), session));
        solicitacoesToPrint.add(new SolicitacaoDTO(solicitacao));
        controlePrevToRegister.add(toControleImpressaoPrev(solicitacao));
      }

      if (!solicitacoesToPrint.isEmpty()) {

        solicitacoesToPrint.forEach((s) -> {
          List<SolicitacaoDTO> sol = new ArrayList<SolicitacaoDTO>();
          sol.add(s);
          try {
            ReportLoader.fillReport("/ordem.jasper", sol, null);
          } catch (Exception e) {

          }
        });
//        System.out.println(sdf.format(new Date()) + " -> Imprimindo " + solicitacoesToPrint.size() + " documento(s).");
//
//        Boolean gravou = false;
//
//        for (ControleImpressaoOS cOs : controleToRegister) {
//          em.persist(cOs);
//          gravou = true;
//        }
//
//        for (ControleImpressaoPrevisaoOS cOs : controlePrevToRegister) {
//          em.persist(cOs);
//          gravou = true;
//        }
//
//        if (gravou) {
//          et.commit();
//        }
      }

    } catch (Exception e) {
      System.out.println(String.format("%s -> Erro inesperado: %s", sdf.format(new Date()), e.getMessage()));
    } finally {
      em.close();
      emf.close();
    }
  }

  private static Properties loadProperties(String[] args) {
    try {
      String fileName = "config.properties";
      if (args != null && args.length > 1) {
        fileName = args[0];
      }
      Properties prop = new Properties();
      InputStream is = new FileInputStream(fileName);
      prop.load(is);
      is.close();
      return prop;
    } catch (Exception e) {
      Properties prop = new Properties();
      prop.put("print", "T.I - Brother_HL-6180DW");
      //prop.put("url", "jdbc:oracle:thin:@10.1.0.235:1521:simula");
      prop.put("url", "jdbc:oracle:thin:@10.1.0.235:1521:treinamento");
      prop.put("user", "usrdbvah");
      prop.put("password", "dbamvah2016");
      prop.put("interval", "60000");
      return prop;
    }
  }

  public static void main(String[] args) throws Exception {
    Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    Properties props = loadProperties(args);
    Long interval = Long.valueOf(props.getProperty("interval"));
    long last = System.currentTimeMillis();
    boolean execute = true;
    int count = 0;

    long curr = System.currentTimeMillis();
    long delta = curr - last;
    if (delta >= interval) {
      last = curr;
      execute = true;
    }
    if (execute) {
      execute = false;
      new Main(count++, props);
    }
  }
}
