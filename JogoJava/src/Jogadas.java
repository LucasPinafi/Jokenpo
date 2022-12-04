import java.util.HashSet;
import java.util.Random;


public class Jogadas {
    static HashSet<Jogo> TESOURA_PAPEL() {
        HashSet<Jogo> jogada = new HashSet<>();
        jogada.add(Jogo.TESOURA);
        jogada.add(Jogo.PAPEL);
        return jogada;
    }

    static HashSet<Jogo> PAPEL_PEDRA() {
        HashSet<Jogo> jogada = new HashSet<>();
        jogada.add(Jogo.PAPEL);
        jogada.add(Jogo.PEDRA);
        return jogada;
    }

    static HashSet<Jogo> PEDRA_LAGARTO() {
        HashSet<Jogo> jogada = new HashSet<>();
        jogada.add(Jogo.PEDRA);
        jogada.add(Jogo.LAGARTO);
        return jogada;
    }

    static HashSet<Jogo> LAGARTO_SPOCK() {
        HashSet<Jogo> jogada = new HashSet<>();
        jogada.add(Jogo.LAGARTO);
        jogada.add(Jogo.SPOCK);
        return jogada;
    }

    static HashSet<Jogo> SPOCK_TESOURA() {
        HashSet<Jogo> jogada = new HashSet<>();
        jogada.add(Jogo.SPOCK);
        jogada.add(Jogo.TESOURA);
        return jogada;
    }

    static HashSet<Jogo> TESOURA_LAGARTO() {
        HashSet<Jogo> jogada = new HashSet<>();
        jogada.add(Jogo.TESOURA);
        jogada.add(Jogo.LAGARTO);
        return jogada;
    }

    static HashSet<Jogo> LAGARTO_PAPEL() {
        HashSet<Jogo> jogada = new HashSet<>();
        jogada.add(Jogo.LAGARTO);
        jogada.add(Jogo.PAPEL);
        return jogada;
    }

    static HashSet<Jogo> PAPEL_SPOCK() {
        HashSet<Jogo> jogada = new HashSet<>();
        jogada.add(Jogo.PAPEL);
        jogada.add(Jogo.SPOCK);
        return jogada;
    }

    static HashSet<Jogo> SPOCK_PEDRA() {
        HashSet<Jogo> jogada = new HashSet<>();
        jogada.add(Jogo.SPOCK);
        jogada.add(Jogo.PEDRA);
        return jogada;
    }

    static HashSet<Jogo> PEDRA_TESOURA() {
        HashSet<Jogo> jogada = new HashSet<>();
        jogada.add(Jogo.PEDRA);
        jogada.add(Jogo.TESOURA);
        return jogada;
    }

    static Jogo obter_vencedor(HashSet<Jogo> jogada){
        if(jogada.containsAll(Jogadas.TESOURA_PAPEL())) {
            return Jogo.TESOURA;
        }
        if(jogada.containsAll(Jogadas.PAPEL_PEDRA())) {
            return Jogo.PAPEL;
        }
        if(jogada.containsAll(Jogadas.PEDRA_LAGARTO())) {
            return Jogo.PEDRA;
        }
        if(jogada.containsAll(Jogadas.LAGARTO_SPOCK())) {
            return Jogo.LAGARTO;
        }
        if(jogada.containsAll(Jogadas.SPOCK_TESOURA())) {
            return Jogo.SPOCK;
        }
        if(jogada.containsAll(Jogadas.TESOURA_LAGARTO())) {
            return Jogo.TESOURA;
        }
        if(jogada.containsAll(Jogadas.LAGARTO_PAPEL())) {
            return Jogo.LAGARTO;
        }
        if(jogada.containsAll(Jogadas.PAPEL_SPOCK())) {
            return Jogo.PAPEL;
        }
        if(jogada.containsAll(Jogadas.SPOCK_PEDRA())) {
            return Jogo.SPOCK;
        }
        return Jogo.PEDRA;
    }

    static Jogo escolher_jogada() {
        Jogo jogo[] = Jogo.values();
        Random indice_aleatorio = new Random();
        return jogo[indice_aleatorio.nextInt(jogo.length)];
    }

}
