# Changelog

Tutte le modifiche importanti a questo progetto saranno documentate in questo file.

Il formato è basato su [Keep a Changelog](https://keepachangelog.com/it-IT/1.0.0/),
e questo progetto aderisce al [Semantic Versioning](https://semver.org/lang/it/).

> [!note]
> Questo file contiene sia il CHANGELOG del **[Client]** che del **[Server]**.

## Client

## Server

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