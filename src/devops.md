# DevOps

[TODO] introduzione sulla devops...

Nelle sezioni a seguire, per ogni sotto progetto, vengono mostrate le procedure
di DevOps implementate per automatizzare e ottimizzare i processi interi che
contribuiscono al miglioramento dell'attività di sviluppo dei diversi team.

## Strategie di Version Control (globali)

- conventional commits
- gitflow (un unico branch main e un branch per ogni _feature_), release/X.Y.Z
  (o hotfix/X.Y.Z)
- commit firmati
- pull request per ogni issue
- pull-based merge come default

## Progetto `Control Unit`

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
