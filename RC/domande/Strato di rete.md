# IP
## Frammentazione
> Identifier:  
> * Identifica il pacchetto a cui fa parte il frammento (insieme a {Mittente, Destinazione})

> Flags:
> * *Reserved*  
> * *do not fragment*  
> * *more fragments*  

> Offset
> * Indica la posizione relativa in parole da 8 Byte

> In IPv6 la frammentazione non e' possibile, rimpiazzata con ICMPv6 *packet too big*
## Datagrammi
> Il campo *upper layer* identifica il protocollo di trasporto utilizzato e serve per il demux.  
> *length* considera anche l'intestazione
## IPv4
> Identificazione rete in classful e classless addressing.  
## DHCP
> Protocollo usato: UDP  
> Livello: Applicazione  
> Fasi dell'assegnazione
## NAT
> Problemi con FTP: Modalità active: il server non puo' comunicare con un client dietro NAT  
## ICMP
> Strato: Rete; anche se incapsulato in datagrammi IP (come un trasporto)  
> Priorità: piu' alta dei pacchetti dati  
## Dual stack
> Mascheramento di datagrammi IPv6 come dati di datagrammi IPv4   
# Forwarding e Routing
## Forwarding / Inoltro
> Svolto dal data plane del router  
> Sposta i pacchetti da e verso le giuste interfacce  
> Confronta l'indirizzo destinazione nel datagramma IP con la tabella di inoltro  
> Fasi dell'inoltro diretto  
> Fasi dell'inoltro indiretto
## Routing / Instradamento
> Svolto dal control plane, il quale risiede sul router o su un entità esterna  
> Sceglie il percorso migliore tra src e dst  
> Routing unicast e multicast (aggiungere appr. a livello collegamento)  
## Sistemi Autonomi
> Perché sono stati definiti: semplificare la gestione della rete globale  
> Perché si ha un solo algoritmo INTER-AS  
> Limitazioni di RIP  
> Advertisement BGP