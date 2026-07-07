**MakerManager GUI**

Descrizione dell'interfaccia grafica

1\. Elementi fissi

Barra laterale sinistra fissa con in ordine: logo con scritta MakerManager (in alto e leggermente più in grande degli altri), poi elenco di pannelli: "Dashboard", "Tutti i progetti", "Inventario", "I miei progetti", "Database", "Impostazioni" e solo in basso a questa schermata laterale è presente il riquadro profilo con immagine profilo (vuota) e nome utente. All'avvio il sistema si trova sulla dashboard principale preselezionando la voce Dashboard tra i pannelli. 

La barra laterale occupa una piccola area a sinistra, tutto il resto della schermata è occupato dalla schermata data dal pannello selezionato. 

Altro elemento fisso è una sottile barra orizzontale posta nella parte più in alto di ciascun pannello. La barra mostra per il momento solo il nome del pannello. 

Infine, ogni schermata mostra molto in piccolo in basso a destra "MakerManager by Giuseppe Vaglica \- versione {versione}" 

2\. Dashboard principale

Descrizione dall'alto verso il basso

Barra orizzontale sottile nella parte alta mostra "Benvenuto {nickname} in MakerManager" (quando dico barra intendo dire un rettangolo senza bordi arrotondati che si è parte dello sfondo e occupa l'intera lunghezza) 

Set di tre riquadri (il riquadro è un rettangolo dai bordi smussati che è in "rilievo" rispetto allo sfondo ed è leggermente più piccolo della schermata sottostante) più piccoli disposti orizzontalmente e in dimensioni uguali sulla stessa riga: il primo mostra il numero di progetti attivi, il secondo mostra il numero di progetti consigliati, il terzo mostra lo stato del server (ONLINE o OFFLINE)   
Sotto questi tre riquadri occupa la lunghezza totale dei tre precedenti un riquadro sottile con scritto "Inventario attivo: {nomeInventario}" 

Riquadro con titolo "Progetti attivi" che contiene la tabella con colonne: "Nome del progetto", "Autore", "Data inizio", "Stato", "Progresso". Di queste colonne la prima mostra il nome del progetto, la seconda il nickname dell'autore, la terza è una data, la quarta mostra un ovale colorato con voci come pianificato, iniziato, in corso, completato; l'ultima invece mostra una barra di avanzamento con la percentuale relativa. 

Nella tabella dei "Progetti attivi", lo stato di avanzamento e/o il progresso devono poter essere modificati in modo rapido e intuitivo direttamente dalla riga interessata (tramite un menu a tendina), per permettere all'utente di aggiornare lo status (es. da "Iniziato" a "In corso") senza dover aprire nuove finestre. Stessa cosa deve poter avvenire per aggiornare la percentuale di avanzamento. 

Secondo riquadro nella schermata principale intitolato "Progetti consigliati". Qui viene mostrata una tabella con le seguenti colonne: "Nome del progetto", "Autore", "Data caricamento", "Fattibilità". Fattibilità è un campo che può mostrare messaggi come "Hai tutti i componenti necessari", "Ti mancano pochi componenti". 

3\. Tutti i progetti

Riquadro con titolo "Tutti i progetti disponibili in catalogo ({numeroProgetti})": tabella con colonne "Nome del progetto", "Autore", "Descrizione" , "Data caricamento". 

4\. Inventario

Set di tre riquadri disposti orizzontalmente che contengono il numero di inventari, il tasto crea un nuovo inventario e il tasto modifica gli inventari esistenti

Riquadro intitolato "I tuoi inventari" che contiene la tabella con colonne "Attivo", "Nome dell'inventario", "Numero di articoli presenti" e "Data ultimo aggiornamento". Le celle della colonna attivo contengono una checkbox per attivare o disattivare un inventario. 

4.1. Inventario selezionato

Cliccando su una riga dell'inventario sarà possibile visualizzarne il contenuto in una nuova schermata. La seguente schermata mostra:

Un set di tre riquadri orizzontali con "Numero articoli", tasto "Aggiungi all'inventario un nuovo articolo", tasto "Elimina un articolo dall'inventario" 

Successivamente il riquadro principale intitolato "{Nome Inventario}" contiene la tabella con colonne "Nome articolo", "Descrizione", "Tipologia", "Quantità". 

Il valore della colonna "Quantità" deve essere facilmente modificabile in linea (es. tramite doppio clic sulla cella o freccette up/down) per permettere all'utente di aggiornare rapidamente le giacenze. 

Cliccando il tasto elimina e poi cliccando successivamente sull'articolo verrà mostrata una finestra popup in cui si chiede conferma per l'eliminazione dell'articolo dall'inventario.

Cliccando il tasto "Aggiungi all'inventario un nuovo articolo", non verrà creato subito l'oggetto, ma si aprirà una finestra di pop-up. Qui l'utente dovrà scegliere se "Importare un elemento esistente dal Catalogo" oppure "Creare un nuovo elemento nel Catalogo". Solo dopo che l'elemento esiste ed è stato selezionato dal database globale (Catalogo), l'utente potrà specificarne la quantità per creare l'articolo all'interno del proprio inventario.

5\. I miei progetti 

Set di tre riquadri disposti orizzontalmente che contengono il numero di progetti creati, il tasto crea un nuovo progetto e il tasto elimina un progetto esistente

Riquadro con titolo "Ciao {nickname} qui ci sono i progetti che hai creato ({numeroProgetti})": tabella con colonne "Nome del progetto", "Descrizione" , "Data caricamento".

Cliccando il tasto elimina e poi cliccando successivamente sul progetto verrà mostrata una finestra popup in cui si chiede conferma per l'eliminazione del progetto. 

Cliccando sul tasto crea un nuovo progetto verrà aperta una finestra popup dove inserire nome e descrizione del progetto da creare.

Cliccando su un progetto nell'elenco sarà possibile modificare la BOM tramite il tasto posto in alto a sinistra: "Modifica la BOM" al seguito della pressione compariranno I tasti per creare una nuova riga della BOM e per eliminare una riga esistente. Qui si applica la stessa logica descritta sull'aggiunta di un elemento al proprio inventario (importazione/creazione) oppure l'eliminazione (pressione sul tasto, click sulla riga, e conferma pop up). 

6\. Database

Solo l'utente con ruolo admin avrà a disposizione un tasto inzializza che se premuto comparirà una finestra pop up di warning che chiede la conferma. 

Riquadro intitolato "Elenco completo degli elementi presenti in catalogo ({numeroElementi})" e che contiene la tabella con "Nome elemento", "Descrizione", "Tipologia". 

7\. Impostazioni

Impostazioni che saranno presenti: seleziona questa impostazione per cercare progetti unendo tutti gli inventari a disposizione e non solo quello attivi. 

8\. Comportamenti comuni

8.1. Visualizzazione di un progetto 

Quando si clicca su un progetto (in un qualsiasi pannello) questo viene mostrato in una nuova finestra e dove la visualizzazione del progetto occupa l'intera schermata. Riquadro di intestazione: in alto è presente il nome del progetto in grande, sotto la descrizione e sotto ancora la data di caricamento e poi l'autore. Questa visualizzazione occupa circa il 25% dell'altezza della finestra. 

Sotto questo primo riquadro di intestazione è mostrata la BOM del progetto ovvero un riquadro intitolato "Requisiti del progetto" che contiene una tabella con "Nome", "Descrizione", "Tipologia", "Quantità", "Disponibilità in inventario" 

Nella finestra di visualizzazione di un progetto, subito sotto i dettagli di intestazione del progetto (nome, autore, data), deve essere presente un pulsante di azione primario. Se il progetto non è ancora attivo, il pulsante mostrerà "Attiva progetto" (per avviarne la costruzione) 