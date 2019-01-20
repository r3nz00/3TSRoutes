package hacking101.a3tsroutes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class AddRoute extends AppCompatActivity {
    private Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        button = (Button) findViewById(R.id.AddRouteButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkForRoute(v);
            }
        });

    }


    public void checkForRoute(View v){
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.AddRouteTextField);
        String message = editText.getText().toString();
//        Route route;
//        if((route = new Route(message))){
//            intent.putExtra(EXTRA_MESSAGE, message);
//            startActivity(intent);
//        }

    }
}
