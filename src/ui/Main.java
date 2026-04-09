package ui;

public class Main {

    public static final String CYAN  = "\u001B[36m";
    public static final String RESET = "\u001B[0m";
    public static void main (String[] args) {
        Menu monMenu = new Menu();
        monMenu.afficherMenuPrincipal();
        System.out.println(CYAN + "================================================" + RESET);
        System.out.println(CYAN + " AU REVOIR ! Merci d'avoir utilisé Mobile Money" + RESET);
        System.out.println(CYAN + "================================================" + RESET);

    }
}