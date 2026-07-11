# Diagrammi di supporto per MakerManager

> [!note]
> Se non visualizzi in contenuto di questo file è perché il tuo lettore non supporta la visualizzazione delle anteprime di diagrammi mermaid. Puoi aprire questo file da VsCode installando l'estensione Markdown Mermaid Preview Support o visualizzare il file alternativo contenente le immagini: [**`diagrammi-alt.md`**](./diagrammi-alt.md) 

- [Diagrammi di supporto per MakerManager](#diagrammi-di-supporto-per-makermanager)
  - [Architettura del Client](#architettura-del-client)
  - [Architettura del Server](#architettura-del-server)
  - [Dominio del Server](#dominio-del-server)
  - [Diagramma di sequenza: Progetti Consigliati](#diagramma-di-sequenza-progetti-consigliati)

## Architettura del Client

```mermaid
classDiagram
    direction TB

    %% -------------------------- AVVIO ------------------------------------

    class App {
        <<Application>>
        -Scene scene$
        +start(Stage) void
        +setRoot(String)$ void
        +getStylesheet()$ String
    }

    class AppConfig {
        <<utility>>
        +String BASE_URL$
        +String ENDPOINT_AUTH_LOGIN$
        +String ENDPOINT_CATALOGO$
        +String ENDPOINT_INVENTARIO$
        +String ENDPOINT_PROGETTI$
        +String ENDPOINT_PROGETTI_CONSIGLIATI$
        +String ENDPOINT_INIZIALIZZA$
    }

    %% ----------------------- PRESENTAZIONE -------------------------------

    class PannelloRicaricabile {
        <<interface>>
        +ricarica() void
    }

    class ShellController {
        -Map~String, Parent~ pannelli
        -Map~String, PannelloRicaricabile~ pannelliRicaricabili
        +initialize(URL, ResourceBundle) void
        -caricaPannello(String, String) void
        -mostra(String) void
    }

    class AuthController {
        +onAccedi() void
        +onRegistrati() void
        -completaAutenticazione(RispostaAutenticazioneDTO) void
    }

    class DashboardController {
        +ricarica() void
    }
    class TuttiProgettiController {
        +ricarica() void
    }
    class InventarioController {
        +ricarica() void
        +onCreaInventario() void
        +onEliminaInventario() void
    }
    class MieiProgettiController {
        +ricarica() void
        +onCreaProgetto() void
        +onEliminaProgetto() void
    }
    class DatabaseController {
        +ricarica() void
        +onInizializza() void
        +onAggiungiElemento() void
        +onEliminaElemento() void
    }
    class ImpostazioniController {
        +onSalva() void
        +onRipristinaPredefinito() void
    }
    class ContenutoInventarioController {
        +onAggiungiArticolo() void
        +onEliminaArticolo() void
    }
    class DettaglioProgettoController {
        +onModificaBom() void
        +onAggiungiRiga() void
        +onEliminaRiga() void
    }

    PannelloRicaricabile <|.. DashboardController
    PannelloRicaricabile <|.. TuttiProgettiController
    PannelloRicaricabile <|.. InventarioController
    PannelloRicaricabile <|.. MieiProgettiController
    PannelloRicaricabile <|.. DatabaseController

    ShellController --> PannelloRicaricabile : ricarica al cambio pannello
    App --> AuthController : carica auth.fxml
    AuthController --> ShellController : dopo il login carica shell.fxml
    InventarioController --> ContenutoInventarioController : apre finestra
    MieiProgettiController --> DettaglioProgettoController : apre finestra

    %% -------------------------- SERVICE ----------------------------------

    class AuthService {
        <<utility>>
        +login(String, String)$ RispostaAutenticazioneDTO
        +registrati(String, String, String)$ RispostaAutenticazioneDTO
    }
    class CatalogoService {
        <<utility>>
        +elenco()$ List~ElementoCatalogoDTO~
        +crea(String, String, String)$ ElementoCatalogoDTO
        +elimina(long)$ void
    }
    class InventarioService {
        <<utility>>
        +elencoInventariConConteggio()$ List~InventarioDTO~
        +contenutoInventario(long)$ List~ArticoloInventarioDTO~
        +creaInventario(String)$ InventarioDTO
        +aggiornaQuantita(long, int)$ ArticoloInventarioDTO
        +eliminaArticolo(long)$ void
    }
    class ProgettoService {
        <<utility>>
        +elencoTutti()$ List~ProgettoDTO~
        +elencoDiUtente(long)$ List~ProgettoDTO~
        +dettaglio(long)$ ProgettoConBomDTO
        +crea(String, String, String)$ ProgettoDTO
        +aggiungiRigaBom(long, long, int)$ RigaBOMDTO
        +consigliati(Integer)$ List~ProgettoConsigliatoDTO~
    }
    class AmministrazioneService {
        <<utility>>
        +inizializzaDatabase()$ void
    }

    %% --------------------------- NETWORK ---------------------------------

    class ApiClient {
        <<utility>>
        -HttpClient HTTP_CLIENT$
        +get(String, String)$ ApiResponse
        +post(String, String, String)$ ApiResponse
        +patch(String, String, String)$ ApiResponse
        +delete(String, String)$ ApiResponse
        -aggiungiAutorizzazione(Builder, String)$ void
    }

    class ApiResponse {
        <<record>>
        +int statusCode
        +String body
        +isSuccesso() boolean
    }

    class ApiException {
        <<RuntimeException>>
        -int statusCode
        +da(ApiResponse)$ ApiException
        -estraiMessaggio(String)$ String
    }

    %% -------------------- SESSIONE E UTILITY -----------------------------

    class Sessione {
        <<singleton>>
        -Sessione ISTANZA$
        -String token
        -Long idUtente
        -String ruolo
        -Integer sogliaMancanti
        +getIstanza()$ Sessione
        +avvia(String, Long, String, String, String) void
        +termina() void
        +isAdmin() boolean
    }

    class EsecutoreAsincrono {
        <<utility>>
        +esegui(Operazione~T~, Consumer~T~, Consumer~Throwable~)$ void
    }

    class GsonProvider {
        <<utility>>
        +get()$ Gson
    }

    %% ---------------------------- DTO ------------------------------------

    class RispostaAutenticazioneDTO
    class UtenteDTO
    class ElementoCatalogoDTO
    class InventarioDTO
    class ArticoloInventarioDTO
    class ProgettoDTO
    class ProgettoConBomDTO
    class ProgettoConsigliatoDTO
    class RigaBOMDTO

    ProgettoConBomDTO *-- RigaBOMDTO

    %% ------------------------ COLLEGAMENTI -------------------------------

    AuthController ..> AuthService
    DashboardController ..> ProgettoService
    TuttiProgettiController ..> ProgettoService
    MieiProgettiController ..> ProgettoService
    DettaglioProgettoController ..> ProgettoService
    InventarioController ..> InventarioService
    ContenutoInventarioController ..> InventarioService
    DatabaseController ..> CatalogoService
    DatabaseController ..> AmministrazioneService
    ImpostazioniController ..> Sessione

    AuthService ..> ApiClient
    CatalogoService ..> ApiClient
    InventarioService ..> ApiClient
    ProgettoService ..> ApiClient
    AmministrazioneService ..> ApiClient

    AuthService ..> GsonProvider
    ProgettoService ..> Sessione : legge il token
    InventarioService ..> Sessione : legge il token

    ApiClient ..> ApiResponse : produce
    ApiException ..> ApiResponse : costruita da

    DashboardController ..> EsecutoreAsincrono
    InventarioController ..> EsecutoreAsincrono
    AuthService ..> ApiException : lancia

    ProgettoService ..> ProgettoConsigliatoDTO
    InventarioService ..> InventarioDTO
    CatalogoService ..> ElementoCatalogoDTO
    AuthService ..> RispostaAutenticazioneDTO
```

## Architettura del Server

```mermaid
classDiagram
    direction LR

    %% ---------------------------- SICUREZZA ------------------------------

    class JwtAuthFilter {
        <<Component>>
        +doFilterInternal(...) void
    }

    class JwtService {
        <<Component>>
        +generaToken(Utente) String
        +estraiEmail(String) String
        +estraiRuolo(String) RuoloUtente
        +isTokenValido(String, String) boolean
    }

    class SecurityConfig {
        <<Configuration>>
        +filterChain(HttpSecurity) SecurityFilterChain
        +passwordEncoder() PasswordEncoder
    }

    class PermessiEndpointAuthorizationManager {
        <<Component>>
        +check(...) AuthorizationDecision
    }

    class PermessiEndpointRepository {
        <<Component>>
        +trovaRegole() List~RegolaPermesso~
    }

    class UtenteCorrente {
        <<Component>>
        +get() Utente
        +verificaProprietarioOAdmin(Utente, String) void
    }

    SecurityConfig --> JwtAuthFilter
    SecurityConfig --> PermessiEndpointAuthorizationManager
    JwtAuthFilter --> JwtService
    PermessiEndpointAuthorizationManager --> PermessiEndpointRepository

    %% ---------------------------- CONTROLLER -----------------------------

    class AuthController {
        <<RestController>>
        +registrazione(RegistrazioneRequestDTO)
        +login(LoginRequestDTO)
    }
    class UtenteController {
        <<RestController>>
        +trovaTuttiGliUtenti()
        +profiloCorrente(UserDetails)
    }
    class CatalogoController {
        <<RestController>>
        +trovaTutti()
        +crea(ElementoCatalogoRequestDTO)
        +elimina(Long)
    }
    class InventarioController {
        <<RestController>>
        +trovaTuttiGliInventari()
        +trovaInventario(Long)
        +trovaInventariUtente(Long)
        +creaInventario(InventarioRequestDTO)
        +eliminaInventario(Long)
        +creaArticoloInventario(ArticoloInventarioRequestDTO)
        +aggiornaQuantitaArticolo(Long, AggiornaQuantitaDTO)
        +eliminaArticoloDaInventario(Long)
    }
    class ProgettoController {
        <<RestController>>
        +trovaTuttiIProgetti()
        +trovaProgetto(Long)
        +trovaProgettiPerTipologia(String)
        +trovaProgettiUtente(Long)
        +creaProgetto(ProgettoRequestDTO)
        +eliminaProgetto(Long)
        +aggiungiRigaBOM(Long, RigaBOMRequestDTO)
        +aggiornaQuantitaRigaBOM(Long, Long, AggiornaQuantitaDTO)
        +eliminaRigaBOM(Long, Long)
    }
    class ProgettiConsigliatiController {
        <<RestController>>
        +trovaProgettiConsigliati(Integer)
    }
    class InizializzazioneController {
        <<RestController>>
        +inizializza()
    }

    %% ---------------------------- SERVICE --------------------------------

    class AuthService {
        <<Service>>
        +registra(RegistrazioneRequestDTO) AuthResponseDTO
        +login(LoginRequestDTO) AuthResponseDTO
    }
    class UtenteService {
        <<Service>>
        +trovaTutti() List
        +trovaProfilo(String) UtenteResponseDTO
    }
    class CatalogoService {
        <<Service>>
        +trovaTutti() List
        +crea(dto) ElementoCatalogoResponseDTO
        +elimina(Long) void
    }
    class InventarioService {
        <<Service>>
        +trovaInventario(Long) List
        +creaInventario(dto) InventarioResponseDTO
        +creaArticoloInventario(dto) ArticoloInventarioResponseDTO
        +aggiornaQuantitaArticolo(Long, dto)
        +eliminaArticoloDaInventario(Long) void
    }
    class ProgettoService {
        <<Service>>
        +trovaTutti() List
        +trovaPerId(Long) ProgettoConBomResponseDTO
        +crea(dto) ProgettoResponseDTO
        +aggiungiRigaBOM(Long, dto) RigaBOMResponseDTO
        +eliminaRigaBOM(Long, Long) void
    }
    class ProgettiConsigliatiService {
        <<Service>>
        +consigliaProgetti(Integer) List~ProgettoConsigliatoResponseDTO~
        -recuperaPossedimenti(Utente) Map
        -risolviSoglia(Integer) int
    }
    class InizializzazioneService {
        <<Service>>
        +inizializza() void
        -cancellaDatiEsistenti() void
        -caricaCatalogo(dati) Map
        -caricaProgetti(dati, ...) void
    }

    %% ------------------------- LOGICA DI BUSINESS ------------------------

    class CalcolatoreFattibilita {
        <<Component>>
        +calcola(List~RigaBOM~, Map~Long,Integer~) EsitoFattibilita
        -rimuoviDuplicati(List~RigaBOM~) Map
    }

    class EsitoFattibilita {
        <<record>>
        +int righeTotali
        +List~ElementoMancante~ dettaglioMancanti
        +righeMancanti() int
        +realizzabile() boolean
        +indiceFattibilita() double
        +pezziMancantiTotali() int
    }

    class ElementoMancante {
        <<record>>
        +String nomeElemento
        +int quantitaRichiesta
        +int quantitaPosseduta
        +int quantitaMancante
    }

    EsitoFattibilita *-- ElementoMancante
    CalcolatoreFattibilita ..> EsitoFattibilita : crea

    %% ---------------------------- FACTORY --------------------------------

    class ArticoloInventarioFactory {
        <<utility>>
        +creaArticoloInventario(String, ElementoCatalogo, Inventario, int)$ ArticoloInventario
    }
    class ProgettoMakerFactory {
        <<utility>>
        +creaProgetto(TipologiaProgetto)$ ProgettoMaker
    }

    %% ---------------------------- REPOSITORY -----------------------------

    class UtenteRepository {
        <<JpaRepository>>
    }
    class ElementoCatalogoRepository {
        <<JpaRepository>>
    }
    class InventarioRepository {
        <<JpaRepository>>
    }
    class ArticoloInventarioRepository {
        <<JpaRepository>>
    }
    class ProgettoMakerRepository {
        <<JpaRepository>>
    }

    %% ------------------------ GESTIONE ERRORI ----------------------------

    class GlobalExceptionHandler {
        <<RestControllerAdvice>>
        +handleRisorsaNonTrovata() 404
        +handleDatiNonValidi() 400
        +handleAccessoNegato() 403
        +handleCredenzialiNonValide() 401
        +handleValidationExceptions() 400
    }

    %% ------------------------ COLLEGAMENTI -------------------------------

    AuthController --> AuthService
    UtenteController --> UtenteService
    CatalogoController --> CatalogoService
    InventarioController --> InventarioService
    ProgettoController --> ProgettoService
    ProgettiConsigliatiController --> ProgettiConsigliatiService
    InizializzazioneController --> InizializzazioneService

    AuthService --> UtenteRepository
    AuthService --> JwtService
    UtenteService --> UtenteRepository
    CatalogoService --> ElementoCatalogoRepository
    InventarioService --> InventarioRepository
    InventarioService --> ArticoloInventarioRepository
    InventarioService --> ElementoCatalogoRepository
    InventarioService --> UtenteCorrente
    ProgettoService --> ProgettoMakerRepository
    ProgettoService --> UtenteCorrente
    ProgettiConsigliatiService --> ProgettoMakerRepository
    ProgettiConsigliatiService --> ArticoloInventarioRepository
    ProgettiConsigliatiService --> CalcolatoreFattibilita
    ProgettiConsigliatiService --> UtenteCorrente
    InizializzazioneService --> ArticoloInventarioFactory
    InizializzazioneService --> ProgettoMakerFactory
```

## Dominio del Server

```mermaid
classDiagram

    %% ------------------------- RELAZIONI ---------------------------------

    Utente "1" --> "0..*" Inventario : possiede
    Utente "1" --> "0..*" ProgettoMaker : autore di

    Inventario "1" *-- "0..*" ArticoloInventario : articoli
    ArticoloInventario "0..*" --> "1" ElementoCatalogo : elementoCatalogo

    ArticoloInventario <|-- ComponenteElettronico
    ArticoloInventario <|-- MaterialeConsumabile
    ArticoloInventario <|-- AttrezzoDaLavoro
    ArticoloInventario <|-- Software

    ProgettoMaker <|-- ProgettoStampa3D
    ProgettoMaker <|-- ProgettoElettronica
    ProgettoMaker <|-- ProgettoRobotica
    ProgettoMaker <|-- ProgettoSoftware

    ProgettoMaker *-- BOM : distintaBase
    ProgettoMaker *-- Progresso : progresso
    BOM "1" *-- "0..*" RigaBOM : righeFabbisogno
    RigaBOM "0..*" --> "1" ElementoCatalogo : articoloRichiesto

    Utente --> RuoloUtente
    ElementoCatalogo --> TipologiaElemento
    ProgettoMaker --> TipologiaProgetto

    %% ------------------------- UTENTE ------------------------------------

    class Utente {
        <<Entity>>
        -Long id
        -String nickname
        -String email
        -String password
        -RuoloUtente ruolo
    }

    class RuoloUtente {
        <<enumeration>>
        UTENTE
        ADMIN
    }

    %% ------------------------- CATALOGO ----------------------------------

    class ElementoCatalogo {
        <<Entity>>
        -Long id
        -String nome
        -String descrizione
        -TipologiaElemento tipologia
    }

    class TipologiaElemento {
        <<enumeration>>
        COMPONENTE_ELETTRONICO
        MATERIALE_CONSUMABILE
        ATTREZZO_DA_LAVORO
        SOFTWARE
    }

    %% ------------------------- INVENTARIO --------------------------------

    class Inventario {
        <<Entity>>
        -Long id
        -String nome
        -Utente utente
        -List~ArticoloInventario~ articoli
        +aggiungiArticolo(ArticoloInventario) void
    }

    class ArticoloInventario {
        <<abstract>>
        <<Entity>>
        -Long id
        -ElementoCatalogo elementoCatalogo
        -Inventario inventario
        -int quantita
        +getQuantita() int
        +setQuantita(int) void
    }

    class ComponenteElettronico {
        <<Entity>>
    }

    class MaterialeConsumabile {
        <<Entity>>
    }

    class AttrezzoDaLavoro {
        <<Entity>>
    }

    class Software {
        <<Entity>>
    }

    %% ------------------------- PROGETTI ----------------------------------

    class ProgettoMaker {
        <<abstract>>
        <<Entity>>
        -Long id
        -String nome
        -String descrizione
        -Utente autore
        -TipologiaProgetto tipologia
        -LocalDate dataCaricamento
        -BOM distintaBase
        -Progresso progresso
        +getDistintaBase() BOM
        +getProgresso() Progresso
    }

    class TipologiaProgetto {
        <<enumeration>>
        STAMPA_3D
        ELETTRONICA
        ROBOTICA
        SOFTWARE
    }

    class ProgettoStampa3D {
        <<Entity>>
    }

    class ProgettoElettronica {
        <<Entity>>
        -String schemaElettricoUrl
        -boolean richiedePcbCustom
    }

    class ProgettoRobotica {
        <<Entity>>
        -String firmwareRepositoryUrl
        -int numeroGradiDiLiberta
    }

    class ProgettoSoftware {
        <<Entity>>
        -String linguaggioPrevalente
        -String piattaformaTarget
    }

    class BOM {
        <<Embeddable>>
        -List~RigaBOM~ righeFabbisogno
        +aggiungiRiga(RigaBOM) void
        +rimuoviRiga(RigaBOM) void
    }

    class RigaBOM {
        <<Entity>>
        -Long id
        -ElementoCatalogo articoloRichiesto
        -int quantitaRichiesta
    }

    class Progresso {
        <<Embeddable>>
        -String statoAvanzamento
        -LocalDateTime ultimoAggiornamento
        -double percentualeCompletamento
        +aggiornaProgresso(String, double) void
    }
```

## Diagramma di sequenza: Progetti Consigliati

```mermaid
sequenceDiagram
    autonumber

    actor U as Utente
    participant DC as DashboardController<br/>(Client)
    participant EA as EsecutoreAsincrono<br/>(Client)
    participant PS as ProgettoService<br/>(Client)
    participant AC as ApiClient<br/>(Client)

    participant JF as JwtAuthFilter<br/>(Server)
    participant PM as PermessiEndpoint<br/>AuthorizationManager
    participant CTRL as ProgettiConsigliati<br/>Controller
    participant SVC as ProgettiConsigliati<br/>Service
    participant CALC as Calcolatore<br/>Fattibilita
    participant DB as MySQL

    U->>DC: apre la Dashboard
    DC->>EA: esegui(operazione, ifSuccesso, ifFallimento)
    Note over EA: la chiamata bloccante gira su un<br/>thread daemon, non su quello grafico
    EA->>PS: consigliati(sogliaMancanti)
    PS->>AC: GET /api/progetti/consigliati?sogliaMancanti=N<br/>Authorization: Bearer {token}
    AC->>JF: HTTP request

    JF->>JF: valida il JWT ed estrae email + ruolo
    JF->>PM: richiesta autenticata
    PM->>PM: applica le regole di<br/>permessi-endpoint.properties (AUTENTICATO)
    PM->>CTRL: autorizzata

    CTRL->>SVC: consigliaProgetti(sogliaMancanti)
    SVC->>SVC: risolviSoglia(N) o default da application.properties
    SVC->>DB: articoli di tutti gli inventari dell'utente
    DB-->>SVC: List~ArticoloInventario~
    SVC->>SVC: recuperaPossedimenti() -> Map~idElemento, quantita~
    SVC->>DB: findAll() dei progetti
    DB-->>SVC: List~ProgettoMaker~

    loop per ogni progetto (esclusi i propri e quelli con BOM vuota)
        SVC->>CALC: calcola(righeBOM, disponibilita)
        CALC->>CALC: rimuoviDuplicati() e confronto con le disponibilita
        CALC-->>SVC: EsitoFattibilita(righeTotali, dettaglioMancanti)
    end

    SVC->>SVC: filtra per soglia e ordina per<br/>indice di fattibilita decrescente
    SVC-->>CTRL: List~ProgettoConsigliatoResponseDTO~
    CTRL-->>AC: 200 OK + JSON

    AC-->>PS: ApiResponse(200, body)
    PS->>PS: deserializza con Gson
    PS-->>EA: List~ProgettoConsigliatoDTO~
    EA-->>DC: callback ifSuccesso (thread JavaFX)
    DC-->>U: tabella "Progetti consigliati" popolata
```
