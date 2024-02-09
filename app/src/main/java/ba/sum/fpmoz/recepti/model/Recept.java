package ba.sum.fpmoz.recepti.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties

public class Recept {
    public String naziv;
    public String sastojci;
    public String priprema;
    public String slika;

    public Recept() {
    }

    public Recept(String naziv, String sastojci, String priprema, String slika) {
        this.naziv = naziv;
        this.sastojci = sastojci;
        this.priprema = priprema;
        this.slika = slika;
    }

    public String getNaziv() {
        return naziv;
    }


    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSastojci() {
        return sastojci;
    }

    public void setSastojci(String sastojci) {
        this.sastojci = sastojci;
    }

    public String getPriprema() {
        return priprema;
    }

    public void setPriprema(String priprema) {
        this.priprema = priprema;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }
}
