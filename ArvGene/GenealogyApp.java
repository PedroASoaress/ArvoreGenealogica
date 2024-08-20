
    
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author 10032603
 */
public class GenealogyApp {
    public static void main(String[] args) {
        GenealogyTree arvore = readTree("ArvoreTeste_UTF8.txt");
       
        // conjunto inicial de casos de teste
        // complete-o para os métodos não implementados
        
        System.out.println("\n---");
        System.out.println("\n2. Ascendentes de Júlia S01 A02");
        List<String> saberAscendencia = arvore.saberAscendencia("Julia S01 A02");
        for(String s: saberAscendencia){
            System.out.println(s+"  ");
        }
        System.out.println("\n---"); 
        System.out.println("\n---");
        
        System.out.println("\n3. Número de descendentes de Mauro Paulo S01: "+
        arvore.pegarNumDescendentes("Mauro Paulo S01"));
        System.out.println("\n---");


        System.out.println("\n4. Listar Filhos de Mauro Paulo S01 :");
        List<String> saberFilhos = arvore.saberFilhos("Mauro Paulo S01");
        for (String s : saberFilhos) {
            System.out.print(s+"  ");
        }
        System.out.println("\n---"); 
        System.out.println("\n---");


        System.out.println("\n5. Netos de Edmundo Antônio S01");
        List<String> saberNetos = arvore.saberNetos("Edmundo Antonio S01");
        for (String s : saberNetos) {
            System.out.print(s+"  ");
        }
        System.out.println("\n---");

        System.out.println("\n6. Listar Tios de Júlia S01 A02");
        List<String> saberTios = arvore.saberTios("Julia S01 A02");
        for(String s : saberTios){
            System.out.println(s+" ");
        }
        System.out.println("\n---");
        System.out.println("\n7. Primos de Julia S01 A02");
        List<String> saberPrimos = arvore.saberPrimos("Julia S01 A02");
        for (String s : saberPrimos) {
            System.out.print(s+"  ");
        }
        System.out.println("\n---");

        System.out.println("\nMostrar...");
        arvore.mostrarEstruturaHierarquica("AA");
        arvore.mostrarEstruturaHierarquica("Henrique Joao S01");
        
       
        

        
    }

    private static GenealogyTree readTree(String arq) {

        GenealogyTree arv = null;
        try {
            BufferedReader buff = new BufferedReader(new FileReader(arq));
            String line = null;
            try {
                line = buff.readLine();
                line = line.trim();
                arv = new GenealogyTree(line);
                line = buff.readLine();
               
                while (line != null) {
                    line = line.trim();
                    String[] pessoas = line.split(" \\| ");
                    for (int i = 1; i < pessoas.length; i++) {
                        arv.inserir(pessoas[i], pessoas[0]);
                    }
                    line = buff.readLine();
                    
                }
            } finally {
                buff.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arv;
    }

}   