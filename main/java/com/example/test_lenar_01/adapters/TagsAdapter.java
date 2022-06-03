package com.example.test_lenar_01.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.test_lenar_01.R;
import com.example.test_lenar_01.dataClasses.Tag;

import java.util.List;

public class TagsAdapter extends BaseAdapter {

    private Context context;
    private List<Tag> list;

    public TagsAdapter(Context context, List<Tag> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            Tag tag = list.get(i);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.edit_post_activity_tags_list_element, null);

            TextView tagName = view.findViewById(R.id.tagName);
            TextView popularity = view.findViewById(R.id.tagPopularity);
            CheckBox checkBox = view.findViewById(R.id.checkbox);

            tagName.setText(tag.getName());
            popularity.setText(tag.getPopularity() + "");

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        return null;
    }
}
