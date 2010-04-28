<meta name="layout" content="main" />

<h3 class="pagetitle">Create lunch</h3>
<div class="contactdetails">
  <div class="contactpicture">
    <img src="${u.userInfo(field: 'profileImageUrl')}"/>
  </div>
  <div class="contactbuttons">
    <img src="${resource(dir: 'img', file: 'twitterbutton.png')}" class="contactbutton"/>
    <img src="${resource(dir: 'img', file: 'fbbutton.png')}" class="contactbutton"/>
  </div>
</div>

<div class="content">
<g:form name="createlunchform" class="createlunch" action="save" method="post">

  <g:textField name="topic" value="${lunch.topic}" class="topic bold clear${hasErrors(bean: lunch, field: 'topic', ' error')}" title="What's your lunch topic?"/>
  <g:textArea name="description" value="${lunch.description}" class="description clear${hasErrors(bean: lunch, field: 'description', ' error')}" title="All other cool things you want to discuss" />
  <g:textField name="date" value="${lunch.date}" class="datetime clear${hasErrors(bean: lunch, field: 'date', ' error')}" title="Pick a date" />
  <g:textField name="time" value="${lunch.time}" class="datetime clear${hasErrors(bean: lunch, field: 'time', ' error')}" title="Time" />
  <g:textField name="location" value="${lunch.location}" class="location clear${hasErrors(bean: lunch, field: 'location', ' error')}" title="Let's meet @ restaurant"/>

  <div class="buttonsrow">
    <table cellpadding="0" cellspacing="0">
      <tr>
        <td>
          <input type="button" id="lucky" class="bluebutton" value="I feel lucky!" />
        </td>
        <td id="luckytext" class="luckytext bold" style="display: table-cell; visibility: hidden;">
          It's your lucky day! You just got 10% of discount in all the special lunches.
          <span class="attention">Don't forget to tell your scret code: wannalunch.</span>
        </td>
      </tr>
    </table>
  </div>

  <div class="buttonsrow">
    <input type="submit" class="bigbluebutton" value="Create lunch!"></input>
    <a href="${createLink(controller: "lunch")}">
      <input type="button" class="biggreybutton" value="Browse lunches" />
    </a>
  </div>

</g:form>
</div>

<div class="speciallunches grey bold">
</div>

<link rel="stylesheet" href="${resource(dir:'css/flick',file:'jquery-ui-1.8.custom.css')}" />
<g:javascript src="jquery-ui-1.8.custom.min.js"/>
<g:javascript src="inputTitle.js"/>
<script type="text/javascript">
  $(document).ready(function() {
	   prepareFieldsWithTitle();
  });

  $(function() {
    $('#date').datepicker({ dateFormat: 'yy-mm-dd', showAnim: '' });
  });

  $("#lucky").click(function() {
    $("#luckytext").css("visibility", "visible");
  });
</script>