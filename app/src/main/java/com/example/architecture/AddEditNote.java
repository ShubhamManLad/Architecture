package com.example.architecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNote extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.architecture.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.architecture.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.architecture.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY= "com.example.architecture.EXTRA_PRIORITY";



    private EditText editText_title;
    private EditText editText_description;
    private NumberPicker numberPicker_priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editText_title = findViewById(R.id.edit_text_title);
        editText_description = findViewById(R.id.edit_text_description);
        numberPicker_priority = findViewById(R.id.number_picker_priority);

        numberPicker_priority.setMinValue(1);
        numberPicker_priority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            editText_title.setText(intent.getStringExtra(EXTRA_TITLE));
            editText_description.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPicker_priority.setValue(intent.getIntExtra(EXTRA_PRIORITY,10));
        }
        else{
            setTitle("Add Note");

        }
    }

    private void saveNote(){
        String title = String.valueOf(editText_title.getText());
        String description = String.valueOf(editText_description.getText());
        int priority = numberPicker_priority.getValue();
        
        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Please Enter the Title and Description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1){
            data.putExtra(EXTRA_ID,id);
        }


        setResult(RESULT_OK,data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}