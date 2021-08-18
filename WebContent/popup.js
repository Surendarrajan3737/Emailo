var oldmessage;
var popup=document.createElement('div');
popup.className = 'popupdiv';
popup.id = 'Popupdiv';
var popupmessage = null;
var popelement;
try {
    popelement = document.getElementById('popup-js');
    popupmessage = popelement.innerHTML;
    popelement.innerHTML = "";
    popelement.appendChild(popup)
}
catch (e) {
    console.log(e);
}
var simplepopup =`<div id="Popupcontent" class="popupcontent">
<span class="close" >&times;</span>
<p id="popmessage">${popupmessage}</p>
</div>`;

var simplepopupstyle = `display: none;  position: fixed;  z-index: 1;  left: 0;
top: 0;  width: 100%;  height: 100%;  overflow: auto;
background-color: rgb(0,0,0);  background-color: rgba(0,0,0,0.4);`;

var simplepopupcontentstyle = `background-color: inherit;
backdrop-filter: blur(6px);
color:white;
margin: 15% auto;
padding: 20px;
border: 1px solid #888;
width: 80%;
border-radius:10px
`;

var popupclosestyle =` color: inherit;
float: right;
font-size: 28px;
font-weight: bold;`

var popupclosehover = `#Popupcontent .close:hover,
.close:focus {
    color: black;
    text-decoration: none;
    cursor: pointer;
}
.popupcontent {
  position: relative;
  background-color: #fefefe;
  margin: auto;
  padding: 0;
  border: 1px solid #888;
  width: 80%;
  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19);
  animation-name: animatetop;
  animation-duration: 0.4s
}

/* Add Animation */
@keyframes animatetop {
  from {top: -300px; opacity: 0}
  to {top: 0; opacity: 1}
}
`;
popup.innerHTML = simplepopup;
var popupcontent = document.getElementById('Popupcontent');
popup.style.cssText = simplepopupstyle;
popupcontent.style.cssText = simplepopupcontentstyle;
var popupclose = popupcontent.querySelector('.close');
popupclose.style.cssText = popupclosestyle;
var styleelement = document.createElement('style');
styleelement.innerHTML = popupclosehover;
popupcontent.append(styleelement);
popupclose.onclick = () => {
    popup.style.display = "none";
    document.getElementById('popmessage').innerHTML = oldmessage;
}
popup.appendChild(popupcontent);
function makevisible(){
    if (popup.style.display == "none") {
        popup.style.display = "block";
    }
    else {
        popup.style.display = "none";
    }
}
function addPopuptoElementEvent(element, event)
{
    element.addEventListener(event, () => {
        makevisible()
    })
}
function triggerPopup(message)
{
    oldmessage = document.getElementById('popmessage').innerHTML;   
    document.getElementById('popmessage').innerHTML = message;
    makevisible()
}