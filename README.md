# MakerManager

</br>

<div align="center">
  <img src="https://img.shields.io/badge/Stato-Work_in_Progress-yellow?style=for-the-badge" alt="Work in Progress Banner">
</div>

</br>

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
    - [Contatti](#contatti)
  - [Informazioni sull'utilizzo dell'AI](#informazioni-sullutilizzo-dellai)
  - [Supporta il progetto](#supporta-il-progetto)

## Introduzione

Il progetto **MakerManager** è un applicazione distribuita realizzata in Java che permette ad hobbisti e professionisti (maker) di gestire in modo efficiente il proprio inventario hardware e la pianificazione dei progetti. Il sistema si compone di un'interfaccia grafica intuitiva realizzata in JavaFX e di un servizio backend autonomo che gestisce la logica di business e la persistenza dei dati. L'obiettivo principale è mettere in correlazione i componenti posseduti dall'utente (es. sensori, microcontrollori, filamenti 3D) con i requisiti di nuovi progetti, permettendo di stimare costi, tenere traccia del tempo impiegato e verificare la fattibilità costruttiva in base alle giacenze.

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

* [ ] Client
  * [ ] Implementazione di una GUI funzionale
    * [ ] Creazione dell'utente
    * [ ] Dashboard utente
      * [ ] L'utente può visualizzare e gestire i suoi progetti
      * [ ] L'utente può visualizzare e gestire i suoi inventari
        * [ ] L'utente può creare un nuovo articolo in inventario selezionando un elemento catalogo già esistente o in alternativa può crearne uno nuovo
    * [ ] Dashboard globale
      * [ ] L'utente può visualizzare i progetti nel catalogo globale
      * [ ] L'utente può aprire un progetto per visualizzarne le caratteristiche
* [ ] Server
  * [X] Utente
    * [X] Registrazione con nickname email e password
      * [X] Sistema con token di autorizzazione (JWT)
    * [X] Autenticazione e permessi sugli endpoint
  * [X] Sistema multi ruolo (utente e admin)
  * [ ] Endpoint avanzati per la modifica di entità già esistenti
    * [ ] Catalogo
      * [ ] È possibile modificare un elemento in catalogo (*)
    * [ ] Progetti
      * [ ] È possibile modificare un progetto (**)
      * [ ] È possibile modificare la quantità di un elemento della BOM
    * [ ] Inventario
      * [ ] È possibile modificare un inventario (**)
      * [ ] È possibile modificare il contenuto di un inventario
  * [ ] Logica di business
    * [ ] Progetti consigliati sulla base delle proprie risorse

</br>

* (*) ci penserò in futuro per il momento è una funzione che potrebbe riguardare sogli gli admin e per il momento non mi interessa
* (**) priorità minore, non so se voglio implementarlo al momento. Comporterebbe un'uleriore schermata nella GUI già abbastanza pesante. Rimane comunque possibile eliminare un oggetto e ricrearlo se si vogliono modificare campi come nome e descrizione.

</br>

> Raggiungimento di [`v1.0.0`] (prima versione demo di MakerManager)

</br>

* [ ] Funzioni future 
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

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

### Installazione

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

### Altri manuali

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

## Funzionalità del software

### Descrizione di MakerManager

**MakerManager** è un software per hobbisti e professionisti che nasce dall'esigenza di avere un sistema semplice per tenere sotto controllo o creare nuovi progetti. Le funzioni di base di MakerManager sono:

* **Gestione dell'inventario:** è possibile creare il proprio inventario di oggettistica, strumenti da lavoro, risorse digitali, e tanto altro; il tutto tenendo traccia delle quantità, tipologie, costi e caratteristiche dei prodotti.
* **Catalogo dei progetti:** viene fornita una lista iniziale di progetti a cui è possibile lavorare. Ogni progetto è diverso sia per tipologia che per scopo: ci sono progetti semplici e progetti difficili da realizzare, costosi e meno costosi, poi ci sono progetti didattici e altri di utilità. Il sistema viene incontro a tutte le esigenze e permette la creazione del proprio progetto e l'inserimento nel catalogo.
* **Gestione dei progetti:** il software permette di mettere in relazione i due punti precedenti permettendo all'utente di cercare un progetto sulla base delle proprie risorse; MakerManager suggerirà all'utente l'elenco dei progetti che può realizzare o dei progetti per cui sono richieste meno risorse aggiuntive.
* **Prototipazione rapida:** prima ancora di pubblicare un progetto in catalogo l'utente ha la possibilità di utilizzare MakerManager per gestire la fase di progettazione e prototipazione del proprio progetto in modo semplice. L'utente avrà un'interfaccia tramite il quale potrà tenere sotto controllo il tempo speso al progetto, le risorse, e dove potrà avere un diario personale per annotazioni, descrizioni delle attività svolte, fonti esterne e materiale didattico di supporto per la creazione del progetto. Tutto ciò costituisce un sistema centralizzato dove poter lavorare. 

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

### Casi d'uso

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

### Esempi di utilizzo

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

### Contatti

Se hai domande sul progetto, suggerimenti o segnalazioni, puoi trovarmi qui:

<p align=center>
  <a href="mailto:giuseppe.vaglica3@gmail.com">
    <img src="https://img.shields.io/badge/Email-giuseppe.vaglica3@gmail.com-D14836?style=flat-square&logo=gmail&logoColor=white" alt="Email" width="50%">
  </a><br>
  <a href="https://t.me/giuseppe_vaglica">
    <img src="https://img.shields.io/badge/Telegram-@giuseppe__vaglica-2CA5E0?style=flat-square&logo=telegram&logoColor=white" alt="Telegram" width="40%">
  </a>
  <a href="https://instagram.com/giuseppe_vaglica">
    <img src="https://img.shields.io/badge/Instagram-@giuseppe__vaglica-E4405F?style=flat-square&logo=instagram&logoColor=white" alt="Instagram" width="40%">
  </a>
</p>

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

## Informazioni sull'utilizzo dell'AI

Dai un'occhiata al documento [**AI.md**](./docs/AI.md).

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

## Supporta il progetto

Se questo strumento ti è stato utile, ti ha fatto risparmiare tempo o semplicemente ti piace, considera di lasciarmi una ⭐️ su [GitHub](https://github.com/giuse27/Programmazione-Avanzata-UNIPI).

È un piccolo gesto gratuito che mi aiuta tantissimo a mantenere il progetto attivo e visibile!

<p align="right">(<a href="#makermanager">ritorna all'inizio</a>)</p>

[Repo - MakerManager-Client]: https://github.com/giuse27/MakerManager-Client
[Repo - MakerManager-Server]: https://github.com/giuse27/MakerManager-Server
[`v0.0.0`]: ./CHANGELOG.md
[`v1.0.0`]: ./CHANGELOG.md
[`v2.0.0`]: ./CHANGELOG.md