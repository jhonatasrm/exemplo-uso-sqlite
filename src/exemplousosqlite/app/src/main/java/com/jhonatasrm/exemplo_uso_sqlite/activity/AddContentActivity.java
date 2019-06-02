package com.jhonatasrm.exemplo_uso_sqlite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jhonatasrm.exemplo_uso_sqlite.data.Content;
import com.jhonatasrm.exemplo_uso_sqlite.data.ContentDAO;
import com.jhonatasrm.exemplo_uso_sqlite.R;

public class AddContentActivity extends AppCompatActivity {

    private ContentDAO contentDAO;
    private EditText editTitle;
    private EditText editDescription;
    private Content content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontent);
        initFindViews();
        contentDAO = ContentDAO.getInstance(this);

        content = (Content) getIntent().getSerializableExtra("content");

        if (content != null) {
            editTitle.setText(content.getNome());
            editDescription.setText(String.valueOf(content.getDescription()));
        }

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.add);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initFindViews() {
        editTitle = findViewById(R.id.edit_title);
        editDescription = findViewById(R.id.edit_description);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(AddContentActivity.this, ListContentActivity.class));
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void process(View view) {
        String title = editTitle.getText().toString();
        String description = editDescription.getText().toString();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, R.string.empty_field, Toast.LENGTH_LONG).show();
            return;
        }

        if (content == null) {
            Content content = new Content(title, description);
            contentDAO.save(content);
        } else {
            content.setNome(title);
            content.setDescription(description);
            contentDAO.update(content);
        }

        setResult(RESULT_OK);
        finish();
    }
}
