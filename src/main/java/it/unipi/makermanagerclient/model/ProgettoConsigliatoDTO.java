package it.unipi.makermanagerclient.model;

/**
 * Rappresentazione lato client di un progetto consigliato 
 */
public class ProgettoConsigliatoDTO {

    private Long id;
    private String tipo;
    private String nome;
    private String descrizione;
    private Long idAutore;
    private String nicknameAutore;
    private double indiceFattibilita;
    private boolean realizzabile;
    private int righeTotali;
    private int righeMancanti;

    public ProgettoConsigliatoDTO() {
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

    public Long getIdAutore() {
        return idAutore;
    }

    public void setIdAutore(Long idAutore) {
        this.idAutore = idAutore;
    }

    public String getNicknameAutore() {
        return nicknameAutore;
    }

    public void setNicknameAutore(String nicknameAutore) {
        this.nicknameAutore = nicknameAutore;
    }

    public double getIndiceFattibilita() {
        return indiceFattibilita;
    }

    public void setIndiceFattibilita(double indiceFattibilita) {
        this.indiceFattibilita = indiceFattibilita;
    }

    public boolean isRealizzabile() {
        return realizzabile;
    }

    public void setRealizzabile(boolean realizzabile) {
        this.realizzabile = realizzabile;
    }

    public int getRigheTotali() {
        return righeTotali;
    }

    public void setRigheTotali(int righeTotali) {
        this.righeTotali = righeTotali;
    }

    public int getRigheMancanti() {
        return righeMancanti;
    }

    public void setRigheMancanti(int righeMancanti) {
        this.righeMancanti = righeMancanti;
    }

    public String getFattibilita() {
        return realizzabile
                ? "Hai tutti i componenti necessari"
                : "Ti mancano " + righeMancanti + " componenti";
    }

    @Override
    public String toString() {
        return nome + " (" + nicknameAutore + ")";
    }

}
