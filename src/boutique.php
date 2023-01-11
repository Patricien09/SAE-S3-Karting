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