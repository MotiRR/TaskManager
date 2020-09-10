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
        .when('/project_info', {
                     templateUrl: 'project-info-page.html',
                     controller: 'projectInfoController'
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
        localStorage.clear();//setItem("token", '');
        $window.location.href = contextPath;
    }

    $scope.auth=function(){
        $http.post(contextPath + '/auth', $scope.authUser)
            .then(function (response) {
            if(response.data.status !== 200)
                $scope.result = "Неправильный логин и/или пароль";
            else {
                localStorage.setItem("token", response.data.token);
                localStorage.setItem("user", $scope.authUser.login);
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
                      localStorage.setItem("user", $scope.registerUser.login);
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
        $window.location.href = contextPath + '#!/project_info';
    }

    $scope.create=function(){
        localStorage.setItem("is_edit_project", 0);
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

var tasks = [];
var users = [];
app.controller('projectController', function ($scope, $location, $window, $http) {

    $scope.select = function (checkBoxValue) {
        fillEntities(tasks, checkBoxValue);
    };

    $scope.selectUser = function (checkBoxValue) {
        fillEntities(users, checkBoxValue);
    };

    fillEntities = function(entities, checkBoxValue) {
        var index = tasks.indexOf(checkBoxValue);
        if (index > -1) {
          entities.splice(index, 1);
        }
        else {
          entities.push(checkBoxValue);
    }};

    $scope.save=function() {
        var is_edit_project = localStorage.getItem("is_edit_project");
           /* $scope.project.title="Project 1";
            $scope.project.leaderId=1;
            $scope.project.users=null;
            $scope.project.tasks=[tasks];*/
            users[0].password="lkjkl";
            var query = {
                'id': null,
                'title': "Project 1",
                'tasks': tasks,
                'users' : users
            };
            console.log(tasks);
           // console.log($scope.project.tasks);
           // "createdAt": "2020-09-10T11:13:01.497762",
           // "updatedAt": "2020-09-10T11:13:01.497762"
        if(is_edit_project == 0) {
            $http.post(contextPath + '/api/v1/projects/create',
                       query, {
                           headers: {'Authorization': 'Bearer '+localStorage.getItem("token")}
                       })
                .then(function (response) {
                console.log(response.data);
                    /*if(response.data.status !== 200)
                         $scope.result = response.data.messages[0];
                    else {
                          localStorage.setItem("token", response.data.token);
                          $window.location.href = contextPath + '#!/projects';
                     }*/
                });
        } else {
            $http.put(contextPath + '/api/v1/projects/create',
                       query, {
                           headers: {'Authorization': 'Bearer '+localStorage.getItem("token")}
                       })
                .then(function (response) {
                console.log(response.data);
                    /*if(response.data.status !== 200)
                         $scope.result = response.data.messages[0];
                    else {
                          localStorage.setItem("token", response.data.token);
                          $window.location.href = contextPath + '#!/projects';
                     }*/
                });
        }
    }

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
        $http.get(contextPath + '/api/v1/tasks',
            {
              headers: {'Authorization': 'Bearer '+localStorage.getItem("token")}
            })
             .then(function (response) {
                 $scope.TaskList = response.data;
         });
        $http.get(contextPath + '/api/v1/users',
            {
              headers: {'Authorization': 'Bearer '+localStorage.getItem("token")}
            })
             .then(function (response) {
                 $scope.UserList = response.data;
         });
    };

    fillTableEdit = function () {
        var project = angular.fromJson(localStorage.getItem("edit_project"));
        console.log(project);
        $scope.title = project.title;
       // $scope.TaskList = project
        $scope.UserList = project.users;
    };


    if (localStorage.getItem("is_edit_project") == 0) {
        fillTable();
    }
    else {
        fillTableEdit();
    }
});

app.controller('projectInfoController', function ($scope, $location, $window, $http) {

    var project;

    $scope.edit = function() {
        localStorage.setItem("is_edit_project", 1);
        localStorage.setItem("edit_project", angular.toJson(project));
        $window.location.href = contextPath + '#!/project';
    };

    var projectId = localStorage.getItem("projectId");
    fillTable = function () {
        $http.get(contextPath + '/api/v1/projects/'+projectId,
            {
                 headers: {'Authorization': 'Bearer '+localStorage.getItem("token")}
            })
        .then(function (response) {
                project = response.data;
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