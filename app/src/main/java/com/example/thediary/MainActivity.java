package com.example.thediary;




import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView entries_List_View;
    ArrayList<String> entries_array;
    ArrayAdapter adapter;
    EditText new_Entry;
    Button add_Button;
    ArrayList<String> texts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting XML elements
        entries_List_View = findViewById(R.id.entries_ListView);
        new_Entry =findViewById(R.id.new_Entry_Edit_Text);
        entries_array = new ArrayList<>();
        add_Button = findViewById(R.id.add_button);

        //Adding SharedPreferences
        entries_array = dataSaved();

       //Assigning the array adapter to the ListView
        adapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1,entries_array);
        entries_List_View.setAdapter(adapter);

        //Setting an On_Item_Click Listener to the Listview to get the information of the item clicked,
        //and send this information to the EditActivity.
        entries_List_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = entries_List_View.getItemAtPosition(position).toString();

                Intent intent = new Intent (MainActivity.this, EditActivity.class);
                intent.putExtra("item", item);
                intent.putExtra("itemPosition", position);
                startActivityForResult(intent , 1);

            }});


        entries_List_View.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                entries_array.remove(position);
                saveData();
                adapter.notifyDataSetChanged();

                return true;
            }
        });


        }


    /**
     * This function add a new entry to the listview, if the entry is empty an alert shows up, telling the user
     * to write something.
     * @param view
     */
    public void addItemToList (View view) {
        if (new_Entry.getText().toString().equals(""))
            {
                Toast toast = Toast.makeText(getApplicationContext(),"Escriba algo" ,Toast.LENGTH_SHORT);
                toast.show();
            }
        else {
            entries_array.add(0,new_Entry.getText().toString());
            new_Entry.setText("");
            adapter.notifyDataSetChanged();

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==1) {
            if(resultCode == RESULT_OK) {
                String editedItem = data.getStringExtra("editedItem");
                int editedItemPosition = data.getIntExtra("itemPosition",0);
                entries_array.set(editedItemPosition,editedItem);
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * Save de the data when the app is closed, this function is called in the onStop method.
     *
     */

    public void saveData (){

        SharedPreferences preferences = getSharedPreferences("Items", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("length", entries_array.size());

        for( int i = 0; i < entries_array.size(); i++){
            editor.putString("item"+i, entries_array.get(i));



        }
        editor.commit();

    }

    /**
     * Return the ArrayList on entries saved, this function is called in the OnCreate method, to assing entries on the listview if there is any data
     * stored.
     * @return
     */

    public ArrayList<String> dataSaved () {

        SharedPreferences preferences = getSharedPreferences("Items",MODE_PRIVATE);

        int length = preferences.getInt("length",0);

        for( int i = 0; i < length; i++){
           String item = preferences.getString("item"+i, "");

           texts.add(0,item);


        }
        return  texts;

    }


    @Override
    protected void onStop() {
        super.onStop();
        //the funtion saveData() is called in the onStop Method to save the data that is already in the list when the app is closed.
        saveData();

        System.out.println(entries_array);

    }
}















