package re.forestier.edu.rpg.health;

import java.util.HashMap;

import re.forestier.edu.rpg.joueur.PlayerClass;
import re.forestier.edu.rpg.joueur.player;
public class HealthService {
    
    private static final HealthService INSTANCE = new HealthService();
    private final HashMap<PlayerClass, HealthRegenerationStrategy> strategies;
    
    private HealthService() {
        strategies = new HashMap<>();
        strategies.put(PlayerClass.ADVENTURER, new AdventurerHealthStrategy());
        strategies.put(PlayerClass.DWARF, new DwarfHealthStrategy());
        strategies.put(PlayerClass.ARCHER, new ArcherHealthStrategy());
        strategies.put(PlayerClass.GOBLIN, new GoblinHealthStrategy());
    }
    
    public static HealthService getInstance() {
        return INSTANCE;
    }
    
    public void updateHealth(player player) {
        if (player.getCurrenthealthpoints() <= 0) {
            System.out.println("Le joueur est KO !");
            return;
        }
        
        if (player.getCurrenthealthpoints() < player.getHealthpoints() / 2) {
            PlayerClass playerClass = PlayerClass.fromString(player.getAvatarClass());
            // Vérification != null conservée pour  des raisons défensices 
            if (playerClass != null && strategies.containsKey(playerClass)) {
                int regeneration = strategies.get(playerClass).calculateRegeneration(player);
                player.setCurrenthealthpoints(player.getCurrenthealthpoints() + regeneration);
            }
        }
        
        // Limite les HP au maximum pour éviter les dépassements au-delà du maximum autorisé
        if (player.getCurrenthealthpoints() >= player.getHealthpoints()) {
            player.setCurrenthealthpoints(player.getHealthpoints());
        }
    }
}

