package app.mobile.fadiel.slotmachinefd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView msg;
    private ImageView img1, img2, img3;
    private Wheel wheel1, wheel2, wheel3;
    private Button btn;
    private boolean isStarted;
    private String button;

    public static final Random RANDOM = new Random();
    private int state;

    public static long randomLong(long lower, long upper) {
        return lower + (long) (RANDOM.nextDouble() * (upper - lower));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        btn = (Button) findViewById(R.id.btn);
        msg = (TextView) findViewById(R.id.msg);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (state){
                    case 0 :
                        wheel1 = new Wheel(new Wheel.WheelListener() {
                        @Override
                        public void newImage(final int img) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    img1.setImageResource(img);
                                }
                            });
                        }
                    }, 200, randomLong(0, 200));

                    wheel1.start();
                    state = 1;
                    btn.setText("Lanjut");
                    break;

                    case 1 :
                    wheel1.stopWheel();
                    wheel2 = new Wheel(new Wheel.WheelListener() {
                        @Override
                        public void newImage(final int img) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    img2.setImageResource(img);
                                }
                            });
                        }
                    }, 200, randomLong(150, 400));

                    wheel2.start();
                    state=2;
                    btn.setText("Lanjut");
                    break;

                    case 2 :
                    wheel2.stopWheel();
                    wheel3 = new Wheel(new Wheel.WheelListener() {
                        @Override
                        public void newImage(final int img) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    img3.setImageResource(img);
                                }
                            });
                        }
                    }, 200, randomLong(150, 400));

                    wheel3.start();
                    btn.setText("Stop");
                    msg.setText("");
                    isStarted = true;
                    state = 3;
                    break;

                    case 3 :
                    wheel3.stopWheel();

                    if (wheel1.currentIndex == wheel2.currentIndex && wheel2.currentIndex == wheel3.currentIndex) {
                        msg.setText("JACKPOT!");
                    } else if (wheel1.currentIndex == wheel2.currentIndex || wheel2.currentIndex == wheel3.currentIndex
                            || wheel1.currentIndex == wheel3.currentIndex) {
                        msg.setText("Balik Modal");
                    } else {
                        msg.setText("Kamu Kalah");
                    }

                    btn.setText("Mulai");
                    button = btn.getText().toString();
                    isStarted = false;
                    state = 0;
                    break;

                }
            }
        });
    }
}
