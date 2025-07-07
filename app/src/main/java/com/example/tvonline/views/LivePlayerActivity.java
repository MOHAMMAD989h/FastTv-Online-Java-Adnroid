package com.example.tvonline.views;

import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tvonline.R;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;

public class LivePlayerActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView surfaceView;
    private ProgressBar loadingSpinner;
    private LibVLC libVlc;
    private MediaPlayer mediaPlayer;
    private String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_player);

        surfaceView = findViewById(R.id.vlc_surface_view);
        loadingSpinner = findViewById(R.id.loading_spinner);

        // تنظیم SurfaceView و SurfaceHolder
        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(this);

        // دریافت URL ویدئو از Intent
        videoUrl = getIntent().getStringExtra("STREAM_URL");

        if (videoUrl == null) {
            Toast.makeText(this, "URL ویدئو یافت نشد.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initializePlayer() {
        if (videoUrl == null) {
            return;
        }

        if (libVlc != null && mediaPlayer != null) {
            // پلیر قبلاً مقداردهی شده است، منابع را آزاد کرده و دوباره ایجاد می‌کنیم
            releasePlayer();
        }

        loadingSpinner.setVisibility(View.VISIBLE);

        ArrayList<String> vlcArgs = new ArrayList<>();
        vlcArgs.add("-vvv"); // Log everything for debugging purposes

        try {
            // LibVLC را با آرگومان‌های لازم ایجاد کنید
            libVlc = new LibVLC(this, vlcArgs);
            mediaPlayer = new MediaPlayer(libVlc);

            // SurfaceView را به MediaPlayer متصل کنید
            mediaPlayer.getVLCVout().setVideoView(surfaceView); // روش جدیدتر برای اتصال
            mediaPlayer.getVLCVout().attachViews(); // اتصال ویو (اگر setVideoView استفاده شد)

            // Listener برای رخدادهای پخش
            mediaPlayer.setEventListener(event -> {
                switch (event.type) {
                    case MediaPlayer.Event.MediaChanged:
                        // زمانی که رسانه شناسایی و آماده پخش شد
                        loadingSpinner.setVisibility(View.GONE);
                        mediaPlayer.play(); // شروع پخش
                        break;
                    case MediaPlayer.Event.Opening:
                        // زمانی که در حال باز کردن رسانه است
                        loadingSpinner.setVisibility(View.VISIBLE);
                        break;
                    case MediaPlayer.Event.Playing:
                        loadingSpinner.setVisibility(View.GONE);
                        break;
                    case MediaPlayer.Event.Paused:
                        // پخش متوقف شد
                        break;
                    case MediaPlayer.Event.EndReached:
                        // پخش به پایان رسید، می‌توانید لوپ کنید یا کاری دیگر انجام دهید
                        mediaPlayer.stop();
                        mediaPlayer.play(); // برای لوپ کردن
                        break;
                    case MediaPlayer.Event.EncounteredError:
                        loadingSpinner.setVisibility(View.GONE);
                        Toast.makeText(LivePlayerActivity.this, "خطا در پخش ویدئو: " + event.toString(), Toast.LENGTH_LONG).show();
                        if (mediaPlayer != null) {
                            mediaPlayer.stop();
                        }
                        finish();
                        break;
                    // می توانید رویدادهای دیگر را نیز مدیریت کنید
                }
            });

            // یک Media (منبع) از URL ایجاد کنید
            Media media = new Media(libVlc, Uri.parse(videoUrl));
            media.setHWDecoderEnabled(true, true); // فعال کردن شتاب‌دهنده سخت‌افزاری

            // مدیا را برای پخش آماده کنید (بدون شروع پخش)
            mediaPlayer.setMedia(media);
            media.release(); // مدیا را آزاد کنید، MediaPlayer به آن ارجاع دارد
        }  catch (Exception genericException) { // <<<<< این بلاک برای خطاهای عمومی تر است، اضافه شده برای اطمینان بیشتر
            Toast.makeText(this, "An unexpected error occurred during VLC setup.", Toast.LENGTH_LONG).show();
            genericException.printStackTrace();
            finish();
        }
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null; // برای اطمینان از مقداردهی مجدد در صورت نیاز
        }
        if (libVlc != null) {
            libVlc.release();
            libVlc = null;
        }
        loadingSpinner.setVisibility(View.GONE);
    }

    // --- SurfaceHolder.Callback Methods ---
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        // زمانی که Surface آماده شد، پلیر را مقداردهی اولیه می‌کنیم
        initializePlayer();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        // زمانی که ابعاد Surface تغییر کرد
        if (mediaPlayer != null && mediaPlayer.getVLCVout().areViewsAttached()) {
            mediaPlayer.getVLCVout().setWindowSize(width, height);
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        // زمانی که Surface از بین رفت، پلیر را آزاد می‌کنیم
        releasePlayer();
    }

    // --- Activity Lifecycle Methods ---
    @Override
    protected void onStart() {
        super.onStart();
        // در onStart، پلیر را مقداردهی اولیه کنید اگر هنوز انجام نشده است
        if (mediaPlayer == null) {
            initializePlayer();
        } else if (!mediaPlayer.isPlaying()) {
            mediaPlayer.play(); // اگر قبلا متوقف شده بود، ادامه پخش
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // در onStop، پخش را متوقف کنید
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause(); // توقف موقت
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // در onDestroy، منابع پلیر را به طور کامل آزاد کنید
        releasePlayer();
    }
}