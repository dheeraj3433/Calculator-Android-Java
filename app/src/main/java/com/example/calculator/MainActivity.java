package com.example.calculator;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView mResultView;
    TextView mInputText;
    Button mNumButton;

    @Override̥
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInputText = findViewById(R.id.input_view);
        mResultView = findViewById(R.id.result_view);
    }

    public void inputText(View view) {

        // Getting the Id of the clicked element.
        int viewId = view.getId();

        // Finding the element by its id and getting the text from it.
        mNumButton = findViewById(viewId);
        String input = mNumButton.getText().toString();

        // Getting the text from input view and appending the input string with it.
        String text = mInputText.getText().toString();

        // Concatenate the string before displaying it.
        if (input.equals("+") || input.equals("-") || input.equals("x") || input.equals("/")) {
            text = text + " " + input + " ";
        } else {
            text = text.concat(input);
        }

        // Setting the text in inputView.
        mInputText.setText(text);
    }

    public void backSpace(View view) {
        String inputText = mInputText.getText().toString().trim();

        // removing the last element before displaying it.
        int len = inputText.length();
        char[] text = inputText.toCharArray();
        StringBuilder finalText = new StringBuilder();

        for (int i = 0; i < len - 1; i++) {
            finalText.append(text[i]);
        }

        mInputText.setText(finalText.toString().trim());
    }

    public void clear(View view) {
        // Clearing the view fields
        mInputText.setText("");
        mResultView.setText("");
    }

    public void calculate(View view) {
        // Implementing the calculation operation
        String[] inputExp = mInputText.getText().toString().trim().split(" ");
        int len = inputExp.length;
        boolean error = false;

        // Handling the input exception
        if (inputExp[0].equals("+") || inputExp[0].equals("-") || inputExp[0].equals("*") || inputExp[0].equals("/") || inputExp[0].equals(".") ||
                inputExp[len - 1].equals("+") || inputExp[len - 1].equals("-") || inputExp[len - 1].equals("*") || inputExp[len - 1].equals("/") || inputExp[len - 1].equals("."))
            error = true;
        else {
            for (int i = 0; i < len; i++) {
                if (inputExp[i].equals("."))
                    error = true;
                if (i + 2 == len) break;
                else if ((inputExp[i].equals("+") || inputExp[i].equals("-") || inputExp[i].equals("*") || inputExp[i].equals("/")) &&
                        (inputExp[i + 2].equals("+") || inputExp[i + 2].equals("-") || inputExp[i + 2].equals("*") || inputExp[i + 2].equals("/")))
                    error = true;
            }
        }

        while (inputExp.length != 1 && !error) {

            // Checking for the operation in BODMAS order
            if (indexOf(inputExp, "/") != -1) {
                int index = indexOf(inputExp, "/");
                float op1 = Float.parseFloat(inputExp[index - 1]);
                float op2 = Float.parseFloat(inputExp[index + 1]);
                inputExp[index - 1] = String.valueOf(op1 / op2);
                inputExp = removeIndex(inputExp, index, index + 1);
            }

            if (indexOf(inputExp, "x") != -1) {
                int index = indexOf(inputExp, "x");
                float op1 = Float.parseFloat(inputExp[index - 1]);
                float op2 = Float.parseFloat(inputExp[index + 1]);
                inputExp[index - 1] = String.valueOf(op1 * op2);
                inputExp = removeIndex(inputExp, index, index + 1);
            }

            if (indexOf(inputExp, "+") != -1) {
                int index = indexOf(inputExp, "+");
                float op1 = Float.parseFloat(inputExp[index - 1]);
                float op2 = Float.parseFloat(inputExp[index + 1]);
                inputExp[index - 1] = String.valueOf(op1 + op2);
                inputExp = removeIndex(inputExp, index, index + 1);
            }

            if (indexOf(inputExp, "-") != -1) {
                int index = indexOf(inputExp, "-");
                float op1 = Float.parseFloat(inputExp[index - 1]);
                float op2 = Float.parseFloat(inputExp[index + 1]);
                inputExp[index - 1] = String.valueOf(op1 - op2);
                inputExp = removeIndex(inputExp, index, index + 1);
            }
        }

        if (error) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        } else {
            mResultView.setText(inputExp[0]);
        }
    }

    private String[] removeIndex(String[] arrString, int index1, int index2) {
        // Declaring variable used
        int j = 0;
        int len = arrString.length;
        String[] arr = new String[len - 2];

        for (int i = 0; i < len; i++) {
            if (i == index1 || i == index2) {
                continue;
            } else {
                arr[j] = arrString[i];
                j++;
            }
        }
        return arr;
    }

    public int indexOf(String[] arrString, String ch) {
        for (int i = 0; i < arrString.length; i++) {
            if (arrString[i].equals(ch)) return i;
        }
        return -1;
    }
}
