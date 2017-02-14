package ru.sberbank.sokomishalov.androidapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import ru.sberbank.sokomishalov.androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView result;
    private List<Button> numberButtons;
    private List<Button> operationButtons;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final Integer NUMBER_MAX_COUNT = 10;
    private static Integer firstOperand = 0;
    private static Integer secondOperand = 0;
    private static String operation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.result = (TextView) findViewById(R.id.result);
        this.result.setText(String.valueOf(firstOperand));
        fillNumberButtons();
        fillOperationButtons();
        setSupportActionBar(toolbar);
        Log.d(TAG,"Activity created");
    }

    public void onHelloButtonClick(View view){
        Toast t = Toast.makeText(getApplicationContext(), "Ну здаров, петух", Toast.LENGTH_LONG);
        t.show();
    }

    public void onNumberButtonClick(View view){
        if (this.result.getText().length() >= NUMBER_MAX_COUNT) return;

        boolean anyPressed = false;
        for (Button operationButton : operationButtons) {
            if (operationButton.isSelected()){
                operationButton.setSelected(false);
                operation = operationButton.getText().toString();
                anyPressed = true;
            }
        }

        for (Button numberButton : numberButtons) {
            if (numberButton.getId() == view.getId()){
                if (anyPressed || this.result.getText().equals("0")){
                   this.result.setText(numberButton.getText());
                } else {
                    this.result.setText(this.result.getText().toString() + "" + numberButton.getText().toString());
                }
            }
        }
    }

    public void onOperationButtonClick(View view){
        for (Button operationButton : operationButtons) {
            if (operationButton.getId() == view.getId()) {
                Log.d(TAG, operationButton.getText() + " set pressed");
                operationButton.setSelected(true);
                if (secondOperand.equals(0)) {
                    operation = operationButton.getText().toString();
                    firstOperand = Integer.parseInt(result.getText().toString());
                    operate();
                } else {
                    secondOperand = Integer.parseInt(result.getText().toString());
                    operate();
                }
            }
        }
    }

    private void operate() {
        if (operation.equals(getResources().getString(R.string.operation_clear))){
            this.result.setText("0");
        } else if (operation.equals(getResources().getString(R.string.operation_plus_minus))){
            if (this.result.getText().toString().charAt(0) == '-'){
                this.result.setText(this.result.getText().subSequence(1,this.result.getText().length()-1));
            } else {
                this.result.setText("-" + "" + this.result.getText());
            }
        } else if (operation.equals(getResources().getString(R.string.operation_dot))){
            this.result.setText(this.result.getText());
        }  else if (operation.equals(getResources().getString(R.string.operation_percent))){
            this.result.setText(this.result.getText());
        } else if (operation.equals(getResources().getString(R.string.operation_multiple))){
            this.result.setText(this.result.getText());
        } else if (operation.equals(getResources().getString(R.string.operation_division))){
            this.result.setText(this.result.getText());
        } else if (operation.equals(getResources().getString(R.string.operation_plus))){
            this.result.setText(this.result.getText());
        } else if (operation.equals(getResources().getString(R.string.operation_minus))){
            this.result.setText(this.result.getText());
        } else if (operation.equals(getResources().getString(R.string.operation_equality))){
            this.result.setText(this.result.getText());
        }
    }

    private void fillNumberButtons(){
        this.numberButtons = new ArrayList<>();
        this.numberButtons.add((Button) findViewById(R.id.buttonZero));
        this.numberButtons.add((Button) findViewById(R.id.buttonOne));
        this.numberButtons.add((Button) findViewById(R.id.buttonTwo));
        this.numberButtons.add((Button) findViewById(R.id.buttonThree));
        this.numberButtons.add((Button) findViewById(R.id.buttonFour));
        this.numberButtons.add((Button) findViewById(R.id.buttonFive));
        this.numberButtons.add((Button) findViewById(R.id.buttonSix));
        this.numberButtons.add((Button) findViewById(R.id.buttonSeven));
        this.numberButtons.add((Button) findViewById(R.id.buttonEight));
        this.numberButtons.add((Button) findViewById(R.id.buttonNine));
    }

    private void fillOperationButtons(){
        this.operationButtons = new ArrayList<>();
        this.operationButtons.add((Button) findViewById(R.id.buttonMultiplication));
        this.operationButtons.add((Button) findViewById(R.id.buttonDivision));
        this.operationButtons.add((Button) findViewById(R.id.buttonPlus));
        this.operationButtons.add((Button) findViewById(R.id.buttonMinus));
        this.operationButtons.add((Button) findViewById(R.id.buttonDot));
        this.operationButtons.add((Button) findViewById(R.id.buttonEquality));
        this.operationButtons.add((Button) findViewById(R.id.buttonClear));
        this.operationButtons.add((Button) findViewById(R.id.buttonSign));
        this.operationButtons.add((Button) findViewById(R.id.buttonPercent));
    }
}
