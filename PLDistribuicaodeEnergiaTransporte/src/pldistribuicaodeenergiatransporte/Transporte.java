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
public class Transporte {

    ArrayList<ArrayList<Integer>> matrizPrincipal;
    ArrayList<ArrayList<Integer>> matrizFinal;
    ArrayList<Integer> necessidade;
    ArrayList<Oferta> listaOferta;
    ArrayList<Empresa> listaEmpresa;
    ArrayList<Integer> disponibilidade;
    ArrayList<Integer> penalidadeLinha;
    ArrayList<Integer> penalidadeColuna;
    ArrayList<Custo> listaVariaveisCusto;

    public Transporte() {
        matrizPrincipal = new ArrayList<ArrayList<Integer>>();
        matrizFinal = new ArrayList<ArrayList<Integer>>();
    }

    public void setListaVariaveisCusto(ArrayList<Custo> listaVariaveisCusto) {
        this.listaVariaveisCusto = listaVariaveisCusto;
    }

    public void setListaOferta(ArrayList<Oferta> listaOferta) {
        this.listaOferta = listaOferta;
    }

    public void setListaEmpresa(ArrayList<Empresa> listaEmpresa) {
        this.listaEmpresa = listaEmpresa;
    }

    public void popularMatrizPrincipal() {
        ArrayList<Integer> linha;

        //construir e popular
        //O número de linhas será o número de ofertantes
        //O número de colunas será o número de empresas
        for (int i = 1; i <= this.listaOferta.size(); i++) { //percorre linha
            linha = new ArrayList<Integer>(); //cria uma nova linha para a matriz

            for (int j = 1; j <= this.listaEmpresa.size(); j++) { //percorre coluna

                int posicao = Custo.buscaPosicaoCusto(i, j, this.listaVariaveisCusto);

                linha.add(this.listaVariaveisCusto.get(posicao).valor);  //coloca os custos nas linhas

            }

            this.matrizPrincipal.add(linha);
        }

    }

    public void popularMatrizFinal() {
        ArrayList<Integer> linha;

        //construir e popular
        //O número de linhas será o número de ofertantes
        //O número de colunas será o número de empresas
        for (int i = 1; i <= this.listaOferta.size(); i++) { //percorre linha
            linha = new ArrayList<Integer>();

            for (int j = 1; j <= this.listaEmpresa.size(); j++) { //percorre coluna                

                linha.add(0);

            }

            this.matrizFinal.add(linha);
        }
    }

    public void setListaNecessidade() {
        ArrayList<Integer> lista = new ArrayList<Integer>();

        for (int i = 0; i < this.listaEmpresa.size(); i++) {
            lista.add(this.listaEmpresa.get(i).valor);
        }

        this.necessidade = lista;
    }

    public void setListaDisponibilidade() {
        ArrayList<Integer> lista = new ArrayList<Integer>();

        for (int i = 0; i < this.listaOferta.size(); i++) {
            lista.add(this.listaOferta.get(i).valor);
        }

        this.disponibilidade = lista;
    }

    public boolean podePenalidadeLinha() {
        int contador = 0;

        //Se houver mais de uma disponibilidade, pode fazer a penalidade
        for (int i = 0; i < this.disponibilidade.size(); i++) {
            if (this.disponibilidade.get(i) != 0) {
                contador++;
            }
        }

        if (contador >= 2) {
            return true;
        } else {
            return false;
        }
    }

    public boolean podePenalidadeColuna() {
        int contador = 0;

        //Se houver mais de uma necessidade, pode fazer a penalidade
        for (int i = 0; i < this.necessidade.size(); i++) {
            if (this.necessidade.get(i) != 0) {
                contador++;
            }
        }

        if (contador >= 2) {
            return true;
        } else {
            return false;
        }
    }

    public void calculoPenalidadeLinha() {
        //Procura os dois menores valores
        int primeiroMenor = 999999;
        int segundoMenor = 999999;
        int posicaoPrimeiro = -1;
        ArrayList<Integer> lista = new ArrayList<Integer>();
        int penalidade;

        for (int k = 0; k < this.matrizPrincipal.size(); k++) { //percorre linhas para achar os menores

            //Verifica se aquela linha foi esgotada
            if (this.disponibilidade.get(k) == 0) {
                penalidade = 0;
                lista.add(penalidade);
            } else {

                //Achando o primeiro menor
                for (int i = 0; i < this.matrizPrincipal.get(0).size(); i++) { //percorre coluna

                    if (this.matrizPrincipal.get(k).get(i) >= 0 && this.matrizPrincipal.get(k).get(i) < primeiroMenor) {
                        primeiroMenor = this.matrizPrincipal.get(k).get(i);         //Se for positivo e menor que o menor
                        posicaoPrimeiro = i;
                    }

                }

                //Achando o segundo menor
                for (int i = 0; i < this.matrizPrincipal.get(0).size(); i++) { //percorre coluna

                    if (this.matrizPrincipal.get(k).get(i) >= 0 && this.matrizPrincipal.get(k).get(i) < segundoMenor && i != posicaoPrimeiro) {
                        segundoMenor = this.matrizPrincipal.get(k).get(i);         //Se for positivo e menor que o menor e diferente do primeiro
                    }
                }

                penalidade = segundoMenor - primeiroMenor;
                lista.add(penalidade);
                primeiroMenor = 999999;
                segundoMenor = 999999;
            }

        }

        this.penalidadeLinha = lista;
    }

    public void calculoPenalidadeColuna() {
        //Procura os dois menores valores
        int primeiroMenor = 999999;
        int segundoMenor = 999999;
        int posicaoPrimeiro = -1;
        ArrayList<Integer> lista = new ArrayList<Integer>();
        int penalidade;

        for (int k = 0; k < this.matrizPrincipal.get(0).size(); k++) { //percorre colunas para achar os menores

            //Verifica se aquela coluna foi esgotada
            if (this.necessidade.get(k) == 0) {
                penalidade = 0;
                lista.add(penalidade);
            } else {

                //Achando o primeiro menor
                for (int i = 0; i < this.matrizPrincipal.size(); i++) { //percorre linhas

                    if (this.matrizPrincipal.get(i).get(k) >= 0 && this.matrizPrincipal.get(i).get(k) < primeiroMenor) {
                        primeiroMenor = this.matrizPrincipal.get(i).get(k);         //Se for positivo e menor que o menor
                        posicaoPrimeiro = i;
                    }

                }

                //Achando o segundo menor
                for (int i = 0; i < this.matrizPrincipal.size(); i++) { //percorre coluna

                    if (this.matrizPrincipal.get(i).get(k) >= 0 && this.matrizPrincipal.get(i).get(k) < segundoMenor && i != posicaoPrimeiro) {
                        segundoMenor = this.matrizPrincipal.get(i).get(k);         //Se for positivo e menor que o menor e diferente do primeiro
                    }
                }

                penalidade = segundoMenor - primeiroMenor;
                lista.add(penalidade);
                primeiroMenor = 999999;
                segundoMenor = 999999;
            }

        }

        this.penalidadeColuna = lista;

    }

    public int maiorPenalidade() {
        int maior = -1;

        //Escolher a maior penalidade comparando as duas listas
        for (int i = 0; i < this.penalidadeLinha.size(); i++) {
            if (this.penalidadeLinha.get(i) > maior) {
                maior = this.penalidadeLinha.get(i);
            }
        }

        for (int i = 0; i < this.penalidadeColuna.size(); i++) {
            if (this.penalidadeColuna.get(i) > maior) {
                maior = this.penalidadeColuna.get(i);
            }
        }

        return maior;

    }

    public boolean solucaoOtima() {
        //A solução só será ótima se não houver mais necessidade ou disponibilidade
        int contador = 0;

        for (int i = 0; i < this.necessidade.size(); i++) {
            if (this.necessidade.get(i) != 0) {
                contador++;
            }
        }

        for (int i = 0; i < this.disponibilidade.size(); i++) {
            if (this.disponibilidade.get(i) != 0) {
                contador++;
            }
        }

        if (contador != 0) {
            return false;
        }

        return true;
    }

    public void algoritmo() {
        //Calcular penalidade
        if (podePenalidadeLinha()) {
            calculoPenalidadeLinha();
        }

        if (podePenalidadeColuna()) {
            calculoPenalidadeColuna();
        }

        int posicaoCelulaLinha = -1;
        int posicaoCelulaColuna = -1;
        int posicaoPenalidadeLinha = -1;
        int posicaoPenalidadeColuna = -1;

        //Selecionar o melhor caminho escolhendo a celula menor
        if (!podePenalidadeColuna() && !podePenalidadeLinha()) { //Se não puder calcular nenhuma penalidade, é porque só tem uma opção
            //Verificar quem está faltando receber
            //Pegar posicao da penalidade

            for (int i = 0; i < this.necessidade.size(); i++) {
                if (this.necessidade.get(i) != 0) {
                    posicaoPenalidadeColuna = i;
                    break;
                }
            }

            for (int i = 0; i < this.disponibilidade.size(); i++) {
                if (this.disponibilidade.get(i) != 0) {
                    posicaoPenalidadeLinha = i;
                    break;
                }
            }

            //Achar posição da celula
            posicaoCelulaColuna = posicaoPenalidadeColuna;
            posicaoCelulaLinha = posicaoPenalidadeLinha;

        } else {
            //Maior penalidade
            int maior = maiorPenalidade();

            boolean isPenalidadeLinha = true;

            //Achar qual linha ou coluna pertence a maior penalidade
            for (int i = 0; i < this.penalidadeColuna.size(); i++) {
                if (this.penalidadeColuna.get(i) == maior) {
                    isPenalidadeLinha = false;
                }
            }

            for (int i = 0; i < this.penalidadeLinha.size(); i++) {
                if (this.penalidadeLinha.get(i) == maior) {
                    isPenalidadeLinha = true;
                }
            }

            //Pegar posicao da celula
            //Se for de linha
            if (isPenalidadeLinha) {
                //Achar a posicao da linha /Onde está posicionado o maior?
                //->A posição na lista de penalidade linha será a posição da linha da matriz

                for (int i = 0; i < this.penalidadeLinha.size(); i++) {//Procura maior na lista penalidade linha
                    if (this.penalidadeLinha.get(i) == maior) {
                        posicaoPenalidadeLinha = i;
                        break;
                    }
                }

                //Percorrer a linha para achar a menor celula
                //Pegar posicao da celula
                int menorCelulaLinha = 999999;

                for (int i = 0; i < this.matrizPrincipal.get(posicaoPenalidadeLinha).size(); i++) {//percorre por coluna
                    if (this.matrizPrincipal.get(posicaoPenalidadeLinha).get(i) < menorCelulaLinha) {
                        menorCelulaLinha = this.matrizPrincipal.get(posicaoPenalidadeLinha).get(i);
                        posicaoCelulaColuna = i;
                    }
                }
                posicaoCelulaLinha = posicaoPenalidadeLinha;

            } else {
                //Achar a posição da coluna
                for (int i = 0; i < this.penalidadeColuna.size(); i++) {//Procura maior na lista penalidade coluna
                    if (this.penalidadeColuna.get(i) == maior) {
                        posicaoPenalidadeColuna = i;
                        break;
                    }
                }

                //Percorrer a coluna para achar a menor celula
                //Pegar posicao da celula
                int menorCelulaColuna = 999999;

                for (int i = 0; i < this.matrizPrincipal.size(); i++) {//percorre por linha
                    if (this.matrizPrincipal.get(i).get(posicaoPenalidadeColuna) < menorCelulaColuna) {
                        menorCelulaColuna = this.matrizPrincipal.get(i).get(posicaoPenalidadeColuna);
                        posicaoCelulaLinha = i;
                    }
                }
                posicaoCelulaColuna = posicaoPenalidadeColuna;

            }

        }
        //----Achada a posição da menor celula

        //Pegar a disponibilidade e necessidade correspondente nas posições da celula
        //Usar a posicao da celula
        int disp = this.disponibilidade.get(posicaoCelulaLinha);
        int nec = this.necessidade.get(posicaoCelulaColuna);

        //Comparar quais dos dois é o menor
        if (disp < nec) {
            //Na matriz principal, se o menor valor for de disponibilidade
            //Disponibilidade (valor na posição correspondente) vai ficar 0
            this.disponibilidade.set(posicaoCelulaLinha, 0);

            //Necessidade (valor na posição correspondente) vai ser necessidade - disponibilidade
            this.necessidade.set(posicaoCelulaColuna, nec - disp);

            //Esgotamento da linha (Inserir naquela linha toda 999999)
            esgotamentoLinha(posicaoCelulaLinha);

            //Setar na matriz final, na posição da celula, o menor valor entre necessidade e disponibilidade
            modificaMatrizFinal(disp, posicaoCelulaLinha, posicaoCelulaColuna);
        }

        if (nec < disp) {
            //Na matriz principal, se o menor valor for de necessidade
            //Necessidade (valor na posição correspondente) vai ficar 0
            this.necessidade.set(posicaoCelulaColuna, 0);

            //Disponibilidade (valor na posição correspondente) vai ser disponibilidade - necessidade
            this.disponibilidade.set(posicaoCelulaLinha, disp - nec);

            //Esgotamento da coluna (Inserir naquela coluna toda 999999)
            esgotamentoColuna(posicaoCelulaColuna);

            //Setar na matriz final, na posição da celula, o menor valor entre necessidade e disponibilidade
            modificaMatrizFinal(nec, posicaoCelulaLinha, posicaoCelulaColuna);
        }

        if (nec == disp) {
            //Na matriz principal, se os valores forem iguais
            //Necessidade e Disponibilidade vai ficar 0
            this.disponibilidade.set(posicaoCelulaLinha, 0);
            this.necessidade.set(posicaoCelulaColuna, 0);

            //Esgotamento da coluna e da linha(Inserir 999999)
            esgotamentoLinha(posicaoCelulaLinha);
            esgotamentoColuna(posicaoCelulaColuna);

            //Setar na matriz final, na posição da celula, o menor valor entre necessidade e disponibilidade
            modificaMatrizFinal(nec, posicaoCelulaLinha, posicaoCelulaColuna);
        }

    }

    public void esgotamentoLinha(int posicaoLinha) {
        //Esgotamento da linha (Inserir naquela linha toda 999999)
        for (int i = 0; i < this.matrizPrincipal.get(posicaoLinha).size(); i++) { //percorre coluna
            this.matrizPrincipal.get(posicaoLinha).set(i, 999999);
        }
    }

    public void esgotamentoColuna(int posicaoColuna) {
        //Esgotamento da coluna (Inserir naquela coluna toda 999999)
        for (int i = 0; i < this.matrizPrincipal.size(); i++) { //percorre linhas
            this.matrizPrincipal.get(i).set(posicaoColuna, 999999);
        }
    }

    public void modificaMatrizFinal(int valor, int posicaoLinha, int posicaoColuna) {
        //Setar na matriz final, na posição da celula, o menor valor entre necessidade e disponibilidade
        this.matrizFinal.get(posicaoLinha).set(posicaoColuna, valor);

    }

    public void resultado() {
        //print da matriz final
        System.out.println("Matriz Final");
        for (int i = 0; i < this.matrizFinal.size(); i++) {
            System.out.println(this.matrizFinal.get(i));
        }

        //Empresa que não conseguiu energia
        //Procura na matriz final, na última linha (posicao do dummy), a celula que é diferente de 0
        //A coluna dessa célula é a empresa
        int ultimaLinha = this.matrizFinal.size() - 1;
        int posicao = -1;

        for (int i = 0; i < this.matrizFinal.get(0).size(); i++) { //percorre coluna
            if (this.matrizFinal.get(ultimaLinha).get(i) != 0) {
                posicao = i;
            }
        }

        System.out.println("Empresa " + this.listaEmpresa.get(posicao).id);
    }
}
