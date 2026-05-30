package re.forestier.edu.rpg.health;

import re.forestier.edu.rpg.joueur.player;

public interface HealthRegenerationStrategy {
    int calculateRegeneration(player player);
}

