package stasbar.com.planlekcji4h;

import android.text.format.Time;

/**
 * Created by Stanisław on 2014-09-24.
 */
public class TimeLeft {
    public int[] tablicaPoczatek(){
        int[] poczatek = new int[9];
        poczatek[0] = 720;
        poczatek[1] = 815;
        poczatek[2] = 910;
        poczatek[3] = 1005;
        poczatek[4] = 1100;
        poczatek[5] = 1205;
        poczatek[6] = 1300;
        poczatek[7] = 1355;
        poczatek[8] = 1450;
        return poczatek;
    }

    public int[] tablicaKoniec(){
        int[] koniec = new int[9];
        koniec[0] = 805;
        koniec[1] = 900;
        koniec[2] = 955;
        koniec[3] = 1050;
        koniec[4] = 1145;
        koniec[5] = 1250;
        koniec[6] = 1345;
        koniec[7] = 1440;
        koniec[8] = 1535;
        return koniec;
    }

    public String[][] tablicaLekcje(){
        String[][] nazwaLekcji = new String[6][9];

        //poniedziałek
        nazwaLekcji[1][0] = "Matematyka";
        nazwaLekcji[1][1] = "Matematyka";
        nazwaLekcji[1][2] = "WOS";
        nazwaLekcji[1][3] = "J. POLSKI";
        nazwaLekcji[1][4] = "PST";
        nazwaLekcji[1][5] = "PST";
        nazwaLekcji[1][6] = "PST";
        nazwaLekcji[1][7] = "PST";
        nazwaLekcji[1][8] = "";
        //wtorek
        nazwaLekcji[2][0] = "";
        nazwaLekcji[2][1] = "J. POLSKI";
        nazwaLekcji[2][2] = "J. ANGIELSKI";
        nazwaLekcji[2][3] = "WF";
        nazwaLekcji[2][4] = "J.ANG ZAWODOWY";
        nazwaLekcji[2][5] = "PSK";
        nazwaLekcji[2][6] = "PSK";
        nazwaLekcji[2][7] = "PSK";
        nazwaLekcji[2][8] = "PSK";
        //sroda
        nazwaLekcji[3][0] = "MATEMATYKA";
        nazwaLekcji[3][1] = "MATEMATYKA";
        nazwaLekcji[3][2] = "PP";
        nazwaLekcji[3][3] = "J.POLSKI";
        nazwaLekcji[3][4] = "J.ANGIELSKI";
        nazwaLekcji[3][5] = "PODSTAWY TELE";
        nazwaLekcji[3][6] = "HISTORIA";
        nazwaLekcji[3][7] = "PP";
        nazwaLekcji[3][8] = "";
        //czwartek
        nazwaLekcji[4][0] = "";
        nazwaLekcji[4][1] = "J. ANGIELSKI";
        nazwaLekcji[4][2] = "HISTORIA";
        nazwaLekcji[4][3] = "FIZYKA";
        nazwaLekcji[4][4] = "GW / J. NIEMIECKI";
        nazwaLekcji[4][5] = "WF";
        nazwaLekcji[4][6] = "J. ANG ZAWODOWY";
        nazwaLekcji[4][7] = "RELIGIA";
        nazwaLekcji[4][8] = "";
        //piatek
        nazwaLekcji[5][0] = "PIOS";
        nazwaLekcji[5][1] = "RELIGIA";
        nazwaLekcji[5][2] = "J. POLSKI";
        nazwaLekcji[5][3] = "J. POLSKI";
        nazwaLekcji[5][4] = "WF";
        nazwaLekcji[5][5] = "J. ANGIELSKI";
        nazwaLekcji[5][6] = "";
        nazwaLekcji[5][7] = "";
        nazwaLekcji[5][8] = "";

        return nazwaLekcji;
    }

    public int toTime(int arg){
        int hours = arg / 100;
        int minutes = arg % 100;
        return hours*60+minutes;
    }

    String wynik;
    int lekcja = 0;
    int pozostaloMin;
    int pozostaloGodzin;
    int pierwszaLekcja;
    int ostatniaLekcja;
    public String getLesson(Time time,int[] poczatek,int[] koniec, String[][] lekcje){
        int czas = time.hour*100 + time.minute;
        pierwszaLekcja=getPierwszaLekcja(time.weekDay);
        ostatniaLekcja=getOstatniaLekcja(time.weekDay);

        for (int x = pierwszaLekcja; x <= ostatniaLekcja; x++) {
            //sobota
            if (time.weekDay==6) {
                wynik = lekcje[1][getPierwszaLekcja(1)];
            }
            //niedziela
            else if (time.weekDay==0){
                wynik = lekcje[1][getPierwszaLekcja(1)];
            }
            //piatek po lekcjach
            else if (time.weekDay==5&& czas>koniec[ostatniaLekcja]){
                wynik = lekcje[1][getPierwszaLekcja(1)];
            }

            //przed lekcjami
            else if (czas<poczatek[pierwszaLekcja]) {
                wynik = lekcje[time.weekDay][pierwszaLekcja];
            }
            //po lekcjach
            else if (czas>koniec[ostatniaLekcja]) {
                wynik = lekcje[time.weekDay + 1][getPierwszaLekcja(time.weekDay + 1)];
            }
            //normalne
            else if(czas > poczatek[x] && czas < koniec[x]){
                lekcja = x;
                wynik="PRZERWA";
            }
            else if (czas < poczatek[x] && czas > koniec[x-1]) {
                lekcja = x;
                wynik = lekcje[time.weekDay][lekcja];

            }
        }

    return wynik;

    }
    public String getTime(Time time,int[] poczatek,int[] koniec){
        int czas = time.hour*100 + time.minute;
        pierwszaLekcja=getPierwszaLekcja(time.weekDay);
        ostatniaLekcja=getOstatniaLekcja(time.weekDay);

        for (int x = pierwszaLekcja; x <= ostatniaLekcja; x++) {
            //sobota
            if (time.weekDay==6){
                pozostaloGodzin = (toTime(poczatek[pierwszaLekcja]+4800) - toTime(czas))/60;
                pozostaloMin = (toTime(poczatek[pierwszaLekcja]+4800) - toTime(czas))%60;
            }
            //niedziela
            else if (time.weekDay==0) {
                pozostaloGodzin = (toTime(poczatek[pierwszaLekcja] + 2400) - toTime(czas)) / 60;
                pozostaloMin = (toTime(poczatek[pierwszaLekcja] + 2400) - toTime(czas)) % 60;
            }
            //piatek po lekcjach
            else if ((time.weekDay==5)&& (czas>koniec[getOstatniaLekcja(time.weekDay)])) {
                pozostaloGodzin = (toTime(poczatek[pierwszaLekcja] + 7200) - toTime(czas)) / 60;
                pozostaloMin = (toTime(poczatek[pierwszaLekcja] + 7200) - toTime(czas)) % 60;
            }
            //po lekcjach
            else if(czas>koniec[getOstatniaLekcja(time.weekDay)]){
                pozostaloGodzin = (toTime(poczatek[getPierwszaLekcja(time.weekDay+1)] + 2400) - toTime(czas)) / 60;
                pozostaloMin = (toTime(poczatek[getPierwszaLekcja(time.weekDay+1)] + 2400) - toTime(czas)) % 60;
            }
            //przed lekcjami
            else if(czas<poczatek[getPierwszaLekcja(time.weekDay)]){
                pozostaloGodzin = (toTime(poczatek[getPierwszaLekcja(time.weekDay)]) - toTime(czas)) / 60;
                pozostaloMin = (toTime(poczatek[getPierwszaLekcja(time.weekDay)]) - toTime(czas)) % 60;
            }
            //normalne
            else if(czas > poczatek[x] && czas < koniec[x]){
                lekcja = x;
                pozostaloMin = toTime(koniec[lekcja]) - toTime(czas);
            }
            else if (czas < poczatek[x] && czas > koniec[x-1]) {
                lekcja = x;
                pozostaloMin = toTime(poczatek[lekcja]) - toTime(czas);
            }
        }

        String reGodzin = String.valueOf(pozostaloGodzin);
        String reMin= String.valueOf(pozostaloMin);
        if(pozostaloGodzin!=0){
            return "Za "+reGodzin+"h "+reMin+"min";
        }else {
            return "Za "+reMin+"min";
        }

    }

    public int getPierwszaLekcja(int dzienTygodnia){
        switch(dzienTygodnia){
            case 1:
                return 0;

            case 2:
                return 1;

            case 3:
                return 0;

            case 4:
                return 1;

            case 5:
                return 0;

            default:
                return 1;
        }
    }
    public int getOstatniaLekcja(int dzienTygodnia){
        switch(dzienTygodnia){
            case 1:
                return 7;
            case 2:
                return 8;

            case 3:
                return 7;

            case 4:
                return 7;

            case 5:
                return 5;

            default:
                return 6;
        }
    }

}
