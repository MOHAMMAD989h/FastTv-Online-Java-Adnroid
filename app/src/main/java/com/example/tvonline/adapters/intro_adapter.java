package com.example.tvonline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.tvonline.R;
import com.example.tvonline.models.intro_model;

import org.w3c.dom.Text;

import java.util.List;

public class intro_adapter extends PagerAdapter {

    List<intro_model> list;

    Context context;

    public intro_adapter(List<intro_model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_intro,container,false);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView_title = view.findViewById(R.id.textview_title);
        TextView textView_description = view.findViewById(R.id.textview_description);

        textView_title.setText(list.get(position).getTitle());
        textView_description.setText(list.get(position).getDescription());
        imageView.setImageResource(list.get(position).getImg_id());

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
