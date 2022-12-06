# Tactical Design

All'interno della seguente sezione vengono presentate tutte le scelte intraprese
relative al _tactical design_. In particolare, queste sono state suddivise a
livello di _bounded context_ (Figura ...) al fine di dettagliarne
l'implementazione della _business logic_, l'architettura e il pattern di
comunicazione.

## Control Unit

Come già anticipato la _control unit_ si occupa della misurazione dei consumi
all'interno di una stanza al fine di aggregarli e comunicarli verso l'esterno.
Data la natura _core_ del _bounded context_ e un discreto numero di concetti di
dominio da modellare, ci si è avvalsi del _domain model pattern_. Infatti la
_business logic_ viene espressa in termini di:

- _value objects_: utilizzati per rappresentare il _token_ di autorizzazione e
  le varie tipologie di misurazioni (temperatura, corrente, ecc.);
- _entities_: sfruttate per definire il concetto di rilevazione (_detection_) e
  sensore;
- _domain services_: impiegati per modellare la logica di persistenza del
  _token_ corrente (`TokenRepository`).

Il pattern adottato prevederebbe altri elementi costitutivi, come gli
_aggregators_, ma il loro impiego non è stato necessario dato il livello di
complessità del dominio.

Inoltre la struttura del _bounded context_ è sostenuta da una architettura
esagonale (Figura \ref{cleanarc}), la quale garantisce caratteristiche quali:

- _modularità_: le regole operative possono essere collaudate indipendentemente
  dalla UI, dal database o qualsiasi altro elemento esterno;
- _indipendenza dai framework_: la scelta dei _framework_ ricade solamente
  sull'ultimo strato dell'architettura, così da utilizzare questi come semplici
  strumenti evitando di sottostare a specifici vincoli;
- _indipendenza dal database_: la _business logic_ non è legata nè a un singolo
  database nè ad una specifica tipologia.

Tutto questo è reso possibile dal rispetto della _"regola della dipendenza"_, la
quale sostiene che le dipendenze presenti nel codice sorgente devono puntare
solo all'interno, verso le politiche di alto livello. Nella pratica, alcune
classi degli strati più esterni vanno ad implementare interfacce definite in
quelli più interni. Infatti, la comunicazione con gli altri _bounded context_
avviene per mezzo di _adapter_ (interfacce) descritti internamente ed
opportunamente concretizzati nell'ultimo livello.

![Clean architecture: suddivisione dei moduli\label{cleanarc}](https://github.com/eco-trip/LSS-report/blob/feature/strategic-design/src/img/cl-architecture.png?raw=true)

Si può quindi dire che la progettazione della _control unit_ è il risultato
della combinazione della terminologia definita dal'_ubiquitous language_, con
gli elementi del _domain model pattern_ ed i concetti dell'architettura
esagonale (Figura \ref{cu-uml}).

![Modellazione UML del dominio\label{cu-uml}](https://github.com/eco-trip/LSS-report/blob/feature/strategic-design/src/img/control-unit-um.png?raw=true)
