package re.forestier.edu.rpg.capacites;

import java.util.HashMap;
import java.util.Random;

import re.forestier.edu.rpg.joueur.PlayerClass;
import re.forestier.edu.rpg.joueur.player;
import re.forestier.edu.rpg.objets.Item;
public class ExperienceService {
    
    private static final ExperienceService INSTANCE = new ExperienceService();
    private static final Item[] OBJECT_LIST = {
        new Item("Lookout Ring", "Prevents surprise attacks", 1, 50),
        new Item("Scroll of Stupidity", "INT-2 when applied to an enemy", 1, 30),
        new Item("Draupnir", "Increases XP gained by 100%", 2, 200),
        new Item("Magic Charm", "Magic +10 for 5 rounds", 1, 75),
        new Item("Rune Staff of Curse", "May burn your ennemies... Or yourself. Who knows?", 3, 150),
        new Item("Combat Edge", "Well, that's an edge", 2, 40),
        new Item("Holy Elixir", "Recover your HP", 1, 60)
    };
    
    private final AbilityService abilityService;
    private final Random random;
    
    private ExperienceService() {
        this.abilityService = AbilityService.getInstance();
        this.random = new Random();
    }
    
    public static ExperienceService getInstance() {
        return INSTANCE;
    }
    
    public boolean addXp(player player, int xp) {
        int currentLevel = player.retrieveLevel();
        player.setXp(player.getXp() + xp);
        int newLevel = player.retrieveLevel();
        
        // Détecte un level-up en comparant le niveau avant et après l'ajout d'XP
        // Si le niveau a changé, donne un objet aléatoire et met à jour les capacités
        if (newLevel != currentLevel) {
            giveRandomObject(player);
            updateAbilities(player, newLevel);
            return true;
        }
        return false;
    }
    
    private void giveRandomObject(player player) {
        // Utilise addItem pour vérifier le poids maximal
        // Si le poids maximal est dépassé, on n'ajoute pas l'objet
        // (le joueur ne peut pas le porter)
        Item randomObject = OBJECT_LIST[random.nextInt(OBJECT_LIST.length)];
        if (!player.addItem(randomObject)) {
            // Si le poids maximal est dépassé, on n'ajoute pas l'objet
            // (le joueur ne peut pas le porter)
        }
    }
    
    private void updateAbilities(player player, int level) {
        PlayerClass playerClass = PlayerClass.fromString(player.getAvatarClass());
        // Met à jour les capacités de manière cumulative : applique toutes les capacités
        // depuis le niveau 1 jusqu'au niveau actuel pour garantir que toutes les capacités
        // sont présentes (corrige le bug de l'aventurier niveau 2)

        for (int l = 1; l <= level; l++) {

        HashMap<String, Integer> abilities = abilityService.getAbilitiesForLevel(playerClass, l);
        
        abilities.forEach((ability, abilityLevel) -> {
            player.getAbilities().put(ability, abilityLevel);
        });
        }
    }
}

