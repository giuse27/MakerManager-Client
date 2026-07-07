# Changelog

- [Changelog](#changelog)
  - [Client](#client)
    - [Client v0.0.0 - 05/07/2026](#client-v000---05072026)
      - [Aggiunte](#aggiunte)
  - [Server](#server)
    - [Server v0.5.0 - 07/07/2026](#server-v050---07072026)
      - [Aggiunte](#aggiunte-1)
      - [Modifiche](#modifiche)
    - [Server v0.4.0 - 07/07/2026](#server-v040---07072026)
      - [Aggiunte](#aggiunte-2)
      - [Modifiche](#modifiche-1)
    - [Server v0.0.0 - 05/07/2026](#server-v000---05072026)
      - [Aggiunte](#aggiunte-3)

Tutte le modifiche importanti a questo progetto saranno documentate in questo file.

Il formato è basato su [Keep a Changelog](https://keepachangelog.com/it-IT/1.0.0/),
e questo progetto aderisce al [Semantic Versioning](https://semver.org/lang/it/).

> [!note]
> Questo file contiene sia il CHANGELOG del **[Client]** che del **[Server]**.

## Client

### [Client v0.0.0] - 05/07/2026

#### Aggiunte

* È stata implementata una GUI di base (temporanea) che copre tutti gli endpoint presenti. Ogni schermata mostra le chiamate i parametri richiesti e mostra una schermata dove è possibile visualizzare l'output della richiesta.
  * Pannello inizializzazione
  * Pannello catalogo
  * Pannello inventario
  * Pannello progetti

## Server

### [Server v0.5.0] - 07/07/2026

#### Aggiunte

* Nuovi endpoint
  * Inventario
    * Aggiorna la quantità di un articolo (PROPRIETARIO / ADMIN)
  * Progetti
    * Aggiorna la quantità di una riga della BOM (PROPRIETARIO / ADMIN)

#### Modifiche

* Typo fix nel metodo `tResponseDTO()` in `ArticoloInventarioMapper.java`, adesso correttamente rinominato in `toResponseDTO()`.

### [Server v0.4.0] - 07/07/2026

#### Aggiunte

Aggiunti nuovi endpoint per l'autenticazione degli utenti la visualizzazione dei profili e dei relativi progetti. Ogni endpoint possiede dei permessi specifici. Sono di seguito riportate le principali aggiunte della versione:

* Profilo utente con entità vera e propria all'interno del database
* Sicurezza relativa alle chiamate http e all'autenticazione con token JWT
* Sistema multi ruolo: ADMIN e UTENTE
* Permessi globali e locali facilmente configurabili
* Nuovi endpoint:
  * Autenticazione
    * Registrazione *(PUBBLICO)*
    * Login *(PUBBLICO)*
  * Utenti
    * Visualizza il proprio profilo *(AUTENTICATO)*
    * Visualizza tutti gli utenti *(ADMIN)*
  * Progetti
    * Visualizza i progetti di un utente *(PUBBLICO)*

#### Modifiche

I precedenti endpoint relativi alla v0.0.0 sono stati modificati per garantire la gestione dei permessi da parte degli utilizzatori del sistema. È di seguito specificato il ruolo default su ciascun endpoint già esistente.

* Inizializzazione
  * Inizializza il database *(ADMIN)*
* Catalogo
  * Visualizza elementi in catalogo *(PUBBLICO)*
  * Aggiungi un elemento al catalogo *(AUTENTICATO)*
  * Elimina un elemento dal catalogo a partire dal suo `id` *(ADMIN)*
* Inventario
  * Visualizza tutti gli inventari *(ADMIN)*
  * Visualizza il contenuto di un inventario *(PROPRIETARIO / ADMIN)*
  * Visualizza gli inventari di un utente *(PROPRIETARIO / ADMIN)*
  * Crea un nuovo inventario *(AUTENTICATO)*
  * Elimina un inventario a partire dal suo `id` *(PROPRIETARIO / ADMIN)*
  * Aggiungi un articolo a un inventario *(PROPRIETARIO / ADMIN)*
  * Elimina un articolo dall'inventario a partire dal suo `id` *(PROPRIETARIO / ADMIN)*
* Progetti
  * Visualizza tutti i progetti *(PUBBLICO)*
  * Visualizza un progetto a partire dal suo `id` *(PUBBLICO)*
  * Visualizza i progetti di una tipologia *(PUBBLICO)*
  * Crea un nuovo progetto *(AUTENTICATO)*
  * Elimina un progetto a partire dal suo `id` *(PROPRIETARIO / ADMIN)*
  * Aggiungi una riga alla BOM di un progetto *(PROPRIETARIO / ADMIN)*
  * Elimina una riga dalla BOM di un progetto a partire dal suo `id` *(PROPRIETARIO / ADMIN)*

### [Server v0.0.0] - 05/07/2026

#### Aggiunte

Sono stati aggiunti numerosi endpoint di base

* Inizializzazione
  * Inizializza il database
* Catalogo
  * Visualizza elementi in catalogo
  * Aggiungi un elemento al catalogo
  * Elimina un elemento dal catalogo a partire dal suo id
* Inventario
  * Visualizza tutti gli inventari
  * Visualizza il contenuto di un inventario
  * Visualizza gli inventari di un utente
  * Crea un nuovo inventario
  * Elimina un inventario a partire dal suo id
  * Aggiungi un articolo a un inventario
  * Elimina un articolo dall'inventario a partire dal suo id
* Progetti
  * Visualizza tutti i progetti
  * Visualizza un progetto a partire dal suo id
  * Visualizza i progetti di una tipologia
  * Crea un nuovo progetto
  * Elimina un progetto a partire dal suo id
  * Aggiungi una riga alla BOM di un progetto
  * Elimina una riga dalla BOM di un progetto a partire dal suo id

[Client]: https://github.com/giuse27/MakerManager-Client
[Server]: https://github.com/giuse27/MakerManager-Server
[Server v0.0.0]: https://github.com/giuse27/MakerManager-Server/releases/tag/v0.0.0
[Server v0.4.0]: https://github.com/giuse27/MakerManager-Server/releases/tag/v0.4.0
[Server v0.5.0]: https://github.com/giuse27/MakerManager-Server/releases/tag/v0.5.0
[Client v0.0.0]: https://github.com/giuse27/MakerManager-Client/releases/tag/v0.0.0