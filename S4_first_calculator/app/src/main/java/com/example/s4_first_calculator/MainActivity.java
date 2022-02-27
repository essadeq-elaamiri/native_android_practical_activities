package com.example.s4_first_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //retrive components
        Button calculateBtn = findViewById(R.id.calculateBtn);
        TextView operation = findViewById(R.id.operationText);
        ListView operationHistory = findViewById(R.id.operationHistoryList);

        List<String> operations = new ArrayList<>();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, operations);

        operationHistory.setAdapter(adapter);
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String operationText = operation.getText().toString();
                // verifying the operation must include one operation +, -, /, *,
                if(!isValidOperation(operationText)){
                    Toast.makeText(getApplicationContext(),"Invalid Operation",Toast.LENGTH_SHORT).show();
                }
                else{
                    // extract the operands
                    // calculate result
                    // format history
                    String textHistory = operationText +"= "+evaluateOperation(operationText);
                    operations.add(textHistory);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private boolean isValidOperation(String operation){
        //"^\d+(\.?\d+)?[+\-\/*%]\d+(\.?\d+)?$"
        if(operation.matches("^\\d+(\\.?\\d+)?[+\\-/*]\\d+(\\.?\\d+)?$")){
            return true;
        }
        return false;
    }

    private String[] extractOperationItems(String operation){
        System.out.printf(operation);
        return operation.split("[+\\-/*]");
    }

    private float evaluateOperation(String operationText){
        String [] operationItems = extractOperationItems(operationText);
        for (String s:
             operationItems) {
            System.out.printf(s);

        }

        float firstOp = Float.parseFloat(operationItems[0]);
        float secondOp = Float.parseFloat(operationItems[1]);

        Pattern pattern = Pattern.compile("[+\\-/*]");
        Matcher matcher = pattern.matcher(operationText);
        String operationSymbol = "" ;
        while (matcher.find()){
            operationSymbol = matcher.group(0);
        }

        switch (operationSymbol){
            case "+":
                return  firstOp + secondOp;
            case "-":
                return firstOp - secondOp;
            case "*":
                return firstOp * secondOp;
            case "/":
                return firstOp / secondOp;

            default:
                return 0.0f;
        }


    }
}