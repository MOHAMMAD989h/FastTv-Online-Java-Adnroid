package com.example.tvonline.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.tvonline.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";


    private DrawerLayout drawer;
    private ViewPager posterViewPager;
    private LinearLayout dotsIndicator;
    private RecyclerView networksRecyclerView;

    private int[] posterImages = {
            R.drawable.poster_image1,
            R.drawable.poster_image2,
            R.drawable.poster_image3,
            R.drawable.poster_image4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView menuIcon = findViewById(R.id.menu_icon);
        if (menuIcon != null) {
            menuIcon.setOnClickListener(v -> {
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
            });
        } else {
            Log.e(TAG, "Menu icon not found in layout!");
        }


        posterViewPager = findViewById(R.id.poster_view_pager);
        dotsIndicator = findViewById(R.id.dots_indicator);

        if (posterViewPager != null && dotsIndicator != null) {
            PosterPagerAdapter posterAdapter = new PosterPagerAdapter(posterImages);
            posterViewPager.setAdapter(posterAdapter);

            addDotsIndicator(0);

            posterViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

                @Override
                public void onPageSelected(int position) {
                    addDotsIndicator(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {}
            });
        } else {
            Log.e(TAG, "ViewPager or DotsIndicator not found in layout!");
        }


        networksRecyclerView = findViewById(R.id.networks_recycler_view);
        if (networksRecyclerView != null) {
            networksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            List<Network> networkList = new ArrayList<>();
            networkList.add(new Network("شبکه یک", R.drawable.one));
            networkList.add(new Network("شبکه دو", R.drawable.two));
            networkList.add(new Network("شبکه سه", R.drawable.three));
            networkList.add(new Network("شبکه چهار", R.drawable.four));
            networkList.add(new Network("شبکه پنج", R.drawable.five));
            networkList.add(new Network("شبکه خبر", R.drawable.news));
            networkList.add(new Network("شبکه آموزش", R.drawable.learn));
            networkList.add(new Network("شبکه قرآن", R.drawable.quran));
            networkList.add(new Network("شبکه مستند", R.drawable.doc));
            networkList.add(new Network("شبکه نمایش", R.drawable.namayesh));
            networkList.add(new Network("شبکه تماشا", R.drawable.tamasha));
            networkList.add(new Network("شبکه امید", R.drawable.omid));
            networkList.add(new Network("شبکه پویا", R.drawable.poya));
            networkList.add(new Network("شبکه نسیم", R.drawable.nasim));
            networkList.add(new Network("شبکه ورزش", R.drawable.sport));
            networkList.add(new Network("شبکه آی‌فیلم", R.drawable.ifilm));
            networkList.add(new Network("شبکه سلامت", R.drawable.health));
            networkList.add(new Network("شبکه افق", R.drawable.ofogh));
            networkList.add(new Network("جام جم یک", R.drawable.jamejam));
            networkList.add(new Network("شبکه سهند", R.drawable.sahand));
            networkList.add(new Network("شبکه اردبیل", R.drawable.ardabil));
            networkList.add(new Network("شبکه آذربایجان غربی", R.drawable.urmia));
            networkList.add(new Network("شبکه اصفهان", R.drawable.isfahan));
            networkList.add(new Network("شبکه خوزستان", R.drawable.khozestan));
            networkList.add(new Network("شبکه فارس", R.drawable.fars));
            networkList.add(new Network("العالم", R.drawable.alaalam));
            networkList.add(new Network("شبکه پرس تیوی", R.drawable.press));


            NetworkAdapter networkAdapter = new NetworkAdapter(networkList);
            networksRecyclerView.setAdapter(networkAdapter);
        } else {
            Log.e(TAG, "Networks RecyclerView not found in layout!");
        }
    }

    private void addDotsIndicator(int position) {
        if (dotsIndicator == null) {
            Log.e(TAG, "Dots indicator is null, cannot add dots.");
            return;
        }
        dotsIndicator.removeAllViews(); // Clear previous dots
        ImageView[] dots = new ImageView[posterImages.length];

        for (int i = 0; i < posterImages.length; i++) {
            dots[i] = new ImageView(this);
            if (i == position) {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dot_active, getTheme())); // Using getTheme() for API 21+
            } else {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dot_inactive, getTheme())); // Using getTheme()
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(8, 0, 8, 0); // Spacing between dots
            dotsIndicator.addView(dots[i], layoutParams);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_share_app) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "این اپلیکیشن عالی را ببینید!");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "اپلیکیشن 'تلویزیون آنلاین من' را از [لینک فروشگاه برنامه شما در اینجا] دانلود کنید");
            startActivity(Intent.createChooser(shareIntent, "اشتراک گذاری برنامه از طریق"));
        } else if (id == R.id.nav_about_us) {
            Toast.makeText(this, "این پروژه یک پروژه ازمایشی برای اشنایی برای استریم شبکه های تلوزیون هست\n email : asadpour808@gmail.com", Toast.LENGTH_LONG).show();
        }

        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    private static class PosterPagerAdapter extends PagerAdapter {
        private int[] images;

        public PosterPagerAdapter(int[] images) {
            this.images = images;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(images[position]);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ImageView) object);
        }
    }

    // --- Model Class for Network Item ---
    public static class Network {
        private String name;
        private int iconResId; // Drawable resource ID for the icon

        public Network(String name, int iconResId) {
            this.name = name;
            this.iconResId = iconResId;
        }

        public String getName() {
            return name;
        }

        public int getIconResId() {
            return iconResId;
        }
    }

    public static class NetworkAdapter extends RecyclerView.Adapter<NetworkAdapter.NetworkViewHolder> {

        private List<Network> networkList;

        public NetworkAdapter(List<Network> networkList) {
            this.networkList = networkList;
        }

        @NonNull
        @Override
        public NetworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.item_network, null);
            return new NetworkViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull NetworkViewHolder holder, int position) {
            Network network = networkList.get(position);
            holder.networkName.setText(network.getName());
            holder.networkIcon.setImageResource(network.getIconResId());

            // Handle item click for each network
            holder.itemView.setOnClickListener(v -> {
                Log.d(TAG, "Clicked on network: " + network.getName()); // Log which network was clicked

                // Base URL برای تلوبیون (ممکن است برای همه کانال‌ها یکسان نباشد)
                // Telewebion often uses specific subdomains/paths for each channel's live stream.
                // These URLs are derived from Telewebion's structure, but may require verification.
                String telewebionCdnBase = "https://cdn.telewebion.com/"; // Often used
                String telewebionCdnwBase = "https://cdnw.telewebion.com/"; // Also seen for some channels

                String targetStreamUrl = null;

                switch (network.getName()) {
                    case "شبکه یک":
                        targetStreamUrl = "https://live-lct1301.telewebion.com/ek/tv1/live/480p/index.m3u8";
                        break;
                    case "شبکه دو":
                        targetStreamUrl = telewebionCdnBase + "tv2/live/playlist.m3u8";
                        break;
                    case "شبکه سه":
                        targetStreamUrl = telewebionCdnBase + "tv3/live/playlist.m3u8";
                        break;
                    case "شبکه چهار":
                        targetStreamUrl = telewebionCdnBase + "tv4/live/playlist.m3u8";
                        break;
                    case "شبکه پنج": // شبکه تهران
                        targetStreamUrl = telewebionCdnBase + "tv5/live/playlist.m3u8";
                        break;
                    case "شبکه خبر": // IRINN
                        targetStreamUrl = telewebionCdnBase + "irinn/live/playlist.m3u8";
                        break;
                    case "شبکه آموزش": // Shabake Amouzesh
                        targetStreamUrl = telewebionCdnBase + "amouzesh/live/playlist.m3u8";
                        break;
                    case "شبکه قرآن": // Shabake Quran
                        targetStreamUrl = telewebionCdnBase + "quran/live/playlist.m3u8";
                        break;
                    case "شبکه مستند": // Shabake Mostanad
                        targetStreamUrl = telewebionCdnBase + "doctv/live/playlist.m3u8"; // based on doctv.ir
                        break;
                    case "شبکه سلامت": // Shabake Salamat
                        targetStreamUrl = telewebionCdnBase + "salamat/live/playlist.m3u8";
                        break;
                    case "شبکه نسیم": // Shabake Nasim
                        targetStreamUrl = telewebionCdnBase + "nasim/live/playlist.m3u8";
                        break;
                    case "شبکه ورزش": // Shabake Varzesh
                        targetStreamUrl = telewebionCdnBase + "varzesh/live/playlist.m3u8";
                        break;
                    case "شبکه پویا":
                        targetStreamUrl = telewebionCdnBase + "pooya/live/playlist.m3u8";
                        break;
                    case "شبکه امید": // Shabake Omid
                        targetStreamUrl = telewebionCdnBase + "omid/live/playlist.m3u8";
                        break;
                    case "شبکه نمایش": // Shabake Namayesh
                        targetStreamUrl = telewebionCdnBase + "namayesh/live/playlist.m3u8";
                        break;
                    case "شبکه تماشا": // Shabake Tamasha
                        targetStreamUrl = telewebionCdnBase + "tamasha/live/playlist.m3u8";
                        break;
                    case "شبکه آی‌فیلم":
                        targetStreamUrl = telewebionCdnBase + "ifilm/live/playlist.m3u8";
                        break;
                    case"شبکه افق":
                        targetStreamUrl = telewebionCdnBase + "ofogh/live/playlist.m3u8";
                        break;
                    case "شبکه پرس تیوی":
                        targetStreamUrl = "https://live.presstv.ir/hls/live.m3u8";
                        break;
                    case "العالم":
                        targetStreamUrl ="https://live.alalam.ir/hls/live.m3u8";
                        // or  https://alalam.iran.liara.run/live/index.m3u8
                        break;
                    case "جام جم یک":
                        targetStreamUrl = telewebionCdnBase + "jamjam1/live/playlist.m3u8";
                        break;
                    case "شبکه اصفهان":
                        targetStreamUrl = telewebionCdnBase + "isfahan/live/playlist.m3u8";
                        // : https://irib-live.iran.liara.run/live/isfahan.m3u8
                        break;
                    case "شبکه سهند":
                        targetStreamUrl = telewebionCdnBase + "sahand/live/playlist.m3u8";
                        // : https://irib-live.iran.liara.run/live/sahand.m3u8
                        break;
                    case "شبکه آذربایجان غربی":
                        targetStreamUrl = telewebionCdnBase + "urmia/live/playlist.m3u8";
                        // : https://irib-live.iran.liara.run/live/urmia.m3u8
                        break;
                    case "شبکه اردبیل":
                        targetStreamUrl = telewebionCdnBase + "ardebil/live/playlist.m3u8";
                        // : https://irib-live.iran.liara.run/live/ardebil.m3u8
                        break;
                    case "شبکه فارس":
                        targetStreamUrl = telewebionCdnBase + "fars/live/playlist.m3u8";
                        break;
                    case "شبکه خوزستان":
                        targetStreamUrl = telewebionCdnBase + "khuzestan/live/playlist.m3u8";
                        break;

                    default:
                        Toast.makeText(v.getContext(), "پخش زنده " + network.getName() + " در حال حاضر موجود نیست.", Toast.LENGTH_SHORT).show();
                        break;
                }

                if (targetStreamUrl != null) {
                    Intent intent = new Intent(v.getContext(), LivePlayerActivity.class);
                    intent.putExtra("CHANNEL_NAME", network.getName()); // نام شبکه واقعی را ارسال کنید
                    intent.putExtra("STREAM_URL", targetStreamUrl); // URL استریم را ارسال کنید
                    v.getContext().startActivity(intent);
                    Log.d(TAG, "Starting LivePlayerActivity for " + network.getName() + " with URL: " + targetStreamUrl);
                }
            });
        }

        @Override
        public int getItemCount() {
            return networkList.size();
        }

        public static class NetworkViewHolder extends RecyclerView.ViewHolder {
            ImageView networkIcon;
            TextView networkName;

            public NetworkViewHolder(@NonNull View itemView) {
                super(itemView);
                networkIcon = itemView.findViewById(R.id.network_icon);
                networkName = itemView.findViewById(R.id.network_name);
            }
        }
    }
}