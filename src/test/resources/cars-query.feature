# language: fr
Fonctionnalité: Lister les voitures

  Scénario: Afficher une voiture par nom
    Etant donné Les voitures suivantes
      | name   | price |
      | twingo | 10000 |
      | espace | 55000 |
    Quand on affiche une "twingo"
    Alors on récupère les informations suivantes du body
      | name   | category |
      | twingo | Small    |