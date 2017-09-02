import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * -------------------------- CODIGO PARA MODIFICAR LAS TABLAS EN UNA BBDD.
 * 1º CÓMO MODIFICAR REGISTROS EN UNA TABLA.
 * @author Usuario
 */
public class ModiciaBBDD {

    /**
     * @param args the command line arguments
     */
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //1º CREAMOS LA CONEXIÓN
        try{
            //1º CREAMOS LA CONEXIÓN
            //creamos el objeto de tipo conexión.
            Connection miConexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false","root","francisco");
            //2º CREAR UN OBJETO  STATEMENT   PARA PODER REALIZAR UNA CONSULTA MYSQL
            Statement miStatement = (Statement) miConexion.createStatement();

            // PARA MODIFICAR REGISTROS EN UNA TABLA. --> creamos una variable que sea == a la sentencia de msqul que MODIFICA datos.
           //---------------- String instruccionSql = "INSERT INTO empresa.empleado (idempleado, nombre, salario) VALUES(7000 ,'Ana', 3000)";
           String instruccionSql = "UPDATE empresa.empleado SET salario = salario*2 WHERE idempleado = 7000"; 
           // DECIMOS AL PROGRAMA QUE EJECUTE LA INSTRUCCIÓN MSQL.
            //en la interface Statement vemos varios mt que permiten ejecutar la instrucción tenemos uno para (INSERT, UPDATE Y DELETE)
            miStatement.executeUpdate(instruccionSql);
            System.out.println("Datos insertados.");

        }catch(Exception e){
            System.out.println("no conecta");
            System.out.println("");
            e.printStackTrace();
        }
    }   
}

