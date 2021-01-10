# Proiect Energy System Etapa 2

## Despre

Proiectare Orientata pe Obiecte, Seriile CA, CD
2020-2021

<https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa2>


Student: Anghel Mihai-Gabriel 
Grupa: 323CD


## Implementare

### Entitati

Am creat pachete specifice rolului pe care il au clasele:
 - fileio: Aici se afla clasele in care salvez datele de input, datele de output si clase
	   cu ajutorul carora fac parsarea datelor catre input respectiv output.
 - common: clasa de constante care ajuta la input.
 - dataprocessing: In acest pachet se afla clasele cu care se realizarea procesarea problemei.
 - strategies: Aici se afla interfata si clasele specifice strategiilor
 - entities: Pachetul contine o clasa de tip enum (tipurile de energie)

Clase:  - Solve - ruleaza simularea sistemului
	- CheckBankrupt - verifica daca toti distribuitorii au ajuns sau nu la faliment
	- ApplyStrategy - aplica strategia aferenta fiecarui distribuitor
	- Contracts - asigneaza contractele dintre consumatori si distribuitori si contractele 
		      dintre distribuitori si producatori
	- FormulasCompute - se calculeaza diferite formule din cerinta si le seteaza:
 			    productionCost, ContractPrice, profit
	- Income - adauga venitul lunar in portofoliul consumatorilor
	- Payments - face plata datoriilor consumatorilor si distribuitorilor 
	- ProductionContracts - ajuta la semnarea contractelor dintre distribuitori si producatori
			        pentru a evita duplicarea codului(in ceea ce priveste strategiile)
	- Updates - face update-urile lunare a tuturor entitatilor
	- GreenStrategy, PriceStrategy, QuantityStrategy - Toate acestea sorteaza producatorii
	  in functie de cerinta, iar apoi se semneaza contractele specifice.

Clasele din fileio nu le mai detaliez pentru ca toate au rolul pe care l-am explicat la 
pachetul fileio.


### Flow

Entrypoint-ul este in main. Se parseaza datele de input cu ajutorul claselor InputLoader si Input.
(InputLoader converteste datele din tipul json (din baza de date), iar Input stocheaza toate
listele(baza de date) pentru a le parsa mai usor). 
Clasa care controleaza flow-ul este Solve(aceasta interactioneaza si agrega majoritatea claselor). 
Din main se se apeleaza start-ul transmitand prin parametri data de baze.
Se ruleaza runda initiala apoi celelalte runde obisnuite oprindu-se procesul fie la sfarsitul
celor 'nrTurns' luni, fie atunci cand toti distribuitorii sunt falimentari(aici se foloseste 
clasa CheckBankrupt).

In runda initiala: - Distribuitorii isi aleg producatorii (clasa Contracts)
	           - Se calculeaza si seteaza formulele distribuitorilor(clasa FormulasCompute)
   		   - Consumatorii isi primesc venitul lunar (clasa Income)
		   - Consumatorii isi aleg distribuitorii (clasa Contracts)
		   - Consumatorii si distribuitorii isi platesc ratele / taxele(clasa Payments)

In rundele obisnuite: - Se fac update-urile contractelor, distribuitorilor, consumatorilor si
			producatorilor (Clasa Updates)
		      - Se calculeaza si seteaza formulele distribuitorilor(clasa FormulasCompute)
   		      - Consumatorii isi primesc venitul lunar (clasa Income)
		      - Consumatorii isi aleg distribuitorii (clasa Contracts)
		      - Consumatorii si distribuitorii isi platesc ratele / taxele(clasa Payments)
		      - Se face update-ul producatorilor
		      - Distribuitorii isi aleg producatorii doar daca producatorii cu care au
		        deja incheiat contracte au suferit modificari
		      - Se face update la lista de contracte a distribuitorilor(se scot 
			consumatorii bankrupt)
		      - In final se face update la statusul lunar al producatorilor, clasa Updates
 			interactionand cu clasa MonthlyStats.

In momentul in care se face update la producatori, se foloseste clasa ApplyStrategy cu ajutorul careia
se aplica una dintre strategiile distribuitorului si astfel se semneaza contractele.
La sfarsitul simularii se construiesc entitatile de output(cu clasa Output) pe baza starii entitatilor 
inrolate in simulare. Se parseaza datele de output si se face afisarea in fisierul corespunzator 
(clasa OutputLoader).


### Elemente de design OOP

Am folosit incapsulare oriunde am considerat ca este nevoie. Majoritatea campurilor sunt private,
iar accesul la ele se face doar prin getter / setter.
Un alt exemplu de incapsulare ar putea fi clasa Solve:
Metodele initialMonth si regularMonth sunt private, iar metoda startSimulation este public tocmai 
pentru ca accesul din main sa fie posibil doar la startSimulation care ruleaza in ordine corecta 
testele.


### Design patterns

In ceea ce priveste Design Patterns, am folosit Observer, Strategy si Singleton.

Observer: Distribuitorii sunt Observers si producatorii sunt Observable
	  In momentul in care producatorii isi modifica proprietatile, distribuitorii acestora sunt 
	  notificati printr-un flag setat astfel incat acestia sa stie ca in momentul alegerii 
	  producatorilor ei trebuie sa isi reaplice strategia de alegere a furnizorilor de energie.
	  Dupa ce isi aleg contractele, distribuitorii isi seteaza dinnou flag-ul pentru a nu mai 
	  participa la alegerea contractelor pana in momentul in care producatorii de la care iau 
 	  energie electrica nu isi modifica starea.

Strategy: Am folosit acest design pattern in cazul distribuitorilor care isi aleg producatorii.
	  M-am folosit de o interfata(Strategy), de 3 clase(GreenStrategy, PriceStrategy,
	  QuantityStrategy) care implementeaza aceasta interfata si de clasa ApplyStrategy
	  care primeste o strategie din cele 3 si apeleaza metoda de alegere a contractelor a
	  strategiei corespunazoare.
	  Fiecare strategie sorteaza producatorii conform cerintei si apoi asigneaza contractele
	  folosind clasa ProductionContracts pentru a minimiza duplicarea codului.

Singleton: Am folosit singleton pentru a folosi aceeasi instanta a clasei Solve atat in main,
	   unde se incepe simularea, cat si in metoda startSimulation din Solve pentru a rula
	   runda initiala si cele obisnuite.


### Dificultati intalnite, limitari, probleme

Pentru a rezolva probleme de checkstyle am adaugat o descriere succinta a claselor si metodelor.
Deasemenea, detalierea realizarii proceselor se afla in comentarii, acolo unde am considerat ca
ajuta acesta fiind si motivul pentru care am incercat sa nu folosesc o descriere foarte detaliata
si obositoare in README.

[optional ## Feedback, comments

