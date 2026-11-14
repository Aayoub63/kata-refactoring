package re.forestier.edu.rpg.joueur;

import java.util.ArrayList;
import java.util.HashMap;

import re.forestier.edu.rpg.capacites.UpdatePlayer;
import re.forestier.edu.rpg.objets.Item;

public class player {
    private String playerName;
    private String Avatar_name;
    private String AvatarClass;
    private Integer money;
    private int healthpoints;
    private int currenthealthpoints;
    private int xp;
    private HashMap<String, Integer> abilities;
    private ArrayList<Item> inventory;
    private int maxWeight;


    public player(String playerName, String avatar_name, String avatarClass, int money, ArrayList<String> inventory) {
        
        // Valide la classe du joueur avant de créer l'objet pour éviter les états invalides
        // Si la classe est invalide, on retourne silencieusement pour maintenir la compatibilité
        // avec les tests existants qui vérifient le comportement avec des classes invalides

        PlayerClass playerClass = PlayerClass.fromString(avatarClass);
        if (playerClass == null) {
            return;
        }

        this.playerName = playerName;
        this.Avatar_name = avatar_name;
        this.AvatarClass = avatarClass;
        this.money = money;
        this.maxWeight = 100; // Poids maximal par défaut
        this.healthpoints = 100; // Valeur par défaut
        this.currenthealthpoints = 100; // Valeur par défaut
        this.xp = 0; // Valeur par défaut
        
         // Convertit l'ancien inventaire de String en Item pour la compatibilité
        this.inventory = new ArrayList<>();
        if (inventory != null) {
            for (String itemName : inventory) {
                this.inventory.add(createItemFromString(itemName));
            }
        }

        // Initialise les capacités au niveau 1 car tous les joueurs commencent au niveau 1.
        // Copie de la map : sinon tous les joueurs partagent la table de référence
        // d'AbilityService, et un level-up modifie les capacités des autres joueurs.
        // playerClass.name() : nom normalisé ("archer" -> "ARCHER")

        this.abilities = new HashMap<>(UpdatePlayer.abilitiesPerTypeAndLevel().get(playerClass.name()).get(1));
    }

    /**
     * Crée un Item à partir d'un String pour la compatibilité avec l'ancien code.
     * Les objets existants sont convertis avec des valeurs par défaut.
     */
    private Item createItemFromString(String itemString) {
        // Parse le format "Nom : Description" ou utilise le nom seul
        String name;
        String description;
        if (itemString.contains(" : ")) {
            String[] parts = itemString.split(" : ", 2);
            name = parts[0];
            description = parts[1];
        } else {
            name = itemString;
            description = "Un objet mystérieux";
        }
        // Valeurs par défaut pour les anciens objets
        return new Item(name, description, 1, 10);
    }
    
    /**
     * Calcule le poids total de l'inventaire.
     */
    public int getTotalWeight() {
        return inventory.stream().mapToInt(Item::getWeight).sum();
    }
    
    /**
     * Ajoute un objet à l'inventaire si le poids maximal n'est pas dépassé.
     * @param item L'objet à ajouter
     * @return true si l'objet a été ajouté, false si le poids maximal serait dépassé
     */
    public boolean addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("L'objet ne peut pas être null");
        }
        if (getTotalWeight() + item.getWeight() > maxWeight) {
            return false;
        }
        inventory.add(item);
        return true;
    }
    
    /**
     * Vend un objet de l'inventaire et ajoute sa valeur à l'argent du joueur.
     * @param item L'objet à vendre
     * @throws IllegalArgumentException si l'objet n'est pas dans l'inventaire
     */
    public void sell(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("L'objet ne peut pas être null");
        }
        if (!inventory.remove(item)) {
            throw new IllegalArgumentException("L'objet n'est pas dans l'inventaire");
        }
        addMoney(item.getValue());
    }
    
    /**
     * Vend un objet de l'inventaire par son index.
     * @param index L'index de l'objet dans l'inventaire
     * @throws IndexOutOfBoundsException si l'index est invalide
     */
    public void sell(int index) {
        if (index < 0 || index >= inventory.size()) {
            throw new IndexOutOfBoundsException("Index invalide : " + index);
        }
        Item item = inventory.remove(index);
        addMoney(item.getValue());
    }
    
    /**
     * Trouve un objet dans l'inventaire par son nom.
     * @param name Le nom de l'objet à chercher
     * @return L'objet trouvé ou null si non trouvé
     */
    public Item findItemByName(String name) {
        return inventory.stream()
                .filter(item -> item.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
    
    public int getMaxWeight() {
        return maxWeight;
    }
    
    public void setMaxWeight(int maxWeight) {
        if (maxWeight < 0) {
            throw new IllegalArgumentException("Le poids maximal ne peut pas être négatif");
        }
        this.maxWeight = maxWeight;
    }

    public String getAvatarClass () {
        return AvatarClass;
    }

    public void removeMoney(int amount) throws IllegalArgumentException {

        // Empêche les transactions qui créeraient une dette, car l'argent ne peut pas être négatif
        // dans ce système de jeu (règle métier : pas de crédit/emprunt)

        if (money < amount) {
            throw new IllegalArgumentException("Player can't have a negative money!");
        }

        money -= amount;
    }


    public void addMoney(int amount) {
        money += amount;
    }



    public int retrieveLevel() {

        // Calcule le niveau dynamiquement à partir de l'XP pour éviter les incohérences
        // Les seuils XP sont définis selon la progression du jeu : chaque niveau nécessite
        // plus d'XP que le précédent (10, 17, 30, 54, etc.) pour créer une courbe de progression
        
        if (xp < 10) {
            return 1;
        }
        else if (xp < 27) {
            return 2;
        }
        if (xp < 57) {
            return 3;
        }
        if (xp < 111) {
            return 4;
        }
        return 5;
    }



    public int getXp() {
        return this.xp;
    }

    public void setXp(int xp) {
        if (xp < 0) {
            throw new IllegalArgumentException("L'XP ne peut pas être négative");
        }
        this.xp = xp;
    }

    // Getters
    public String getPlayerName() {
        return playerName;
    }

    public String getAvatarName() {
        return Avatar_name;
    }

    public Integer getMoney() {
        return money;
    }

    public int getHealthpoints() {
        return healthpoints;
    }
    
    public void setHealthpoints(int healthpoints) {
        if (healthpoints < 0) {
            throw new IllegalArgumentException("Les points de vie maximum ne peuvent pas être négatifs");
        }
        this.healthpoints = healthpoints;
    }
    
    public int getCurrenthealthpoints() {
        return currenthealthpoints;
    }
    
    public void setCurrenthealthpoints(int currenthealthpoints) {
        this.currenthealthpoints = currenthealthpoints;
    }
    
    public HashMap<String, Integer> getAbilities() {
        return abilities;
    }
    
    public ArrayList<Item> getInventory() {
        return inventory;
    }
    
    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

}