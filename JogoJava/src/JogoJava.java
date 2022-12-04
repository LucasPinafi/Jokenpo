import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashSet;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class JogoJava {
    static String SERVIDOR = "192.168.1.15";
    static int PORTA = 5051;
    static Socket conexao;
    static int HEADER = 32;
    static Vector<Jogo> jogador = new Vector<>();
    static Vector<Jogo> adversario = new Vector<>();
    static int placar_jogador = 0;
    static int placar_adversario = 0;

    final static int NUM_MAXIMO_RODADAS = 15;


    public static void main(String[] args) throws IOException {
        conexao = new Socket(SERVIDOR, PORTA);
        new Thread(jogar).start();
        new Thread(receber_jogada).start();
    }

    static Runnable jogar = new Runnable() {
        @Override
        public void run()  {
            while(true) {
                Jogo jogada = Jogadas.escolher_jogada();
                DataOutputStream enviar;

                try {
                    enviar = new DataOutputStream(conexao.getOutputStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    enviar.writeUTF(jogada.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                jogador.add(jogada);
                try {
                    analisar();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    };
    static Runnable receber_jogada = new Runnable() {
        @Override
        public void run() {
            while(true) {
                BufferedReader recebido;
                String jogada_recebida;
                Jogo jogo_recebido = Jogo.TESOURA;
                try {
                    recebido = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                } catch (IOException e) {
                    System.out.println("Excecao1 " + e.getMessage());
                    throw new RuntimeException(e);
                }
                try {
                    jogada_recebida = recebido.readLine();
                } catch (IOException e) {
                    System.out.println("Excecao2 " + e.getMessage());
                    throw new RuntimeException(e);
                }
                for(Jogo _jogo: Jogo.values()) {
                    if(_jogo.toString().equals(jogada_recebida)) {
                        jogo_recebido = _jogo;
                    }
                }
                adversario.add(jogo_recebido);
            }
        }
    };
    static void analisar() throws InterruptedException {
        while(jogador.size() != adversario.size()) {
//            System.out.println(jogador);
//            System.out.println(adversario);
//            System.out.println("---");
            continue;
        }
        Jogo jogou = jogador.get(jogador.size() - 1);
        Jogo recebido = adversario.get(jogador.size() - 1);
        HashSet<Jogo> rodada = new HashSet<>();
        rodada.add(jogou);
        rodada.add(recebido);
        Jogo vitoria = Jogadas.obter_vencedor(rodada);
        String resultado;

        System.out.println("JAVA escolheu: " + jogou.toString());
        System.out.println("PYTHON escolheu: " + recebido.toString());

        if(jogou == recebido) {
            resultado = "Empate!";
        } else {
            if(vitoria == jogou) {
                resultado = "JAVA ganhou a rodada";
                placar_jogador += 1;
            } else {
                resultado = "JAVA perdeu a rodadada!";
                placar_adversario += 1;
            }
        }
        System.out.println(resultado);
        System.out.println("PLACAR: JAVA " + placar_jogador + " X PYTHON " + placar_adversario);
        TimeUnit.SECONDS.sleep(5);
        System.out.println("\n*****************NOVA RODADA*****************");
    }
}