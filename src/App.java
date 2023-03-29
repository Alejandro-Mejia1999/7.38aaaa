import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        int[] memory = new int[1000];
        float acumulador = 0;
        int contadorDeInstrucciones = 0;
        int codigoDeOperacion = 0;
        int operando = 0;
        int registroDeInstruccion = 0;
        System.out.println("* Bienvenido a Simpletron! *");
        System.out.println("* Por favor, introduzca en su programa una instruccion *");
        System.out.println("* (o palabra de datos) a la vez. Yo le mostrare *");
        System.out.println("* el numero de ubicacion y un signo de interrogacion (?) *");
        System.out.println("* Entonces usted escribira la palabra para esa ubicacion. *");
        System.out.println("* Teclee -99999 para dejar de introducir su programa. *");
        // Carga del programa en memoria
        do {
            System.out.printf("%02d ? ", contadorDeInstrucciones);
            String instruccionHex = input.next();
            if (instruccionHex.equals("-99999")) {
                break;
            }

            if (instruccionHex.length() == 1) {
                instruccionHex = "0" + instruccionHex; // Asegurarse de que la instruccion tenga dos digitos
            }
            float instruccion = Float.parseFloat(instruccionHex);
            memory[contadorDeInstrucciones] = (int) instruccion;
            contadorDeInstrucciones++;

        } while (contadorDeInstrucciones < 1000);
        System.out.println("* Se completo la carga del programa *");
        System.out.println("* Empieza la ejecucion del programa *");

        // Ejecución del programa en Simpletron
        contadorDeInstrucciones = 0;
        while (codigoDeOperacion != 43) {
            registroDeInstruccion = (int) memory[contadorDeInstrucciones];
            codigoDeOperacion = registroDeInstruccion / 1000;
            operando = registroDeInstruccion % 100;

            switch (codigoDeOperacion) {
                case 10: // Leer
                    System.out.print("Ingrese un entero: ");
                    int numero = input.nextInt();
                    memory[operando] = numero;
                    break;
                case 11: // Escribir
                    // System.out.printf("El contenido de la ubicacion %02d es %d%n", operando,
                    // memory[operando]);

                    for (int j = 0; j < memory[operando]; j++) {
                        System.out.print(memory[operando] + "-");
                        operando++;

                    }
                    break;
                case 12: // Leer
                    System.out.print("Ingrese una cadena: ");
                    String cadena = input.next();
                    char[] caracter = cadena.toCharArray();
                    for (int i = 0; i < cadena.length(); i++) {
                        int ascii = (int) caracter[i];
                        memory[operando + i] = ascii;

                    }
                    break;
                case 13: // Escribir
                    String output = "";
                    int address = operando;
                    while (memory[address] != 0) {
                        char character = (char) memory[address];
                        output += character;
                        address++;
                    }
                    System.out.println(output);
                    break;
                case 14: // Nueva linea
                    System.out.println(" "); // Agregar una nueva linea
                    break;
                case 15: // Almacenar cadena
                    System.out.print("Ingrese una cadena: ");
                    String cade = input.next();
                    int addres = 14;
                    for (int i = 0; i < cade.length(); i++) {
                        char caracte = cade.charAt(i);
                        int ascii = (int) caracte;
                        memory[addres + i] = ascii;
                    }
                    memory[addres + cade.length()] = 0; // Agregar una caracter nulo al final de la cadena
                    break;

                case 20: // Cargar
                    acumulador = memory[operando];
                    break;
                case 21: // Almacenar
                    memory[operando] = (int) acumulador;
                    break;
                case 30: // Sumar
                    acumulador += memory[operando];
                    if (acumulador > 9999 || acumulador < -9999) {
                        System.out.println("* ERROR: Desbordamiento del acumulador *");
                        System.exit(1);
                    }
                    break;
                case 31: // Restar
                    acumulador -= memory[operando];
                    if (acumulador > 9999 || acumulador < -9999) {
                        System.out.println("* ERROR: Desbordamiento del acumulador *");
                        System.exit(1);
                    }
                    break;
                case 32: // Dividir
                    if (memory[operando] == 0) {
                        System.out.println("* ERROR: Division por cero *");
                        System.exit(1);
                    }
                    acumulador /= memory[operando];
                    break;
                case 16: // Residuo
                    if (memory[operando] == 0) {
                        System.out.println("* ERROR: Division por cero *");
                        System.exit(1);
                    }
                    acumulador %= memory[operando];
                    break;
                case 17: // elevar un numero
                    acumulador = (int) Math.pow(acumulador, memory[operando]);
                    if (acumulador > 9999 || acumulador < -9999) {
                        System.out.println("* ERROR: Desbordamiento del acumulador *");
                        System.exit(1);
                    }
                    break;
                case 18:// hexadecimal

                    String hex = Integer.toHexString(memory[operando]);
                    String resul = hex.toUpperCase();
                    System.out.println(resul);
                    break;
            }

            contadorDeInstrucciones++;
        }

        input.close();
    }
}
