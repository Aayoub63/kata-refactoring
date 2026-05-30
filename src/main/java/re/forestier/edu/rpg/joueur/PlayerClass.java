package re.forestier.edu.rpg.joueur;


public enum PlayerClass {
    ARCHER,
    ADVENTURER,
    DWARF,
    GOBLIN;

    public static PlayerClass fromString(String className) {

        // Convertit en majuscules pour accepter les variations de casse (robustesse)
        // Cela permet d'accepter "archer", "ARCHER", "Archer" sans distinction
        // Retourne null plutôt que de lever une exception pour permettre au code appelant
        // de gérer les classes invalides de manière défensive (compatibilité avec les tests existants)


        try {
            return valueOf(className.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
}
