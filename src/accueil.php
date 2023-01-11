<!doctype html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="style/main.css" type="text/css">
        <link rel="stylesheet" href="style/footer.css" type="text/css">
        <link rel="stylesheet" href="style/header.css" type="text/css">
        <title> Accueil </title>
    </head>
    <body>
        <?php 
            require_once("header.php"); 
        ?>
        <!--Présentation de notre site du club de karting -->

        <div id="contenu">
            <h1> Bienvenue sur le site du club de karting de la ville de Saint-Gorgon </h1><br>

            <h2 class="midTitle"> Vous voulez nous rejoindre ? </h2><br>

            <p class="par"> C'est simple, soit vous pouvez vous inscrire dans l'onglet <em>Inscription</em>, soit vous pouvez nous rejoindre sur notre circuit situé à Saint-Gorgon. </p><br>
            <p class="par"> Ici ce réunit chaque jour, plus de 20 pilotes de karting de tous niveaux. </p><br>

            <h2 class="midTitle"> Vous pouvez aussi regarder quelques vidéos de notre club </h2><br>
            
            <video class="vids" width = "400" height = "300" controls>
                <source src="im_vid/KartingPres.mp4" type="video/mp4">
            </video>

            <video class="vids" width = "400" height = "300" controls>
                <source src="im_vid/KartingPres3.mp4" type="video/mp4">
            </video>

            <video class="vids" width = "400" height = "300" controls>
                <source src="im_vid/KartingPres2.mp4" type="video/mp4">
            </video><br>

            <h2 class="midTitle">Vous pouvez aussi regarder quelques photos de notre club </h2><br>

            <img class="IM" src="im_vid/photoKarting.jpg" alt="Photo du karting">
            <div class="im-content">
                <p class="parIM">Championnat régional en KZ2</p>
            </div>
        
    
            <img class="IM" src="im_vid/photoKarting2.jpg" alt="Photo du karting">
            <div class="im-content">
                <p class="parIM">Circuit à la fin de la journée</p>
            </div>
        
    
            <img class="IM" src="im_vid/photoKarting3.jpg" alt="Photo du karting">
            <div class="im-content">
                <p class="parIM">Essai du nouveau PRAGA KA100</p>
            </div>
                
            
        </div>

        <?php
            require_once("footer.php");
        ?>
    </body>
</html>