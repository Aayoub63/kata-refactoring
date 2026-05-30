package re.forestier.edu;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.affichage.Affichage;
import re.forestier.edu.rpg.capacites.UpdatePlayer;
import re.forestier.edu.rpg.joueur.player;

public class UnitTests {

    @Test
    @DisplayName("Sample test")
    void testPlayerName() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        assertThat(player.getPlayerName(), is("Florian"));
    }

    @Test
    @DisplayName("Impossible to have negative money")
    void testNegativeMoney() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());

        try {
            p.removeMoney(200);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail();
    }
    @Test 
    @DisplayName("Test retrait d'argent valide")
    void testRemoveMoneyValid() {
    player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
    player.removeMoney(30);
    assertThat(player.getMoney(), is(70));
    }

    @Test
    @DisplayName("removeMoney: retrait exact à zéro")
    void testRemoveMoneyToZero() {
    player p = new player("Florian", "Grognak", "ADVENTURER", 100, new ArrayList<>());
    p.removeMoney(100);
    assertThat(p.getMoney(), is(0));
    }
    
    @Test
    @DisplayName("Test récupération de l'XP")
    void testGetXp() {
    player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
    UpdatePlayer.addXp(player, 50);
    assertThat(player.getXp(), is(50));
    }
    @Test
    @DisplayName("Test création joueur avec classe invalide")
    void testInvalidAvatarClass() {
    player player = new player("Florian", "Grognak le barbare", "INVALID", 100, new ArrayList<>());
    // Le constructeur retourne sans initialiser les champs si la classe est invalide
    assertNull(player.getPlayerName());
    }

    @Test
    @DisplayName("Test calcul du niveau - niveau 1")
    void testRetrieveLevel1() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        // Le joueur commence au niveau 1 avec 0 XP
        assertThat(player.retrieveLevel(), is(1));
    }

    @Test
    @DisplayName("Test calcul du niveau - niveau 2")
    void testRetrieveLevel2() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        // Ajouter 15 XP pour atteindre le niveau 2
        UpdatePlayer.addXp(player, 15);
        assertThat(player.retrieveLevel(), is(2));
    }

    @Test
    @DisplayName("exactement 10 XP -> niveau 2")
    void testRetrieveLevelBoundaryLevel2() {
    player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
    UpdatePlayer.addXp(player, 10);
    assertThat(player.retrieveLevel(), is(2));
    }

    @Test
    @DisplayName("Test calcul du niveau - niveau 3")
    void testRetrieveLevel3() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        // Ajouter 30 XP pour atteindre le niveau 3
        UpdatePlayer.addXp(player, 30);
        assertThat(player.retrieveLevel(), is(3));
    }

    @Test
    @DisplayName("exactement 27 XP -> niveau 3")
    void testRetrieveLevelBoundaryLevel3() {
    player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
    UpdatePlayer.addXp(player, 27);
    assertThat(player.retrieveLevel(), is(3));
    }

    @Test
    @DisplayName("Test calcul du niveau - niveau 4")
    void testRetrieveLevel4() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        // Ajouter 60 XP pour atteindre le niveau 4
        UpdatePlayer.addXp(player, 60);
        assertThat(player.retrieveLevel(), is(4));
    }

    @Test
    @DisplayName("exactement 57 XP -> niveau 4")
    void testRetrieveLevelBoundaryLevel4() {
    player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
    UpdatePlayer.addXp(player, 57);
    assertThat(player.retrieveLevel(), is(4));
    }

    @Test
    @DisplayName("Test calcul du niveau - niveau 5")
    void testRetrieveLevel5() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        // Ajouter 120 XP pour atteindre le niveau 5
        UpdatePlayer.addXp(player, 120);
        assertThat(player.retrieveLevel(), is(5));
    }

    @Test
    @DisplayName("exactement 111 XP -> niveau 5")
    void testRetrieveLevelBoundaryLevel5() {
    player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
    UpdatePlayer.addXp(player, 111);
    assertThat(player.retrieveLevel(), is(5));
    }

    @Test
    @DisplayName("Test ajout d'argent")
    void testAddMoney() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        player.addMoney(50);
        assertThat(player.getMoney(), is(150));
    }

    @Test
    @DisplayName("Test ajout d'argent avec valeur nulle")
    void testAddMoneyWithNull() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        // Le code gère les valeurs nulles en ajoutant 0
        player.addMoney(0);
        assertThat(player.getMoney(), is(100));
    }

    @Test
    @DisplayName("Test ajout d'argent avec montant négatif")
    void testAddNegativeMoney() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        player.addMoney(-50);
        assertThat(player.getMoney(), is(50));
    }

    @Test
    @DisplayName("Test constructeur de classe Affichage")
    void TestConstructeurAffichage(){
        Affichage affichage = new Affichage();
        assertNotNull(affichage);
    }
    
    @Test
    @DisplayName("Test affichage joueur avec inventaire")
    void testAfficherJoueurAvecInventaire() {
    ArrayList<String> inventory = new ArrayList<>();
    inventory.add("Épée en fer");
    inventory.add("Potion de soin");
    inventory.add("Bouclier en bois");

    player player = new player("Alice", "Luna l'elfe", "ARCHER", 250, inventory);
    
    String result = Affichage.afficherJoueur(player);
    
    assertThat(result.contains("Joueur Luna l'elfe joué par Alice"), is(true));
    assertThat(result.contains("Niveau : 1 (XP totale : 0)"), is(true));
    assertThat(result.contains("Capacités :"), is(true));
    assertThat(result.contains("Inventaire :"), is(true));
    assertThat(result.contains("Épée en fer"), is(true));
    assertThat(result.contains("Potion de soin"), is(true));
    assertThat(result.contains("Bouclier en bois"), is(true));
    }


    @Test
    @DisplayName("Test constructeur de UpdatePlayer")
    void TestConstructeurUpdatePlayer(){
        UpdatePlayer update_player = new UpdatePlayer();
        assertNotNull(update_player);
    }
    
    @Test
    @DisplayName("addXp: pas de level-up -> false et niveau inchangé")
    void testAddXpNoLevelUp() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        int levelAvant = p.retrieveLevel();
        boolean leveled = UpdatePlayer.addXp(p, 5); // 5 < 10 donc reste niveau 1
        assertThat(leveled, is(false));
        assertThat(p.retrieveLevel(), is(levelAvant));
        assertThat(p.getXp(), is(5));
    }

    @Test
    @DisplayName("addXp: level-up direct au niveau 3 -> inventaire +1 et capacités mises à jour")
    void testAddXpLevelUpTo3() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        int invAvant = p.getInventory().size();
        boolean leveled = UpdatePlayer.addXp(p, 27); // 27 => niveau 3
        assertThat(leveled, is(true));
        assertThat(p.retrieveLevel(), is(3));
        assertThat(p.getInventory().size(), is(invAvant + 1));
        // Vérifie quelques capacités attendues au niveau 3
        assertThat(p.getAbilities().get("ATK"), is(5));
        assertThat(p.getAbilities().get("ALC"), is(1));
    }

    @Test
    @DisplayName("majFinDeTour: ADVENTURER < 50% HP et niveau < 3 -> +1 HP")
    void testMajFinDeTourAdventurerLowHpLowLevel() {
    player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
    p.setHealthpoints(100);
    p.setCurrenthealthpoints(40); // < 50
    UpdatePlayer.majFinDeTour(p);
    assertThat(p.getCurrenthealthpoints(), is(41)); // +2 puis -1 => +1
    }
   
    @Test
    @DisplayName("majFinDeTour: ARCHER < 50% HP sans arc magique -> +1 HP")
    void testMajFinDeTourArcherLowHpNoBow() {
    player p = new player("Alice", "Luna", "ARCHER", 100, new ArrayList<>());
    p.setHealthpoints(100);
    p.setCurrenthealthpoints(40);
    UpdatePlayer.majFinDeTour(p);
    assertThat(p.getCurrenthealthpoints(), is(41));
    }
   
    @Test
    @DisplayName("majFinDeTour: ARCHER < 50% HP avec Magic Bow -> +5 HP depuis 40")
    void testMajFinDeTourArcherLowHpWithBow() {
    ArrayList<String> inv = new ArrayList<>();
    inv.add("Magic Bow");
    player p = new player("Alice", "Luna", "ARCHER", 100, inv);
    p.setHealthpoints(100);
    p.setCurrenthealthpoints(40);
    UpdatePlayer.majFinDeTour(p);
    // +1 puis + (41/8 - 1) = +4 => total +5 -> 45
    assertThat(p.getCurrenthealthpoints(), is(45));
    }
   
    @Test
    @DisplayName("majFinDeTour: DWARF < 50% HP avec Holy Elixir -> +2 HP")
    void testMajFinDeTourDwarfWithElixir() {
    ArrayList<String> inv = new ArrayList<>();
    inv.add("Holy Elixir");
    player p = new player("Thorin", "Oakenshield", "DWARF", 100, inv);
    p.setHealthpoints(100);
    p.setCurrenthealthpoints(40);
    UpdatePlayer.majFinDeTour(p);
    assertThat(p.getCurrenthealthpoints(), is(42));
    }
   
    @Test
    @DisplayName("majFinDeTour: KO (0 HP) -> inchangé")
    void testMajFinDeTourKo() {
    player p = new player("Florian", "Grognak", "ADVENTURER", 100, new ArrayList<>());
    p.setHealthpoints(100);
    p.setCurrenthealthpoints(0);
    UpdatePlayer.majFinDeTour(p);
    assertThat(p.getCurrenthealthpoints(), is(0));
    }
   
    @Test
    @DisplayName("majFinDeTour: dépasse PV max -> capé à HP")
    void testMajFinDeTourCapAtMax() {
    player p = new player("Florian", "Grognak", "ADVENTURER", 100, new ArrayList<>());
    p.setHealthpoints(100);
    p.setCurrenthealthpoints(200);
    UpdatePlayer.majFinDeTour(p);
    assertThat(p.getCurrenthealthpoints(), is(100));
    }   
    @Test
    @DisplayName("majFinDeTour: DWARF < 50% HP sans Holy Elixir -> +2 HP")
    void testMajFinDeTourDwarfNoElixir() {
    player p = new player("Thorin", "Oakenshield", "DWARF", 100, new ArrayList<>());
    p.setHealthpoints(100);
    p.setCurrenthealthpoints(40);
    UpdatePlayer.majFinDeTour(p);
    assertThat(p.getCurrenthealthpoints(), is(41));
    }
   
    @Test
    @DisplayName("majFinDeTour: >= 50% HP ne change rien sauf cap à max")
    void testMajFinDeTourAtOrAboveHalf() {
    player p = new player("Eowyn", "Shieldmaiden", "ARCHER", 100, new ArrayList<>());
    p.setHealthpoints(100);
    p.setCurrenthealthpoints(50); // >= 50%
    UpdatePlayer.majFinDeTour(p);
    assertThat(p.getCurrenthealthpoints(), is(50));
   
    p.setCurrenthealthpoints(99);
    UpdatePlayer.majFinDeTour(p);
    assertThat(p.getCurrenthealthpoints(), is(99));
    }
   
    @Test
    @DisplayName("Affichage: contient quelques valeurs de capacités niveau 1")
    void testAffichageAbilitiesContainsValues() {
    ArrayList<String> inv = new ArrayList<>();
    player p = new player("Bob", "Hero", "ADVENTURER", 0, inv);
    String out = Affichage.afficherJoueur(p);
    assertThat(out.contains("ATK"), is(true));
    assertThat(out.contains("DEF"), is(true));
    assertThat(out.contains("CHA"), is(true));
    }
   
 
   
    @Test
    @DisplayName("Constructeur: classes valides initialisent les capacités niveau 1")
    void testConstructorValidClassesSeedAbilities() {
    player a = new player("A", "Ava", "ADVENTURER", 0, new ArrayList<>());
    player r = new player("R", "Arc", "ARCHER", 0, new ArrayList<>());
    player d = new player("D", "Durin", "DWARF", 0, new ArrayList<>());
    assertThat(a.getAbilities().isEmpty(), is(false));
    assertThat(r.getAbilities().isEmpty(), is(false));
    assertThat(d.getAbilities().isEmpty(), is(false));
    }
   
    @Test
    @DisplayName("ADVENTURER low HP niveau >=3 -> +2 HP net")
    void testAdventurerLowHpHighLevel() {
    player p = new player("Florian", "Grognak", "ADVENTURER", 0, new ArrayList<>());
    UpdatePlayer.addXp(p, 27); // niveau 3
    p.setHealthpoints(100);
    p.setCurrenthealthpoints(40);
    UpdatePlayer.majFinDeTour(p);
    assertThat(p.getCurrenthealthpoints(), is(42));
    }
   
    @Test
    @DisplayName("getAvatarClass retourne la classe")
    void testGetAvatarClass() {
    player p = new player("Florian", "Grognak", "ARCHER", 0, new ArrayList<>());
    assertThat(p.getAvatarClass(), is("ARCHER"));
    }
}
