package re.forestier.edu.rpg.health;

import re.forestier.edu.rpg.joueur.player;

public class GoblinHealthStrategy implements HealthRegenerationStrategy {
    @Override
    public int calculateRegeneration(player player) {
        return 1;
    }
}

