package re.forestier.edu.rpg.health;

import re.forestier.edu.rpg.joueur.player;

public class ArcherHealthStrategy implements HealthRegenerationStrategy {
    @Override
    public int calculateRegeneration(player player) {
        int regeneration = 1;
        if (player.findItemByName("Magic Bow") != null) {
            // Division entière : peut donner 0 ou négatif si currenthealthpoints < 8
            // Le bonus est basé sur les HP actuels divisés par 8, moins 1
            regeneration += player.getCurrenthealthpoints() / 8 - 1;
        }
        return regeneration;
    }
}

