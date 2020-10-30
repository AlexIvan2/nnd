'use strict';

var module = angular.module('ciApp.controllers', []);
module.controller("CountryController", ["$scope", "CountryService", "PHONE_NUMBER_PATTERN",
    function ($scope, CountryService, PHONE_NUMBER_PATTERN) {

        var init = function () {
            $scope.phoneNumber;
            $scope.country = null;
            $scope.phoneNumberPattern = PHONE_NUMBER_PATTERN;
        };

        $scope.countryCheck = function () {
            if ($scope.myForm.$invalid) {
                alert($scope.phoneNumber + "is invalid phone number!");
                event.preventDefault();
            } else {
                CountryService.getCountryByPhoneNumber($scope.phoneNumber).then(function (valueResult) {
                    $scope.country = valueResult.data.countryCode;
                    console.log(valueResult);
                }, function (reason) {
                    console.log("error occurred:" + reason);
                }, function (value) {
                    console.log("no callback");
                });
            }
        };

        init();

    }]);