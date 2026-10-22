
package com.example.katalogfilm;

import java.util.LinkedHashMap;
import java.util.ArrayList;

public class FavoritManager {

    private static final LinkedHashMap<String, Film> daftarFavorit =
            new LinkedHashMap<>();

    public static void tambahFavorit(Film film) {
        daftarFavorit.put(film.getJudul(), film);
    }

    public static void hapusFavorit(String judul) {
        daftarFavorit.remove(judul);
    }

    public static boolean isFavorit(String judul) {
        return daftarFavorit.containsKey(judul);
    }

    public static ArrayList<Film> getDaftarFavorit() {
        return new ArrayList<>(daftarFavorit.values());
    }

    public static int getJumlahFavorit() {
        return daftarFavorit.size();
    }
}
