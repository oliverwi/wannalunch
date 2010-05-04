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
        <a href="${createLink(uri: "/")}">
          <img src="${resource(dir: 'img', file: 'logo.png')}" class="headerlogo"/>
        </a>
        <div class="headercontrols">
          <a href="http://twitter.com/wannalunch" target="_blank">Follow us</a>
          <a href="http://twitter.com/wannalunch" target="_blank" class="clearLink">
            <img src="${resource(dir: 'img', file: 'twitterbutton.png')}">
          </a>
          <a href="http://www.facebook.com/wannalunch" target="_blank" class="clearLink">
            <img src="${resource(dir: 'img', file: 'fbbutton.png')}">
          </a>
          <a href="${createLink(controller: 'lunch')}" class="bluebutton">Browse lunches</a>

          <u:isLoggedIn>
            <a href="${createLink(controller: 'lunch', action: 'create')}" class="orangebutton">Create lunch</a>
          </u:isLoggedIn>
          <u:isNotLoggedIn>
            <a href="#info" rel="facebox" class="orangebutton">Create lunch</a>
          </u:isNotLoggedIn>
          <br/>
          <span style="margin-right: 2px;">
            <u:isNotLoggedIn>
              <a href="${twitter.loginLink()}">log in with twitter</a>
            </u:isNotLoggedIn>
            <u:isLoggedIn>
              <u:userInfo field="username"/>
              &bull;
              <a href="${createLink(controller: "profile", action: "edit")}">Profile</a>
              &bull;
              <a href="${twitter.logoutLink()}">Logout</a>
            </u:isLoggedIn>
          </span>
        </div>
      </div>

      <div class="main">
        <g:layoutBody />
        <div class="clearfix"></div>
      </div>

      <div class="footer">
        &copy; 2010 Wannalunch?
        <a href="http://blog.wannalunch.com">Blog</a> &bull;
        <a href="${createLink(controller: "about")}">About</a> &bull;
        <a href="${createLink(controller: "about", action: "signup")}">Business</a> &bull;
        <a href="http://getsatisfaction.com/Wannalunch">Feedback</a> &bull;
        <a href="http://feeds.wannalunch.com/Wannalunch">Subscribe</a>
      </div>
    </div>
  </body>

  <g:javascript>
    jQuery(document).ready(function($) {
      $('a[rel*=facebox]').facebox()
    });
  </g:javascript>
  <div id="info" style="display:none;">
    <p><br/>You need to log in</p>
  </div>

</html>