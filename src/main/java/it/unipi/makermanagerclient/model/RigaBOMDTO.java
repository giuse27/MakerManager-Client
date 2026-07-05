package it.unipi.makermanagerclient.model;

/**
 * Rappresentazione lato client di una RigaBOM.
 */
public class RigaBOMDTO {

    private Long id;
    private String elementoCatalogo;
    private int quantita;

    public RigaBOMDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getElementoCatalogo() {
        return elementoCatalogo;
    }

    public void setElementoCatalogo(String elementoCatalogo) {
        this.elementoCatalogo = elementoCatalogo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    @Override
    public String toString() {
        return elementoCatalogo + " x" + quantita;
    }

}