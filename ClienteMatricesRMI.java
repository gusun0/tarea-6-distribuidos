import java.rmi.Naming;

public class ClienteMatricesRMI 
{
    
    public static void main(String[] args) throws Exception 
    {
        // Tamaño de la matriz
        int N = Integer.parseInt(args[0]);

        //Definicion de matrices

        float[][] A = new float[N][N];
        float[][] B = new float[N][N];
        float[][] C = new float[N][N];

    
        float[][] A1;
        float[][] A2;
        float[][] B1;
        float[][] B2;

        float[][] C1;
        float[][] C2;
        float[][] C3;
        float[][] C4;

        System.out.println("El numero es " + N);
        String url1 = "rmi://localhost/prueba";
        // String url2 = "rmi://10.0.0.8/prueba";
        
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                A[i][j] = i + 3 * j;
                B[i][j] = i - 3 * j;               
            }
        } 
        
        // transpone la matriz B, la matriz traspuesta queda en B
        for (int i = 0; i < N; i++){
            for (int j = 0; j < i; j++){
                float t = B[i][j];
                B[i][j] = B[j][i];
                B[j][i] = t;
            }
        }
        
        // Obtiene una referencia que "apunta" al objeto remoto asociado a la URL
        InterfaceMatricesRMI r1 = (InterfaceMatricesRMI)Naming.lookup(url1);
        // InterfaceMatricesRMI r2 = (InterfaceMatricesRMI)Naming.lookup(url2);
        
        A1 = separa_matriz(A, 0, N);
        A2 = separa_matriz(A, N/2, N);
        B1 = separa_matriz(B, 0, N); 
        B2 = separa_matriz(B, N/2, N);

        C1 = r1.multiplica_matrices(A1, B1, N);
        C2 = r1.multiplica_matrices(A1, B2, N);
        C3 = r1.multiplica_matrices(A2, B1, N);
        C4 = r1.multiplica_matrices(A2, B2, N);

        acomoda_matriz(C, C1, 0, 0, N);
        acomoda_matriz(C, C2, 0, N/2, N);
        acomoda_matriz(C, C3, N/2, 0, N);
        acomoda_matriz(C, C4, N/2, N/2, N);
        
        if (N == 8){

            imprimir_matriz(A, N, N, "A");
            imprimir_matriz(B, N, N, "B transpuesta");
            imprimir_matriz(C, N, N, "C");
            System.out.println("checksum = " + checksum(C));

        } else {
            System.out.println("checksum = " + checksum(C));
        }
    }    
    
    static void acomoda_matriz (float[][] C,float[][] A, int renglon, int columna, int N) 
    {
        
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N/2; j++)
                C[i + renglon][j + columna] = A[i][j];
    
    }
    
    static void imprimir_matriz(float[][] m, int filas, int columnas, String s) 
    {
       
        System.out.println("\nImprimiendo " + s);
        for (int i = 0; i< filas; i++){
            for (int j = 0; j < columnas; j++){
                System.out.print(m[i][j] + " ");
            }
            System.out.println("");
        }
    }

    static double checksum(float[][] m) {
        
        double s = 0;
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                s += m[i][j];
        return s;    
    }
    
    static float[][] separa_matriz(float[][] A,int inicio, int N) 
    {
        
        float[][] M = new float[N/2][N];
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N; j++)
                M[i][j] = A[i + inicio][j];
        return M;
    
    }
}