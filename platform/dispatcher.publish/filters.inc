    /0001 { /type "deny" /glob "*" } # Deny all. Allow specific entries.


    # Enable specific mime types in non-public content directories
    /0200 { /type "allow" /glob "* *.css *" }
    /0201 { /type "allow" /glob "* *.gif *" }
    /0202 { /type "allow" /glob "* *.js *" }
    /0203 { /type "allow" /glob "* *.png *" }
    /0204 { /type "allow" /glob "* *.swf *" }
    /0205 { /type "allow" /glob "* *.jpeg *" }
    /0206 { /type "allow" /glob "* *.jpg *" }
    /0207 { /type "allow" /glob "* *.ico *" }
    /0208 { /type "allow" /glob "* *.svg *" }
    /0209 { /type "allow" /glob "* *.woff *" }
    /0210 { /type "allow" /glob "* *.ttf *" }

    # Allow POSTs to form selectors
    /0300 { /type "allow" /glob "POST /content/[.]*.form.html" }
    /0310 { /type "allow" /glob "* /bin/statistics/tracker/*" }

    # Enable Adobe AEM personalization
    /0350 { /type "allow" /glob "* /libs/cq/personalization/*" }

    # Security and content grabbing
    /0400 { /type "deny" /glob "GET *.infinity.json*" }
    /0401 { /type "deny" /glob "GET *.-1.json*" }
    /0402 { /type "deny" /glob "GET *.tidy.json*" }
    /0403 { /type "deny" /glob "GET *.sysview.xml*" }
    /0404 { /type "deny" /glob "GET *.docview.json*" }
    /0405 { /type "deny" /glob "GET *.docview.xml*" }
    /0406 { /type "deny" /glob "* /etc/replication*" }
    /0407 { /type "deny" /glob "* /etc/replication.infinity.json*" }
    /0408 { /type "deny" /glob "* *.2json*" }
    /0409 { /type "deny" /glob "GET *.*[0-9].json*" }

    # Abusive query requests
    /0450 { /type "deny" /glob "* *querybuilder.json*" } # Deny /content queries
    /0451 { /type "deny" /glob "* *.query.json*" } # Deny /content queries
    /0452 { /type "deny" /glob "* *.feed.xml*" } # Deny feed requests

    # Site verification and crawler access
    /0455 { /type "allow" /glob "GET /robots.txt*" }
    /0456 { /type "allow" /glob "GET /BingSiteAuth.xml*" } # Microsoft Bing

    # Custom allow URLs
    # <none>

    # Adobe documented URLs
    /1000 { /type "allow" /glob "* /libs/cq/i18n/*" }
    /1001 { /type "allow" /glob "* /libs/cq/ui/widgets.js*" }
    /1002 { /type "allow" /glob "* /libs/cq/security/userinfo.json*" }
    /1003 { /type "allow" /glob "* /libs/wcm/stats/tracker.js*" }
    /1004 { /type "allow" /glob "* /etc/segmentation.segment.js*" }
    /1005 { /type "allow" /glob "* /etc/designs/*" }
    /1006 { /type "allow" /glob "* /etc/clientlibs/*" }
    /1007 { /type "allow" /glob "* /etc/clientcontext/*" }
