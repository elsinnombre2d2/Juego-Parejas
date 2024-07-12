import gulp from 'gulp';
import concat from 'gulp-concat';
import uglify from 'gulp-uglify';
import cleanCSS from 'gulp-clean-css';
import imagemin from 'gulp-imagemin';
import { deleteAsync } from 'del';
import { createRequire } from 'module';

const require = createRequire(import.meta.url);

// Ruta a tus archivos

const paths = {
  scripts: ['app/**/*.js','app/services/*.js','app/end/*.js','app/start/*.js','!lib/**'],  // Incluye todos los archivos JS en cualquier subdirectorio dentro de 'app'
  styles: ['app/**/*.css','!lib/**'],  // Incluye todos los archivos CSS en cualquier subdirectorio dentro de 'app'
  images: ['app/img/*','!lib/**'],  // Incluye todas las imágenes en el directorio 'img' dentro de 'app'
  html: ['app/**/*.html','!lib/**'],  // Incluye todos los archivos HTML excepto los de bower_components
};

// Tarea para limpiar la carpeta de distribución
gulp.task('clean', () => {
  return deleteAsync(['dist']);
});

// Tarea para procesar JavaScript
gulp.task('scripts', () => {
  return gulp.src(paths.scripts)
    .pipe(concat('app.min.js'))
    .pipe(uglify())
    .pipe(gulp.dest('dist/js'));
});

// Tarea para procesar CSS
gulp.task('styles', () => {
  return gulp.src(paths.styles)
    .pipe(concat('styles.min.css'))
    .pipe(cleanCSS())
    .pipe(gulp.dest('dist/css'));
});

// Tarea para optimizar imágenes
gulp.task('images', () => {
  return gulp.src(paths.images)
    .pipe(imagemin())
    .pipe(gulp.dest('dist/images'));
});

// Tarea para copiar archivos HTML
gulp.task('html', () => {
  return gulp.src(paths.html)
    .pipe(gulp.dest('dist'));
});

// Tarea de build que ejecuta todas las tareas anteriores
gulp.task('build', gulp.series('clean', gulp.parallel('scripts', 'styles', 'images', 'html')));

// Tarea por defecto
gulp.task('default', gulp.series('build'));
