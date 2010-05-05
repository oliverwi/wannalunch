<html>
  <head>
    <title><g:layoutTitle default="wannalunch?" /></title>
    <meta content="EQuoDGgti8bbtMb37cCmr20J9DTuLemcRIa1--GGel8" name="google-site-verification">
    <meta content="4F9CB3F952E59509EB3445636CE726DD" name="msvalidate.01">
    <meta content="05411e97f902d444" name="y_key">
    <meta content="wannalunch? - Simple social platform for setting up lunches. Currently in Tallinn, soon to be in other cities. " name="description">
    <meta content="Wannalunch, Twitter, Facebook, Social Media, Tallinn, Restaurants, Meetup" name="keywords">

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" />
    <link rel="stylesheet" href="${resource(dir: 'facebox', file: 'facebox.css')}" />
    <link rel="shortcut icon" href="${resource(dir:'images', file:'favicon.ico')}" type="image/x-icon" />
    <g:layoutHead />

    <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.2.min.js"></script>
    <g:javascript library="application" />
    <g:javascript src="facebox.js" />

    <script src="http://c.compete.com/bootstrap/d7f94c9f6fce7cd4d6eea7184b4a203f/bootstrap.js" type="text/javascript" async=""></script>
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
        <a href="http://feeds.wannalunch.com/Wannalunch">Subscribe</a> &bull;
      </div>
    </div>

    <script type="text/javascript" charset="utf-8">
      var is_ssl = ("https:" == document.location.protocol);
      var asset_host = is_ssl ? "https://s3.amazonaws.com/getsatisfaction.com/" : "http://s3.amazonaws.com/getsatisfaction.com/";
      document.write(unescape("%3Cscript src='" + asset_host + "javascripts/feedback-v2.js' type='text/javascript'%3E%3C/script%3E"));
    </script>
    <script
      src="http://s3.amazonaws.com/getsatisfaction.com/javascripts/feedback-v2.js"
      type="text/javascript">
    </script>

    <script type="text/javascript" charset="utf-8">
      var feedback_widget_options = {};

      feedback_widget_options.display = "overlay";
      feedback_widget_options.company = "wannalunch";
      feedback_widget_options.placement = "left";
      feedback_widget_options.color = "#222";
      feedback_widget_options.style = "idea";

      var feedback_widget = new GSFN.feedback_widget(feedback_widget_options);
    </script>

    <script type="text/javascript">
      var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
      document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
    </script>

    <g:if env="production">
      <script type="text/javascript">
        try {
      	  var pageTracker = _gat._getTracker("UA-2481746-23");
      	  pageTracker._trackPageview();
      	} catch(err) {}
      </script>
    </g:if>
  </body>

  <g:javascript>
    jQuery(document).ready(function($) {
      $('a[rel*=facebox]').facebox({
        loadingImage: "<g:resource dir="facebox" file="loading.gif" />", 
        closeImage: "<g:resource dir="facebox" file="closelabel.gif" />"
      });
    });
  </g:javascript>
  <div id="info" style="display:none;">
    <p><br/>You need to log in</p>
  </div>
  
  <g:render template="/templates/helpPopup" />

</html>