<meta name="layout" content="main" />

<h3 class="pagetitle">Create lunch</h3>
<div class="contactdetails">
  <div class="contactpicture">
    <img src=""/>
  </div>
  <div class="contactbuttons">
    <img src="${resource(dir: 'img', file: 'twitterbutton.png')}" class="contactbutton"/>
    <img src="${resource(dir: 'img', file: 'fbbutton.png')}" class="contactbutton"/>
  </div>
</div>

<div class="content">
<g:form class="createlunch" action="save" method="post">

  <g:textField name="topic" value="${lunch.topic}" class="topic bold clear" value="What's your lunch topic?"/>
  <g:textArea name="description" value="${lunch.description}" class="description clear">All other cool things you want to discuss.</g:textArea>
  <g:textField name="date" value="${lunch.date}" class="datetime clear" />
  <g:textField name="time" value="${lunch.time}" class="datetime clear" />
  <g:textField name="location" value="${lunch.location}" class="location clear" value="Let's meet @"/>
  
  <div class="buttonsrow">
    <table cellpadding="0" cellspacing="0">
      <tr>
        <td>
          <button id="lucky" class="bluebutton ignore">I feel lucky!</button>
        </td>
        <td class="luckytext bold" style="display: table-cell;">
          It's your lucky day! You just got 10% of discount in all the special lunches.
          <span class="attention">Don't forget to tell your scret code: wannalunch.</span>
        </td>
      </tr>
    </table>
  </div>
  
  <div class="buttonsrow">
    <input class="bigbluebutton" type="submit" value="Create lunch!"></input>
    <a>
      <button class="biggreybutton">Browse lunches</button>
    </a>
  </div>

</g:form>
</div>

<div class="speciallunches grey bold">
</div>