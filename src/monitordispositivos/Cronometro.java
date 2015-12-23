/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitordispositivos;

/**
 *
 * @author Alfonso 7
 */
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Cronometro implements Runnable {
    public static int onoff = 0;
    Integer horas , minutos, segundos;
    String hrs="", min="", seg="";
    JLabel this_seg, this_min, this_hrs;
    Thread hilo;
    boolean cronometroActivo;
    JPanel  pnl_vota_fin;
    JPanel  pnl_vota_prog;
    
    
    public Cronometro(JLabel hrs, JLabel min, JLabel seg,Integer hora, Integer minuto, Integer segundo, JPanel pnl_prog, JPanel pnl_fin)
    {
        this_min = min;
        this_hrs = hrs;
        this_seg = seg;
        this.horas = hora;
        this.minutos = minuto;
        this.segundos = segundo;
        this.pnl_vota_fin = pnl_fin;
        this.pnl_vota_prog = pnl_prog;
    }

    public void run(){
        //min es minutos, seg es segundos y mil es milesimas de segundo
        
        try
        {
            //Mientras cronometroActivo sea verdadero entonces seguira
            //aumentando el tiempo
            while( cronometroActivo )
            {
                Thread.sleep( 1000 );
                //Incrementamos 4 milesimas de segundo
                segundos -= 1;

                //Cuando llega a 1000 osea 1 segundo aumenta 1 segundo
                //y las milesimas de segundo de nuevo a 0
                if( segundos < 0 )
                {
                    segundos = 59;
                    minutos -= 1;
                    //Si los segundos llegan a 60 entonces aumenta 1 los minutos
                    //y los segundos vuelven a 0
                    if( minutos < 0 )
                    {
                        minutos = 59;
                        horas--;
                        
                        if(horas < 0)
                        {
                            minutos = 0;
                            segundos = 0;
                            horas = 0;
                            pararCronometro();
                        }
                    }
                    
                }

                //Esto solamente es estetica para que siempre este en formato
                //00:00:000
                if( horas < 10 ) hrs = "0" + horas;
                else hrs = horas.toString();
                if( minutos < 10 ) min = "0" + minutos;
                else min = minutos.toString();

                if( segundos < 10 ) seg = "0" + segundos;
                else seg = segundos.toString();

                //Colocamos en la etiqueta la informacion
                this_seg.setText(seg);
                this_min.setText(min);
                this_hrs.setText(hrs);
                
            }
        }catch(Exception e){
            System.out.println("ERROR DE CONEXIÓN");
        }
    }
    
    //Iniciar el cronometro poniendo cronometroActivo 
    //en verdadero para que entre en el while
    public void iniciarCronometro() {
        cronometroActivo = true;
        hilo = new Thread( this );
        hilo.start();
    }

    //Esto es para parar el cronometro
    public void pararCronometro(){
        pnl_vota_fin.setVisible(true);
        pnl_vota_prog.setVisible(false);
        cronometroActivo = false;
    }
    
    public boolean estatusCronometro(){
        return cronometroActivo;
    }
    
}