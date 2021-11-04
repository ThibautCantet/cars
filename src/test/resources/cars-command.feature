# language: fr
Fonctionnalité: Commandes concernant les voitures

  Scénario: Création d'une voiture de type Small
    Quand on crée une nouvelle voiture "twingo" à 10000€
    Alors on reçoit un Created
    Et on récupère les informations suivantes de la base
      | name   | price | category |
      | twingo | 10000 | Small    |

  Scénario: Création d'une voiture de type Medium
    Quand on crée une nouvelle voiture "megane" à 25000€
    Alors on reçoit un Created
    Et on récupère les informations suivantes de la base
      | name   | price | category |
      | megane | 25000 | Medium   |

  Scénario: Création d'une voiture de type Family
    Quand on crée une nouvelle voiture "espace" à 55000€
    Alors on reçoit un Created
    Et on récupère les informations suivantes de la base
      | name   | price | category |
      | espace | 55000 | Family   |

  Scénario: Mise à jour du prix
    Etant donné Les voitures suivantes
      | id | name   | price | category |
      | 1  | twingo | 10000 | Small    |
    Quand on met à jour le prix à 11000 de la voiture (1)
    Alors on reçoit un OK
    Et on récupère les informations suivantes de la base
      | name   | price | category |
      | twingo | 11000 | Small    |

  Scénario: Mise à jour du prix impossible à cause d'un changement de catégorie
    Etant donné Les voitures suivantes
      | name   | price | category |
      | twingo | 10000 | Small    |
    Quand on met à jour le prix à 50000 de la voiture (1)
    Alors on reçoit un non modifié
    Et on récupère les informations suivantes de la base
      | name   | price | category |
      | twingo | 10000 | Small    |

  Scénario: Dupliquer une voiture
    Etant donné Les voitures suivantes
      | name   | price | category |
      | twingo | 10000 | Small    |
      | espace | 55000 | Family   |
    Quand on duplique une "twingo" en "megane" à 25000€
    Alors on reçoit un Created
    Et on récupère les informations suivantes de la base
      | name   | price | category |
      | twingo | 10000 | Small    |
      | megane | 25000 | Medium   |
      | espace | 55000 | Family   |