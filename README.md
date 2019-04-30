# MailRobot
Application cliente avec implémentation partielle du protocole SMTP

Auteurs : doriane Tedongmo kaffo

## Description de l'application

L'idée de notre application est d'écrire un agent logiciel qui va permettre de faire les plaisanteries. L'objectif est de se familiariser avec le protocole de messagerie SMTP et de voir à quel point on arrive facilement à ouvrir une communication avec TCP avec un serveur SMTP que si on connait la spécification de ce protocole. On arrive à demander au serveur d'envoyer les massages électroniques à des adresses emails qu'on lui donne.


## Instructions pour configurer un faux serveur SMTP (avec Docker)

L'utilisation de notre logiciel nécessite :
Pour tester le logiciel, vous avez besoin d'une `connexion internet` et d'un `docker machine` qui a pour IP `192.168.99.100` et de l'IDE ìntelliJ` installé sur votre ordinateur
- Vous devez cloner le projet
- Ouvrir votre terminal docker
- Vous placer sur le dossier docker (MailRobot/LabSMTP/docker)
- Taper les commandes suivantes :  `docker build --tag "nom_repertoire:nom_image" .`  
`docker images` pour voire l'id de l'image
`docker run -p 8080:8080 -p 2525:2525 --name nom_image -d nom_image`

- Sur le navigateur `192.168.99.100:8080`
- Sur IntelliJ lancer le projet depuis FormelMail.java
- Actualiser le navigateur et vous verrez les mails envoyés sur l'interface de MockMock


#### Remarque :   
Vous trouvez 
`Un testDouble ou server MockMock` sur https://github.com/tweakers-dev/MockMock . Il contient un jar exécutatble (vous ne pouvez le télécharger que si celui sur le repos n'est pas fonctionnel. Si non pas besoin).

## Des instructions claires et simples pour configurer votre outil et lancer une campagne de blague

L’utilisateur de notre application va juste définir une liste de victime (les adresses électroniques) dans un fichier `victim.utf8` et après notre application va générer des groupes de victimes (3,  5 personnes comme on le souhaite) dans le fichier  `configurations.properties`. Dans un groupe on a un expéditeur et n récepteurs avec un message (dans le fichier `message` que l’utilisateur spécifique son contenue. Notre programme va se connecter à un serveur SMTP et lui demander d’envoyer un message électronique, les récepteurs vont recevoir sans se rendre compte que c’est une autre victime (qui joue le rôle d’expéditeur) qui l’a envoyé alors qu’elle ne le sait même pas.


## description de l’implémentation

Pour concevoir notre application, nous avons créé :
- Le fichier de configuration pour entrer dans notre projet (il contient le l'adresse du server, le port et le nombre de groupe)
- Les fichiers qui contiennent chacun la liste de victim et de message
- Un interface configuration pour ressortie un résultat des données entrées et qui ont été traitées.
- Une classe Configuration : on lit les fichiers de victims, message et la configuration pour pouvoir paramétrer la connection au serveur
- La methode loadPropertie permet de lire les propriétés depuis le fichier de configuration
- La methode LoadDataFromFile permet de lire les fichiers et retourne une liste des adresses des victimes.
- La classe loadAddressFromFile permet de lire les adresses (composé du nom et prénom de l'utilisateur ainsi que @heig-vd.ch). Un peu limité parce que je me suis basé sur la forme de l'adresse email de la heig-vd. Cette addresse me retourne le nom et le prénom.
-  après on les geter getServerAddress, getServerport, getVictim, getNumberOfGroup et getMessage
-  Notre model qui contient :
- la classe Person qui contient l'address, le firstName et le lastName des victims
-  La classe prank qui est constitué de la liste des victimes et messages et du senderMail. Pour éviter que le senderMail reçoit le message, on l'extrait.
- La classe generate prank : qui permet de générer les plaisanteries. De ce fait, nous avons besoin de la liste de victimes. Elle retourne la liste de plaisanteries dont chaque plaisanterie varie en fonction du groupe (avec les methodes generatePranks et generateGroup)
- La classe MailAdress : qui carcterise tout ce qui est l’entête d’un message
- Une classe formelMail qui est le point d'entrée de mon application. Elle contient les commandes SMTP, la méthode sendPrank pour l'envoie des plaisanteries. La méthode send qui permet d'initier la communication et faire les différents échanges. La méthode getResponse permet de recevoir la réponse du serveur et d'extraire les trois premiers caractères pour voir si la réponse est positive ou pas. La méthode sendMail pour l'envoie du mail avec le format SMTP. 

