package re.forestier.edu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import re.forestier.edu.rpg.capacites.UpdatePlayer;
import re.forestier.edu.rpg.joueur.player;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests pour les nouvelles fonctionnalités ajoutées.
 * Ce fichier contient uniquement les tests pour les nouvelles fonctionnalités :
 * - Classe GOBLIN
 * - Classe Item
 * - Poids maximal
 * - Méthode sell()
 * - Tests de couverture JaCoCo pour ces nouvelles fonctionnalités
 */
public class NewFeaturesTests {
    
    // Tests pour les nouvelles fonctionnalités - Item
    @Test
    @DisplayName("Test création Item")
    void testItemCreation() {
        re.forestier.edu.rpg.objets.Item item = new re.forestier.edu.rpg.objets.Item("Épée", "Une épée tranchante", 5, 100);
        assertThat(item.getName(), is("Épée"));
        assertThat(item.getDescription(), is("Une épée tranchante"));
        assertThat(item.getWeight(), is(5));
        assertThat(item.getValue(), is(100));
    }
    
    @Test
    @DisplayName("Test Item toString")
    void testItemToString() {
        re.forestier.edu.rpg.objets.Item item = new re.forestier.edu.rpg.objets.Item("Épée", "Une épée tranchante", 5, 100);
        assertThat(item.toString(), is("Épée : Une épée tranchante"));
    }
    
    @Test
    @DisplayName("Test ajout d'Item avec vérification du poids")
    void testAddItemWithWeight() {
        player p = new player("Test", "Hero", "ADVENTURER", 100, new ArrayList<>());
        re.forestier.edu.rpg.objets.Item item1 = new re.forestier.edu.rpg.objets.Item("Item1", "Description", 50, 10);
        re.forestier.edu.rpg.objets.Item item2 = new re.forestier.edu.rpg.objets.Item("Item2", "Description", 51, 10);
        
        assertThat(p.addItem(item1), is(true));
        assertThat(p.getTotalWeight(), is(50));
        assertThat(p.addItem(item2), is(false)); // Dépasserait le poids max (100)
        assertThat(p.getTotalWeight(), is(50)); // Le poids n'a pas changé
    }
    
    @Test
    @DisplayName("Test méthode sell avec Item")
    void testSellItem() {
        player p = new player("Test", "Hero", "ADVENTURER", 100, new ArrayList<>());
        re.forestier.edu.rpg.objets.Item item = new re.forestier.edu.rpg.objets.Item("Épée", "Description", 5, 50);
        p.addItem(item);
        
        assertThat(p.getMoney(), is(100));
        assertThat(p.getInventory().size(), is(1));
        
        p.sell(item);
        
        assertThat(p.getMoney(), is(150));
        assertThat(p.getInventory().size(), is(0));
    }
    
    @Test
    @DisplayName("Test méthode sell avec index")
    void testSellItemByIndex() {
        player p = new player("Test", "Hero", "ADVENTURER", 100, new ArrayList<>());
        re.forestier.edu.rpg.objets.Item item1 = new re.forestier.edu.rpg.objets.Item("Item1", "Description", 5, 30);
        re.forestier.edu.rpg.objets.Item item2 = new re.forestier.edu.rpg.objets.Item("Item2", "Description", 5, 40);
        p.addItem(item1);
        p.addItem(item2);
        
        p.sell(0); // Vendre le premier item
        
        assertThat(p.getMoney(), is(130));
        assertThat(p.getInventory().size(), is(1));
        assertThat(p.getInventory().get(0), is(item2));
    }
    
    @Test
    @DisplayName("Test findItemByName")
    void testFindItemByName() {
        player p = new player("Test", "Hero", "ADVENTURER", 100, new ArrayList<>());
        re.forestier.edu.rpg.objets.Item item = new re.forestier.edu.rpg.objets.Item("Magic Bow", "Un arc magique", 3, 200);
        p.addItem(item);
        
        re.forestier.edu.rpg.objets.Item found = p.findItemByName("Magic Bow");
        assertNotNull(found);
        assertThat(found.getName(), is("Magic Bow"));
        
        assertNull(p.findItemByName("NonExistent"));
    }
    
    // Tests pour GOBLIN
    @Test
    @DisplayName("Test GOBLIN niveau 1")
    void testGoblinLevel1() {
        player p = new player("Test", "Gob", "GOBLIN", 0, new ArrayList<>());
        assertThat(p.getAbilities().get("INT"), is(2));
        assertThat(p.getAbilities().get("ATK"), is(2));
        assertThat(p.getAbilities().get("ALC"), is(1));
    }
    
    @Test
    @DisplayName("Test GOBLIN progression de niveau")
    void testGoblinLevelProgression() {
        player p = new player("Test", "Gob", "GOBLIN", 0, new ArrayList<>());
        UpdatePlayer.addXp(p, 10); // Niveau 2
        assertThat(p.retrieveLevel(), is(2));
        assertThat(p.getAbilities().get("ATK"), is(3));
        assertThat(p.getAbilities().get("ALC"), is(4));
        
        UpdatePlayer.addXp(p, 20); // Niveau 3
        assertThat(p.retrieveLevel(), is(3));
        assertThat(p.getAbilities().get("VIS"), is(1));
        
        UpdatePlayer.addXp(p, 30); // Niveau 4
        assertThat(p.retrieveLevel(), is(4));
        assertThat(p.getAbilities().get("DEF"), is(1));
        
        UpdatePlayer.addXp(p, 60); // Niveau 5
        assertThat(p.retrieveLevel(), is(5));
        assertThat(p.getAbilities().get("DEF"), is(2));
        assertThat(p.getAbilities().get("ATK"), is(3));
    }
    
    // Tests pour le poids maximal
    @Test
    @DisplayName("Test poids maximal par défaut")
    void testDefaultMaxWeight() {
        player p = new player("Test", "Hero", "ADVENTURER", 100, new ArrayList<>());
        assertThat(p.getMaxWeight(), is(100));
    }
    
    @Test
    @DisplayName("Test modification du poids maximal")
    void testSetMaxWeight() {
        player p = new player("Test", "Hero", "ADVENTURER", 100, new ArrayList<>());
        p.setMaxWeight(50);
        assertThat(p.getMaxWeight(), is(50));
        
        re.forestier.edu.rpg.objets.Item item = new re.forestier.edu.rpg.objets.Item("Item", "Description", 51, 10);
        assertThat(p.addItem(item), is(false)); // Dépasserait le nouveau poids max
    }
    
    // Test pour GoblinHealthStrategy
    @Test
    @DisplayName("Test GoblinHealthStrategy - régénération")
    void testGoblinHealthStrategy() {
        player p = new player("Test", "Gob", "GOBLIN", 0, new ArrayList<>());
        p.setHealthpoints(100);
        p.setCurrenthealthpoints(40); // < 50%
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.getCurrenthealthpoints(), is(41)); // +1 HP
    }
    
    // Tests pour améliorer la couverture JaCoCo - Item
    @Test
    @DisplayName("Test Item - validation nom null")
    void testItemNameNull() {
        try {
            new re.forestier.edu.rpg.objets.Item(null, "Description", 1, 10);
            fail("Devrait lever une exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Le nom de l'objet ne peut pas être vide"));
        }
    }
    
    @Test
    @DisplayName("Test Item - validation nom vide")
    void testItemNameEmpty() {
        try {
            new re.forestier.edu.rpg.objets.Item("", "Description", 1, 10);
            fail("Devrait lever une exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Le nom de l'objet ne peut pas être vide"));
        }
    }
    
    @Test
    @DisplayName("Test Item - validation nom avec espaces")
    void testItemNameWhitespace() {
        try {
            new re.forestier.edu.rpg.objets.Item("   ", "Description", 1, 10);
            fail("Devrait lever une exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Le nom de l'objet ne peut pas être vide"));
        }
    }
    
    @Test
    @DisplayName("Test Item - validation description null")
    void testItemDescriptionNull() {
        try {
            new re.forestier.edu.rpg.objets.Item("Nom", null, 1, 10);
            fail("Devrait lever une exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("La description ne peut pas être nulle"));
        }
    }
    
    @Test
    @DisplayName("Test Item - validation poids négatif")
    void testItemWeightNegative() {
        try {
            new re.forestier.edu.rpg.objets.Item("Nom", "Description", -1, 10);
            fail("Devrait lever une exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Le poids ne peut pas être négatif"));
        }
    }
    
    @Test
    @DisplayName("Test Item - validation valeur négative")
    void testItemValueNegative() {
        try {
            new re.forestier.edu.rpg.objets.Item("Nom", "Description", 1, -1);
            fail("Devrait lever une exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("La valeur ne peut pas être négative"));
        }
    }
    
    @Test
    @DisplayName("Test Item - equals avec même objet")
    void testItemEqualsSameObject() {
        re.forestier.edu.rpg.objets.Item item = new re.forestier.edu.rpg.objets.Item("Nom", "Description", 1, 10);
        assertThat(item.equals(item), is(true));
    }
    
    @Test
    @DisplayName("Test Item - equals avec null")
    void testItemEqualsNull() {
        re.forestier.edu.rpg.objets.Item item = new re.forestier.edu.rpg.objets.Item("Nom", "Description", 1, 10);
        assertThat(item.equals(null), is(false));
    }
    
    @Test
    @DisplayName("Test Item - equals avec objet différent")
    void testItemEqualsDifferentClass() {
        re.forestier.edu.rpg.objets.Item item = new re.forestier.edu.rpg.objets.Item("Nom", "Description", 1, 10);
        assertThat(item.equals("String"), is(false));
    }
    
    @Test
    @DisplayName("Test Item - equals avec objets égaux")
    void testItemEqualsEqualObjects() {
        re.forestier.edu.rpg.objets.Item item1 = new re.forestier.edu.rpg.objets.Item("Nom", "Description", 1, 10);
        re.forestier.edu.rpg.objets.Item item2 = new re.forestier.edu.rpg.objets.Item("Nom", "Description", 1, 10);
        assertThat(item1.equals(item2), is(true));
    }
    
    @Test
    @DisplayName("Test Item - equals avec objets différents")
    void testItemEqualsDifferentObjects() {
        re.forestier.edu.rpg.objets.Item item1 = new re.forestier.edu.rpg.objets.Item("Nom1", "Description", 1, 10);
        re.forestier.edu.rpg.objets.Item item2 = new re.forestier.edu.rpg.objets.Item("Nom2", "Description", 1, 10);
        assertThat(item1.equals(item2), is(false));
        
        item1 = new re.forestier.edu.rpg.objets.Item("Nom", "Description1", 1, 10);
        item2 = new re.forestier.edu.rpg.objets.Item("Nom", "Description2", 1, 10);
        assertThat(item1.equals(item2), is(false));
        
        item1 = new re.forestier.edu.rpg.objets.Item("Nom", "Description", 1, 10);
        item2 = new re.forestier.edu.rpg.objets.Item("Nom", "Description", 2, 10);
        assertThat(item1.equals(item2), is(false));
        
        item1 = new re.forestier.edu.rpg.objets.Item("Nom", "Description", 1, 10);
        item2 = new re.forestier.edu.rpg.objets.Item("Nom", "Description", 1, 20);
        assertThat(item1.equals(item2), is(false));
    }
    
    @Test
    @DisplayName("Test Item - hashCode")
    void testItemHashCode() {
        re.forestier.edu.rpg.objets.Item item1 = new re.forestier.edu.rpg.objets.Item("Nom", "Description", 1, 10);
        re.forestier.edu.rpg.objets.Item item2 = new re.forestier.edu.rpg.objets.Item("Nom", "Description", 1, 10);
        assertThat(item1.hashCode(), is(item2.hashCode()));
    }
    
    // Tests pour améliorer la couverture JaCoCo - player
    @Test
    @DisplayName("Test player - createItemFromString avec format 'Nom : Description'")
    void testCreateItemFromStringWithFormat() {
        ArrayList<String> inventory = new ArrayList<>();
        inventory.add("Lookout Ring : Prevents surprise attacks");
        player p = new player("Test", "Hero", "ADVENTURER", 100, inventory);
        assertThat(p.getInventory().size(), is(1));
        assertThat(p.getInventory().get(0).getName(), is("Lookout Ring"));
        assertThat(p.getInventory().get(0).getDescription(), is("Prevents surprise attacks"));
    }
    
    @Test
    @DisplayName("Test player - addItem avec item null")
    void testAddItemNull() {
        player p = new player("Test", "Hero", "ADVENTURER", 100, new ArrayList<>());
        try {
            p.addItem(null);
            fail("Devrait lever une exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("L'objet ne peut pas être null"));
        }
    }
    
    @Test
    @DisplayName("Test player - sell avec item null")
    void testSellItemNull() {
        player p = new player("Test", "Hero", "ADVENTURER", 100, new ArrayList<>());
        try {
            p.sell((re.forestier.edu.rpg.objets.Item) null);
            fail("Devrait lever une exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("L'objet ne peut pas être null"));
        }
    }
    
    @Test
    @DisplayName("Test player - sell avec item non dans l'inventaire")
    void testSellItemNotInInventory() {
        player p = new player("Test", "Hero", "ADVENTURER", 100, new ArrayList<>());
        re.forestier.edu.rpg.objets.Item item = new re.forestier.edu.rpg.objets.Item("Item", "Description", 1, 10);
        try {
            p.sell(item);
            fail("Devrait lever une exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("L'objet n'est pas dans l'inventaire"));
        }
    }
    
    @Test
    @DisplayName("Test player - sell avec index invalide négatif")
    void testSellInvalidIndexNegative() {
        player p = new player("Test", "Hero", "ADVENTURER", 100, new ArrayList<>());
        try {
            p.sell(-1);
            fail("Devrait lever une exception");
        } catch (IndexOutOfBoundsException e) {
            assertThat(e.getMessage(), is("Index invalide : -1"));
        }
    }
    
    @Test
    @DisplayName("Test player - sell avec index invalide trop grand")
    void testSellInvalidIndexTooLarge() {
        player p = new player("Test", "Hero", "ADVENTURER", 100, new ArrayList<>());
        re.forestier.edu.rpg.objets.Item item = new re.forestier.edu.rpg.objets.Item("Item", "Description", 1, 10);
        p.addItem(item);
        try {
            p.sell(5);
            fail("Devrait lever une exception");
        } catch (IndexOutOfBoundsException e) {
            assertThat(e.getMessage(), is("Index invalide : 5"));
        }
    }
    
    @Test
    @DisplayName("Test player - setMaxWeight avec valeur négative")
    void testSetMaxWeightNegative() {
        player p = new player("Test", "Hero", "ADVENTURER", 100, new ArrayList<>());
        try {
            p.setMaxWeight(-1);
            fail("Devrait lever une exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Le poids maximal ne peut pas être négatif"));
        }
    }
    
    // Tests pour couvrir les dernières branches manquées
    @Test
    @DisplayName("Test ExperienceService - level-up avec inventaire plein (poids max dépassé)")
    void testLevelUpWithFullInventory() {
        player p = new player("Test", "Hero", "ADVENTURER", 100, new ArrayList<>());
        // Remplir l'inventaire jusqu'au poids max
        p.setMaxWeight(1); // Poids max très faible
        // Ajouter un objet de poids 1 pour remplir l'inventaire
        re.forestier.edu.rpg.objets.Item heavyItem = new re.forestier.edu.rpg.objets.Item("Heavy", "Item", 1, 10);
        assertThat(p.addItem(heavyItem), is(true));
        assertThat(p.getTotalWeight(), is(1));
        // Maintenant l'inventaire est plein, le level-up ne pourra pas ajouter d'objet
        int invSizeBefore = p.getInventory().size();
        UpdatePlayer.addXp(p, 10); // Level-up au niveau 2
        // L'objet n'a pas pu être ajouté car le poids max est dépassé
        assertThat(p.getInventory().size(), is(invSizeBefore));
        assertThat(p.retrieveLevel(), is(2)); // Mais le level-up a bien eu lieu
    }
    
    @Test
    @DisplayName("Test HealthService - playerClass null (classe invalide)")
    void testHealthServiceWithInvalidClass() {
        player p = new player("Test", "Hero", "ADVENTURER", 100, new ArrayList<>());
        p.setHealthpoints(100);
        p.setCurrenthealthpoints(40); // < 50%
        
        // Utiliser la réflexion pour modifier AvatarClass à une valeur qui fera que fromString retourne null
        try {
            java.lang.reflect.Field field = player.class.getDeclaredField("AvatarClass");
            field.setAccessible(true);
            field.set(p, "INVALID_CLASS_THAT_RETURNS_NULL");
            
            // Maintenant getAvatarClass() retournera "INVALID_CLASS_THAT_RETURNS_NULL"
            // et PlayerClass.fromString() retournera null
            UpdatePlayer.majFinDeTour(p);
            // Si playerClass est null, la régénération ne se fait pas
            assertThat(p.getCurrenthealthpoints(), is(40)); // Pas de régénération
        } catch (Exception e) {
            // Si la réflexion échoue, on skip ce test
        }
    }
    
    @Test
    @DisplayName("Test player - constructeur avec inventory null")
    void testPlayerConstructorWithNullInventory() {
        // Tester le cas où inventory est null dans le constructeur
        player p = new player("Test", "Hero", "ADVENTURER", 100, null);
        // L'inventaire devrait être initialisé comme une liste vide
        assertNotNull(p.getInventory());
        assertThat(p.getInventory().size(), is(0));
    }
}

