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
        <title> Vos Informations </title>
    </head>

    <body onload="movePicture();">
        <?php 
            require_once("header.php"); 
        ?>
        
        <?php
            require_once("infoUtilisateur.php");
            //Si l'utilisateur est un admin, on affiche un message
            if (isset($_SESSION["connected"]) && $_SESSION["connected"] == true && $_SESSION["admin"] == true) {
                //On affiche les informations de l'utilisateur
                infoUtilisateur();
            }

            //Si l'utilisateur est un adhérent on affiche un message
            if (isset($_SESSION["connected"]) && $_SESSION["connected"] == true && $_SESSION["admin"] == false) {
                //On affiche les informations de l'utilisateur
                infoUtilisateur();
            }
        ?>

        <?php 
            require_once("footer.php");
        ?>
    </body>
    
</html>