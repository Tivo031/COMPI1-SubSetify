// ------------  Paquete e importaciones ------------
package analizadores; 

import java_cup.runtime.*;

%%	
//-------> Directivas (No tocar)

%public 
%class lexico
%cup
%char
%column
%line
%unicode
%ignorecase

%{ 
%} 

// ------> Expresiones Regulares 
digito = [0-9]
numero = {digito}+
letra = [a-zA-ZñÑ]
id = {letra}+({letra}|{digito}|"_")*
signo = "!"|"#"|"$"|"&"|"%"|"'"|"("|")"|"*"|"+"|","|"-"|"."|"/"|":"|";"|"<"|"="|">"|"?"|"@"|"["|"]"|"^"|"_"|"`"|"{"|"}"|"|"|"\\"
flecha = ->
especiales = "\"\\n\""|"\"\\\'\""|"\"\\\"\""
cadena =  \"[^\"\n]*\"

//-----------> Estados
%state COMENT_SIMPLE
%state COMENT_MULTI

%%
// ------------  Reglas Lexicas -------------------

<YYINITIAL> "<!"                {yybegin(COMENT_MULTI);}     // Si la entrada es un comentario inicia aqui
<COMENT_MULTI> "!>"             {yybegin(YYINITIAL);}        // Si se acaba el comentario vuelve a YYINITIAL
<COMENT_MULTI> .                {}
<COMENT_MULTI> [ \t\r\n\f]      {}

<YYINITIAL> "//"                {yybegin(COMENT_SIMPLE);}   // Si la entrada es comentario simple inicia aqui
<COMENT_SIMPLE> [^"\n"]         {}                          // 
<COMENT_SIMPLE> "\n"            {yybegin(YYINITIAL);}       // aqui sale del estado

<YYINITIAL> "{"             {System.out.println("Reconocido: << "+yytext()+" >>, llavea");
                             return new Symbol(sym.llaveA, yycolumn, yyline, yytext());}

<YYINITIAL> "}"             {System.out.println("Reconocido: << "+yytext()+" >>, llavec");
                            return new Symbol(sym.llaveC, yycolumn, yyline, yytext());}

<YYINITIAL> "("             {System.out.println("Reconocido: << "+yytext()+" >>, ParentesisA");
                            return new Symbol(sym.parentesisA, yycolumn, yyline, yytext());}

<YYINITIAL> ")"             {System.out.println("Reconocido: << "+yytext()+" >>, ParentesisC");
                            return new Symbol(sym.parentesisC, yycolumn, yyline, yytext());}

<YYINITIAL> "-"             {System.out.println("Reconocido: << "+yytext()+" >>, Guion");
                            return new Symbol(sym.guion, yycolumn, yyline, yytext());}

<YYINITIAL> ":"             {System.out.println("Reconocido: << "+yytext()+" >>, dospuntos");
                            return new Symbol(sym.dospuntos, yycolumn, yyline, yytext());}

<YYINITIAL> ";"             {System.out.println("Reconocido: << "+yytext()+" >>, puntocoma");
                            return new Symbol(sym.puntocoma, yycolumn, yyline, yytext());}

<YYINITIAL> "."             {System.out.println("Reconocido: << "+yytext()+" >>, punto");
                            return new Symbol(sym.punto, yycolumn, yyline, yytext());}

<YYINITIAL> "*"             {System.out.println("Reconocido: << "+yytext()+" >>, asterisco");
                            return new Symbol(sym.asterisco, yycolumn, yyline, yytext());}

<YYINITIAL> "+"             {System.out.println("Reconocido: << "+yytext()+" >>, mas");
                            return new Symbol(sym.mas, yycolumn, yyline, yytext());}

<YYINITIAL> "?"             {System.out.println("Reconocido: << "+yytext()+" >>, interrogacion");
                            return new Symbol(sym.interrogacion, yycolumn, yyline, yytext());}

<YYINITIAL> "|"             {System.out.println("Reconocido: << "+yytext()+" >>, or");
                            return new Symbol(sym.disyuncion, yycolumn, yyline, yytext());}

<YYINITIAL> ","             {System.out.println("Reconocido: << "+yytext()+" >>, coma");
                            return new Symbol(sym.coma, yycolumn, yyline, yytext());}

<YYINITIAL> "~"             {System.out.println("Reconocido: << "+yytext()+" >>, virgulilla");
                            return new Symbol(sym.virgulilla, yycolumn, yyline, yytext());}

<YYINITIAL> "conj"          {System.out.println("Reconocido: << "+yytext()+" >>, imprimir");
                            return new Symbol(sym.conj, yycolumn, yyline, yytext());}

<YYINITIAL> {id}            {System.out.println("Reconocido: << "+yytext()+" >>, id");
                            return new Symbol(sym.id, yycolumn, yyline, yytext());}

<YYINITIAL> {numero}        {System.out.println("Reconocido: << "+yytext()+" >>, numero");
                             return new Symbol(sym.numero, yycolumn, yyline, yytext());}
/*
{letra}         {System.out.println("Reconocido: << "+yytext()+" >>, letra");
                return new Symbol(sym.letra, yycolumn, yyline, yytext());}
*/
<YYINITIAL> {signo}         {System.out.println("Reconocido: << "+yytext()+" >>, Signo");
                            return new Symbol(sym.signo, yycolumn, yyline, yytext());}

<YYINITIAL> {especiales}    {System.out.println("Reconocido: << "+yytext()+" >>, especial");
                            return new Symbol(sym.especial, yycolumn, yyline, yytext());}

<YYINITIAL> {flecha}        {System.out.println("Reconocido: << "+yytext()+" >>, flecha");
                            return new Symbol(sym.flecha, yycolumn, yyline, yytext());}

<YYINITIAL> {cadena}        {System.out.println("Reconocido: << "+yytext()+" >>, Cadena");
                            return new Symbol(sym.cadena, yycolumn, yyline, yytext()); }


//------> Ingorados 
[ \t\r\n\f]     {/* Espacios en blanco se ignoran */}

//------> Errores L�xicos 
.           	{ System.out.println("Error Lexico: " + yytext() + " | Fila:" + yyline + " | Columna: " + yycolumn); }
