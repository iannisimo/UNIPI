# [Mini web server](https://elearning.di.unipi.it/mod/assign/view.php?id=9444)

Scrivere un programma JAVA che implementi un server Http che gestisca richieste di trasferimento di file di diverso tipo (es. immagini jpeg, gif) provenienti da un browser web.

Il  server:

* sta in ascolto su una porta nota al client (es. 6789)
* gestisce richieste Http di tipo GET alla Request URL localhost:port/filename
* le connessioni possono essere non persistenti.
* usare le classi Socket e ServerSocket per sviluppare il programma server
* per inviare al server le richieste, utilizzare un qualsiasi browser