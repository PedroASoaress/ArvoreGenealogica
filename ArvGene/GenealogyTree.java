import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;


    /*
     * Escreva uma aplicação que permita:
       1- ler os dados de uma família a partir de um arquivo texto. O arquivo contém a seguinte estrutura:
        1.1-a primeira linha contém o nome da pessoa que é a raiz da árvore. Este primeiro elemento da lista contem pai "null", 
            que é o patriarca, ou matriarca, da família;
        1.2-as demais linhas contém o nome do pai/mãe, seguida pela sua lista de filhos. 
            Os nomes são separados por "|" (por simplicidade, assumir que não há duas pessoas com o mesmo nome na família. 
       2- listar a ascendência de uma pessoa (pai, avô, bisavô, etc) 
       3- contar o número de descendentes de uma pessoa OK
       4- listar os filhos de uma pessoa OK
       5- listar os netos de uma pessoa
       6- saber quem são os tios de uma pessoa
       7- saber quem são os primos de uma pessoa
       8- mostrar uma estrutura hierárquica contendo os dados da família, a partir de um nome fornecido
     */

/**
 *
 * @author 10032603
 */
public class GenealogyTree {
    private Node raiz;
    //adicionar nElementos pra ficar mais facil contagens
 
    

    //obs: foi necessário remover o static da classe Node
    //primeiro passo, criar a classe nodo com seus atributos Elem(elementos, está em String pois contém o nome de pessoas), o nodo pai, e a list
    //node contendo os filhos
    
    private class Node {
        String Elem;
        Node Pai;
        List<Node> Filhos;
        
        //inicialização do nodo, setando o elemento como valor pois não podemos pegar ou mexer diretamente com um nodo e seu elemento porque
        //alteraria a árvore, assim utilizamos um valor como referência
        public Node(String  valor) {
            Elem = valor;
            Pai = null;
            Filhos = new ArrayList<>();
        }
    }
    
      public GenealogyTree(String elem) {
          raiz = new Node(elem);
      }  
      
      //insere novo nó na árvore
      public void inserir(String elem, String paiStr) {
         Node pai = pesquisa(paiStr, raiz); 
         if (pai!=null) {
             Node novo = new Node(elem);
             novo.Pai = pai;
             pai.Filhos.add(novo);
         }
      }
      
      private Node pesquisa(String e, Node r) {
        //se o elemento do nodo referencia(Node r) for igual ao elemento (e) retorna referencia
          if (r.Elem.equals(e))
              return r;
          for (Node f : r.Filhos) {
              Node aux = pesquisa(e, f);
              if ( aux != null) return aux;
          }  
          return null;
      }
      
      public void preordem() {
          preordem(raiz);
      }
   
      private void preordem(Node r) {
          System.out.print(r.Elem+" - ");
          for (Node f : r.Filhos) {
              preordem(f);       
          }
      }
      
      public void largura() {
          Queue<Node> q = new LinkedList<>();
          
          q.add(raiz);
          while ( !q.isEmpty() ) {
              Node aux = q.remove();
              System.out.print(aux.Elem+" - ");
              for (Node f : aux.Filhos) {
                  q.add(f);       
              }
          }
      }


    //2- listar a ascendência de uma pessoa (pai, avô, bisavô, etc)
    public List<String> saberAscendencia(String Pessoa){
        Node ref = pesquisa(Pessoa, raiz);//encontra pessoa na árvore
        List<String> listarAscendencia = new ArrayList<>(); 
        if(ref==null){
            throw new IllegalArgumentException(" Essa pessoa não existe.");
        }
        //criar pilha
        //pilha é usada pois permite ir empilhando do mais antigo até o mais recente
        Stack<String> p = new Stack<>();
        Node atual = ref.Pai; 
        while(atual!=null){
                p.push(atual.Elem); //Adiciona na pilha
                atual = atual.Pai;
        }
        while(!p.isEmpty()){
            String elemento = p.pop();//pop é para remover
            if(elemento != null){
                listarAscendencia.add(elemento);//adiciona na lista
            }
        }
        return listarAscendencia;
     }

     // 3- contar o número de descendentes de uma pessoa
    public Integer pegarNumDescendentes(String Pessoa){
        Node ref = pesquisa(Pessoa, raiz);//pesquisa se a pessoa existe na arvore
            return quantidadeDescendentes(ref); 
    }

    //fazer método recursivo para contar a quantidade de descendentes
    private Integer quantidadeDescendentes(Node r){
        if(r==null){
            throw new IllegalArgumentException(" Essa pessoa não existe.");
        }
        int nElementos=0;
        for(Node f: r.Filhos){
            nElementos++;
            nElementos+=quantidadeDescendentes(f);
        }

        return nElementos;
    }
    //4- listar os filhos de uma pessoa
    public List<String> saberFilhos(String Pessoa){
        Node refPai = pesquisa(Pessoa, raiz);
        List<String> listarFilhos = new ArrayList<>();
        if(refPai == null){
            throw new IllegalArgumentException("Essa pessoa não existe.");
        }
        for(Node filho: refPai.Filhos){
            listarFilhos.add(filho.Elem);
        }
        return listarFilhos;
      }

    //5- listar os netos de uma pessoa
    public List<String> saberNetos(String Pessoa){
        Node refAvo = pesquisa(Pessoa, raiz);
        List<String> listarNetos = new ArrayList<>();
        if(refAvo==null){
            throw new IllegalArgumentException("Essa pessoa não existe.");
        }else{
        
            for(Node filho: refAvo.Filhos){
                for(Node neto: filho.Filhos){
                    listarNetos.add(neto.Elem);
                }
            }
        
        return listarNetos;
        }
      }


    //6- saber quem são os tios de uma pessoa
    public List<String> saberTios(String Pessoa){
        Node ref = pesquisa(Pessoa, raiz);
        List<String> listarTios = new ArrayList<>();
        if(ref == null){
            throw new IllegalArgumentException("Pessoa não existe. ");
        }
        Node avo = ref.Pai.Pai;
        if(ref != null && ref.Pai != null && avo!=null){
            for(Node tio: avo.Filhos){
                if(tio != ref.Pai){
                    listarTios.add(tio.Elem);
                }
            }
        }
        return listarTios;
    }
    
    //7- saber quem são os primos de uma pessoa
    public List<String> saberPrimos(String Pessoa){
        List<String> listarPrimos = new ArrayList<>();
        Node ref = pesquisa(Pessoa, raiz);
        if(ref == null){
            throw new IllegalArgumentException("Essa pessoa não existe.");
        }
        
        Node avo = ref.Pai.Pai;
        if(ref != null && ref.Pai !=null && avo!= null){
            for(Node tio: avo.Filhos){
                if(tio != ref.Pai){
                    for(Node primo : tio.Filhos){
                        listarPrimos.add(primo.Elem);
                    }
                }
            }
        }
      
        return listarPrimos;
    }
    //8- mostrar uma estrutura hierárquica contendo os dados da família, a partir de um nome fornecido
    public void mostrarEstruturaHierarquica(String Pessoa){
             Node ref = pesquisa(Pessoa, raiz);
             if(ref == null){
                System.out.println(Pessoa+ " Pessoa não existe na árvore genealógica.");
            }
            mostrarEstruturaHierarquica(ref,0," ");
        }
    private void mostrarEstruturaHierarquica(Node nodo, int nivel, String prefixo) {
        if(nodo == null){
                return;
         }
        //add a formatação de nível para os nós filhos
        String recuo = "";
        for(int i=0;i<nivel;i++){
                recuo+="  |";
            }
        //imprime nó atual com recuo
        System.out.println(recuo + nodo.Elem);
        
        //chama recursivamente para cada filho
        for(Node filho : nodo.Filhos){
            mostrarEstruturaHierarquica(filho, nivel + 1, recuo);
            }
        }
    }

