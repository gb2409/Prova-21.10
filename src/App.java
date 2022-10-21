import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws InterruptedException, IOException {
        int opcao;
        Scanner in = new Scanner(System.in);
        List <Produto> produtos = new ArrayList<>();
        List <Venda> vendas = new ArrayList<>();
        //produtos.add(new Produto(1, "Batata", 2.00, 7));
    
        
        do {

            System.out.println("\n----\nMENU\n----\n");
            System.out.println("1| - Incluir produto");
            System.out.println("2| - Consultar produto");
            System.out.println("3| - Listagem de produtos");
            System.out.println("4| - Relatorio de vendas detalhadas");
            System.out.println("5| - Realizar venda");
            System.out.println("0| - Sair");
            System.out.println("Selecione uma opção: ");

            opcao = in.nextInt();
            in.nextLine();

            if (opcao == 1) {

                System.out.println("Nome do produto: ");
                    String nome = in.nextLine();
                    System.out.println("Codigo do produto: ");
                    int codigo = in.nextInt();
                    System.out.println("Valor unitario: Ex: ( 2,50 | 1,00 )");
                    Double valor = in.nextDouble();
                    System.out.println("Quantidade armazenada no estoque: ");
                    int Quantidade = in.nextInt();
                    
                    for (int i = 0; i < produtos.size(); i++) {
                        if (produtos.get(i).getCodigo() == codigo) {
                            produtos.remove(i);
                        } 
                    }
                        produtos.add(new Produto(codigo, nome, valor, Quantidade));

                    in.nextLine();

                    voltarMenu(in);
            } else if (opcao == 2) {

                if (produtos.isEmpty()){
                    System.out.println("Não existem produtos cadastrados no sistema. ");
                } else {

                System.out.println("Digite o codigo do produto a ser buscado: ");
                int codigo = in.nextInt();

                for (int i = 0; i < produtos.size(); i++) {
                    if (produtos.get(i).getCodigo() == codigo) {
                        System.out.printf("\n---------------\nProduto: %s \nCodigo: %d \nValor: %f \nQuantidade no estoque: %d \n---------------", produtos.get(i).getNome(), produtos.get(i).getCodigo(), produtos.get(i).getValor(), produtos.get(i).getQuantidade());
                    } 
                }
            }
                in.nextLine();

                voltarMenu(in);
            } else if (opcao == 3) {

                Double maior = Double.MIN_VALUE, medio = 0.0, menor = Double.MAX_VALUE;

                for (int i = 0; i < produtos.size(); i++) {
                
                    if (produtos.get(i).getValor() > maior) {
                        maior = produtos.get(i).getValor();
                    }
                    
                    if (produtos.get(i).getValor() < menor) {
                        menor = produtos.get(i).getValor();
                    }

                    medio = produtos.stream().collect(Collectors.averagingDouble(Produto::getValor));

                }
                
                ComparadorPreco comparador = new ComparadorPreco(maior, medio, menor);

                if (produtos.isEmpty()){
                    System.out.println("Não existem produtos cadastrados no sistema.");
                } else {


                System.out.println("\nProdutos:");
                for (Produto p: produtos){
                    System.out.printf("\n---------------\nProduto: %s \nCodigo: %d \nValor unitario: %f \nQuantidade no estoque: %d\n--------------- ", p.getNome(), p.getCodigo(), p.getValor(), p.getQuantidade()); 
                    
                }

                System.out.print(comparador.toString());
            }
                voltarMenu(in);
            }
            else if (opcao == 4) {
                if (vendas.isEmpty()) {
                    System.out.println("\nNão existe nenhum produto cadastrado!");
                } else {
                
                    Double maior = Double.MIN_VALUE, medio = 0.0, menor = Double.MAX_VALUE;
                    for (int i = 0; i < vendas.size(); i++) {
                
                        if (vendas.get(i).getValor() > maior) {
                            maior = vendas.get(i).getValor();
                        }
                        
                        if (vendas.get(i).getValor() < menor) {
                            menor = vendas.get(i).getValor();
                        }
    
                        medio = vendas.stream().collect(Collectors.averagingDouble(Venda::getValor));
    
                    }
                    
                    ComparadorPreco comparador = new ComparadorPreco(maior, medio, menor);

                    vendas.sort(new ComparadorData());
                    String dia = vendas.get(0).getData();
                    String mes = vendas.get(vendas.size() -1).getData();
                    System.out.printf("\nVendas || %s - %s:" , dia, mes);
                    
                    for (Venda p : vendas){
                        for (int i=0; i < produtos.size(); i++) {
                            System.out.printf("\n--------------------\nData: %s\nProduto: %s - %d\nQuantidade obtida no estoque: %d\nValor unitario: %s\nValor Total: %s\n-------------------- ", p.getData(), produtos.get(i).getNome(), produtos.get(i).getCodigo(),produtos.get(i).getQuantidade(), produtos.get(i).getValor(), p.getValorTotal());
                        }
                    }
                            System.out.print(comparador.toString());
                }
                    
                         voltarMenu(in);   
            } 

                  else if (opcao == 5) {

                boolean verificar = false;

                if (produtos.isEmpty()){
                    System.out.println("Não existe nenhum produto cadastrado no sistema para efetuar a venda.");
                
            voltarMenu(in);
                } else {

                    System.out.println("\nInforme a data da venda.");
                    String data = in.nextLine();
                    Venda venda = new Venda(data, null, null, null, null);
                    
                    while (venda.getData() == "ERRO") {
                        System.out.println("Pressione enter para reiniciar");

                        in.nextLine();
                     System.out.println("\nInforme a data da venda.");
                     data = in.nextLine();
                     venda = new Venda(data, null, null, null, null);
                    }
                    System.out.println("informe o codigo do produto: ");
                    int produto = in.nextInt();
                    System.out.println("informe a quantidade do produto: ");
                    int Quantidade = in.nextInt();

                    for (int i = 0; i < produtos.size(); i++) {
                        if (produtos.get(i).getCodigo() == produto) {
                            verificar = true;
                            if (produtos.get(i).getQuantidade() < Quantidade || Quantidade == 0){
                                System.out.println("Quantidade não possuida no estoque! ");
                            } else{
                                produtos.get(i).setQuantidade(produtos.get(i).getQuantidade()- Quantidade);
                                Double valor = Quantidade * produtos.get(i).getValor();
                                vendas.add(new Venda(data, valor, valor, valor, valor));
                                
                                System.out.println("Venda concluida!");
                                
                            }
                        }
                  
                    }
                }
                if (verificar == false){
                    System.out.println("Não existe nenhum produto cadastrado com este codigo!");
               
                    in.nextLine();
               voltarMenu(in);

                }

              }
            else if (opcao != 0) {  
                System.out.println("\nOpção inválida!");
            }
        } while (opcao != 0);

        System.out.println("Fim do programa!");

        in.close();
    }

    private static void voltarMenu(Scanner in) throws InterruptedException, IOException {
        System.out.println("\nPressione ENTER para voltar ao menu.");
        in.nextLine();

        
        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            System.out.print("\033[H\033[2J");
        
        System.out.flush();
    }
}       //gbzada