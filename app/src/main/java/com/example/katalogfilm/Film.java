
package com.example.katalogfilm;

public class Film {
    private String judul;
    private String genre;
    private String tahun;
    private String rating;
    private String sinopsis;
    private int gambar;

    public Film(String judul, String genre, String tahun,
                String rating, String sinopsis, int gambar) {
        this.judul = judul;
        this.genre = genre;
        this.tahun = tahun;
        this.rating = rating;
        this.sinopsis = sinopsis;
        this.gambar = gambar;
    }

    public String getJudul() { return judul; }
    public String getGenre() { return genre; }
    public String getTahun() { return tahun; }
    public String getRating() { return rating; }
    public String getSinopsis() { return sinopsis; }
    public int getGambar() { return gambar; }
}
