# Kata RPG : refactoring et qualité logicielle

Projet de Master 1 (génie logiciel, ISIMA), réalisé en binôme. Le client fournit un code Java difficile à lire, qu'il faut faire évoluer sans le casser.

## Ce que nous avons réalisé

1. **Tests avant tout changement** : tests unitaires et tests d'approbation (ApprovalTests) pour figer le comportement du code d'origine, sans le modifier, jusqu'à une couverture complète avec JaCoCo.
2. **Tests de mutation** avec PIT, pour vérifier que les tests détectent vraiment les erreurs, en particulier sur les valeurs limites.
3. **Refactoring**, avec les tests figés :
   - patron Strategy pour la régénération de vie (une stratégie par classe de joueur) à la place des `if` imbriqués ;
   - services à responsabilité unique : expérience, capacités, santé ;
   - énumération `PlayerClass` à la place des chaînes de caractères ;
   - affichage avec `StringBuilder`.
4. **Nouvelles fonctionnalités en TDD** (un commit de tests, puis un commit de code) :
   - correction du bug de l'aventurier au niveau 2 ;
   - nouvelle classe de joueur : le Gobelin ;
   - vrais objets (`Item` : nom, description, poids, valeur), vente et poids maximal ;
   - affichage en Markdown.

## Résultats

| Mesure | À la fin du projet | Après le correctif |
|---|---|---|
| Tests | 70, tous verts | 73, tous verts |
| Couverture JaCoCo | 96 % des instructions, 95 % des branches | 96 % des instructions |
| Mutation PIT | 126 mutants tués sur 139 (91 %) | 118 sur 139 (85 %) |

## Correctif ultérieur

En reprenant le projet, j'ai trouvé une régression introduite par le refactoring : le cache de la table des capacités était partagé entre tous les joueurs, si bien qu'un joueur qui montait de niveau modifiait les capacités des nouveaux joueurs. Le correctif a été fait en TDD, en deux commits (d'abord les tests qui reproduisent le problème, puis la correction), et intégré à `main` par la [pull request #1](../../pull/1). L'affichage trie désormais les capacités par nom, car l'ordre d'une `HashMap` n'est pas garanti.

## Lancer le projet

```bash
./gradlew test               # tests
./gradlew jacocoTestReport   # couverture : build/reports/jacoco/test/html/index.html
./gradlew pitest             # mutation : build/reports/pitest/index.html
```

---

# Sujet d'origine

# Correctif - machine ISIMA

Sur les machines ISIMA, vous **devez** :

* Ouvrir un terminal
* Taper la commande suivante : `echo "JAVA_HOME=/usr/lib/jvm/jdk-21.0.4-oracle-x64" >> ~/.bashrc`
* Fermer le terminal (et vous pouvez ensuite ouvrir à nouveau des terminaux sur votre VSCode ou autre)


# M1 - Kata

## RPG Player Manager

Bienvenue sur RPG Player Manager, un superbe projet rédigé en Java... Enfin ça, c'est que vous a dit le client avant de
vous envoyer le code source.

### Partie 1 - Tests unitaires

En effet, le code de ce projet est moche, complexe, et surtout, moche. Et félicitations, vous devez coder une nouvelle
fonctionnalité à l'intérieur !

Avant de pouvoir implémenter la nouvelle fonctionnalité (qui sera décrite plus tard), vous allez en premier lieu vous
assurer de ne rien casser. Pour cela, vous allez devoir :

* Comprendre le code actuel (prenez des notes sur les règles métier, ça vous aidera) ;
* Rédiger des tests unitaires et globaux pour vous assurer que tout fonctionne correctement ;
* Le tout, sans toucher le code actuel.

JaCoCo et PIT seront vos amis (+ de détails dans le sujet de TP).

Attention, le client vous informe que pour une raison inconnue, le passage de l'aventurier au niveau 2 ne fonctionne
pas... Mais le niveau 3, 4 et 5, eux, fonctionnent.

Rédigez des tests unitaires et globaux de manière que JaCoCo vous indique 100% de code coverage. Des exemples vous
sont fournis dans `src/test/java/re.forestier.re`.

Durant cette phase, il est **strictement interdit** de toucher au code source !

### Partie 2 - Refactoring

Durant la phase de refactoring, vous êtes libres de toucher à ce que vous voulez dans le programme ! N'oubliez pas de
régulièrement commiter votre travail pour éviter de vous retrouver perdu dans un refactoring impossible...

L'objectif : rendre propre le code actuel sans casser vos tests actuels ! Dans cette phase, il est **strictement
interdit** de toucher aux tests !

Quelques pistes :

* Réduire la complexité cognitive ;
* Améliorer le système d'affichage, par exemple avec FreeMarker ou un StringBuilder (FreeMarker sera plus approprié) ;
* Est-il cohérent d'avoir ces trois classes dans `re.forestier.re.rpg` ? (probablement un redécoupage à faire) ;
* Consistence dans le code ;
* Gérer correctement les erreurs ;
* Utiliser des énumérations, regarder si les structures sont bien adaptées, etc.

N'hésitez pas à demander à votre chargé de TP des conseils au fur et à mesure.

Note : Durant cette phase, vous pouvez en profiter pour corriger le bug de l'aventurier niveau 2...

### Partie 3 - Ajouter les nouvelles fonctionnalités

En vous donnant le projet, le client vous a demandé d'implémenter de nouvelles fonctionnalités :

#### Ajout d'un nouveau rôle de joueur : Gobelin (`GOBLIN` en anglais) :

- Niveau 1 :
    - `INT` = 2
    - `ATK` = 2
    - `ALC` = 1
- Niveau 2 :
    - `ATF` = 3
    - `ALC` = 4
- Niveau 3 :
    - `VIS` = 1
- Niveau 4 :
    - `DEF` = 1
- Niveau 5 :
    - `DEF` = 2
    - `ATK` = 3

#### Améliorer la gestion des objets :

Actuellement, un objet, c'est une simple chaîne de caractères. L'idée est d'en faire un vrai objet, disposant :

- D'un nom ;
- D'une description ;
- D'un poids ;
- D'une valeur.
- Il sera possible pour un joueur de vendre un objet de son inventaire (prévoir une méthode `sell()`).
- Par ailleurs, le joueur a désormais un poids max qu'il peut porter. Si un joueur ajoute un objet à son inventaire qui
  fait dépasser le poids maximal, l'opération échoue.

Lors de l'ajout de ces fonctionnalités, vous penserez à créer les tests nécessaires.

#### Ajouter une nouvelle méthode d'affichage :

Actuellement, il est uniquement possible d'avoir le détail d'un joueur dans un affichage textuel. Le client souhaiterait
que vous ajoutiez un affichage complémentaire en Markdown. Markdown est un moyen simple de formater du texte. Voici
quelques règles de Markdown :

* `#` permet de créer un titre, `##` un sous-titre, et ainsi de suite ;
* Des caractères entre `*` formate un texte *en italique* ; et `**` formate un texte **en gras** ;
* Les listes doivent commencer par `*` au début de la ligne ;

Pour découvrir davantage la syntaxe Markdown, regardez le contenu de ce fichier !
