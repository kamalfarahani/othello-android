package com.max.kamal.othellogame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.max.kamal.othellogame.R;

public class MainActivity extends AppCompatActivity {

    private EditText[] playersNames = new EditText[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupLayout();
        setupViews();
    }

    private void setupLayout(){
        setContentView(R.layout.activity_main);
        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Othello");
        setSupportActionBar(toolbar);
    }

    private void setupViews() {
        initPlayersName();
        setupSubmitButton();
    }

    private void initPlayersName() {
        playersNames[0] = (EditText)findViewById(R.id.player1_name);
        playersNames[1] = (EditText)findViewById(R.id.player2_name);
    }

    private void setupSubmitButton() {
        Button submitButton = (Button)findViewById(R.id.submit_btn);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (areNamesAllowed()) {
                    Intent intent = new Intent(MainActivity.this, GameBoardActivity.class);
                    intent.putExtra("pl1", playersNames[0].getText().toString());
                    intent.putExtra("pl2", playersNames[1].getText().toString());
                    startActivity(intent);

                } else {
                    Toast.makeText(
                            MainActivity.this, "Please enter names", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean areNamesAllowed() {
        if (playersNames[0].getText().toString().length() > 0
                && playersNames[1].getText().length() > 0)
            return true;

        return false;
    }
}
