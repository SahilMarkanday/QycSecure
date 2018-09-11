package com.qycsecure.marka.qycsecure;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EmployeesActivity extends AppCompatActivity {
    ImageButton delete;
    ListView list;
    String[] listItems;
    String string;
    DataBaseHelper db = new DataBaseHelper(this);
    ArrayList<Integer> listID = new ArrayList<>();
    int counter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        delete = (ImageButton) findViewById(R.id.delete);
        list = (ListView) findViewById(R.id.list);
        final List<String> aList = db.getAllNames();
        listItems = new String[aList.size()];
        for(int i = 0; i < aList.size(); i++){
            String listD = aList.get(i);
            listItems[i] = listD;
        }

        ArrayAdapter adapter = new ArrayAdapter(EmployeesActivity.this, android.R.layout.simple_list_item_1, listItems);
        list.setAdapter(adapter);

        listID = db.getAllID();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(EmployeesActivity.this, EmployeeDetailsActivity.class);
                i.putExtra("position", position);
                i.putExtra("listID", listID);
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listItems.length == 0){
                    Toast.makeText(EmployeesActivity.this, "No users to delete", Toast.LENGTH_SHORT).show();
                }
                else{
                    showEnterLabelDialog();
                }
            }
        });
    }

    private void showEnterLabelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EmployeesActivity.this);
        builder.setTitle("Please enter their name");

        final EditText input = new EditText(EmployeesActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Submit", null); // Set up positive button, but do not provide a listener, so we can check the string before dismissing the dialog
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false); // User has to input a name
        AlertDialog dialog = builder.create();

        // Source: http://stackoverflow.com/a/7636468/2175837
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button mButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        string = input.getText().toString().trim();
                        int counter2 = 1;
                        if (!string.isEmpty()) {
                            for(int i = 0; i < listItems.length; i++){
                                if(listItems[i].equals(string)){
                                    counter = listID.get(i);
                                    break;
                                }
                                else{
                                    counter2++;
                                }
                            }
                            if(counter2 == listItems.length+1){
                                Toast.makeText(getBaseContext(), "Name is not registered", Toast.LENGTH_LONG).show();
                            }
                            else {
                                db.deleteUser(counter);
                                for(int i = 0; i < listID.size(); i++){
                                    if(counter == listID.get(i)){
                                        listID.remove(i);
                                    }
                                }
                                final List<String> aList = db.getAllNames();
                                listItems = new String[aList.size()];
                                for(int i = 0; i < aList.size(); i++){
                                    String listD = aList.get(i);
                                    listItems[i] = listD;
                                }

                                ArrayAdapter adapter = new ArrayAdapter(EmployeesActivity.this, android.R.layout.simple_list_item_1, listItems);
                                list.setAdapter(adapter);
                            }
                            dialog.dismiss();

                        }
                    }
                });
            }
        });

        // Show keyboard, so the user can start typing straight away
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        dialog.show();
    }

}
