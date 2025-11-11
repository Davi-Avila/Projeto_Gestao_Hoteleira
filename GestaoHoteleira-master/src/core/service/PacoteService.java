package core.service;
import base.enumeration.Funcionalidade;
import base.exception.AcomodacaoException;
import base.exception.PacoteException;
import base.util.Utilidades;
import core.dao.AcomodacaoDAO;
import core.dao.PacoteDAO;
import core.model.Acomodacao;
import core.model.Pacote;
import java.util.ArrayList;
public class PacoteService {
    private PacoteDAO pacoteDAO;

    public PacoteService(PacoteDAO pacoteDAO) {
        this.pacoteDAO = pacoteDAO;
    }

    public String listar() throws PacoteException {
        ArrayList<Pacote> pacotes = pacoteDAO.selecionar();
        String lista = "";
        if (pacotes.size() > 0) {
            for (Pacote pacote : pacotes) {
                lista += pacote + "\n";
            }
        } else {
            lista = "Nenhuma pacote encontrada.";
        }
        return lista;
    }
    public String cadastrar(String nome, String idAcomodacao, String qtdDiarias, String valorTotal) throws PacoteException, AcomodacaoException {
        String mensagemErro = validarCampos(Funcionalidade.CADASTRAR, null, nome, idAcomodacao, qtdDiarias, valorTotal);
        if(!mensagemErro.isEmpty()) throw new PacoteException(mensagemErro);

        Integer qtdDiariasNumero = Integer.parseInt(qtdDiarias);
        Double valorTotalNumero = Double.parseDouble(valorTotal);

        Long idAcomodacaoNumerico = Long.parseLong(idAcomodacao);
        AcomodacaoDAO acomodacaoDAO = new AcomodacaoDAO();
        Acomodacao acomodacao = acomodacaoDAO.selecionarPorId(idAcomodacaoNumerico);

        Pacote pacote = new Pacote(
                nome,
                acomodacao,
                qtdDiariasNumero,
                valorTotalNumero
        );

        if(pacoteDAO.inserir(pacote)) {
            return "Pessoa cadastrada com sucesso!";
        } else {
            throw new PacoteException("Não foi possível cadastrar a pessoa! Por favor, tente novamente.");
        }
    }

    public String alterar(
            String id,
            String nome,
            String idAcomodacao,
            String qtdDiarias,
            String valorTotal

    ) throws PacoteException, AcomodacaoException {
        String mensagemErro = validarCampos(Funcionalidade.ALTERAR, id, nome,idAcomodacao , qtdDiarias, valorTotal);
        if (!mensagemErro.isEmpty()) throw new PacoteException(mensagemErro);
        Long idNumerico = Long.parseLong(id);
        Integer qtdDiariasNumerica = Integer.parseInt(qtdDiarias);
        Long idAcomodacaoNumerico = Long.parseLong(idAcomodacao);
        Double valorTotalNumerico = Double.parseDouble(valorTotal);
        AcomodacaoDAO acomodacaoDAO = new AcomodacaoDAO();
        Acomodacao acomodacao = acomodacaoDAO.selecionarPorId(idAcomodacaoNumerico);



        Pacote pacote = new Pacote(
                idNumerico,
                nome,
                acomodacao,
                qtdDiariasNumerica,
               valorTotalNumerico
        );

        if (pacoteDAO.atualizar(pacote)) {
            return "Pacote alterada com sucesso!";
        } else {
            throw new PacoteException("Não foi possível alterar a pacote!! Por favor, tente novamente.");
        }
    }
    public String excluir(String id) throws PacoteException {
        String mensagemErro = validarCampos(Funcionalidade.EXCLUIR, id, null, null, null,null );
        if(!mensagemErro.isEmpty()) throw new PacoteException(mensagemErro);

        Long idNumerico = Long.parseLong(id);
        if(pacoteDAO.deletar(idNumerico)) {
            return "Pessoa excluída com sucesso!";
        } else {
            throw new PacoteException("Não foi possível excluir a pessoa! Por favor, tente novamente.");
        }
    }


    private String validarCampos(
            Funcionalidade funcionalidade,
            String id,
            String nome,
            String idAcomodacao,
            String qtdDiarias,
            String valorTotal
    ) throws PacoteException {
        String erros = "";
        // Verificação de id
        if(funcionalidade == Funcionalidade.ALTERAR || funcionalidade == Funcionalidade.EXCLUIR) {
            if(!id.isEmpty()) {
                if (Utilidades.validarNumero(id)) {
                    Long idNumerico = Long.parseLong(id);
                    if(pacoteDAO.selecionarPorId(idNumerico) == null) erros += "\n- Id não encontrado.";
                } else {
                    erros += "\n- Id inválido.";
                }
            } else {
                erros += "\n- Id é obrigatório.";
            }
        }

        if(funcionalidade == Funcionalidade.CADASTRAR) {
            String qtdDiariasTrim = qtdDiarias.trim();
            String idAcomodacaoTrim = idAcomodacao.trim();
            if(!qtdDiariasTrim.isEmpty()) {
                if (!Utilidades.validarNumero(qtdDiarias)) {
                    erros += "\n- Quantidade diária inválida.";
                }
            } else {
                erros += "\n- Quantidade diarias é obrigatória";
            }
            if(idAcomodacao.trim().isEmpty()) {
                erros += "\n- Acomodação é obrigatória";}
            if(nome.trim().isEmpty()) {
                erros += "\n- Nome é obrigatória";}
            return erros;
        }

        // Montagem da mensagem de erro
        String mensagemErro = "";
        if(!erros.isEmpty()) {
            mensagemErro = "Não foi possível " + funcionalidade.name().toLowerCase() + " a pessoa! " +
                    "Erro(s) encontrado(s):" + erros;
        }
        return mensagemErro;
    }
}
