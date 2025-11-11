package core.test;

import base.enumeration.Funcionalidade;
import base.exception.AcomodacaoException;
import base.exception.PacoteException;
import core.service.PacoteService;


public class PacoteTest {
    private PacoteService pacoteService;
    public PacoteTest(PacoteService pacoteService) {
        this.pacoteService = pacoteService;
    }
    public String testar(Funcionalidade funcionalidade) throws PacoteException, AcomodacaoException {
        switch (funcionalidade) {
            case LISTAR:
                return this.listar();
            case CADASTRAR:
                return  this.cadastrar();
            case ALTERAR:
                return this.alterar();
            case EXCLUIR:
                return this.excluir();
            default:
                return null;
        }
    }
    public String listar() throws PacoteException {
        return pacoteService.listar();
    }
    public String cadastrar() throws PacoteException, AcomodacaoException {
        // Dados para cadastro
        String nome = "";
        String acomodacao = "";
        String qtdDiarias = "";
        String valorTotal = "";
        return pacoteService.cadastrar(nome, acomodacao, qtdDiarias, valorTotal);
    }
        public String alterar() throws PacoteException, AcomodacaoException {
        String id = "";
        String nome = "";
        String idAcomodacao = "";
        String qtdDiarias = "";
        String valorTotal = "";
        return pacoteService.alterar(id, nome, idAcomodacao, qtdDiarias, valorTotal);
    }
    public String excluir() throws PacoteException {
        // Dados para exclusão
        String id = "";
        return pacoteService.excluir(id);
    }
}
