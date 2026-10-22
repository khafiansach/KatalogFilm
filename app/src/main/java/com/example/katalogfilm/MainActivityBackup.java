package com.example.katalogfilm;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivityBackup extends AppCompatActivity {

    private EditText searchFilm;
    private FilmAdapter filmAdapter;
    private ArrayList<Film> daftarFilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View root = findViewById(R.id.main);

        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
                Insets systemBars = insets.getInsets(
                        WindowInsetsCompat.Type.systemBars()
                );

                v.setPadding(
                        systemBars.left,
                        systemBars.top,
                        systemBars.right,
                        systemBars.bottom
                );

                return insets;
            });
        }

        searchFilm = findViewById(R.id.searchFilm);
        RecyclerView recyclerFilm = findViewById(R.id.recyclerFilm);

        // DATA FILM
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

        // PENGATURAN RECYCLERVIEW
        recyclerFilm.setLayoutManager(
                new LinearLayoutManager(this)
        );

        filmAdapter = new FilmAdapter(daftarFilm);
        recyclerFilm.setAdapter(filmAdapter);

        // CEK JUMLAH DATA FILM
        Toast.makeText(
                this,
                "Jumlah film awalS: " + daftarFilm.size(),
                Toast.LENGTH_LONG
        ).show();

        // FITUR PENCARIAN FILM
        if (searchFilm != null) {
            searchFilm.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(
                        CharSequence s,
                        int start,
                        int count,
                        int after
                ) {
                }

                @Override
                public void onTextChanged(
                        CharSequence s,
                        int start,
                        int before,
                        int count
                ) {
                    filmAdapter.filter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }
}
