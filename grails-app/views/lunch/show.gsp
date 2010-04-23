<meta name="layout" content="main" />

<h4>Liis Peetermann's lunch</h4>
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
  <h2>${lunch.topic}</h2>
  <p class="grey">
    ${lunch.description}
  </p>
  <p class="grey bold author">Liis Peetermann</p>
  
  <div class="eventdetails bold">
    <p>On ${lunch.date} at ${lunch.time}</p>
    @ ${lunch.location}
    <a class="contentlink" href="">See special offers</a>
  </div>
  
  <a href=""><button class="bigbluebutton">Lunch!</button></a>
  
  <a href="${createLink(action: 'show', params: [id: nextId])}">
    <button class="biggreybutton">Next</button>
  </a>

  <div class="postcommentbox">
    <img src="img/comments1.png"/>
    <div class="textareawrapper">
      <textarea></textarea>
    </div>
    <p><button class="greybutton">Comment</button></p>
  </div>

  <div class="comment">
    <img src="img/comments2.png"/>
    <p><span class="bold">J&uuml;ri Kask</span> <span class="small">Today</span></p>
    i'm really interested, but for me it's better to meet on friday... Still interested?
  </div>

  <div class="comment">
    <img src="img/comments2.png"/>
    <p><span class="bold">J&uuml;ri Kask</span> <span class="small">Today</span></p>
    I have a friend who knows much about this subject. Maybe she could join us?
  </div>
</div>

<div class="participants grey">
  <h3>Wanna!</h3>
  <div>
    <img src="img/wanna.png"/><br/>Sushi Cat
  </div>
  <div>
    <img src="img/wanna.png"/><br/>Mari Kuusk
  </div>
</div>