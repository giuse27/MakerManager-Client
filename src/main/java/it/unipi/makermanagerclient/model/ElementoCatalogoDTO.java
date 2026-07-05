package it.unipi.makermanagerclient.model;

/**
 * Rappresentazione lato client di un ElementoCatalogo.
 */
public class ElementoCatalogoDTO {

    private Long id;
    private String nome;
    private String descrizione;
    private String tipologia;

    public ElementoCatalogoDTO() {
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    @Override
    public String toString() {
        return nome + " (" + tipologia + ")";
    }

}