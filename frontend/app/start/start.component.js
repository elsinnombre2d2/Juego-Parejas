// start/start.component.js
angular.module('start')
  .component('start', {
    templateUrl: 'start/start.template.html',
    controller: ['ScoreService', '$location', 'UserDataService', function StartController(ScoreService, $location, UserDataService) {
      var self = this;

      self.$onInit = function() {
        self.topScores = [];
        var userData=UserDataService.getUserData();
        self.userName = userData.userName;
        self.userTeam = userData.userTeam;

        // Obtener las mejores puntuaciones al iniciar el componente
        
        self.topScores = ScoreService.topScores();
        
      };

      self.customFilter = function(score) {
        var lowerCaseName = self.userName.toLowerCase(); // Convertir la consulta a minúsculas
        var name = score.name.toLowerCase(); // Convertir el nombre del score a minúsculas
        var lowerCaseTeam = self.userTeam.toLowerCase();
        return lowerCaseName === score.name.toLowerCase() && lowerCaseTeam === score.team.toLowerCase();
      };
      self.startGame = function() {
        // Guardar el nombre y equipo del usuario y comenzar el juego
        if (self.userName && self.userTeam) {
          UserDataService.setUserData(self.userName, self.userTeam);
          // Redirigir a la pantalla del juego
          $location.path('/game');
        }
      };
    }]
  });
