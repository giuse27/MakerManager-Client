package it.unipi.makermanagerclient.model;

/**
 * Rappresentazione lato client di un Inventario: rispecchia
 * InventarioResponseDTO del Server, che include anche il nickname del
 * proprietario (comodo per mostrarlo in tabella senza una chiamata in
 * più).
 */
public class InventarioDTO {

    private Long id;
    private String nome;
    private Long idUtente;
    private String nicknameUtente;

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

    public String getNicknameUtente() {
        return nicknameUtente;
    }

    public void setNicknameUtente(String nicknameUtente) {
        this.nicknameUtente = nicknameUtente;
    }

    @Override
    public String toString() {
        return nome + " (id=" + id + ", utente=" + nicknameUtente + ")";
    }

}