package re.forestier.edu.rpg.health;

import re.forestier.edu.rpg.joueur.player;

public class DwarfHealthStrategy implements HealthRegenerationStrategy {
    @Override
    public int calculateRegeneration(player player) {
        int regeneration = 1;
        if (player.findItemByName("Holy Elixir") != null) {
            regeneration += 1;
        }
        return regeneration;
    }
}

