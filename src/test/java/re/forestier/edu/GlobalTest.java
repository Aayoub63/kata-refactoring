package re.forestier.edu;

import org.junit.jupiter.api.Test;
import re.forestier.edu.rpg.affichage.Affichage;
import re.forestier.edu.rpg.capacites.UpdatePlayer;
import re.forestier.edu.rpg.joueur.player;
import re.forestier.edu.rpg.objets.Item;

import java.util.ArrayList;

import static org.approvaltests.Approvals.verify;

public class GlobalTest {

    @Test
    void testAffichageBase() {
        player player = new player("Florian", "Gnognak le Barbare", "ADVENTURER", 200, new ArrayList<>());
        UpdatePlayer.addXp(player, 20);
        player.setInventory(new ArrayList<Item>());

        verify(Affichage.afficherJoueur(player));
    }

    @Test
    void testAffichageMarkdown() {
        player player = new player("Florian", "Gnognak le Barbare", "ADVENTURER", 200, new ArrayList<>());
        UpdatePlayer.addXp(player, 20);
        player.setInventory(new ArrayList<Item>());

        verify(Affichage.afficherJoueurMarkdown(player));
    }
}
