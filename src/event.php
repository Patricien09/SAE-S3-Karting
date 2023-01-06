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
        <title> Evenements </title>
    </head>

    <body onload="movePicture();">
        <?php 
            require_once("header.php"); 
            require_once("connectBD.php");

            //On récupère les événements
            $con = connect_bd();
            $sql = "SELECT * FROM `match`";
            $stmt = $con->prepare($sql);

            $res = $stmt->execute();

            if (!$res) {
                echo "Erreur lors de la récupération des événements";
            }

            $sql = "SELECT * FROM `circuit` WHERE `idCircuit` = :idCircuit";
            $stmt2 = $con->prepare($sql);
        ?>
        
        <div id="contenu">
            <h1> Evenements </h1>
            <h2> A venir : </h2>
            <div class="event-list">
                <ul class="events">
                    <?php
                        //On affiche les événements
                        while ($row = $stmt->fetch()) {
                            $values = [
                                "idCircuit" => $row["Circuit_idCircuit"]
                            ];
                    
                            $res = $stmt2->execute($values);
                    
                            if (!$res) {
                                echo "Erreur lors de la récupération des circuits";
                            }
                            
                            $rowCirc = $stmt2->fetch();
                            echo "<li class='event'>";
                            echo "<h2>" . $row["date"] . "</h2>";
                            echo "<p> Circuit : " . $rowCirc["nom"] . "</p>";
                            echo "<p> Adresse : " . $rowCirc["adresse"] . "</p>";
                            echo "<p> Début : " . $row["heureDebut"] . "</p>";
                            echo "<p> Fin : " . $row["heureFin"] . "</p>";
                            echo "<p> Nombre de participants nécessaires : " . $row["nbrPartNecessaire"] . "</p>";
                            echo "<button id=\"" . $row["idMatch"] . "\" class=\"registerMatch\"> S'inscrire </button>";
                            echo "</li>";
                        }
                    ?>
                </ul>
            </div>
            <h2> Passés : </h2>
            <div class="event-list">
                <ul class="events">
                    <li class="event">Bonjour</li>
                </ul>
            </div>
        </div>

        <?php 
            require_once("footer.php");
        ?>
    </body>
    
</html>