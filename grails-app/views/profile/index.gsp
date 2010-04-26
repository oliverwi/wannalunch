<meta name="layout" content="main" />

<h3 class="pagetitle">Your profile</h3>
<div class="contactdetails">
  <div class="contactpicture">
    <img src="${twitter.userInfo(field: 'profileImageUrl')}"/>
  </div>
</div>

<div class="content">
  <h2>${twitter.userInfo(field: 'name')}</h2>
  <g:form name="profile" method="post" action="update">
    <table cellpadding="0" cellspacing="0">
      <tr>
        <td>
          <img src="${resource(dir: 'img', file: 'twitterbutton.png')}"></img>
          <span class="grey bold"><twitter:userInfo field="username" /></span>
        </td>
      </tr>
      <tr>
        <td>
          <img src="${resource(dir: 'img', file: 'fbbutton.png')}"></img>
          <input class="profileinput clear" type="text" title="What's your profile link?" name="facebookProfile" />
        </td>
      </tr>
      <tr>
        <td>
          <img src="${resource(dir: 'img', file: 'linkedinbutton.png')}"></img>
          <input class="profileinput clear" type="text" title="What's your profile link?" name="linkedinProfile" />
        </td>
      </tr>
      <tr>
        <td>
          Update profile picture:<br/>
          <input class="fileinput" type="file" name="picture" />
        </td>
      </tr>
      <tr>
        <td>
          <input type="submit" class="bigbluebutton" value="Update profile" />
        </td>
      </tr>
    </table>
  </g:form>
  
  <h2>My upcoming lunches</h2>
  <div class="upcominglunch">
    
  </div>
</div>

<g:javascript src="inputTitle.js"/>
<script type="text/javascript">
  $(document).ready(function() {
     prepareFieldsWithTitle();
  });
</script>