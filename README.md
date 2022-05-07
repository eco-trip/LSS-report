# LSS-report

Ecotrip è un sistema che incentiva la sostenibilità nel settore alberghiero
stimolando direttamente gli ospiti a un corretto comportamento grazie alla gamification.

Il presente progetto è stato commissionato da un cliente reale che opera con alcuni partner 
in America progettando e costruendo nuove strutture alberghiere.

Abbiamo lavorato a stretto contatto con il cliente sin dalle prime fasi di knowlegde crunching
e design fino alla consegna del prototipo, di seguito illustreremo passo passo 
il problema e la nostra soluzione.

# Contesto e obiettivo

La sostenibilità in Europa è un tema molto sentito anche nel settore alberghiero, da anni infatti
sono presenti sistemi e centraline che permettono di ridurre gli sprechi energetici andando
ad esempio a spegnere gli impianti elettrici quando l'ospite esce dalla sua camera.

In America invece la maggior parte degli hotel non dispone di questi semplici meccanismi e l'utenza
è molto meno attenta al problema eco sostenibilità.
Un esempio banale di spreco energetico che risulta essere un comportamento diffuso, 
avviene quando in estate un ospite esce dalla camera lasciando il condizionatore acceso a temperature molto basse 
e contemporaneamente le tende aperte.

Dal 2020 la [Sustainable Hospitality Alliance](https://sustainablehospitalityalliance.org/), che comprende il 30%
dell'industra alberghiera globale, si impegna a proporre linee guida e best practice per il design e la gestione 
di hotel sostenibili. Tuttavia il processo per la sostenibilità è molto lungo ed impegnativo e l'alleanza è 
ancora in una fase preliminare di raccolta dati. 

L'obiettivo del nostro cliente è quello di proporre all'alleanza sopracitata Ecotrip, un nuovo sistema che incentivi direttamente gli ospiti a tenere un corretto comportamento. 
Il sistema è composto da un app che l'ospite può utilizzare per visualizzare i consumi della sua stanza ed ottenere un punteggio "sostenibilità" opportumanente calcolato tenendo conto di vari fattori.
Per monitorare i consumi il sistema comprende una centralina, da installare per ogni stanza dell'albergo, 
che raccoglie i dati attraverso diversi sensori e li carica sul cloud.

Da questo obiettivo di business abbiamo effettuato l'analisi di dominio partendo con alcune sessioni
di knowledge crunching con il cliente.

# Analisi Requisiti / knwoledge krunching / ?

Dalle prime interazioni con l'esperto di dominio lato cliente, si è notato che questo aveva ben chiari gli obiettivi 
finali del progetto, ma abbiamo dovuto fornire consulenza tecnica, tecnologica e strategica per definire insieme le 
funzionalità da includere subito in Ecotrip e quelle invece lasciate a sviluppi futuri.

Dopo alcune sessioni di knowledge crunching siamo giunti al seguente testo condiviso.

Il gestore di un hotel può supportare il sistema Ecotrip installando la centralina in una o più camere.
Una centralina comprende diversi sensori cablati che andranno opportunamente sistemati all'interno della camera.
I sensori previsti per il primo prototipo di Ecotrip sono:
- 1 sensore per rilevare il consumo elettrico per l'intero circuito luci + 1 per il circuito prese
- 1 flussometro per misurare il consumo d'acqua fredda + 1 per l'acqua calda
- 1 termometro per misurare la temperatura dell'acqua fredda + 1 per l'acqua calda
- 1 sensore di luminosità per ogni finestra, per rilevare se la tenda è aperta o chiusa
- 1 sensore di temperatura ambientale
- 1 sensore di umidità ambientale

La centralina comprende anche un insieme di sensori per identificare se l'ospite è presente nella camera o meno: 
questo specifico aspetto viene lasciato per sviluppi successivi al primo prototipo, il quale rileverà la presenza dell'ospite
attraverso un semplice interruttore meccanico. Nel prodotto finale prevediamo invece due possibilità: 
- nel caso in cui la camera dispone già di una centralina che accende e spegne l'impianto elettrico in base all'inserimento della chiave 
da parte dell'ospite, allora la nostra centralina identificherà la presenza in base all'attivazione degli impianti.
- negli altri casi doteremo la camera di un sensore per identificare se la porta di accesso è aperta/chiusa e di uno o più sensori di
presenza da installare a soffitto, in modo da stabilire se l'ospite è entrato o uscito ogni volta che la porta viene chiusa.

La centralina è connessa alla rete wifi dell'hotel ed ha accesso ad Internet, questa campionerà ogni X secondi i dati da tutti i sensori 
e li invierà ad un archivio remoto. 
Una volta installata, prima di poter inviare i dati, la centralina deve essere abbinata con l'hotel ed un numero di camera.

Al fine di fornire le funzioni di abbinamento e monitoraggio dati, sarà predisposto un pannello di controllo:
qui gli amministratori di Ecotrip potranno gestire la lista degli hotel, le loro camere e le centraline installate ancora da abbinare.
Gli amministratori potranno infine abilitare per ogni hotel un account per il suo gestore.
Il gestore di un hotel potrà quindi accedere al pannello di controllo e visualizzare per ciascuna delle sue camere i dati raccolti 
dai sensori ed aggiornati in tempo reale. 

Infine, alla centralina è collegato un transponder NFC che deve essere installato nella camera in modo che sia visibile agli ospiti, 
magari identificabile con il logo di Ecotrip.
Quando un ospite avvicina il proprio smartphone provvisto di NFC al transponder della centralina, questo riceve indicazioni 
per avviare automaticamente l'app Ecotrip con un parametro rappresentante il codice pernottamento.
L'app permette all'ospite di visualizzare in tempo reale i propri consumi ed il punteggio "sostenibilità" calcolato sulla
base di precise formule. L'app riceve questi dati da un servizio remoto che li fornirà sulla base del codice pernottamento.

Al fine di garantire sicurezza e privacy dei dati, il codice pernottamento non è rappresentato dal numero di camera, ma è costituito
dal numero di camera affiancato ad un codice random generato al checkin dell'ospite e disattivato al suo checkout. 
Il gestore dell'hotel tramite il pannello di controllo potrà quindi per ogni camera indicare il momento di checkin e checkout:
questa funzione manuale in ottica di sviluppo futuro sarà automatizzabile con la connessione del pannello di controllo al gestionale 
dell'albergo.
La centralina dovrà quindi essere in grado, oltre che inviare dati, anche di aggiornare il codice pernottamento da utilizzare
con il transponder.

