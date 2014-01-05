module.exports = function(grunt) {

  // Project configuration.
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    aws: grunt.file.readJSON('grunt-aws.json'), // for S3

    stylus: {
      compile: {
        options: {
          paths: ['styl'], // folder, where files to be imported are located
          urlfunc: 'url', // use embedurl('test.png') in our code to trigger Data URI embedding
          'include css': true
        },
        files: {
          '../resources/public/css/app.css': 'styl/app.styl' // 1:1 compile
        }
      }
    },

    s3: {
      key: '<%= aws.key %>',
      secret: '<%= aws.secret %>',
      bucket: '<%= aws.bucket %>',
      access: 'public-read',
      encodePaths: true,
      //debug: true,

      // Files to be uploaded.
      upload: [
        {
          src: '../resources/public/index.html',
          dest: 'index.html',
          gzip: true
        },
        {
          src: '../resources/public/sitemap/index.html',
          dest: 'sitemap/index.html',
          gzip: true
        },
        {
          src: '../resources/public/translate/index.html',
          dest: 'translate/index.html',
          gzip: true
        },
        {
          src: '../resources/public/time-to-read/index.html',
          dest: 'time-to-read/index.html',
          gzip: true
        },
        {
          src: '../resources/public/rsa-signer/index.html',
          dest: 'rsa-signer/index.html',
          gzip: true
        },
        {
          src: '../resources/public/mangopay/index.html',
          dest: 'mangopay/index.html',
          gzip: true
        },
        {
          src: '../resources/public/pygmenter/index.html',
          dest: 'pygmenter/index.html',
          gzip: true
        },
        {
          src: '../resources/public/lein-sitemap/index.html',
          dest: 'lein-sitemap/index.html',
          gzip: true
        },
        {
          src: '../resources/public/lein-ping/index.html',
          dest: 'lein-ping/index.html',
          gzip: true
        },
        {
          src: '../resources/public/hashids/index.html',
          dest: 'hashids/index.html',
          gzip: true
        },
        {
          src: '../resources/public/css/app.css',
          dest: 'css/app.css',
          gzip: true
        },
        {
          src: '../resources/public/js/vendor.js',
          dest: 'js/vendor.js',
          gzip: true
        },
        {
          src: '../resources/public/images/*.png',
          dest: 'images/',
          rel: '../resources/public/images'
        },
        {
          src: '../resources/public/google235235bcbf7ddb0d.html',
          dest: 'google235235bcbf7ddb0d.html'
        },
        {
          src: '../resources/public/robots.txt',
          dest: 'robots.txt'
        },
        {
          src: '../resources/public/humans.txt',
          dest: 'humans.txt'
        },
        {
          src: '../resources/public/sitemap.xml',
          dest: 'sitemap.xml'
        },
        {
          src: '../resources/public/favicon.ico',
          dest: 'favicon.ico'
        }
      ]
    },

    concat: {
      options: {
        separator: ';'
      },
      vendor: {
        src: [
              'js/jquery.js',
              'js/foundation.min.js',
              'js/foundation.topbar.js',
              'js/foundation.offcanvas.js'
              ],
        dest: '../resources/public/js/vendor.js'
      },
    },

    uglify: {
      vendor: {
        files: {
          '../resources/public/js/vendor.js': ['../resources/public/js/vendor.js']
        }
      }
    },
    watch: {
      src: {
        files: ['styl/*.styl'],
        tasks: ['build']
      }
    }

  });

  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-stylus');
  grunt.loadNpmTasks('grunt-s3');
  grunt.loadNpmTasks('grunt-contrib-watch');

  grunt.registerTask('build', ['stylus:compile', 'concat:vendor']);
  grunt.registerTask('deploy', ['stylus:compile', 'concat:vendor', 'uglify:vendor', 's3:upload']);

};
