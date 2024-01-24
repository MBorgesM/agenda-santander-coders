package util;

import java.io.*;

public class Util {
    public static Long lerIdSequencia(String pathArquivoSeq) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pathArquivoSeq));
        String linhaAtual;
        String ultimaLinha = null;

        while ((linhaAtual = reader.readLine()) != null) {
            ultimaLinha = linhaAtual;
        }

        reader.close();

        if (ultimaLinha == null) {
            incrementarIdSequencia(pathArquivoSeq);
            return 1L;
        } else {
            return Long.parseLong(ultimaLinha);
        }
    }

    public static void incrementarIdSequencia(String pathArquivoSeq) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pathArquivoSeq));
        BufferedWriter writer = new BufferedWriter(new FileWriter(pathArquivoSeq, true));
        String linhaAtual;
        String ultimaLinha = null;

        while ((linhaAtual = reader.readLine()) != null) {
            ultimaLinha = linhaAtual;
        }

        reader.close();

        if (ultimaLinha == null) {
            writer.write("1");
            writer.close();
            return;
        }

        Long ultimoId = Long.parseLong(ultimaLinha);
        Long proximoId = Long.sum(ultimoId, 1L);

        writer.newLine();
        writer.write(proximoId.toString());
        writer.close();
    }
}
