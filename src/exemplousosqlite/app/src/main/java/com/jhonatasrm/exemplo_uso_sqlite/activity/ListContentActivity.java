package com.jhonatasrm.exemplo_uso_sqlite.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.jhonatasrm.exemplo_uso_sqlite.data.Content;
import com.jhonatasrm.exemplo_uso_sqlite.dialog.DeleteDialog;
import com.jhonatasrm.exemplo_uso_sqlite.data.ContentDAO;
import com.jhonatasrm.exemplo_uso_sqlite.R;
import com.jhonatasrm.exemplo_uso_sqlite.adapter.ContentAdapter;

import java.util.List;

import shortbread.Shortbread;
import shortbread.Shortcut;

// shortcut ver lista
@Shortcut(id = "lista", icon = R.drawable.list_content, shortLabel = "Ver lista")
public class ListContentActivity extends AppCompatActivity implements OnItemLongClickListener, DeleteDialog.OnDeleteListener {

    private static final int REQ_EDIT = 10;

    private ContentDAO contentDAO;
    private ContentAdapter adapter;
    private TextView nothingInDB;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listcontent);
        Shortbread.create(this);
        initFindViews();

        adapter = new ContentAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);
        contentDAO = ContentDAO.getInstance(this);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_list);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);

        /*
        * Hide/Show Toolbar when scrolling another View
        * with CoordinatorLayout does not work with ListView/GridView by default.
        * You need to enable NestedScrolling on them.
        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
        }

        updateList();
    }

    private void initFindViews() {
        listView = findViewById(R.id.list);
        nothingInDB = findViewById(R.id.nothing_in_db);
    }

    private void updateList() {
        List<Content> contents = contentDAO.list();
        if (contents.isEmpty()) {
            listView.setVisibility(View.INVISIBLE);
            nothingInDB.setVisibility(View.VISIBLE);
        } else {
            nothingInDB.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
        adapter.setItems(contents);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_listcontent, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(getApplicationContext(), AddContentActivity.class);
                startActivityForResult(intent, REQ_EDIT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_EDIT && resultCode == RESULT_OK) {
            updateList();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        Content content = adapter.getItem(position);

        DeleteDialog dialog = new DeleteDialog();
        dialog.setContent(content);
        dialog.show(getSupportFragmentManager(), "deleteDialog");
        return true;
    }

    @Override
    public void onDelete(Content content) {
        contentDAO.delete(content);
        updateList();
    }

    // shortcut para adicionar conteúdo
    @Shortcut(id = "add", icon = R.drawable.add_shortcut, shortLabel = "Adicionar conteúdo")
    public void addContent() {
        startActivity(new Intent(ListContentActivity.this, AddContentActivity.class));
    }
}
