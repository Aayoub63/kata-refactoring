package re.forestier.edu.rpg.health;

import re.forestier.edu.rpg.joueur.player;

public class AdventurerHealthStrategy implements HealthRegenerationStrategy {
    @Override
    public int calculateRegeneration(player player) {
        int regeneration = 2;
        if (player.retrieveLevel() < 3) {
            regeneration -= 1;
        }
        return regeneration;
    }
}

