package com.example.katalogfilm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ProfilFragment extends Fragment {

    public ProfilFragment() {
        // Konstruktor kosong untuk Fragment
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(
                R.layout.fragment_profil,
                container,
                false
        );

        LinearLayout kartuFavorit = view.findViewById(R.id.kartuFavorit);
        TextView txtJumlahFavorit = view.findViewById(R.id.txtJumlahFavorit);

        perbaruiJumlahFavorit(txtJumlahFavorit);

        kartuFavorit.setOnClickListener(v -> {
            ArrayList<Film> favorit = FavoritManager.getDaftarFavorit();

            if (favorit.isEmpty()) {
                Toast.makeText(
                        requireContext(),
                        "Belum ada film favorit. Pilih film dari menu Film dulu, ya!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            String[] judulFilm = new String[favorit.size()];

            for (int i = 0; i < favorit.size(); i++) {
                judulFilm[i] = favorit.get(i).getJudul();
            }

            new AlertDialog.Builder(requireContext())
                    .setTitle("Film Favorit Pilihanmu")
                    .setItems(judulFilm, (dialog, which) -> {
                        Film film = favorit.get(which);

                        android.content.Intent intent =
                                new android.content.Intent(
                                        requireContext(),
                                        DetailActivity.class
                                );

                        intent.putExtra("judul", film.getJudul());
                        intent.putExtra(
                                "info",
                                film.getGenre() + " | " + film.getTahun()
                                        + " | Rating " + film.getRating() + "/10"
                        );
                        intent.putExtra("sinopsis", film.getSinopsis());
                        intent.putExtra("gambar", film.getGambar());

                        startActivity(intent);
                    })
                    .setNegativeButton("Tutup", null)
                    .show();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        View view = getView();
        if (view != null) {
            TextView txtJumlahFavorit =
                    view.findViewById(R.id.txtJumlahFavorit);
            perbaruiJumlahFavorit(txtJumlahFavorit);
        }
    }

    private void perbaruiJumlahFavorit(TextView textView) {
        if (textView != null) {
            textView.setText(
                    "Favorit (" + FavoritManager.getJumlahFavorit() + ")"
            );
        }
    }
}
