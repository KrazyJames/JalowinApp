package mx.itson.jalowin;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public int c = 0;
    public int r;
    public int dulces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button)findViewById(R.id.reiniciar)).setOnClickListener(this);
        reiniciar();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        String opcion = "opcion"+r;
        final int pierde = this.getResources().getIdentifier(opcion,"id", this.getPackageName());
        if (v.getId() == pierde){
            ((Button) findViewById(pierde)).setBackgroundResource(R.drawable.icon_calabaza);
            MediaPlayer m = MediaPlayer.create(this, R.raw.girl_scream);
            m.start();
            c++;
            Toast.makeText(this,"Perdiste :(",Toast.LENGTH_LONG).show();
            ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(1000);
            setEnableButtons(false);
            setPerdidos();
            revelar(pierde);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after X secs = X,000ms
                    reiniciar();
                }
            }, 3000);
        }else if (v.getId() != R.id.reiniciar){
            ((Button) findViewById(v.getId())).setBackgroundResource(R.drawable.icon_dulce);
            setEnableButton(v.getId());
            dulces++;
            if (dulces == 8){
                setEnableButtons(false);
                Toast.makeText(this,"Ganaste",Toast.LENGTH_LONG).show();
                MediaPlayer m = MediaPlayer.create(this,R.raw.witches_laugh);
                m.start();
                ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(1000);
                revelar(pierde);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after X secs = X,000ms
                        reiniciar();
                    }
                }, 3000);
            }
        }else if(v.getId() == R.id.reiniciar){
            reiniciar();
        }
    }

    /**
     * Genera el numero random para la partida
     */
    public void setRandom(){
        r = (int) Math.floor(Math.random()*(9-1+1)+1);
        //System.out.println(r);
    }

    /**
     * Coloca la imagen del sombrero en cada uno de los nueve botones
     *
     */
    public void setSombreros(){
        for (int i = 0; i < 9; i++) {
            String o = "opcion" + (i+1);
            Button btn = ((Button)findViewById(getResources().getIdentifier(o,"id",this.getPackageName())));
            btn.setBackgroundResource(R.drawable.icon_sombrero);
            btn.setOnClickListener(this);
        }
    }

    /**
     * Reinicia los valores por defecto sin alterar la cantidad de partidas perdidas
     *
     */
    public void reiniciar(){
        setSombreros();
        setRandom();
        dulces = 0;
        setPerdidos();
        setEnableButtons(true);
    }

    /**
     * Concatena al textview la cantidad de partidas perdidas
     *
     */
    public void setPerdidos(){
        TextView lblPerdidos = (TextView) findViewById(R.id.lblPerdidos);
        String perdidos = this.getResources().getString(R.string.lblPerdidos)+" "+c;
        lblPerdidos.setText(perdidos);
    }

    /**
     * Habilita o deshabilita los nueve botones ademas del boton de reinicio
     *
     * @param enableButtons si se desea habilitar o deshabilitar los nueve botones (true = habilitar, false = deshabilitar)
     */
    public void setEnableButtons(boolean enableButtons){
        for (int i = 0; i < 9; i++) {
            String o = "opcion" + (i+1);
            Button btn = ((Button)findViewById(getResources().getIdentifier(o,"id",this.getPackageName())));
            btn.setEnabled(enableButtons);
        }
        ((Button)findViewById(getResources().getIdentifier("reiniciar","id",this.getPackageName()))).setEnabled(enableButtons);
    }

    /**
     * Deshabilita el boton por su id
     *
     * @param id el id del boton
     */
    public void setEnableButton(int id){
        Button btn = ((Button)findViewById(id));
        btn.setEnabled(false);
    }

    /**
     * Revela cada uno de los dulces tomando en cuenta la calabaza
     *
     * @param idCalabaza el id de la calabaza
     */
    public void revelar(int idCalabaza){
        for (int i = 0; i < 9; i++) {
            String o = "opcion" + (i+1);
            Button btn = ((Button)findViewById(getResources().getIdentifier(o,"id",this.getPackageName())));
            if (idCalabaza != btn.getId()) {
                btn.setBackgroundResource(R.drawable.icon_dulce);
            }else{
                btn.setBackgroundResource(R.drawable.icon_calabaza);
            }
        }
    }

}
