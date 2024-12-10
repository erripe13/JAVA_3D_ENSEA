# Projet Aéroports : Documentation

## Introduction

Ce projet a été une vraie avancée pour moi. 
J'ai toujours eu du mal avec la programmation orientée objet, et au début, ça n'a pas été simple. 
J'ai mieux compris à quoi elle sert et pourquoi elle est si utile, surtout pour structurer un projet "complexe" comme celui-ci.

J'ai particulièrement bloqué sur deux points :
1. **L'intégration de l'API** : comprendre comment récupérer et utiliser les données a pris du temps.
2. **Le parsing JSON** : gérer les données imbriquées et parfois imprévisibles n'a pas été évident.

---

## Fonctionnement du Projet

### **Aeroport**
- Représente un aéroport avec son code IATA, son nom, son pays et ses coordonnées.
- Peut calculer la distance avec un autre aéroport.

### **Earth**
- Gère l'affichage de la Terre en 3D.
- Permet d'ajouter des marqueurs rouges ou jaunes pour représenter les aéroports.

### **Flight**
- Représente un vol avec son numéro, ses aéroports de départ et d'arrivée, et ses horaires.

### **JsonFlightFiller**
- Analyse les données JSON de l'API pour créer des objets `Flight`.
- Gère les erreurs pour éviter que le parsing ne plante si une donnée manque.

### **World**
- Stocke la liste des aéroports.
- Permet de trouver un aéroport par son code ou sa proximité géographique.

### **GatherFlightsTask**
- Récupère les vols depuis l'API pour un aéroport donné.
- Affiche les aéroports de départ comme des marqueurs jaunes.

### **Interface**
- L’application principale.
- Permet de cliquer sur la Terre, d'afficher les aéroports proches, et de voir les aéroports de départ associés.

---

## Conclusion

Ce projet m'a permis de vraiment comprendre l'intérêt de l'orienté objet. Même si ça n'a pas toujours été facile, en particulier avec l'API et le JSON, j'ai appris à mieux structurer mon code et à gérer des problèmes complexes étape par étape. 
