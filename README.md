Liferay Property Clearing Demo
==============================

As you are probably aware, the database is the final arbiter when determining a property setting. I once managed to use this system feature to lock myself out of the only admin account I had access to on a client system. This is a hook I wrote to get myself out of that jam (I didn't have access to the database).

You can adapt it to clear any property settings you like. Adapt it some more to set them as well.

Usage
-----
```bash
$ git clone https://github.com/psvehla/liferay-property-modifier.git
$ cd liferay-property-modifier
$ mvn package
```

Deploy
------
If you're using Liferay Portal with Tomcat, copy the war to the deploy directory.

```
$ cp target/liferay-property-modifier.war $LIFERAY_HOME/deploy/
```

Licence
-------

Copyright 2014 Red Barn Consulting

Licenced under the LGPL Licence, Version 3.0: http://www.gnu.org/licenses/lgpl.html
