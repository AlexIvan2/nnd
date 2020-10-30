'use strict';

var ciApp = angular.module('ciApp', [
    'ui.bootstrap',
    'ciApp.controllers',
    'ciApp.services']);

ciApp.constant('PHONE_NUMBER_PATTERN','^(\\+|00){1}\\d{1,7}\\d{3,}$');

