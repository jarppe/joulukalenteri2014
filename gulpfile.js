/* jshint node: true */

var gulp         = require("gulp"),
    del          = require("del"),
    plumber      = require("gulp-plumber"),
    uglify       = require("gulp-uglify"),
    minifyHTML   = require("gulp-minify-html"),
    usemin       = require("gulp-usemin"),
    minifyCSS    = require("gulp-minify-css"),
    webserver    = require("gulp-webserver"),
    gprint       = require('gulp-print');;

var src   = ".";

gulp.task("clean", function(cb) {
  del([dist], cb);
});

gulp.task("default", function() {
  gulp
    .src([src])
    .pipe(gprint())
    .pipe(webserver({
      livereload: false
    }));
});
