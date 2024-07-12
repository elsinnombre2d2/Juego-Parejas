// end/end.component.js
angular.module("end").component("end", {
  templateUrl: "end/end.template.html",
  controller: [
    "UserDataService",
    "ScoreService",
    "$location",
    function EndController(UserDataService, ScoreService, $location) {
      var self = this;

      self.$onInit = function () {
        var userData = UserDataService.getUserData();
        
        self.userName = userData.userName;
        self.userTeam = userData.userTeam;
  
        // Obtenemos los datos del puntaje guardados previamente
        var score = UserDataService.getScore();
        
        
        self.movements = score.movements;
        self.time = score.time;
        self.id = score.id;
        if (score.id != 0)
          ScoreService.getPosition({ id: self.id }).$promise.then(function (
            response
          ) {
            self.position = response.position;
          });
      };
      var score = UserDataService.getScore();
      // Obtenemos los datos del usuario guardados previamente

      // Función para reiniciar el juego
      self.restartGame = function () {
        // Limpiar los datos del usuario y puntaje
        //UserDataService.setUserData('', ''); // Limpiar nombre y equipo
        UserDataService.setScore(0, 0, 0); // Reiniciar movimientos y tiempo

        // Redirigir al inicio del juego o a donde sea necesario
        // Por ejemplo, si tienes $location inyectado:
        $location.path("/game");
      };

      self.toStart = function () {
        //UserDataService.setUserData('', ''); // Limpiar nombre y equipo
        UserDataService.setScore(0, 0, 0); // Reiniciar movimientos y tiempo
        $location.path("/start");
      };
    },
  ],
});
