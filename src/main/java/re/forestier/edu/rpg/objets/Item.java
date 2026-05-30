package re.forestier.edu.rpg.objets;

public class Item {
    private final String name;
    private final String description;
    private final int weight;
    private final int value;
    
    public Item(String name, String description, int weight, int value) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'objet ne peut pas être vide");
        }
        if (description == null) {
            throw new IllegalArgumentException("La description ne peut pas être nulle");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Le poids ne peut pas être négatif");
        }
        if (value < 0) {
            throw new IllegalArgumentException("La valeur ne peut pas être négative");
        }
        
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.value = value;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getWeight() {
        return weight;
    }
    
    public int getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return name + " : " + description;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item item = (Item) obj;
        return name.equals(item.name) && 
               description.equals(item.description) && 
               weight == item.weight && 
               value == item.value;
    }
    
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + weight;
        result = 31 * result + value;
        return result;
    }
}

