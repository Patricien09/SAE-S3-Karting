<!doctype html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link href="style.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script>
            function login(){
                $("#loginModal").modal('show');
            }
            

        </script>
        <header>
            
            <h1 class="logo">
                <img src="logos/Fesse.png" width="65" height="65">
            </h1>

            <nav class="header">
                <p> 
                    <div class="wrapper">
                        <a href="accueil.php">Accueil</a>
                        <a href="event.php">Evenements</a>
                        <a href="information.php">Informations</a>
                        <a href="multi.php">Multimédia</a>

                        <a href="partenaire.php">Partenaires</a>
                        <a href="boutique.php">Boutique</a>
                        
                        

                        <a href="inscription.php">Inscription</a>
                        <a href="connecion.php">Connexion</a>
                    </div>
                </p>
            </nav>
        </header>

        <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="loginModalLabel">Login</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <!-- Le formulaire avec un ID pour pouvoir être récupéré avec jquery (==> #loginForm) -->
                <form id="loginForm">

                    <!-- Mise en forme des champs input du formulaire -->
                    <div class="modal-body">
                        <div class="form-group">
                            <input type="text" name="name" class="form-control" id="username" placeholder="Your Username...">
                        </div>
                        <div class="form-group">
                            <input type="password" name="password" class="form-control" id="password" placeholder="Your password...">
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
    </body>
</html>