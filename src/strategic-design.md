# Strategic design

Analizzando i sottodomini abbiamo identificato i componenti fisici e logici del
sistema, andando così a definire i bounded-context e le loro interazioni.

## Bounded context

Per _bounded context_ si intende una suddivisione del dominio ben delineata ed
avente un _ubiquitous language_ consistente nei suoi confini, un proprio modello
e che rappresenti fisicamente un progetto a se stante con un proprio ciclo di
vita, implementato e mantenuto da un unico team.

In questa fase di progettazione si è anche deciso e specificato quali servizi
esterni si occupano di gestire i sottodomini generici del problema. Infatti la
scelta dello specifico servizio vincola le interazioni tra _bounded context_ e si
riflette nella context map.

### Administration

Per quanto concerne `Hotel Management` e `Stay Management` abbiamo deciso di
definirli in un unico _bounded context_ `Administration`: in questo modo i due
singoli sottodomini rappresentano componenti logici del singolo servizio.

Poiché è richiesto che il pannello di controllo sia fruibile via web si applica
il backend-for-frontend pattern: `Administration` si occuperà di fornire una
RESTful API ad uso del frontend che sarà descritto in seguito.

Si noti che con `Administration` abbiamo evitato la logica a microservizi pura,
andando cioè a creare un _bounded context_ per ciascun sottodominio, in quanto:

- non ci sono conflitti a livello di domain language tra i due sottodomini
- il ciclo di vita può essere unito in modo da avere un unico deployment su
  unica infrastruttura riducendo i costi di fornitura del servizio;
- `Hotel Management` avrà un numero di richieste sempre molto basso e lo scaling
  del servizio può essere dimensionato pensando unicamente a `Stay Management`.

L'alternativa a questa strategia è di separare i due servizi e progettarli con
paradigma totalmente serverless, che renda i costi del servizio proporzionali
all'utilizzo. Questa modalità però dipende strettamente dai servizi offerti dal
cloud provider e richiederebbe uno studio più approfondito dello stesso, cosa
che non è possibile affrontare in questa sede.

### AWS Cognito:

Per il sottodominio generico Authentication definiamo il _bounded context_ AWS
Cognito che verrà affidato all'omonimo servizio Amazon.

### Control Panel

L'interfaccia utente di `Administration` e `Authentication` sarà separata in un
_bounded context_ a parte che rappresenta un progetto con framework React. Qui
saranno quindi definiti, oltre che il layout generale, i diversi componenti che
realizzeranno la logica di business front-end per i servizi `Hotel Management`,
`Stay Management` ed `Authentication`. Inoltre l'interfaccia dovrà visualizzare i
dati di `Room Monitoring`, `Data Elaboration` e lo stato delle centraline servito da
`Control Unit Management`, andando quindi a interagire con i relativi _bounded
context_ descritti in seguito.

### AWS IoT Core

Con il _bounded context_ AWS IoT Core, affidato all'omonimo servizio Amazon,
includiamo i sottodomini `Control Unit Management`, Control Unit Maintenance e lo
storage cloud dei dati generati da `Room Monitoring`. AWS IoT Core di Amazon
permette di gestire agevolmente tutto questo tramite un apposito pannello di
controllo, dove è anche possibile abbinare le centraline alle stanze attraverso
tagging. Infine, tramite un apposita Rest API è possibile ottenere per ogni
centralina i dati caricati, verificarne lo stato ed inviare comandi:
quest'ultima possibilità può essere utilizzata per inviare il token di un
ospite.

### Guest Authorization Service

Il sottodominio `Guest Authorization` è realizzabile con due sistemi distinti. Il
primo _bounded context_, che chiameremo Guest Authorization Service, si occupa
della generazione del token quando viene creato un nuovo soggiorno in `Stay
Management`, e del suo trasferimento alla centralina attraverso AWS IoT Core.

L'altro sistema necessario al processo di `Guest Authorization` è incluso come
modulo nel software della centralina.

### Control Unit

Predisponiamo un unico _bounded context_ per il software della centralina, il
quale è costituito da due moduli: uno che realizza le funzioni del sottodomini
Room Monitoring e l'altro quelle di `Guest Authorization` per quanto concerne la
gestione del transponder NFC.

Si è evitato di creare due software indipendenti poiché i sottodomini coinvolti
non presentano conflitti di _domain language_, devono avere lo stesso ciclo di
vita e un unico deployment per semplificare la configurazione e manutenzione
delle centraline.

### Guest App

Per il sottodominio Guest App definiamo un omonimo _bounded context_.

## Context map

Di seguito vengono mostrate le relazioni tra i bounded context.

![context](./images/context-map.png)

Dalla context map si nota come il **Control Panel** ha una dipendenza con AWS
Cognito gestita tramite ACL: il pannello infatti richiede l'autenticazione
utente attraverso il servizio amazon e permette tramite apposita pagina di
gestire gli account. Il pannello inoltre aggrega le informazioni da più servizi
come `AWS IoT Core` per recuperare lo stato delle centraline installate ed i dati
dei consumi real-time, oltre che Data Elaboration Service per ottenere i calcoli
relativi ai soggiorni. Infine fornisce l'interfaccia utente per le funzioni di
Administration.

Per quanto riguarda **Administration**, oltre a fornire un API per il pannello
di controllo, ne fornisce un'altra in modalità OHS per la `Guest App` che
necessita di reperire le informazioni su `Stay` ed `Hotel`. Entrambe queste API
necessitano di verificare l'autenticità delle richieste, per questo
`Administration` dipende da un lato da AWS Cognito e dall'altro da `Guest
Authorization Service` che genera i token usati da `Guest App` per eseguire le
richieste. Viste in questo modo queste due dipendenze non rappresentano
connessioni con i servizi remoti in quanto le verifiche possono essere eseguite
localmente ad `Administration`, tuttavia il processo di verifica è vincolato
alle tecnologie usate dai rispettivi servizi. `Administration` comunque comunica
direttamente con i due servizi di autenticazione/autorizzazione nel momento in
cui richiede l'inserimento di un nuovo account Albergatore o la generazione del
token per il pernottamento al check-in di un visitatore.

**Guest Authorization Service** dipende da AWS IoT Core per sincronizzare con la
relativa `Control Unit` il token generato al check-in o rimosso al checkout.

**Control Unit**, oltre che ricevere aggiornamenti di stato da AWS IoT Core, vi
inoltra i dati raccolti dai sensori.

Per quanto riguarda **Guest App**, oltre che connettersi ad `Administration`,
necessita dei dati di Data Elaboration Service forniti tramite OHS. Infine,
dipende dalla `Control Unit` per la modalità con la quale riceve un nuovo token
attraverso NFC.

Per concludere, **Data Elaboration Service** richiede aggiornamenti ad AWS IoT
Core per quanto riguarda i nuovi dati da elaborare e fornisce un API in modalità
OHS per `Guest App` e un altra per il Control Panel. Inoltre è tecnicamente
vincolata a `Guest Authorization Service` in quanto si deve verificare localmente
l'autenticità del token ricevuto con le richieste di `Guest App`.
