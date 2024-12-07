# Rapport Adrien TP JAVA 
Le rapport de ce projet a pour but de résumer le parcours de l'élève tout au long de ce TP. Il partagera ses problèmes et ses réussites et conclura par un retour personnel sur l'ensemble.<br/>
Tout d'abord, j'ai voulu avoir une organisation de projet claire et structurée. 
Pour cela, j'ai utilisé les paquets JAVA qui ont deux principaux objectifs.
Une structure qui aide à décomposer mentalement un projet en parties plus petites, simplifiant la compréhension des lecteurs sur la façon dont les composants sont connectés et comment ils interagissent.
Nous avons aussi une "Avoiding name clashes" qui évite les collisions de noms lorsque d'autres projets définissent une classe portant le même nom.<br/>

Pour s'assurer de la bonne compréhension des "loggings" et de leurs bonnes lectures dans le code. J'ai remplacé les "Systeme.out" par des "LOG.fine".<br/>
```
 // Set the logging level to FINE
        LOG.setLevel(Level.FINE);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINE);
        LOG.addHandler(handler);
```
L'interface Log est très utile pour les messages de problème d'execption, exemple : 
```
SEVERE: An error occurred: {
  "error": {
    "code": "usage_limit_reached",
    "message": "Your monthly usage limit has been reached. Please upgrade your Subscription Plan."
  }
```
Dans la modifictaion des attentes du TP, j'ai remplacé le zoom sur la terre au clique gauche par un zoom à la molette, pour une meilleure utilisation. 
J'ai eu beaucoup de dificulté à comprendre la consigne concernant l'utilisation de l'API, je n'ai pas tout de suite compris qu'on devait utiliser l'aéroport d'arrivée.<br/>


Voici le diagramme de classe de mon projet :
![Class Diagram](doc/class_diagram.png)



# Conclusion

Le projet m'a permis de mieux comprendre l'utilisation de threads, l'utilisation d'une API en JAVA.