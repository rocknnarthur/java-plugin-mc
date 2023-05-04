package stunrayn.cl;

public enum GradeList {

    FONDATEUR(0, 100, "§8[§4Fonda§8] §c", "", " §8§l>> §b"),

    JOUEUR(1, 1, "§7", "", " > "),

    POTOW(2, 40, "§4♡§2Poto§4♡ §2","" , " §f> §7");

    //fields
    private final int power, id;
    private final String prefix, suffix, chatSeparator;

    //constructor
    private GradeList(int id, int power, String prefix, String suffix, String chatSeparator) {
        this.id = id;
        this.power = power;
        this.prefix = prefix;
        this.suffix = suffix;
        this.chatSeparator = chatSeparator;
    }

    //Method GETTER
    public final int getId() {
        return id;
    }

    public final int getPower() {
        return power;

    }

    public final String getPrefix() {
        return prefix;

    }

    public final String getSuffix() {
        return suffix;
    }

    public final String getName() {
        return this.toString() ;
    }

    public final String getChatSeparator() {
        return chatSeparator;
    }
}

