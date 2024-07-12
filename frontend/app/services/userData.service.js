// services/userData.service.js
angular.module('services')
  .service('UserDataService', function() {
    var userData = {
      userName: '',
      userTeam: ''
    };
    var score = {
        id:0,
        movements:0,
        time:0
    };

    return {
      getUserData: function() {
        return userData;
      },
      setUserData: function(name, team) {
        userData.userName = name;
        userData.userTeam = team;
      },
      getScore: function() {
        return score;
      },
      setScore: function(id,moves, totalTime) {
        score.id=id;
        score.movements = moves;
        score.time = totalTime;
      }
    };
  });
