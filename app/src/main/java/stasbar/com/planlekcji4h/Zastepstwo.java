package stasbar.com.planlekcji4h;

/**
 * Created by Stanisław on 2014-10-17.
 */
public class Zastepstwo {

    String nauczycielNieobecny;
    String godzina;
    String klasa;
    String lekcjaPlanowana;
    String nauczycielZastepujacy;
    String lekcjaZamieniona;
    Zastepstwo(){
    }
    Zastepstwo(String nauczycielNieobecny, String godzina, String klasa, String lekcjaPlanowana,String nauczycielZastepujacy, String lekcjaZamieniona ){
        this.nauczycielNieobecny = nauczycielNieobecny;
        this.godzina = godzina;
        this.klasa = klasa;
        this.lekcjaPlanowana = lekcjaPlanowana;
        this.nauczycielZastepujacy = nauczycielZastepujacy;
        this.lekcjaZamieniona = lekcjaZamieniona;
    }
    Zastepstwo(String godzina, String klasa, String lekcjaPlanowana,String nauczycielZastepujacy, String lekcjaZamieniona ){
        this.nauczycielNieobecny = "";
        this.godzina = godzina;
        this.klasa = klasa;
        this.lekcjaPlanowana = lekcjaPlanowana;
        this.nauczycielZastepujacy = nauczycielZastepujacy;
        this.lekcjaZamieniona = lekcjaZamieniona;
    }

}
