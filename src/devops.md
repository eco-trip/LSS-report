# DevOps

Nella realizzazione di un progetto software, la gestione del codice sorgente e il processo di continuous integration and continuous delivery (CI/CD) sono elementi fondamentali per garantire una efficace collaborazione tra i membri del team e un deployment affidabile e rapido delle funzionalità implementate. 

In questo paragrafo verrà descritta l'organizzazione del progetto e le relative pratiche DevOps, dal lavoro di squadra alla distribuzione del software.

Di seguito una panoramica sull'organizzazione dei vari repository

- [GitHub Actions](#github-actions) Sono state create diverse Composite GitHub Actions per evitare la ridondanza del codice e mantenere i workflow degli altri repository del progetto più leggeri e riutilizzabili.

- [Control Unit](#control-unit): Repository per il software della centralina, che include tutto il codice necessario per la sua funzionalità.

- [Ecotrip](#ecotrip): Repository che gestisce la parte del progetto basata su microservizi sul cloud. Include sottomoduli per ciascun servizio e verrà discussa successivamente.
#### Strategie di Version Control (globali)

Per garantire la qualità e la tracciabilità del codice sviluppato, abbiamo adottato una rigorosa strategia di controllo di versione. 

- Conventional Commit per standardizzare la formattazione dei messaggi di commit e aiutare nella generazione automatica del changelog. 
- Commit firmati, garantendo la responsabilità individuale per il codice inserito.
- Pull basato sul rebase per garantire la pulizia delle storia del repository e una maggiore leggibilità.
- GitFlow come modello di lavoro che prevende l'utilizzo in un'unica branch con la versione stabile del codice e le caratteristiche (_feature_) sviluppate su branch separate ed unite tramite Pull Request. Per il repo che riguarda la parte cloud del progetto (Ecotrip ed i relativi sottomoduli) abbiamo utilizzato anche la branch "dev" come punto di riferimento per la versione in corso del progetto in sviluppo. Inoltre, è anche possibile utilizzare una branch di "staging" se necessario per la preparazione della versione successiva.

## GitHub Actions

### [semantic-release-action](https://github.com/eco-trip/semantic-release-action)

Questa azione segue il paradigma dei "conventional commits" per determinare la prossima versione del progetto, basandosi sulla regola SemVer (Semantic Versioning). In questo modo, la versione del progetto viene automaticamente incrementata in base alla gravità delle modifiche apportate.

È in grado di generare automaticamente un changelog, riepilogando tutte le modifiche apportate dall'ultima versione del progetto. Questo changelog viene quindi utilizzato per creare una nuova release su GitHub, rendendo più semplice e trasparente la gestione delle versioni del progetto.

### [npm-pull-request-action](https://github.com/eco-trip/npm-pull-request-action)

Azione da eseguire durante la creazione di una pull request per repository basati su `npm` (tutti quelli relativi ai microservizi cloud). In particolare, questa azione esegue il lancio di alcuni strumenti di controllo qualità del codice, tra cui:

- Prettier: un formattatore automatico di codice che aiuta a mantenere uno stile coerente ed esteticamente gradevole del codice sorgente.

- ESLint: un linter che analizza il codice sorgente alla ricerca di potenziali problemi di sintassi, buone pratiche e altre regole personalizzabili.

- Test: lanciare gli unit test per verificare che il codice sia corretto e funzionante. Inoltre, la coverage report viene generata per verificare la copertura di test del codice e identificare eventuali aree che potrebbero necessitare di ulteriori test

L'utilizzo di questi strumenti durante la creazione di una pull request consente di individuare eventuali problemi nel codice prima che questo venga integrato nella branch principale, garantendo una maggiore qualità e stabilità del progetto.

### [ci-deploy-action](https://github.com/eco-trip/ci-deploy-action)

Azione utilizzata ogni volta che viene effettuata una release di staging o produzione di uno dei microservizi cloud di ecotrip. Questa azione lancia degli script che compilano i template di AWS SAM, con l'obiettivo di creare o aggiornare l'infrastruttura AWS (Amazon Web Services) tramite Cloud Formation. La "CI Deploy Action" garantisce un deploy rapido e affidabile, rendendo più efficiente il processo di release del progetto senza la necessità di interagire con l'interfaccia grafica di Amazon ed evitando momenti di "down" dei servizi.

### [update-submodules-action](https://github.com/eco-trip/update-submodules-action): 

Questa azione serve per automatizzare il processo di aggiornamento dei submodule all'interno del  repository principale `Ecotrip`. Questa azione viene lanciata ogni volta che una branch di un sottomodulo viene aggiornata con un push di un nuovo commit. In questo modo, il repository principale è sempre aggiornato al commit corretto del sottomodulo (nella relativa branch), mantenendo una relazione consistente tra i diversi componenti del progetto. Questo permette di semplificare il processo di mantenimento del progetto garantendo la coerenza.

## Control Unit 

[rasp-control-unit](https://github.com/eco-trip/rasp-control-unit) repository per il software della centralina, che include tutto il codice necessario per la sua funzionalità.

Come già descritto nelle sezioni precedenti, il progetto relativo alla
centralina si basa su una architettura esagonale, concretamente realizzata come
una _build_ multi-progetto avvalendosi del tool di _build automation_
**Gradle**. Questa soluzione ha permesso di suddividere la complessità del
progetto in moduli interconnessi evitando una struttura monolitica. In linea con
i concetti della _clean architecture_ i moduli sviluppati sono i seguenti:

- `utils`: modulo di livello 0 che contiene strutture dati e algoritmi comuni;
- `domain`: modulo che implementa oggetti(entità) di dominio;
- `core`: modulo che sfrutta gli oggetti di dominio per implementare gli _use
  cases_;
- `room-monitoring`: modulo che implementa la logica di acquisizione ed invio
  dei consumi e fattori ambientali;
- `authorization`: modulo contenente la logica relativa alla gestione del
  _token_ di autorizzazione condiviso con l'utente (_guest_).

![Vista completa dei moduli e delle loro dipendenze](images/gradle-multi-build.png)

I moduli `authorization` e `room-monitoring` possono essere dispiegati
separatamente come servizi distinti.

### Quality assurance

Al fine di mantenere un'elevata qualità del progetto, sono stati adottati i
seguenti strumenti di _quality assurance_:

- **Gradle catalogs**: recente funzionalità di `Gradle` che ha permesso di
  centralizzare la dichiarazione delle dipendenze di progetto in un file
  chiamato catalogo, evitando la loro ripetizione in ciascun modulo. Il fine è
  quello di semplificare l'aggiornamento manuale o automatizzato di versione
  delle dipendenze.
- **checkstyle**: strumento di sviluppo che supporta i programmatori nello
  sviluppo di codice Java che aderisce ad uno standard di codifica. I file di
  configurazione utilizzati risiedono all'interno della directory
  `config/checkstyle`.
- **jacoco**: strumento di coverage di codice Java, lanciato automaticamente a
  fronte dell'esecuzione del task `build` di Gradle. Il _plugin_ può causare il
  fallimento della `build` del progetto nel caso non si raggiunga la soglia di
  _coverage_ prefissata. Infine i risultati della _coverage_ possono essere
  visualizzati attraverso i report html generati da _jacoco_ stesso.
- **spotbugs**: _fork_ del progetto
  [FindBugs](https://findbugs.sourceforge.net/), è un _plugin_ che sfrutta
  analisi statiche al fine di individuare _bugs_ all'interno di codice Java.

Siccome tutti i moduli sviluppati sono progetti Java, si è scelto di applicare a
ciascuno di essi un set di regole comuni definite all'interno di un
**convention-plugin**. Nella pratica si è realizzato un _plugin_ custom
(`java-quality-common-convention.gradle.kts`) all'interno della cartella
`/buildSrc`, successivamente incluso come dipendenza di ciascun modulo, che
oltre a comprendere i _plugin_ `java-library`, `checkstyle`, `jacoco` e
`spotbugs`, per ognuno specifica una configurazione di base, come per esempio la
percentuale minima di _coverage_.

Infine all'interno del file `settings.gradle.kts` sono stati applicati i
_plugin_ `com.gradle.enterprise` e
`org.danilopianini.gradle-pre-commit-git-hooks`. Il primo ha permesso di
generare report in grado di segnalare eventuali problematiche ed inefficienze
all'interno della `build` di Gradle, velocizzando quindi l'attività di
_debugging_. Il secondo invece ha imposto dei vincoli sintattici sui messaggi di
`commit` al fine di rispettare lo schema dei _Conventional commits_.

[TODO] Renovate

### Workflow CI e CD

Allo scopo di mantenere intatta la _build_ durante tutto il processo di
sviluppo, si è realizzato un _workflow_ (`build-and-deploy.yml`) ad hoc tramite
le Github Actions. Questo viene eseguito ogni volta che si effettua una _push_ o
una _pull request_ verso il branch `main`. Il workflow si compone di due _job_:

- `build`: come primo passo rende accessibile il repository al _workflow_
  tramite `actions/checkout@v3`, successivamente sfrutta l'action
  `DanySK/build-check-deploy-gradle-action` così da eseguire un'intera
  _pipeline_ di CI ('build', 'check' e 'clean') sul progetto Gradle ed infine
  effettua l'upload degli artefatti (`.jar`) generati avvalendosi di
  `actions/upload-artifact@v3`
- `release`: dipende dal _job_ precedente poiché necessita degli artefatti
  caricati, infatti adopera `actions/download-artifact@v3` per memorizzare
  questi all'interno del `$GITHUB_WORKSPACE` e successivamente pubblicarli
  mediante l'_action_ custom `eco-trip/semantic-release-action`, precedentemente
  descritta.

Dato che i servizi sviluppati (`control-unit`) eseguiranno solo su determinati
modelli di dispositivi, nello specifico _raspberry_, si è ritenuto non
necessario eseguire il _job_ `build` su una matrice di sistemi operativi, ne
utilizzare diverse versioni di Java.

All'interno del file di _workflow_ vi è il riferimento a un particolare _token_
(`secrets.GH_PACKAGES_TOKEN`) necessario per accedere a _packages_ privati
memorizzati tramite l'omonima funzionalità di Github Packages. Questa soluzione
ha permesso di pubblicare diversi artefatti prodotti a partire da un _fork_
personale della libreria **Pi4J**, creato per aggiungere funzionalità di basso
livello, che soddisfano specifiche esigenze, non ancora ufficialmente
supportate.

## Ecotrip

descrizione architettura con immagine

descrizione del metodo di lavoro con docker 

- Ecotrip
	- Administraton
	- App
	- CP
	- Cognito
	- DataElaboration
	- GuestAuthorization
