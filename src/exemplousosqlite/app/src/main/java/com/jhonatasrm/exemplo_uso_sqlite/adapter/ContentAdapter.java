package com.jhonatasrm.exemplo_uso_sqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhonatasrm.exemplo_uso_sqlite.data.Content;
import com.jhonatasrm.exemplo_uso_sqlite.R;

import java.util.ArrayList;
import java.util.List;

public class ContentAdapter extends BaseAdapter {

    public Context context;
    public List<Content> contents = new ArrayList<>();

    public ContentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return contents.size();
    }

    @Override
    public Content getItem(int position) {
        return contents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contents.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.adapter_listcontent, parent, false);
            holder = new ViewHolder();
            holder.txtNome = view.findViewById(R.id.txt_nome);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        Content content = contents.get(position);

        holder.txtNome.setText(content.getNome());

        return view;
    }

    public void setItems(List<Content> contents) {
        this.contents = contents;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView txtNome;
    }
}
