package com.example.katalogfilm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FilmFragment extends Fragment {

    private EditText searchFilm;
    private FilmAdapter filmAdapter;
    private ArrayList<Film> daftarFilm;

    public FilmFragment() {
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
                R.layout.fragment_film,
                container,
                false
        );

        searchFilm = view.findViewById(R.id.searchFilm);
        RecyclerView recyclerFilm =
                view.findViewById(R.id.recyclerFilm);

        daftarFilm = new ArrayList<>();

        daftarFilm.add(new Film(
                "Interstellar",
                "Sci-Fi",
                "2014",
                "8.7",
                "Perjalanan melintasi ruang dan waktu demi menyelamatkan umat manusia.",
                R.drawable.interstellar
        ));

        daftarFilm.add(new Film(
                "Inception",
                "Action | Sci-Fi",
                "2010",
                "8.8",
                "Seorang pencuri memasuki mimpi untuk menjalankan misi berbahaya.",
                R.drawable.inception
        ));

        daftarFilm.add(new Film(
                "The Dark Knight",
                "Action | Crime",
                "2008",
                "9.0",
                "Batman menghadapi Joker yang mengancam keamanan Gotham City.",
                R.drawable.dark_knight
        ));

        recyclerFilm.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        filmAdapter = new FilmAdapter(daftarFilm);
        recyclerFilm.setAdapter(filmAdapter);

        if (searchFilm != null) {
            searchFilm.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(
                        CharSequence s, int start, int count, int after
                ) {
                }

                @Override
                public void onTextChanged(
                        CharSequence s, int start, int before, int count
                ) {
                    filmAdapter.filter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        return view;
    }
}
