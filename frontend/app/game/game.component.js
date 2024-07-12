// game/game.component.js
angular.module("game").component("game", {
    templateUrl: "game/game.template.html",
    controller: [
      "PixabayService",
      "GameService",
      "ScoreService",
      "UserDataService",
      "$timeout",
      "$interval",
      "$location",
      function GameController(PixabayService,GameService, ScoreService, UserDataService, $timeout, $interval, $location) {
        var self = this;
        
        self.loading = true;
        self.$onInit = function () {
          self.cards = [];
          self.anverse = [];
          self.busy = true;
          //var images = GameService.query();
          var imagePaths=['images/as.png','images/king.png','images/nine.png','images/queen.png','images/three.png']
          /*PixabayService.getAnverseImages().$promise.then(function (response){
            response.forEach((image) => {
              let card1 = {
                id: self.cards.length + 1,
                content: image.largeImageURL,
                flipped: true,
                matched: false,
              };
    
              let card2 = {
                id: self.cards.length + 2,
                content: image.largeImageURL,
                flipped: true,
                matched: false,
              };
    
              self.cards.push(card1, card2);
            });
            for (var i = self.cards.length - 1; i > 0; i--) {
              var j = Math.floor(Math.random() * (i + 1));
              var temp = self.cards[i];
              self.cards[i] = self.cards[j];
              self.cards[j] = temp;
            }
            self.loading = false;
          });*/
          imagePaths.forEach((path, index) => {
            let card1 = {
              id: self.cards.length + 1,
              content: path,  // Usando el path de imagePaths
              flipped: true,
              matched: false,
            };
        
            let card2 = {
              id: self.cards.length + 2,
              content: path,  // Usando el path de imagePaths
              flipped: true,
              matched: false,
            };
        
            self.cards.push(card1, card2);
            });
            for (var i = self.cards.length - 1; i > 0; i--) {
              var j = Math.floor(Math.random() * (i + 1));
              var temp = self.cards[i];
              self.cards[i] = self.cards[j];
              self.cards[j] = temp;
            }
            self.loading = false;
          

          PixabayService.getReverseImage().$promise.then(function(response){
            self.reverse=response.largeImageURL
            
          });



          $timeout(function () {
            self.cards.forEach((card) => {
              card.flipped = false;
            });
            self.startTimer();
            self.busy = false;
          }, 2500);
  
          var userData = UserDataService.getUserData();
          self.userName = userData.userName;
          self.userTeam = userData.userTeam;
        };
  
        self.firstSelection = null;
        self.moves = 0;
        self.time = 0;
        self.timer = null;
        self.gameFinished = false;
  
        self.startTimer = function () {
          self.timer = $interval(function () {
            self.time++;
          }, 100);
        };
  
        self.stopTimer = function () {
          if (self.timer) {
            $interval.cancel(self.timer);
            self.timer = null;
          }
        };
  
        self.allMatched = function () {
          return self.cards.every(function (card) {
            return card.matched;
          });
        };
  
        self.selectCard = function (card) {
          if (!self.busy && !card.flipped) {
            card.flipped = true;
            if (self.firstSelection === null) {
              self.firstSelection = card;
            } else {
              self.moves++;
              if (self.firstSelection.content === card.content) {
                card.matched = true;
                self.firstSelection.matched = true;
                self.firstSelection = null;
                if (self.allMatched()) {
                  self.gameFinished = true;
                  self.stopTimer();
                  self.saveScore();
                }
              } else {
                self.busy = true;
                $timeout(function () {
                  self.firstSelection.flipped = false;
                  card.flipped = false;
                  self.firstSelection = null;
                  self.busy = false;
                }, 1000);
              }
            }
          }
        };
        self.saveScore = function () {
          var scoreData = {
            userName: self.userName,
            team: self.userTeam,
            moves: self.moves,
            time: self.time
          };
          
          
          ScoreService.save(scoreData).$promise.then(function (response) {
            
            UserDataService.setScore(response.id,self.moves,self.time);
            $location.path('/end');
          }).catch(function (error) {
            console.error('Error saving score:', error);
          });

        };
      }
    ]
  });
  