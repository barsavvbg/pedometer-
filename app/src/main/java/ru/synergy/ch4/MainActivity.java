package ru.synergy.ch4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    int increment;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startbtn = (Button) findViewById(R.id.startbtn);
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                // получаем шаг инкремента из текстового поля
                EditText et = (EditText) findViewById(R.id.increment);
                // конвертируем строку в число
                increment = Integer.parseInt(et.getText().toString());

                // создаём новый диалог
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setMessage("Загрузка...");
                // шкала должна быть горизонтальной
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                // значение шкалы по умолчанию - 0
                dialog.setProgress(0);

                // получаем максимальное значение
                EditText max = (EditText) findViewById(R.id.maximum);
                // конвертируем строку в число
                int maximum = Integer.parseInt(max.getText().toString());
                // устанавливаем максимальное значение
                dialog.setMax(maximum);
                // отображаем диалог
                dialog.show();

                // создаём поток для обновления шкалы
                Thread background = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // увеличиваем значение шкалы каждые 500 мс,
                            // пока не будет достигнуто максимальное значение
                            while (dialog.getProgress() <= dialog.getMax()) {
                                // ждём 500 мс
                                Thread.sleep(500);

                                // активируем обработчик обновления
                                progressHandler.sendMessage (progressHandler.obtainMessage());

                            }
                        } catch (java.lang.InterruptedException e){

                        }
                    }
                });
                // запускаем фоновый поток
                background.start();
            }
            // обработчик для фонового обновления
            Handler progressHandler = new Handler() {
                public void handleMessage (Message msg) {
                    // увеличиваем значение шкалы
                    dialog.incrementProgressBy(increment);
                }
            };
        });
    }
}