package stasbar.com.planlekcji4h;

/**
 * Created by Stanisław on 2014-10-17.
 */
public class Wpis {
     int dzien;
     String nazwaLekcji;
     String opis;
    Wpis(){
        this.dzien = 0;
        this.nazwaLekcji = "NazwaLekcji";
        this.opis = "opis";
    }
    Wpis(int dzien, String nazwaLekcji, String opis){
        this.dzien = dzien;
        this.nazwaLekcji = nazwaLekcji;
        this.opis = opis;
    }
    public String getDzien (){
        String tempDzien = String.valueOf(dzien);
        return tempDzien;
    }
}
