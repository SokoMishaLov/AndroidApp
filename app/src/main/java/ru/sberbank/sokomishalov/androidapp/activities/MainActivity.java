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

    private ButtonHelper buttonHelper = new ButtonHelper();
    private MathHelper mathHelper = new MathHelper();

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final Integer NUMBER_MAX_COUNT = 10;
    private static Number firstOperand = 0;
    private static Number secondOperand = 0;
    private static String operation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.result = (TextView) findViewById(R.id.result);
        this.result.setText(String.valueOf(firstOperand));
        this.buttonHelper.fillNumberButtons();
        this.buttonHelper.fillOperationButtons();
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
                if (this.result.getText().equals("0")){
                   this.result.setText(numberButton.getText());
                } else if (anyPressed) {
                   this.result.setText(numberButton.getText());
                   //second operand is not equal 0 any more
                   secondOperand = 1;
                } else {
                    this.result.setText(this.result.getText().toString() + "" + numberButton.getText().toString());
                }
            }
        }
    }

    public void onOperationButtonClick(View view){
        for (Button operationButton : operationButtons) {
            if (operationButton.getId() == view.getId()) {
                Log.d(TAG, operationButton.getText() + " pressed");
                operationButton.setSelected(true);
                if (secondOperand.equals(0)) {
                    operation = operationButton.getText().toString();
                    firstOperand = this.mathHelper.getNumberValueOfTextView();
                    Log.d(TAG, "first operand" + firstOperand);
                } else {
                    secondOperand = this.mathHelper.getNumberValueOfTextView();
                    Log.d(TAG, "second operand" + secondOperand);
                }
                this.mathHelper.operate(operationButton);
            }

        }
    }

    private class MathHelper {

        private Number getNumberValueOfTextView(){
            return result.getText().length() != 0 ? 0 :
                    (result.getText().toString().contains(getResources().getString(R.string.operation_dot)) ?
                            Double.parseDouble(result.getText().toString()) :
                            Integer.parseInt(result.getText().toString()));
        }

        private void operate(Button operationButton) {
            if (operation.equals(getResources().getString(R.string.operation_clear))){
                result.setText("0");
                firstOperand = 0;
                secondOperand = 0;
                operation = "";
                operationButton.setSelected(false);
            } else if (operation.equals(getResources().getString(R.string.operation_plus_minus))){
                if (result.getText().toString().charAt(0) == '-'){
                    result.setText(result.getText().subSequence(1,result.getText().length()));
                } else if (result.getText().equals("0")) {
                    result.setText(result.getText());
                } else {
                    result.setText("-" + "" + result.getText());
                }
                operation = "";
                operationButton.setSelected(false);
            } else if (operation.equals(getResources().getString(R.string.operation_dot))){
                if (!result.getText().toString().contains(getResources().getString(R.string.operation_dot))) {
                    result.setText(result.getText() + "" + getResources().getString(R.string.operation_dot));
                }
                operation = "";
                operationButton.setSelected(false);
            }  else if (operation.equals(getResources().getString(R.string.operation_percent))){
                result.setText(result.getText());
            } else if (operation.equals(getResources().getString(R.string.operation_multiple))){
                result.setText(result.getText());
            } else if (operation.equals(getResources().getString(R.string.operation_division))){
                result.setText(result.getText());
            } else if (operation.equals(getResources().getString(R.string.operation_plus))){
                result.setText(result.getText());
            } else if (operation.equals(getResources().getString(R.string.operation_minus))){
                result.setText(result.getText());
            } else if (operation.equals(getResources().getString(R.string.operation_equality))){
                result.setText(result.getText());
            }
        }

    }

    private class ButtonHelper {

        private void fillNumberButtons(){
            numberButtons = new ArrayList<>();
            numberButtons.add((Button) findViewById(R.id.buttonZero));
            numberButtons.add((Button) findViewById(R.id.buttonOne));
            numberButtons.add((Button) findViewById(R.id.buttonTwo));
            numberButtons.add((Button) findViewById(R.id.buttonThree));
            numberButtons.add((Button) findViewById(R.id.buttonFour));
            numberButtons.add((Button) findViewById(R.id.buttonFive));
            numberButtons.add((Button) findViewById(R.id.buttonSix));
            numberButtons.add((Button) findViewById(R.id.buttonSeven));
            numberButtons.add((Button) findViewById(R.id.buttonEight));
            numberButtons.add((Button) findViewById(R.id.buttonNine));
        }

        private void fillOperationButtons(){
            operationButtons = new ArrayList<>();
            operationButtons.add((Button) findViewById(R.id.buttonMultiplication));
            operationButtons.add((Button) findViewById(R.id.buttonDivision));
            operationButtons.add((Button) findViewById(R.id.buttonPlus));
            operationButtons.add((Button) findViewById(R.id.buttonMinus));
            operationButtons.add((Button) findViewById(R.id.buttonDot));
            operationButtons.add((Button) findViewById(R.id.buttonEquality));
            operationButtons.add((Button) findViewById(R.id.buttonClear));
            operationButtons.add((Button) findViewById(R.id.buttonSign));
            operationButtons.add((Button) findViewById(R.id.buttonPercent));
        }
    }



}
