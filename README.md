# Elevator
## Opis
Symulator systemu wind napisany w Javie.
## Uruchamianie
By uruchomić program, wykorzystaj plik *jars/Elevator.jar*. Wymagana wersja Javy: **11**
## Sterowanie
* Użyj panelu po lewej stronie ekranu, by stworzyć nowy układ wind.
* Użyj przycisku **Step** na dole ekranu, by wykonać krok symulacji.
* Użyj przycisków **UP** i **DOWN** po lewej stronie, by przyzwać windę na dane piętro.
* Kliknij lewym przyciskiem myszy na drzwi windy *i* na piętrze *j*, by zasymulować kliknięcie przycisku *j* wewnątrz kabiny windy *i*. 
* Kliknij prawym przyciskiem  myszy na drzwi windy *i* na piętrze *j*, by wymusić przemieszczenie windy *i* na piętro *j*.
* Kliknij przycisk **Clear**, by zresetować przyciski wewnątrz kabiny danej windy.
## Sposób działania
Każda winda wykonuje kursy w górę i w dół i stara się w danym momencie utrzymać swój kierunek ruchu: jeżeli jedzie w górę/dół, to stara się kontynuować ruch do momentu odwiedzenia najwyżej/najniżej położonego złożonego jej zadania. Jadąc w górę, winda zatrzyma się na piętrze, jeżeli jest ono jej piętrem docelowym albo ktoś na zewnątrz wyraził chęć jazdy w górę; analogicznie przy jeździe w dół. Jeżeli pewną liczbę kroków symulacji winda nie otrzyma żadnego zadania, zacznie wracać na parter.

Po kliknięciu przycisku UP lub DOWN, system stara się przydzielić zadanie odwiedzenia tego piętra windzie, która jest "najbliżej", czyli windzie, która po otrzymaniu zadania otworzyłaby drzwi na tym piętrze najwcześniej (uwzględniając zasady ruchu wind). Winda, która otrzyma takie zadanie, stara się na nowo znaleźć najbliższe windy dla przycisków UP i DOWN, które są jej przydzielone, jako że jej tor ruchu mógł ulec wydłużeniu.  
