import contato.Contato;
import contato.ContatoServicos;
import menu.Menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Agenda {
    private static List<Contato> contatos;
    private static final String PATH = "D:\\projects\\santander-coders\\Logica-de-Programacao\\projeto\\src\\";
    private static final String ARQUIVO_CONTATOS = PATH + "contatos.txt";
    private static final String ARQUIVO_SEQ_CONTATOS = PATH + "contatos_seq.txt";
    private static final String ARQUIVO_TELEFONES = PATH + "telefones.txt";
    private static final String ARQUIVO_SEQ_TELEFONES = PATH + "telefones_seq.txt";

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int opcao = 0;

        do {
            contatos = carregarContatos();
            Menu.menuInicial(contatos);
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    try {
                        ContatoServicos.criarContato(sc, ARQUIVO_CONTATOS, ARQUIVO_SEQ_CONTATOS, ARQUIVO_TELEFONES,
                                ARQUIVO_SEQ_TELEFONES);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    Menu.opcaoInvalida();
                    break;
            }
        } while (opcao != 4);

        Menu.finalizarExecucao();
        sc.close();
    }

    private static List<Contato> carregarContatos() throws Exception {
        List<Contato> contatos = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CONTATOS));
        String contatoLinha;

        while ((contatoLinha = reader.readLine()) != null) {
            Contato contato = Contato.linhaParaContato(contatoLinha, ARQUIVO_TELEFONES);
            contatos.add(contato);
        }

        return contatos;
    }
}