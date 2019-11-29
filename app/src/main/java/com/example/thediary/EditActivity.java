package com.example.thediary;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class EditActivity extends AppCompatActivity {
    EditText editArea;
    int itemPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editArea= findViewById(R.id.item);
        getData();

    }



    public void getData (){

        Intent intent = getIntent();
        String entry_body = intent.getStringExtra("item");
         itemPosition = intent.getIntExtra("itemPosition",0);
        editArea.setText(entry_body);


    }

    public void saveEditedItem(View view) {
        String editedItem = editArea.getText().toString();

        Intent editedItemIntent = new Intent();
        editedItemIntent.putExtra("editedItem",editedItem);
        editedItemIntent.putExtra("itemPosition",itemPosition);
        setResult(RESULT_OK,editedItemIntent);
        finish();



    }
}

