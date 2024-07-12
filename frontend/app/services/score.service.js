angular.module('services')
  .factory('ScoreService', ['$resource', function($resource) {
    var baseUrl = 'http://localhost:8080/api/scores';
    return $resource(baseUrl, {}, {
      'topScores': {
        method: 'GET',
        url: baseUrl + "/top-scores",
        isArray: true
      },
      'getPosition': {
        method: 'GET',
        url: baseUrl + "/:id/position",  
        params: { id: '@id' }
      },
      'save': {
        method: 'POST',
        url: baseUrl
      }
    });
  }]);
