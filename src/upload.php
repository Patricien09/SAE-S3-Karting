<?php

session_start();

$target_dir = "users/" . $_SESSION["username"] . "/";
$target_file = $target_dir . "profil." . pathinfo($_FILES["file"]["name"], PATHINFO_EXTENSION);
$uploadOk = 1;

// Vérifie que l'image est bien une image
if (isset($_POST["submit"])) {
    $check = getimagesize($_FILES["file"]["tmp_name"]);
    if ($check !== false) {
        echo "Le fichier est une image - " . $check["mime"] . ".";
        $uploadOk = 1;
    } else {
        echo "Le fichier n'est pas une image.";
        $uploadOk = 0;
    }
}

// Vérifie la taille du fichier
if ($_FILES["file"]["size"] > 1000000) {
    echo "Le fichier est trop volumineux.";
    $uploadOk = 0;
}

// Vérifie l'état de $uploadOk
// S'il est égal à 0, une erreur s'est produite
if ($uploadOk == 0) {
    echo "Sorry, your file was not uploaded.";
} else {
    // Si tout est bon, on tente d'uploader le fichier dans le dossier spécifié
    if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file)) {
        echo "Le fichier " . htmlspecialchars(basename($_FILES["file"]["name"])) . " a été upload.";
    } else {
        echo "Erreur lors de l'upload du fichier.";
    }
}

header("Location: profil.php");

?>