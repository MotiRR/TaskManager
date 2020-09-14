var app = angular.module('app', ['ngRoute']);
var contextPath = 'http://localhost:8189/tm'

/*
var fileSaver = angular
  .module('fileSaver', ['ngFileSaver'])

fileSaver.controller('saveFile', function saveFile($scope, FileSaver, Blob) {
    val = {
            text: 'Hey ho lets go!'
    };

    $scope.download = function() {
        var data = new Blob([text], { type: 'text/plain;charset=utf-8' });
        FileSaver.saveAs(data, 'text.txt');
        };
    });
*/

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
        .when('/task_info', {
                             templateUrl: 'task-info-page.html',
                             controller: 'taskInfoController'
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
        console.log(response);
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
        $window.location.href = contextPath + '#!/task_info';
    }

    $scope.create=function(){
        localStorage.setItem("is_edit_task", 0);
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

app.controller('taskInfoController', function ($scope, $location, $window, $http) {

    var task;

    $scope.edit = function() {
        localStorage.setItem("is_edit_task", 1);
        localStorage.setItem("edit_task", angular.toJson(task));
        $window.location.href = contextPath + '#!/task';
    };

   /* var fileSaver = angular
      .module('fileSaver', ['ngFileSaver'])

    fileSaver.controller('saveFile', function saveFile($scope, FileSaver, Blob) {
        val = {
                text: 'Hey ho lets go!'
        };*/


    $scope.download = function(file_id, file_type, file_url) {
        console.log(file_id);


        /*$http.get(contextPath + '/api/v1/files/'+file_id,
                    {
                         headers: {'Authorization': 'Bearer '+localStorage.getItem("token"),
                    })
                .then(function (response) {

                 });
        */
    };

    var taskId = localStorage.getItem("taskId");
    fillTable = function () {
        $http.get(contextPath + '/api/v1/tasks/'+taskId,
            {
                 headers: {'Authorization': 'Bearer '+localStorage.getItem("token")}
            })
        .then(function (response) {
                task = response.data;
                fill_task($scope, response.data);
         });

    };
    fillTable();

$http.get(contextPath + '/api/v1/files',
            {
                headers: {'Authorization': 'Bearer '+localStorage.getItem("token")}
            })
            .then(function (response) {
            console.log(response.data);
                $scope.fileList = response.data;
            });


});

var projectTask;
app.controller('taskController', function ($scope, $location, $window, $http) {

    var taskId = localStorage.getItem("taskId");
    var is_edit_task = localStorage.getItem("is_edit_task");

     /*<tr><td>Id:</td><td>{{id}}</td></tr>
        <tr><td>Title:</td><td>{{title}}</td></tr>
        <tr><td>Description</td><td>{{description}}</td></tr>
        <tr><td>DeadLine</td><td>{{deadLine}}</td></tr>
        <tr><td>LeaderId</td><td>{{leaderId}}</td></tr>
        <tr><td>Priority</td><td>{{priority}}</td></tr>
        <tr><td>Status</td><td>{{status}}</td></tr>
        <tr><td>CreatedAt</td><td>{{createdAt}}</td></tr>
        <tr><td>UpdatedAt</td><td>{{updatedAt}}</td></tr>
        <tr><td>Project</td><td>{{project}}</td></tr>*/

    $scope.getFileDetails = function (e) {

        $scope.files = [];
        $scope.$apply(function () {

            for (var i = 0; i < e.files.length; i++) {
                $scope.files.push(e.files[i])
            }
        });
    };

    $scope.select = function(p) {
        projectTask = p;
    };

    $scope.save=function() {
        if(is_edit_task == 0) {
        console.log($scope.project);
        config = fill_config();
        fill_task(config, $scope);
        config.project = projectTask;
            $http.post(contextPath + '/api/v1/tasks',
                       config, {
                           headers: {'Authorization': 'Bearer '+localStorage.getItem("token")}
                       })
                .then(function (response) {
                    var y = response.data;
                    if(response.status !== 201)
                         alert(response.data.message);
                    else {
                         alert(response.data.message);
                         $window.location.href = contextPath + '#!/tasks';
                     }
                });
        } else {
            var config_task = angular.fromJson(localStorage.getItem("edit_task"));
            fill_task(config_task, $scope);
            uploadFiles($scope.files, config_task.id);
            $http.put(contextPath + '/api/v1/tasks',
                       config_task, {
                           headers: {'Authorization': 'Bearer '+localStorage.getItem("token")}
                       })
                .then(function (response) {
                    console.log(response.data);
                    $window.location.href = contextPath + '#!/tasks';
                });
        }
    }

    fillTable = function () {
        var config_task = angular.fromJson(localStorage.getItem("edit_task"));
        fill_task($scope, config_task);
    };
    if(is_edit_task == 1)
       fillTable();

    $http.get(contextPath + '/api/v1/projects',
        {
          headers: {'Authorization': 'Bearer '+localStorage.getItem("token")}
        })
        .then(function (response) {
          $scope.projectList = response.data;
        });
});

fill_task = function (rec, don) {
    rec.title = don.title;
    rec.description = don.description;
    rec.deadLine = don.deadLine;
    rec.leaderId = don.leaderId;
    rec.priority = don.priority;
    rec.status = don.status;
    rec.project = don.project;
};

fill_config = function() {
    return {
            'id': null,
            'title': null,
            'leaderId': null,
            'description': null,
            'project': null,
            'priority': null,
            'status': null,
            'deadLine': null,
            'users': null,
            'comments': null,
            'createdAt': null,
            'updatedAt': null
    }
}

uploadFiles = function (files, taskId) {

    var data = new FormData();

    for (var i in files) {
        data.append("file", files[0]);
        data.append("taskId", taskId);
    }

    var objXhr = new XMLHttpRequest();
    objXhr.addEventListener("progress", updateProgress, false);
    objXhr.open("POST", contextPath + "/api/v1/files/upload");
    objXhr.setRequestHeader('Authorization', 'Bearer '+localStorage.getItem("token"))
    objXhr.send(data);
};

updateProgress = function(e) {
    if (e.lengthComputable) {
        document.getElementById('pro').setAttribute('value', e.loaded);
        document.getElementById('pro').setAttribute('max', e.total);
    }
};

app.factory('logService', function($http, $q){
    return{
        getData: function($scope){
            alert($scope.login);
            alert($scope.logout);
        }
    }
})