package com.example.yc001_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private String Current_num=""; //save the num current input
    private String Current_operator=""; //save the operator current input
    private String Current_sign=""; //save the operator current input
    private String Current_answer=""; //save the current answer
    private EditText EditText_currentInput ; //show current input in edit text
    private TextView TextView_currentFormula ;
    private int int_operator_add_count=0;
    private int int_operator_minus_count=0;
    private int int_operator_mul_count=0;
    private int int_operator_mod_count=0;
    private boolean bool_canInputOperator=false;
    private boolean bool_canUseOperator=true;
    private boolean bool_canUseEqual=false;
    private ArrayList ArrayList_sign=new ArrayList();
    private ArrayList ArrayList_operator=new ArrayList();
    private ArrayList ArrayList_num=new ArrayList();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText_currentInput = (EditText) findViewById(R.id.Num_input);
        TextView_currentFormula = (TextView) findViewById(R.id.Str_answer);
    }
    //////////////////////////////////////////////////////////////////////////set edit text number when click the number button
    private void resetAfterEqual(){
          Current_num="";
          Current_operator="";
          Current_answer="";
          Current_sign="";
          int_operator_add_count=0;
          int_operator_minus_count=0;
          int_operator_mul_count=0;
          int_operator_mod_count=0;
          bool_canUseOperator=true;
          bool_canInputOperator=false;
          bool_canUseEqual=false;
          ArrayList_operator.clear();
          ArrayList_num.clear();
          ArrayList_sign.clear();
          displayCurrentFormula();


    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////set edit text number when click the number button
    public void setInputNum(View view ) { //set number to String Current_num
        bool_canUseOperator=true;
        Current_num+=view.getTag().toString();
        displayCurrentNum();
    }

    public  void setInputNULL(View view ){ //onclick delete clear edit text
        Current_num="";
        Current_operator="";
        Current_answer="";
        Current_sign="";
        int_operator_add_count=0;
        int_operator_minus_count=0;
        int_operator_mul_count=0;
        int_operator_mod_count=0;
        bool_canUseOperator=true;
        bool_canInputOperator=false;
        bool_canUseEqual=false;
        ArrayList_operator.clear();
        ArrayList_num.clear();
        ArrayList_sign.clear();
        displayCurrentFormula();
        displayCurrentNum();
    }

    private  void displayCurrentNum(){ //display number on edit text
        EditText_currentInput.setText(Current_num);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////



    //////////////////////////////////////////////////////////////////////////set text view formula when click the operator button
    public void setInputOperator(View view){

        if(!Current_num.isEmpty()){ //need to have number in front of operator or don't execute

            bool_canInputOperator=true;

        }

        if(bool_canInputOperator) {
            Current_operator = view.getTag().toString();
            if (bool_canUseOperator == false) {

                switch (Current_answer.substring(Current_answer.length() - 1)) {
                    case "+":
                        int_operator_add_count--;
                        ArrayList_operator.remove(ArrayList_operator.size() - 1);
                        break;

                    case "-":
                        int_operator_minus_count--;
                        ArrayList_operator.remove(ArrayList_operator.size() - 1);
                        break;

                    case "*":
                        int_operator_mul_count--;
                        ArrayList_operator.remove(ArrayList_operator.size() - 1);
                        break;

                    case "/":
                        int_operator_mod_count--;
                        ArrayList_operator.remove(ArrayList_operator.size() - 1);
                        break;


                }

                Current_answer = Current_answer.substring(0, Current_answer.length() - 1);
            }

            switch (Current_operator) {
                case "+":
                    int_operator_add_count++;
                    ArrayList_operator.add("+");
                    break;

                case "-":
                    int_operator_minus_count++;
                    ArrayList_operator.add("-");
                    break;

                case "*":
                    int_operator_mul_count++;
                    ArrayList_operator.add("*");
                    break;

                case "/":
                    int_operator_mod_count++;
                    ArrayList_operator.add("/");
                    break;


            }

            displayCurrentFormula();
            bool_canUseOperator = false;
        }

       /* else if(Current_num.isEmpty()){ //it is a negative sign

            Current_sign = view.getTag().toString();
            switch (Current_sign) {
                case "-":
                    ArrayList_sign.add("-");
                    break;

                 default:
                     break;

            }
            TextView_currentFormula.setText(Current_sign);

        }*/


    }




    private  void displayCurrentFormula(){ //set current formula
        Current_answer+=Current_num+Current_operator;
        TextView_currentFormula.setText(Current_answer);
        Current_num=""; //reset current number to ""
        //bool_canUseEqual=true;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////



    //////////////////////////////////////////////////////////////////////////set edit text when click the equal button

    public  void setCurrentAnswer( View view ){ //set current answer

        if(!Current_num.isEmpty()&&int_operator_add_count+int_operator_minus_count+int_operator_mod_count+int_operator_mul_count!=0){ //the last number can't be empty && need to have at least on operator
            bool_canUseEqual=true;
        }



        if(bool_canUseEqual){

       /*seperate the number*/
        Current_answer+=Current_num;
        for (int i=1;i<=int_operator_add_count+int_operator_minus_count+int_operator_mul_count+int_operator_mod_count;i++) {

            if(i==1) { //first number need to see if have negative sign
                ArrayList_num.add(Current_sign +Double.valueOf(Current_answer.substring(0, Current_answer.indexOf(ArrayList_operator.get(i - 1).toString()))));
                Current_answer = Current_answer.substring(Current_answer.indexOf(ArrayList_operator.get(i - 1).toString()) + 1, Current_answer.length());
            }
            else{
                ArrayList_num.add(Double.valueOf(Current_answer.substring(0, Current_answer.indexOf(ArrayList_operator.get(i - 1).toString()))));
                Current_answer = Current_answer.substring(Current_answer.indexOf(ArrayList_operator.get(i - 1).toString()) + 1, Current_answer.length());

            }

            if (i == int_operator_add_count + int_operator_minus_count + int_operator_mul_count + int_operator_mod_count) {
                ArrayList_num.add(Current_answer);
            }
        }



        /*calculate the number*/

        int calculate_count=0;
        if(int_operator_mul_count!=0||int_operator_mod_count!=0){ //include * or /
            for(int i=1 ;i<=int_operator_mul_count+int_operator_mod_count+int_operator_add_count+int_operator_minus_count;i++){
                switch (ArrayList_operator.get(i-1-calculate_count).toString()){
                    case "*":

                        ArrayList_num.add(i-calculate_count,Double.valueOf(ArrayList_num.get(i-1-calculate_count).toString())*Double.valueOf(ArrayList_num.get(i-calculate_count).toString())) ;
                        ArrayList_num.remove(i-1-calculate_count);
                        ArrayList_num.remove(i-calculate_count);
                        ArrayList_operator.remove(i-1-calculate_count);
                        calculate_count++;

                        break;

                    case "/":

                        ArrayList_num.add(i-calculate_count,Double.valueOf(ArrayList_num.get(i-1-calculate_count).toString())/Double.valueOf(ArrayList_num.get(i-calculate_count).toString())) ;
                        ArrayList_num.remove(i-1-calculate_count);
                        ArrayList_num.remove(i-calculate_count);
                        ArrayList_operator.remove(i-1-calculate_count);
                        calculate_count++;

                        break;

                    default:
                        break;


                }


            }


        }
        calculate_count=0;
            for(int i=1 ;i<=int_operator_add_count+int_operator_minus_count;i++){
                switch (ArrayList_operator.get(i-1-calculate_count).toString()){
                    case "+":

                        ArrayList_num.add(i-calculate_count,Double.valueOf(ArrayList_num.get(i-1-calculate_count).toString())+Double.valueOf(ArrayList_num.get(i-calculate_count).toString())) ;
                        ArrayList_num.remove(i-1-calculate_count);
                        ArrayList_num.remove(i-calculate_count);
                        ArrayList_operator.remove(i-1-calculate_count);
                        calculate_count++;

                        break;

                    case "-":
                        ArrayList_num.add(i-calculate_count,Double.valueOf(ArrayList_num.get(i-1-calculate_count).toString())-Double.valueOf(ArrayList_num.get(i-calculate_count).toString())) ;
                        ArrayList_num.remove(i-1-calculate_count);
                        ArrayList_num.remove(i-calculate_count);
                        ArrayList_operator.remove(i-1-calculate_count);
                        calculate_count++;
                        break;

                }


            }





        Current_answer=ArrayList_num.get(0).toString();
        EditText_currentInput.setText(Current_answer);
        resetAfterEqual();

        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////

}
