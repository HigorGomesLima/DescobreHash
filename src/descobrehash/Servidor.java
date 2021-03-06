package descobrehash;

import java.io.File;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
        static List listaConexoes = new ArrayList();
    
    public static void main(String[] args) throws Exception {
        
        ServerSocket ss = new ServerSocket(7777);
        
        File arq = new File("wordlist.txt");
        int tamanhoArquivo = (int) arq.length();
        
        FileInputStream arquivo = new FileInputStream(arq);
        byte[] bytesArquivo = new byte[tamanhoArquivo];
        arquivo.read(bytesArquivo);
        arquivo.close();
        int numeroConexoes = 0;
        int tamanhoI = tamanhoArquivo/5;
        //senha = 0000hdRx
        String descobre = "1b077a0228e8691232b6e88d1d377576a062361d";
        while (numeroConexoes < 5) {
            Socket novaConexao = ss.accept();
            byte[] arquivoCliente = getSubConjunto(bytesArquivo,tamanhoI,numeroConexoes);
            numeroConexoes++;
            ConexaoCliente cc = new ConexaoCliente(novaConexao, arquivoCliente, numeroConexoes,descobre);
            listaConexoes.add(cc);
            cc.start();
        }
    }
    
    public static byte[] getSubConjunto(byte[] array,int tamanho,int posicao){
        byte[] saida = new byte[tamanho];
        int j = 0;
        for(int i = tamanho*posicao;i < tamanho*(posicao+1);i++,j++){
            saida[j] = array[i];
        }
        return saida;
    }
    
}
