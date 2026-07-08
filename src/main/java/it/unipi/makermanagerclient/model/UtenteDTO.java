package it.unipi.makermanagerclient.model;

/**
 * Rappresentazione lato client di un Utente
 */
public class UtenteDTO {

    private Long id;
    private String nickname;
    private String email;
    private String ruolo;

    public UtenteDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    @Override
    public String toString() {
        return nickname + " (" + ruolo + ")";
    }

}
