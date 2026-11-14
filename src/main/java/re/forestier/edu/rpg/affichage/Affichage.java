package re.forestier.edu.rpg.affichage;

import java.util.TreeMap;

import re.forestier.edu.rpg.joueur.player;

public class Affichage {

    public static String afficherJoueur(player player) {

        // Utilise StringBuilder plutôt que la concaténation de String pour des performances
        // optimales : évite la création de nombreux objets String intermédiaires
        
        StringBuilder result = new StringBuilder();

        result.append("Joueur ").append(player.getAvatarName()).append(" joué par ").append(player.getPlayerName());

        // Affiche le niveau calculé dynamiquement avec l'XP pour montrer la progression
        // L'XP totale est affichée pour que le joueur sache combien il lui reste avant le prochain niveau

        result.append("\nNiveau : ").append(player.retrieveLevel()).append(" (XP totale : ").append(player.getXp()).append(")");
        result.append("\n\nCapacités :");

        // Parcourt les capacités triées par nom : l'ordre d'une HashMap n'est pas garanti
        // Le format "nom : valeur" permet une lecture rapide des statistiques

        new TreeMap<>(player.getAbilities()).forEach((name, level) -> {
            result.append("\n   ").append(name).append(" : ").append(level);
        });
        result.append("\n\nInventaire :");

         // Affiche tous les objets pour que le joueur voie ce qu'il possède
        // L'inventaire est important car certains objets affectent les mécaniques de jeu

        player.getInventory().forEach(item -> {
            result.append("\n   ").append(item.toString());
        });

        return result.toString();
    }

    /**
     * Affiche les informations d'un joueur au format Markdown.
     * Utilise une structure simple : titres, sous-titres et listes.
     */
    public static String afficherJoueurMarkdown(player player) {
        StringBuilder result = new StringBuilder();

        // Titre principal
        result.append("# Joueur ").append(player.getAvatarName()).append("\n\n");

        // Joueur (italique)
        result.append("Joué par *").append(player.getPlayerName()).append("*\n\n");

        // Niveau
        result.append("## Niveau\n\n");
        result.append("Niveau : **").append(player.retrieveLevel()).append("** (XP totale : ").append(player.getXp()).append(")\n\n");

        // Capacités
        result.append("## Capacités\n\n");
        new TreeMap<>(player.getAbilities()).forEach((name, value) -> {
            result.append("* ").append(name).append(" : ").append(value).append("\n");
        });
        result.append("\n");

        // Inventaire
        result.append("## Inventaire\n\n");
        if (player.getInventory().isEmpty()) {
            result.append("*(Inventaire vide)*\n");
        } else {
            player.getInventory().forEach(item -> {
                result.append("* ").append(item.toString()).append("\n");
            });
        }
        result.append("\n");

        return result.toString();
    }
}

