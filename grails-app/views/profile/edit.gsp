<meta name="layout" content="main" />

<h3 class="pagetitle">Your profile</h3>
<div class="contactdetails">
  <div class="contactpicture">
    <img src="${u.userInfo(field: 'profileImageUrl')}"/>
  </div>
</div>

<div class="content">
  <h2>${u.userInfo(field: 'name')}</h2>
  <g:form name="profile" method="post" action="update" enctype="multipart/form-data">
    <table cellpadding="0" cellspacing="0">
      <tr>
        <td>
          <img src="${resource(dir: 'img', file: 'twitterbutton.png')}"/>
          <span class="grey bold">
            <g:if test="${user.twitterAccount}">
              <a href="${twitter.linkToProfile(user: user)}" target="_blank">
                <g:fieldValue bean="${user}" field="twitterAccount.username"/>
              </a>
            </g:if>
            <g:else>
              <a href="${twitter.loginLink(merge: true)}">Merge my Twitter account</a>
            </g:else>
          </span>
        </td>
      </tr>
      <tr>
        <td>
          <img src="${resource(dir: 'img', file: 'fbbutton.png')}"/>
          <span class="grey bold">
            <g:if test="${user.facebookAccount}">
              <a href="${facebook.linkToProfile(user: user)}" target="_blank">Facebook profile</a>
            </g:if>
            <g:else>
              <a href="${facebook.loginLink(merge: true)}">Merge my Facebook account</a>
            </g:else>
          </span>
        </td>
      </tr>
      <tr>
        <td>
          <img src="${resource(dir: 'img', file: 'emailbutton.png')}"/>
          <g:textField class="profileinput clear" type="text" title="What's your email address?" name="email" value="${user.email}" />
        </td>
      </tr>
      <tr>
        <td>
          <img src="${resource(dir: 'img', file: 'linkedinbutton.png')}"/>
          <g:textField class="profileinput clear" type="text" title="What's your profile link?" name="linkedInProfile" value="${user.linkedInProfile}" />
        </td>
      </tr>
      <tr>
        <td>
          Update profile picture:<br/>
          <input class="fileinput" type="file" name="profileImage" />
        </td>
      </tr>
      <tr>
        <td>
          <input type="submit" class="bigbluebutton" value="Update profile" />
          <span class="attention small bold">
            <g:if test="${flash.message}">
              ${flash.message}
            </g:if>
          </span>
        </td>
      </tr>
    </table>
  </g:form>

  <h2>My upcoming lunches</h2>
  <g:each var="lunch" in="${upcomingLunches}">
    <g:render template="/templates/upcomingLunch" model="[lunch: lunch]" />
  </g:each>
</div>

<g:javascript src="inputTitle.js"/>
<script type="text/javascript">
  $(document).ready(function() {
     prepareFieldsWithTitle();
  });
</script>