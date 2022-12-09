<?php
//fonction qui recupere les informations de l'utilisateur
    function infoUtilisateur() {
        require_once("connectbd.php");
        
        //Afficher les infos de l'utilisateur connecté avec une requete sql et en utilisant PDO et en l'affichant en HTML

        $connexion = connect_bd();
        $sql = "SELECT * FROM `personne` WHERE `mail` = :mail AND `mdp` = :mdp";
        $stmt = $connexion->prepare($sql);
        $stmt->bindParam(':mail', $_SESSION["username"]);
        $stmt->bindParam(':mdp', $_SESSION["password"]);
        $res = $stmt->execute();
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
                        <p>Nom : <?php echo $row["nom"]; ?></p>
                        <p>Prénom : <?php echo $row["prenom"]; ?></p>
                        <p>Téléphone : <?php echo $row["telephone"]; ?></p>
                        <p>Mail : <?php echo $row["mail"]; ?></p>
                    </div>
                </body>
            <?php
            }
        }
        
    }
?>