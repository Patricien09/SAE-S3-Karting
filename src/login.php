<?php
    session_start();
    require_once("connectbd.php");

    //Si le formulaire a été envoyé
    if (isset($_POST["username"]) && isset($_POST["password"])) {
        $_SESSION["username"] = $_POST["username"];
        $_SESSION["password"] = $_POST["password"];

        $connexion = connect_bd();

        //Récupère toutes les lignes correspondantes à l'utilisateur
        $sql = "SELECT * FROM `personne` WHERE `mail` = :mail AND `mdp` = :mdp";

        $stmt = $connexion->prepare($sql);

        $stmt->bindParam(':mail', $_SESSION["username"]);
        $stmt->bindParam(':mdp', $_SESSION["password"]);

        $res = $stmt->execute();

        if (!$res) {
            echo "Problème d'accès à la bdd";
        } else {
            //Si la personne n'exsite pas
            if ($stmt->rowCount() == 0) {
                ?>

                <script>
                    alert("Erreur de connexion");
                </script>

            <?php
            } else {
                $_SESSION["connected"] = true;
                //recupere l'id de l'admin dans la table admin
                $sql = "SELECT `Personne_idPersonne` FROM `admin` WHERE `Personne_idPersonne` = :id";
                $stmt2 = $connexion->prepare($sql);
                $stmt2 -> bindParam(':id', $stmt->fetch()["idPersonne"]);

                $res = $stmt2->execute();
                //script pour savoir si l'utilisateur est un admin ou non
                if ($stmt2->rowCount() == 0) {
                    $_SESSION["admin"] = false;
                    ?>
                    <script>
                        alert("Vous n'êtes pas un admin");
                    </script>
                <?php
                } else {
                    $_SESSION["admin"] = true;
                    ?>
                    <script>
                        alert("Vous êtes un admin");
                    </script>
                <?php
                }
                $_POST = array();
            ?>

                <script>
                    alert("Connexion réussie");
                </script>

            <?php
                header("Location: accueil.php");
            }
        }
    }
?>