angular.module('services')
  .factory('GameService', ['$resource', function($resource) {
    var mockScores = [
        { img: 1 },
        { img: 2 },
        { img: 3 },
        { img: 4 },
        { img: 5 },

      ];

    return {
      query: function() {
        return mockScores;
      }
    };
  }]);