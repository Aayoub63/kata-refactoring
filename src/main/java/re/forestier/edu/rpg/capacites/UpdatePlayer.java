package re.forestier.edu.rpg.capacites;

import java.util.HashMap;

import re.forestier.edu.rpg.joueur.PlayerClass;
import re.forestier.edu.rpg.joueur.player;
import re.forestier.edu.rpg.health.HealthService;
public class UpdatePlayer {

    private static final ExperienceService experienceService = ExperienceService.getInstance();
    private static final HealthService healthService = HealthService.getInstance();
    private static final AbilityService abilityService = AbilityService.getInstance();

    // Méthode de compatibilité pour les tests : délègue à AbilityService
    // Construit la structure imbriquée attendue par les tests existants
    public static HashMap<String, HashMap<Integer, HashMap<String, Integer>>> abilitiesPerTypeAndLevel() {
        HashMap<String, HashMap<Integer, HashMap<String, Integer>>> result = new HashMap<>();
        
        for (PlayerClass playerClass : PlayerClass.values()) {
            HashMap<Integer, HashMap<String, Integer>> levels = new HashMap<>();
            for (int level = 1; level <= 5; level++) {
                levels.put(level, abilityService.getAbilitiesForLevel(playerClass, level));
            }
            result.put(playerClass.name(), levels);
        }
        
        return result;
    }

    public static boolean addXp(player player, int xp) {
        return experienceService.addXp(player, xp);
    }

    public static void majFinDeTour(player player) {
        healthService.updateHealth(player);
    }
}