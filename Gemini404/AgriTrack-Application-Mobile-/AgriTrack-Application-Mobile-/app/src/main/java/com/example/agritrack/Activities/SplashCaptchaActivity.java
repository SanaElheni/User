package com.example.agritrack.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.agritrack.R;
import java.util.Random;

public class SplashCaptchaActivity extends AppCompatActivity {

    private TextView tvCaptcha, tvTimer;
    private EditText etAnswer;
    private Button btnVerify;
    private int num1, num2, correctAnswer;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_captcha);

        initViews();
        generateNewCaptcha();
        startTimer();
    }

    private void initViews() {
        tvCaptcha = findViewById(R.id.tvCaptcha);
        tvTimer = findViewById(R.id.tvTimer);
        etAnswer = findViewById(R.id.etAnswer);
        btnVerify = findViewById(R.id.btnVerify);

        btnVerify.setOnClickListener(v -> verifyAnswer());
        etAnswer.setOnEditorActionListener((v, actionId, event) -> {
            verifyAnswer();
            return true;
        });
    }

    private void generateNewCaptcha() {
        Random random = new Random();
        num1 = random.nextInt(15) + 5;  // 5-20
        num2 = random.nextInt(15) + 5;  // 5-20
        correctAnswer = num1 + num2;
        tvCaptcha.setText(num1 + " + " + num2 + " = ?");
        etAnswer.setText("");
        etAnswer.requestFocus();
    }

    private void verifyAnswer() {
        try {
            String answerStr = etAnswer.getText().toString().trim();
            if (answerStr.isEmpty()) return;

            int userAnswer = Integer.parseInt(answerStr);
            if (userAnswer == correctAnswer) {
                Toast.makeText(this, "✅ Vérification réussie !", Toast.LENGTH_SHORT).show();
                if (timer != null) timer.cancel();
                goToFinances();
            } else {
                Toast.makeText(this, "❌ " + correctAnswer + " ! Essaye encore", Toast.LENGTH_SHORT).show();
                generateNewCaptcha();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "❌ Nombre seulement", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTimer() {
        timer = new CountDownTimer(15000, 1000) {  // 15s
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("⏱️ " + (millisUntilFinished / 1000) + "s");
            }
            @Override
            public void onFinish() {
                Toast.makeText(SplashCaptchaActivity.this, "⚠️ Timeout - Accès autorisé", Toast.LENGTH_SHORT).show();
                goToFinances();
            }
        }.start();
    }

    private void goToFinances() {
        startActivity(new Intent(this, FinanceActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
    }
}
