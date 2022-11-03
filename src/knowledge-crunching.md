# knwoledge krunching

Dalle prime interazioni con l'esperto di dominio lato cliente, si è notato che
questo aveva ben chiari gli obiettivi finali del progetto, ma abbiamo dovuto
fornire consulenza tecnica, tecnologica e strategica per definire insieme le
funzionalità da includere subito in Ecotrip e quelle invece lasciate a sviluppi
futuri.

Dopo alcune sessioni di knowledge crunching siamo giunti al seguente testo
condiviso.

Il gestore di un hotel, l'albergatore, può supportare il sistema Ecotrip
installando la centralina in una o più camere. Una centralina comprende diversi
sensori cablati che andranno opportunamente sistemati all'interno della camera.
I sensori previsti per il primo prototipo di Ecotrip sono:

- 1 sensore per rilevare il consumo elettrico
- 1 flussometro per misurare il consumo d'acqua fredda + 1 per l'acqua calda
- 1 termometro per misurare la temperatura dell'acqua fredda + 1 per l'acqua
  calda
- 1 sensore di luminosità per ogni finestra, per rilevare se la tenda è aperta o
  chiusa
- 1 sensore di temperatura ambientale
- 1 sensore di umidità ambientale

La centralina comprende anche un insieme di sensori per identificare se l'ospite
è presente nella camera o meno: questo specifico aspetto viene lasciato per
sviluppi successivi al primo prototipo, il quale "rileverà" la presenza
dell'ospite attraverso un semplice interruttore meccanico. Nel prodotto finale
prevediamo invece due possibilità:

- nel caso in cui la camera dispone già di una centralina che accende e spegne
  l'impianto elettrico in base all'inserimento della chiave da parte
  dell'ospite, allora la nostra centralina identificherà la presenza in base
  all'attivazione degli impianti.
- negli altri casi doteremo la camera di un sensore per identificare se la porta
  di accesso è aperta/chiusa e di uno o più sensori di presenza da installare a
  soffitto, in modo da stabilire se l'ospite è entrato o uscito ogni volta che
  la porta viene chiusa.

Una volta installata, la centralina viene connessa alla rete wifi dell'hotel in
modo da consentirle l'accesso ad Internet, inoltre viene configurata abbinandola
al numero di camera. Dopo la configurazione, la centralina campionerà ogni X
secondi i dati da tutti i sensori e li invierà sul cloud: deve essere possibile
stabilire l'origine dei dati nei termini di hotel e camera. Infine il sistema
deve prevedere meccanismi di controllo dello stato (centralina online/offline) e
di manutenzione da remoto.

Gli amministratori di Ecotrip tramite un pannello di controllo web possono
gestire la lista degli hotel, le loro camere, visualizzare lo stato delle
centraline installate ed infine registrare l'account dell'albergatore.

L'albergatore potrà quindi accedere al pannello di controllo e, per ciascuna
delle sue camere, visualizzare i dati istantanei raccolti dai sensori ed
indicare i momenti di check-in e check-out di un ospite: quest'ultima funzione
manuale, in ottica di sviluppo futuro, sarà automatizzabile con la connessione
del pannello di controllo al gestionale dell'albergo.

Per ogni soggiorno, cioè il periodo tra il check-in ed il check-out dell'ospite,
saranno visualizzabili oltre che i dati sintetizzati, anche il consumo totale di
CO2 ed il punteggio "sostenibilità" ottenuto. Il calcolo della CO2 viene fatto
considerando il consumo elettrico utilizzato per alimentare la stanza, compreso
quello per il suo riscaldamentoe e quello stimato per riscaldare l'acqua: si
considera infatti che negli USA le camere vengono riscaldate unicamente con
pompa di calore e non si utilizza gas/metano. Per effettuare il calcolo CO2 deve
essere specificato per ciascun hotel il costo dell'energia nei termini di
CO2/Kilowatt. Il punteggio "sostenibilità" tiene conto, non solo della CO2
totale, ma anche del comportamento tenuto dall'ospite durante la sua permanenza.
Alcuni comportamenti penalizzanti sono:

- il fatto di uscire dalla camera lasciando in periodo estivo di giorno tende
  aperte e condizionatore acceso a pieno regime.
- usare eccessiva acqua

L'ospite attraverso l'app Ecotrip può visualizzare i dati del suo soggiorno
corrente e di quelli passati, comprendenti sia i dati sintetizzati, che il
consumo CO2 ed il punteggio "sostenibilità" aggiornati in tempo reale.

Al fine di permettere all'ospite di collegarsi facilmente ed autonomamente ai
dati del proprio soggiorno, a quest'ultimo viene associato un token univoco,
cioè un codice casuale generato al momento del check-in, ed inviato alla
centralina della relativa camera. Alla centralina è collegato un transponder NFC
che deve essere installato nella camera in modo che sia visibile agli ospiti,
magari identificabile con il logo di Ecotrip. Quando un ospite avvicina al
transponder della centralina il proprio smartphone provvisto di NFC, questo
riceve indicazioni per avviare automaticamente l'app Ecotrip con il token come
parametro: da questo momento l'app memorizzerà il token e permetterà di
visualizzare i dati del soggiorno richiedendoli ad un servizio remoto.
L'operazione di collegamento token-app può essere fatta una sola volta e solo
prima del check-out, questo garantisce sicurezza e privacy riducendo il rischio
di violazioni di accesso ai dati.

Al check-out è possibile calcolare uno sconto in base alla CO2 risparmiata
rispetto a valori obiettivo e/o in base al punteggio "sostenibilità" ottenuto.

## User stories

Tra le user stories identificabili, quelle reltive al checkin/checkout e
autorizzazione dell'ospite mediante token risultano essere poco banali, le
abbiamo quindi approfondite con l'esperto di dominio.

In ordine:

- l'albergatore esegue il checkin dell'ospite impostando un nuovo soggiorno per
  una camera
- un nuovo token relativo al soggiorno viene generato
- il token viene inviato alla centralina della camera
- l'ospite, una volta entrato nella camera, esegue il tap con il suo cellulare
  sul transponder NFC della centralina
- la centralina comunica al cellulare di aprire l'app indicando il token del
  soggiorno
- l'ospite visualizza tramite app i dati del soggiorno

![User story](./images/guest-authorization-story.svg)

## Use cases

Di seguito riassumiamo i casi d'uso raggruppati in base al contesto

![control panel use case](./images/control-panel-use-case.svg)

![guest use case](./images/guest-authorization-use-case.svg)

![room monitoring case](./images/room-monitoring-use-case.svg)

![app use case](./images/app-use-case.svg)

## Subdomains

Abbiamo identificato N sottodomini.

![subdomains](./images/subdomains.png)

Hotel Management: comprende le funzionalità svolte dall'amministratore Ecotrip
che riguardano la configurazione di nuovi hotel con le camere.

Stay Management: comprende la gestione dei soggiorni con le funzioni di check-in
e check-out da parte dell'hotelier.

Authentication: comprende l'autenticazione utenti per l'accesso ai servizi di
Hotel e Stay Management oltre che la possibilità di registrare gli account per
gli hotelier.

Control Unit Management: comprende la gestione delle centraline installate con
la possibilità di verifica dello stato e di abbinamento alle camere.

Control Unit Maintenance: comprende il sistema per la manutenzione da remoto
delle centraline installate.

Room Monitoring: comprende il software della centralina necessario per il
campionamento dei dati dai sensori e lo stoccaggio in un servizio cloud.

Data Elaboration: comprende il sistema per il calcolo della stima dei consumi
C02 e del puntenggio "sostenibilità" relativo ai soggiorni, a partire dai dati
collezionati.

Guest Authorization: include il processo di generazione del token per un nuovo
soggiorno ed il suo trasferimento alla centralina e successivamente allo
smartphone mediante transponder NFC, permettendo così all'ospite di accedere ai
dati del suo soggiorno.

Guest App: include la visualizzazione dei dati del soggiorno tramite applicativo
fruibile dagli ospiti, inoltre implementa gli aspetti di gamification.

Di seguito il core domain chart dove mostriamo che secondo la nostra analisi i
sottodomini centrali di ecotrip sono Room Monitoring, Data Elaboration e Guest
App. Consideriamo però l'app uno short-term core, in quanto il concetto di
gamification potrebbe essere ripreso da altri in altri modi e quindi perdere di
differenziazione nel mercato.

I sottodimini Control Unit Management e Maintenance sono di tipo generico in
quanto rappresentano servizi per IoT offerti in modo esaustivo dai grandi cloud
provider: ad esempio tramite AWS IoT Core è possibile gestire le centraline
abbinandole con dei tag alle camere degli hotel, verificarne lo stato ed
effettuare manutenzione remota. Stessa cosa vale per il sottodominio
Authentication che può essere delegato ad un servizio esterno come AWS Cognito.

Infine, sottodomini Hotel Management, Stay Management e Guest Authorization sono
considerati di supporto.

![subdomains](./images/core-domain-chart.png)
