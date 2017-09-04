import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * REALIZA UNA SELECCIÓN EN LA TABLA EMPLEADOS, BASADA EN LA SELECIÓN REALIZADA EN LOS JComboBox QUE APARECEN EN LA 
 * INTERFACE_GRÁFICA. LOS JComboBox TRABAJAN DE FORMA CONJUNTA, DE FORMA INDEPENDIENTE Y NO TRABAJAN SI NO HA HABIDO SELECCIÓN.
 * 
 * @franciscoJavieR
 */
public class AplicacionConsultaBBDD_4 {
    public static void main(String[] arg){
        Marco33 marco = new Marco33();
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }    
}

  class Marco33 extends JFrame {

    public Marco33() {
        Lamina lamina = new Lamina();
        add(lamina);

        setBounds(500, 30, 400, 450);
        setTitle("Aplicación para BBDD");
        setVisible(true);

        Toolkit icon = Toolkit.getDefaultToolkit();
        Image imag = icon.getImage("../iconos/a.gif");
        setIconImage(imag);
    }
}

class Lamina extends JPanel {

    private JPanel laminaN, laminaS;
    private JComboBox todos, todos2;
    private JTextArea areaTexto;
    private JButton botonC;

    private JScrollPane barra;

    public Lamina() {

        setLayout(new BorderLayout());//-----------Lamina pricipal.
        laminaN = new JPanel();// ----------lamina que alberga los ComboBox.
        todos = new JComboBox();
        todos2 = new JComboBox();

        try {
            ////////////////////////////////////////////////////////////////////////
            // --- CREAMOS CONSULTA PARA CARGAR EL JComboBox oficios.
            //1º Conectamos con BBDD.
            Connection miConexion2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false", "root", "francisco");
            //2º crea objeto para poder realizar una consulta  -----> createStatement().
            Statement miStatement = miConexion2.createStatement();
            //3º ejecutamos y recorremos la consulta.
            ResultSet miRs = miStatement.executeQuery("SELECT DISTINCTROW oficio from empresa.empleado");
            //-- AL RECORRE miRs CARGA EL OBJETO 'JComboBox'.

            todos.addItem("Select_Oficio");
            while (miRs.next()) {
                String dato = miRs.getString(1);
                todos.addItem(dato);
            }
            // ==== EJECUTAMOS UNA NUEVA SENTENCIA PARA CARGAR OTRO DE LOS JComboBox
            miRs = miStatement.executeQuery("SELECT DISTINCTROW depnum from empresa.empleado");
            //-- AL RECORRE miRs CARGA EL OBJETO 'JComboBox'.
            todos2.addItem("Select_NumDep");
            while (miRs.next()) {
                String dato = miRs.getString(1);
                todos2.addItem(dato);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Lamina.class.getName()).log(Level.SEVERE, null, ex);
        }

        laminaN.add(todos);
        laminaN.add(todos2);

        areaTexto = new JTextArea(); // ----------- area donde aparecen los datos de la consulta a la BBDD.
        barra = new JScrollPane(areaTexto);

        laminaS = new JPanel(); // ---------- lamina que alberga el botón Consulta.
        botonC = new JButton(" Consulta ");
        laminaS.add(botonC);

        add(laminaN, BorderLayout.NORTH);
        add(barra, BorderLayout.CENTER);
        add(laminaS, BorderLayout.SOUTH);

        botonC.addActionListener(new BotonW());// ------- boton a la escucha.
    }

    private class BotonW implements ActionListener {

        // --- DEPENDIENDO DE LAS OPCIONES SELECCIONADAS EN LOS DOS JComboBox(), sentencia TIENE UN VALOR DIFERENTE
        private String sentencia = "___!!! ERROR, NO HA SELECIONADO OPCIÓN. ¡¡¡¡¡¡¡";
        private String sentencia1 = "SELECT oficio, depnum, nombre FROM empresa.empleado WHERE oficio = ? AND depnum = ?";
        private String sentencia2 = "SELECT oficio, depnum, nombre FROM empresa.empleado WHERE oficio = ? ";
        private String sentencia3 = "SELECT oficio, depnum, nombre FROM empresa.empleado WHERE depnum = ? ";

        private PreparedStatement miStatement, miStatement1, miStatement2;
        private ResultSet miRs;

        private String datos = "";

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                //Establece conexión con BBDD.
                Connection miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false", "root", "francisco");
                // _Almacenan el valor de los elementos seleccionados en los JComboBox
                String empleos1 = (String) todos.getSelectedItem();
                String numerosD1 = (String) todos2.getSelectedItem();

                if (empleos1.equals("Select_Oficio") && numerosD1.equals("Select_NumDep")) {
                    areaTexto.setText(" ==== SELECCIONE OPCIÓN EN EL DESPLEGABLE");
                    datos = "";
                } else {
                    if (!empleos1.equals("Select_Oficio") && !numerosD1.equals("Select_NumDep")) {
                        //Prepara la sentencia para aplicarla a la BBDD.
                        miStatement = miConexion.prepareStatement(sentencia1);
                        //Asignamos los parámetros.
                        String valorTodos = (String) todos.getSelectedItem();
                        int valorTodos2 = Integer.parseInt((String) todos2.getSelectedItem());

                        miStatement.setString(1, valorTodos);
                        miStatement.setInt(2, (int) valorTodos2);
                        //Ejecutamos y recorremos la sentencia
                        miRs = miStatement.executeQuery();
                    } else if (!empleos1.equals("Select_Oficio") && numerosD1.equals("Select_NumDep")) {
                        //Prepara la sentencia para aplicarla a la BBDD.
                        miStatement1 = miConexion.prepareStatement(sentencia2);
                        //Asignamos los parámetros.
                        String valorTodos = (String) todos.getSelectedItem();

                        miStatement1.setString(1, valorTodos);
                        miRs = miStatement1.executeQuery();
                    } else if (empleos1.equals("Select_Oficio") && !numerosD1.equals("Select_NumDep")) {
                        //Prepara la sentencia para aplicarla a la BBDD.
                        miStatement2 = miConexion.prepareStatement(sentencia3);
                        //Asignamos los parámetros.
                        int valorTodos2 = Integer.parseInt((String) todos2.getSelectedItem());
                        miStatement2.setInt(1, (int) valorTodos2);
                        
                        //Ejecutamos y recorremos la sentencia.
                        miRs = miStatement2.executeQuery();
                    }
                    while (miRs.next()) {
                        datos += "Oficio.- " + miRs.getString(1) + ".   Nº departamento.- " + miRs.getInt(2) + ".   Nombre.- " + miRs.getString(3) + "\n";
                    }

                    datos += "\n";
                    areaTexto.setText(datos);
                    miRs.close();
                }
                //datos =" ";// -------- para que no se almacenen los datos en el area de texto.
            } catch (SQLException ex) {
                Logger.getLogger(Lamina.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
