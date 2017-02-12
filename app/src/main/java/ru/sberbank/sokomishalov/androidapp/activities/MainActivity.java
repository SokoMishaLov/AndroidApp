package ru.sberbank.sokomishalov.androidapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import ru.sberbank.sokomishalov.androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private List<Button> numberButtons;
    private List<Button> operationButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.textView = (TextView) findViewById(R.id.result);
        fillNumberButtons();
        fillOperationButtons();
        setSupportActionBar(toolbar);
    }

    public void onHelloButtonClick(View view){
        Toast t = Toast.makeText(getApplicationContext(), "Ну здаров, петух", Toast.LENGTH_LONG);
        t.show();
    }

    public void onNumberButtonClick(View view){
        for (Button numberButton : numberButtons) {
            if (numberButton.getId() == view.getId()){
                this.textView.setText(this.textView.getText().toString() + "" + numberButton.getText().toString());
            }
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
        this.operationButtons.add((Button) findViewById(R.id.buttonAddition));
        this.operationButtons.add((Button) findViewById(R.id.buttonSubtraction));
        this.operationButtons.add((Button) findViewById(R.id.buttonDot));
        this.operationButtons.add((Button) findViewById(R.id.buttonEquality));
        this.operationButtons.add((Button) findViewById(R.id.buttonClear));
        this.operationButtons.add((Button) findViewById(R.id.buttonSign));
        this.operationButtons.add((Button) findViewById(R.id.buttonPercent));
    }
}
