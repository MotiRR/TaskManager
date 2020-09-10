var app = angular.module('app', ['ngRoute']);
var contextPath = 'http://localhost:8189/tm'

app.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
                    templateUrl: 'main-page.html',
                    controller: 'mainController'
        })
        .when('/login', {
                     templateUrl: 'login-page.html',
                     controller: 'loginController'
                })
        .when('/register', {
                     templateUrl: 'register-page.html',
                     controller: 'registerController'
        })
        .when('/projects', {
                     templateUrl: 'projects-page.html',
                     controller: 'projectsController'
        })
        .when('/project', {
                     templateUrl: 'project-page.html',
                     controller: 'projectController'
        })
        .when('/tasks', {
                     templateUrl: 'tasks-page.html',
                     controller: 'tasksController'
        })
        .when('/task', {
                     templateUrl: 'task-page.html',
                     controller: 'taskController'
         })
});


app.controller('mainController', function ($scope, $location, $window, $http, logService) {

/*if(localStorage.getItem("login") == null) {
logService.getData($scope);
    }*/

});


app.controller('loginController', function ($scope, $location, $window, $http) {

    $scope.exit=function(){
        localStorage.setItem("token", '');
        $window.location.href = contextPath;
    }

    $scope.auth=function(){
        $http.post(contextPath + '/auth', $scope.authUser)
            .then(function (response) {
            if(response.data.status !== 200)
                $scope.result = "Неправильный логин и/или пароль";
            else {
                localStorage.setItem("token", response.data.token);
                $window.location.href = contextPath + '#!/projects';
                }
            });
    }
});

app.controller('registerController', function ($scope, $location, $window, $http) {

    $scope.register=function(){
        $http.post(contextPath + '/register', $scope.registerUser)
            .then(function (response) {
                if(response.data.status !== 201)
                     $scope.result = response.data.messages[0];
                else {
                      localStorage.setItem("token", response.data.token);
                      $window.location.href = contextPath + '#!/projects';
                 }
            });
    }
});

app.controller('projectsController', function ($scope, $location, $window, $http) {

    /*if(localStorage.getItem("token") === '') {
        $window.location.href = contextPath;
    }*/

    $scope.select=function(projectId){
        localStorage.setItem("projectId", projectId);
        $window.location.href = contextPath + '#!/project';
    }

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

app.controller('projectController', function ($scope, $location, $window, $http) {

    var projectId = localStorage.getItem("projectId");
    fillTable = function () {
        $http.get(contextPath + '/api/v1/projects/'+projectId,
            {
                 headers: {'Authorization': 'Bearer '+localStorage.getItem("token")}
               })
        .then(function (response) {
                $scope.ProjectId = response.data.id;
                $scope.ProjectTitle = response.data.title;
         });
    };
    fillTable();
});

app.controller('tasksController', function ($scope, $location, $window, $http) {

    $scope.select=function(taskId){
        localStorage.setItem("taskId", taskId);
        $window.location.href = contextPath + '#!/task';
    }

    fillTable = function () {
        $http.get(contextPath + '/api/v1/tasks',
           {
             headers: {'Authorization': 'Bearer '+localStorage.getItem("token")}
           })
            .then(function (response) {
                $scope.TaskList = response.data;
            });
    };
    fillTable();
});

app.controller('taskController', function ($scope, $location, $window, $http) {
    var taskId = localStorage.getItem("taskId");
    fillTable = function () {
        $http.get(contextPath + '/api/v1/tasks/'+taskId,
           {
              headers: {'Authorization': 'Bearer '+localStorage.getItem("token")}
           })
           .then(function (response) {
              $scope.TaskId = response.data.id;
              $scope.TaskTitle = response.data.title;
           });
     };
     fillTable();
});

app.factory('logService', function($http, $q){
    return{
        getData: function($scope){
            alert($scope.login);
            alert($scope.logout);
        }
    }
})