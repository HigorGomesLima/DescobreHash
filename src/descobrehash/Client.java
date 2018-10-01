package descobrehash;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Client {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Socket socket = new Socket ("localhost", 7777);
        DataInputStream entrada = new DataInputStream (socket.getInputStream());
        DataOutputStream saida = new DataOutputStream (socket.getOutputStream());
        int tamanho = entrada.readInt();
        System.out.println("Procurando ...");
        byte[] bytesArquivo = new byte[tamanho];
        entrada.readFully(bytesArquivo);
        int numArq = entrada.readInt();
        FileOutputStream arquivo = new FileOutputStream(new File("arquivoCliente" + numArq + ".txt"));
        arquivo.write(bytesArquivo);
        saida.writeUTF("Cliente "+numArq+" iniciado");
        String codigo = entrada.readUTF();
        arquivo.close();
        
        FileReader arq = new FileReader("arquivoCliente" + numArq + ".txt");
        BufferedReader lerArq = new BufferedReader(arq);
        String linha = lerArq.readLine();
        String senha = null;
        int posicao = 0;
        while(linha != null && senha == null){
            MessageDigest m = MessageDigest.getInstance("SHA1");
            m.update(linha.getBytes(), 0, linha.length());
            String atual = new BigInteger(1, m.digest()).toString(16);
            if(atual.equals(codigo)){
                senha = linha;
                //System.out.println("FOI !!");
            }
            linha = lerArq.readLine();
            posicao++;
        }
        lerArq.close();
        arq.close();
        if(senha != null){
            saida.writeUTF("Cliente "+ numArq+" Senha encontrada: " + senha + " na posicao :"+ (tamanho*(numArq-1)+posicao));
        }else{
            saida.writeUTF("Cliente "+ numArq+" Senha não encontrada");
        }
        
    }
}
