package org.academiadecodigo.gitbusters.Utility;

public enum Graphics {

    STEP0("  +---+\n" +
            "  |   |\n" +
            "      |\n" +
            "      |\n" +
            "      |\n" +
            "      |\n" +
            "==============",0),

    STEP1("  +---+\n" +
            "  |   |\n" +
            "  O   |\n" +
            "      |\n" +
            "      |\n" +
            "      |\n" +
            "==============",1),
    STEP2("  +---+\n" +
            "  |   |\n" +
            "  O   |\n" +
            "  |   |\n" +
            "      |\n" +
            "      |\n" +
            "==============",2),
    STEP3("  +---+\n" +
            "  |   |\n" +
            "  O   |\n" +
            " /|   |\n" +
            "      |\n" +
            "      |\n" +
            "==============",3),
    STEP4("  +---+\n" +
            "  |   |\n" +
            "  O   |\n" +
            " /|\\  |\n" +
            "      |\n" +
            "      |\n" +
            "==============",4),
    STEP5("  +---+\n" +
            "  |   |\n" +
            "  O   |\n" +
            " /|\\  |\n" +
            " /    |\n" +
            "      |\n" +
            "==============",5),
    STEP6("  +---+\n" +
            "  |   |\n" +
            "  O   |\n" +
            " /|\\  |\n" +
            " / \\  |\n" +
            "      |\n" +
            "==============",6);



    private String picture;
    private int id;
    private static int counter = 0;

    Graphics(String picture, int id){
        this.picture = picture;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPicture() {
        return picture;
    }

    public static int getCounter() {
        return counter;
    }

    public static void incrementCounter(){
        counter++;
    }

    public static void resetCounter(){
        counter = 0;
    }
}
