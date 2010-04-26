<html>
  <head>
    <title><g:layoutTitle default="wannalunch?" /></title>
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <link rel="shortcut icon" href="${resource(dir:'images', file:'favicon.ico')}" type="image/x-icon" />
    <g:layoutHead />
    <g:javascript library="application" />
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.2.min.js"></script>
  </head>
  <body>
    <div class="wrapper">
      <div class="header">
        <img src="${resource(dir: 'img', file: 'logo.png')}" class="headerlogo"/>
        <div class="headercontrols">
          <a href="http://twitter.com/wannalunch">Follow us</a>
          <a href="http://twitter.com/wannalunch">
            <img src="${resource(dir: 'img', file: 'twitterbutton.png')}">
          </a>
          <a href="http://www.facebook.com/wannalunch">
            <img src="${resource(dir: 'img', file: 'fbbutton.png')}">
          </a>
          <a href="${createLink(controller: 'lunch', action: 'show')}" class="bluebutton">Browse lunches</a>
          <a href="${createLink(controller: 'lunch', action: 'create')}" class="orangebutton">Create</a>
          <br/>
          <span>
            <twitter:isNotLoggedIn>
              <a href="${twitter.loginLink()}">log in with twitter</a>
            </twitter:isNotLoggedIn>
            <twitter:isLoggedIn>
              <a href="${twitter.logoutLink()}">log out <twitter:userInfo field="screenName"/></a>
            </twitter:isLoggedIn>
          </span>
        </div>
      </div>

      <div class="main">
        <g:layoutBody />
        <div class="clearfix"></div>
      </div>

      <div class="footer">
        &copy; 2010 wannalunch - About - sayatme
      </div>
    </div>
  </body>
</html>