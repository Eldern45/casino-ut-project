Autorid: Timofei Šinšakov, Jessenia Tsenkman


Projekti eesmärk oli luua lihtsustatud tööversioon kasiinost. Selleks loodi kontosüsteem ja 2 kasiinomängu - Blackjack ja Täringud. Kogu kasutajaga suhtlus on realiseeritud läbi java.swing teegi. Programmi käivitamisel tervitab kasutajat tervitusaken, mis pakub võimalust luua konto või sisse logida juba olemasolevasse. Konto loomisel pakutakse sisestada e-post, soovitud kasutajanimi ja parool. Eduka konto loomise korral kantakse kasutaja virtuaalsele kontole 50 eurot. Pärast konto loomist saab sellesse sisse logida. Selleks on vajalik e-post ja parool. Seejärel näeb kasutaja menüüd, kus saab vaadata hetkekontot, muuta kasutajanime, muuta parooli ja alustada mängimist mänge. Praegu on realiseeritud 2 mängu: Blackjack ja Täringud. Blackjack on 21 mäng ja Täringud on arvamismäng, milline number kukub 6-küljelisel täringul. Mõlemas mängus on diileriks arvuti. Raha kontole kandmise ja väljavõtmise funktsionaalsus puudub, kuna see on kasiino prooviversioon.


Main.java - Käivitab programmi
ConsoleInterface.java - Põhiklass. Selle kaudu toimub suhtlus kasutajaga.
Account.java - Klass kasutajakontode objektide loomiseks
AccountManager.java - Abiklass kontode haldamiseks. Näiteks sisaldab kasulikke meetodeid, mis võimaldavad kontosid salvestada ja laadida (saveAccounts(), loadAccounts())
Blackjack, Card, Deck, Hand - Need kõik on klassid Blackjack-mängu realiseerimiseks.
Rohkemate üksikasjade kohta meetodite ja koodi kirjelduse kohta saab leida programmi koodist, lugedes kommentaare.


Projekt jagunes kaheks osaks: kontosüsteemi loomine ja mängude realiseerimine. Allpool on toodud iga grupiliikme panus projekti:

Timofei: Kontosüsteemi loomine (AccountManager, suur osa klassist ConsoleInterface), kommentaaride lisamine, vigade ja puuduste parandamine koodis (enamasti seotud mängudega), meeskonna liider. Projektile kulus umbes 15-20 tundi.
Jessenia: Kontosüsteemi prototüübi loomine, Blackjacki ja Täringute mängude loomine (ja vastavate meetodite/klasside loomine), kommentaaride lisamine. Projektile kulus umbes 5-10 tundi.


Projekti käigus sain aru, et mul puudub kõvadus liidrina. Ma ei seadnud Jesseniale selgeid tähtaegu ega pööranud vigadele suurt tähelepanu, sest tahtsin näidata end parimast küljest. Ent lõpuks oleks pidanud looma puhtad töösuhted, kuna Jessenia osutus mitte eriti vastutustundlikuks inimeseks, mis sundis mind suurema osa tööst viimasel hetkel üle võtma. Mis puutub projektisse endasse, siis minu jaoks oli uus tehnoloogia java.swing. Enne selle valdamist kulus märkimisväärne aeg. Muid probleeme ei tekkinud.


Ma arvan, et projektis on kasutajaliides ja mängud hästi realiseeritud. Teisest küljest võin öelda, et koodi loogika ja struktuur võiksid olla paremad. Tulevikus püüan kirjutada koodi puhtamalt.


Kood läbis vigade tuvastamiseks erinevaid tegevusi, näiteks nuppude vajutamine erinevas järjekorras ja programmi käitumise analüüs pärast selliseid tegevusi. Näiteks selle lähenemisviisi abil avastati viga, mille tõttu Blackjacki mäng ei salvestanud mängija kontosaldo töö ajal, vaid salvestas selle faili, mis võis tekitada konflikti, kui kasutaja soovis minna teise mängu.