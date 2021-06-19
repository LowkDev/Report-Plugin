package me.mherzaqaryan.report.reportSystem;

public enum CheatingType {

    KILLAURA("killaura"),
    FLIGHT("flight"),
    SCAFFOLD("scaffold"),
    REACHING("reaching"),
    SPEED("speed"),
    ANTI_KNOCKBACK("antikb"),
    OTHER("other");

    private String name;

    CheatingType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CheatingType getEnum(String name) {
        switch (name) {
            case "Killaura":
                return KILLAURA;
            case "Flight":
                return FLIGHT;
            case "Scaffold":
                return SCAFFOLD;
            case "Reaching":
                return REACHING;
            case "Speed":
                return SPEED;
            case "Antikb" :
                return ANTI_KNOCKBACK;
            case "Other":
                return OTHER;
        }
        return OTHER;
    }

}
