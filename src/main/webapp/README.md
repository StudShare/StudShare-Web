# StudShare

<b>Członkowie grupy:</b><br />
Mateusz Zweigert<br />
Kamil Liwiński<br />
Adam Żuk<br />
Bartosz Gross<br /><br />

<b>Temat pracy:</b> Projekt serwisu społecznościowego do wymiany notatkami, przykładami i wiedzą między uczniami i studentami.<br /><br />

<h2><b>! </b></h2><a href="https://github.com/StudShare/StudShare/blob/master/TODO.md">Założenia do zrealizowania w ciągu semestru zimowego 2015/2016</a><br /><br />

<b>Zakres funkcjonalności:</b>

0. Zawartość
  - rozumiana jako suma elementów wchodzących w skład bazy danych notatek<br /><br />

1. Gość
  - rozumiany jako użytkownik nie posiadający konta w serwisie lub niezalogowany<br />
  - posiada możliwość zarejestrowania i zarządzania swoim kontem użytkownika - poprzez zarządzanie rozumiane jest edycja hasła, zdjęcia profilowego, wyświetlanej nazwy użytkownika oraz podanego e-maila<br />
  - posiada możliwość przeglądania zasobów serwisu bez możliwości oceniania<br />
  - nie posiada możliwości dodawania nowej zawartości do bazy danych<br /><br />

2. Zarejestrowany użytkownik
  - rozumiany jako użytkownik posiadający konto i aktualnie zalogowany w serwisie<br />
  - posiada możliwość zarządzania swoim kontem użytkownika - poprzez zarządzanie rozumiane jest edycja hasła, zdjęcia profilowego, wyświetlanej nazwy użytkownika oraz podanego e-maila<br />
  - posiada możliwość korzystania z wszystkich funkcji serwisu (przeglądanie, dodawanie nowej zawartości, ocenianie...)<br />
  - nowa zawartość może być dodana w formie tekstu jak i zdjęć<br />
  - posiada możliwość zarządzania dodanymi przez siebie zasobami (przeglądanie, edycja, usuwanie)<br /><br />

3. Moderator
  - rozumiany jest jako superużytkownik serwisu posiadający podstawowe role zarządzające<br />
  - otrzymuje powiadomienia o nowo dodanej zawartości<br />
  - posiada uprawnienia do usunięcia zawartości niezgodnej z regulaminem serwisu<br />
  - posiada uprawnienia do edycji oznaczeń (tzw. tagów) i tytułów poszczególnych elementów wchodzących w skład zawartości serwisu, jeżeli te nie są zgodne z tematyką jakiej dotyczy treść zawartości<br /><br />

4. Administrator
  - rozszerza funkcjonalność moderatora serwisu tzn. posiada te same uprawnienia co moderator oraz dysponuje dodatkowymi<br />
  - posiada możliwość zarządzania kontami użytkowników serwisu (również moderatorów) w zakresie usuwania i edycji<br />
  - posiada dostęp do ustawień działania serwisu (zmiana logiki algorytmów, wyłączanie/włączanie poszczególnych funkcji itp.)<br /><br />

<b>Składowe projektu:</b>

Na projekt składają się:

  - serwis internetowy w formie aplikacji internetowej (WEBAPP)
  - aplikacja mobilna na system operacyjny Android z możliwością publikowania zdjęć z aparatu urządzenia<br /><br />

<b>Technologie:</b> Java, HTML, CSS, JavaScript (jQuery), JAX-RS, Spring, Hibernate, Android SDK<br /><br />

<b>Schematy</b><br />

  - <a href="http://oi66.tinypic.com/2bb9qb.jpg" target="_blank">Diagram przypadków użycia</a>
  - <a href="http://i68.tinypic.com/2ufrj2a.jpg" target="_blank">Wzbogacony wizerunek (Rich Picture)</a>
