/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pldistribuicaodeenergiatransporte;

import java.util.ArrayList;

/**
 *
 * @author Débora
 */
public class PLDistribuicaodeEnergiaTransporte {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //-----------------------
        //Adicionando os dados

        //adicionar empresas
        ArrayList<Empresa> listaEmpresa = Empresa.adicionarDadosEmpresa();

        //adicionar ofertas
        ArrayList<Oferta> listaOferta = Oferta.adicionarDadosOferta();

        //adicionar custos
        ArrayList<Custo> listaVariaveisCusto = Custo.criarVariaveisCusto(listaEmpresa, listaOferta);
            
        //criar matriz
        Transporte t = new Transporte();
        
        t.setListaOferta(listaOferta);
        t.setListaEmpresa(listaEmpresa);
        t.setListaVariaveisCusto(listaVariaveisCusto);
        
        //Popular matriz principal
        t.popularMatrizPrincipal();
        
        //mostrarMatrizPrincipal
        System.out.println("Matriz Principal");
        for (int i = 0; i < t.matrizPrincipal.size(); i++) {
            System.out.println(t.matrizPrincipal.get(i));
        }
        
        //popular matrizFinal
        t.popularMatrizFinal();
        
        //mostrar matrizFinal
        System.out.println("Matriz Final");
        for (int i = 0; i < t.matrizFinal.size(); i++) {
            System.out.println(t.matrizFinal.get(i));
        }
        
        //criar lista de necessidade
        t.setListaNecessidade();
        
        //mostrar lista de necessidade
        System.out.println("Necessidade");
        System.out.println(t.necessidade);
        
        //criar lista de disponibilidade
        t.setListaDisponibilidade();
        
        //mostrar lista de necessidade
        System.out.println("Disponibilidade");
        System.out.println(t.disponibilidade);
        
//        t.calculoPenalidadeLinha();
//        t.calculoPenalidadeColuna();
//        
//        System.out.println("Penalidade Linha");
//        System.out.println(t.penalidadeLinha);
//        System.out.println("Penalidade Coluna");
//        System.out.println(t.penalidadeColuna);
        
        while(!t.solucaoOtima()){
            t.algoritmo();
        }
        
        t.resultado();
        
    }
    
}
