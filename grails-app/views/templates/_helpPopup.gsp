<div id="step1Container" style="display: none;">
	<div id="helpPopupStep1" class="helpPopup">
	  <div class="helpTitle">
	    <span class="blue">Step 1</span> <span class="grey">Create your lunch</span>
	  </div>
	  <img class="helpImg" src="${resource(dir: 'img/help', file: 'help1.png')}" />
	  <ul class="helpPoints">
	    <li>
	      <div>Set an eye-catching topic you would like to discuss during your lunch</div>
	      <div class="helpEg">e.g. Let's talk about successful start-up companies</div>
	    </li>
	    <li>
	      <div>Add some details</div>
	      <div class="helpEg">e.g. I have just started a new company and could use some advice</div>
	    </li>
	    <li>
	      <div>Choose the date and time, suggest where you'd like to lunch and create your lunch!</div>
	    </li>
	  </ul>
	  
	  <div class="helpNav">
	    <span class="next">
	      <a href="#" name="helpGoToStep2">
	        <img src="${resource(dir: 'img/help', file: 'arrow-next.png')}" />
	      </a>
	    </span>
	    <div class="clearfix"></div>
	  </div>
	</div>
</div>

<div id="step2Container" style="display: none;">
  <div id="helpPopupStep2" class="helpPopup">
    <div class="helpTitle">
      <span class="blue">Step 2</span> <span class="grey">Accept your lunchers</span>
    </div>
    <img class="helpImg" src="${resource(dir: 'img/help', file: 'help2.png')}" />
    <ul class="helpPoints">
      <li>You will see a list of people interested in participating on your lunch</li>
      <li>You can accept people to your lunch by clicking on the accept link under their names</li>
      <li>Accepted people will move to the accepted box</li>
    </ul>
  
    <div class="helpNav">
      <span class="prev">
        <a href="#" name="helpGoToStep1">
          <img src="${resource(dir: 'img/help', file: 'arrow-prev.png')}" />
        </a>
      </span>
      <span class="next">
        <a href="#" name="helpGoToStep3">
          <img src="${resource(dir: 'img/help', file: 'arrow-next.png')}" />
        </a>
      </span>
      <div class="clearfix"></div>
    </div>    
  </div>
</div>

<div id="step3Container" style="display: none;">
  <div id="helpPopupStep3" class="helpPopup">
    <div class="helpTitle">
      <span class="blue">Step 3</span> <span class="grey">Participate in lunches</span>
    </div>
    <img class="helpImg" src="${resource(dir: 'img/help', file: 'help3.png')}" />
    <ul class="helpPoints">
      <li>Browse all lunches and choose the one you would like to participate</li>
      <li>Click Lunch! and you will be added to the wanna list of this lunch</li>
      <li>The lunch creator can then accept you to participate</li>
      <li>And you will have an awesome lunch with cool new people!</li>
    </ul>
  
    <div class="helpNav">
      <span class="prev">
        <a href="#" name="helpGoToStep2">
          <img src="${resource(dir: 'img/help', file: 'arrow-prev.png')}" />
        </a>
      </span>
      <div class="clearfix"></div>
    </div>
  </div>
</div>

<g:javascript>

  $("a[href='#showHelp']").click(function() {
    showHelp($("#step1Container"), true)
  });
  
  function showHelp(stepContainer, openBox) {
    if (openBox) {
      jQuery.facebox(stepContainer.html())
    } else {
      $("#facebox div[class='content']").html(stepContainer.html())
    }
    
    $("#facebox a[name='helpGoToStep1']").click(function() {
      showHelp($("#step1Container"), false)
    })
    
    $("#facebox a[name='helpGoToStep2']").click(function() {
      showHelp($("#step2Container"), false)
    })
    
    $("#facebox a[name='helpGoToStep3']").click(function() {
      showHelp($("#step3Container"), false)
    })
  }
  
</g:javascript>