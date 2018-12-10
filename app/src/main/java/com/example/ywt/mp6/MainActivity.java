package com.example.ywt.mp6;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private int balance = 100;
    private String type = "";
    private int betAmount = 0;
    private int bestScore = 100;
    public static final String SHAEED_PREFS = "sharedPrefs";
    public static final String saveText = "";
    private String text = "";
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences(SHAEED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(saveText, Integer.toString(bestScore));
        editor.apply();
    }
    private void toLoad() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHAEED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(saveText, "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    private void updateViews() {
        TextView score = findViewById(R.id.score);
        if (text == "") {
            return;
        } else {
            score.setText(text);
            bestScore = Integer.parseInt(text);
        }
    }
    public void whatToBet() {
        TextView gg = findViewById(R.id.gg);
        gg.setVisibility(View.INVISIBLE);
        TextView betType = findViewById(R.id.betType);
        betType.setVisibility(View.INVISIBLE);
        Button odd = findViewById(R.id.odd);
        odd.setVisibility(View.INVISIBLE);
        Button even = findViewById(R.id.even);
        even.setVisibility(View.INVISIBLE);
        Button triple = findViewById(R.id.triple);
        triple.setVisibility(View.INVISIBLE);
        TextView bet = findViewById(R.id.bet);
        bet.setVisibility(View.VISIBLE);
        Button dollar10 = findViewById(R.id.dollar10);
        dollar10.setVisibility(View.VISIBLE);
        Button dollar30 = findViewById(R.id.dollar30);
        dollar30.setVisibility(View.VISIBLE);
        Button dollar50 = findViewById(R.id.dollar50);
        dollar50.setVisibility(View.VISIBLE);
        Button all = findViewById(R.id.all);
        all.setVisibility(View.VISIBLE);
        if (balance < 30) {
            dollar30.setVisibility(View.INVISIBLE);
        }
        if (balance < 50) {
            dollar50.setVisibility(View.INVISIBLE);
        }
    }


    public void betting() {
        final ImageView die1_0 = findViewById(R.id.die1_0);
        final ImageView die2_0 = findViewById(R.id.die2_0);
        final ImageView die3_0 = findViewById(R.id.die3_0);
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (die1_0.getVisibility() == View.VISIBLE) {
                    die1_0.setVisibility(View.INVISIBLE);
                } else {
                    die1_0.setVisibility(View.VISIBLE);
                }
                if (die2_0.getVisibility() == View.VISIBLE) {
                    die2_0.setVisibility(View.INVISIBLE);
                } else {
                    die2_0.setVisibility(View.VISIBLE);
                }
                if (die3_0.getVisibility() == View.VISIBLE) {
                    die3_0.setVisibility(View.INVISIBLE);
                } else {
                    die3_0.setVisibility(View.VISIBLE);
                }
                super.handleMessage(msg);
            }
        };
        final Timer timerone = new Timer(true);
        TimerTask flash = new TimerTask() {
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timerone.schedule(flash, 1, 500);
        final Handler handler1 = new Handler() {
            public void handleMessage(Message msg) {
                timerone.cancel();
                TextView gg = findViewById(R.id.gg);
                gg.setVisibility(View.INVISIBLE);
                TextView win = findViewById(R.id.win);
                TextView lose = findViewById(R.id.lose);
                TextView gold = findViewById(R.id.gold);
                balance = balance - betAmount;
                Random dice = new Random();
                int dice1 = dice.nextInt(6) + 1;
                showFace(1, dice1);
                int dice2 = dice.nextInt(6) + 1;
                showFace(2, dice2);
                int dice3 = dice.nextInt(6) + 1;
                showFace(3, dice3);
                int result = dice1 + dice2 + dice3;
                if (type.equals("odd")) {
                    if (result % 2 == 1) {
                        win.setVisibility(View.VISIBLE);
                        balance = balance + betAmount * 2;
                    } else {
                        lose.setVisibility(View.VISIBLE);
                    }
                }
                if (type.equals("even")) {
                    if (result % 2 == 0) {
                        balance = balance + betAmount * 2;
                        win.setVisibility(View.VISIBLE);
                    } else {
                        lose.setVisibility(View.VISIBLE);
                    }
                }
                if (type.equals("triple")) {
                    if (dice1 == dice2 && dice2 == dice3) {
                        balance = balance + betAmount * 30;
                        win.setVisibility(View.VISIBLE);
                    } else {
                        lose.setVisibility(View.VISIBLE);
                    }
                }
                Button start = findViewById(R.id.start);
                start.setVisibility(View.VISIBLE);
                Button dollar10 = findViewById(R.id.dollar10);
                dollar10.setVisibility(View.INVISIBLE);
                Button dollar30 = findViewById(R.id.dollar30);
                dollar30.setVisibility(View.INVISIBLE);
                Button dollar50 = findViewById(R.id.dollar50);
                dollar50.setVisibility(View.INVISIBLE);
                Button all = findViewById(R.id.all);
                all.setVisibility(View.INVISIBLE);
                TextView bet = findViewById(R.id.bet);
                TextView score = findViewById(R.id.score);
                if (balance <= 0) {
                    gg.setVisibility(View.VISIBLE);
                    start.setVisibility(View.INVISIBLE);
                }
                bet.setVisibility(View.INVISIBLE);
                gold.setText(Integer.toString(balance));
                if (balance > bestScore) {
                    bestScore = balance;
                    score.setText(Integer.toString(bestScore));
                }
            }
        };
        TimerTask cancel = new TimerTask() {
            public void run() {
                Message message2 = new Message();
                message2.what = 3;
                handler1.sendMessage(message2);
            }
        };

        timerone.schedule(cancel, 3000);
    }

    public void showFace(int whichDice, int aNumber) {
        if (whichDice == 1) {
            ImageView die1_0 = findViewById(R.id.die1_0);
            switch(aNumber) {
                case 1: ImageView die1_1 = findViewById(R.id.die1_1);
                        die1_1.setVisibility(View.VISIBLE);
                        die1_0.setVisibility(View.INVISIBLE);
                        break;
                case 2: ImageView die1_2 = findViewById(R.id.die1_2);
                        die1_2.setVisibility(View.VISIBLE);
                        die1_0.setVisibility(View.INVISIBLE);
                        break;
                case 3: ImageView die1_3 = findViewById(R.id.die1_3);
                        die1_3.setVisibility(View.VISIBLE);
                        die1_0.setVisibility(View.INVISIBLE);
                        break;
                case 4: ImageView die1_4 = findViewById(R.id.die1_4);
                        die1_4.setVisibility(View.VISIBLE);
                        die1_0.setVisibility(View.INVISIBLE);
                        break;
                case 5: ImageView die1_5 = findViewById(R.id.die1_5);
                        die1_5.setVisibility(View.VISIBLE);
                        die1_0.setVisibility(View.INVISIBLE);
                        break;
                case 6: ImageView die1_6 = findViewById(R.id.die1_6);
                        die1_6.setVisibility(View.VISIBLE);
                        die1_0.setVisibility(View.INVISIBLE);
                        break;
            }
        }
        if (whichDice == 2) {
            ImageView die2_0 = findViewById(R.id.die2_0);
            switch(aNumber) {
                case 1: ImageView die2_1 = findViewById(R.id.die2_1);
                    die2_1.setVisibility(View.VISIBLE);
                    die2_0.setVisibility(View.INVISIBLE);
                    break;
                case 2: ImageView die2_2 = findViewById(R.id.die2_2);
                    die2_2.setVisibility(View.VISIBLE);
                    die2_0.setVisibility(View.INVISIBLE);
                    break;
                case 3: ImageView die2_3 = findViewById(R.id.die2_3);
                    die2_3.setVisibility(View.VISIBLE);
                    die2_0.setVisibility(View.INVISIBLE);
                    break;
                case 4: ImageView die2_4 = findViewById(R.id.die2_4);
                    die2_4.setVisibility(View.VISIBLE);
                    die2_0.setVisibility(View.INVISIBLE);
                    break;
                case 5: ImageView die2_5 = findViewById(R.id.die2_5);
                    die2_5.setVisibility(View.VISIBLE);
                    die2_0.setVisibility(View.INVISIBLE);
                    break;
                case 6: ImageView die2_6 = findViewById(R.id.die2_6);
                    die2_6.setVisibility(View.VISIBLE);
                    die2_0.setVisibility(View.INVISIBLE);
                    break;
            }
        }
        if (whichDice == 3) {
            ImageView die3_0 = findViewById(R.id.die3_0);
            switch(aNumber) {
                case 1: ImageView die3_1 = findViewById(R.id.die3_1);
                    die3_1.setVisibility(View.VISIBLE);
                    die3_0.setVisibility(View.INVISIBLE);
                    break;
                case 2: ImageView die3_2 = findViewById(R.id.die3_2);
                    die3_2.setVisibility(View.VISIBLE);
                    die3_0.setVisibility(View.INVISIBLE);
                    break;
                case 3: ImageView die3_3 = findViewById(R.id.die3_3);
                    die3_3.setVisibility(View.VISIBLE);
                    die3_0.setVisibility(View.INVISIBLE);
                    break;
                case 4: ImageView die3_4 = findViewById(R.id.die3_4);
                    die3_4.setVisibility(View.VISIBLE);
                    die3_0.setVisibility(View.INVISIBLE);
                    break;
                case 5: ImageView die3_5 = findViewById(R.id.die3_5);
                    die3_5.setVisibility(View.VISIBLE);
                    die3_0.setVisibility(View.INVISIBLE);
                    break;
                case 6: ImageView die3_6 = findViewById(R.id.die3_6);
                    die3_6.setVisibility(View.VISIBLE);
                    die3_0.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView die1_0 = findViewById(R.id.die1_0);
                die1_0.setVisibility(View.VISIBLE);
                ImageView die1_1 = findViewById(R.id.die1_1);
                die1_1.setVisibility(View.INVISIBLE);
                ImageView die1_2 = findViewById(R.id.die1_2);
                die1_2.setVisibility(View.INVISIBLE);
                ImageView die1_3 = findViewById(R.id.die1_3);
                die1_3.setVisibility(View.INVISIBLE);
                ImageView die1_4 = findViewById(R.id.die1_4);
                die1_4.setVisibility(View.INVISIBLE);
                ImageView die1_5 = findViewById(R.id.die1_5);
                die1_5.setVisibility(View.INVISIBLE);
                ImageView die1_6 = findViewById(R.id.die1_6);
                die1_6.setVisibility(View.INVISIBLE);

                ImageView die2_0 = findViewById(R.id.die2_0);
                die2_0.setVisibility(View.VISIBLE);
                ImageView die2_1 = findViewById(R.id.die2_1);
                die2_1.setVisibility(View.INVISIBLE);
                ImageView die2_2 = findViewById(R.id.die2_2);
                die2_2.setVisibility(View.INVISIBLE);
                ImageView die2_3 = findViewById(R.id.die2_3);
                die2_3.setVisibility(View.INVISIBLE);
                ImageView die2_4 = findViewById(R.id.die2_4);
                die2_4.setVisibility(View.INVISIBLE);
                ImageView die2_5 = findViewById(R.id.die2_5);
                die2_5.setVisibility(View.INVISIBLE);
                ImageView die2_6 = findViewById(R.id.die2_6);
                die2_6.setVisibility(View.INVISIBLE);

                ImageView die3_0 = findViewById(R.id.die3_0);
                die3_0.setVisibility(View.VISIBLE);
                ImageView die3_1 = findViewById(R.id.die3_1);
                die3_1.setVisibility(View.INVISIBLE);
                ImageView die3_2 = findViewById(R.id.die3_2);
                die3_2.setVisibility(View.INVISIBLE);
                ImageView die3_3 = findViewById(R.id.die3_3);
                die3_3.setVisibility(View.INVISIBLE);
                ImageView die3_4 = findViewById(R.id.die3_4);
                die3_4.setVisibility(View.INVISIBLE);
                ImageView die3_5 = findViewById(R.id.die3_5);
                die3_5.setVisibility(View.INVISIBLE);
                ImageView die3_6 = findViewById(R.id.die3_6);
                die3_6.setVisibility(View.INVISIBLE);

                Button dollar10 = findViewById(R.id.dollar10);
                dollar10.setVisibility(View.INVISIBLE);
                Button dollar30 = findViewById(R.id.dollar30);
                dollar30.setVisibility(View.INVISIBLE);
                Button dollar50 = findViewById(R.id.dollar50);
                dollar50.setVisibility(View.INVISIBLE);
                Button all = findViewById(R.id.all);
                all.setVisibility(View.INVISIBLE);

                die3_0.setVisibility(View.VISIBLE);
                TextView win = findViewById(R.id.win);
                TextView lose = findViewById(R.id.lose);
                Button name = findViewById(R.id.start);
                name.setVisibility(View.INVISIBLE);
                lose.setVisibility(View.INVISIBLE);
                win.setVisibility(View.INVISIBLE);
                TextView betType = findViewById(R.id.betType);
                betType.setVisibility(View.VISIBLE);
                Button odd = findViewById(R.id.odd);
                odd.setVisibility(View.VISIBLE);
                Button even = findViewById(R.id.even);
                even.setVisibility(View.VISIBLE);
                Button triple = findViewById(R.id.triple);
                triple.setVisibility(View.VISIBLE);
            }
        });

        Button odd = findViewById(R.id.odd);
        odd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatToBet();
                type = "odd";
            }
        });
        Button even = findViewById(R.id.even);
        even.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatToBet();
                type = "even";
            }
        });
        Button triple = findViewById(R.id.triple);
        triple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatToBet();
                type = "triple";
            }
        });
        Button dollar10 = findViewById(R.id.dollar10);
        dollar10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                betAmount = 10;
                betting();
            }
        });
        Button dollar30 = findViewById(R.id.dollar30);
        dollar30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                betAmount = 30;
                betting();
            }
        });
        Button dollar50 = findViewById(R.id.dollar50);
        dollar50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                betAmount = 50;
                betting();
            }
        });
        Button all = findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                betAmount = balance;
                betting();
            }
        });
        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView gg = findViewById(R.id.gg);
                gg.setVisibility(View.INVISIBLE);

                ImageView die1_0 = findViewById(R.id.die1_0);
                die1_0.setVisibility(View.VISIBLE);
                ImageView die1_1 = findViewById(R.id.die1_1);
                die1_1.setVisibility(View.INVISIBLE);
                ImageView die1_2 = findViewById(R.id.die1_2);
                die1_2.setVisibility(View.INVISIBLE);
                ImageView die1_3 = findViewById(R.id.die1_3);
                die1_3.setVisibility(View.INVISIBLE);
                ImageView die1_4 = findViewById(R.id.die1_4);
                die1_4.setVisibility(View.INVISIBLE);
                ImageView die1_5 = findViewById(R.id.die1_5);
                die1_5.setVisibility(View.INVISIBLE);
                ImageView die1_6 = findViewById(R.id.die1_6);
                die1_6.setVisibility(View.INVISIBLE);

                Button dollar10 = findViewById(R.id.dollar10);
                dollar10.setVisibility(View.INVISIBLE);
                Button dollar30 = findViewById(R.id.dollar30);
                dollar30.setVisibility(View.INVISIBLE);
                Button dollar50 = findViewById(R.id.dollar50);
                dollar50.setVisibility(View.INVISIBLE);
                Button all = findViewById(R.id.all);
                all.setVisibility(View.INVISIBLE);

                ImageView die2_0 = findViewById(R.id.die2_0);
                die2_0.setVisibility(View.VISIBLE);
                ImageView die2_1 = findViewById(R.id.die2_1);
                die2_1.setVisibility(View.INVISIBLE);
                ImageView die2_2 = findViewById(R.id.die2_2);
                die2_2.setVisibility(View.INVISIBLE);
                ImageView die2_3 = findViewById(R.id.die2_3);
                die2_3.setVisibility(View.INVISIBLE);
                ImageView die2_4 = findViewById(R.id.die2_4);
                die2_4.setVisibility(View.INVISIBLE);
                ImageView die2_5 = findViewById(R.id.die2_5);
                die2_5.setVisibility(View.INVISIBLE);
                ImageView die2_6 = findViewById(R.id.die2_6);
                die2_6.setVisibility(View.INVISIBLE);

                ImageView die3_0 = findViewById(R.id.die3_0);
                die3_0.setVisibility(View.VISIBLE);
                ImageView die3_1 = findViewById(R.id.die3_1);
                die3_1.setVisibility(View.INVISIBLE);
                ImageView die3_2 = findViewById(R.id.die3_2);
                die3_2.setVisibility(View.INVISIBLE);
                ImageView die3_3 = findViewById(R.id.die3_3);
                die3_3.setVisibility(View.INVISIBLE);
                ImageView die3_4 = findViewById(R.id.die3_4);
                die3_4.setVisibility(View.INVISIBLE);
                ImageView die3_5 = findViewById(R.id.die3_5);
                die3_5.setVisibility(View.INVISIBLE);
                ImageView die3_6 = findViewById(R.id.die3_6);
                die3_6.setVisibility(View.INVISIBLE);

                TextView win = findViewById(R.id.win);
                TextView lose = findViewById(R.id.lose);
                Button name = findViewById(R.id.start);
                name.setVisibility(View.VISIBLE);
                lose.setVisibility(View.INVISIBLE);
                win.setVisibility(View.INVISIBLE);
                TextView betType = findViewById(R.id.betType);
                betType.setVisibility(View.INVISIBLE);
                Button odd = findViewById(R.id.odd);
                odd.setVisibility(View.INVISIBLE);
                Button even = findViewById(R.id.even);
                even.setVisibility(View.INVISIBLE);
                Button triple = findViewById(R.id.triple);
                triple.setVisibility(View.INVISIBLE);
                TextView bet = findViewById(R.id.bet);
                bet.setVisibility(View.INVISIBLE);
                TextView gold = findViewById(R.id.gold);
                balance = 100;
                gold.setText(Integer.toString(balance));
            }
        });
        toLoad();
        updateViews();
    }
}
