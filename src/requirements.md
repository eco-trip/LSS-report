# Requisiti di sistema

Di seguito vengono elencati prima i requisiti di sistema funzionali, suddivisi
per i diversi sottodomini di Ecotrip, e successivamente quelli non funzionali.

1. Requisiti funzionali
    1. Authentication
        1. Autenticazione utenti con email e password
            1. Registrazione nuovi utenti
            2. Ruoli previsti: amministratori Ecotrip e albergatori
    2. Room Monitoring
        1. Raccolta dati con campionamento ogni secondo da diversi sensori
            1. Rilevazione del consumo energetico con sensore di corrente
            2. Rilevazione del consumo di acqua tramite due flussometri
            3. Rilevazione della temperatura di acqua calda / fredda tramite due sensori di temperatura
            4. Rilevazione dello stato delle tende (aperte/chiuse) della stanza attraverso un sensore di luminosità installato su ogni finestra
            5. Rilevazione della temperatura della stanza mediante un sensore di temperatura ambientale
            6. Rilevazione della percentuale di umidità della stanza tramite un sensore di umidità ambientale
        1. Identificazione presenza ospite all'interno della stanza (opzionale)
        2. Aggregazione dati campionati ogni 5 secondi
            1. Calcolo del consumo energetico
            2. Calcolo del consumo di acqua
        3. Invio ogni 5 secondi dei dati rilevati ed aggregati a piattaforma cloud
    3. Guest Authorization
        1. Generazione token univoco, firmato e verificabile al check-in dell'ospite contenete identificativi di hotel, stanza e pernottamento
        2. Sincronizzazione su centralina del token generato in remoto
        3. Controllo sensore NFC su centralina per trasferire il token a dispotivo mobile
        4. Il tag NFC deve funzionare in modo da avviare sullo smartphone la web
           app Ecotrip senza azione dell'utente, ma solo all'avvicinamento del
           dispositivo al tag
    4. Control Unit Management
        1. Configurazione da parte degli amministratori Ecotrip di nuove
           centraline installate assegnando identificativo di hotel e stanza
        2. Verifica dello stato di funzionamento da remoto da parte degli
           amministratori Ecotrip
    5. Control Unit Maintenance
        1. Accesso da remoto a centralina installata da parte agli
           amministratori Ecotrip
        2. Aggiornamento automatico del software della centralina
    6. Hotel Management
        1. Accesso a pannello di controllo riservato solo agli amministratori ecotrip
        2. Gestione completa degli hotel con informazioni quali: identificativo,
           nome, indirizzo e costo dell'energia nei termini di CO2/Kilowatt
        3. Possibilità di creare l'account dell'albergatore per un hotel
        4. Gestione completa delle camere di un'hotel con informazioni quali:
           identificativo e numero

    7. Stay Management
        1. Accesso a pannello di controllo riservato agli amministratori ecotrip ed all'albergatore proprietario dell'hotel
        2. Visualizzazione dei pernottamenti suddivisi per camera
        3. Per ogni camera possibilità di eseguire check-in e check-out di un ospite
        4. Visualizzazione del punteggio "sostenibilità" del pernottamento con relativo consumo totale di CO2
        5. Visualizzazione dei consumi istantanei di una camera

    8. Data Elaboration
        1. Calcolo della CO2 a partire dai dati di un pernottamento
        2. Algoritmo per il calcolo del punteggio "sostenibilità" di un
           pernottamento a partire dalla CO2 generata e considerando il
           comportamento tenuto dall'ospite:
            1. uso eccessivo di acqua
            2. uscire dalla camera lasciando il condizionatore accesso e le
               tende aperte di giorno  
    9. Guest App
        1. Avvio automatico con avvicinamento del dispositivo mobile al tag NFC
        2. Visualizzazione dei dati del pernottamento corrente: informazioni
           generali, consumo CO2 e "punteggio sostenibilità"
        3. Visualizzazione dei dati dei pernottamenti passati
2. Requisiti non funzionali
    1. Garantire privacy per gli ospiti sia per quanto riguarda la scelta dei
       meccanismi/sensori utilizzati dalla centralina, sia per quanto concerne
       la possibilità che qualcuno possa accedere ai dati non propri (esempio
       ospiti che accedono a dati di altri ospiti)

    2. Scalabilità del sistema cloud all'occorrenza, sia per quanto concerne la
       ricezione ed elaborazione dei dati che per gli ospiti connessi
