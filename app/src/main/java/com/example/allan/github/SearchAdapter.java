package com.example.allan.github;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by allan on 22-Oct-17.
 */

class SearchAdapter extends ArrayAdapter {
    private Context context;
    private int resourse;
    private ArrayList<SearchResult> objects;

    public SearchAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<SearchResult> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resourse=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView=layoutInflater.inflate(resourse, null);
        }

        SearchResult result = objects.get(position);

        //TextView resultNumber=convertView.findViewById(R.id.resultsNumber);
        ImageView avatar=convertView.findViewById(R.id.avatarURL);
        TextView name=convertView.findViewById(R.id.name);
        TextView login=convertView.findViewById(R.id.login);

        Picasso.with(context).load(result.getAvatarURL()).into(avatar);
        name.setText(result.getName());
        login.setText(result.getLogin());

        return convertView;
    }
}
