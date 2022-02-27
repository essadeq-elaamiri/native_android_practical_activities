package miri.pro.s4_calculatrice_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private String operand ="";
    private String operation="";

    TextView operationPlaceHolder;
    EditText operationScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get view via their IDs
        operationPlaceHolder = findViewById(R.id.operationPlaceHolder);
        operationScreen = findViewById(R.id.operationScreen);


    }
    public void btnClicked(View view){
        Button btn = (Button) view;
        String btnText = btn.getText().toString();
        String btnIdTextComplete = btn.getResources().getResourceName(btn.getId());//** returns the string id with package name
        String btnIdText = btnIdTextComplete.substring(btnIdTextComplete.indexOf("/")+1); // returns the id string

        String placeholderText = operationPlaceHolder.getText().toString();
        float result = 0f;

        if(btnIdText.startsWith("operation")){

            //TODO: if there is a value in the screen add it to placeholder, add the operation, empty screen
            // if the last char is an operation show toast
            // if the operation is complete calculate it and add operation clicked to result (firstOp)
            // if it is "=" if there is a text a number in the screen set add it to placeholder and show result (keep it as firstOp)

            if(placeholderText.isEmpty() || !String.valueOf(placeholderText.charAt(placeholderText.length()-1)).matches("[+\\-/*=(sqrt)]")){
                if(btnText.equals("=")){
                    operation += operand;
                }
                else{
                    operation += operand+ btnText;
                }
                operationPlaceHolder.setText(operation);
                operand = "";
                operationScreen.setText("");
            }else{
                if (!operand.isEmpty()){
                    operation+= operand;
                    result = evaluateOperation(operation);

                    operationPlaceHolder.setText(operation);
                    operand = String.valueOf(result);
                    operationScreen.setText(operand);
                    operation = "";
                    /*
                    if(!btnText.equals("=")){
                        operation += operand + btnText;
                        Log.d("Op : ",operation);
                    }

                     */
                }
            }


        }
        else if(btnIdText.startsWith("operand") || btnIdText.equals("point")){
            if(!(btnIdText.equals("point") && operand.contains("."))){
                operand += btnText;
                operationScreen.setText(operand);
            }
        }
        else{
            if (btnIdText.equals("_x")){
                //TODO:  empty screen and delete the secondOp if it is deleted, delete operation ...
                String op = operationScreen.getText().toString();
                if(op.length() > 1){
                    op = op.substring(0, op.length()-1);
                }
                operand = op;
                operationScreen.setText(op);
            }
            else if(btnIdText.equals("_c")){
                //TODO: empty screen + placeholder + operands variales
                operationScreen.setText("");
                operationPlaceHolder.setText("");
                operation="";
                operand = "";
            }
        }

    }

    private String[] extractOperationItems(String operation){
        String []ope ;
        String opTemp="";
        if(!operation.startsWith("-")){
            ope =  operation.split("[+\\-/*(sqrt)]");
        }
        else{
            opTemp = operation.substring(1);
            ope = opTemp.split("[+\\-/*]");
            ope[0] = "-"+ope[0];
        }

        return ope;
    }

    private float evaluateOperation(String operationText){
        //TODO: implement SQRT operation
        // add negative numbers to the operation
        // add () to the operations ...

        String [] operationItems = extractOperationItems(operationText);
        if(operationItems.length <= 1)
            return Float.parseFloat(operationItems[0]);
        for (String s:
                operationItems) {
            System.out.printf(s);

        }

        float firstOp = Float.parseFloat(operationItems[0]);
        float secondOp = operationItems[1].isEmpty()? 0f : Float.parseFloat(operationItems[1]);

        Pattern pattern = Pattern.compile("[+\\-/*(sqrt)]");
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
            case "sqrt":
                return (float)Math.sqrt(firstOp);

            default:
                return 0.0f;
        }


    }
}