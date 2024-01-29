package contato;

import exceptions.TelefoneJaCadastradoException;
import menu.Menu;
import telefone.Telefone;
import telefone.TelefoneServicos;
import util.Util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContatoServicos {
    public static void criarContato(Scanner sc, String pathArquivo, String pathArquivoSeq,
                                        String pathArquivoTelefone, String pathArquivoSeqTelefone) throws Exception {
        Long id = Util.lerIdSequencia(pathArquivoSeq);

        System.out.print("\nInsira o nome do contato: ");
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

    public static void cadastrarContato(Contato contato, String pathArquivo, String pathArquivoSeq) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(pathArquivo, true));

        writer.write(contato.contatoParaLinha());
        writer.newLine();
        writer.close();

        Util.incrementarIdSequencia(pathArquivoSeq);
    }

    public static void removerContato(Long id, String path, String pathArquivo, String pathArquivoTelefone) {
        File arquivoOriginal = new File(pathArquivo);
        File arquivoAux = new File(path + "contatos_temp.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(pathArquivo));
            BufferedWriter writer = new BufferedWriter(new FileWriter(path + "contatos_temp.txt"))) {
            String linha;

            while((linha = reader.readLine()) != null) {
                Long idAtual = Long.valueOf(linha.split(";")[0]);

                if (id.equals(idAtual)) {
                    String parametrosTelefones = linha.split("\\|")[1];
                    String[] ids = parametrosTelefones.split(",");
                    for (String idTelefone : ids) {
                        TelefoneServicos.removerTelefone(Long.valueOf(idTelefone), path, pathArquivoTelefone);
                    }
                    continue;
                }
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.delete(Paths.get(pathArquivo));
            arquivoAux.renameTo(arquivoOriginal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editarContato(
            Scanner sc, String path, String pathArquivo, String pathArquivoTelefone,
            String pathArquivoSeqTelefone) throws TelefoneJaCadastradoException, IOException {
        System.out.print("\nInsira o ID do contato que deseja editar: ");
        Long id = sc.nextLong();
        sc.nextLine();

        File arquivoOriginal = new File(pathArquivo);
        File arquivoAux = new File(path + "contatos_temp.txt");
        String linha;
        boolean editado = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(pathArquivo));
             BufferedWriter writer = new BufferedWriter(new FileWriter(path + "contatos_temp.txt"))) {
            while ((linha = reader.readLine()) != null) {
                Long idAtual = Long.valueOf(linha.split(";")[0]);

                if (!(id.equals(idAtual))) {
                    writer.write(linha);
                    writer.newLine();
                    continue;
                }

                Contato contato = Contato.linhaParaContato(linha, pathArquivoTelefone);

                System.out.print("Deseja editar informações do contato (C) ou dos telefones (T)? ");
                String escolha = sc.nextLine().toUpperCase();

                if (escolha.equals("C")) {
                    System.out.print("Insira o novo nome do contato: ");
                    String novoNome = sc.nextLine();
                    System.out.print("Insira o novo sobrenome do contato: ");
                    String novoSobrenome = sc.nextLine();

                    contato.setNome(novoNome);
                    contato.setSobrenome(novoSobrenome);

                } else if (escolha.equals("T")) {
                    Menu.printTelefones(contato.getTelefones());
                    System.out.print("Deseja cadastrar (C) ou remover (R) um telefone? ");
                    String opcao = sc.nextLine().toUpperCase();

                    if (opcao.equals("C")) {
                        Telefone novoTelefone =
                                TelefoneServicos.criarTelefone(sc, pathArquivoTelefone, pathArquivoSeqTelefone);
                        contato.addTelefone(novoTelefone);
                    }
                    else if (opcao.equals("R")) {
                        if (contato.getTelefones().size() > 1) {
                            System.out.println("Insira o ID do telefone que deseja remover: ");
                            Long idTelefone = sc.nextLong();
                            sc.nextLine();
                            TelefoneServicos.removerTelefone(idTelefone, path, pathArquivoTelefone);
                            contato.removerTelefone(idTelefone);
                        } else {
                            System.out.println("Um contato deve possuir ao menos 1 telefone");
                            continue;
                        }
                    }
                }

                linha = contato.contatoParaLinha();
                writer.write(linha);
                writer.newLine();
                editado = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (editado) {
            Files.delete(Paths.get(pathArquivo));
            arquivoAux.renameTo(arquivoOriginal);
        } else {
            System.out.print("\nContato com ID " + id + " não encontrado.");
            Files.delete(Paths.get(path + "contatos_temp.txt"));
        }
    }
}
