# os.hashobject.com

Hashobject team open source corner.

[![Dependencies Status](https://jarkeeper.com/hashobject/os.hashobject.com/status.png)](https://jarkeeper.com/hashobject/os.hashobject.com)


## Full deploy

Inside `frontend` directory execute command:

```
  pygmentize -S default -f html > styl/pygments.css
  lein index-html;lein projects-html;lein sitemap-xml; nvm use 0.8.26;grunt deploy; lein sitemap
  nvm use 0.8.26;grunt deploy
```

This will build all html, sitemap, css and deploy it to S3.

## CloudFront invalidation

/
/index.html
/css/app.css
/js/vendor.js
/translate/
/translate/index.html
/time-to-read/
/time-to-read/index.html
/hashids/
/hashids/index.html
/sitemap/
/sitemap/index.html
/rsa-signer/
/rsa-signer/index.html
/mangopay/
/mangopay/index.hmtl
/pygmenter/
/pygmenter/index.html
/lein-sitemap/
/lein-sitemap/index.html
/lein-ping/
/lein-ping/index.html


## Content License

Except as otherwise noted, the content of this [site](http://os.hashobject.com)
is licensed under the [Creative Commons Attribution 3.0 License](http://creativecommons.org/licenses/by/3.0/),
and code samples are licensed under the [Eclipse Public License 1.0](http://opensource.org/licenses/eclipse-1.0).

## License

Copyright © 2013-2014 Hashobject Ltd (team@hashobject.com).

Distributed under the [Eclipse Public License](http://opensource.org/licenses/eclipse-1.0).
