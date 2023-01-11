<?php 
    require_once("header.php"); 
    require_once("infoUtilisateur.php");
    //Si l'utilisateur est un admin, on affiche un message
    if (isset($_SESSION["connected"]) && $_SESSION["connected"] == true) {
        //On affiche les informations de l'utilisateur
        infoUtilisateur();
    }
?>

<?php 
    require_once("footer.php");
?>