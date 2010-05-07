<meta name="layout" content="main" />

<h3 class="pagetitle">Create lunch</h3>
<div class="contactdetails">
  <div class="contactpicture">
    <img src="${u.userInfo(field: 'profileImageUrl')}"/>
  </div>
</div>

<div class="content">
<g:form name="createLunchForm" class="createlunch" action="save" method="post">

  <g:textField name="topic" value="${lunch.topic}" class="topic bold clear${hasErrors(bean: lunch, field: 'topic', ' error')}" title="What's your lunch topic?"/>
  <g:textArea name="description" value="${lunch.description}" class="description clear${hasErrors(bean: lunch, field: 'description', ' error')}" title="All other cool things you want to discuss" />
  <g:textField name="date" value="${lunch.date}" class="datetime clear${hasErrors(bean: lunch, field: 'date', ' error')}" title="Pick a date" />
  <g:textField name="time" value="${lunch.time}" class="datetime clear${hasErrors(bean: lunch, field: 'time', ' error')}" title="Time" />
  <g:textField name="location" value="${lunch.location}" class="location clear${hasErrors(bean: lunch, field: 'location', ' error')}" title="Restaurant @ ${u.city()}"/>

  <div class="createLunchFormControlsLine">
    <g:each in="${com.wannalunch.domain.Lunch.PaymentOption.values()}">
      <g:radio id="${it.name()}" name="paymentOption" value="${it.name()}" checked="${lunch.paymentOption == it}"/><label for="${it.name()}">${it.text}</label>
    </g:each>
  </div>

  <%--
  <div class="createLunchFormControlsLine">
    <g:checkBox name="creatorWantsNotifications" value="${lunch.creatorWantsNotifications}" />
    <label for="creatorWantsNotifications">I want to receive e-mail notifications for this lunch</label>
  </div>
  --%>

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
    <input type="button" id="createFormButton" class="bigbluebutton clearLink" value="Create lunch!" />
  </div>

</g:form>
</div>

<div class="speciallunches grey bold">
  Special lunches
  <a href="${createLink(controller: "about", action: "partners")}">See all</a>
  <table cellspacing="0" cellpadding="0">
    <tr>
      <td><img src="${resource(dir: 'img', file: 'part_1_africankitchen.jpg')}"></td>
      <td><img src="${resource(dir: 'img', file: 'part_6_cestlavie.jpg')}"></td>
    </tr>
    <tr>
      <td><img src="${resource(dir: 'img', file: 'part_2_bonaparte.jpg')}"></td>
      <td><img src="${resource(dir: 'img', file: 'part_7_chakra.jpg')}"></td>
    </tr>
    <tr>
      <td><img src="${resource(dir: 'img', file: 'part_3_bossanova.jpg')}"></td>
      <td><img src="${resource(dir: 'img', file: 'part_8_clazz.jpg')}"></td>
    </tr>
    <tr>
      <td><img src="${resource(dir: 'img', file: 'part_4_cafetao.jpg')}"></td>
      <td><img src="${resource(dir: 'img', file: 'part_9_cubanita.jpg')}"></td>
    </tr>
    <tr>
      <td><img src="${resource(dir: 'img', file: 'part_5_cafevs.jpg')}"></td>
      <td><img src="${resource(dir: 'img', file: 'part_10_fahle.jpg')}"></td>
    </tr>
    <tr>
      <td><img src="${resource(dir: 'img', file: 'part_11_island.jpg')}"></td>
      <td><img src="${resource(dir: 'img', file: 'part_12_silk.jpg')}"></td>
    </tr>
  </table>
</div>

<link rel="stylesheet" href="${resource(dir:'css/flick',file:'jquery-ui-1.8.custom.css')}" />
<link rel="stylesheet" href="${resource(dir:'css',file:'timePicker.css')}" />
<g:javascript src="inputTitle.js"/>
<g:javascript src="jquery-ui-1.8.custom.min.js"/>
<g:javascript src="jquery.timePicker.js"/>
<script type="text/javascript">
  $(document).ready(function() {
	   prepareFieldsWithTitle();
  });

  $(function() {
    $('#date').datepicker({ dateFormat: 'yy-mm-dd', showAnim: '' });
    $('#time').timePicker();
  });

  $("#lucky").click(function() {
    $("#luckytext").css("visibility", "visible");
  });
</script>


<div id="requestEmailContainer" style="display:none;">
  <div id="requestEmail" class="small grey">
    <p>
      <br/>
      We need your e-mail address to send you notifications about your lunch.<br/>
      You can always update your e-mail in your profile page.<br/>
    </p>
    <br/>
    E-mail: <g:textField name="email" class="location clear" value="${lunch.creator.email}" style="border: 1px solid #cacaca; width: 250px;" />
    <br/>
    <div id="ajaxMessage"></div>
    <br/>
    <input type="button" id="createFormWithEmailButton" class="bluebutton clearLink" value="Thanks!" />
    <br/>
    <br/>
  </div>
</div>

<g:javascript>
  $("#createFormButton").click(function() {

    <g:if test="${lunch.creator.email}">
	    $("#createLunchForm").submit()
    </g:if>
    <g:else>
      if ($('#creatorWantsNotifications:checked').val() !== undefined) {
        jQuery.facebox($("#requestEmailContainer").html())

        // facebox duplicates the code into a #facebox div so both
        // IDs are needed to select the displayed element
        $("#facebox #email").focus()
        $("#facebox #createFormWithEmailButton").click(function() {
          saveEmail()
        });
      } else {
        $("#createLunchForm").submit()
      }
    </g:else>

  });

  function saveEmail() {
    $.ajax({
      method: "post",
      url: "${createLink(controller: 'profile', action: 'updateEmail')}",
      data: "email=" + $("#facebox #email").val(),
      dataType: "text",
      success: function() {
        $("#createLunchForm").submit()
      },
      error: function() {
        $("#facebox #ajaxMessage").html('Oops! An error occurred! Please check if the address you typed is correct!<br/>')
      }
    })
  }
</g:javascript>