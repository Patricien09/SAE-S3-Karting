<?php

    session_start();
    if (!isset($_POST["date"]) || !isset($_POST["heureDebut"]) || !isset($_POST["heureFin"]) || !isset($_POST["nombreParticipant"]) || !isset($_POST["idCircuit"])) {
        exit("Erreur");
    }

    require_once("connectBD.php");

    $con = connect_bd();

    $sql = "INSERT INTO `reservation` (`date`, `heureDebut`, `heureFin`, `nombreParticipant`, `Circuit_idCircuit`, `Adherent_Personne_idPersonne`, `autorisation`) VALUES (:date, :heureDebut, :heureFin, :nombreParticipant, :idCircuit, :idPersonne, 0)";
    echo $sql;
    $values = [
        "date" => $_POST["date"],
        "heureDebut" => $_POST["heureDebut"] . ":00",
        "heureFin" => $_POST["heureFin"] . ":00",
        "nombreParticipant" => $_POST["nombreParticipant"],
        "idCircuit" => $_POST["idCircuit"],
        "idPersonne" => $_SESSION["id"]
    ];
    foreach ($values as $key => $value) {
        echo $key . " : " . $value;
    }

    $stmt = $con->prepare($sql);
    $res = $stmt->execute($values);

    if (!$res) {
        exit("Erreur");
    }

    exit("Réussie");

?>