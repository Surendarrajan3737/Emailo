<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
      <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
      <title>Insert title here</title>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <!-- Compiled and minified CSS -->
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
      <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
      <link rel="stylesheet" href="styles.css">
      <title>inbox</title>
      <script src="https://kit.fontawesome.com/4a39ce030f.js" crossorigin="anonymous"></script>

</head>

<body style="background-image: radial-gradient(rgb(32, 16, 16),rgb(12, 12, 12));position: relative;">
      <div
            style="width: 100%; background-color: rgba(255, 255, 255, .15);backdrop-filter: blur(5px); border-bottom: 2px solid rgb(125, 195, 255); display: block;justify-content: space-around;">
            <a href="home.html"><img id="logo_gif" src="./Resources/emailo-gif.gif" alt="Home Page"
                        style="position: absolute;;margin: 1%;width: 10%;"></a>

            <nav id="search_nav" style="margin-bottom:5px;margin-top: 0px;">
                  <div class="nav-wrapper" style="background-color: rgb(146, 148, 151);border-radius: 10px;">
                        <form>
                              <div class="input-field">
                                    <input id="search" type="search" required
                                          style="color: rgb(61, 61, 61);border-radius: 10px;margin-top:5px ;">
                                    <label class="label-icon" for="search" style="color: rgb(65, 138, 43);"><i
                                                class="material-icons">search</i></label>
                                    <i class="material-icons" data->close</i>
                              </div>
                        </form>
                  </div>
            </nav>
            <div class=" logoutBox" style="cursor: pointer;">
                  <div> <i class="medium material-icons white-text">exit_to_app</i></div>
            </div>
      </div>

      <div style="display:flex;flex-direction: row; width: 100%;justify-content: space-around;margin-top: 1%;">
            <div class="nav_stip z-depth-3"
                  style="background-color: rgba(255, 255, 255, .15);backdrop-filter: blur(5px); border-radius: 10px;width: 200px;">
                  <div class="nav_elements"><a href="#!" style="font-size: large;"><i class="small material-icons"
                                    id="inbox">inbox</i>Inbox</a></div>

                  <div class="nav_elements"><a href="#!" style="font-size: large;"><i class="small material-icons"
                                    id="draft">drafts</i>Drafts</a></div>

                  <div class="nav_elements"><a href="#!" style="font-size: large;"><i class="small material-icons"
                                    id="sent">send</i>Sent</a></div>

                  <div class="nav_elements"><a href="#!" style="font-size: large;"><i class="small material-icons"
                                    id="starred">star</i>Starred</a></div>

                  <div class="nav_elements"><a href="#!" style="font-size: large;"><i class="small material-icons"
                                    id="Trash">delete</i>Trash</a></div>

                  <div class="whiteBox" style="background-color: transparent"></div>
                  <div class="util_elements"><a href="https://www.github.com" style="font-size: large;" target="_blank"
                              rel="noopener"><i class="fab fa-github"></i></a>
                  </div>

                  <div class="util_elements"><a href="settings.html" style="font-size: large;"><i
                                    class="fas fa-users-cog"></i></a>
                  </div>


            </div>
            <div id="homeContainer" class="col s12 m2 z-depth-3"
                  style="display: inline-block;max-height:600px;background-color: rgba(255, 255, 255, .15);backdrop-filter: blur(5px);position: relative;overflow-y: scroll;">
                  
            </div>
            <div class="util_strip z-depth-3"
                  style="background-color: rgba(255, 255, 255, .15);backdrop-filter: blur(5px);">

                  <div class="util_elements"><a href="#!" style="font-size: large;"><i
                                    class="far fa-calendar-alt"></i></a>
                  </div>
                  <div class="util_elements"><a href="#!" style="font-size: large;"><i
                                    class="fas fa-sticky-note "></i></a>
                  </div>

                  <div class="util_elements"><a href="#!" style="font-size: large;"><i class="fas fa-tasks"
                                    alt="tasks"></i></a>
                  </div>

                  <div class=" util_elements cIcon" onclick="addComposeBox()"
                        style="display: block; align-items: baseline;font-size: large;">
                        <i class="fas  fa-plus"
                              style="margin-left: 15%; display: block;color: aliceblue;font-size: 4em;"></i>
                        <span style="font-size: 22px;color: aliceblue;display: block;">&nbsp;Compose</span>
                  </div>


            </div>
      </div>
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
      <script defer>
            var Username = sessionStorage.getItem('Username');
            var mailID = sessionStorage.getItem(Username);
            console.log(Username, mailID);
            function addListeners() {
                  let st = document.getElementById('star_inbox_read');
                  st.addEventListener('click', () => {
                        if (st.innerHTML == 'star') {
                              st.innerHTML = 'star_border'
                        } else {
                              st.innerHTML = 'star'
                        }
                  });
                  document.querySelector('.logoutBox').addEventListener('click', () => {
                        sessionStorage.clear();
                        location.href = 'index.html';
                  });
            }

            
//             var homeContents;
//             var compID = 1;
//             var flag = true;
//             function addComposeBox() {
//                   var composeBox = `<div class="composeWrap">
//                   <div class="headerBox">
//                         <p style="margin-left:3%;transform: translateY(-10px);" color:white>New Message<p>
//                         <div id='closeCompose${compID}' class="closeCompose" onclick()="(e)=> {
//                         let compose = e.target.parentNode.parentNode;
//                         console.log(compose.id);
//                         compose.remove();
//                   }">X</div>
//                   </div>
//                   <div style="height:450px;width:500px">
//                         <div class="head_area">
//                               <div class="row" style="margin-bottom: 0px;">
//                                     <!-- compose -->
//                                     <form action="https://www.google.com" method="POST">
//                                           <input type="text" placeholder="Recepients:" id="To" name="To" />
//                                           <input type="text" id="Subject" placeholder="Subject" name="Subject" />
//                                           <textarea placeholder="Content Here!" id="mailContent" class="txtarea" spellcheck="true"></textarea>
//                                           <br>
//                                           <label for="myfile">Select files:</label>
//                                           <input type="file" id="myfile" name="myfile" multiple style="float: left;">
//                                           <button id="sendBtn" type="submit" value="Send" >
//                                                 <i class="material-icons">send</i>
//                                           </button>
//                                           <button id="draftBtn" type="submit" value="Draft" >
//                                                 <i class="material-icons">drafts</i>
//                                           </button>

//                                     </form>
//                                     <div class="col l3 m4 s12">
//                                           <div class="row extraIcons">
//                                           </div>
//                                     </div>
//                               </div>
//                         </div>
//                   </div>
//             </div>
// `;
//                   console.log(compID)
//                   var cmpBox = document.getElementById("cmpBX");
//                   cmpBox.innerHTML += composeBox;
//                   document.getElementById(`closeCompose${compID}`).onclick = function (e) {
//                         let compose = e.target.parentNode.parentNode;
//                         console.log(compose.id);
//                         compose.remove();
//                   }
//                   compID++;
//             }

            var fetchmailcontent = async (mailID) => {
                  let form = new FormData();
                  form.set("mailID", mailID);
                  console.log(mailID+'---------------->')
                  console.log(JSON.stringify(form));
                  let response = await fetch('FetchMailcontent', {
                        method: 'POST',
                        body: form,
                  }).then((data) => data.json())
                        .then(data => data)
                        .catch((err) => {
                              console.log(err)
                        })
                  console.log(response)
                  renderMail(response);
            }
            fetchmailcontent(mailID);
            function renderMail(mailCnt){
                  let name= mailCnt['mailcontent'].sname;
                  let mail= mailCnt['mailcontent'].smail;
                  let subject= mailCnt['mailcontent'].subject;
                  let content=mailCnt['mailcontent'].message;
                  console.log(name)
                  console.log(mail)
                  console.log(subject)
                  console.log(content)
                  var mailBOX = ``;
                  document.getElementById("mailUname").innerHTML=name
                  document.getElementById("mailUID").innerHTML=mail
                  document.getElementById("mailSubject").innerHTML=subject
                  document.getElementById("mailText").innerHTML=content
                  addListeners();
            }

      </script>
      <div id="cmpBX">

      </div>
</body>

</html>