# Note sull'utilizzo dell'AI

> [!note]
> Modelli utilizzati: Gemini 3.1 Pro e Claude Sonnet 5 (medio, no pensiero)

## Utilizzo generale

* Ho usato l'AI per avere alcuni piccoli aiuti nel **brainstorming** delle idee, nella stesura della **documentazione** e nella modellazione della **roadmap**.

* Utilizzo nell'apprendimento rapido del framework Spring.

* Guida per le convenzioni generali sulla struttura dei file e sull'applicazione dell'SRP (single responsibility principle). 

## Client

* Implementazione di una GUI temporanea per la v0.0.0 e il test degli endpoint.

* Migliorie estetiche e strutturali alla GUI.

* Modifiche sulla robustezza (es. `SpinnerFactories`) e sulle utility per il json (nella v0.0.0)

## Server

* Mi sono fatto aiutare dall'AI nella fase iniziale con l'obiettivo di ottenere uno scheletro per il server e per definire a grandi linee soprattutto il package `model` e parte dell'architettura di base Spring.

* Ho fatto revisionare di tanto in tanto il codice all'AI per verificarne la robustezza e la corettezza. Una parte consistente conseguita da questa verifica è stata la gestione delle eccezioni e l'utilizzo della libreria jakarta.validation e le sue annotazioni. Complessivamente questo mi ha permesso di controllare tutte le situazioni anomale e riportarle sotto forma di messaggio json semplice da leggere.