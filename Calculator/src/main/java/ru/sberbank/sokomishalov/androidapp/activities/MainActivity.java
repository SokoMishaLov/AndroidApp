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

    private static Double firstOperand;
    private static Double secondOperand;
    private static String firstOperator;
    private static String secondOperator;
    private static Boolean isSecondOperandExists;
    private static Boolean reWriteTextViewValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.result = (TextView) findViewById(R.id.result);
        this.mathHelper.clearOrInitAllStaticFields();
        this.buttonHelper.fillNumberButtons();
        this.buttonHelper.fillOperationButtons();
        this.result.setText(String.valueOf(firstOperand.intValue()));
        setSupportActionBar(toolbar);
        Log.d(TAG,"Activity created");
    }

    public void onHelloButtonClick(View view){
        Toast t = Toast.makeText(getApplicationContext(), "Ну здаров, петух", Toast.LENGTH_LONG);
        t.show();
    }

    public void onNumberButtonClick(View view){
        if (this.result.getText().length() >= NUMBER_MAX_COUNT) return;

        for (Button numberButton : numberButtons) {
            if (numberButton.getId() == view.getId()){

                // if it's starting and zero - rewrite it
                if ((firstOperator.equals("") && this.result.getText().charAt(0) == '0') || (reWriteTextViewValue)) {
                   this.result.setText(numberButton.getText());
                   reWriteTextViewValue = false;

                // else add symbol to result string
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

                if (!isSecondOperandExists) {
                    firstOperator = operationButton.getText().toString();
                    firstOperand = this.mathHelper.getDoubleValueOfTextView();
                    Log.d(TAG, "first operand is " + firstOperand);
                } else {
                    secondOperator = operationButton.getText().toString();
                    secondOperand = this.mathHelper.getDoubleValueOfTextView();
                    Log.d(TAG, "second operand is " + secondOperand);
                }
                this.mathHelper.operate();
            }

        }
    }

    private class MathHelper {

        private Double getDoubleValueOfTextView(){
            return result.getText().toString().length() == 0 ? 0. : Double.parseDouble(result.getText().toString());
        }

        private void setResultStringValueFromDouble(Double value){
            Log.d(TAG, value.toString());

            if (value - value.intValue() < Double.MIN_VALUE){
                result.setText(String.valueOf(value.intValue()));
            } else {
                result.setText(String.valueOf(value));
            }
        }



        private void operate() {
            if (firstOperator.equals(getResources().getString(R.string.operation_clear)) || secondOperator.equals(getResources().getString(R.string.operation_clear))){

                //clear everything
                result.setText("0");
                mathHelper.clearOrInitAllStaticFields();
                reWriteTextViewValue = true;

            } else if (firstOperator.equals(getResources().getString(R.string.operation_plus_minus)) || secondOperator.equals(getResources().getString(R.string.operation_plus_minus))){

                //add a sign to operand

                if (result.getText().toString().charAt(0) == '-'){
                    result.setText(result.getText().subSequence(1,result.getText().length()));
                } else if (result.getText().equals("0")) {
                    result.setText(result.getText());
                } else {
                    result.setText("-" + "" + result.getText());
                }

                if (isSecondOperandExists) {
                    secondOperator = "";
                } else {
                    firstOperator = "";
                }

            } else if (firstOperator.equals(getResources().getString(R.string.operation_dot)) || secondOperator.equals(getResources().getString(R.string.operation_dot))){

                //convert integer to a double by adding dot to operand

                if (!result.getText().toString().contains(getResources().getString(R.string.operation_dot))) {
                    result.setText(result.getText() + "" + getResources().getString(R.string.operation_dot));
                }

                if (isSecondOperandExists){
                    secondOperator = "";
                } else {
                    firstOperator = "";
                }

            } else if (firstOperator.equals(getResources().getString(R.string.operation_percent))){

                // setting value by 1% of first operand

                setResultStringValueFromDouble(firstOperand /100);
                firstOperator = "";

            } else if (secondOperator.equals(getResources().getString(R.string.operation_percent))){

                // executing firstOperator '%' with two operands

                if (firstOperator.equals(getResources().getString(R.string.operation_multiple))){

                    // multiply percent of first operand

                    setResultStringValueFromDouble(firstOperand * ((firstOperand * secondOperand / 100)));
                    mathHelper.clearOrInitAllStaticFields();
                    reWriteTextViewValue = true;

                } else if (firstOperator.equals(getResources().getString(R.string.operation_division))){

                    // divide percent of first operand

                    setResultStringValueFromDouble(firstOperand / ((firstOperand * secondOperand / 100)));
                    mathHelper.clearOrInitAllStaticFields();
                    reWriteTextViewValue = true;

                } else if (firstOperator.equals(getResources().getString(R.string.operation_plus))){

                    // sum percent of first operand

                    setResultStringValueFromDouble(firstOperand + ((firstOperand * secondOperand / 100)));
                    mathHelper.clearOrInitAllStaticFields();
                    reWriteTextViewValue = true;

                } else if (firstOperator.equals(getResources().getString(R.string.operation_minus))){

                    // residual percent of first operand

                    setResultStringValueFromDouble(firstOperand - ((firstOperand * secondOperand / 100)));
                    mathHelper.clearOrInitAllStaticFields();
                    reWriteTextViewValue = true;

                }

            } else if (secondOperator.equals("")){

                // if second operator is not clicked by this time -
                // clearing input field, quit and waiting for input second operand
                Log.d(TAG, "waiting for second operand");
                isSecondOperandExists = true;
                reWriteTextViewValue = true;

            } else if (firstOperator.equals(getResources().getString(R.string.operation_multiple))){

                // executing multiple

                setResultStringValueFromDouble(firstOperand * secondOperand);
                mathHelper.addOperation();

            } else if (firstOperator.equals(getResources().getString(R.string.operation_division))){

                // executing division

                setResultStringValueFromDouble(firstOperand / secondOperand);
                mathHelper.addOperation();

            } else if (firstOperator.equals(getResources().getString(R.string.operation_plus))){

                // executing sum

                setResultStringValueFromDouble(firstOperand + secondOperand);
                mathHelper.addOperation();

            } else if (firstOperator.equals(getResources().getString(R.string.operation_minus))){

                // executing residual

                setResultStringValueFromDouble(firstOperand - secondOperand);
                mathHelper.addOperation();

            } else if (firstOperator.equals(getResources().getString(R.string.operation_equality))){

                // doing nothing, because it's already done

                result.setText(result.getText());
                mathHelper.addOperation();

            }
        }

        private void clearOrInitAllStaticFields(){
            firstOperand = 0.;
            secondOperand = 0.;
            isSecondOperandExists = false;
            reWriteTextViewValue = false;
            firstOperator = "";
            secondOperator = "";
        }

        private void addOperation(){
            firstOperand  = getDoubleValueOfTextView();
            firstOperator = secondOperator;
            reWriteTextViewValue = true;
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
