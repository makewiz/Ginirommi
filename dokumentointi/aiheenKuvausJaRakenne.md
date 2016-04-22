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
  * [Sekvenssikaavio](https://www.websequencediagrams.com/cgi-bin/cdraw?lz=dGl0bGUgUGFpa2FsbGluZW4gcGVsaSBib3R0aWEgdmFzdGFhbgoKS8OkeXR0w6Rqw6QtPlN0YXJ0U2NyZWVuOiBBRVZUIHAAMQ8KABgLLT5TZXJ2ZXI6IG5ldyAABgYoMCkADRZzdGFydCgADRBvY2tldCBmaXJzdAA_BwANBSgibG9jYWxob3N0IiwgcwBhBS5nZXRQb3J0KCkALxZzZWMAFywAgTsOUmVhZGVyV3JpdGVyIGx1a2lqYQCBUAYADQwoAIEjBQAYIzIAKRNzZWMAZRBCbwCBbgdCb3QoAG8GAIIuD0ZyYW1lQ29udHJvbGwAgmEIAAYPADMHMgCCaQ8AWAUAgmAVAEQRAIMTCABfDy0-TG9naW4AhAcIbmV3IAAGCwBtCgAmEQCEOQ1zZXRWaXNpYmxlKEZBTFNFAEcgACULVFJVRSkK&s=default)

**Luokkakaavio**

![Luokkakaavio](/dokumentointi/Luokkakaavio.png)

