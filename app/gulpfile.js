/**
 *  Welcome to your gulpfile!
 *  The gulp tasks are split into several files in the gulp directory
 *  because putting it all here was too long
 */

'use strict';

var fs = require('fs');
var gulp = require('gulp');
var runkeeper = require('runkeeper-js');
var ngAnnotate = require('gulp-ng-annotate');
var minify = require('gulp-uglify');
var rename = require('gulp-rename');
var concat = require('gulp-concat');
var sourcemaps = require('gulp-sourcemaps');

/**
 *  This will load all js or coffee files in the gulp directory
 *  in order to load all gulp tasks
 */
fs.readdirSync('./gulp').filter(function(file) {
  return (/\.(js|coffee)$/i).test(file);
}).map(function(file) {
  require('./gulp/' + file);
});

//gulp.task('minify', function() {
//  return gulp.src([
//      'src/app/home/*.js',
//      'src/app/ingredient/*.js',
//      'src/app/main/*.js',
//      'src/manage/dise/*.js',
//      'src/scripts/main.js'
//    ])
//    .pipe(sourcemaps.init())
//    .pipe(concat('concat.js'))
//    .pipe(gulp.dest('dist'))
//    .pipe(rename('uglify.js'))
//    .pipe(ngAnnotate()) // Makes angular safe to minify.
//    .pipe(minify()) // Minifies the concatenated js file.
//    .pipe(sourcemaps.write('./'))
//    .pipe(gulp.dest('dist'));
//});

/**
 *  Default task clean temporaries directories and launch the
 *  main optimization build task
 */
gulp.task('default', ['clean'], function () {
  gulp.start('build');
});
