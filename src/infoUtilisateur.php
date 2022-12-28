<?php
//fonction qui recupere les informations de l'utilisateur
function infoUtilisateur()
{
    require_once("connectbd.php");

    //Afficher les infos de l'utilisateur connecté avec une requete sql et en utilisant PDO et en l'affichant en HTML

    $connexion = connect_bd();
    $sql = "SELECT * FROM `personne` WHERE `mail` = :mail";
    $stmt = $connexion->prepare($sql);
    $values = [
        ":mail" => $_SESSION["username"]
    ];

    $res = $stmt->execute($values);
    if (!$res) {
        echo "Problème d'accès à la bdd";
    } else {
        if ($stmt->rowCount() == 0) {
?>
            <script>
                alert("Erreur de connexion");
            </script>
        <?php
        } else {
            $row = $stmt->fetch();
        ?>

            <body>
                <div id="contenu">
                    <h1>Informations personnelles</h1>
                    <p>Photo de profil :
                        <?php
                        // Vérifie si un dossier au nom de l'utilisateur existe, sinon le crée
                        if (!file_exists("users/" . $_SESSION["username"])) {
                            mkdir("users/" . $_SESSION["username"]);
                        }

                        // Vérifie qu'il existe une image dans ce dossier, sinon affiche une image par défaut
                        if ($result = glob("users/" . $_SESSION["username"] . "/profil.*")) {
                            // Ajoute le paramètre t=... pour éviter que le navigateur utilise une image en cache
                            echo "<img src='" . $result[0] . "?t=" . time() . "' alt='Photo de profil' width='200' height='200'>";
                        } else {
                            echo "<img src='logos/kart.jpg' alt='Photo de profil' width='200' height='200'>";
                        }

                        // Ajoute un bouton permettant de choisir une image
                        ?>
                        <form action="upload.php" method="post" enctype="multipart/form-data">
                            <div class="modal-body">
                                <div class="form-group">
                                    <input type="file" name="file" id="file" accept="image/*">
                                </div>
                                <div class="form-group">
                                    <input type="reset" name="reset" class="btn btn-secondary">
                                    <input type="submit" name="submit" class="btn btn-primary">
                                </div>
                            </div>
                        </form>

                    </p>
                    <p>Nom : <?php echo $row["nom"]; ?></p>
                    <p>Prénom : <?php echo $row["prenom"]; ?></p>
                    <p>Téléphone : <?php echo $row["telephone"]; ?></p>
                    <p>Mail : <?php echo $row["mail"]; ?></p>
                    <p>Document administratif : <?php echo $row["mail"]; ?></p>
                </div>
            </body>
<?php
        }
    }
}
?>