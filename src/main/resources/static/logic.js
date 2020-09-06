var app = angular.module('app', ['ngRoute']);
var contextPath = 'localhost:8189/tm'

app.config(function ($routeProvider) {
    $routeProvider
        .when('/login', {
                    templateUrl: 'login-page.html',
                    controller: 'loginController'
        })
});

app.controller('loginController', function ($scope, $http) {
    fillTable = function () {
        $http.get(contextPath + '/auth')
            .then(function (response) {
                $scope.PopularBooksList = response.data;
            });
    }
    fillTable();
});