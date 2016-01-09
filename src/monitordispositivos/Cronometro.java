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

public class Cronometro extends Thread {

    public static int onoff = 0;
    String hrs = "", min = "", seg = "";
    JLabel this_seg, this_min, this_hrs;
    JPanel pnl_vota_fin;
    JPanel pnl_vota_prog;
    Thread hilo;
    boolean cronometroActivo;
    long tiempoRestante;

    public Cronometro(JLabel hrs, JLabel min, JLabel seg, long tiempoRestante, JPanel pnl_prog, JPanel pnl_fin) {
        this_min = min;
        this_hrs = hrs;
        this_seg = seg;
        this.pnl_vota_fin = pnl_fin;
        this.pnl_vota_prog = pnl_prog;
        this.tiempoRestante = tiempoRestante;
    }

    private String calcularEtiqueta(long tiempoActualMilis) {
        int horasRestantes = (int) (tiempoActualMilis / 3600000);
        long minutosRestantesMilis = tiempoActualMilis - horasRestantes * 3600000;
        int minutosRestantes = (int) (minutosRestantesMilis / 60000);
        long segundosRestantesMilis = minutosRestantesMilis - minutosRestantes * 60000;
        int segundosRestantes = (int) (segundosRestantesMilis / 1000);
        return (horasRestantes < 10 ? "0" + horasRestantes : horasRestantes)
                + ":"
                + (minutosRestantes < 10 ? "0" + minutosRestantes : minutosRestantes)
                + ":"
                + (segundosRestantes < 10 ? "0" + segundosRestantes : segundosRestantes);
    }

    public void run() {
        try {
            //Mientras cronometroActivo sea verdadero entonces seguira
            //calculando el tiempo restante.
            while (cronometroActivo && tiempoRestante > 0) {
                Thread.sleep(1000);
                actualizaEtiqueta(calcularEtiqueta(tiempoRestante));
                tiempoRestante -= 1000;
            }
        } catch (InterruptedException e) {
            System.out.println("ERROR DE CONEXIÓN");
        }
    }

    private void actualizaEtiqueta(String datosDeEtiqueta) {
        //Colocamos en la etiqueta la informacion
        String[] datos = datosDeEtiqueta.split(":");
        hrs = datos[0];
        min = datos[1];
        seg = datos[2];
        this_seg.setText(seg);
        this_min.setText(min);
        this_hrs.setText(hrs);
    }

    //Iniciar el cronometro poniendo cronometroActivo 
    //en verdadero para que entre en el while
    public void iniciarCronometro() {
        cronometroActivo = true;
        hilo = new Thread(this);
        hilo.start();
    }

    //Esto es para parar el cronometro
    public void pararCronometro() {
        pnl_vota_fin.setVisible(true);
        pnl_vota_prog.setVisible(false);
        cronometroActivo = false;
    }

    public boolean estatusCronometro() {
        return cronometroActivo;
    }

}
