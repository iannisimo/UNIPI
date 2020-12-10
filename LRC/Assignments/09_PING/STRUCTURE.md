# Client
* PingClient.java
    * Classe principale del client.
* Const.java
    * Contiene le costanti utili per il client.
# Server
Il server e' stato creato in versione blocking e non-blocking.

Per funzionare correttamente, la versione non-blocking deve utilizzare il metodo selectNow() poiche' select() e' troppo lento a rispondere alla richiesta di messaggio e falsa le misure; in questo modo pero' il server esegue operazioni a ciclo infinito, provocando un elevato utilizzo di risorse.

* Const.java
    * Contiene le costanti utili per il server.
* ReqResp.java
    * Struttura utilizzata per mantenere la richiesta ricevuta, la risposta da reinviare, e l'indirizzo del mittente.
* NetErr.java
    * Stub che si occupa di simulare i ritardi di rete.

## Blocking
* PingServer.java
    * Classe principale del server.
* ReplyTask.java
    * Classe runnable che si occupa di simulare il ritardo di rete e di rispondere al client.
## NonBlocking
* PingServer.java
    * Classe principale del server.
* KeyWait.java
    * Classe runnable che si occupa di simulare il ritardo di rete e registrare sul canale l'operazione di scrittura.
