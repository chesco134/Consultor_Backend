/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitordispositivos;

import InterfazUsuario.Acceso;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.ImageIcon;
import org.json.JSONException;
import org.json.JSONObject;
import polivoto.networking.IOHandler;

/**
 *
 * @author Alfonso 7
 */
public class AnalistaLocal extends javax.swing.JFrame {

    boolean progreso = true;
    Cronometro cronometro;
    RecibirVotos escuchar;
    int votos = 0;
    Integer poblacion;

    /**
     * Creates new form Analista_local
     */
    public AnalistaLocal() {
        poblacion = null;
        initComponents();
        /*
         * Obtener dimensiones de pantalla
         */
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // System.out.println("Tu resolución es de " + screenSize.width + "x" + screenSize.height);

        /*
         * Dibujar Paneles
         */
        this.setSize(screenSize);
        this.getContentPane().setBackground(Color.WHITE);

        /*
         * Cabecera
         */
        ImageIcon img = new ImageIcon(getClass().getResource("/imagenes/cabecera_fondo.png"));
        cabecera_fondo.setIcon(new ImageIcon(img.getImage().getScaledInstance(cabecera_fondo.getWidth(), cabecera_fondo.getHeight(), Image.SCALE_SMOOTH)));

        img = new ImageIcon(getClass().getResource("/imagenes/logo_polivoto.png"));
        logo_polivoto.setIcon(new ImageIcon(img.getImage().getScaledInstance(logo_polivoto.getWidth(), logo_polivoto.getHeight(), Image.SCALE_SMOOTH)));

        img = new ImageIcon(getClass().getResource("/imagenes/logo_ipn.png"));
        logo_ipn.setIcon(new ImageIcon(img.getImage().getScaledInstance(logo_ipn.getWidth(), logo_ipn.getHeight(), Image.SCALE_SMOOTH)));

        img = new ImageIcon(getClass().getResource("/imagenes/UPIITA.png"));
        logo_escuela.setIcon(new ImageIcon(img.getImage().getScaledInstance(logo_escuela.getWidth(), logo_escuela.getHeight(), Image.SCALE_SMOOTH)));

        String nombre_escuela = "Unidad Profesional interdisciplinaria en Ingeniería y Tecnologías Avanzadas del IPN";
        lblescuela.setText("<html> <div style=\"text-align: center;\" >" + nombre_escuela + "</div></html>");
        lblmsj_esperando.setText("<html> <div style=\"text-align: justify;\" >" + lblmsj_esperando.getText() + "</div></html>");
        /*
         * Panel Votaciones Terminadas
         */
        pnl_vota_fin.setVisible(false);
        pnl_vota_prog.setVisible(true);
        pnl_consultar.setVisible(false);
    }

    public void beginListening() {
        Service service = new Service();
        service.start();
    }

    private class Service extends Thread {

        private IOHandler ioHandler;

        @Override
        public void run() {
            try {
                ServerSocket server = new ServerSocket(5004);
                JSONObject json;
                json = new JSONObject(Acceso.AC.consultaParametrosIniciales());
                cronometro = new Cronometro(lblhrs, lblmin, lblseg, json.getInt("horas"), json.getInt("minutos"), json.getInt("segundos"), pnl_vota_prog, pnl_vota_fin);
                cronometro.iniciarCronometro();
                escuchar = new RecibirVotos();
                poblacion = json.getInt("poblacion");
                poblacion = new Integer(0);
                System.out.println("Startup dada: " + json.toString());
                escuchar.iniciarEscucha(votos - 1, poblacion, lblvotos_totales, lblporcentaje, pnlgrafica, pnl_consultar, pnl_esperar);

                /*
                 * Inicia cronómetro
                 */
                while (RecibirVotos.RECIBIENDO) {
                    Socket socket = server.accept(); // We should perform some kind of validation...
                    ioHandler = new IOHandler(new DataInputStream(socket.getInputStream()), new DataOutputStream(socket.getOutputStream()));
                    json = new JSONObject(new String(ioHandler.handleIncommingMessage()));
                    ioHandler.close();
                    socket.close();
                    switch (json.getInt("action")) {
                        case 1:
                            System.out.println("Voto nuevo");
                            escuchar.actualizarConteo();
                            break;
                        case 2:
                            System.out.println("Proceso Finalizado");
                            escuchar.pnl_consulta.setVisible(true);
                            escuchar.pnl_espera.setVisible(false);
                            RecibirVotos.RECIBIENDO = false;
                            break;
                        case 3:
                            poblacion = json.getInt("poblacion");
                            escuchar.votos--;
                            escuchar.actualizarConteo();
                            break;
                    }
                    socket.close();
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        logo_polivoto = new javax.swing.JLabel();
        logo_escuela = new javax.swing.JLabel();
        logo_ipn = new javax.swing.JLabel();
        lblconsultor = new javax.swing.JLabel();
        cabecera_fondo = new javax.swing.JLabel();
        pnl_vota_fin = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pnl_consultar = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        pnl_esperar = new javax.swing.JPanel();
        lblmsj_esperando = new javax.swing.JLabel();
        lbl_cargando = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lbl_votos_totales = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblvotos_totales = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lbl_porcentaje = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        pnlgrafica = new javax.swing.JPanel();
        lblporcentaje = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lbl_tiemporest = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        lblhrs = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblmin = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblseg = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblpuntos = new javax.swing.JLabel();
        lblpuntos1 = new javax.swing.JLabel();
        pnl_vota_prog = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        lblescuela = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Analísta Local");
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(getToolkit().getScreenSize());
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PoliVoto Electrónico ");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, -1, -1));

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("© ");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, -1, -1));

        logo_polivoto.setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().add(logo_polivoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 60, 60));
        getContentPane().add(logo_escuela, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 0, 60, 60));
        getContentPane().add(logo_ipn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 0, 70, 60));

        lblconsultor.setFont(new java.awt.Font("Calibri", 0, 36)); // NOI18N
        lblconsultor.setForeground(new java.awt.Color(255, 255, 255));
        lblconsultor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblconsultor.setText("CONSULTOR");
        getContentPane().add(lblconsultor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 1370, 50));

        cabecera_fondo.setText("fondo");
        getContentPane().add(cabecera_fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1380, 60));

        pnl_vota_fin.setBackground(new java.awt.Color(255, 255, 255));
        pnl_vota_fin.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 0), 3, true));
        pnl_vota_fin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("EL TIEMPO DESTINADO AL PROCESO HA EXPIRADO.");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 696, -1));

        pnl_consultar.setBackground(new java.awt.Color(255, 255, 255));
        pnl_consultar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setBackground(new java.awt.Color(153, 51, 0));
        jButton2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("CONSULTAR");
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        pnl_consultar.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 212, 67));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("AHORA PUEDES CONSULTAR LOS RESULTADOS FINALES.");
        pnl_consultar.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 686, -1));

        jPanel5.add(pnl_consultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 680, 430));

        pnl_esperar.setBackground(new java.awt.Color(255, 255, 255));
        pnl_esperar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblmsj_esperando.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lblmsj_esperando.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblmsj_esperando.setText("ESPERANDO A QUE LOS USUARIO QUE YA HABÍAN INICIADO SU PROCESO DE VOTACIÓN O FINALICEN.");
        pnl_esperar.add(lblmsj_esperando, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 570, 80));

        lbl_cargando.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/espera.gif"))); // NOI18N
        lbl_cargando.setText("jLabel10");
        pnl_esperar.add(lbl_cargando, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, 130, 140));

        jPanel5.add(pnl_esperar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 680, 430));

        pnl_vota_fin.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 690, 500));

        jPanel6.setBackground(new java.awt.Color(204, 255, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 204, 0)));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Calibri Light", 1, 36)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("VOTACIONES FINALIZADAS");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 690, -1));

        pnl_vota_fin.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 690, 100));

        getContentPane().add(pnl_vota_fin, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 80, 710, 640));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 0), 3, true));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_votos_totales.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        lbl_votos_totales.setText("VOTOS TOTALES:");
        jPanel2.add(lbl_votos_totales, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblvotos_totales.setFont(new java.awt.Font("Arial", 1, 100)); // NOI18N
        lblvotos_totales.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblvotos_totales.setText("0");
        jPanel7.add(lblvotos_totales, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 300, 96));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 40)); // NOI18N
        jLabel6.setText("VOTOS");
        jPanel7.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, 150, 50));

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 560, 150));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 580, 200));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 0), 3, true));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_porcentaje.setFont(new java.awt.Font("Calibri", 1, 20)); // NOI18N
        lbl_porcentaje.setText("PORCENTAJE DE PARTICIPACIÓN:");
        jPanel3.add(lbl_porcentaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlgrafica.setEnabled(false);

        javax.swing.GroupLayout pnlgraficaLayout = new javax.swing.GroupLayout(pnlgrafica);
        pnlgrafica.setLayout(pnlgraficaLayout);
        pnlgraficaLayout.setHorizontalGroup(
            pnlgraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        pnlgraficaLayout.setVerticalGroup(
            pnlgraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );

        jPanel8.add(pnlgrafica, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 320, 220));

        lblporcentaje.setBackground(new java.awt.Color(255, 255, 255));
        lblporcentaje.setFont(new java.awt.Font("Arial", 1, 80)); // NOI18N
        lblporcentaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblporcentaje.setText("0%");
        jPanel8.add(lblporcentaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 310, 110));

        jPanel3.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 560, 210));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 580, 230));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 0), 3, true));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_tiemporest.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lbl_tiemporest.setText("TIEMPO RESTANTE:");
        jPanel4.add(lbl_tiemporest, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblhrs.setBackground(new java.awt.Color(255, 255, 255));
        lblhrs.setFont(new java.awt.Font("Arial", 1, 90)); // NOI18N
        lblhrs.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblhrs.setText("00");
        lblhrs.setOpaque(true);
        jPanel9.add(lblhrs, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 140, 80));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Hrs");
        jLabel7.setOpaque(true);
        jPanel9.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 90, 40));

        lblmin.setBackground(new java.awt.Color(255, 255, 255));
        lblmin.setFont(new java.awt.Font("Arial", 1, 90)); // NOI18N
        lblmin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblmin.setText("00");
        lblmin.setOpaque(true);
        jPanel9.add(lblmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, 130, 80));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("seg");
        jLabel9.setOpaque(true);
        jPanel9.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 100, 60));

        lblseg.setBackground(new java.awt.Color(255, 255, 255));
        lblseg.setFont(new java.awt.Font("Arial", 1, 90)); // NOI18N
        lblseg.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblseg.setText("00");
        lblseg.setOpaque(true);
        jPanel9.add(lblseg, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, 120, 80));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("min");
        jLabel11.setOpaque(true);
        jPanel9.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 100, 40));

        lblpuntos.setBackground(new java.awt.Color(255, 255, 255));
        lblpuntos.setFont(new java.awt.Font("Arial", 1, 100)); // NOI18N
        lblpuntos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblpuntos.setText(":");
        lblpuntos.setOpaque(true);
        jPanel9.add(lblpuntos, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, 40, 70));

        lblpuntos1.setBackground(new java.awt.Color(255, 255, 255));
        lblpuntos1.setFont(new java.awt.Font("Arial", 1, 100)); // NOI18N
        lblpuntos1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblpuntos1.setText(":");
        lblpuntos1.setOpaque(true);
        jPanel9.add(lblpuntos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 40, 70));

        jPanel4.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 560, 150));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 530, 580, 190));

        pnl_vota_prog.setBackground(new java.awt.Color(255, 255, 255));
        pnl_vota_prog.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 0), 3, true));
        pnl_vota_prog.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 686, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 496, Short.MAX_VALUE)
        );

        pnl_vota_prog.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 690, 500));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setText("INFORMACIÓN:");
        pnl_vota_prog.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblescuela.setBackground(new java.awt.Color(255, 255, 255));
        lblescuela.setFont(new java.awt.Font("Calibri", 0, 30)); // NOI18N
        lblescuela.setForeground(new java.awt.Color(102, 0, 0));
        lblescuela.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblescuela.setText("VOTACIONES EN PROGRESO");
        jPanel11.add(lblescuela, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 650, 80));

        pnl_vota_prog.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 690, 80));

        getContentPane().add(pnl_vota_prog, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 80, 710, 640));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void init() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AnalistaLocal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnalistaLocal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnalistaLocal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnalistaLocal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AnalistaLocal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cabecera_fondo;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lbl_cargando;
    private javax.swing.JLabel lbl_porcentaje;
    private javax.swing.JLabel lbl_tiemporest;
    private javax.swing.JLabel lbl_votos_totales;
    private javax.swing.JLabel lblconsultor;
    private javax.swing.JLabel lblescuela;
    private javax.swing.JLabel lblhrs;
    private javax.swing.JLabel lblmin;
    private javax.swing.JLabel lblmsj_esperando;
    private javax.swing.JLabel lblporcentaje;
    private javax.swing.JLabel lblpuntos;
    private javax.swing.JLabel lblpuntos1;
    private javax.swing.JLabel lblseg;
    private javax.swing.JLabel lblvotos_totales;
    private javax.swing.JLabel logo_escuela;
    private javax.swing.JLabel logo_ipn;
    private javax.swing.JLabel logo_polivoto;
    private javax.swing.JPanel pnl_consultar;
    private javax.swing.JPanel pnl_esperar;
    private javax.swing.JPanel pnl_vota_fin;
    private javax.swing.JPanel pnl_vota_prog;
    private javax.swing.JPanel pnlgrafica;
    // End of variables declaration//GEN-END:variables

}
