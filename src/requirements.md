
Dettagliamo di seguito i requisiti di sistema funzionali suddivisi per i diversi sottodomini del sistema Ecotrip.
Successivamente specifichiamo i requisiti non funzionali.

1. Requisiti funzionali

    1.1 Authentication

        1.1.1 Autenticazione utenti con email e password

            1.1.1.1 Registrazione nuovi utenti
            1.1.1.2 Ruoli previsti: amministratori ecotrip e albergatori
       
    
    1.2 Room Monitoring
      
        1.2.1 Raccolta dati con campionamento ogni secondo da diversi sensori
            1.2.1.1 Rilevazione del consumo energetico con sensore di corrente
            1.2.1.2 Rilevazione del consumo di acqua tramite due flussometri
            1.2.1.3 Rilevazione della temperatura di acqua calda / fredda tramite due sensori di temperatura
            1.2.1.4 Rilevazione dello stato delle tende (aperte/chiuse) della stanza attraverso un sensore di luminosità installato su ogni finestra
            1.2.1.5 Rilevazione della temperatura della stanza mediante un sensore di temperatura ambientale
            1.2.1.6 Rilevazione della percentuale di umidità della stanza tramite un sensore di umidità ambientale
        1.2.2 Identificazione presenza ospite all'interno della stanza (opzionale)
        1.2.3 Aggregazione dati campionati ogni 5 secondi
            1.2.3.1 Calcolo del consumo energetico
            1.2.3.1 Calcolo del consumo di acqua
        1.2.4 Invio ogni 5 secondi dei dati rilevati ed aggregati a piattaforma cloud

    1.3 Guest Authorization

        1.3.1 Generazione token univoco, firmato e verificabile al checkin dell'ospite contenete identificativi di hotel, stanza e pernottamento
        1.3.2 Sincronizzazione su centralina del token generato in remoto
        1.3.3 Controllo sensore NFC su centralina per trasferire il token a dispotivo mobile
        1.3.4 Il tag NFC deve funzionare in modo da avviare sullo smartphone la web app Ecotrip senza azione dell'utente, 
                ma solo all'avvicinamento del dispositivo al tag

    1.4 Control Unit Management

        1.4.1 Configurazione da parte degli amministratori Ecotrip di nuove centraline installate assegnando identificativo di hotel e stanza
        1.4.2 Verifica dello stato di funzionamento da remoto da parte degli amministratori Ecotrip

    1.5 Control Unit Maintenance

        1.5.1 Accesso da remoto a centralina installata da parte agli amministratori Ecotrip
        1.5.2 Aggiornamento automatico del software della centralina

    1.6 Hotel Management

        1.6.1 Accesso a pannello di controllo riservato solo agli amministratori ecotrip
        1.6.2 Gestione completa degli hotel con informazioni quali: identificativo, nome, indirizzo e costo dell'energia nei termini di CO2/Kilowatt
        1.6.3 Possibilità di creare l'account dell'albergatore per un hotel
        1.6.4 Gestione completa delle camere di un'hotel con informazioni quali: identificativo e numero

    1.7 Stay Management

        1.7.1 Accesso a pannello di controllo riservato agli amministratori ecotrip ed all'albergatore proprietario dell'hotel
        1.7.2 Visualizzazione dei pernottamenti suddivisi per camera
        1.7.3 Per ogni camera possibilità di eseguire check-in e check-out di un ospite
        1.7.4 Visualizzazione del punteggio "sostenibilità" del pernottamento con relativo consumo totale di CO2
        1.7.5 Visualizzazione dei consumi istantanei di una camera

    1.8 Data Elaboration

        1.8.1 Calcolo della CO2 a partire dai dati di un pernottamento
        1.8.2 Algoritmo per il calcolo del punteggio "sostenibilità" di un pernottamento a partire dalla CO2 generata e considerando il comportamento tenuto dall'ospite:
            1.8.2.1 uso eccessivo di acqua
            1.8.2.2 uscire dalla camera lasciando il condizionatore accesso e le tende aperte di giorno  

    
    1.9 Guest App

        1.9.1 Avvio automatico con avvicinamento del dispositivo mobile al tag NFC
        1.9.2 Visualizzazione dei dati del pernottamento corrente: informazioni generali, consumo CO2 e "punteggio sostenibilità"
        1.9.3 Visualizzazione dei dati dei pernottamenti passati
    
2. Requisiti non funzionali

    2.1 Garantire privacy per gli ospiti sia per quanto riguarda la scelta dei meccanismi/sensori utilizzati dalla centralina, 
            sia per quanto concerne la possibilità che qualcuno possa accedere ai dati non propri (esempio ospiti che accedono a dati di altri ospiti)

    2.1 Scalabilità del sistema cloud all'occorrenza, sia per quanto concerne la ricezione ed elaborazione dei dati che per gli ospiti connessi
