import java.rmi.Naming;

public class ServidorMatricesRMI {
    
    public static void main(String[] args) throws Exception{
        String url = "rmi://localhost/prueba";
        ClaseMatricesRMI obj = new ClaseMatricesRMI();
    
        // Registra la instancia en el rmiregistry
        Naming.rebind(url,obj);
    }
}