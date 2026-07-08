package it.unipi.makermanagerclient.sessione;

/**
 * Contiene i dati dell'utente autenticato nella sessione corrente del
 * Client: token JWT e identità e ruolo dell'utente. Applico il singleton
 * perchè l'applicazione gestisce sempre e solo un utente autenticato alla 
 * volta, e questi dati servono a più controlli e service
 */
public final class Sessione {

    private static final Sessione ISTANZA = new Sessione();

    private String token;
    private Long idUtente;
    private String nickname;
    private String email;
    private String ruolo;

    private Sessione() {
    }

    public static Sessione getIstanza() {
        return ISTANZA;
    }

    /**
     * Popola la sessione con l'esito di un login/registrazione riuscito.
     */
    public void avvia(
        String token, 
        Long idUtente, 
        String nickname, 
        String email, 
        String ruolo
    ) {
        this.token = token;
        this.idUtente = idUtente;
        this.nickname = nickname;
        this.email = email;
        this.ruolo = ruolo;
    }

    /**
     * Azzera la sessione (logout).
     */
    public void termina() {
        this.token = null;
        this.idUtente = null;
        this.nickname = null;
        this.email = null;
        this.ruolo = null;
    }

    public boolean isAutenticato() {
        return token != null;
    }

    public boolean isAdmin() {
        return "ADMIN".equals(ruolo);
    }

    public String getToken() {
        return token;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getRuolo() {
        return ruolo;
    }

}
