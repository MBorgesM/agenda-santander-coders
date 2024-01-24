package menu;

import contato.Contato;

import java.util.List;

public class Menu {
    public static void menuInicial(List<Contato> contatos) {
        StringBuilder sb = new StringBuilder("""
                ##################
                ##### AGENDA #####
                ##################
                                
                >>>> Contatos <<<<
                ID | Nome
                """);

        for (Contato contato : contatos) {
            sb.append(contato);
            sb.append("\n");
        }

        sb.append("""
                                
                >>>> Menu <<<<
                1 - Adicionar Contato
                2 - Remover Contato
                3 - Editar Contato
                4 - Sair
                """);

        System.out.print(sb);
    }

    public static void opcaoInvalida() {
        System.out.println("""
                
                Opção inválida, utilize apenas os valores indicados no menu.
                """);
    }

    public static void finalizarExecucao() {
        System.out.println("""
                
                Obrigado por utilizar nossa agenda!
                """);
    }
}
