# MakerManager

<p align="center">
  <a href="https://github.com/giuse27/MakerManager-Client">
    <img src="https://img.shields.io/github/stars/giuse27/MakerManager-Client?style=social" alt="Stars">
  </a>
  <a href="https://github.com/giuse27/MakerManager-Client/releases">
    <img src="https://img.shields.io/github/v/release/giuse27/MakerManager-Client?color=green" alt="Latest Release">
  </a>
  <a href="https://github.com/giuse27/MakerManager-Client">
    <img src="https://img.shields.io/github/license/giuse27/MakerManager-Client" alt="License">
  </a>
  </br>
  <a href="https://github.com/giuse27">
    <img src="https://img.shields.io/badge/GitHub-giuse27-blue?logo=github&style=for-the-badge" alt="GitHub User">
  </a>
</p>

Progetto "MakerManager" di Giuseppe Vaglica per il corso di Programmazione Avanzata (prof. Vecchio, Vallati, A.A. 2025/2026).

- [MakerManager](#makermanager)
  - [Introduzione](#introduzione)
  - [Sviluppo del software](#sviluppo-del-software)
    - [Changelog](#changelog)
    - [Roadmap](#roadmap)
  - [Manuali](#manuali)
    - [Prerequisiti](#prerequisiti)
    - [Installazione](#installazione)
    - [Altri manuali](#altri-manuali)
  - [Funzionalità del software](#funzionalità-del-software)
    - [Descrizione di MakerManager](#descrizione-di-makermanager)
    - [Casi d'uso](#casi-duso)
    - [Esempi di utilizzo](#esempi-di-utilizzo)
  - [Informazioni generali](#informazioni-generali)
    - [Autore](#autore)
    - [Licenza](#licenza)
  - [Informazioni sull'utilizzo dell'AI](#informazioni-sullutilizzo-dellai)

## Introduzione

Il progetto MakerManager è un'applicazione distribuita realizzata in Java che permette ad hobbisti e professionisti (maker) di gestire in modo efficiente il proprio inventario hardware e la pianificazione dei progetti. Il sistema si compone di un'interfaccia grafica realizzata in JavaFX e di un servizio backend autonomo (Spring Boot) che gestisce la logica di business e la persistenza dei dati su MySQL.

L'obiettivo principale è mettere in correlazione i componenti posseduti dall'utente (es. sensori, microcontrollori, filamenti 3D) con i requisiti dei progetti presenti in catalogo, così da suggerire all'utente quali progetti può realizzare subito e quali richiedono ancora pochi acquisti.

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

## Sviluppo del software

> [!warning] ATTENZIONE  
> Lo sviluppo di questo progetto è stato diviso in due repository per esigenze legate alla consegna del progetto:
> * **[Repo - MakerManager-Client]** per lo sviluppo dell'interfaccia di MakerManager
> * **[Repo - MakerManager-Server]** per la logica di back-end del server

> Nonostante le due repository siano separate tutta la documentazione è contenuta qua dentro e fa riferimento al software MakerManager nel suo complesso. Pertanto, **è possibile fare affidamento al solo contenuto di questa repository**.  

> Anche le **versioni** presenti nelle due repo sono coerenti

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

### Changelog

Puoi consultare il changelog per i dettagli delle varie versioni di questo progetto nel file [`CHANGELOG.md`](./CHANGELOG.md).

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

### Roadmap  

> [!note] 
> La **roadmap** elenca le funzioni già realizzate e da realizzare del progetto. Non dà dettagli implementativi ma serve solo per tenere traccia del **quadro generale** del progetto.

* [X] Client
  * [X] Creazione e setup del progetto MakerManager-Client
  * [X] Architettura base
    * [X] Interfaccia minimale per testare gli endpoint del server
    * [X] Tutti gli endpoint creati lato server possono essere testati
* [X] Server
  * [X] Creazione e setup del progetto MakerManager-Server
    * [X] Configurazione della connessione MySQL in `application.properties`
  * [X] Architettura base
    * [X] `/inizializza`
    * [X] `/api/catalogo...`
      * [X] Visualizza elementi in catalogo
      * [X] Aggiungi un elemento al catalogo
      * [X] Elimina un elemento dal catalogo a partire dal suo id
    * [X] `/api/inventario...`
      * [X] Visualizza tutti gli inventari
      * [X] Visualizza il contenuto di un inventario
      * [X] Visualizza gli inventari di un utente
      * [X] Crea un nuovo inventario
      * [X] Elimina un inventario a partire dal suo id
      * [X] Aggiungi un articolo a un inventario
      * [X] Elimina un articolo dall'inventario a partire dal suo id
    * [X] `/api/progetti...`
      * [X] Visualizza tutti i progetti
      * [X] Visualizza un progetto a partire dal suo id
      * [X] Visualizza i progetti di una tipologia
      * [X] Crea un nuovo progetto
      * [X] Elimina un progetto a partire dal suo id
      * [X] Aggiungi una riga alla BOM di un progetto
      * [X] Elimina una riga dalla BOM di un progetto a partire dal suo id 

</br>

> Raggiungimento di [`v0.0.0`] (architettura base di MakerManager)

</br>

* [X] Client
  * [X] Implementazione di una GUI funzionale
    * [X] Registrazione/login dell'utente
    * [X] Dashboard utente
      * [X] L'utente può visualizzare e gestire i suoi progetti
      * [X] L'utente può visualizzare e gestire i suoi inventari
        * [X] L'utente può creare un nuovo articolo in inventario selezionando un elemento catalogo già esistente o in alternativa può crearne uno nuovo
    * [X] Dashboard globale
      * [X] L'utente può visualizzare i progetti nel catalogo globale
      * [X] L'utente può aprire un progetto per visualizzarne le caratteristiche
* [X] Server
  * [X] Utente
    * [X] Registrazione con nickname email e password
      * [X] Sistema con token di autorizzazione (JWT)
    * [X] Autenticazione e permessi sugli endpoint
  * [X] Sistema multi ruolo (utente e admin)
  * [X] Endpoint avanzati per la modifica di entità già esistenti
    * [ ] Catalogo
      * [ ] È possibile modificare un elemento in catalogo (*)
    * [X] Progetti
      * [ ] È possibile modificare un progetto (**)
      * [X] È possibile modificare la quantità di un elemento della BOM
    * [X] Inventario
      * [ ] È possibile modificare un inventario (**)
      * [X] È possibile modificare il contenuto di un inventario
  * [X] Logica di business
    * [X] Progetti consigliati sulla base delle proprie risorse

</br>

* (*) ci penserò in futuro per il momento è una funzione che potrebbe riguardare sogli gli admin e per il momento non mi interessa
* (**) priorità minore, non so se voglio implementarlo al momento. Comporterebbe un'uleriore schermata nella GUI già abbastanza pesante. Rimane comunque possibile eliminare un oggetto e ricrearlo se si vogliono modificare campi come nome e descrizione.

</br>

> Raggiungimento di [`v1.0.0`] (prima versione demo di MakerManager)

</br>

* [ ] Funzioni future 
  * [ ] Tracciamento reale del progresso di un progetto
  * [ ] Interazione con altri utenti
  * [ ] Interfaccia di prototipazione
    * [ ] Diario personale
    * [ ] Funzionalità basate sulla logica economica
      * [ ] Ricerca di progetti tramite budget personale
  * [ ] Possibilità di rendere i progetti privati o pubblici

</br>

> Raggiungimento di [`v2.0.0`] (prima versione completa di MakerManager)

</br>

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

## Manuali

### Prerequisiti

* **Java 21** o superiore.
* **MySQL 8.0** o superiore, in ascolto su `127.0.0.1:3306` con credenziali `root` / `root`.
* **Maven** (per la gestione delle dipendenze).
* **JavaFX Runtime** (incluso nelle dipendenze Maven).

> NOTA BENE: il database viene creato automaticamente all'avvio.

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

### Installazione

> [!warning]
> Le due repository sono versionate in modo coerente: scarica sempre la release del Server corrispondente a quella del Client (in questo momento v1.0.0).

L'indirizzo del Server è centralizzato in un'unica costante, AppConfig.BASE_URL: se il Server gira su un'altra macchina o su un'altra porta è sufficiente modificare quella riga.

1. **Avvia il Server (repository MakerManager-Server):**

Scarica il codice sorgente della repository e da NetBeans importa il progetto e avvialo.

Il server resta in ascolto su http://localhost:8080. Al primo avvio viene creato automaticamente il database e l'utente ADMIN di default, con le credenziali definite in `src/main/resources/application.properties`:

```properties
sadmin.default.nickname=giuse27
admin.default.email=giuse27@makermanager.com
admin.default.password=LOUVRE2014
```

2. **Avvia il Client (questa repository):**

Scarica il codice sorgente della repository e da NetBeans importa il progetto e avvialo.

3. **Primo avvio e inizializzazione:**

All'avvio del Client compare la schermata di autenticazione: registra un nuovo utente oppure accedi con le credenziali dell'ADMIN di default.

Su un database vuoto non c'è nulla da vedere: **accedi come ADMIN**, apri il pannello **Database** e premi **Inizializza**. Il Server cancella l'eventuale contenuto pregresso (tranne l'ADMIN di default) e ricarica catalogo, utenti, inventari e progetti dal file `src/main/resources/data/inizializzazione.json` (server).

È possibile proseguire sia dall'account admin sia rientarre con un utente di test tra quelli caricati dal JSON:

```json
  "utenti": [
    { 
      "nickname": "pippo", 
      "email": "pippo@test.com", 
      "password": "LOUVRE2014" 
    },
    { 
      "nickname": "maria", 
      "email": "maria@test.com", 
      "password": "LOUVRE2014" 
    },
    { 
      "nickname": "peppe", 
      "email": "peppe@test.com", 
      "password": "LOUVRE2014" 
    },
    { 
      "nickname": "andrea", 
      "email": "andrea@test.com", 
      "password": "LOUVRE2014" 
    }
  ]
```

Aprendo la Dashboard MakerManager mostrerà i progetti consigliati in base a ciò che quell'utente possiede in inventario (l'admin a inizializzazione non possiede nulla).

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

### Altri manuali

| Documento | Contenuto | 
| :-- | :-- |
| [**`docs/endpoint.md`**](./docs/endpoint.md) | Manuale completo di tutti gli endpoint esposti dal Server, con permessi ed esempi curl. |
| [**`docs/documentazione-progetto.md`**](./docs/documentazione-progetto.md) | Documento di progetto: caso d'uso, struttura, scelte progettuali, test, sviluppi futuri. |
| [**`docs/diagrams/diagrammi.md`**](./docs/diagrams/diagrammi.md) | Diagrammi Mermaid: dominio del Server, architettura del Client, casi d'uso. |
| [**`CHANGELOG.md`**](./CHANGELOG.md) | Storico delle versioni di Client e Server. |

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

## Funzionalità del software

### Descrizione di MakerManager

**MakerManager** è un software per hobbisti e professionisti che nasce dall'esigenza di avere un sistema semplice per tenere sotto controllo o creare nuovi progetti. Le funzioni di base sono:

* **Gestione dell'inventario:** è possibile creare uno o più inventari di componenti, materiali, strumenti da lavoro e risorse digitali, tenendo traccia delle quantità e delle tipologie.

* **Catalogo condiviso:** il catalogo elementi definisce i "modelli teorici" (es. Arduino Nano), mentre il catalogo progetti raccoglie i progetti pubblicati dagli utenti, ciascuno con la propria distinta base (B.O.M. -> *Bill of Materials*).

* **Progetti consigliati:** il sistema mette in relazione i due punti precedenti e suggerisce all'utente i progetti che può realizzare subito con ciò che possiede, oppure quelli per cui gli mancano pochi elementi (soglia configurabile).

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

### Casi d'uso

I singoli casi d'uso sono meglio descritti nella documentazione pdf del progetto o nel file in [**`docs/documentazione-progetto.md`**](./docs/documentazione-progetto.md).

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

### Esempi di utilizzo

Gli esempi di utilizzo del software sono presenti nella documentazione pdf del progetto o nel file in [**`docs/documentazione-progetto.md`**](./docs/documentazione-progetto.md).

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

## Informazioni generali

### Autore

Questo progetto è stato ideato e sviluppato interamente da:

<div align="center">
  <a href="https://github.com/giuse27">
    <img src="https://github.com/giuse27.png" width="180" alt="Giuseppe Vaglica" style="border-radius:50%;" /><br><br>
    <img src="https://img.shields.io/badge/Giuseppe_Vaglica-@giuse27-green?style=flat-square&logo=github" alt="Badge Giuseppe Vaglica" width="256"/>
  </a>
</div>

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

### Licenza

Puoi consultare la licenza di questo progetto nel file [`LICENSE`](./LICENSE).

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

## Informazioni sull'utilizzo dell'AI

Dai un'occhiata al documento [**AI.md**](./docs/AI.md).

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

[Repo - MakerManager-Client]: https://github.com/giuse27/MakerManager-Client
[Repo - MakerManager-Server]: https://github.com/giuse27/MakerManager-Server
[`v0.0.0`]: ./CHANGELOG.md
[`v1.0.0`]: ./CHANGELOG.md
[`v2.0.0`]: ./CHANGELOG.md