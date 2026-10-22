package com.example.katalogfilm;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private WebView webView;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView poster = findViewById(R.id.detailPoster);
        TextView judul = findViewById(R.id.detailJudul);
        TextView info = findViewById(R.id.detailInfo);
        TextView sinopsis = findViewById(R.id.detailSinopsis);
        Button kembali = findViewById(R.id.btnKembali);

        webView = findViewById(R.id.detailWebView);
        videoView = findViewById(R.id.detailVideoView);

        // Mengambil data film dari Intent
        String namaFilm = getIntent().getStringExtra("judul");
        String infoFilm = getIntent().getStringExtra("info");
        String sinopsisFilm = getIntent().getStringExtra("sinopsis");
        int gambarFilm = getIntent().getIntExtra("gambar", 0);

        // Menampilkan detail film
        judul.setText(namaFilm != null ? namaFilm : "Detail Film");
        info.setText(infoFilm != null ? infoFilm : "");
        sinopsis.setText(sinopsisFilm != null ? sinopsisFilm : "");

        if (gambarFilm != 0) {
            poster.setImageResource(gambarFilm);
        }

        // WebView untuk trailer YouTube
        WebSettings pengaturanWeb = webView.getSettings();
        pengaturanWeb.setJavaScriptEnabled(true);
        pengaturanWeb.setDomStorageEnabled(true);
        pengaturanWeb.setMediaPlaybackRequiresUserGesture(false);
        webView.setWebChromeClient(new WebChromeClient());

        String urlTrailer = getUrlTrailer(namaFilm);

        if (urlTrailer != null) {
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl(urlTrailer);
        } else {
            webView.setVisibility(View.GONE);
        }

        // Memilih video lokal sesuai judul film
        int videoResource = getVideoResource(namaFilm);

        if (videoResource != 0) {
            Uri alamatVideo = Uri.parse(
                    "android.resource://" + getPackageName()
                            + "/" + videoResource
            );

            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoURI(alamatVideo);

            MediaController kontrolVideo = new MediaController(this);
            videoView.setMediaController(kontrolVideo);
            kontrolVideo.setAnchorView(videoView);

            videoView.setOnErrorListener((mediaPlayer, what, extra) -> {
                Toast.makeText(
                        DetailActivity.this,
                        "Video gagal diputar. Periksa file MP4.",
                        Toast.LENGTH_LONG
                ).show();
                return true;
            });
        } else {
            videoView.setVisibility(View.GONE);
        }

        // Tombol kembali
        kembali.setOnClickListener(v -> finish());
    }

    // Memilih file video berdasarkan judul film
    private int getVideoResource(String namaFilm) {
        if (namaFilm == null) {
            return 0;
        }

        switch (namaFilm) {
            case "Interstellar":
                return R.raw.trailer_demo;

            case "Inception":
                return R.raw.inception_video;

            case "The Dark Knight":
                return R.raw.dark_knight;

            default:
                return 0;
        }
    }

    // URL pemutar trailer YouTube
    private String getUrlTrailer(String namaFilm) {
        if (namaFilm == null) {
            return null;
        }

        switch (namaFilm) {
            case "Interstellar":
                return "https://www.youtube.com/embed/zSWdZVtXT7E";

            case "Inception":
                return "https://www.youtube.com/embed/YoHD9XEInc0";

            case "The Dark Knight":
                return "https://www.youtube.com/embed/EXeTwQWrcwY";

            default:
                return null;
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.stopLoading();
            webView.destroy();
        }

        if (videoView != null) {
            videoView.stopPlayback();
        }

        super.onDestroy();
    }
}
