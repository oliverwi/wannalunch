<meta name="layout" content="main" />

<h3 class="pagetitle">${user.name}'s profile</h3>
<div class="contactdetails">
  <div class="contactpicture">
    <img src="${user.profileImageUrl}"/>
  </div>
</div>

<div class="content">
  <g:form name="profile" method="post" action="update">
    <table cellpadding="0" cellspacing="0">
      <g:if test="${user.twitterAccount}">
        <tr>
          <td>
            <img src="${resource(dir: 'img', file: 'twitterbutton.png')}"/>
            <span class="grey bold">
              <a href="${twitter.linkToProfile(user: user)}" target="_blank">
                <g:fieldValue bean="${user}" field="twitterAccount.username"/>
              </a>
            </span>
          </td>
        </tr>
      </g:if>
      <g:if test="${user.facebookAccount}">
	      <tr>
	        <td>
	            <img src="${resource(dir: 'img', file: 'fbbutton.png')}"/>
	            <a href="${facebook.linkToProfile(user: user)}" target="_blank">Facebook profile</a>
	        </td>
	      </tr>
      </g:if>
      <g:if test="${user.linkedInProfile}">
	      <tr>
	        <td>
	          <img src="${resource(dir: 'img', file: 'linkedinbutton.png')}"/>
	          <a href="${user.linkedInProfile}" target="_blank">
	            ${user.linkedInProfile}
	          </a>
	        </td>
	      </tr>
	    </g:if>
    </table>
  </g:form>

  <h2>${user.name}'s upcoming lunches</h2>
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