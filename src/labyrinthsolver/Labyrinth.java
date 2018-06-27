/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthsolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Pedro H. Gomes
 */
public class Labyrinth {
    
    
    /*
        # - muros
        0 - caminho
        R - robo
    */
    
    private int size;
    private Coordinates start;
    private Coordinates end;
    private String fileName;
    private String[][] labyrinth;
    
    public Labyrinth(String fileName){
        this.fileName = fileName;
        this.build();
    } 
    
    public void show(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                System.out.print(this.labyrinth[i][j]);
            }
            System.out.println("");
        }
    }
    
    
    public String[][] build(){
    try {
      FileReader arq = new FileReader(this.fileName);
      BufferedReader lerArq = new BufferedReader(arq);
      
      //primeira linha do arquivo é o tamanho
      String linha = lerArq.readLine();
      
      this.size = Integer.parseInt(linha);
      
      this.labyrinth = new String[this.size][this.size]; 
                                        
      //da segunda em diante, é o labirinto
      linha = lerArq.readLine();
      String[] split = linha.split(" ");
      
        
      for(int i = 0; i < this.size; i++){
        for(int j = 0; j < this.size; j++){
            labyrinth[i][j] = split[j];
            if(split[j].equalsIgnoreCase("S")){
                this.start = new Coordinates(i,j);   
            }else if(split[j].equalsIgnoreCase("E")){
                this.end = new Coordinates(i,j);
            }
        }
        linha = lerArq.readLine();
        if(linha == null){
            break;
        }
        split = linha.split(" ");
      }
         
      
      
      return labyrinth;
     
    } catch (IOException e) {
        System.err.printf("Erro na abertura do arquivo: %s.\n",
          e.getMessage());
    }
    
    return null;
  }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Coordinates getStart() {
        return start;
    }

    public void setStart(Coordinates start) {
        this.start = start;
    }

    public Coordinates getEnd() {
        return end;
    }

    public void setEnd(Coordinates end) {
        this.end = end;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[][] getLabyrinth() {
        return labyrinth;
    }

    public void setLabyrinth(String[][] labyrinth) {
        this.labyrinth = labyrinth;
    }
    
}
