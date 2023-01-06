<?php

if (!isset($_POST["idMatch"]) || !isset($_POST["idPersonne"])) {
    exit("Erreur");
}

require_once("connectBD.php");

$con = connect_bd();

$sql = "SET FOREIGN_KEY_CHECKS=0; INSERT INTO `match_has_adherent` (`Match_idMatch`, `Adherent_Personne_idPersonne`) VALUES (:idMatch, :idPersonne); SET FOREIGN_KEY_CHECKS=1";

$values = [
    "idMatch" => $_POST["idMatch"],
    "idPersonne" => $_POST["idPersonne"]
];

$stmt = $con->prepare($sql);
$res = $stmt->execute($values);

if (!$res) {
    exit("Erreur");
}

exit("Réussie")

?>