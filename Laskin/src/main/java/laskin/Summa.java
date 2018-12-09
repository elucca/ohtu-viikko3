package laskin;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Summa extends Laskuoperaatio {

    public Summa(TextField tuloskentta, TextField syotekentta, Button nollaa, Button undo, Sovelluslogiikka sovellus) {
        super(tuloskentta, syotekentta, nollaa, undo, sovellus);
    }

    @Override
    public void laske(int arvo) {
        super.getSovellus().plus(arvo);
    }
 
}
