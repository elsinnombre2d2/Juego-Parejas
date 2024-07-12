
angular.module('services')
  .factory('PixabayService', ['$resource', function($resource) {
    var baseUrl = 'http://localhost:8080/api/images';
    return $resource(baseUrl, {}, {
      'getReverseImage': {
        method: 'GET',
        url: baseUrl + "/reverse"
      },
      'getAnverseImages': {
        method: 'GET',
        url: baseUrl + "/anverse",
        isArray: true
      }
    });
  }]);