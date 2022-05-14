# knwoledge krunching

Dalle prime interazioni con l'esperto di dominio lato cliente, si è notato che
questo aveva ben chiari gli obiettivi finali del progetto, ma abbiamo dovuto
fornire consulenza tecnica, tecnologica e strategica per definire insieme le
funzionalità da includere subito in Ecotrip e quelle invece lasciate a sviluppi
futuri.

Dopo alcune sessioni di knowledge crunching siamo giunti al seguente testo
condiviso.

Il gestore di un hotel può supportare il sistema Ecotrip installando la
centralina in una o più camere. Una centralina comprende diversi sensori cablati
che andranno opportunamente sistemati all'interno della camera. I sensori
previsti per il primo prototipo di Ecotrip sono:

- 1 sensore per rilevare il consumo elettrico per l'intero circuito luci + 1 per
  il circuito prese
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

La centralina è connessa alla rete wifi dell'hotel ed ha accesso ad Internet,
questa campionerà ogni X secondi i dati da tutti i sensori e li invierà ad un
archivio remoto. Una volta installata, prima di poter inviare i dati, la
centralina deve essere abbinata con l'hotel ed un numero di camera.

Al fine di fornire le funzioni di abbinamento e monitoraggio dati, sarà
predisposto un pannello di controllo: qui gli amministratori di Ecotrip potranno
gestire la lista degli hotel, le loro camere e le centraline installate ancora
da abbinare. Gli amministratori potranno infine, per ogni hotel, registrare
l'account dell'albergatore. L'albergatore potrà quindi accedere al pannello di
controllo e visualizzare per ciascuna delle sue camere i dati raccolti dai
sensori ed aggiornati in tempo reale.

INVERTIRE TUTTO IL DISCORSO -> parlare prima dell'albergatore e del token, poi
della centralina col transponder NFC e dell'ospite

Infine, alla centralina è collegato un transponder NFC che deve essere
installato nella camera in modo che sia visibile agli ospiti, magari
identificabile con il logo di Ecotrip. Quando un ospite avvicina al transponder
della centralina il proprio smartphone provvisto di NFC, questo riceve
indicazioni per avviare automaticamente l'app Ecotrip con un parametro "token",
un codice rappresentante il pernottamento del cliente. L'app permette all'ospite
di visualizzare in tempo reale i propri consumi ed il punteggio "sostenibilità"
calcolato sulla base di precise formule. L'app riceve questi dati da un servizio
remoto che li fornirà sulla base del token fornito.

Al fine di garantire sicurezza e privacy dei dati, il codice pernottamento non è
rappresentato dal numero di camera, ma è costituito dal numero di camera
affiancato ad un codice random generato al checkin dell'ospite e disattivato al
suo checkout. L'albergatore tramite il pannello di controllo potrà quindi per
ogni camera indicare il momento di checkin e checkout: questa funzione manuale
in ottica di sviluppo futuro sarà automatizzabile con la connessione del
pannello di controllo al gestionale dell'albergo. La centralina dovrà quindi
essere in grado, oltre che di inviare i dati dei sensori, anche di aggiornare il
codice pernottamento da utilizzare con il proprio transponder NFC: al checkin di
un ospite riceve il nuovo token.
