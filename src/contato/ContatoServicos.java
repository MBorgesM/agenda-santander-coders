package contato;

import exceptions.TelefoneJaCadastradoException;
import telefone.Telefone;
import telefone.TelefoneServicos;
import util.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContatoServicos {
    public static void criarContato(Scanner sc, String pathArquivo, String pathArquivoSeq,
                                        String pathArquivoTelefone, String pathArquivoSeqTelefone) throws Exception {
        Long id = Util.lerIdSequencia(pathArquivoSeq);

        System.out.print("Insira o nome do contato: ");
        String nome = sc.nextLine();

        System.out.print("Insira o sobrenome do contato: ");
        String sobrenome = sc.nextLine();

        System.out.print("Insira a quantidade de telefones do contato: ");
        int qtdTelefones = sc.nextInt();
        sc.nextLine();
        List<Telefone> telefones = new ArrayList<>();

        for (int i = 0; i < qtdTelefones; i++) {
            System.out.printf("\nTelefone %d: \n", i + 1);
            try {
                Telefone telefone = TelefoneServicos.criarTelefone(sc, pathArquivoTelefone, pathArquivoSeqTelefone);
                for (int j = 0; j < i; j++) {
                    if (telefone.equals(telefones.get(j))) {
                        throw new TelefoneJaCadastradoException
                                ("Telefone repetido, a operação foi cancelada. Por favor insira telefones únicos");
                    }
                }
                telefones.add(telefone);
            } catch (TelefoneJaCadastradoException e) {
                System.err.println(e.getMessage());
                return;
            }
        }

        Contato contato = new Contato(id, nome, sobrenome, telefones);
        cadastrarContato(contato, pathArquivo, pathArquivoSeq);
    }

    public static void cadastrarContato(Contato contato, String pathArquivo, String pathArquivoSeq) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(pathArquivo, true));

        writer.write(contato.contatoParaLinha());
        writer.newLine();
        writer.close();

        Util.incrementarIdSequencia(pathArquivoSeq);
    }

}
