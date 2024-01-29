package telefone;

import exceptions.TelefoneJaCadastradoException;
import util.Util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class TelefoneServicos {
    public static Telefone criarTelefone(Scanner sc, String pathArquivo, String pathArquivoSeq)
            throws TelefoneJaCadastradoException, IOException {
        Long id = Util.lerIdSequencia(pathArquivoSeq);

        System.out.print("Insira o DDD do telefone: ");
        String ddd = sc.nextLine();

        System.out.print("Insira o número do telefone: ");
        Long numero = sc.nextLong();
        sc.nextLine();

        if (validaTelefoneUnico(ddd, numero, pathArquivo)) {
            throw new TelefoneJaCadastradoException("Esse telefone já está cadastrado");
        }

        Telefone telefone = new Telefone(id, ddd, numero);

        cadastrarTelefone(telefone, pathArquivo, pathArquivoSeq);

        return telefone;
    }

    public static void cadastrarTelefone(Telefone telefone, String pathArquivo, String pathArquivoSeq)
            throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(pathArquivo, true));

        writer.write(telefone.telefoneParaLinha());
        writer.newLine();
        writer.close();

        Util.incrementarIdSequencia(pathArquivoSeq);
    }

    public static Telefone recuperarTelefonePeloId(Long id, String pathArquivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(pathArquivo))) {
            String telefoneLinha;

            while ((telefoneLinha = reader.readLine()) != null) {
                String idAtual = telefoneLinha.split(";")[0];
                Long idAtualLong = Long.valueOf(idAtual);

                if (id.equals(idAtualLong)) {
                    return Telefone.linhaParaTelefone(telefoneLinha);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void removerTelefone(Long id, String path, String pathArquivo) throws IOException {
        File arquivoOriginal = new File(pathArquivo);
        File arquivoAux = new File(path + "telefones_temp.txt");
        BufferedReader reader = new BufferedReader(new FileReader(pathArquivo));
        BufferedWriter writer = new BufferedWriter(new FileWriter(path + "telefones_temp.txt"));
        String linha;

        while ((linha = reader.readLine()) != null) {
            String idAtual = linha.split(";")[0];
            Long idAtualLong = Long.valueOf(idAtual);

            if (id.equals(idAtualLong)) continue;
            writer.write(linha);
            writer.newLine();
        }

        writer.close();
        reader.close();
        Files.delete(Paths.get(pathArquivo));
        arquivoAux.renameTo(arquivoOriginal);
    }

    private static boolean validaTelefoneUnico(String ddd, Long numero, String pathArquivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pathArquivo));
        String telefone = reader.readLine();
        while (telefone != null) {
            String dddCadastrado = telefone.split(";")[1];
            Long numeroCadastrado = Long.valueOf(telefone.split(";")[2]);
            if (ddd.equals(dddCadastrado) && numero.equals(numeroCadastrado)) {
                return true;
            }
            telefone = reader.readLine();
        }
        reader.close();
        return false;
    }
}
