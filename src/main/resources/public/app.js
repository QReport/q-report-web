var app = angular.module('qreport', ['ngRoute', 'chart.js'])


app.run(function($rootScope){
    $rootScope.formatTimestampRelative = function(millis){
        return moment(millis).fromNow()
    }
})

app.config(function($routeProvider){
    $routeProvider
    .when("/auth", {
        templateUrl: 'templates/auth.html'
    })
    .when("/dashboard", {
        templateUrl: 'templates/dashboard.html'
    })
    .when("/ticket/:id", {
        templateUrl: 'templates/ticket.html'
    })
    .when("/admin", {
        templateUrl: 'templates/admin/admin-global.html'
    })
    .when("/admin/users", {
        templateUrl: 'templates/admin/admin-users.html'
    })
    .when("/admin/stats", {
        templateUrl: 'templates/admin/admin-stats.html'
    })
    .otherwise({
        redirectTo: '/auth'
    })
})
