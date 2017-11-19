package com.gzn.cal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author gzn
 */
public class MainActivity extends AppCompatActivity {

    /**
     * 定义需要的常量。
     */
    private String ERROR = "error", ADD = "+", SUB = "-", MUL = "*",
            DIV = "/", POW = "^", MOD = "%";
    /**
     * left 左括号， right 右括号，div 除，mod 取余， mul 乘，sub 减，add 加, point 小数点
     */
    private Button mod, pow, div, mul, sub, add, point;
    /**
     * num0: 0, num1: 1，num2：2，num3: 3，num4：4，num5：5，num6: 6，num7: 7，num8: 8，num9: 9
     */
    private Button num0, num1, num2, num3, num4, num5, num6, num7, num8, num9;
    /**
     * 定义输入文本框
     */
    private EditText calculator;
    private TextView result;

    private View.OnClickListener onclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = calculator.getText().toString();
            /**
             * 如果在文本框中显示的是 error 时，if 条件触发。并且将文本框清空，之后显示按过的数字或者是操作符
             */
            if (str.equals(ERROR)) {
                str = "";
            }
            /**
             * 添加数字到文本中
             */
            if (v == num0) {
                str += "0";
            }
            if (v == num1) {
                str += "1";
            }
            if (v == num2) {
                str += "2";
            }
            if (v == num3) {
                str += "3";
            }
            if (v == num4) {
                str += "4";
            }
            if (v == num5) {
                str += "5";
            }
            if (v == num6) {
                str += "6";
            }
            if (v == num7) {
                str += "7";
            }
            if (v == num8) {
                str += "8";
            }
            if (v == num9) {
                str += "9";
            }

            /**
             * 把操作符显示到文本中
             */
            if (v == pow) {
                str += POW;
            }
            if (v == div) {
                str += DIV;
            }
            if (v == mod) {
                str += MOD;
            }
            if (v == mul) {
                str += MUL;
            }
            /**
             * div 除，mod 取余， mul 乘，sub 减，add 加, point 小数点
             */
            if (v == sub) {
                str += SUB;
            }
            if (v == add) {
                str += ADD;
            }
            if (v == point) {
                str += ".";
            }
            calculator.setText(str);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 实例化
         */
        calculator = (EditText) findViewById(R.id.calculator);
        /**
         * 设置光标的位置
         */
        calculator.setSelection(calculator.getText().length());
        /**
         * 当文本内容发生改变时，对光标进行移动
         */
        calculator.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculator.setSelection(calculator.getText().length());
            }
        });
        result = (TextView) findViewById(R.id.show_result);
        num0 = (Button) findViewById(R.id.num0);
        num1 = (Button) findViewById(R.id.num1);
        num2 = (Button) findViewById(R.id.num2);
        num3 = (Button) findViewById(R.id.num3);
        num4 = (Button) findViewById(R.id.num4);
        num5 = (Button) findViewById(R.id.num5);
        num6 = (Button) findViewById(R.id.num6);
        num7 = (Button) findViewById(R.id.num7);
        num8 = (Button) findViewById(R.id.num8);
        num9 = (Button) findViewById(R.id.num9);

        /**
         * mod, pow, div, mul, sub, add, point;
         */
        mod = (Button) findViewById(R.id.mod);
        pow = (Button) findViewById(R.id.pow);
        div = (Button) findViewById(R.id.div);
        mul = (Button) findViewById(R.id.mul);
        sub = (Button) findViewById(R.id.sub);
        add = (Button) findViewById(R.id.add);
        point = (Button) findViewById(R.id.point);

        /**
         * 添加监听器
         */
        num0.setOnClickListener(onclickListener);
        num1.setOnClickListener(onclickListener);
        num2.setOnClickListener(onclickListener);
        num3.setOnClickListener(onclickListener);
        num4.setOnClickListener(onclickListener);
        num5.setOnClickListener(onclickListener);
        num6.setOnClickListener(onclickListener);
        num7.setOnClickListener(onclickListener);
        num8.setOnClickListener(onclickListener);
        num9.setOnClickListener(onclickListener);


        mod.setOnClickListener(onclickListener);
        pow.setOnClickListener(onclickListener);
        div.setOnClickListener(onclickListener);
        mul.setOnClickListener(onclickListener);
        sub.setOnClickListener(onclickListener);
        add.setOnClickListener(onclickListener);
        point.setOnClickListener(onclickListener);
    }

    /**
     * 点击等号触发的事件
     *
     * @param v
     */
    public void equal(View v) {
        String string = calculator.getText().toString();
        String str = string.substring(string.length() - 1, string.length());
        if (str.equals(ADD) || str.equals(SUB) || str.equals(MUL)
                || str.equals(DIV) || str.equals(MOD) || str.equals(POW)) {
            calculator.setText(ERROR);
        } else {
            String ch = "";
            List<Double> doubles = splitNumFromExp(string);
            Double calResult = 0.0;
            /**
             * 判断是否有操作符，
             * 若没有，直接输出，
             * 若有，则需要对字符串进行截取操作符和数字
             */
            if (doubles.size() == 1) {
                calResult = doubles.get(0);
            } else {
                ch = splitOpFromExp(string);
                /**
                 * 计算加法
                 */
                if (ch.equals(ADD)) {
                    try {
                        Double d1 = doubles.get(0), d2 = doubles.get(1);
                        calResult = d1 + d2;
                    } catch (Exception e) {
                        calculator.setText(ERROR);
                    }
                }
                /**
                 * 计算减法
                 */
                if (ch.equals(SUB)) {
                    try {
                        Double d1 = doubles.get(0), d2 = doubles.get(1);
                        calResult = d1 - d2;
                    } catch (Exception e) {
                        calculator.setText(ERROR);
                    }
                }
                /**
                 * 计算乘法
                 */
                if (ch.equals(MUL)) {
                    try {
                        Double d1 = doubles.get(0), d2 = doubles.get(1);
                        calResult = d1 * d2;
                    } catch (Exception e) {
                        calculator.setText(ERROR);
                    }
                }

                /**
                 * 计算乘方
                 */
                if (ch.equals(POW)) {
                    try {
                        Double d1 = doubles.get(0), d2 = doubles.get(1);
                        calResult = Math.pow(d1, d2);
                    } catch (Exception e) {
                        calculator.setText(ERROR);
                    }
                }
                /**
                 * 取余运算
                 */
                if (ch.equals(MOD)) {
                    try {
                        Double d1 = doubles.get(0), d2 = doubles.get(1);
                        calResult = d1 % d2;
                    } catch (Exception e) {
                        calculator.setText(ERROR);
                    }
                }
            }

            result.setText("" + calResult);
            calculator.setText("0");

            /**
                 * 计算除法
                 */
                if (ch.equals(DIV)) {
                    try {
                        Double d1 = doubles.get(0), d2 = doubles.get(1);
                        if (d2 == 0.0) {
                            calculator.setText("分母不能为零");
                            result.setText("0");
                        } else {
                            calResult = d1 / d2;
                            calculator.setText("0");
                            result.setText("" + calResult);
                        }
                    } catch (Exception e) {
                        calculator.setText(ERROR);
                    }
                }
        }
    }

    /**
     * 分割出来操作符
     *
     * @param exp 要传入的表达式
     * @return
     */
    public String splitOpFromExp(String exp) {
        StringTokenizer st = new StringTokenizer(exp, "1234567890.@");
        String ch = String.valueOf(st.nextElement().toString().trim().charAt(0));
        return ch;
    }

    /**
     * @param exp 要传入的表达式
     * @return
     */
    public List<Double> splitNumFromExp(String exp) {
        List<Double> doubles = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(exp, "+-/*^%");
        while (st.hasMoreTokens()) {
            String numStr = st.nextElement().toString().trim();
            try {
                if (numStr.charAt(0) == '@') {
                    numStr = "-" + numStr.substring(1);
                }
                doubles.add(Double.parseDouble(numStr));
            } catch (NumberFormatException e) {
                calculator.setText(ERROR);
            }
        }
        return doubles;
    }

    /**
     * 点击ac触发的事件，功能是清零工作
     * 清空操作：对文本框设置为空，显示结果的 TextView 设置为 0。
     *
     * @param v
     */
    public void ac(View v) {
        calculator.setText("0");
        result.setText("0");
    }

    /**
     * 对运算表达式进行退格处理，没点一次只能退一个字符
     *
     * @param v
     */
    public void quit(View v) {
        String str = calculator.getText().toString();
        if (str.equals("")) {
            calculator.setText("0");
        } else {
            String str1 = str.substring(0, str.length() - 1);
            if (str1.equals("")) {
                calculator.setText("0");
            } else {
                calculator.setText(str1);
            }
        }
    }
}
