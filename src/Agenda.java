import contato.Contato;
import contato.ContatoServicos;
import menu.Menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Agenda {
    private static final String PATH = "D:\\projects\\santander-coders\\Logica-de-Programacao\\projeto\\src\\";
    private static final String ARQUIVO_CONTATOS = PATH + "contatos.txt";
    private static final String ARQUIVO_SEQ_CONTATOS = PATH + "contatos_seq.txt";
    private static final String ARQUIVO_TELEFONES = PATH + "telefones.txt";
    private static final String ARQUIVO_SEQ_TELEFONES = PATH + "telefones_seq.txt";

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int opcao;
        iniciarArquivos();

        do {
            List<Contato> contatos = carregarContatos();
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
                    System.out.print("\nInsira o ID do contato que deseja remover: ");
                    Long id = sc.nextLong();
                    sc.nextLine();
                    ContatoServicos.removerContato(id, PATH, ARQUIVO_CONTATOS, ARQUIVO_TELEFONES);
                    break;
                case 3:
                    ContatoServicos.editarContato(sc, PATH, ARQUIVO_CONTATOS, ARQUIVO_TELEFONES, ARQUIVO_SEQ_TELEFONES);
                    break;
                case 4:
                    Menu.finalizarExecucao();
                    break;
                default:
                    Menu.opcaoInvalida();
                    break;
            }
        } while (opcao != 4);

        sc.close();
    }

    private static List<Contato> carregarContatos() {
        List<Contato> contatos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CONTATOS))) {
            String contatoLinha;

            while ((contatoLinha = reader.readLine()) != null) {
                Contato contato = Contato.linhaParaContato(contatoLinha, ARQUIVO_TELEFONES);
                contatos.add(contato);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return contatos;
    }

    private static void iniciarArquivos() {
        File contato = new File(ARQUIVO_CONTATOS);
        if (!contato.exists()) {
            try {
                contato.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        File contatoSeq = new File(ARQUIVO_SEQ_CONTATOS);
        if (!contatoSeq.exists()) {
            try {
                contatoSeq.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        File telefone = new File(ARQUIVO_TELEFONES);
        if (!telefone.exists()) {
            try {
                telefone.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        File telefoneSeq = new File(ARQUIVO_SEQ_TELEFONES);
        if (!telefoneSeq.exists()) {
            try {
                telefoneSeq.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}