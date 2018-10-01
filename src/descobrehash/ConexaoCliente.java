package descobrehash;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConexaoCliente extends Thread{
    Socket socket;
    DataInputStream entrada;
    DataOutputStream saida;
    byte[] bytesArquivo;
    int numero;
    String caracter;
    
        public ConexaoCliente(Socket novaConexao, byte[] bytesArquivo, int numConexao,String codigo) throws IOException {
        socket = novaConexao;
        entrada = new DataInputStream (socket.getInputStream());
        saida = new DataOutputStream (socket.getOutputStream());
        this.bytesArquivo = bytesArquivo;
        numero = numConexao;
        caracter = codigo;
    }
    
    public void run() {
        try {
            saida.writeInt(bytesArquivo.length);
            saida.write(bytesArquivo);
            saida.writeInt(numero);
            String resposta = entrada.readUTF();
            System.out.println(resposta);
            saida.writeUTF(caracter);
            resposta = entrada.readUTF();
            System.out.println(resposta);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
