<?php
require_once("login.php");
?>

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
        <title> Accueil </title>
    </head>
    <body>

    <script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script>
        function login() {
            $("#loginModal").modal('show');
        }

        function register() {
            $("#regModal").modal('show');
        }

        $(document).ready(function() {
            $("#regForm").submit(function(e) {
                e.preventDefault();
                $.ajax({
                    url: "inscription.php",
                    dataType: "html",
                    type: "POST",
                    data: $(this).serialize(),
                    success: function(data) {
                        // Si les mots de passe ne sont pas identiques
                        if (data == "Les mots de passe ne sont pas identiques") {
                            $("#regError").html(data);
                        }
                        // Si l'adresse mail est déjà utilisée
                        else if (data == "Cette adresse mail est déjà utilisée") {
                            $("#regError").html(data);
                        } else if (data == "Inscription réussie") {
                            $("#regError").html("");
                            $("#regModal").modal('hide');
                            $("#loginModal").modal('show');
                        }
                    },
                    error: function(data) {
                        alert("Erreur lors de l'inscription");
                    }
                });
            });
        });

        // Fonction pour enregistrer un adhérent à un match, ou le désinscrire
        // Envoie les données à registerMatch.php
        // Si l'inscription/désinscription est réussie, on affiche un message de succès
        // registerMatch.php ajoute l'id du match et de l'adhérent dans la table match_has_adherent
        function registerMatch(idMatch, idPersonne, unreg) {
            if (unreg == true) {
                var msg = "Voulez-vous vraiment vous désinscrire de ce match ?";
            } else {
                var msg = "Voulez-vous vraiment vous inscrire à ce match ?";
            }
            if (confirm(msg)) {
                $.ajax({
                    url: "registerMatch.php",
                    dataType: "html",
                    type: "POST",
                    data: {
                        unRegister: unreg,
                        idMatch: idMatch,
                        idPersonne: idPersonne
                    },
                    success: function(data) {
                        if (data == "Réussie inscr")
                            $("#matchError" + idMatch).html("Inscription réussie");
                        else if (data == "Réussie unreg")
                            $("#matchError" + idMatch).html("Désinscription réussie");
                        else if (data == "Erreur")
                            $("#matchError" + idMatch).html("Erreur lors de l'inscription au match");
                        // Reload the page
                        setTimeout(function() {
                            location.reload();
                        }, 1000);
                    },
                    error: function(data) {
                        alert("Erreur lors de l'inscription au match");
                    }
                });
            }
        }

        function setWinner(idMatch) {
            var list = document.getElementById("list" + idMatch);
            var idWinner = list.options[list.selectedIndex].value;

            if (confirm("Voulez-vous vraiment déclarer ce pilote comme gagnant ?")) {
                $.ajax({
                    url: "setWinner.php",
                    dataType: "html",
                    type: "POST",
                    data: {
                        idMatch: idMatch,
                        idWinner: idWinner
                    },
                    success: function(data) {
                        if (data == "Réussie")
                            $("#winError" + idMatch).html("Déclaration réussie");
                        else if (data == "Erreur")
                            $("#winError" + idMatch).html("Erreur");
                        // Reload the page
                        setTimeout(function() {
                            location.reload();
                        }, 1000);
                    },
                    error: function(data) {
                        alert("Dommage");
                    }
                });
            }
        }
    </script>

    <script type="text/javascript" src="script/burgermenu.js"></script>

    <header>

        <?php
        // Affiche l'image de profil de l'utilisateur connecté
        if (isset($_SESSION['connected']) && $_SESSION['connected']) {
            $mail = $_SESSION['username'];
            if ($result = glob("users/" . $mail . "/profil.*")) {
                echo "<a href='profil.php'><img src='" . $result[0] . "?t=" . time() . "' class='logo'></a>";
            } else {
                echo "<a href='accueil.php'><img src='logos/kart.jpg' class='logo'></a>";
            }
        }
        ?>

        <h1> Notre Club De Karting </h1>

        <div onclick="menu();"><span id="burger"></span></div>

        <nav>
            <div class="wrapper">
                <ul class="list-unstyled">
                    <li><a href="accueil.php"><b>Accueil</b></a></li>
                    <li><a href="multi.php"><b>Multimédia</b></a></li>
                    <li><a href="event.php"><b>Evenements</b></a></li>

                    <li><a href="partenaire.php"><b>Partenaires</b></a></li>
                    <li><a href="reservation.php"><b>Réservation</b></a></li>

                    
                    <?php
                    //si l'utilisateur est connecté, on affiche le bouton du profil et de déconnexion
                    if (isset($_SESSION["connected"]) && $_SESSION["connected"] == true) {
                        echo "<li><a class='button1' href='profil.php'><b>Profil</b></a></li>";
                        echo "<li><a class='button1' href='logout.php'><b>Deconnexion</b></a></li>";
                    } else {
                        echo "<li><a onClick=\"register()\" href=#><b>Inscription</b></a></li>";
                        echo "<a class='button1' onClick='login();' href=#><b>Connexion</b></a>";
                    }
                    ?>
                </ul>
            </div>
        </nav>
    </header>

    <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="loginModalLabel">Connexion</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <!-- Le formulaire avec un ID pour pouvoir être récupéré avec jquery (==> #loginForm) -->
                <form id="loginForm" action="login.php" method="post">

                    <!-- Mise en forme des champs input du formulaire -->
                    <div class="modal-body">
                        <div class="form-group">
                            <input type="text" name="username" class="form-control" id="username" placeholder="Votre identifiant..." required>
                        </div>
                        <div class="form-group">
                            <input type="password" name="password" class="form-control" id="password" placeholder="Votre mot de passe..." required>
                        </div>
                        <div class="form-group">
                            <!-- Texte qui servira à afficher les eventuelles erreurs -->
                            <small id="authError" class="text-danger">

                            </small>
                        </div>
                    </div>

                    <!-- Mise en forme des boutons du formulaire ==> SUBMIT & CANCEL-->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
                        <button type="submit" class="btn btn-primary">Connexion</button>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <div class="modal fade" id="regModal" tabindex="-1" role="dialog" aria-labelledby="regModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="regModalLabel">Inscription</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <!-- Le formulaire avec un ID pour pouvoir être récupéré avec jquery (==> #regForm) -->
                <form id="regForm" action="inscription.php" method="post">

                    <!-- Mise en forme des champs input du formulaire -->
                    <div class="modal-body">
                        <div class="form-group">
                            <input type="text" name="name" class="form-control" id="name" placeholder="Votre Nom..." required>
                        </div>
                        <div class="form-group">
                            <input type="text" name="forename" class="form-control" id="forename" placeholder="Votre Prénom..." required>
                        </div>
                        <div class="form-group">
                            <input type="text" name="adresse" class="form-control" id="adresse" placeholder="Votre adresse postale..." required>
                        </div>
                        <div class="form-group">
                            <input type="tel" name="phone" class="form-control" id="phone" placeholder="Votre numéro de téléphone..." pattern="^(?:(?:\+|00)33|0)\s*[1-9](?:[\s.-]*\d{2}){4}$" required>
                        </div>
                        <div class="form-group">
                            <input type="email" name="mail" class="form-control" id="mail" placeholder="Votre mail..." required>
                        </div>
                        <div class="form-group">
                            <input type="password" name="password" class="form-control" id="password" placeholder="Votre mot de passe..." required>
                        </div>
                        <div class="form-group">
                            <input type="password" name="password2" class="form-control" id="password2" placeholder="Resaissisez votre mot de passe..." required>
                        </div>
                        <div class="form-group">
                            <!-- Texte qui servira à afficher les eventuelles erreurs -->
                            <small id="regError" class="text-danger">

                            </small>
                        </div>
                    </div>

                    <!-- Mise en forme des boutons du formulaire ==> SUBMIT & CANCEL-->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
                        <button type="submit" class="btn btn-primary">Inscription</button>
                    </div>
                </form>
            </div>
        </div>
    </div>