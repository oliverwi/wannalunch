<html>
  <head>
    <title><g:layoutTitle default="wannalunch?" /></title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" />
    <link rel="stylesheet" href="${resource(dir: 'facebox', file: 'facebox.css')}" />
    <link rel="shortcut icon" href="${resource(dir:'images', file:'favicon.ico')}" type="image/x-icon" />
    <g:layoutHead />
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.2.min.js"></script>
    <g:javascript library="application" />
    <g:javascript src="facebox.js" />
  </head>
  <body>
    <div class="wrapper">
      <div class="header">
        <img src="${resource(dir: 'img', file: 'logo.png')}" class="headerlogo"/>
        <div class="headercontrols">          
          <a href="http://twitter.com/wannalunch">Follow us</a>
          <a href="http://twitter.com/wannalunch" class="clearLink">
            <img src="${resource(dir: 'img', file: 'twitterbutton.png')}">
          </a>
          <a href="http://www.facebook.com/wannalunch" class="clearLink">
            <img src="${resource(dir: 'img', file: 'fbbutton.png')}">
          </a>
          <a href="${createLink(controller: 'lunch', action: 'show')}" class="bluebutton">Browse lunches</a>
          <a href="${createLink(controller: 'lunch', action: 'create')}" class="orangebutton">Create</a>
          <br/>
          <span>
            <user:isNotLoggedIn>
              <a href="${twitter.loginLink()}">log in with twitter</a>
            </user:isNotLoggedIn>
            <user:isLoggedIn>
              <user:userInfo field="username"/>
              /
              <a href="${createLink(controller: "profile", action: "edit")}">Profile</a>
              /
              <a href="${twitter.logoutLink()}">Logout</a>
            </user:isLoggedIn>
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
  
  <g:javascript>
    jQuery(document).ready(function($) {
      $('a[rel*=facebox]').facebox()
    });
  </g:javascript>
  <div id="logininfo" style="display:none;">
    <p><br/>You need to log in</p>
  </div>
  
</html>