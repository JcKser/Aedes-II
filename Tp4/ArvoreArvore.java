import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//-----------------------CLASSE JOGADOR---------------------------//
class Jogador {

    // instâncias
    private int id;
    private String nome;
    private int altura;
    private int peso;
    private String universidade;
    private int anoNascimento;
    private String cidadeNascimento;
    private String estadoNascimento;

    // construtores

    public Jogador(String pedido, File tabela) {
        try {
            Scanner scanner = new Scanner(tabela);

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine(); // lê a linha
                String[] elementos = linha.split(",", -1); // divide a linha pela vírgula e faz um array

                for (int i = 0; i < elementos.length; i++) {
                    if (elementos[i].isEmpty()) {
                        elementos[i] = "nao informado";
                    }
                }

                if (pedido.equals(elementos[0]) && elementos.length == 8) {
                    setId(Integer.parseInt(elementos[0]));
                    setNome(elementos[1]);
                    setAltura(Integer.parseInt(elementos[2]));
                    setPeso(Integer.parseInt(elementos[3]));
                    setUniversidade(elementos[4]);
                    setAnoNascimento(Integer.parseInt(elementos[5]));
                    setCidadeNascimento(elementos[6]);
                    setEstadoNascimento(elementos[7]);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("arquivo não encontrado");
        }
    }

    public Jogador(String nome) {
        this.nome = nome;
        this.altura = 0;
        this.anoNascimento = 0;
        this.cidadeNascimento = "0";
        this.estadoNascimento = "0";
        this.id = 0;
        this.peso = 0;
        this.universidade = "0";
    }

    // funções set

    public void setId(int id) {
        this.id = id;
    }

    private void setNome(String nome) {
        this.nome = nome;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public void setUniversidade(String universidade) {
        this.universidade = universidade;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public void setCidadeNascimento(String cidadeNascimento) {
        this.cidadeNascimento = cidadeNascimento;
    }

    public void setEstadoNascimento(String estadoNascimento) {
        this.estadoNascimento = estadoNascimento;
    }

    // funções gets

    public int getId() {
        return this.id;
    }

    public int getAltura() {
        return this.altura;
    }

    public String getNome() {
        return this.nome;
    }

    public int getPeso() {
        return this.peso;
    }

    public String getUniversidade() {
        return this.universidade;
    }

    public int getAnoNascimento() {
        return this.anoNascimento;
    }

    public String getCidadeNascimento() {
        return this.cidadeNascimento;
    }

    public String getEstadoNascimento() {
        return this.estadoNascimento;
    }

    // funções normais

    public String dados() {
        return (
                " ## " +
                        getNome() +
                        " ## " +
                        getAltura() +
                        " ## " +
                        getPeso() +
                        " ## " +
                        getAnoNascimento() +
                        " ## " +
                        getUniversidade() +
                        " ## " +
                        getCidadeNascimento() +
                        " ## " +
                        getEstadoNascimento() +
                        " ##");
    }

    public int getMod() {
        return (this.altura % 15);
    }
}

// -----------------------CLASSE NO---------------------------//
class No {
    int elemento;
    No esq, dir;
    No2 outro;

    public No(int elemento) {
        this(elemento, null, null, null);
    }

    public No(int elemento, No esq, No dir, No2 outro) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
        this.outro = outro;
    }
}

class No2 {
    Jogador elemento;
    No2 esq, dir;

    public No2(Jogador elemento) {
        this(elemento, null, null);
    }

    public No2(Jogador elemento, No2 esq, No2 dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

// -----------------------CLASSE ArvoreBinaria---------------------------//
class ArvoreBi {

    No raiz;

    public ArvoreBi() throws Exception {
        raiz = null;
        this.inserirMod(7);
        this.inserirMod(3);
        this.inserirMod(11);
        this.inserirMod(1);
        this.inserirMod(5);
        this.inserirMod(9);
        this.inserirMod(12);
        this.inserirMod(0);
        this.inserirMod(2);
        this.inserirMod(4);
        this.inserirMod(6);
        this.inserirMod(8);
        this.inserirMod(10);
        this.inserirMod(13);
        this.inserirMod(14);
    }

    // INSERIRMod mod
    public void inserirMod(int elemento) throws Exception {
        raiz = inserirMod(elemento, raiz);
    }

    private No inserirMod(int elemento, No i) throws Exception {
        if (i == null) {
            i = new No(elemento);
        } else if (elemento < i.elemento) {
            i.esq = inserirMod(elemento, i.esq);
        } else if (elemento > i.elemento) {
            i.dir = inserirMod(elemento, i.dir);
        } else {
            throw new Exception("Erro ao inserirMod MOD");
        }
        return i;
    }

    // INSERIR jogador mod

    public void inserir(Jogador jogador) throws Exception {
        raiz = inserir(jogador, raiz);
    }

    private No inserir(Jogador jogador, No i) throws Exception {

        if (i == null) {
            throw new Exception("Erro ao inserir: JOGADOR inválido!");

        } else if (jogador.getMod() < i.elemento) {
            i.esq = inserir(jogador, i.esq);
        } else if (jogador.getMod() > i.elemento) {
            i.dir = inserir(jogador, i.dir);
        } else {
            i.outro = inserir(jogador, i.outro);
        }
        return i;
    }

    // INSERIR jogador

    private No2 inserir(Jogador jogador, No2 i) throws Exception {

        if (i == null) {
            i = new No2(jogador);
        } else if (jogador.getNome().compareTo(i.elemento.getNome()) < 0) {
            i.esq = inserir(jogador, i.esq);
        } else if (jogador.getNome().compareTo(i.elemento.getNome()) > 0) {
            i.dir = inserir(jogador, i.dir);
        } else {
            throw new Exception("Erro ao inserir");
        }

        return i;
    }

    // PESQUISAR
    public boolean mostrar(String nome) throws Exception {

        System.out.print(nome + " raiz");

        return mostrar(nome, raiz);
    }

    private boolean mostrar(String nome, No i) {
        boolean resp = false;

        if (i != null) {
            if (!resp) {
                resp = mostrarSub(nome, i.outro);
            }
            if (!resp) {
                System.out.print(" esq");
                resp = mostrar(nome, i.esq);
            }
            if (!resp) {
                System.out.print(" dir");
                resp = mostrar(nome, i.dir);
            }

        }

        return resp;
    }

    private boolean mostrarSub(String nome, No2 i) {
        boolean resp = false;

        if (i != null) {

            if (nome.equals(i.elemento.getNome())) {
                resp = true;
            } else {
                System.out.print(" ESQ");
                resp = mostrarSub(nome, i.esq);

                if (!resp) {
                    System.out.print(" DIR");
                    resp = mostrarSub(nome, i.dir);
                }
            }

        }

        return resp;
    }

}

// -------------------Main---------------------------//
public class ArvoreArvore {

    public static void main(String[] args) throws Exception {

        // variáveis e objetos
        Scanner scanner = new Scanner(System.in);
        File arquivo = new File("/tmp/players.csv");
        ArvoreBi arvore = new ArvoreBi();

        /* Leitura do jogador e inserção na Árvore */
        String pedido = scanner.nextLine();
        while (!pedido.equalsIgnoreCase("FIM")) {
            Jogador jogador = new Jogador(pedido, arquivo);
            arvore.inserir(jogador);
            pedido = scanner.nextLine();
        }

        pedido = scanner.nextLine();
        while (!pedido.equalsIgnoreCase("FIM")) {
            Jogador jogador = new Jogador(pedido);
            if (arvore.mostrar(jogador.getNome())) {
                System.out.println(" SIM");
            } else {
                System.out.println(" NAO");
            }

            pedido = scanner.nextLine();
        }

        scanner.close();
    }
}
