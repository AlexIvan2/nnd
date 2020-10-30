'use strict';

angular.module('ciApp.services', [])
    .factory('CountryService',
        ['$http', function ($http) {
                var countryService;

                countryService = {
                    getCountryByPhoneNumber: function (phoneNumber) {
                        return $http.get('/rest/v1/check/' + phoneNumber);
                    }

                };

                return countryService;
            }]
    );

