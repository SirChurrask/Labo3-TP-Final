import java.util.Scanner;

public class Main {
    static Scanner scan;
    public static void main(String[] args) {

        int seleccion = 0;
        int i = 0;

        scan = new Scanner(System.in);
        Menu menues = new Menu();

        do
        {
            System.out.printf(menues.mostrarMenuInicial());
            System.out.printf("Ingrese una opcion: ");
            seleccion = scan.nextInt();

            switch (seleccion)
            {
                case 1:
                    for(i=0; i<10; i++)
                    {
                        System.out.println("");


                    }

                    break;
                case 2:
                    for(i=0; i<10; i++)
                    {
                        System.out.println("");
                    }

                    int seleccionInstrucciones = 0;

                    do
                    {
                        MenuInstrucciones instrucciones = new MenuInstrucciones();

                        System.out.println(menues.mostrarMenuInstrucciones());
                        System.out.printf("Ingrese una opcion: ");
                        seleccionInstrucciones = scan.nextInt();

                        switch (seleccionInstrucciones)
                        {
                            case 1:
                                for(i=0; i<10; i++)
                                {
                                    System.out.println("");
                                }

                                System.out.println(instrucciones.mostrarJugadas());

                                break;
                            case 2:
                                for(i=0; i<10; i++)
                                {
                                    System.out.println("");
                                }

                                System.out.println(instrucciones.mostrarValores());

                                System.out.printf("Presione 1 para volver al menu anterior o presiones 2 para cerrar el programa: ");
                                int opcion = scan.nextInt();

                                if(opcion == 1)
                                {
                                    seleccionInstrucciones = 0;
                                }
                                break;
                            case 3:
                                for(i=0; i<10; i++)
                                {
                                    System.out.println("");
                                }

                                seleccion = 0;

                                break;
                            default:
                                for(i=0; i<10; i++)
                                {
                                    System.out.println("");
                                }

                                System.out.printf("Opcion ingresada invalida, vuelvalo a intentar");
                                break;
                        }
                    }while(seleccionInstrucciones < 1 || seleccionInstrucciones > 3);

                    break;
                case 3:
                    for(i=0; i<10; i++)
                    {
                        System.out.println("");
                    }

                    break;
                default:
                    for(i=0; i<10; i++)
                    {
                        System.out.println("");
                    }

                    System.out.printf("Opcion ingresada invalida, vuelvalo a intentar");
                    break;
            }
        }while(seleccion < 1 || seleccion > 3);
    }
}