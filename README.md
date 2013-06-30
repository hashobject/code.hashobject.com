# os.hashobject.com

HashObject team open source corner.


## Full deploy

Inside `frontend` directory execute command:

```
  pygmentize -S default -f html > styl/pygments.css
  lein index-html;lein projects-html;lein sitemap-xml; grunt deploy; lein sitemap
```

This will build all html, sitemap, css and deploy it to S3.


## Content License

Except as otherwise noted, the content of this [site](http://os.hashobject.com)
is licensed under the [Creative Commons Attribution 3.0 License](http://creativecommons.org/licenses/by/3.0/),
and code samples are licensed under the [Eclipse Public License 1.0](http://opensource.org/licenses/eclipse-1.0).

## License

Copyright © 2013 HashObject Ltd (team@hashobject.com).

The use and distribution terms for this software are covered by the [Eclipse Public License 1.0](http://opensource.org/licenses/eclipse-1.0)
which can be found in the file epl-v10.html at the root of this distribution.

By using this software in any fashion, you are agreeing to be bound by the terms of this license.

You must not remove this notice, or any other, from this software.