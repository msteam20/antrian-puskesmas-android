package puskesmas.antrian.com.antrianpuskesmas.models;

public class Pasien {
    public int id;
    public String nama;
    public String alamat;
    public String kelamin;
    public String tgl_lahir;
    public String tempat_lahir;
    public String no_askes;
    public String no_telp;

    public Pasien() {

    }

    public Pasien(int id, String nama, String alamat, String kelamin, String tgl_lahir, String tempat_lahir, String no_askes, String no_telp) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.kelamin = kelamin;
        this.tgl_lahir = tgl_lahir;
        this.tempat_lahir = tempat_lahir;
        this.no_askes = no_askes;
        this.no_telp = no_telp;
    }
}
