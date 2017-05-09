Overview
--------

JaffaRIA provide a single solution for building RIA applications for the Enterprise
It is a combination of the ExtJS core library, ExtJS examples, Community Extensions
and additional features and Extensions provided solely as part of Jaffa RIA

It is intent is a single package that includes a much richer feature set than that 
provided by the default ExtJS deployment.

Although the intent for many of the extensions if for wide use, many are specific to using
Java as the server architecture, using DWR as the integration layer between Java and JavaScript
and the use of other aspects of the server-side Jaffa framework (Data Services for Graph Objects,
Class Meta Data and AOP Rules, Message Internationalization, User and Application Context).s


Note: This module is a packaged version of the ExtJS RIA library from http://extjs.com
      As of ExtJS v2.1 onwards, ExtJS is licensed under a Dual GPLv3 (http://gplv3.fsf.org/)
      and Commercial License. (http://extjs.com/store/extjs/license.php)

Licensing
---------

JaffaRIA unlike the other Jaffa modules has a more complex licensing

1) All original code belonging to the JaffaRIA project is licensed under the LGPL
   The intent of this license is that
    - You can use it and modify it, but those modification must be LGPL and hence Open Source
    - You can use it as-is in any open source or commercial product

2) This library also contains parts of ExtJS both from the main project and the examples
    - The ExtJS Open Source License Exception for Development (http://extjs.com/products/ux-exception.php)
    allows these extensions to be LGPL, but these libraries still depend on ExtJS which you
    must use under either GPL or a Commercial License

3) This library contains contributions provided via the ExtJS forum. These fall into 3 groups
    - Extensions already under a compatible LGPL license
    - Extensions under a broader license (Apache, BSD, etc)
    - Extensions under no license
   In these cases we have put this all in Jaffa under the same LGPL license, and where
   applicable complied with the requirements to acknowledge the use of this source.


In summary what this means to the end user is you can extend JaffaRIA under the terms
of the LGPL, but in addition to must also conform to the ExtJS licensing agreement
which implies you can
  (a) use ExtJS/JaffaRIA in a GPLv3 manor in which you are not "selling" the
      derivative "product"
  (b) use ExtJS/JaffaRIA in a commercial manor such that it is sold as part of a
      "product", in which case you will require a ExtJS Commercial License to deploy
      this "product"

   
Acknowledgements
- ExtJS published Examples
    - Tree Column
- Multi Grouping Grid
    - Initial Work by 
- Grid Filters Plug-in
    - Initial work by
- TreeGrid
    - Initial work by
    



Additional extensions to ExtJS are provided by this module and are licensed as part of the JAFFA framework under the LGPL
(See http://jaffa.cvs.sourceforge.net/*checkout*/jaffa/JaffaCore/licenses/jaffa.license)
