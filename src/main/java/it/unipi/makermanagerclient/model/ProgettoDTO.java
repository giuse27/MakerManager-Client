package it.unipi.makermanagerclient.model;

/**
 * Rappresentazione lato client di un ProgettoMaker
 */
public class ProgettoDTO {

    private Long id;
    private String tipo;
    private String nome;
    private String descrizione;

    public ProgettoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return nome + " (" + tipo + ")";
    }

}