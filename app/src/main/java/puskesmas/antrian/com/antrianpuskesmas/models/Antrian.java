package puskesmas.antrian.com.antrianpuskesmas.models;

public class Antrian {
    public int id;
    public String w_antrian;
    public String no_antrian;
    public boolean in_progress;
    public int dilayani;
    public Poli poli;
    public Pasien pasien;

    public Antrian(int id, String w_antrian, String no_antrian, boolean in_progress, int dilayani, Poli poli, Pasien pasien) {
        this.id = id;
        this.w_antrian = w_antrian;
        this.no_antrian = no_antrian;
        this.in_progress = in_progress;
        this.dilayani = dilayani;
        this.poli = poli;
        this.pasien = pasien;
    }
}
