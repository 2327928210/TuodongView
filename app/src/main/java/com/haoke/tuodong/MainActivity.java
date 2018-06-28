package com.haoke.tuodong;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
    private TouchMoveView touchMoveView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        touchMoveView= (TouchMoveView) findViewById(R.id.touchMoveView);

        touchMoveView.setTouchTargetListener(new TouchMoveView.TouchTargetListener() {
            @Override
            public void onTouchTarget(View tagetView) {

                tagetView.setBackgroundColor(getResources().getColor(R.color.yellow));
            }

            @Override
            public void onTouchTargetEnd(View tagetView) {
                tagetView.setBackgroundColor(getResources().getColor(R.color.red));
            }
        });
    }


}
