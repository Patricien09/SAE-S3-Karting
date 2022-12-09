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

        <script type="text/javascript" src="script/moovePict.js"></script>
        
        <title> Boutique </title>
    </head>

    <body>
        <?php 
            require_once("header.php"); 
        ?>

        <?php
            //Si l'utilisateur n'est pas connecté, on affiche un message
            if (!isset($_SESSION["connected"]) || $_SESSION["connected"] == false) {
                echo "<div id='contenu'>
                    <h1>Boutique</h1>

                    <p id='blink'>VOUS DEVEZ VOUS CONNECTER POUR VOIR LA BOUTIQUE</p>
            
                    <img id='gifDrole0' src='logos/partyParrot.gif'>
                    <img id='gifDrole1' src='logos/partyParrot.gif'>
                </div>";
            }
        ?>
                    
        <?php
            //Si l'utilisateur est un admin, on affiche un message
            if (isset($_SESSION["connected"]) && $_SESSION["connected"] == true && $_SESSION["admin"] == true) {
                echo "<div id='contenu'>

                    <h1>Boutique</h1>
                    <p>Vous êtes un administrateur</p>
                </div>";
            }

            //Si l'utilisateur est un adhérent on affiche un message
            if (isset($_SESSION["connected"]) && $_SESSION["connected"] == true && $_SESSION["admin"] == false) {
                echo "<div id='contenu'>

                    <h1>Boutique</h1>
                    <p>Vous êtes un adhérent</p>
                </div>";
            }
        ?>

        <?php 
            require_once("footer.php");
        ?>
    </body>
    
</html>