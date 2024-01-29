package telefone;

public class Telefone {

    private Long id;
    private String ddd;
    private Long numero;

    public Telefone(Long id, String ddd, Long numero) {
        this.id = id;
        this.ddd = ddd;
        this.numero = numero;
    }

    public Long getId() {
        return this.id;
    }

    public String getDdd() {
        return this.ddd;
    }

    private void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public Long getNumero() {
        return this.numero;
    }

    private void setNumero(Long numero) {
        this.numero = numero;
    }

    public String telefoneParaLinha() {
        return  id +
                ";" +
                ddd +
                ";" +
                numero;
    }

    public static Telefone linhaParaTelefone(String linha) {
        String[] parametros = linha.split(";");

        Long id = Long.valueOf(parametros[0]);
        String ddd = parametros[1];
        Long numero = Long.valueOf(parametros[2]);

        return new Telefone(id, ddd, numero);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Telefone telefone)) {
            return false;
        }

        return this.ddd.equals(telefone.getDdd()) &&
                this.numero.equals(telefone.getNumero());
    }

    @Override
    public String toString() {
        return this.id + "  | (" + this.ddd + ") " + this.numero;
    }
}
