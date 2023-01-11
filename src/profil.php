<?php 
    require_once("header.php"); 
    require_once("infoUtilisateur.php");
    //Si l'utilisateur est un admin, on affiche un message
    if (isset($_SESSION["connected"]) && $_SESSION["connected"] == true && $_SESSION["admin"] == true) {
        echo "<h1>Vous êtes un admin</h1>";
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