package core.controller;

import base.enumeration.Funcionalidade;
import base.exception.AcomodacaoException;
import base.exception.PacoteException;
import core.test.PacoteTest;


import java.util.Scanner;

public class PacoteController {
    public PacoteTest pacoteTest;
    public PacoteController(PacoteTest pacoteTest) {
        this.pacoteTest = pacoteTest;
    }
    public void iniciar() throws PacoteException,AcomodacaoException {
        Scanner entrada = new Scanner(System.in);
        String opcao = null;
        System.out.println(
                "=== TESTE MODULO PACOTE ===\n"
                        + "1 - Listar\n"
                        + "2 - Cadastrar\n"
                        + "3 - Alterar\n"
                        + "4 - Excluir"
        );
        do {
            System.out.println("\nEscolha a funcionalidade:");
            opcao = entrada.nextLine();

            Funcionalidade funcionalidade = null;
            switch (opcao) {
                case "1":
                    funcionalidade = Funcionalidade.LISTAR;
                    break;
                case "2":
                    funcionalidade = Funcionalidade.CADASTRAR;
                    break;
                case "3":
                    funcionalidade = Funcionalidade.ALTERAR;
                    break;
                case "4":
                    funcionalidade = Funcionalidade.EXCLUIR;
                    break;
            }
            if(funcionalidade != null) {
                try {
                    System.out.println("Funcionalidade: " + funcionalidade);
                    System.out.println(pacoteTest.testar(funcionalidade));
                } catch(PacoteException | AcomodacaoException excecao) {
                    System.err.println(excecao.getMessage());
                }
            }

        } while(!opcao.isEmpty());
    }

}
