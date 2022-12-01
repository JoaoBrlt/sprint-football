# Sprint Football

Temps consacré : 5 heures

## Dépendances

- [Docker Engine](https://docs.docker.com/engine/install/)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Lancement

1. Lancer la base de données et le serveur à l'aide de Docker Compose :

```bash
docker-compose up -d
```

> *Note*: Il est possible que le serveur redémarre plusieurs fois tant que la base de données n'est pas disponible.

2. Le serveur est disponible à l'adresse suivante : http://localhost:8080
3. La documentation de l'API est disponible à l'adresse suivante : http://localhost:8080/swagger-ui/index.html

## Choix réalisés

1. **Conception d'une application monolithique**

Dans le cas d'une petite application, une architecture monolithique offre de nombreux avantages.
Tout d'abord, cela simplifie le développement puisqu'il n'y a qu'une seule base de code.
De plus, cela simplifie l'opération de l'application puisqu'il n'y a qu'un seul service à déployer, surveiller, maintenir, etc.
Ces avantages sont d'autant plus importants si l'équipe chargée de l'application est petite puisque cela lui permet de se concentrer sur la production de valeur ajoutée.

2. **Utilisation d'objets de transfert de données (DTO)**

L'application utilise des objets de transfert de données entre la couche de présentation et la couche de traitement.
En effet, cela permet de découpler la couche de présentation de la couche de traitement et du domaine métier.
De plus, cela permet de fournir un contrat d'interface tout en garantissant une meilleure flexibilité à la couche de traitement.
De ce fait, cela résulte à la fois en un couplage faible et un transfert de données optimisé.

3. **Utilisation d'interfaces de services**

Les services de l'application implémentent des interfaces.
En effet, cela permet de fournir plusieurs implémentations de chaque service si cela devient nécessaire par la suite.
De plus, certaines fonctionnalités de Spring (telles que le mécanisme de cache, de retry, etc.) utilisent les proxy dynamiques fournis par Java.
Or, par défaut, la plateforme ne prend en charge la génération de proxy que pour les classes implémentant au moins une interface.
Il est possible d'utiliser CGLIB (Code Generation Library) pour contourner cette limitation, mais il s'agit d'un "hack" pour générer du bytecode au moment de l'exécution (toujours avec certaines limitations).
De ce fait, l'utilisation de ces interfaces permet de fournir une meilleure abstraction des services et une meilleure flexibilité pour les futurs développements.

4. **Gestion personnalisée des exceptions**

L'application fournit un gestionnaire d'exception personnalisé.
En effet, Spring Boot gère par défaut de nombreuses exceptions.
Cependant, son implémentation ne fournit pas des messages d'erreurs détaillés permettant de comprendre la source du problème.
L'utilisation de ce gestionnaire d'exception personnalisé permet de formaliser un format de message d'erreurs et de fournir plus de détails sur les différentes erreurs.

5. **Migration de la base de données à l'aide de Flyway**

L'application utilise Flyway, un outil de migration de base de données.
Cet outil utilise des scripts pour provisionner une base de données.
Il permet d'ordonner les scripts de déploiement, de versionner le modèle de données utilisé par une base de données, de recréer une base de données à partir de zéro, de faciliter les rollbacks en cas d'erreur, etc.
L'avantage principal de cet outil est qu'il garantit que les nouvelles releases sont toujours livrées avec l'état correspondant de la base de données.

6. **Documentation de l'API à l'aide d'OpenAPI / Swagger UI**

L'application fournie une documentation de l'API à l'aide d'OpenAPI et de Swagger UI.
Cette documentation offre de nombreux avantages.
En effet, elle permet de formaliser les spécifications de l'API sur les endpoints fournis, les données échangées, les paramètres disponibles, etc.
De plus, elle permet d'améliorer l'expérience des développeurs utilisant notre API.
Enfin, elle permet de diminuer le temps consacré à l'intégration des nouveaux développeurs utilisant notre API.

7. **Déploiement de l'application à l'aide de Docker**

L'application et l'infrastructure nécessaire à son fonctionnement (base de données) sont entièrement conteneurisées à l'aide de Docker.
En effet, Docker permet de faciliter les déploiements d'application et la gestion du dimensionnement de l'infrastructure.
De plus, Docker permet d'optimiser l’utilisation de l'infrastructure et de standardiser les environnements de déploiement.

8. **Mise en place d'un workflow GitHub Actions**

Le projet utilise un workflow GitHub Actions pour s'assurer que la compilation, les tests unitaires et les tests d'intégration passent avec succès après chaque modification (intégration continue).
Cela permet de réduire considérablement le nombre de risques potentiels puisque chaque modification est testée dans un environnement connu.

## Évolutions possibles

1. Mettre en place des outils de monitoring (e.g. Prometheus, Grafana, Loki, ELK)
2. Mettre en place des outils d'analyse de la qualité logicielle (e.g. Sonarqube, Pitest)
3. Mettre en place des outils d'analyse de sécurité applicative (e.g. Checkmarx, OWASP ZAP)
4. Respecter la contrainte HATEOAS de l'architecture d'application REST
5. Automatiser le déploiement de la solution (e.g. Ansible, CD, etc.)
6. etc.
