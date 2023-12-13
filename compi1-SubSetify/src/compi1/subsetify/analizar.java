/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi1.subsetify;

import java.io.StringReader;

/**
 *
 * @author Steven
 */
public class analizar {
    
    public analizar( ){
         //analizadores("src/analizadores/","lexico.flex","sintactico.cup");
//        String cadena = """
//                      	{
//                        // CONJUNTOS
//                        CONJ: letra -> a~z;
//                        CONJ: digito -> 0-9;
//                        ////// EXPRESIONES REGULARES
//                        ExpReg1 -> . (letra) * | * * | (letra) (digito);
//                        ExpresionReg2 -> . (digito). *." + (digito);
//                        RegEx3 -> . (digito) * I *_* | (letra) (digito);
//                      }
//                      """;
        ///analizar(cadena);
        
    }
    
     // Realizar Analisis
    public static void analizar (String entrada){
        try {
            analizadores.lexico  lexico = new analizadores.lexico(new StringReader(entrada)); 
            analizadores.sintactico  parser = new analizadores.sintactico(lexico);
            parser.parse();
        } catch (Exception e) {
            System.out.println("Error fatal en compilación de entrada.");
            System.out.println(e);
        } 
    } 
    
    public static void analizadores(String ruta, String jflexFile, String cupFile){
        try {
            String opcionesJflex[] =  {ruta+jflexFile,"-d",ruta};
            jflex.Main.generate(opcionesJflex);

            String opcionesCup[] =  {"-destdir", ruta,"-parser","sintactico",ruta+cupFile};
            java_cup.Main.main(opcionesCup);
            
        } catch (Exception e) {
            System.out.println("No se ha podido generar los analizadores");
            System.out.println(e);
        }
    }
    
    
}
