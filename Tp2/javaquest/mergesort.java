
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.time.Duration;

class Jogador {
    private int id;
    private String nome;
    private int altura;
    private int peso;
    private String universidade;
    private int anoNascimento;
    private String cidadeNascimento;
    private String estadoNascimento;

    Jogador() {
        id = -1;
        nome = "";
        altura = -1;
        peso = -1;
        universidade = "";
        anoNascimento = -1;
        cidadeNascimento = "";
        estadoNascimento = "";
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getAltura() {
        return altura;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getPeso() {
        return peso;
    }

    public void setUniversidade(String universidade) {
        this.universidade = universidade;
    }

    public String getUniversidade() {
        return universidade;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public int getAnoNascimento() {
        return anoNascimento;
    }

    public void setCidadeNascimento(String cidadeNascimento) {
        this.cidadeNascimento = cidadeNascimento;
    }

    public String getCidadeNascimento() {
        return cidadeNascimento;
    }

    public void setEstadoNascimento(String estadoNascimento) {
        this.estadoNascimento = estadoNascimento;
    }

    public String getEstadoNascimento() {
        return estadoNascimento;
    }

    public void ler(String s) {
        String[] array = s.split(",", 8);
        this.id = parseToInt(array[0]);
        this.nome = array[1].isEmpty() ? "nao informado" : array[1];
        this.altura = parseToInt(array[2]);
        this.peso = parseToInt(array[3]);
        this.universidade = array[4].isEmpty() ? "nao informado" : array[4];
        this.anoNascimento = parseToInt(array[5]);
        this.cidadeNascimento = array[6].isEmpty() ? "nao informado" : array[6];
        this.estadoNascimento = array[7].isEmpty() ? "nao informado" : array[7];
    }

    private int parseToInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1; // Valor padrão em caso de falha na conversão
        }
    }
}

public class mergesort {
    public static void main(String[] args) throws IOException {
        Jogador[] tempPlayers = new Jogador[3923];
        Instant start = Instant.now();
        int comparacoes = 0;
        int movimentacoes = 0;
        int id;
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        LinkedList<Jogador> players = new LinkedList<>();

        methodToTime();

        BufferedReader br = new BufferedReader(new FileReader("/tmp/players.csv"));
        String str = br.readLine();

        for (int i = 0; i < 3922; i++) {
            str = br.readLine();

            tempPlayers[i] = new Jogador();
            tempPlayers[i].ler(str);
        }
        br.close();

        String entrada = sc.readLine();
        while (!entrada.equals("FIM")) {
            id = Integer.parseInt(entrada);

            boolean found = false;
            int i = 0;
            while (found == false) {
                if (tempPlayers[i].getId() == id) {
                    players.add(tempPlayers[i]);
                    found = true;
                }
                i++;

            }

            // searchId(id, players);
            entrada = sc.readLine();
        }

        int[] temp = mergeSortByUniversidade(players);

        movimentacoes += temp[0];
        comparacoes += temp[1];

        for (Jogador player : players) {
            System.out.println("[" + player.getId() + " ## " + player.getNome() + " ## " + player.getAltura() + " ## "
                    + player.getPeso() + " ## " + player.getAnoNascimento() + " ## " + player.getUniversidade() + " ## "
                    + player.getCidadeNascimento() + " ## " + player.getEstadoNascimento() + "]");
        }

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis(); // em milissegundos

        wInfo(1445217, timeElapsed, comparacoes, movimentacoes);
    }

    public static void wInfo(int Matricula, float time, int comparacoes, int movimentacoes) {
        try (FileWriter myWriter = new FileWriter("/tmp/1445217_insercao.txt")) {
            myWriter.write(Matricula + "\t " + time + "ms\t " + "\t" + comparacoes + "\t " + movimentacoes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void swap(LinkedList<Jogador> jogadores, int i, int minIndex) {
        Jogador temp = jogadores.get(minIndex);
        jogadores.set(minIndex, jogadores.get(i));
        jogadores.set(i, temp);
    }

    public static int[] mergeSortByUniversidade(LinkedList<Jogador> jogadores) {
        int[] temp = new int[2];
        if (jogadores.size() > 1) {
            int mid = jogadores.size() / 2;
            LinkedList<Jogador> left = new LinkedList<>();
            LinkedList<Jogador> right = new LinkedList<>();
    
            for (int i = 0; i < mid; i++) {
                left.add(jogadores.get(i));
            }
    
            for (int i = mid; i < jogadores.size(); i++) {
                right.add(jogadores.get(i));
            }
    
            temp = mergeSortByUniversidade(left);
            temp = mergeSortByUniversidade(right);
    
            int i = 0;
            int j = 0;
            int k = 0;
    
            while (i < left.size() && j < right.size()) {
                temp[1]++;
                if (left.get(i).getUniversidade().compareTo(right.get(j).getUniversidade()) < 0 ||
                    (left.get(i).getUniversidade().equals(right.get(j).getUniversidade()) &&
                     left.get(i).getNome().compareTo(right.get(j).getNome()) < 0)) {
                    jogadores.set(k, left.get(i));
                    i++;
                } else {
                    jogadores.set(k, right.get(j));
                    j++;
                }
                k++;
            }
    
            while (i < left.size()) {
                jogadores.set(k, left.get(i));
                i++;
                k++;
            }
    
            while (j < right.size()) {
                jogadores.set(k, right.get(j));
                j++;
                k++;
            }
        }
        return temp;
    }
    
    
    private static void methodToTime() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
