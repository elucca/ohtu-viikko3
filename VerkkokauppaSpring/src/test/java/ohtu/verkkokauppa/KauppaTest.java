package ohtu.verkkokauppa;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {

    Pankki pankki;
    Viitegeneraattori viite;
    Varasto varasto;
    Kauppa kauppa;

    @Before
    public void setUp() {
        pankki = mock(Pankki.class);
        viite = mock(Viitegeneraattori.class);
        varasto = mock(Varasto.class);
        kauppa = new Kauppa(varasto, pankki, viite);
    }

    /*
    aloitetaan asiointi, koriin lisätään tuote, jota varastossa on ja suoritetaan ostos,
    eli kutsutaan metodia kaupan tilimaksu(), varmistettava että kutsutaan pankin metodia
    tilisiirto oikealla asiakkaalla, tilinumerolla ja summalla
     */
    @Test
    public void yksittaisenOstoksenTekoToimii() {
        // määritellään että viitegeneraattori palauttaa viitten 42s
        when(viite.uusi()).thenReturn(42);

        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // tehdään ostokset
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        kauppa.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(5));
    }

    /*
    aloitetaan asiointi, koriin lisätään kaksi eri tuotetta, joita varastossa on ja
    suoritetaan ostos, varmistettava että kutsutaan pankin metodia tilisiirto oikealla
    asiakkaalla, tilinumerolla ja summalla
     */
    @Test
    public void useanEriTuotteenOstoToimii() {
        // määritellään että viitegeneraattori palauttaa viitten 42s
        when(viite.uusi()).thenReturn(42);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10,
        // ja tuote 2 on omenamehu jonka hinta on 2 ja saldo 8
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(8);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "omenamehu", 2));

        // ostokset
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("pekka", "12345");

        // varmistus
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(7));
    }

    /*
    aloitetaan asiointi, koriin lisätään kaksi samaa tuotetta jota on varastossa tarpeeksi
    ja suoritetaan ostos, varmistettava että kutsutaan pankin metodia tilisiirto oikealla
    asiakkaalla, tilinumerolla ja summalla
     */
    @Test
    public void useanSamanTuotteenOstoToimii() {
        // määritellään että viitegeneraattori palauttaa viitten 42s
        when(viite.uusi()).thenReturn(42);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // ostokset
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("pekka", "12345");

        // varmistus
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(10));
    }

    /*
    aloitetaan asiointi, koriin lisätään tuote jota on varastossa tarpeeksi ja tuote
    joka on loppu ja suoritetaan ostos, varmistettava että kutsutaan pankin metodia
    tilisiirto oikealla asiakkaalla, tilinumerolla ja summalla
     */
    @Test
    public void useanEriTuotteenOstoToimiiKunYhtaOnJaToistaEi() {
        // määritellään että viitegeneraattori palauttaa viitten 42s
        when(viite.uusi()).thenReturn(42);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10,
        // ja tuote 2 on omenamehu jonka hinta on 2 ja saldo 8
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "omenamehu", 2));

        // ostokset
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("pekka", "12345");

        // varmistus
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(5));
    }

    /*
    varmistettava, että metodin aloitaAsiointi kutsuminen nollaa edellisen ostoksen
    tiedot (eli edellisen ostoksen hinta ei näy uuden ostoksen hinnassa)
     */
    @Test
    public void aloitaAsiointiNollaaEdellisenOstoksenTiedot() {
        // määritellään että viitegeneraattori palauttaa viitten 42s
        when(viite.uusi()).thenReturn(42);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // ostokset
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("pekka", "12345");

        when(viite.uusi()).thenReturn(1);

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("pekka", "12345");

        // varmistus
        verify(pankki).tilisiirto(eq("pekka"), eq(1), eq("12345"), anyString(), eq(5));
    }

    /*
    varmistettava, että kauppa pyytää uuden viitenumeron jokaiselle maksutapahtumalle
     */
    @Test
    public void kauppaPyytaaUudenViitenumeronJokaTapahtumalle() {
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // ostokset
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("pekka", "12345");

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("pekka", "12345");

        verify(viite, times(2)).uusi();
    }

    @Test
    public void koristaPoistaminenOnnistuu() {
        // määritellään että viitegeneraattori palauttaa viitten 42s
        when(viite.uusi()).thenReturn(42);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10,
        // ja tuote 2 on omenamehu jonka hinta on 2 ja saldo 8
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(8);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "omenamehu", 2));

        // ostokset
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.poistaKorista(2);
        kauppa.tilimaksu("pekka", "12345");
        // varmistus
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(5));

    }
}
