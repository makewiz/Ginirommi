**Aihe:** Ginirommi, kahden pelaajan korttipeli. Toteutetaan ginirommin sääntöjä (https://en.wikipedia.org/wiki/Gin_rummy)
noudattava korttipeli kahdelle pelaajalle. Peli toimii verkon välityksellä kahdella pelaajalla, tai paikallisesti tekoälyä vastaan.
Verkkopeli ominaisuus toteutetaan käyttämällä javan socketteja. Moninpeli on toteutettava kahdella tietokoneella verkon välityksellä, koska toisen pelaajan korttien näkeminen ei ole sallittua.

**Käyttäjät:** Pelaaja

**Toiminnot:**
    
* Isännöi peli
  * Toimii jos pelaajalla on nimi ja toimiva portti on annettu.
    
* Liity peliin
  * Toimii jos pelaajalla on nimi ja IP-osoite on annettu ja siihen saadaan yhteys.
    
* Pelaa tekoälyä vastaan

**Luokkakaavio**

![Luokkakaavio](dokumentointi/Luokkakaavio.png)

