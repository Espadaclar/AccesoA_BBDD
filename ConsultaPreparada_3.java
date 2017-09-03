import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * ------CONSULTAS PREPARADAS. ---------------------
 *  SON DE RENDIMIENTO SUPERIOR PORQUE SE PUEDEN REUTILZAR PARA VARIAS CONSULTAS, DANDO OTRO
 *    VALOR A LOS PARÁMETROS QUE SE REPRESENTAN CON INTERROGACIONES.
 * 
 * UTILIDADES --> 1º Para poder repetir una consulta con diferentes criterios
 * las veces que queramos 2º la misma consulta la puedes repetir varias veces .
 * PASOS : 1º CREAMOS LA CONEXIÓN 2º CREAR UN OBJETO PreparedStatement PARA
 * PREPARAR LA CONSULTA MYSQL (las interrogaciones se les asigna el valor con un
 * mt setString(.., ..), setInt(.., ...) etc.). 3º ESTABLECER PARÁMETROS DE
 * CONSULTA. sentencia.setString(1, "Director"); 4º EJECUTAR Y RECORRER LA
 * CONSULTA ResultSet miResultSet = sentencia.executeQuery();/---crea un objeto
 * de tipo tablaVirtual.
 *
 * @author franciscoJavieR
 * @version (02 / 09 / 2017)
 * @author Usuario
 */
public class ConsultaPreparada_3
{
    
    public static void main(String[] args) {
        //1º CREAMOS LA CONEXIÓN
        try {
            //1º CREAMOS LA CONEXIÓN
            Connection miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false", "root", "francisco");
            //2º CREAR UN OBJETO  PreparedStatement   PARA PODER REALIZAR UNA CONSULTA MYSQL
            PreparedStatement sentencia = miConexion.prepareStatement("SELECT  oficio, comision, nombre, fechaalta FROM empresa.empleado"
                    + " WHERE oficio = ? AND comision = ?");
            //3º ESTABLECER PARÁMETROS DE CONSULTA.
            sentencia.setString(1, "Director");
            sentencia.setFloat(2, 0.00f);
            //4º EJECUTAR Y RECORRER LA CONSULTA
            ResultSet miResultSet = sentencia.executeQuery();//---crea un objeto de tipo tablaVirtual.

            System.out.println("==== DIRECTORES CON 0 € DE COMISION ====");
            while (miResultSet.next()) {
                System.out.println("Oficio.- " + miResultSet.getString(1) + ". Comisión.- " + miResultSet.getFloat(2) + ". Nombre.- "
                        + miResultSet.getString(3) + ". Fecha de alta.- " + miResultSet.getDate(4) + ".");
                //System.out.println(miResultSet.getString(1)+ " " + miResultSet.getFloat(2)+ " " +miResultSet.getString(3));
            }
            
            //=========== REUTILIZAMOS LA MISMA CONSULTA CAMBIANDO EL VALOR DE LOS PARAMETROS ===========
            sentencia.setString(1, "Empleado");
            sentencia.setFloat(2, 0.00f);
            //4º EJECUTAR Y RECORRER LA CONSULTA
            miResultSet = sentencia.executeQuery();//---crea un objeto de tipo tablaVirtual.

            System.out.println("\n==== EMPLEADOS CON 0 € DE COMISION ====");
            while (miResultSet.next()) {
                System.out.println("Oficio.- " + miResultSet.getString(1) + ". Comisión.- " + miResultSet.getFloat(2) + ". Nombre.- "
                        + miResultSet.getString(3) + ". Fecha de alta.- " + miResultSet.getDate(4) + ".");
                //System.out.println(miResultSet.getString(1)+ " " + miResultSet.getFloat(2)+ " " +miResultSet.getString(3));
            }
            ////////////////////////////////////////////////////////////////////////////////
            miResultSet.close();

        } catch (Exception e) {
            System.out.println("no conecta");
            System.out.println("");
            e.printStackTrace();
        }
    }
}
