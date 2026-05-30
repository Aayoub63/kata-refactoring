package re.forestier.edu.rpg.capacites;
import java.util.HashMap;

import re.forestier.edu.rpg.joueur.PlayerClass;

public class AbilityService {

    private static final AbilityService INSTANCE = new AbilityService();
    private final HashMap<PlayerClass, HashMap<Integer, HashMap<String, Integer>>> abilitiesCache;
    
    private AbilityService() {
        abilitiesCache = initializeAbilities();
    }
    
    public static AbilityService getInstance() {
        return INSTANCE;
    }
    
    public HashMap<String, Integer> getAbilitiesForLevel(PlayerClass playerClass, int level) {
        return abilitiesCache.get(playerClass).get(level);
    }
    
    private HashMap<String, Integer> createLevelMap(String... abilities) {

        // Utilise un format varargs alterné (nom, valeur, nom, valeur...) pour simplifier
        // la définition des capacités dans le code : plus lisible que des appels put() multiples
        // Format choisi pour réduire la verbosité lors de l'initialisation des capacités

        HashMap<String, Integer> levelMap = new HashMap<>();
        for (int i = 0; i < abilities.length; i += 2) {
            levelMap.put(abilities[i], Integer.parseInt(abilities[i + 1]));
        }
        return levelMap;
    }
    
    
    private HashMap<PlayerClass, HashMap<Integer, HashMap<String, Integer>>> initializeAbilities() {
        HashMap<PlayerClass, HashMap<Integer, HashMap<String, Integer>>> abilities = new HashMap<>();
        
        // La structure imbriquée (classe -> niveau -> capacités) permet un accès rapide O(1) aux capacités
        
        // Structure de données principale : une map par classe de joueur
        // Chaque classe contient une map de niveaux, chaque niveau contient les capacités à ce niveau
        
        // ADVENTURER
        HashMap<Integer, HashMap<String, Integer>> adventurerMap = new HashMap<>();
        adventurerMap.put(1, createLevelMap("INT", "1", "DEF", "1", "ATK", "3", "CHA", "2"));
        adventurerMap.put(2, createLevelMap("INT", "2", "CHA", "3"));
        adventurerMap.put(3, createLevelMap("ATK", "5", "ALC", "1"));
        adventurerMap.put(4, createLevelMap("DEF", "3"));
        adventurerMap.put(5, createLevelMap("VIS", "1", "DEF", "4"));
        abilities.put(PlayerClass.ADVENTURER, adventurerMap);
        
        // ARCHER
        HashMap<Integer, HashMap<String, Integer>> archerMap = new HashMap<>();
        archerMap.put(1, createLevelMap("INT", "1", "ATK", "3", "CHA", "1", "VIS", "3"));
        archerMap.put(2, createLevelMap("DEF", "1", "CHA", "2"));
        archerMap.put(3, createLevelMap("ATK", "3"));
        archerMap.put(4, createLevelMap("DEF", "2"));
        archerMap.put(5, createLevelMap("ATK", "4"));
        abilities.put(PlayerClass.ARCHER, archerMap);
        
        // DWARF
        HashMap<Integer, HashMap<String, Integer>> dwarfMap = new HashMap<>();
        dwarfMap.put(1, createLevelMap("ALC", "4", "INT", "1", "ATK", "3"));
        dwarfMap.put(2, createLevelMap("DEF", "1", "ALC", "5"));
        dwarfMap.put(3, createLevelMap("ATK", "4"));
        dwarfMap.put(4, createLevelMap("DEF", "2"));
        dwarfMap.put(5, createLevelMap("CHA", "1"));
        abilities.put(PlayerClass.DWARF, dwarfMap);
        
        // GOBLIN
        HashMap<Integer, HashMap<String, Integer>> goblinMap = new HashMap<>();
        goblinMap.put(1, createLevelMap("INT", "2", "ATK", "2", "ALC", "1"));
        goblinMap.put(2, createLevelMap("ATK", "3", "ALC", "4"));
        goblinMap.put(3, createLevelMap("VIS", "1"));
        goblinMap.put(4, createLevelMap("DEF", "1"));
        goblinMap.put(5, createLevelMap("DEF", "2", "ATK", "3"));
        abilities.put(PlayerClass.GOBLIN, goblinMap);
        
        return abilities;
    }
    
}
