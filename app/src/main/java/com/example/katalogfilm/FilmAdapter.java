
package com.example.katalogfilm;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> {

    private final ArrayList<Film> daftarFilm;
    private final ArrayList<Film> semuaFilm = new ArrayList<>();

    public FilmAdapter(ArrayList<Film> daftarFilm) {
        this.daftarFilm = new ArrayList<>(daftarFilm);
        this.semuaFilm.addAll(daftarFilm);
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_film, parent, false);

        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull FilmViewHolder holder, int position) {

        Film film = daftarFilm.get(position);
        String judul = film.getJudul();

        holder.imgPoster.setImageResource(film.getGambar());
        holder.txtJudul.setText(judul);

        holder.txtInfo.setText(
                film.getGenre() + " | " + film.getTahun()
                        + " | Rating " + film.getRating() + "/10"
        );

        holder.txtSinopsis.setText(film.getSinopsis());

        // Tampilkan status favorit yang tersimpan bersama
        perbaruiTombolFavorit(holder, judul);

        holder.btnFavorit.setOnClickListener(v -> {
            if (FavoritManager.isFavorit(judul)) {
                FavoritManager.hapusFavorit(judul);

                Toast.makeText(
                        v.getContext(),
                        judul + " dihapus dari favorit",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                FavoritManager.tambahFavorit(film);

                Toast.makeText(
                        v.getContext(),
                        judul + " ditambahkan ke favorit",
                        Toast.LENGTH_SHORT
                ).show();
            }

            perbaruiTombolFavorit(holder, judul);
        });

        holder.btnDetail.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);

            intent.putExtra("judul", film.getJudul());
            intent.putExtra(
                    "info",
                    film.getGenre() + " | " + film.getTahun()
                            + " | Rating " + film.getRating() + "/10"
            );
            intent.putExtra("sinopsis", film.getSinopsis());
            intent.putExtra("gambar", film.getGambar());

            v.getContext().startActivity(intent);
        });
    }

    private void perbaruiTombolFavorit(
            FilmViewHolder holder, String judul) {

        holder.btnFavorit.setText(
                FavoritManager.isFavorit(judul)
                        ? "♥ Hapus Favorit"
                        : "♡ Tambah Favorit"
        );
    }

    public void filter(String kataKunci) {
        daftarFilm.clear();

        String kata = kataKunci.toLowerCase().trim();

        if (kata.isEmpty()) {
            daftarFilm.addAll(semuaFilm);
        } else {
            for (Film film : semuaFilm) {
                if (film.getJudul().toLowerCase().contains(kata)) {
                    daftarFilm.add(film);
                }
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return daftarFilm.size();
    }

    static class FilmViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPoster;
        TextView txtJudul, txtInfo, txtSinopsis;
        Button btnDetail, btnFavorit;

        FilmViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.imgPoster);
            txtJudul = itemView.findViewById(R.id.txtJudul);
            txtInfo = itemView.findViewById(R.id.txtInfo);
            txtSinopsis = itemView.findViewById(R.id.txtSinopsis);
            btnDetail = itemView.findViewById(R.id.btnDetail);
            btnFavorit = itemView.findViewById(R.id.btnFavorit);
        }
    }
}
