package it.unipi.makermanagerclient.model;

import java.util.List;

/**
 * Rappresentazione lato client di un ProgettoMaker completo di B.O.M.:
 * rispecchia ProgettoConBomResponseDTO del Server
 */
public class ProgettoConBomDTO {

    private Long id;
    private String tipo;
    private String nome;
    private String descrizione;
    private Long idAutore;
    private String nicknameAutore;
    private List<RigaBOMDTO> bom;

    public ProgettoConBomDTO() {
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

    public List<RigaBOMDTO> getBom() {
        return bom;
    }

    public void setBom(List<RigaBOMDTO> bom) {
        this.bom = bom;
    }

    @Override
    public String toString() {
        return nome + " (" + tipo + ", autore=" + nicknameAutore + ")";
    }

}
