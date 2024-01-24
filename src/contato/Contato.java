package contato;

import telefone.Telefone;
import telefone.TelefoneServicos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Contato {
    private Long id;
    private String nome;
    private String sobrenome;
    private List<Telefone> telefones;

    public Contato(Long id, String nome, String sobrenome, List<Telefone> telefones) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefones = telefones;
    }

    public Long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return this.sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public List<Telefone> getTelefones() {
        return this.telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public String contatoParaLinha() {
        StringBuilder telefonesIds = new StringBuilder();

        for (Telefone telefone : this.telefones) {
            telefonesIds.append(telefone.getId());
            telefonesIds.append(",");
        }

        String telefones = telefonesIds.substring(0, telefonesIds.length() - 1);

        return  id +
                ";" +
                nome +
                ";" +
                sobrenome +
                ";|" +
                telefones;
    }

    public static Contato linhaParaContato(String linha, String pathArquivo) throws IOException {
        String[] parametros = linha.split("\\|");
        String[] parametrosContato = parametros[0].split(";");

        Long id = Long.valueOf(parametrosContato[0]);
        String nome = parametrosContato[1];
        String sobrenome = parametrosContato[2];
        List<Telefone> telefones = new ArrayList<>();

        String[] parametrosTelefone = parametros[1].split(",");
        for (String telefone : parametrosTelefone) {
            Long idTelefone = Long.valueOf(telefone);
            telefones.add(TelefoneServicos.recuperarTelefonePeloId(idTelefone, pathArquivo));
        }

        return new Contato(id, nome, sobrenome, telefones);
    }

    @Override
    public String toString() {
        return this.id + "  | " + this.nome + " " + this.sobrenome;
    }
}
