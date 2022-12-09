<script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script>
    function login(){
        $("#loginModal").modal('show');
    }
</script>

<?php
    require_once("login.php");
?>

<script type="text/javascript" src="script/burgermenu.js"></script>

<header>
    
    <a href="accueil.php" class="logo"><img src="logos/kart.jpg"></a>

    <h1> Notre Club De Karting </h1>

    <div onclick="menu();"><span id="burger"></span></div> 

    <nav>
        <div class="wrapper">
            <ul class="list-unstyled">
                <li><a class="button1" href="accueil.php"><b>Accueil</b></a></li>
                <li><a class="button1" href="information.php"><b>Informations</b></a></li>
                <li><a class="button1" href="multi.php"><b>Multimédia</b></a></li>
                <li><a class="button1" href="event.php"><b>Evenements</b></a></li>

                <li><a class="button1" href="partenaire.php"><b>Partenaires</b></a></li>
                <li><a class="button1" href="boutique.php"><b>Boutique</b></a></li>
                
                <li><a class="button1" href="inscription.php"><b>Inscription</b></a></li>
                <li>
                    <?php
                        if (isset($_SESSION["connected"]) && $_SESSION["connected"] == true) {
                            echo "<a class='button1' href='logout.php'><b>Deconnexion</b></a>";
                        } else {
                            echo "<a class='button1' onClick='login();' href=#><b>Connexion</b></a>";
                        }
                    ?>
                </li>
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
                        <input type="text" name="username" class="form-control" id="username" placeholder="Votre identifiant...">
                    </div>
                    <div class="form-group">
                        <input type="password" name="password" class="form-control" id="password" placeholder="Votre mot de passe...">
                    </div>
                    <div class="form-group">
                        <!-- Texte qui servira à afficher les eventuelles erreurs -->
                        <small id="authError" class="text-danger">

                        </small>
                    </div>
                </div>

                <!-- Mise en forme des boutons du formulaire ==> SUBMIT & CANCEL-->
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Login</button>
                </div>
            </form>
        </div>
    </div>
</div>