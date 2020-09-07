var app = angular.module('app', ['ngRoute']);
var contextPath = 'http://localhost:8189/tm'

app.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
                    templateUrl: 'login-page.html',
                    controller: 'loginController'
        })
        .when('/login', {
                     templateUrl: 'login-page.html',
                     controller: 'loginController'
                })
        .when('/register', {
                     templateUrl: 'register-page.html',
                     controller: 'registerController'
        })
        .when('/project', {
                     templateUrl: 'project-page.html',
                     controller: 'projectController'
        })
        .when('/task', {
                     templateUrl: 'task-page.html',
                     controller: 'taskController'
        })
});

app.controller('loginController', function ($scope, $location, $window, $http) {

    $scope.exit=function(){
        localStorage.setItem("token", '');
        $window.location.href = contextPath;
    }

    $scope.auth=function(){
        $http.post(contextPath + '/auth', $scope.authUser)
            .then(function (response) {
            if(response.data.status !== "200")
                $scope.result = "Неправильный логин и/или пароль";
            else {
                localStorage.setItem("token", response.data.token);
                $window.location.href = contextPath + '#!/project';
                }
            });
    }
});

app.controller('registerController', function ($scope, $location, $window, $http) {

    $scope.register=function(){
        $http.post(contextPath + '/register', $scope.registerUser)
            .then(function (response) {
                if(response.data.status !== "200")
                     $scope.result = "Что то не так";
                else {
                      token = response.data.token;
                      $window.location.href = contextPath + '#!/project';
                 }
            });
    }
});

app.controller('projectController', function ($scope, $location, $window, $http) {

    /*if(localStorage.getItem("token") === '') {
        $window.location.href = contextPath;
    }*/

    fillTable = function () {
        $http.get(contextPath + '/api/v1/projects',
           {
             headers: {'Authorization': 'Bearer '+localStorage.getItem("token")}
           })
            .then(function (response) {
                $scope.ProjectList = response.data;
            });
    };
    fillTable();
});

app.controller('taskController', function ($scope, $location, $window, $http) {

});