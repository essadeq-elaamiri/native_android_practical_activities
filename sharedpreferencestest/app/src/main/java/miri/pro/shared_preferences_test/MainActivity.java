package miri.pro.shared_preferences_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.icu.text.Edits;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText phraseTextView;
    private CheckBox zigzagCheckBox;
    private Spinner ourSpinner;
    private Button  saveButton;
    private Button  loadButton;
    private Button  clearButton;
    private TextView loadedDataTextView;

    private final String SHEARED_PREF_NAME = "Settings_test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phraseTextView = findViewById(R.id.phraseTextView);
        zigzagCheckBox = findViewById(R.id.zigzagCheckBox);
        ourSpinner = (Spinner) findViewById(R.id.ourSpinner);
        saveButton = findViewById(R.id.saveButton);
        loadButton = findViewById(R.id.loadButton);
        clearButton = findViewById(R.id.clearButton);
        loadedDataTextView = findViewById(R.id.loadedDataTextView);

        // TODO: load data to spinner
        // TODO: adding buttons click listeners

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item );
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ourSpinner.setAdapter(spinnerAdapter);



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> data = new HashMap<>();
                String phrase = phraseTextView.getText().toString();
                String zigzag = String.valueOf(zigzagCheckBox.isChecked());
                String planet = (String) ourSpinner.getSelectedItem();

                data.put("phrase", phrase);
                data.put("zigzag", zigzag);
                data.put("planet", planet);
                saveDataToSharedPreferences( SHEARED_PREF_NAME,data);
            }
        });

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> data = loadDataFromSharedPreferences(SHEARED_PREF_NAME);
                StringBuilder stringBuilder = new StringBuilder();
                for (String key: data.keySet()){
                    stringBuilder.append(key + ": "+data.get(key)+"\n");
                }
                loadedDataTextView.setText(stringBuilder);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearInputs();
                clearDataFromSharedPreferences(SHEARED_PREF_NAME);
            }
        });


    }
    private void saveDataToSharedPreferences(String sharedPreferencesFileName,HashMap<String, String> data){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPreferencesFileName, MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        for (String key: data.keySet()){
            sharedPreferencesEditor.putString(key, data.get(key));
            sharedPreferencesEditor.commit();
            Log.d("TAG::", key);
        }
        Toast.makeText(this, "Data added !", Toast.LENGTH_SHORT).show();
    }

    private HashMap<String,String> loadDataFromSharedPreferences(String sharedPreferencesFileName){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPreferencesFileName, MODE_PRIVATE);
        return (HashMap<String,String>)sharedPreferences.getAll();

    }

    private void clearDataFromSharedPreferences(String sharedPreferencesFileName){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPreferencesFileName, MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.clear();
        Toast.makeText(this, "Data cleared !", Toast.LENGTH_SHORT).show();
    }

    private void clearInputs(){
        phraseTextView.setText("");
        zigzagCheckBox.setChecked(false);
        ourSpinner.setSelected(false);
    }
}