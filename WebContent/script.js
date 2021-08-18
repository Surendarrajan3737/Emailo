var Username = 'defaultUser'
Username = sessionStorage.getItem("Username");
if (Username == undefined) {
    //location.href = 'index.html';
}
function addmailListeners() {
    let lists = document.getElementById('homeContainer').querySelectorAll('li');
    lists.forEach(list => {

        let st = list.querySelector('.starIcon');
        st.addEventListener('click', (e) => {
            if (st.innerHTML == 'star') {
                st.innerHTML = 'star_border'
            } else {
                st.innerHTML = 'star'
            }
            let form = new FormData();
            console.log(e.target.dataset.mailid);
            form.set("mailID", e.target.dataset.mailid);
            console.log(JSON.stringify(form));
            let response = fetch('starMail', {
                method: 'POST',
                body: form,
            }).then((data) => data.json())
                .then(data => data)
                .catch((err) => {
                    console.log(err)
                })

        });

        let del = list.querySelector('.delteIcon');
        del.addEventListener('click', (e) => {
            let form = new FormData();
            console.log(e.target.dataset.mailid);
            form.set("mailID", e.target.dataset.mailid);
            console.log(JSON.stringify(form));
            let response = fetch('binMail', {
                method: 'POST',
                body: form,
            }).then((data) => data.json())
                .then(data => data)
                .catch((err) => {
                    console.log(err)
                })
            document.getElementById(e.target.dataset.mailid).remove();
        })
        let card = list.querySelector('.truncate');
        card.addEventListener('click', (e) => {
            console.log(Username, e.target.dataset.mailid);
            sessionStorage.setItem(Username, e.target.dataset.mailid);
            location.href = 'mailview.jsp';
        })
    });
}
/**
 * function name addcomposelisteners 
 * adds listeners to the compose div popup
 */
function addComposelisterners() {
    console.log('Fetching composeWrap')
    let lists = document.getElementById('cmpBX').querySelectorAll('li');
    console.log(lists)
    lists.forEach(list => {
        list.querySelector('.closeCompose').onclick = function (e) {
            let compose = list;
            console.log(compose.id);
            compose.remove();
        }
        list.querySelector('#sendBtn').onclick = (e) => {
            e.preventDefault();
            let formdat = new FormData(f1);
            let tomail = document.getElementById("To").value;
            let mailsub = document.getElementById("Subject").value;
            let mailcnt = document.getElementById("mailContent").value;
            formdat.set("mailtype", "inbox");
            formdat.set("frommail", sessionStorage.getItem("Username"));
            formdat.set("tomail", tomail);
            formdat.set("mailsub", mailsub);
            formdat.set("mailcnt", mailcnt);
            sendmail(formdat);
        }
        list.querySelector('#draftBtn').onclick = (e) => {
            let formdat = new FormData(f1);
            formdat.set("mailtype", "draft");
            sendmail(formdat);
        }
    });
}

const makeVisible = (containerID) => {
    let containers = ["inboxmailList", "draftmailList", "sentmailList", "starredmailList", "trashmailList"]
    for (let container of containers)
    {
        if (container == containerID)
        {
            console.log(container)
            document.getElementById(container).style.display = "block";
            continue;
        }
        document.getElementById(container).style.display = "none";    
    }
}

/**
 * 
 * @param {string} emailID user mailID
 * @param {string} type type of the mail to be retrieved
 */

var fetchMail = async (emailID, type) => {
    makeVisible(`${type}mailList`);
    let form = new FormData();
    form.set("emailID", emailID);
    form.set("mailtype", type);
    console.log(form);
    let response = await fetch('FetchMails', {
        method: 'POST',
        body: form,
    }).then((data) => data.json())
        .then(data => data)
        .catch((err) => {
            console.log(err)
        })
    console.log(response)
    renderMail(`${type}mailList`, response);
}

document.querySelector('.logoutBox').addEventListener('click', () => {
    sessionStorage.clear();
    location.href = 'index.html'
});

inbox.onclick = () => {
    fetchMail(Username, "inbox");
}
draft.onclick = () => {
    fetchMail(Username, "draft");
}
sent.onclick = () => {
    fetchMail(Username, "sent");
}
starred.onclick = () => {
    fetchMail(Username, "starred");
}
Trash.onclick = () => {
    fetchMail(Username, "trash");
}


function renderMail(mailrendertype,maildata) {
    document.getElementById(mailrendertype).innerHTML = "";
    if (maildata.length == 0) {
        document.getElementById(mailrendertype).innerHTML = `<h5 style="margin:auto; color:aliceblue;" > No messages found</h5>`;
        return;
    }
    for (let i = 0; i < maildata.length; i++) {
        var mailStr = `<li>
                              <div class="myCard" id=${maildata[i].mailid} data-mailid=${maildata[i].mailid}>
                                    <img data-mailid=${maildata[i].mailid} src="https://img.icons8.com/material-rounded/24/000000/important-mail.png"
                                          style="margin: 0 10px;" />
                                    <div class="text truncate" data-mailid=${maildata[i].mailid} >
                                    <b data-mailid=${maildata[i].mailid}> ${maildata[i].mailcontent['sname']}</b>&nbsp<strong data-mailid=${maildata[i].mailid}>${maildata[i].mailcontent['subject']}</strong>
                                     &nbsp${maildata[i].mailcontent['message']}
                                    </div>
                                    <div id="mailTime" style="margin-left:auto" data-mailid=${maildata[i].mailid}>
                                          <p style="display: block;width: 100px;padding-right: 5px;right: 5px;"> 
                                                <strong>${maildata[i].maildate} </strong>
                                          </p>
                                    </div>
                                    <div id="mailOptions" data-mailid=${maildata[i].mailid}>
                                          <i class="small material-icons starIcon" id="star_inbox_read" data-mailid=${maildata[i].mailid}
                                                style="margin: 0 10px;">star_border</i>
                                          <i class="small material-icons delteIcon" id="delete_inbox_read" data-mailid=${maildata[i].mailid}
                                                style="margin: 0 10px;">delete</i>
                                    </div>
                              </div>
                        </li>`;
        document.getElementById(mailrendertype).innerHTML += mailStr;
    }
    addmailListeners();
}
//TODO fetchMail(Username, "inbox");

var homeContents;
var flag = true;
var composeBoxElement = `
    <div class="composeWrap">
        <div class="headerBox">
            <p style="margin-left:3%;transform: translateY(-10px);" color:white>New Message<p>
            <div class="closeCompose">X</div>
        </div>
        <div style="height:450px;width:500px">
            <div class="head_area">
                <div class="row" style="margin-bottom: 0px;">
                    <form id="f1" >
                        <input type="text" placeholder="Recepients:" id="To" name="To" />
                        <input type="text" id="Subject" placeholder="Subject" name="Subject" />
                        <textarea placeholder="Content Here!" id="mailContent" class="txtarea" spellcheck="true"></textarea>
                        <br>
                        <label for="myfile">Select files:</label>
                        <input type="file" id="myfile" name="myfile" multiple style="float: left;">
                    </form>
                    <button id="sendBtn" value="Send" >
                        <i class="material-icons">send</i>
                    </button>
                    <button id="draftBtn" value="Draft" >
                        <i class="material-icons">drafts</i>
                    </button>                       
                    <div class="col l3 m4 s12">
                        <div class="row extraIcons"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>`;

/**
 * creates a new compose element on the page
 */
function addComposeBox() {
    var cmpBox = document.getElementById("cmpBX");
    var composeBox = document.createElement('li');
    composeBox.innerHTML = composeBoxElement;
    cmpBox.append(composeBox);
    addComposelisterners();
}
/**
 * initiates an SMPT request to the server to send an Email
 * @param {*} formdat 
 */
const sendmail = async (formdat) => {
    
    console.log(JSON.stringify(formdat));
    let data = await fetch('SMTP', {
        method: 'POST',
        body: formdat,
    }).then((data) => data.json())
        .then(data => data)
        .catch((err) => {
            console.log(err)
        })
    console.log(data);
    return data;
}

