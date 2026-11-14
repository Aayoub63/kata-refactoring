package re.forestier.edu;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.capacites.UpdatePlayer;
import re.forestier.edu.rpg.joueur.player;

public class RegressionTests {

    @Test
    @DisplayName("Le level-up d'un joueur ne modifie pas les capacités des nouveaux joueurs")
    void testLevelUpNeModifiePasLesAutresJoueurs() {
        player premier = new player("Florian", "Premier", "ADVENTURER", 0, new ArrayList<>());
        UpdatePlayer.addXp(premier, 20);

        player second = new player("Florian", "Second", "ADVENTURER", 0, new ArrayList<>());

        assertThat(second.getAbilities().get("INT"), is(1));
        assertThat(second.getAbilities().get("CHA"), is(2));
    }

    @Test
    @DisplayName("Deux joueurs ne partagent pas la même map de capacités")
    void testCapacitesNonPartagees() {
        player p1 = new player("Florian", "Un", "DWARF", 0, new ArrayList<>());
        player p2 = new player("Florian", "Deux", "DWARF", 0, new ArrayList<>());

        assertThat(p1.getAbilities(), not(sameInstance(p2.getAbilities())));
    }

    @Test
    @DisplayName("Une classe écrite en minuscules est acceptée")
    void testClasseEnMinuscules() {
        player archer = new player("Florian", "Archer", "archer", 0, new ArrayList<>());

        assertNotNull(archer.getAbilities());
        assertThat(archer.getAbilities().get("VIS"), is(3));
    }
}
