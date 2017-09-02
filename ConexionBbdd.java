import java.sql.*;
/**
 * ===== PROCESOS A SEGUIR DESDE LA CONEXIÓN A LA BBDD HASTA LA LECTURA O MODIFICACIÓN DE DATOS EN AL BBDD. ====
 * 
 *1º establecer conexión con la BBDD, estando en local  creamos un Object conexión y le pasamos la dirección.
	RECORDEMOS QU LANZA UNA EXCEPCION. ¿?????  Poner ‘try catch’.

 *a) crear objeto de tipo ‘interface Connection’ y la cl ‘DriverManager la cual ofrece el servicio básico para manejar el JDBC
 * drive’, dispones de varios mt, getConnection (“…..”); para establecer la conexión, como estamos usando ‘mysql’ 
 * usamos el ‘getConnection("jdbc:mysql://localhost:3306/?useSSL=false","root","francisco");’ 
 *  este método ---devuelve un objeto de tipo ‘Connection’, luego;
 * -Connection miConexion = DriverManager.getConnection("jdbc(Connection):mysql://localhost:3306/?useSSL=false","root","francisco");
		-------  YA HEMOS CREADO LA CONEXIÓN. ----------¡¡¡¡¡¡¡

 *2º Crear un Objeto Statement (para poder ejecutar una consulta mysql) teniendo el  Object miConexion, le pasamos el
 * mt ‘createStatement()’, este mt, devueve un  ‘Objeto Statement’, que utilizamos para enviar consultas SQL a la BBDD. 
 * Luego….. ------------------------- Statement miStatement = (Statement) miConexion.createStatement();
 
 *3º Ejecutar sentencia SQL, (para ello necesitamos el ‘Objeto Statement’),  podemos aplicar su mt
 * ‘executeQuery(“Sentencia SQL”)’ que nos permite ejecutar una sentencia MSQL y que devuelve un objeto de tipo ‘ResulSet’,
 * esto es un objeto con forma de  tabla virtual donde se almacenaR la información que vamos a comentar en la BBDD.
 * luego ….. ------------------------ ResultSet miResultSet = miStatement.executeQuery("Select* from empresa.empleado;");

 *4º Leer el resultset, recorrer esa tabla virtual. para ello utilizamos algunos de los mt que ofrece la cl ‘resulset’, y un bucle.
 *,next(),   va pasando el cursor de una fila a la siguiente.
 *,getString(“….  “),  le pasamos le nombre de la columna de la que queremos obtener los datos.
 *,	getDate("... "), --->  
 *, getDouble(String columnLabel), ---> SI QUEREMOS OPERAR CON LOS DATOS DE LA TABLA TENEMOS QUE UTILIZAR EL MT ADECUADO
 *, getLong(String columnLabel),   ---> QUE CORRESPONDA CON EL TIPO DE DATO QUE CONTIENE LA COLUMNA. ¡¡¡¡¡¡¡¡¡¡

 * @author franciscoJavieR 
 * @version (02 / 09 / 2017)
 */

public class ConexionBbdd
{
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //1º CREAMOS LA CONEXIÓN
        try{
             //----------------------> 1º CREAMOS LA CONEXIÓN
            //creamos el objeto de tipo conexión.
                Connection miConexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false","root","francisco");
            //-----------------------> 2º CREAR UN OBJETO  STATEMENT   PARA PODER REALIZAR UNA CONSULTA MYSQL
            Statement miStatement = (Statement) miConexion.createStatement();
            //-----------------------> 3º EJECUTAR SQL
           // ResultSet miResultSet = miStatement.executeQuery("Select* from empresa.departamento;");
           ResultSet miResultSet = miStatement.executeQuery("Select* from empresa.empleado;");
            //-----------------------> 4º LEER EL MIRESULTSET
            System.out.println("ID   NOMBRE  COMISIÓN\n");
            while(miResultSet.next()){
                System.out.println(miResultSet.getString("idempleado")+ " " +miResultSet.getString("nombre")+ " " +miResultSet.getString("comision"));
                //System.out.println(miResultSet.getString("Localidad"));
            }
        }catch(Exception e){
            System.out.println("no conecta");
            System.out.println("");
            e.printStackTrace();
        }
        
        
    }
    
}
