package it.unipi.makermanagerclient.model;

public class InventarioDTO {

    private Long id;
    private String nome;
    private Long idUtente;

    public InventarioDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    @Override
    public String toString() {
        return nome + " (id=" + id + ", utente=" + idUtente + ")";
    }

}